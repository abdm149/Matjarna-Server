package com.matjarna.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.matjarna.model.language.Language;
import com.matjarna.service.language.LanguageService;

@Component
public class LanguageArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	LanguageService languageService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(Language.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		String languageCode = webRequest.getParameter("language");
		if (languageCode == null) {
			languageCode = "en";
		}
		return languageService.getByCode(languageCode);
	}
}
