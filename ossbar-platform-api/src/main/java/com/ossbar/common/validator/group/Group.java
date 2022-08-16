package com.ossbar.common.validator.group;

import javax.validation.GroupSequence;

/**
 * Title:定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验 Group
 * Copyright: Copyright (c) 2017
 * Company:creatorblue.co.,ltd
 * 
 * @author creatorblue.co.,ltd
 * @version 1.0
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}
