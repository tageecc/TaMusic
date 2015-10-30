package com.bigtage.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bigtage.bean.DisLike;
import com.bigtage.bean.HistorySong;
import com.bigtage.bean.Like;
import com.bigtage.bean.Song;
import com.bigtage.bean.User;
import com.bigtage.dao.SongDAO;

@Service
public class SongService {
	@Resource
	private SongDAO sDao;

	public boolean save(Song song) {
		if (sDao.eixt(song)) {
			return true;
		}
		return sDao.save(song);
	}

	public Song playSong(User u, String panel) {
		List<Song> songs;
		if ("srpd".equals(panel)) {// 私人频道
			songs = sDao.getSongSrpd(u.getUid());
		} else if ("hxpd".equals(panel)) {// 红心频道
			songs = sDao.getSongHxpd(u.getUid());
		} else if ("rmpd".equals(panel)) {// 热门频道
			songs = sDao.getSongRmpd();
		} else {// 随便听听
			songs = sDao.getSongs(u);
		}
		if (songs != null && songs.size() > 0) {
			// 随机播放
			Song song = songs.get((int) (Math.random() * (songs.size())));
			return song;
		}
		return null;

	}

	/**
	 * 根据id获取歌曲
	 * 
	 * @param id
	 * @return
	 */
	public Song getSongById(int id) {
		List<Song> songs = sDao.getSongById(id);
		if (songs.size() > 0) {
			return songs.get(0);
		}
		return null;
	}

	/**
	 * 上一首歌， 当前用户状态为已登录
	 * 
	 * @param uid
	 * @return
	 */
	public Song getPrevious(int uid) {
		Song song = sDao.getPrevious(uid);
		return song;
	}

	/**
	 * 根据用户id获取上传过的所有歌曲
	 * 
	 * @param uid
	 * @return
	 */
	public List<Song> getMyfile(int uid) {

		return sDao.getSongByUid(uid);
	}

	/**
	 * 根据songid更新src
	 * 
	 * @param songid
	 * @param string
	 * @return
	 */
	public boolean updateLrc(int songid, String string) {
		return sDao.updateLrc(songid, string);
	}

	/**
	 * 添加喜欢歌曲
	 * 
	 * @param like
	 * @return 1：成功添加，-1：成功取消，0：数据库失败
	 */
	public int addLike(Like like) {
		// 先查找是否存在
		if (sDao.isLike(like)) {
			if (sDao.cancleLike(like)) {
				return -1;
			}
		} else {
			if (sDao.addLike(like)) {
				return 1;
			}
		}
		return 0;
	}

	public boolean isLike(Like like) {
		return sDao.isLike(like);
	}

	/**
	 * 添加不喜欢歌曲
	 * 
	 * @param like
	 * @return 1：成功添加，-1：成功取消，0：数据库失败
	 */
	public int addDisLike(DisLike dislike) {
		if (sDao.isDisLike(dislike)) {
			if (sDao.cancleDisLike(dislike)) {
				return -1;
			}
		} else {
			if (sDao.addDisLike(dislike)) {
				return 1;
			}
		}
		return 0;
	}

	public boolean saveHistory(HistorySong historySong) {
		// 为播放歌曲的播放数+1
		sDao.increaseSong(historySong.getSongid());
		return sDao.saveHistory(historySong);
	}

}
