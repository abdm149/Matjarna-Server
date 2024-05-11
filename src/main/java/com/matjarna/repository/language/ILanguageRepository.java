package com.matjarna.repository.language;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.matjarna.model.language.Language;

@Repository
public interface ILanguageRepository extends JpaRepository<Language, Long> {

	@Query("select l from Language l where l.code = ?1")
	Language getByCode(String code);

}
