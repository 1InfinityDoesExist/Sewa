package com.patel.org.sewa.model.response;

import java.io.Serializable;
import java.util.List;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long pageNumber;
	private long pageSize;
	private long totalRecords;
	private List<JSONObject> response;

}
