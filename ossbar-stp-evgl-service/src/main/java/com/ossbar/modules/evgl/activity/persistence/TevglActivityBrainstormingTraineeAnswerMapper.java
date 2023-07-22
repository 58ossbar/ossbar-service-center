package com.ossbar.modules.evgl.activity.persistence;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.ossbar.modules.evgl.activity.domain.TevglActivityBrainstormingTraineeAnswer;
import com.ossbar.core.baseclass.persistence.BaseSqlMapper;

/**
 * <p> Title: 活动之头脑风暴---学员作答信息表</p>
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2019 </p>
 * <p> Company:ossbar.co.,ltd </p>
 *
 * @author zhuq
 * @version 1.0
 */

@Mapper
public interface TevglActivityBrainstormingTraineeAnswerMapper extends BaseSqlMapper<TevglActivityBrainstormingTraineeAnswer> {
	
	/**
	 * 根据条件查询记录，返回List<Map>
	 * @param map
	 * @return
	 * @apiNote 查询返回了下字段：anId主键，ctId课堂主键，activityId活动主键，traineeId学员主键，content回答内容，createTime回答时间，
	 * traineeName学员名称（昵称），traineePic学员证件照（头像），traineeSex学员性别
	 */
	List<Map<String, Object>> selectListMapByMap(Map<String, Object> map);
	
	/**
	 * 查看内容
	 * @param anId
	 * @return
	 * @apiNote 查询返回了如下字段：anId主键，ctId课堂主键，activityId活动主键，content作答内容，createTime提交时间，activityTitle活动标题，activityContent活动内容
	 */
	Map<String, Object> selectObjectMapById(Object anId);
	
	/**
	 * 根据条件仅查询主键id
	 * @param params
	 * @return
	 */
	List<String> selectAnIdListByMap(Map<String, Object> params);
}