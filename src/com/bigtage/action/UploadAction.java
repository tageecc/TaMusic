package com.bigtage.action;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bigtage.bean.Song;
import com.bigtage.bean.User;
import com.bigtage.service.SongService;
import com.bigtage.util.Common;
import com.bigtage.util.MP3Info;

@Controller
@RequestMapping("/file")
public class UploadAction {

	@Resource
	private SongService songService;

	/**
	 * 上传歌曲
	 * 
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload")
	@ResponseBody
	public Map<String, Object> upload(HttpServletRequest request,
			HttpSession session) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 判断用户是否登陆
		User user = (User) session.getAttribute("user");
		if (user == null) {
			map.put("status", false);
			map.put("msg", "请先登录！");
			return map;
		}

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> fileNames = multipartRequest.getFileNames();
		while (fileNames.hasNext()) {
			MultipartFile multipartFile = multipartRequest.getFile(fileNames
					.next());
			// upload路径
			String realPath = request.getSession().getServletContext()
					.getRealPath("/upload");
			// 文件名
			String filename = multipartFile.getOriginalFilename();
			// 获取文件的后缀
			String suffix = filename.substring(filename.lastIndexOf("."));
			// 文件MD5
			String md5 = Common.getMD5(multipartFile.getBytes());
			// 保存的路径,文件名保存为MD5值
			String filePath = realPath + "/" + md5 + suffix;
			// 判断文件是否存在。若存在则急速秒传
			File file = Common.fileExists(filePath);
			if (file != null) {
				map.put("jsmc", "极速秒传！");
			} else {
				file = Common.saveFileAndReturn(filePath,
						multipartFile.getBytes());
			}
			if (file != null) {
				// 如果文件类型为mp3，则分析其属性
				if (".mp3".equals(suffix)) {
					MP3Info info = new MP3Info(file);
					Song song = new Song(info.getName(), info.getSinger(),
							"upload/" + md5 + suffix,
							info.getImg(realPath, md5), user.getUid(),
							System.currentTimeMillis(), 0);
					if (songService.save(song)) {
						map.put("status", true);
						map.put("msg", "上传成功！");
					}

				}
			} else {
				map.put("status", false);
				map.put("msg", "文件保存失败");
			}
		}
		return map;
	}
}
