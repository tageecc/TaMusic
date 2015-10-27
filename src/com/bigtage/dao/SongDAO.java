package com.bigtage.dao;

import java.util.List;

import com.bigtage.bean.HistorySong;
import com.bigtage.bean.Song;

public interface SongDAO {

	public boolean save(Song song);

	public Song findById(int songid);

	public boolean eixt(Song song);

	public List<Song> getSongs();

	public List<Song> getSongById(int id);

	public Song getPrevious(int uid);

	public boolean saveHistory(HistorySong historySong);// 保存历史记录

	public boolean increaseSong(int songid);//为播放歌曲的播放数+1

}
