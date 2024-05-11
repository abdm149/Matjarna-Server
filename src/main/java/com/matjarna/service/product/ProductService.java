package com.matjarna.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.matjarna.dto.product.ProductFilters;
import com.matjarna.exception.ServiceException;
import com.matjarna.model.country.Country;
import com.matjarna.model.language.Language;
import com.matjarna.model.price.Discount;
import com.matjarna.model.price.Price;
import com.matjarna.model.product.Product;
import com.matjarna.model.product.ProductDescription;
import com.matjarna.model.product.ProductImage;
import com.matjarna.repository.product.IProductRepository;
import com.matjarna.service.image.ImageService;

@Service
public class ProductService implements IProductService {

	@Autowired
	IProductRepository productRepository;

	@Autowired
	private ImageService imageService;

	@Override
	public Product saveProduct(Product product) {

		boolean update = product.getId() != null;

		product.setCode(product.getCode().toLowerCase());
		Set<ProductDescription> descriptions = product.getDescriptions();
		for (ProductDescription description : descriptions) {
			description.setProduct(product);
		}

		Set<ProductImage> images = product.getSubImages();
		for (ProductImage image : images) {
			image.setProduct(product);
		}

		Set<Price> prices = product.getPrices();
		for (Price price : prices) {
			Discount discount = price.getDiscount();
			if (discount != null) {
				discount.setPrice(price);
			}
			price.setProduct(product);
		}

		String oldImagePath = null;
		List<ProductImage> deletedImages = null;
		if (update) {
			Product oldProduct = productRepository.findById(product.getId()).get();
			if (!oldProduct.getImage().equals(product.getImage())) {
				oldImagePath = oldProduct.getImage();
			}

			deletedImages = processMedia(oldProduct, product);
		}

		Product productToReturn = productRepository.save(product);
		if (oldImagePath != null) {
			imageService.deleteImage(oldImagePath);
		}
		if (deletedImages != null) {
			for (ProductImage img : deletedImages) {
				imageService.deleteImage(img.getPath());
			}
		}
		return productToReturn;
	}

	private List<ProductImage> processMedia(Product oldProduct, Product newProduct) {
		List<ProductImage> deletedImages = new ArrayList<>();
		Set<ProductImage> processedImages = oldProduct.getSubImages();
		List<ProductImage> givenImages = new ArrayList<>(newProduct.getSubImages());

		for (ProductImage image : processedImages) {
			int index = givenImages.indexOf(image);
			if (index >= 0) {
				ProductImage updatedImage = givenImages.remove(index);
				image.setSortOrder(updatedImage.getSortOrder());

			} else {
				deletedImages.add(image);
			}
		}
		processedImages.removeAll(deletedImages);
		processedImages.addAll(givenImages);
		newProduct.setSubImages(processedImages);
		return deletedImages;
	}

	@Override
	public Product getByCode(String code) {
		return productRepository.getByCode(code.toLowerCase());
	}

	@Override
	public long getNumberOfProducts(long id) {
		return productRepository.getNumberOfProducts(id);
	}

	@Override
	public Optional<Product> getById(long id) {
		return productRepository.findById(id);
	}

	@Override
	public Page<Product> getProducts(Pageable pageable, Language language, ProductFilters search, Country country) {
		Page<Product> page = productRepository.getProducts(pageable, language, search.isActive(),
				search.getSearchTerm(), search.getParentId(), search.isDiscounted(), country);
		return page;
	}

	@Override
	public Optional<Product> getProductById(Long id) {
		return productRepository.findById(id);
	}

	public void deleteProduct(Long id) {
		Optional<Product> productToDelete = productRepository.findById(id);
		if (!productToDelete.isPresent()) {
			throw new ServiceException(String.format("Product with id %d wasn't found", id));
		}
		productRepository.deleteById(id);
		imageService.deleteImage(productToDelete.get().getImage());
		Set<ProductImage> productSubImages = productToDelete.get().getSubImages();
		for (ProductImage subImage : productSubImages) {
			imageService.deleteImage(subImage.getPath());
		}

	}

}
