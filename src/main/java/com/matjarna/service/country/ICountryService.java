package com.matjarna.service.country;

import com.matjarna.model.country.Country;

public interface ICountryService {

	boolean isEmpty();

	Country createCountry(Country country);

	Country getByCode(String code);

	Country getByCodeOrDefault(String code);

}
