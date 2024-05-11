package com.matjarna.dto.price;

import java.util.Objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PriceDto {

	private Long id;

	@NotNull(message = "Invalid blank price")
	private Double price;

	@NotBlank(message = "Invalid blank country")
	private String countryCode;

	@NotBlank(message = "Invalid blank currency")
	private String currencyCode;

	@Valid
	private DiscountDto discount;

	public PriceDto() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public DiscountDto getDiscount() {
		return discount;
	}

	public void setDiscount(DiscountDto discount) {
		this.discount = discount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PriceDto price = (PriceDto) o;

		return price.getPrice().equals(this.price)
				&& price.getCountryCode().toLowerCase().equals(countryCode.toLowerCase());
	}

	@Override
	public int hashCode() {
		return Objects.hash(price, countryCode, currencyCode);
	}

}
