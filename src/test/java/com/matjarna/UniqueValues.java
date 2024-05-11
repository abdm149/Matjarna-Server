package com.matjarna;

public class UniqueValues {

	private Long id;
	private String uniqueValue;

	public UniqueValues(Long id, String uniqueValue) {
		super();
		this.id = id;
		this.uniqueValue = uniqueValue;
	}

	public Long getId() {
		return id;
	}

	public String getUniqueValue() {
		return uniqueValue;
	}
}
