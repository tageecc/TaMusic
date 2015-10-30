package com.bigtage.dao;

import java.util.List;

import com.bigtage.bean.DisLike;
import com.bigtage.bean.HistorySong;
import com.bigtage.bean.Like;
import com.bigtage.bean.Song;

public interface SongDAO {

	public boolean save(Song song);

	public boolean eixt(Song song);

	public List<Song> getSongs();

	public List<Song> getSongById(int id);

	public Song getPrevious(int uid);

	public boolean saveHistory(HistorySong historySong);// 保存历史记录

	public boolean increaseSong(int songid);// 为播放歌曲的播放数+1

	public List<Song> getSongByUid(int uid);

	public boolean updateLrc(int songid, String string);

	public List<Song> getSongSrpd(int uid);

	public List<Song> getSongHxpd(int uid);

	public List<Song> getSongRmpd();

	public boolean addLike(Like like);// 添加过喜欢

	public boolean isLike(Like like);// 是否添加过喜欢

	public boolean cancleLike(Like like);// 取消喜欢

	public boolean isDisLike(DisLike dislike);// 是否添加过不喜欢

	public boolean addDisLike(DisLike dislike);// 添加不喜欢

	public boolean cancleDisLike(DisLike dislike);// 取消不喜欢

}
