package com.bigtage.dao;

import java.util.List;

import com.bigtage.bean.User;

public interface UserDAO {

	public boolean save(User user);

	public List<User> findById(int uid);

	public List<User> findByNameAndPwd(String name, String pwd);

	public List<User> findByEmailAndPwd(String email, String pwd);

	public List<User> findByPhoneAndPwd(String phone, String pwd);
}
