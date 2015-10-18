package com.bigtage.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bigtage.bean.User;
import com.bigtage.dao.UserDAO;
import com.bigtage.util.SystemHibernateSupport;

@Repository
@SuppressWarnings("unchecked")
public class UserDAOImpl extends SystemHibernateSupport implements UserDAO {

	@Override
	public boolean save(User user) {
		return (boolean) getHibernateTemplate().save(user);
	}

	@Override
	public List<User> findById(int uid) {
		return (List<User>) getHibernateTemplate().find(
				"from User where uid='" + uid + "'");
	}

	@Override
	public List<User> findByNameAndPwd(String name, String pwd) {
		return (List<User>) getHibernateTemplate().find(
				"from User where username='" + name + "' and password='" + pwd
						+ "'");
	}

	@Override
	public List<User> findByEmailAndPwd(String email, String pwd) {
		return (List<User>) getHibernateTemplate().find(
				"from User where email='" + email + "' and password='" + pwd
						+ "'");
	}

	@Override
	public List<User> findByPhoneAndPwd(String phone, String pwd) {
		return (List<User>) getHibernateTemplate().find(
				"from User where phone='" + phone + "' and password='" + pwd
						+ "'");
	}

}
