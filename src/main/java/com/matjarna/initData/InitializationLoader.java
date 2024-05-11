package com.matjarna.initData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.matjarna.constants.Constants;
import com.matjarna.model.country.Country;
import com.matjarna.model.currency.Currency;
import com.matjarna.model.language.Language;
import com.matjarna.model.user.User;
import com.matjarna.service.country.ICountryService;
import com.matjarna.service.currency.ICurrencyService;
import com.matjarna.service.language.ILanguageService;
import com.matjarna.service.user.IUserService;

import jakarta.annotation.PostConstruct;

@Component
public class InitializationLoader {

	@Autowired
	private ILanguageService languageService;

	@Autowired
	private IUserService userService;

	@Autowired
	private ICountryService countryService;

	@Autowired
	private ICurrencyService currencyService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	public void init() {
		// TODO : Drop all if/else statements
		if (languageService.isEmpty()) {
			createLanguages();
		}
		if (countryService.isEmpty()) {
			createCountries();
		}
		if (currencyService.isEmpty()) {
			createCurrencies();
		}
		if (userService.findByEmail("admin@matjarna.com") == null) {
			createSuperAdmin();
		}

	}

	private void createLanguages() {
		for (String lang : Constants.LANGUAGES) {
			languageService.createLanguage(new Language(lang));
		}
	}

	private void createCountries() {
		for (String code : Constants.COUNTRY_CODE) {
			Country country = new Country();
			country.setCode(code);
			countryService.createCountry(country);
		}
	}

	private void createCurrencies() {
		for (String code : Constants.CURRENCY_MAP.keySet()) {
			Currency currency = new Currency();
			currency.setName(Constants.CURRENCY_MAP.get(code));
			currency.setCode(code);
			currencyService.createCurrency(currency);
		}
	}

	private void createSuperAdmin() {
		User superAdmin = new User();
		superAdmin.setEmail("admin@matjarna.com");
		superAdmin.setPassword(passwordEncoder.encode("Matjarna123"));
		superAdmin.setFirstName("Admin");
		superAdmin.setLastName("Matjana");
		userService.createUser(superAdmin);
	}

}
