package com.bigtage.bean;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_detail")
public class UserDetail {

	private int uid;
	private String headimg;
	private String introduction;
	private int sex;
	private Timestamp birthday;
	private String province;
	private String city;

	public UserDetail() {
		super();
	}

	public UserDetail(int uid, String headimg, String introduction, int sex,
			Timestamp birthday, String province, String city) {
		super();
		this.uid = uid;
		this.headimg = headimg;
		this.introduction = introduction;
		this.sex = sex;
		this.birthday = birthday;
		this.province = province;
		this.city = city;
	}

	@Id
	@GeneratedValue
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public Timestamp getBirthday() {
		return birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "UserDetail [uid=" + uid + ", headimg=" + headimg
				+ ", introduction=" + introduction + ", sex=" + sex
				+ ", birthday=" + birthday + ", province=" + province
				+ ", city=" + city + "]";
	}

}
