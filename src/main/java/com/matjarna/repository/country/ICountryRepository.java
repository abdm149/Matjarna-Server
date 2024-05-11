package com.matjarna.repository.country;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.matjarna.model.country.Country;

@Repository
public interface ICountryRepository extends JpaRepository<Country, Long> {

	@Query("select c from Country c where lower(c.code) = lower(?1)")
	Country getByCode(String code);

}
