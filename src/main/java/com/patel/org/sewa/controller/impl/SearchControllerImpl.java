package com.patel.org.sewa.controller.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.patel.org.sewa.controller.SearchController;
import com.patel.org.sewa.model.request.SearchRequest;
import com.patel.org.sewa.model.response.SearchResponse;
import com.patel.org.sewa.service.SearchService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SearchControllerImpl implements SearchController {

	@Autowired
	private SearchService searchService;

	@Override
	public ResponseEntity<SearchResponse> searchUsingPOST(HttpServletRequest req,
			@RequestBody SearchRequest searchRequest, Pageable pageable) throws IOException {
		log.info("-----Search api method.----");
		return ResponseEntity.status(HttpStatus.OK).body(searchService.search(searchRequest, pageable));
	}

}
