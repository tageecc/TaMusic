package com.bigtage.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bigtage.bean.User;
import com.bigtage.dao.UserDAO;
import com.bigtage.util.Common;

@Service
public class UserService {
	@Resource
	private UserDAO uDAO;

	public List<User> login(String str, String pwd) {
		List<User> users;
		if (Common.isNumer(str)) {
			users = uDAO.findByPhoneAndPwd(str, pwd);
		} else if (Common.isemail(str)) {
			users = uDAO.findByEmailAndPwd(str, pwd);
		} else {
			users = uDAO.findByNameAndPwd(str, pwd);
		}
		return users;
	}
}
