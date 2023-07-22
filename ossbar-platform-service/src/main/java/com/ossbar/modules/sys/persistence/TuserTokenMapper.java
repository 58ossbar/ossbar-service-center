package com.ossbar.modules.sys.persistence;

import org.apache.ibatis.annotations.Mapper;

import com.ossbar.core.baseclass.persistence.BaseSqlMapper;
import com.ossbar.modules.sys.domain.TuserToken;
/**
 * Title: 系统用户TokenDescription: Copyright: Copyright (c) 2017
 * Company:ossbar.co.,ltd
 * 
 * @author ossbar.co.,ltd
 * @version 1.0
 */
@Mapper
public interface TuserTokenMapper extends BaseSqlMapper<TuserToken> {
    
    TuserToken selectObjectByUserId(String userId);

    TuserToken selectByToken(String token);
	
}
