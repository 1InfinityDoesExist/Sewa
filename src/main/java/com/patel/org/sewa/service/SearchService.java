package com.patel.org.sewa.service;

import java.io.IOException;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.patel.org.sewa.model.request.SearchRequest;
import com.patel.org.sewa.model.response.SearchResponse;

@Service
public interface SearchService {

	public SearchResponse search(SearchRequest searchRequest, Pageable pageable) throws IOException;
}
