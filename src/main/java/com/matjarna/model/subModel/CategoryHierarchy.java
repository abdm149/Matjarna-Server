package com.matjarna.model.subModel;

public class CategoryHierarchy {

	private Long id;

	private String code;

	private int sortOrder;

	private Long parentId;

	public CategoryHierarchy() {

	}

	public CategoryHierarchy(Long id, String code, int sortOrder, Long parentId) {
		this.id = id;
		this.code = code;
		this.sortOrder = sortOrder;
		this.parentId = parentId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
