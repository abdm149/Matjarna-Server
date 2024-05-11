package com.matjarna.service.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matjarna.model.country.Country;
import com.matjarna.repository.country.ICountryRepository;

@Service
public class CountryService implements ICountryService {

	@Autowired
	ICountryRepository countryRepository;

	@Override
	public boolean isEmpty() {
		return countryRepository.count() == 0;
	}

	@Override
	public Country createCountry(Country country) {
		return countryRepository.save(country);
	}

	@Override
	public Country getByCode(String code) {
		return countryRepository.getByCode(code.toUpperCase());
	}

	@Override
	public Country getByCodeOrDefault(String code) {
		Country country = countryRepository.getByCode(code.toUpperCase());
		if (country == null) {
			country = countryRepository.getByCode("us".toUpperCase());
		}
		return country;
	}
}
