package com.patel.org.sewa.controller;

import java.io.IOException;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.patel.org.sewa.model.request.SearchRequest;
import com.patel.org.sewa.model.response.SearchResponse;

public interface SearchController {

	@PostMapping(path = "/search")
	public ResponseEntity<SearchResponse> searchUsingPOST(@RequestBody SearchRequest request, Pageable pageable)
			throws IOException;
}
