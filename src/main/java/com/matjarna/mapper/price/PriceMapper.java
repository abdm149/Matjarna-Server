package com.matjarna.mapper.price;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.matjarna.dto.price.PriceDto;
import com.matjarna.model.country.Country;
import com.matjarna.model.currency.Currency;
import com.matjarna.model.price.Price;
import com.matjarna.service.country.ICountryService;
import com.matjarna.service.currency.ICurrencyService;

@Mapper(componentModel = "spring", uses = DiscountMapper.class)
public abstract class PriceMapper {

	@Autowired
	ICountryService countryService;

	@Autowired
	ICurrencyService currencyService;

	@Mappings({ @Mapping(source = "countryCode", target = "country", qualifiedByName = "mapStringToCountry"),
			@Mapping(source = "currencyCode", target = "currency", qualifiedByName = "mapStringToCurrency") })
	public abstract Price toPrice(PriceDto dto);

	@Mappings({ @Mapping(source = "country", target = "countryCode", qualifiedByName = "mapCountryToString"),
			@Mapping(source = "currency", target = "currencyCode", qualifiedByName = "mapCurrencyToString") })
	public abstract PriceDto toDto(Price price);

	@Named("mapStringToCountry")
	Country mapStringToCountry(String countryCode) {
		return countryService.getByCode(countryCode);
	}

	@Named("mapCountryToString")
	String mapCountryToString(Country country) {
		return country.getCode();
	}

	@Named("mapStringToCurrency")
	Currency mapStringToCurrency(String currencyCode) {
		return currencyService.getByCode(currencyCode);
	}

	@Named("mapCurrencyToString")
	String mapCurrencyToString(Currency currency) {
		return currency.getCode();
	}

}
