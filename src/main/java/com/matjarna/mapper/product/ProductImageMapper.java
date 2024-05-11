package com.matjarna.mapper.product;

import org.mapstruct.Mapper;

import com.matjarna.dto.product.ProductImageDto;
import com.matjarna.model.product.ProductImage;

@Mapper(componentModel = "spring")
public abstract class ProductImageMapper {

	public abstract ProductImage toImage(ProductImageDto dto);

	public abstract ProductImageDto toDto(ProductImage image);

}
