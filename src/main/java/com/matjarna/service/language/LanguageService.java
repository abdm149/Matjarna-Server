package com.matjarna.service.language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matjarna.model.language.Language;
import com.matjarna.repository.language.ILanguageRepository;

@Service
public class LanguageService implements ILanguageService {

	@Autowired
	ILanguageRepository languageRepository;

	@Override
	public Language createLanguage(Language language) {
		return languageRepository.save(language);
	}

	@Override
	public boolean isEmpty() {
		return languageRepository.count() == 0;
	}

	@Override
	public Language getByCode(String code) {
		return languageRepository.getByCode(code);
	}
}
