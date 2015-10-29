package com.bigtage.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "like_info")
public class Like {

	private int likeid;
	private int uid;
	private int songid;

	public Like() {
		super();
	}

	public Like(int uid, int songid) {
		super();
		this.uid = uid;
		this.songid = songid;
	}

	@Id
	@GeneratedValue
	public int getLikeid() {
		return likeid;
	}

	public void setLikeid(int likeid) {
		this.likeid = likeid;
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
