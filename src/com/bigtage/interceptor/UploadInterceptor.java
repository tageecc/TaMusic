package com.bigtage.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bigtage.bean.User;

public class UploadInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		System.out.println("UploadInterceptor->afterCompletion");
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("UploadInterceptor->postHandle");

	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
		System.out.println("UploadInterceptor->preHandle");
		// 判断用户是否登陆
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			System.out.println("user存在");
			return true;
		}
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		Map<String, Object> map = new HashMap<String, Object>() {
			{
				put("status", false);
				put("msg", "请先登录");
				put("url", "login.html");
			}
		};
		response.getWriter().print(map);

		return false;
	}
}
