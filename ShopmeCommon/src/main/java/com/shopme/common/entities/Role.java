package com.shopme.common.entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 64, nullable = false, unique = true)
	private String name;

	@Column(length = 191, name = "display_name")
	private String displayName;

	@Column(length = 2000)
	private String description;
	
	public Role() {}

	public Role(Integer id, String name, String displayName, String description) {
		super();
		this.id = id;
		this.name = name;
		this.displayName = displayName;
		this.description = description;
	}

	public Role(Integer id) {
		this.id = id;
	}

	public Role(String name) {
		super();
		this.name = name;
	}

	public Role(String name, String displayName, String description) {
		super();
		this.name = name;
		this.displayName = displayName;
		this.description = description;
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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Role [name=" + name + ", displayName=" + displayName + ", description=" + description + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if(id==null) {
			if(other.id !=null)
				return false;
		}
		else if(!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
	
}
