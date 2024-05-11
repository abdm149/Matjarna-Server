package com.matjarna.dto;

import java.io.Serializable;

public class EntityUnique implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean unique;

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

}
