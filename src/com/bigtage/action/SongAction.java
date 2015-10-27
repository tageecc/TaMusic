package com.bigtage.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigtage.bean.Song;
import com.bigtage.bean.User;
import com.bigtage.service.SongService;

@Controller
@RequestMapping("/song")
public class SongAction {
	@Resource
	private SongService songService;

	/**
	 * 默认播放模式为 随便听听
	 * 
	 * @param str
	 * @param pwd
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/play")
	@ResponseBody
	public Map<String, Object> randomPlay(HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		Song song = songService.randomPlay((User) session.getAttribute("user"));
		map.put("status", true);
		map.put("song", song);
		return map;

	}

	@RequestMapping("/{id}")
	@ResponseBody
	public Map<String, Object> getSongById(@PathVariable int id,
			HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		Song song = songService.getSongById(id);
		if (song != null) {
			map.put("status", true);
			map.put("song", song);
		} else {
			map.put("status", false);
		}
		return map;

	}

	@RequestMapping("/previous")
	@ResponseBody
	public Map<String, Object> getPrevious(HttpServletRequest request,
			HttpSession session) {

		Map<String, Object> map = new HashMap<String, Object>();

		// 判断用户是否登陆，若没登陆则直接返回随机
		User user = (User) session.getAttribute("user");
		if (user == null) {
			Song song = songService.randomPlay(null);
			map.put("status", true);
			map.put("song", song);
			return map;
		}
		Song song = songService.getPrevious(user.getUid());
		if (song != null) {
			map.put("status", true);
			map.put("song", song);
			return map;
		} else {
			map.put("status", false);
			map.put("msg", "第一次来听歌就点上一曲的你真是够了!");
			return map;
		}

	}
}
