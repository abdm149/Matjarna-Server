package com.matjarna.repository.category;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.matjarna.model.category.Category;
import com.matjarna.model.language.Language;
import com.matjarna.model.subModel.CategoryHierarchy;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {

	@Query("select c from Category c where c.code = ?1")
	Category getByCode(String code);

	@Query("select distinct c from Category c left join fetch c.descriptions d join d.language l "
			+ "where l = :language and (:active is null or c.active = :active) "
			+ "and ((:parentId is null and c.parent is null) or (c.parent.id = :parentId)) "
			+ "and (:searchTerm is null or lower(d.title) like lower(concat('%', :searchTerm, '%')) "
			+ "or lower(c.code) like lower(concat('%', :searchTerm, '%')))")
	Page<Category> getCategories(Pageable pageable, Language language, Boolean active, String searchTerm,
			Long parentId);

	@Query("select count(c) from Category c where c.parent.id = ?1")
	long getNumberOfChildren(long parnetId);

	@Query("select new com.matjarna.model.subModel.CategoryHierarchy(c.id, c.code, c.sortOrder, c.parent.id) from Category c ORDER BY "
			+ "CASE WHEN c.parent IS NULL THEN 0 ELSE 1 END, c.parent.id NULLS FIRST, c.sortOrder")
	List<CategoryHierarchy> getAllCategories();

}
