package com.bigtage.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigtage.bean.User;
import com.bigtage.service.UserService;
import com.bigtage.util.Common;

@Controller
@RequestMapping("/login")
public class LoginAction {

	@Resource
	private UserService uService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(@RequestParam String str,
			@RequestParam String pwd, HttpServletRequest request,
			HttpSession session) throws IOException {

		if (Common.isNull(str) && Common.isNull(pwd)) {
			List<User> users = uService.login(str, pwd);
			if (users.size() > 0) {
				session.setAttribute("user", users.get(0));// 设置session
				return new HashMap<String, Object>() {
					{
						put("status", true);
						put("msg", "登陆成功服务器正在跳转！");
						put("url", "index.html");
					}
				};
			}
		}
		return new HashMap<String, Object>() {
			{
				put("ststus", false);
				put("msg", "登陆失败,请确认登陆信息是否正确!");
			}
		};
	}
}
