package com.shopme.admin.brands;

import java.util.List;
import java.util.Optional;

import com.shopme.common.entities.Brand;

public interface BrandService {
	public Brand save(Brand brand);
	public List<Brand> brands();
	public Optional<Brand> findOne(Integer id);
	public void delete(Brand brand);
}
