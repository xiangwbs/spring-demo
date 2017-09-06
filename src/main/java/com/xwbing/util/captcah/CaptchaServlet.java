package com.xwbing.util.captcah;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 说明: 创建验证码的servlet <br/>
 * 创建日期: 2016年8月29日 上午10:59:27 <br/>
 * 作者: xwb
 */
public class CaptchaServlet extends HttpServlet {
	private static final long serialVersionUID = -124247581620199710L;
	/*
	 * 存放session中的值
	 */
	public static final String KEY_CAPTCHA = "SE_KEY_MM_CODE";
	/**
	 * 在web.xml里配置CaptchaServlet
	 */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res){
		// 设置相应类型,告诉浏览器输出的内容为图片
		res.setContentType("image/jpeg");
		// 禁止图像缓存。
		res.setHeader("Pragma", "No-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setDateHeader("Expire", 0);
		try {
			HttpSession session = req.getSession();
			CaptchaUtil tool = new CaptchaUtil();
			StringBuffer code = new StringBuffer();
			BufferedImage image = tool.genRandomCodeImage(code);
			session.removeAttribute(KEY_CAPTCHA);
			session.setAttribute(KEY_CAPTCHA, code.toString());
			// 将内存中的图片通过流形式输出到客户端
			OutputStream out=res.getOutputStream();
			ImageIO.write(image, "JPEG", out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
