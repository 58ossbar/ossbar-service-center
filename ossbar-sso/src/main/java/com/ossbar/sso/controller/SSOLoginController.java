package com.ossbar.sso.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.utils.tool.StrUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

/**
 * 登陆认证控制类
 * 
 * @author zhuq
 *
 */
@RestController
public class SSOLoginController {
	@Autowired
	private Producer producer;
	@Autowired
	private AuthenticationManager myAuthenticationManager;
	@Autowired
	private ConsumerTokenServices tokenServices;

	@RequestMapping("/doLogin")
	public R login(HttpServletRequest request, String username, String password, String captcha, HttpSession session) {
		Object captchaSession = session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if (StrUtils.isEmpty(captcha) || !captcha.equals(captchaSession)) {
			return R.error("验证码错误！");
		}
		try {
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,
					password);
			Authentication authentication = myAuthenticationManager.authenticate(authRequest); // 调用loadUserByUsername
			SecurityContextHolder.getContext().setAuthentication(authentication);
			session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
			// 获取回调地址
			Object savedRequestObject = session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
			String redirectUrl = request.getContextPath()
					+ (request.getContextPath().endsWith("/") ? "index" : "/index");
			if (savedRequestObject != null) {
				redirectUrl = ((SavedRequest) savedRequestObject).getRedirectUrl();
				session.removeAttribute("SPRING_SECURITY_SAVED_REQUEST");
			}
			return R.ok(redirectUrl);
		} catch (AuthenticationException ex) {
			return R.error("用户名或密码错误！");
		}
	}

	@RequestMapping("captcha.jpg")
	public void captcha(HttpSession session, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");
		// 生成文字验证码
		String text = producer.createText();
		// 生成图片验证码
		BufferedImage image = producer.createImage(text);
		// 保存到shiro session
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, text);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}

	/**
	 * 登陆
	 * 
	 * @param principal
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	/**
	 * 首页
	 * 
	 * @param principal
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index() {
		return new ModelAndView("index");
	}

	/**
	 * 退出
	 * 
	 * @param principal
	 * @return
	 */
	@RequestMapping("/logout")
	public ModelAndView logout(String accessToken) {
		tokenServices.revokeToken(accessToken);
		return new ModelAndView("login");
	}

	/**
	 * 退出
	 * 
	 * @param principal
	 * @return
	 */
	@RequestMapping("/logoutRest")
	public R logoutRest(String accessToken) {
		tokenServices.revokeToken(accessToken);
		return R.ok();
	}
}
