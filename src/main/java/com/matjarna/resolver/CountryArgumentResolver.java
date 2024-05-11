package com.matjarna.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.matjarna.model.country.Country;
import com.matjarna.service.country.CountryService;

@Component
public class CountryArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	CountryService countryService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(Country.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		String countryCode = webRequest.getParameter("country");
		if (countryCode == null) {
			countryCode = "us";
		}
		if ("IL".equalsIgnoreCase(countryCode)) {
			countryCode = "ps";
		}
		return countryService.getByCodeOrDefault(countryCode);
	}
}
