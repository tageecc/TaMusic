package com.bigtage.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigtage.bean.DisLike;
import com.bigtage.bean.HistorySong;
import com.bigtage.bean.Like;
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
	@RequestMapping("/play/{panel}")
	@ResponseBody
	public Map<String, Object> randomPlay(@PathVariable String panel,
			HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			map.put("status", false);
			map.put("msg", "暂时不开放游客，请先登录");
			return map;
		}
		Song song = songService.playSong((User) session.getAttribute("user"),
				panel);

		if (song != null) {
			map.put("status", true);
			map.put("song", song);
			// 获得是否喜欢此歌曲
			if (songService.isLike(new Like(user.getUid(), song.getSongid()))) {
				map.put("islike", true);
			}

		} else {
			map.put("status", false);
			map.put("msg", "出错了");
		}

		return map;

	}

	/**
	 * 根据id获取歌曲
	 * 
	 * @param id
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/{id}")
	@ResponseBody
	public Map<String, Object> getSongById(@PathVariable int id,
			HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			map.put("status", false);
			map.put("msg", "暂时不开放游客，请先登录");
			return map;
		}
		Song song = songService.getSongById(id);
		if (song != null) {
			map.put("status", true);
			map.put("song", song);
			// 获得是否喜欢此歌曲
			if (songService.isLike(new Like(user.getUid(), song.getSongid()))) {
				map.put("islike", true);
			}
		} else {
			map.put("status", false);
		}
		return map;

	}

	/**
	 * 获取上一曲
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/previous")
	@ResponseBody
	public Map<String, Object> getPrevious(HttpServletRequest request,
			HttpSession session) {

		Map<String, Object> map = new HashMap<String, Object>();

		// 判断用户是否登陆，若没登陆则直接返回随机
		User user = (User) session.getAttribute("user");
		if (user == null) {
			Song song = songService.playSong(null, "");
			map.put("status", true);
			map.put("song", song);
			return map;
		}
		Song song = songService.getPrevious(user.getUid());
		if (song != null) {
			map.put("status", true);
			map.put("song", song);
			// 获得是否喜欢此歌曲
			if (songService.isLike(new Like(user.getUid(), song.getSongid()))) {
				map.put("islike", true);
			}
		} else {
			map.put("status", false);
			map.put("msg", "第一次来听歌就点上一曲的你真是够了!");
		}
		return map;
	}

	/**
	 * 根据用户id 获取上传过的所有歌曲
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/myfile")
	@ResponseBody
	public Map<String, Object> getMyfile(HttpServletRequest request,
			HttpSession session) {

		Map<String, Object> map = new HashMap<String, Object>();
		// 判断用户是否登陆，若没登陆则直接返回随机
		User user = (User) session.getAttribute("user");
		if (user == null) {
			map.put("status", false);
			map.put("msg", "请登录");
			return map;
		}
		List<Song> songs = songService.getMyfile(user.getUid());
		if (songs != null && songs.size() > 0) {
			map.put("status", true);
			map.put("songs", songs);
		} else {
			map.put("status", false);
			map.put("msg", "你还没传过歌！");
		}
		return map;
	}

	/**
	 * 添加喜欢
	 * 
	 * @param songid
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/like/{songid}")
	@ResponseBody
	public Map<String, Object> likeSong(@PathVariable int songid,
			HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			map.put("status", false);
			map.put("msg", "暂时不开放游客，请先登录");
			return map;
		}
		int i = songService.addLike(new Like(user.getUid(), songid));
		if (i != 0) {
			map.put("status", true);
			map.put("count", i);
		} else {
			map.put("status", false);
			map.put("msg", "网络繁忙请稍后再试");
		}
		return map;
	}

	@RequestMapping("/dislike/{songid}")
	@ResponseBody
	public Map<String, Object> dislikeSong(@PathVariable int songid,
			HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			map.put("status", false);
			map.put("msg", "请先登录");
			return map;
		}
		int i = songService.addDisLike(new DisLike(user.getUid(), songid));
		if (i != 0) {
			map.put("status", true);
			map.put("count", i);
		} else {
			map.put("status", false);
			map.put("msg", "网络繁忙请稍后再试");
		}
		return map;
	}

	/**
	 * 保存播放记录
	 * 
	 * @param songid
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/history/{songid}")
	@ResponseBody
	public Map<String, Object> SongHistory(@PathVariable int songid,
			HttpServletRequest request, HttpSession session) {
		System.out.println(songid);
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			map.put("status", false);
			map.put("msg", "请登录");
			return map;
		}
		if (songService.saveHistory(new HistorySong(user.getUid(), songid,
				System.currentTimeMillis()))) {
			map.put("status", true);
		} else {
			map.put("status", false);
			map.put("msg", "网络繁忙请稍后再试");
		}
		return map;
	}
}
