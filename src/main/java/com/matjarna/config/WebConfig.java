package com.matjarna.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.matjarna.resolver.CountryArgumentResolver;
import com.matjarna.resolver.LanguageArgumentResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	LanguageArgumentResolver languageArgumentResolver;

	@Autowired
	CountryArgumentResolver countryArgumentResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(languageArgumentResolver);
		resolvers.add(countryArgumentResolver);
	}

}