package com.matjarna.mapper.product;

import org.mapstruct.Mapper;

import com.matjarna.dto.product.ProductDescriptionDto;
import com.matjarna.mapper.language.LanguageMapper;
import com.matjarna.model.product.ProductDescription;

@Mapper(componentModel = "spring", uses = LanguageMapper.class)
public abstract class ProductDescriptionMapper {

	public abstract ProductDescription toProductDescription(ProductDescriptionDto dto);

	public abstract ProductDescriptionDto toProductDescriptionDto(ProductDescription description);
}
