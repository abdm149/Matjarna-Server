package com.matjarna.dto.price;

import java.util.Objects;

import com.matjarna.validation.endAfterStart.Durationable;
import com.matjarna.validation.endAfterStart.EndAfterStart;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@EndAfterStart
public class DiscountDto implements Durationable {

	private Long id;

	@NotNull(message = "Invalid null value")
	private Double value;

	@NotBlank(message = "Invalid null start date")
	private String startDate;

	private String endDate;

	public DiscountDto() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	@Override
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DiscountDto discount = (DiscountDto) o;
		return discount.getStartDate().equals(startDate) && discount.value.equals(value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(startDate, value);
	}

}
