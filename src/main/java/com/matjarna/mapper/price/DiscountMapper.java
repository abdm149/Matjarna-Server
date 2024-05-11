package com.matjarna.mapper.price;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.matjarna.constants.Constants;
import com.matjarna.dto.price.DiscountDto;
import com.matjarna.model.price.Discount;

@Mapper(componentModel = "spring")
public abstract class DiscountMapper {

	@Mappings({ @Mapping(source = "startDate", target = "startDate", qualifiedByName = "mapStringToDate"),
			@Mapping(source = "endDate", target = "endDate", qualifiedByName = "mapStringToDate") })
	public abstract Discount toDiscount(DiscountDto dto);

	@Mappings({ @Mapping(source = "startDate", target = "startDate", qualifiedByName = "mapDateToString"),
			@Mapping(source = "endDate", target = "endDate", qualifiedByName = "mapDateToString") })
	public abstract DiscountDto toDto(Discount discount);

	@Named("mapStringToDate")
	public Date mapStringToDate(String string) {
		if (string == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
		try {
			return formatter.parse(string);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Named("mapDateToString")
	public String mapDateToString(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
		return formatter.format(date);
	}

}
