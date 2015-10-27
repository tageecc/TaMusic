package com.bigtage.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.bigtage.bean.User;
import com.bigtage.dao.UserDAO;

@Repository
public class UserDAOImpl implements UserDAO {

	@Resource
	private SessionFactory sessionFactory;

	@Override
	public boolean save(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> findById(int uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByNameAndPwd(String name, String pwd) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		List<User> users = null;
		try {
			tx = session.beginTransaction();
			users = session.createQuery(
					"from User u where u.username='" + name
							+ "' and u.password='" + pwd + "'").list();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return users;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByEmailAndPwd(String email, String pwd) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		List<User> users = null;
		try {
			tx = session.beginTransaction();
			users = session.createQuery(
					"from User u where u.email='" + email
							+ "' and u.password='" + pwd + "'").list();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return users;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByPhoneAndPwd(String phone, String pwd) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		List<User> users = null;
		try {
			tx = session.beginTransaction();
			users = session.createQuery(
					"from User u where u.phone='" + phone
							+ "' and u.password='" + pwd + "'").list();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return users;
	}

}
