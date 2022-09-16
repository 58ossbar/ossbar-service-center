package com.ossbar.modules.evgl.web.controller;

import com.ossbar.core.baseclass.domain.R;
import com.ossbar.modules.evgl.common.EvglGlobal;
import com.ossbar.modules.evgl.tch.api.TevglTchTeacherService;
import com.ossbar.modules.evgl.tch.domain.TevglTchTeacher;
import com.ossbar.modules.evgl.trainee.api.TevglTraineeInfoService;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.modules.evgl.trainee.vo.TraineeInfoVO;
import com.ossbar.platform.core.common.utils.UploadFileUtils;
import com.ossbar.utils.constants.Constant;
import com.ossbar.utils.tool.BeanUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import com.ossbar.utils.tool.TicketDesUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2018 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 * @date 2018年7月3日 下午2:39:30
 */
@RestController
@RequestMapping("login-api")
public class ThirdLoginController {

    @Reference(version = "1.0.0")
    private TevglTraineeInfoService tevglTraineeInfoService;
    @Reference(version = "1.0.0")
    private TevglTchTeacherService tevglTchTeacherService;

    @Autowired
    private UploadFileUtils uploadFileUtils;

    @RequestMapping("login")
    public R login(HttpServletRequest request, HttpServletResponse response, String inputAccountLogin, String inputPasswordLogin, String inputYzcodeLogin) {
        TevglTraineeInfo user = tevglTraineeInfoService.selectByMobile(inputAccountLogin);
        if (user == null) {
            return R.error(202, "用户名或密码错误!");
        }
        if (StrUtils.isNotEmpty(inputYzcodeLogin)) {
            HttpSession session = request.getSession();
            Object yzCode = session.getAttribute("loginMobile_"+inputAccountLogin);
            session.removeAttribute("loginMobile_"+inputAccountLogin);
            // 如果验证码正确
            if(inputYzcodeLogin == null || yzCode == null || !inputYzcodeLogin.equals(yzCode)) {
                return R.error(201, "验证码错误!");
            }
        } else if (StrUtils.isNotEmpty(inputPasswordLogin)) {
            if (StrUtils.isEmpty(user.getUserPasswd()) || !user.getUserPasswd().equals(TicketDesUtil.encryptWithMd5(inputPasswordLogin, user.getUserYan()))){
                return R.error(202, "用户名或密码错误!");
            }
        }
        String token = initLoginData(request, response, user);
        TraineeInfoVO vo = new TraineeInfoVO();
        BeanUtils.copyProperties(vo, user);
        vo.setToken(token);
        vo.setTraineeHead(uploadFileUtils.stitchingPath(vo.getTraineeHead(), "2"));
        handleSomeData(vo, user);
        return R.ok().put(Constant.R_DATA, vo);
    }

    @RequestMapping("user")
    //@CheckSession
    public R user(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object traineeInfo = session.getAttribute(EvglGlobal.LOGIN_SESSION_KEY);
        if (traineeInfo == null) {
            traineeInfo = getTraineeInfoByToken(request);
            if (traineeInfo == null) {
                return R.error("无法获取登录信息");
            }
        }
        TevglTraineeInfo info = (TevglTraineeInfo)traineeInfo;
        String isBind = StrUtils.toString(session.getAttribute(EvglGlobal.LOGIN_SESSION_ISBIND));
        TraineeInfoVO vo = new TraineeInfoVO();
        BeanUtils.copyProperties(vo, info);
        handleSomeData(vo, info);
        vo.setIsBind(isBind);
        return R.ok().put(Constant.R_DATA, vo);
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("loginout")
    public R loginOut(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(EvglGlobal.LOGIN_SESSION_TOKEN);
        session.removeAttribute(EvglGlobal.LOGIN_SESSION_KEY);
        session.removeAttribute(EvglGlobal.LOGIN_SESSION_ISLOGIN);
        session.removeAttribute(EvglGlobal.LOGIN_SESSION_ISBIND);
        session.invalidate();
        Cookie imgCodeCookie = new Cookie(EvglGlobal.TOKEN_KEY_NAME, "");
        imgCodeCookie.setMaxAge(0);
        imgCodeCookie.setPath("/");
        response.addCookie(imgCodeCookie);
        return R.ok();
    }

    private void handleSomeData(TraineeInfoVO vo, TevglTraineeInfo user) {
        // 判断身份
        TevglTchTeacher tevglTchTeacher = tevglTchTeacherService.selectObjectByTraineeId(user.getTraineeId());
        System.out.println("tevglTchTeacher" + tevglTchTeacher);
        vo.setIfTeacher((tevglTchTeacher == null || !"Y".equals(tevglTchTeacher.getState())) ? false : true);
        // 处理头像
        if (tevglTchTeacher != null) {
            String pic = StrUtils.isEmpty(user.getTraineePic()) ? user.getTraineeHead() : uploadFileUtils.stitchingPath(user.getTraineePic(), "2");
            if ("4".equals(user.getTraineeType()) && tevglTchTeacher != null && "Y".equals(tevglTchTeacher.getState())) {
                if (StrUtils.isNotEmpty(tevglTchTeacher.getTeacherPic())) {
                    pic = uploadFileUtils.stitchingPath(tevglTchTeacher.getTeacherPic(), "7");
                }
            }
            vo.setTraineeHead(pic);
        }
    }

    /**
     *
     * @param request
     * @return
     */
    private Object getTraineeInfoByToken(HttpServletRequest request) {
        String token = getMyToken(request);
        if (StrUtils.isNotEmpty(token)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("token", token);
            List<TevglTraineeInfo> list = tevglTraineeInfoService.selectListByMap(map);
            if (list == null || list.isEmpty()) {
                return null;
            }
            return list.get(0);
        }
        return null;
    }


    /**
     * 获取token
     * @return
     */
    private String getMyToken(HttpServletRequest request) {
        // 获取token
        String token = request.getHeader(EvglGlobal.TOKEN_KEY_NAME);
        // 如果请求头中没有token,从cookie中获取一下
        if (StrUtils.isEmpty(token)) {
            Cookie[] cookies = request.getCookies();
            if(cookies != null) {
                for (Cookie cookie : cookies) {
                    switch(cookie.getName()){
                        case EvglGlobal.TOKEN_KEY_NAME:
                            token = cookie.getValue();
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return token;
    }

    /**
     * 组织登陆信息
     * @param request
     * @param response
     * @param tevglTraineeInfo
     * @return
     */
    private String initLoginData(HttpServletRequest request, HttpServletResponse response, TevglTraineeInfo tevglTraineeInfo) {
        HttpSession session = request.getSession();
        // 签发免登陆token并保存到数据
        String token = Identities.uuid();
        tevglTraineeInfo.setToken(token);
        tevglTraineeInfoService.update(tevglTraineeInfo);
        Cookie imgCodeCookie = new Cookie(EvglGlobal.TOKEN_KEY_NAME, token);
        imgCodeCookie.setMaxAge(3600*24*7);
        imgCodeCookie.setPath("/");
        response.addCookie(imgCodeCookie);
        session.setAttribute(EvglGlobal.LOGIN_SESSION_TOKEN, token);
        session.setAttribute(EvglGlobal.LOGIN_SESSION_KEY, tevglTraineeInfo);
        session.setAttribute(EvglGlobal.LOGIN_SESSION_ISLOGIN, "Y");
        if(StrUtils.isNotEmpty(tevglTraineeInfo.getMobile())) {
            session.setAttribute(EvglGlobal.LOGIN_SESSION_ISBIND, "Y");
        }
        return token;
    }


}
