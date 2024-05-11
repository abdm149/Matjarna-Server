package com.matjarna.dto.category;

import java.util.Objects;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CategoryDescriptionDto {

	private Long id;

	@NotBlank(message = "Invalid empty title")
	@NotNull(message = "Invalid null title")
	private String title;

	@Length(max = 255)
	private String description;

	@NotBlank(message = "Invalid empty language")
	@NotNull(message = "Invalid null language")
	private String language;

	public CategoryDescriptionDto() {
	}

	public CategoryDescriptionDto(CategoryDescriptionDto categoryDescriptionDto) {
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
		CategoryDescriptionDto categoryDescription = (CategoryDescriptionDto) o;
		return categoryDescription.getTitle().equals(title) && categoryDescription.getLanguage().equals(language);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, language);
	}

	@Override
	public String toString() {
		return "CategoryDescriptionDto [id=" + id + ", title=" + title + ", description=" + description + ", language="
				+ language + "]";
	}

}
