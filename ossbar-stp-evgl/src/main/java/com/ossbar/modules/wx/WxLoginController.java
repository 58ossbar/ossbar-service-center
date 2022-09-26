package com.ossbar.modules.wx;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.medu.api.TmeduApiTokenService;
import com.ossbar.modules.evgl.medu.domain.TmeduApiToken;
import com.ossbar.modules.evgl.tch.api.TevglTchTeacherService;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.utils.tool.DateUtils;
import com.ossbar.utils.tool.HttpUtils;
import com.ossbar.utils.tool.StrUtils;
import io.swagger.annotations.Api;
import org.apache.commons.io.IOUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weixin.popular.bean.wxa.WxaDUserInfo;
import weixin.popular.util.WxaUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Title:登录、授权等 Copyright: Copyright (c) 2017
 * Company:creatorblue.co.,ltd
 * 
 * @author creatorblue.co.,ltd
 * @version 1.0
 */
@RestController
@RequestMapping("/wx/api")
@Api(value="权限验证相关授权及服务接口",tags= {"权限验证相关授权及服务接口"})
public class WxLoginController {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	// 小程序
	@Value("${com.creatorblue.appid:}")
	private String custappid;
	@Value("${com.creatorblue.secret:}")
	private String custsecret;
	// 短信
	@Value("${com.creatorblue.accessKeyId:}")
	private String accessKeyId;
	@Value("${com.creatorblue.accessKeySecret:}")
	private String accessKeySecret;
	@Value("${com.creatorblue.signName:}")
	private String signName;
	@Value("${com.creatorblue.templateCode:}")
	private String templateCode;

	@Autowired
	private Producer producer;
	@Reference(version = "1.0.0")
	private TmeduApiTokenService tmeduApiTokenService;
	@Reference(version = "1.0.0")
	private TevglTraineeInfoService tevglTraineeInfoService;
	@Reference(version = "1.0.0")
	private TevglTchTeacherService tevglTchTeacherService;

	Random random = new Random();
	
	/**
	 * 验证码
	 * @param session
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/captcha.jpg")
	public void captcha(HttpSession session, HttpServletResponse response, String token) throws ServletException, IOException {
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		ServletOutputStream out;
		try {
			out = response.getOutputStream();
	    	if(te == null){
	    		return;
	    	}
			response.setHeader("Cache-Control", "no-store, nocache");
			response.setContentType("image/jpeg");
			// 生成文字验证码
			String text = "";
	    	String chars = "0123456789";
	    	int length = 4;
	    	for(int i = 0;i< length ;i++){
				text += chars.charAt(random.nextInt(chars.length()));
			}
			// 生成图片验证码
			BufferedImage image = producer.createImage(text);
			// 保存到session
			session.setAttribute(Constants.KAPTCHA_SESSION_KEY, text);
			out = response.getOutputStream();
			ImageIO.write(image, "jpg", out);
			IOUtils.closeQuietly(out);
			te.setImgCode(text);
			tmeduApiTokenService.updateByOpenid(te);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取会话id
	 * @return
	 */
	@PostMapping("sessionid")
	@Transactional
	public R getToken(String code, String nickName, String sex, String avatarUrl, String userType) throws Exception{
		String state = "0"; // 是否注册了手机0注册1未注册
		R r = null;
		// 调取微信服务,取得用户openid和session_key
		String url = "https://api.weixin.qq.com/sns/jscode2session?" + "appid=" + custappid + "&secret=" + custsecret
				+ "&js_code=" + code + "&grant_type=authorization_code";
		byte[] data;
    	log.info("微信服务请求url:" + url);
    	try {
			JSONObject obj = null;
			// 如果失败 则重复三次
			for (int i = 0; i < 3; i++) {
				data = HttpUtils.httpGet(url);
				log.info("微信服务请求结果:" + data);
				obj=  JSONObject.parseObject(new String(data));
				if (obj.getString("errcode") == null ){
					break;
				} else if(i == 2){ // 三次失败，则返回失败状态
					return R.error(-1, obj.toString());
				}
				Thread.sleep(300);
			}
			String openid = obj.getString("openid"); // 微信用户的唯一标识
	    	String sessionKey = obj.getString("session_key"); // 会话密钥
	    	// 取得已存在的token
	    	boolean newToken = false;
	    	TmeduApiToken token = tmeduApiTokenService.selectTokenByopenid(openid, null);
	    	if (token == null) {
	    		newToken = true;
	    		token = new TmeduApiToken();
		    	token.setUserType(userType);
	    	}
	    	TevglTraineeInfo cust = tevglTraineeInfoService.selectObjectByOpenId(openid);
	    	// 如果没有找到客户则注册客户
	    	if (cust == null) {
	    		cust = new TevglTraineeInfo();
	    		cust.setTraineeWxid(openid);
	    		cust.setNickName(nickName);
	    		cust.setTraineeHead(avatarUrl);
	    		cust.setTraineeSex(sex);
	    		cust.setTraineeType("1"); // 用户类型
	    		tevglTraineeInfoService.save(cust);
	    	} else {
	    		// 此段代码-兼容处理下昵称为【微信用户】的问题
	    		if ("微信用户".equals(cust.getNickName())) {
	    			cust.setNickName(nickName);
	    			cust.setTraineeHead(avatarUrl);
	    			tevglTraineeInfoService.update(cust);
	    		}
	    	}
	    	// 填充信息
	    	token.setUserId(cust.getTraineeId());
	    	token.setMobile(cust.getMobile());
	    	token.setOpenid(openid);
	    	token.setToken(UUID.randomUUID().toString());
	    	token.setSessionKey(sessionKey);
	    	token.setUpdateTime(DateUtils.getNowTimeStamp());
	    	token.setExpireTime(DateUtils.getNowTimeStamp()); // 当前时间
	    	if (newToken) {
	    		tmeduApiTokenService.insert(token);
	    		// TODO 上线前记得删除
	    		//doSaveTeacherInfo(cust);
	    	} else {
	    		tmeduApiTokenService.updateByOpenid(token);
	    	}
	    	Map<String,Object> m = new HashMap<String,Object>();
	    	m.put("userToken", token.getToken());
	    	m.put("expireTime", token.getExpireTime());
	    	// 客户未注册手机
	    	if (token.getMobile() == null || token.getMobile().isEmpty()) {
	    		state = "1";
	    	}
	    	m.put("type", token.getUserType());
	    	m.put("state", state);
	    	// 返回token
	    	r = R.ok(m);
		} catch (Exception e) {
			throw e;
		}
		return r;
	}
	
