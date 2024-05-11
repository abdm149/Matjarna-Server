package com.matjarna.mapper.category;

import org.mapstruct.Mapper;

import com.matjarna.dto.category.CategoryDescriptionDto;
import com.matjarna.mapper.language.LanguageMapper;
import com.matjarna.model.category.CategoryDescription;

@Mapper(componentModel = "spring", uses = LanguageMapper.class)
public interface CategoryDescriptionMapper {

	CategoryDescription toCategoryDescription(CategoryDescriptionDto dto);

	CategoryDescriptionDto toCategoryDescriptionDto(CategoryDescription description);

}