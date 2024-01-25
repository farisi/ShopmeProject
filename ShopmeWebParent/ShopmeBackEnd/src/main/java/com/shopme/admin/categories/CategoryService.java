package com.shopme.admin.categories;

import java.util.List;
import java.util.Optional;

import com.shopme.common.entities.Category;

public interface CategoryService {
	List<Category> categories();
	Category save(Category category);
	Optional<Category> findOne(Integer id);
	void delete(Category category);
	List<Category> listCategoryUseByForm();
}