	/**
	 * 发送短信验证码（登录所用）
	 * @param token
	 * @param mobile
	 * @param code
	 * @return
	 */
	@PostMapping("/sendsmscode")
	public R sendSMSCode(String token, String mobile, String code) {
		if (StrUtils.isEmpty(token) || StrUtils.isEmpty(mobile)) {
			return R.error("必填参数为空");
		}
		R r = null;
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if (te == null) {
    		return R.error(401, "Invalid token");
    	}
		/*// 验证码错误
		if (!code.equalsIgnoreCase(te.getImgCode())) {
			return R.error(-2,"Verification code error");
		}*/
		// 如果是客户，查询此手机号是否已绑定，如果是则
		TevglTraineeInfo cust = tevglTraineeInfoService.selectByMobile(mobile);
		if (cust != null && StrUtils.isNotEmpty(cust.getTraineeWxid())) {
    		// 暂时注释
			//return R.error(-3, "此手机号已编写");
    	}
		// 生成五位随机码
 	    String randCode = Integer.toString(new Random().nextInt(900000) + 100000);
 	    // 需要先在阿里去上创建短信模版，再组织模版参数
		// TODO 自行实现发送验证码
 	    boolean flag = true;
	   	if (!flag) {
			return R.error(-1, "发送失败");
		}
 	    te.setSmsCode(randCode);
	   	te.setMobile(mobile);
	   	tmeduApiTokenService.updateByOpenid(te);
	   	r = R.ok();
   		return r;
	}
	
