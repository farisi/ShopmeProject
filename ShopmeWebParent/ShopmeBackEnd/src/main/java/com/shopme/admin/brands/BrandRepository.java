package com.shopme.admin.brands;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entities.Brand;

public interface BrandRepository extends PagingAndSortingRepository<Brand, Integer>, ListCrudRepository<Brand, Integer>{

}
