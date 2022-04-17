package com.patel.org.sewa.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.patel.org.sewa.model.request.SearchRequest;
import com.patel.org.sewa.model.response.SearchResponse;

public interface SearchController {

	// @PreAuthorize("hasAuthority('ROLE_ADMIN')") //roles - AMDIN
	// @PreAuthorize("hasAuthority('admin')") //authorities - ROLE_admin
	// @PreAuthorize("hasRole('ROLE_ADMIN')") // roles - ADMIN
	@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'SUPER_ADMIN1')") // roles- ADMIN
	// @PreAuthorize("hasRole('ADMIN') and hasRole('SUPER_ADMIN') and
	// hasRole('SUPER_ADMIN1')") // roles - ADMIN
	// @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or
	// hasRole('SUPER_ADMIN1')") // roles - ADMIN
	// @PreAuthorize("hasRole('admin')")//authorities - ROLE_admin
	@PostMapping(path = "/search")
	public ResponseEntity<SearchResponse> searchUsingPOST(HttpServletRequest req, @RequestBody SearchRequest request,
			Pageable pageable) throws IOException;
}