	/**
	 * 新版登录接口
	 * @param token
	 * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
	 * @param iv 加密算法的初始向量
	 * @return
	 */
	@PostMapping("/doLogin")
	@Transactional
	public R doLogin(String token, String encryptedData, String iv) {
		if (StrUtils.isEmpty(token) || StrUtils.isEmpty(encryptedData) || StrUtils.isEmpty(iv)) {
			return R.error("必传参数为空");
		}
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if (te == null) {
			return R.error(401, "Invalid token");
		}
		// 返回数据
    	//Map<String,Object> m = new HashMap<>();
		// 手机0注册1未注册
    	//String state = "1";
    	// 学员类型来源字典：1客户2系统用户3学员4教师
    	String traineeType = "1";
    	// 手机号码
    	if (StrUtils.isEmpty(te.getMobile())) {
    		// 解密数据获得手机号码
        	WxaDUserInfo wxaDUserInfo = WxaUtil.decryptUserInfo(te.getSessionKey(), encryptedData, iv);
        	if (wxaDUserInfo != null) {
        		te.setMobile(wxaDUserInfo.getPurePhoneNumber());
        	}
    	}
    	log.debug("手机号码：" + te.getMobile());
    	// 如果该手机号码在网站绑定过，则将本次绑定操作合并到网站上去
    	TevglTraineeInfo tInfo = tevglTraineeInfoService.selectByMobile(te.getMobile());
    	if (tInfo != null) {
    		// 将小程序中的收藏数据同步到网站账号上去
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("traineeId", te.getUserId());

			// 删除小程序的临时数据
			if (StrUtils.isEmpty(tInfo.getMobile())) {
				tevglTraineeInfoService.delete(te.getUserId());
			}
			te.setUserId(tInfo.getTraineeId());
			traineeType = tInfo.getTraineeType();
    	}
    	String custId = te.getUserId();
		TevglTraineeInfo cust = tevglTraineeInfoService.selectObjectById(custId);
		if (cust != null) {
			cust.setMobile(te.getMobile()); // 绑定手机号码
			cust.setTraineeWxid(te.getOpenid());
			cust.setCreateTime(DateUtils.getNowTimeStamp()); // 注册时间
			tevglTraineeInfoService.update(cust);	
		}
		// 更新
		te.setImgCode("");
    	te.setSmsCode("");
    	tmeduApiTokenService.updateByOpenid(te);
    	// 更新用户类型
    	te.setUserType(traineeType);
    	tmeduApiTokenService.update(te);

		return R.ok("登录成功");
	}
	
	/**
	 * 发送短信验证码（修改手机号码所用）
	 * @param token 用户token
	 * @param mobile 手机号
	 * @param type 发送类型，1.旧手机，2.新手机
	 * @return
	 */
	@PostMapping("/sendsmscode1")
	public R sendSMSCode1(String token, String mobile, String type) {
		R r = checkIsPass(token, mobile);
		Integer code = (Integer) r.get("code");
		if (code != 0) {
			return r;
		}
		if (StrUtils.isEmpty(type)) {
			return R.error("参数type为空");
		}
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		if (te == null) {
			return R.error(401, "Invalid token");
		}
		if ("1".equals(type) && !mobile.equals(te.getMobile())) {
    		return R.error(-1, "手机号错误，请输入正确的手机号");
		} else {
			TevglTraineeInfo traineeInfo = tevglTraineeInfoService.selectByMobile(mobile);
			if (traineeInfo != null && !traineeInfo.getTraineeId().equals(te.getUserId())) {
    			return R.error(-1, "该手机号已绑定其他微信号，请更换手机号");
			}
		}
		// 生成6位随机码
 	    String randCode = Integer.toString(new Random().nextInt(900000) + 100000);
 	    // 需要先在阿里去上创建短信模版，再组织模版参数 
 	    boolean flag = true;
	   	if (!flag) {
			return R.error(-1, "验证码发送失败");
		}
	   	te.setSmsCode(mobile + "_" + randCode);
	   	tmeduApiTokenService.updateByOpenid(te);
	   	r = R.ok();
    	return r;
	}
	
	/**
	 * 验证短信验证码（修改手机号所用）
	 * @param token
	 * @param mobile
	 * @param smscode
	 * @return
	 */
	@PostMapping("validsmscode")
	public R validSMSCode(String token,String mobile,String smscode){
		R r = checkIsPass(token, mobile);
		Integer code = (Integer) r.get("code");
		if (code != 0) {
			return r;
		}
		if (StrUtils.isEmpty(smscode)) {
			return R.error("参数smscode为空");
		}
		TmeduApiToken te = tmeduApiTokenService.selectTokenByToken(token);
		String[] str = te.getSmsCode().split("_");
		if (!mobile.equals(te.getMobile()) || !str[0].equals(mobile) || !smscode.equals(str[1])) {
    		return R.error(-1,"手机号有误或验证码错误");
    	}
		te.setSmsCode("");
    	tmeduApiTokenService.update(te);
    	return R.ok();
	}
	
	private R checkIsPass(String token, String mobile) {
		if (StrUtils.isEmpty(token)) {
			return R.error("参数token为空");
		}
		if (StrUtils.isEmpty(mobile)) {
			return R.error("参数mobile为空");
		}
		return R.ok();
	}

}
