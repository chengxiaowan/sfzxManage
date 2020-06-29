package com.yocto.controller.business;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yocto.controller.base.BaseController;
import com.yocto.entity.system.User;
import com.yocto.service.business.attachInfoManage.IAttachInfoService;
import com.yocto.util.AppUtil;
import com.yocto.util.Const;
import com.yocto.util.FileDownload;
import com.yocto.util.FileUpload;
import com.yocto.util.Jurisdiction;
import com.yocto.util.PageData;
import com.yocto.util.TextUtil;

@Controller
@RequestMapping(value = "/attach")
public class AttachController extends BaseController {

	@Resource(name = "attachService")
	private IAttachInfoService attachService;

	/**
	 * 附件下载
	 * 
	 * @return
	 */
	@RequestMapping(value = "/download")
	@ResponseBody
	public void download(final HttpServletResponse response) {
		try {
			PageData pd = this.getPageData();
			String id = pd.getString("id");
			if (TextUtil.isNotNull(id)) {
				pd = attachService.findById(pd);
				if (null != pd && !pd.isEmpty()) {
					String filePath = pd.getString("realPath");
					String fileName = pd.getString("originalFilename");
					System.out.println("1:" + fileName);
					FileDownload.fileDownload(response, getRequest(), filePath, fileName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 附件上传
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload")
	@ResponseBody
	public Object upload(@RequestParam(required = false) MultipartFile file) throws Exception {
		PageData map = null;
		if (null != file && !file.isEmpty()) {
			// 保存文件
			map = FileUpload.upload(file);
			// 获取上传人
			User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
			if (null != user) {
				map.put("uploader", user.getNAME());
			}
		}
		return AppUtil.returnObject(new PageData(), map);
	}
}
