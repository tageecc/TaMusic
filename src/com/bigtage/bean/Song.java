package com.bigtage.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "song")
public class Song {

	private int songid;
	private String name;// 歌曲名
	private String singer;// 歌手
	private String src;// 歌曲地址
	private String lrc;// lrc地址
	private String img;// 封面图地址
	private int uid;// 上传用户id
	private long uptime;// 上传时间
	private Integer count;// 播放次数

	public Song() {
		super();
	}

	public Song(String name, String singer, String src, String lrc, String img,
			int uid, long uptime, Integer count) {
		super();
		this.name = name;
		this.singer = singer;
		this.src = src;
		this.lrc = lrc;
		this.img = img;
		this.uid = uid;
		this.uptime = uptime;
		this.count = count;
	}

	@Id
	@GeneratedValue
	public int getSongid() {
		return songid;
	}

	public void setSongid(int songid) {
		this.songid = songid;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getLrc() {
		return lrc;
	}

	public void setLrc(String lrc) {
		this.lrc = lrc;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public long getUptime() {
		return uptime;
	}

	public void setUptime(long uptime) {
		this.uptime = uptime;
	}

	@Column(name = "count")
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Song [songid=" + songid + ", name=" + name + ", singer="
				+ singer + ", src=" + src + ", lrc=" + lrc + ", img=" + img
				+ ", uid=" + uid + ", uptime=" + uptime + ", count=" + count
				+ "]";
	}

}
