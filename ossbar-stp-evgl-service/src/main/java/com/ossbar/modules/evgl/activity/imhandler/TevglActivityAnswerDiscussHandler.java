package com.ossbar.modules.evgl.activity.imhandler;

import com.ossbar.modules.common.GlobalEmpiricalValueGetType;
import com.ossbar.modules.evgl.activity.persistence.TevglActivityAnswerDiscussMapper;
import com.ossbar.modules.evgl.medu.me.persistence.TmeduMeLikeMapper;
import com.ossbar.modules.evgl.pkg.persistence.TevglPkgActivityRelationMapper;
import com.ossbar.modules.evgl.tch.persistence.TevglTchClassroomMapper;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeEmpiricalValueLog;
import com.ossbar.modules.evgl.trainee.domain.TevglTraineeInfo;
import com.ossbar.modules.evgl.trainee.persistence.TevglTraineeEmpiricalValueLogMapper;
import com.ossbar.modules.evgl.trainee.persistence.TevglTraineeInfoMapper;
import com.ossbar.utils.redis.RedisUtils;
import com.ossbar.utils.tool.Identities;
import com.ossbar.utils.tool.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@Service
public class TevglActivityAnswerDiscussHandler {

    private static final Logger log = LoggerFactory.getLogger(TevglActivityAnswerDiscussHandler.class);

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private TevglActivityAnswerDiscussMapper tevglActivityAnswerDiscussMapper;
    @Autowired
    private TevglTraineeEmpiricalValueLogMapper tevglTraineeEmpiricalValueLogMapper;
    @Autowired
    private TevglTraineeInfoMapper tevglTraineeInfoMapper;
    @Autowired
    private TmeduMeLikeMapper tmeduMeLikeMapper;
    @Autowired
    private TevglTchClassroomMapper tevglTchClassroomMapper;
    @Autowired
    private TevglPkgActivityRelationMapper tevglPkgActivityRelationMapper;


    /**
     * 实际保存
     * @param msg
     * @param channelContext
     * @param convertMsg 消息基类 {"fromuser": "", "touser": "", "toparty": "", "totag": "", "msgtype": ""} 等
     * @param mediaId 附件主键
     */


    /**
     * 处理经验值
     *
     * @param activityId
     * @param loginUserId
     */
    private void handleEmpiricalValue(String activityId, String loginUserId, Integer upperLimitValue, String msgId) {
        // 查询条件
        Map<String, Object> params = new HashMap<>();
        // 查询这个人通过答疑讨论获取的经验值
        params.put("traineeId", loginUserId);
        params.put("activityId", activityId);
        params.put("state", "Y");
        Integer currentValue = tevglTraineeEmpiricalValueLogMapper.sumEmpiricalValueByMap(params);
        // 已获得的经验值是否超过限制值
        log.debug("当前通过此[答疑讨论]获得的经验值:" + currentValue);
        log.debug("此[答疑讨论]的上限经验值:" + upperLimitValue);
        // 如果没有超过,则继续增加经验
        if (new BigDecimal(currentValue).compareTo(new BigDecimal(upperLimitValue)) == -1) {
            saveEmpiricalValue(activityId, loginUserId, msgId);
        }
    }

    /**
     * 学员通过参与答疑讨论获得经验值
     *
     * @param activityId  活动主键（亦是群聊主键）
     * @param loginUserId 当前登录用户
     * @param msgId       消息主键
     */
    private void saveEmpiricalValue(String activityId, String loginUserId, String msgId) {
        Integer value = 1; // 本次活动经验值
        TevglTraineeEmpiricalValueLog empiricalValueLog = new TevglTraineeEmpiricalValueLog();
        empiricalValueLog.setEvId(Identities.uuid());
        empiricalValueLog.setType(GlobalEmpiricalValueGetType.ACTIVITY_3_ANSWER_DISCUSS); // 获取方式
        empiricalValueLog.setTraineeId(loginUserId);
        empiricalValueLog.setCtId(null);
        empiricalValueLog.setActivityId(activityId);
        empiricalValueLog.setEmpiricalValue(value);
        empiricalValueLog.setState("Y");
        empiricalValueLog.setParams1(msgId); // 请存值消息主键
        tevglTraineeEmpiricalValueLogMapper.insert(empiricalValueLog);
        // 更新总经验值
        TevglTraineeInfo traineeInfo = new TevglTraineeInfo();
        traineeInfo.setTraineeId(loginUserId);
        traineeInfo.setEmpiricalValue(value);
        tevglTraineeInfoMapper.plusNum(traineeInfo);
    }

    private String handle(Object userImg) {
        if (StrUtils.isNull(userImg)) {
            return null;
        }
        String userimg = userImg.toString();
        if (userimg.indexOf("uploads") != -1) {
            String first = userimg.substring(0, 1);
            if (!"/".equals(first)) {
                userimg = "/" + userimg;
            }
        }
        return userimg;
    }
}
