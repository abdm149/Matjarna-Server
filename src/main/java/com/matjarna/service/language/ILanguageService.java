package com.matjarna.service.language;

import com.matjarna.model.language.Language;

public interface ILanguageService {

	Language createLanguage(Language language);

	boolean isEmpty();

	Language getByCode(String code);
}
