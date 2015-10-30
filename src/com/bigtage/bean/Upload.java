package com.bigtage.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "upload")
public class Upload {

	private int uploadid;
	private int uid;
	private int songid;
	private long time;

	public Upload() {
		super();
	}

	public Upload(int uid, int songid, long time) {
		super();
		this.uid = uid;
		this.songid = songid;
		this.time = time;
	}

	@Id
	@GeneratedValue
	public int getUploadid() {
		return uploadid;
	}

	public void setUploadid(int uploadid) {
		this.uploadid = uploadid;
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

	@Column(name = "time")
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
