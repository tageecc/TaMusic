package com.bigtage.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	private int uid;
	private String username;
	private String password;
	private String email;
	private Integer phone;

	public User() {
		super();
	}

	public User(int uid, String username, String password, String email,
			int phone) {
		super();
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
	}

	@Id
	@GeneratedValue
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", password="
				+ password + ", email=" + email + ", phone=" + phone + "]";
	}

}
