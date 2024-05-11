package com.matjarna.service.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matjarna.model.currency.Currency;
import com.matjarna.repository.currency.ICurrencyRepository;

@Service
public class CurrencyService implements ICurrencyService {

	@Autowired
	ICurrencyRepository currencyRepository;

	@Override
	public boolean isEmpty() {
		return currencyRepository.count() == 0;
	}

	@Override
	public Currency createCurrency(Currency currency) {
		return currencyRepository.save(currency);
	}

	@Override
	public Currency getByCode(String code) {
		return currencyRepository.getByCode(code.toUpperCase());
	}

}
