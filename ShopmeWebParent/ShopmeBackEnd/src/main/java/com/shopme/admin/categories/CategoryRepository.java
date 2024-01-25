package com.shopme.admin.categories;


import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entities.Category;


public interface CategoryRepository extends PagingAndSortingRepository<Category,Integer>, ListCrudRepository<Category, Integer> {
	
	@EntityGraph(attributePaths = {"children"},type=EntityGraphType.FETCH)
	public List<Category> findAll();
	
	@EntityGraph(attributePaths = {"children"},type=EntityGraphType.FETCH)
	public List<Category> findByParentIsNull();
}
