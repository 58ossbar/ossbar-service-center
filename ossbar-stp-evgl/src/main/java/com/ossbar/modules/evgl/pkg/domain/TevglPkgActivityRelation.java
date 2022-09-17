package com.ossbar.modules.evgl.pkg.domain;

import com.alibaba.fastjson.JSONObject;
import com.ossbar.core.baseclass.domain.BaseDomain;

/**
 * <p> Title: </p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:creatorblue.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

public class TevglPkgActivityRelation extends BaseDomain<TevglPkgActivityRelation> {
	private static final long serialVersionUID = 1L;
	
	//alias
	public static final String TABLE_ALIAS = "TevglPkgActivityRelation";
	public static final String ALIAS_PA_ID = "主键ID";
	public static final String ALIAS_PKG_ID = "教学包主键ID";
	public static final String ALIAS_ACTIVITY_ID = "活动ID";
	public static final String ALIAS_ACTIVITY_TYPE = "来源字典1投票问卷2头脑风暴3答疑讨论4测试活动5作业/小组任务6课堂表现7轻直播/讨论8签到";
	public static final String ALIAS_ACTIVITY_STATE = "实际活动状态(0未开始1进行中2已结束)";
	public static final String ALIAS_ACTIVITY_BEGIN_TIME = "实际活动开始时间";
	public static final String ALIAS_ACTIVITY_END_TIME = "实际活动结束时间";
	public static final String ALIAS_GROUP_ID = "关联群id，用于解决一个答疑讨论活动，能被多个课堂使用的情况";
	

    /**
     * 主键ID       db_column: pa_id 
     */	
 	//@NotNull(msg="主键ID不能为空")
 	//@MaxLength(value=32, msg="字段[主键ID]超出最大长度[32]")
	private String paId;
    /**
     * 教学包主键ID       db_column: pkg_id 
     */	
 	//@NotNull(msg="教学包主键ID不能为空")
 	//@MaxLength(value=32, msg="字段[教学包主键ID]超出最大长度[32]")
	private String pkgId;
    /**
     * 活动ID       db_column: activity_id 
     */	
 	//@NotNull(msg="活动ID不能为空")
 	//@MaxLength(value=32, msg="字段[活动ID]超出最大长度[32]")
	private String activityId;
    /**
     * 来源字典1投票问卷2头脑风暴3答疑讨论4测试活动5作业/小组任务6课堂表现7轻直播/讨论8签到       db_column: activity_type 
     */	
 	//@NotNull(msg="来源字典1投票问卷2头脑风暴3答疑讨论4测试活动5作业/小组任务6课堂表现7轻直播/讨论8签到不能为空")
 	//@MaxLength(value=32, msg="字段[来源字典1投票问卷2头脑风暴3答疑讨论4测试活动5作业/小组任务6课堂表现7轻直播/讨论8签到]超出最大长度[32]")
	private String activityType;
    /**
     * 活动状态       db_column: activity_state 
     */	
 	//@NotNull(msg="活动状态不能为空")
 	//@MaxLength(value=2, msg="字段[活动状态]超出最大长度[2]")
	private String activityState;
 	/**
     * 活动实际开始时间       db_column: activity_begin_time 
     */	
 	////@NotNull(msg="活动实际开始时间不能为空")
 	//@MaxLength(value=20, msg="字段[活动实际开始时间]超出最大长度[20]")
	private String activityBeginTime;
    /**
     * 活动实际结束时间       db_column: activity_end_time 
     */	
 	////@NotNull(msg="活动实际结束时间不能为空")
 	//@MaxLength(value=20, msg="字段[活动实际结束时间]超出最大长度[20]")
	private String activityEndTime;
 	/**
     * 关联群id，用于解决一个答疑讨论活动，能被多个课堂使用的情况       db_column: group_id 
     */	
 	////@NotNull(msg="关联群id，用于解决一个答疑讨论活动，能被多个课堂使用的情况不能为空")
 	//@MaxLength(value=32, msg="字段[关联群id，用于解决一个答疑讨论活动，能被多个课堂使用的情况]超出最大长度[32]")
	private String groupId;
    
	//columns END

	public TevglPkgActivityRelation(){
	}

	public TevglPkgActivityRelation(
		String paId
	){
		this.paId = paId;
	}

	public void setPaId(String value) {
		this.paId = value;
	}
	public String getPaId() {
		return this.paId;
	}
	public void setPkgId(String value) {
		this.pkgId = value;
	}
	public String getPkgId() {
		return this.pkgId;
	}
	public void setActivityId(String value) {
		this.activityId = value;
	}
	public String getActivityId() {
		return this.activityId;
	}
	public void setActivityType(String value) {
		this.activityType = value;
	}
	public String getActivityType() {
		return this.activityType;
	}
	public void setActivityState(String value) {
		this.activityState = value;
	}
	public String getActivityState() {
		return this.activityState;
	}
	public String getActivityBeginTime() {
		return activityBeginTime;
	}
	public void setActivityBeginTime(String activityBeginTime) {
		this.activityBeginTime = activityBeginTime;
	}
	public String getActivityEndTime() {
		return activityEndTime;
	}
	public void setActivityEndTime(String activityEndTime) {
		this.activityEndTime = activityEndTime;
	}

	private TevglPkgInfo tevglPkgInfo;
	
	public void setTevglPkgInfo(TevglPkgInfo tevglPkgInfo){
		this.tevglPkgInfo = tevglPkgInfo;
	}
	
	public TevglPkgInfo getTevglPkgInfo() {
		return tevglPkgInfo;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}

