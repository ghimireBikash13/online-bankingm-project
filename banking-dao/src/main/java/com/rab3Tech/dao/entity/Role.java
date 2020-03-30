package com.rab3Tech.dao.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "roles_tbl")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int rid;

	@Column(length = 45)
	private String name;

	@Column(length = 100)
	private String description;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "rid"), inverseJoinColumns = @JoinColumn(name = "loginid"))
	private Set<Login> logins;

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Login> getLogins() {
		return logins;
	}

	public void setLogins(Set<Login> logins) {
		this.logins = logins;
	}

}
