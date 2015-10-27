package com.bigtage.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bigtage.bean.HistorySong;
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

	public Song randomPlay(User u) {
		List<Song> songs = sDao.getSongs();
		int a = (int) (Math.random() * (songs.size() - 1));
		System.out.println("a:" + a);
		Song song = songs.get(a);
		// 如果用户是登陆状态，则保存听歌记录
		if (u != null) {
			sDao.saveHistory(new HistorySong(u.getUid(), song.getSongid(),
					System.currentTimeMillis()));
		}
		// 为播放歌曲的播放数+1
		sDao.increaseSong(song.getSongid());
		return song;
	}

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
		// 保存听歌记录,为播放歌曲的播放数+1
		sDao.saveHistory(new HistorySong(uid, song.getSongid(), System
				.currentTimeMillis()));
		sDao.increaseSong(song.getSongid());
		return song;
	}
}
