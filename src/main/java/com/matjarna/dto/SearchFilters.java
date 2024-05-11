package com.matjarna.dto;

public class SearchFilters {

	private Boolean active;

	private String searchTerm;

	public SearchFilters() {
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

}
