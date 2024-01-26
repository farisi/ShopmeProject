package com.shopme.common.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "Name could not empty!")
	@Column(length=128, nullable = false, unique = true)
	private String name;
	
	@Column(length=64, nullable = false, unique = true)
	private String alias;
	
	private String description;
	
	@Column(length=128,nullable = false)
	private String image;
	
	private boolean enabled;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="parent_id", unique = false, nullable = true)
	private Category parent;
	
	@OneToMany(mappedBy = "parent",fetch=FetchType.LAZY)
	private Set<Category> children = new HashSet<>();
	
	public Category() {}
	public Category(String name) {
		this.setName(name);
	}
	
	public Category(Integer id,String name) {
		this.setName(name);
		this.id = id;
	}
	
	public static Category copyIdAndName(Category c) {
		Category copyCategory = new Category();
		copyCategory.setId(c.getId());
		copyCategory.setName(c.getName());
		return copyCategory;
	}
	
	public static Category copyIdAndName(Integer id, String name) {
		Category copyCategory = new Category();
		copyCategory.setId(id);
		copyCategory.setName(name);
		return copyCategory;
	}
	
	public static Category copyFull(Category c) {
		Category cpc = new Category();
		cpc.setId(c.getId());
		cpc.setAlias(c.getAlias());
		cpc.setName(c.getName());
		cpc.setEnabled(c.isEnabled());
		cpc.setImage(c.getImage());
		return cpc;
	}
	
	public static Category copyFull(Category c, String name) {
		Category cpc = copyFull(c);
		cpc.setName(name);
		return cpc;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public Set<Category> getChildren() {
		return children;
	}

	public void setChildren(Set<Category> children) {
		this.children = children;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
}