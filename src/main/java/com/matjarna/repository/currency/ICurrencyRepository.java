package com.matjarna.repository.currency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.matjarna.model.currency.Currency;

@Repository
public interface ICurrencyRepository extends JpaRepository<Currency, Long> {

	@Query("select c from Currency c where c.code = ?1")
	Currency getByCode(String code);
}
