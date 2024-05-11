package com.matjarna.dto.product;

import java.util.Objects;

import com.matjarna.dto.category.CategoryDescriptionDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductDescriptionDto {

	private Long id;

	@NotBlank(message = "Invalid empty title")
	@NotNull(message = "Invalid null title")
	private String title;

	private String description;

	@NotBlank(message = "Invalid empty language")
	@NotNull(message = "Invalid null language")
	private String language;

	public ProductDescriptionDto() {
	}

	public ProductDescriptionDto(CategoryDescriptionDto categoryDescriptionDto) {
		this.id = categoryDescriptionDto.getId();
		this.description = new String(categoryDescriptionDto.getDescription());
		this.language = new String(categoryDescriptionDto.getLanguage());
		this.title = new String(categoryDescriptionDto.getTitle());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ProductDescriptionDto productDescription = (ProductDescriptionDto) o;
		return productDescription.getTitle().equals(title) && productDescription.getLanguage().equals(language);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, language);
	}

	@Override
	public String toString() {
		return "ProductDescriptionDto [id=" + id + ", title=" + title + ", description=" + description + ", language="
				+ language + "]";
	}

}
