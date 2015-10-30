package com.bigtage.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dislike_info")
public class DisLike {

	private int dislikeid;
	private int uid;
	private int songid;

	public DisLike() {
		super();
	}

	public DisLike(int uid, int songid) {
		super();
		this.uid = uid;
		this.songid = songid;
	}

	@Id
	@GeneratedValue
	public int getDislikeid() {
		return dislikeid;
	}

	public void setDislikeid(int dislikeid) {
		this.dislikeid = dislikeid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getSongid() {
		return songid;
	}

	public void setSongid(int songid) {
		this.songid = songid;
	}

}
