package com.rab3Tech.dao.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user_login_tbl")
public class Login {

	@Id
	private String loginid;
	private String name;
	private String password;
	private String locked;
	@Column(name = "no_of_attempt")
	private int noOfAttempt;
	private Timestamp llt;
	private Timestamp lwap;
	@Transient
	private String email;
	private Date passwordExpire;
	@Column(name = "token")
	private String token;

	@OneToMany(mappedBy = "login", cascade = CascadeType.ALL) // login is an attribute present inside
																// CustomerQuestionAnswer entity
	private List<CustomerQuestionAnswer> customerQuestionAnswers;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "loginid"), inverseJoinColumns = @JoinColumn(name = "rid"))
	private Set<Role> roles;

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public int getNoOfAttempt() {
		return noOfAttempt;
	}

	public void setNoOfAttempt(int noOfAttempt) {
		this.noOfAttempt = noOfAttempt;
	}

	public Timestamp getLlt() {
		return llt;
	}

	public void setLlt(Timestamp llt) {
		this.llt = llt;
	}

	public Timestamp getLwap() {
		return lwap;
	}

	public void setLwap(Timestamp lwap) {
		this.lwap = lwap;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getPasswordExpire() {
		return passwordExpire;
	}

	public void setPasswordExpire(Date passwordExpire) {
		this.passwordExpire = passwordExpire;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<CustomerQuestionAnswer> getCustomerQuestionAnswers() {
		return customerQuestionAnswers;
	}

	public void setCustomerQuestionAnswers(List<CustomerQuestionAnswer> customerQuestionAnswers) {
		this.customerQuestionAnswers = customerQuestionAnswers;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
