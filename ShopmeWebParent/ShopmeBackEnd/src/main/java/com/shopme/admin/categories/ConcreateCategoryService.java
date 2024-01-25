package com.shopme.admin.categories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopme.common.entities.Category;

@Service
public class ConcreateCategoryService implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Cacheable(cacheNames = "categoriesCache", key="'allCategories'")
	@Transactional(readOnly = true)
	@Override
	public List<Category> categories() {
		// TODO Auto-generated method stub
		List<Category> categories = categoryRepo.findByParentIsNull();
		return listHierarchicalCategories(categories);
	}
	
	@Cacheable(cacheNames = "categoriesCache", key="'categoriesUseByForm'")
	@Transactional(readOnly = true)
	@Override
	public List<Category> listCategoryUseByForm() {
		
		List<Category> categoriesUseByForm = categoryRepo.findByParentIsNull();
		return listHierarchicalCategories(categoriesUseByForm);
	}
	
	private List<Category> listHierarchicalCategories(List<Category> rootCategories){
		List<Category> hierarchicalCategories = new ArrayList<>();
		for(Category rootCategory : rootCategories) {
			hierarchicalCategories.add(Category.copyFull(rootCategory));
			Set<Category> children = rootCategory.getChildren();
			for(Category subCategory : children) {
				String name="--" + subCategory.getName();
				hierarchicalCategories.add(Category.copyFull(rootCategory,name));
				listSubHierarchicalCategories(hierarchicalCategories, subCategory, 1);
			}
		}
		return hierarchicalCategories;
	}
	

	
	private void listSubHierarchicalCategories(List<Category> hierarchicalCategories,Category parent, int subLevel) {
		int newSubLevel=subLevel + 1;
		Set<Category> children = parent.getChildren();
		for(Category subCategory : children) {
			String name="";
			for(int i=0; i < newSubLevel; i++) {
				name+="--" + subCategory.getName();
			}
			name += subCategory.getName();
			hierarchicalCategories.add(Category.copyFull(subCategory,name));
			listSubHierarchicalCategories(hierarchicalCategories, subCategory, newSubLevel);
		}
	}
	
	private void  listSubCategoriesUsedInForm(List<Category> categoriesUsedInForm, Category parent, int subLevel) {
		int newSubLevel=subLevel + 1;
		Set<Category> children=parent.getChildren();
		for(Category subCategory : children) {
			String name="";
			for(int i=0; i < newSubLevel; i++) {
				name+="--" + subCategory.getName();
			}
			name += subCategory.getName();
			categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(),name));
			listSubHierarchicalCategories(categoriesUsedInForm,subCategory, newSubLevel);
		}
	}

	@CacheEvict(cacheNames = "categoriesCache", allEntries=true)
	@Transactional
	@Override
	public Category save(Category category) {
		// TODO Auto-generated method stub
		return categoryRepo.save(category);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Category> findOne(Integer id) {
		// TODO Auto-generated method stub
		return categoryRepo.findById(id);
	}
	
	@CacheEvict(cacheNames = "categoriesCache", allEntries=true)
	@Transactional
	@Override
	public void delete(Category category) {
		// TODO Auto-generated method stub
		categoryRepo.delete(category);
	}
}
