package com.matjarna.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.matjarna.model.country.Country;
import com.matjarna.model.language.Language;
import com.matjarna.model.product.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

	@Query("select p from Product p where p.code = ?1")
	Product getByCode(String code);

	@Query("select count(p) from Product p where p.parent.id = ?1")
	long getNumberOfProducts(long id);

	@Query("select distinct p from Product p left join fetch p.descriptions d left join fetch d.language l "
			+ "left join fetch p.prices pr left join fetch pr.country c where l = :language and (:active is null or p.active = :active) "
			+ "and (:parentId is null or p.parent.id = :parentId) " + "and (:isDiscounted is null "
			+ "or (:isDiscounted = true and exists (select 1 from Price prd where prd.product = p and prd.discount is not null)) "
			+ "or (:isDiscounted = false and exists (select 1 from Price prd where prd.product = p and prd.discount is null))) "
			+ "and (:searchTerm is null or lower(d.title) like lower(concat('%', :searchTerm, '%')) "
			+ "or lower(p.code) like lower(concat('%', :searchTerm, '%')))"
			+ "and (:country is null or pr.country = :country)")
	Page<Product> getProducts(Pageable pageable, Language language, Boolean active, String searchTerm, Long parentId,
			Boolean isDiscounted, Country country);

}
