package com.matjarna.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;

public class EntityDtoList<T, R> {

	private List<T> results = new ArrayList<>();

	private long totalNumber;

	public EntityDtoList() {
	}

	public EntityDtoList(Page<R> page, Function<R, T> mapper) {
		for (R entity : page.getContent()) {
			results.add(mapper.apply(entity));
		}
		setTotalNumber(page.getTotalElements());
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public long getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(long totalNumber) {
		this.totalNumber = totalNumber;
	}

}
