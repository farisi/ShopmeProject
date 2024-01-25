package com.shopme.admin.brands;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entities.Brand;

@Service
public class BrandConcrete implements BrandService {
	
	@Autowired
	private BrandRepository brandRepo;

	@Override
	public Brand save(Brand brand) {
		// TODO Auto-generated method stub
		return brand;
	}

	@Override
	public List<Brand> brands() {
		// TODO Auto-generated method stub
		return brandRepo.findAll();
	}

	@Override
	public Optional<Brand> findOne(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void delete(Brand brand) {
		// TODO Auto-generated method stub
		brandRepo.delete(brand);
	}

}
