package com.matjarna.service.currency;

import com.matjarna.model.currency.Currency;

public interface ICurrencyService {

	boolean isEmpty();

	Currency createCurrency(Currency currency);

	Currency getByCode(String code);
}
