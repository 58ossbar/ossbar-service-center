package com.ossbar.modules.evgl.trainee.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ossbar.modules.evgl.trainee.vo.TevglTraineeScoreVo;

@Mapper
public interface TevglTraineeScoreMapper {

	/**
	 * 根据条件查询学生成绩
	 * @param map
	 * @return
	 */
	List<TevglTraineeScoreVo> findTraineeScoreList(Map<String, Object> map);
	
	/**
	 * 查询部分学生信息
	 * @param traineeId
	 * @return
	 */
	Map<String, Object> findStudentInfo(String traineeId);
	
}
