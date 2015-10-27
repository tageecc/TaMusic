package com.bigtage.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;

public class MP3Info {

	private static Logger logger = Logger.getLogger(MP3Info.class);

	private MP3File mp3file = null;// jaudiotagger获取封面图片的类
	private String charset = "GBK";// 解析MP3信息时用的字符编码
	private byte[] buf;// MP3的标签信息的byte数组

	public MP3Info(File file) throws Exception {
		this.mp3file = new MP3File(file);

		buf = new byte[128];// 初始化标签信息的byte数组

		RandomAccessFile raf = new RandomAccessFile(file, "r");// 随机读写方式打开MP3文件
		raf.seek(raf.length() - 128);// 移动到文件MP3末尾
		raf.read(buf);// 读取标签信息

		raf.close();// 关闭文件

		if (buf.length != 128) {// 数据是否合法
			// throw new IOException("MP3标签信息数据长度不合法!");
			logger.info("MP3标签信息数据长度不合法!");
		}

		if (!"TAG".equalsIgnoreCase(new String(buf, 0, 3))) {// 信息格式是否正确
			// throw new IOException("MP3标签信息数据格式不正确!");
			logger.info("MP3标签信息数据格式不正确!");
		}
	}

	public String getName() {
		String name = null;
		try {
			name = new String(buf, 3, 30, charset).trim();
		} catch (UnsupportedEncodingException e) {
			name = new String(buf, 3, 30).trim();
		}
		if (name != null && name.length() > 0) {
			return name;
		} else {
			return "未知";
		}
	}

	public String getSinger() {
		String singer = null;
		try {
			singer = new String(buf, 33, 30, charset).trim();
		} catch (UnsupportedEncodingException e) {
			singer = new String(buf, 33, 30).trim();
		}
		if (singer != null && singer.length() > 0) {
			return singer;
		} else {
			return "未知";
		}
	}

	public String getImg(String realPath, String md5) {
		boolean flag = false;

		try {
			AbstractID3v2Tag tag = mp3file.getID3v2Tag();
			AbstractID3v2Frame frame = (AbstractID3v2Frame) tag
					.getFrame("APIC");
			FrameBodyAPIC body = (FrameBodyAPIC) frame.getBody();

			byte[] imageData = body.getImageData();
			FileOutputStream fos = new FileOutputStream(realPath + "/" + md5
					+ ".jpg");
			fos.write(imageData);
			fos.close();
			flag = true;
		} catch (Exception e) {
			logger.info("mp3文件图片解析失败!");
		}

		return flag ? "upload/" + md5 + ".jpg" : "";
	}
}
