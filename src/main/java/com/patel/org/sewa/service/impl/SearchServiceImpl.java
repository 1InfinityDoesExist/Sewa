package com.patel.org.sewa.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import com.patel.org.sewa.model.request.SearchRequest;
import com.patel.org.sewa.model.response.SearchResponse;
import com.patel.org.sewa.service.SearchService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SearchServiceImpl implements SearchService {

	@Autowired
	private RestHighLevelClient esClient;

	@Value("${search.regex:*%s*}")
	private String searchRegex;

	private static final ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@Override
	public SearchResponse search(SearchRequest searchRequest, Pageable pageable) throws IOException {

		String keyword = searchRequest.getKeyword();
		if (!ObjectUtils.isEmpty(keyword)) {
			keyword.replace(" ", "*");
		}
		log.info("-----Keyword : {}", keyword);

		Set<String> indices = new HashSet<String>();

		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

		log.info("----Query before : {}", boolQuery);
		searchRequest.getIndices().entrySet().stream().forEach(entry -> {
			indices.add(entry.getKey());
			buildSearchQuery(boolQuery, entry, keyword);
		});
		log.info("----Query after : {}", boolQuery);

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(boolQuery)
				.from(pageable.getPageNumber() * pageable.getPageSize()).size(pageable.getPageSize()).explain(true);

		// sorting
		Optional.ofNullable(pageable.getSort()).map(Sort::iterator)
				.ifPresent(iterator -> iterator.forEachRemaining(
						order -> searchSourceBuilder.sort(order.getProperty(), Optional.ofNullable(order.getDirection())
								.map(Direction::toString).map(SortOrder::fromString).orElse(SortOrder.ASC))));

		org.elasticsearch.action.search.SearchRequest request = new org.elasticsearch.action.search.SearchRequest()
				.indices(Iterables.toArray(indices, String.class)).source(searchSourceBuilder);
		org.elasticsearch.action.search.SearchResponse searchResponse = esClient.search(request,
				RequestOptions.DEFAULT);

		SearchHits searchHits = searchResponse.getHits();
		SearchHit[] hits = searchHits.getHits();

		List<JSONObject> response = Arrays.stream(hits).map(docuement -> {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject = (JSONObject) new JSONParser().parse(docuement.getSourceAsString());
				jsonObject.put("_id", docuement.getId());
				log.info("----Docuement object from elastic : {}", jsonObject);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return jsonObject;
		}).filter(Objects::nonNull).collect(Collectors.toList());

		return SearchResponse.builder().pageNumber(pageable.getPageNumber()).pageSize(pageable.getPageSize())
				.totalRecords(searchHits.getTotalHits().value).response(response).build();
	}

	/**
	 * 
	 * @param boolQuery
	 * @param entry
	 * @param keyword
	 */
	private void buildSearchQuery(BoolQueryBuilder boolQuery, Entry<String, List<String>> entry, String keyword) {

		log.info("-----Building query builder-----");
		BoolQueryBuilder bQuery = QueryBuilders.boolQuery().must(QueryBuilders.typeQuery("instance"));

		if (CollectionUtils.isEmpty(entry.getValue())) {
			bQuery.must(QueryBuilders.queryStringQuery(String.format(searchRegex, keyword)));
		} else {
			BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
			for (String field : entry.getValue()) {
				queryBuilder.should(QueryBuilders.queryStringQuery(String.format(searchRegex, keyword)).field(field));
			}
			bQuery.must(queryBuilder);
		}
		boolQuery.should(bQuery);
	}
}
