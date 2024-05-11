package com.matjarna.mapper.category;

import java.util.List;

import org.mapstruct.Mapper;

import com.matjarna.dto.category.CategoryHierarchyDto;
import com.matjarna.model.subModel.CategoryHierarchy;

@Mapper(componentModel = "spring")
public abstract class CategoryHierarchyMapper {

	public abstract List<CategoryHierarchyDto> toDto(List<CategoryHierarchy> categories);

}
