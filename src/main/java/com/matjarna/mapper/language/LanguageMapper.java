package com.matjarna.mapper.language;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.matjarna.model.language.Language;
import com.matjarna.service.language.LanguageService;

@Mapper(componentModel = "spring")
public abstract class LanguageMapper {

	@Autowired
	private LanguageService languageService;

	public String languageToString(Language language) {
		return language.getCode();
	}

	public Language toLanguageEntity(String code) {
		if (code == null) {
			return null;
		}
		Language language = languageService.getByCode(code);
		return language;
	}

}
