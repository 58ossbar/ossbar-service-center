SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_api_token
-- ----------------------------
DROP TABLE IF EXISTS t_api_token;
CREATE TABLE t_api_token (
  user_id varchar(32) NOT NULL,
  token varchar(100) NOT NULL COMMENT 'token',
  expire_time varchar(20) DEFAULT NULL COMMENT '过期时间',
  update_time varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (user_id),
  UNIQUE KEY token (token)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户Token';

-- ----------------------------
-- Table structure for t_api_user
-- ----------------------------
DROP TABLE IF EXISTS t_api_user;
CREATE TABLE t_api_user (
  user_id varchar(32) NOT NULL,
  username varchar(50) NOT NULL COMMENT '用户名',
  mobile varchar(20) NOT NULL COMMENT '手机号',
  password varchar(64) DEFAULT NULL COMMENT '密码',
  create_time varchar(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (user_id),
  UNIQUE KEY username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Table structure for t_org_user
-- ----------------------------
DROP TABLE IF EXISTS t_org_user;
CREATE TABLE t_org_user (
  orguser_id varchar(32) NOT NULL,
  ORG_ID varchar(32) DEFAULT NULL COMMENT '机构ID',
  USER_ID varchar(32) DEFAULT NULL COMMENT '用户ID',
  IS_MAIN int(11) NOT NULL DEFAULT '0' COMMENT '是否主机构（0：否，1：是）',
  PRIMARY KEY (orguser_id),
  KEY FK_Reference_11 (ORG_ID),
  KEY FK_Reference_12 (USER_ID),
  CONSTRAINT t_org_user_ibfk_1 FOREIGN KEY (ORG_ID) REFERENCES t_sys_org (ORG_ID),
  CONSTRAINT t_org_user_ibfk_2 FOREIGN KEY (USER_ID) REFERENCES t_sys_userinfo (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='单位用户关联表';

-- ----------------------------
-- Table structure for t_role_post
-- ----------------------------
DROP TABLE IF EXISTS t_role_post;
CREATE TABLE t_role_post (
  ROLE_POSTID varchar(32) NOT NULL,
  POST_ID varchar(32) DEFAULT NULL COMMENT '岗位ID',
  ROLE_ID varchar(32) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (ROLE_POSTID),
  KEY FK_Reference_15 (POST_ID),
  KEY FK_Reference_16 (ROLE_ID),
  CONSTRAINT t_role_post_ibfk_1 FOREIGN KEY (POST_ID) REFERENCES t_sys_post (POST_ID),
  CONSTRAINT t_role_post_ibfk_2 FOREIGN KEY (ROLE_ID) REFERENCES t_sys_role (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='岗位角色关联表';

-- ----------------------------
-- Table structure for t_sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS t_sys_login_log;
CREATE TABLE t_sys_login_log (
  loginlogid varchar(32) NOT NULL COMMENT '主键',
  logname varchar(255) DEFAULT NULL COMMENT '日志名称',
  userid varchar(32) DEFAULT NULL COMMENT '管理员id',
  create_time varchar(20) DEFAULT NULL COMMENT '创建时间',
  succeed varchar(255) DEFAULT NULL COMMENT '是否执行成功',
  message text COMMENT '具体消息',
  ip varchar(255) DEFAULT NULL COMMENT '登录ip',
  PRIMARY KEY (loginlogid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录记录';
-- ----------------------------
-- Table structure for t_sys_codebuilder
-- ----------------------------
DROP TABLE IF EXISTS t_sys_codebuilder;
CREATE TABLE t_sys_codebuilder (
  CODEID varchar(32) NOT NULL,
  PRI varchar(10) DEFAULT NULL COMMENT '是否主键',
  TABLENAME varchar(100) DEFAULT NULL COMMENT '表名',
  FIELDS varchar(100) DEFAULT NULL COMMENT '字段',
  QUERYCONDITION varchar(4) DEFAULT NULL COMMENT '是否为查询条件Y，N',
  FILL varchar(4) DEFAULT NULL COMMENT '是否必填',
  DESCRIPT varchar(100) DEFAULT NULL COMMENT '描述',
  CONTROL varchar(200) DEFAULT NULL COMMENT '所属控件:select ,textarea,text,date',
  PRIMARY KEY (CODEID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='框架代码辅助表';

-- ----------------------------
-- Table structure for t_sys_dataprivilege
-- ----------------------------
DROP TABLE IF EXISTS t_sys_dataprivilege;
CREATE TABLE t_sys_dataprivilege (
  ROLE_ORGID varchar(32) NOT NULL,
  ORG_ID varchar(32) DEFAULT NULL COMMENT '机构ID',
  ROLE_ID varchar(32) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (ROLE_ORGID),
  KEY FK_Reference_13 (ROLE_ID),
  KEY FK_Reference_4 (ORG_ID),
  CONSTRAINT t_sys_dataprivilege_ibfk_1 FOREIGN KEY (ROLE_ID) REFERENCES t_sys_role (role_id),
  CONSTRAINT t_sys_dataprivilege_ibfk_2 FOREIGN KEY (ORG_ID) REFERENCES t_sys_org (ORG_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_dict
-- ----------------------------
DROP TABLE IF EXISTS t_sys_dict;
CREATE TABLE t_sys_dict (
  DICT_ID varchar(32) NOT NULL,
  DICT_TYPE varchar(30) DEFAULT NULL COMMENT '字典分类',
  DICT_NAME varchar(60) DEFAULT NULL COMMENT '字典分类名称',
  DICT_VALUE varchar(200) DEFAULT NULL COMMENT '字典值',
  DICT_CODE varchar(60) DEFAULT NULL COMMENT '字典编码',
  REMARK varchar(100) DEFAULT NULL COMMENT '字典描述',
  DICT_SORT varchar(2) DEFAULT NULL COMMENT '1、平台内2、平台外',
  SORT_NUM int(11) DEFAULT NULL COMMENT '排序号',
  PARENT_TYPE varchar(32) DEFAULT NULL COMMENT '父分类',
  DISPLAY_SORT varchar(10) DEFAULT NULL COMMENT '字典展现分类:下拉类型(select)树形(tree) 复选型(checkbox)单选radio',
  DICT_CLASS int(11) DEFAULT NULL,
  MULTI_TYPE varchar(1) DEFAULT NULL COMMENT '单选或多选：主要针对树形控件',
  ORG_ID varchar(32) DEFAULT NULL COMMENT '所属机构',
  ISDEFAULT varchar(10) DEFAULT NULL COMMENT '默认值',
  displaying varchar(2) DEFAULT NULL COMMENT '是否显示',
  create_user_id varchar(32) DEFAULT NULL COMMENT '创建人',
  create_time varchar(32) DEFAULT NULL COMMENT '创建时间',
  update_user_id varchar(32) DEFAULT NULL COMMENT '修改人',
  update_time varchar(32) DEFAULT NULL COMMENT '修改时间',
  dict_url varchar(500) DEFAULT NULL COMMENT '字典图标',
  PRIMARY KEY (DICT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_group
-- ----------------------------
DROP TABLE IF EXISTS t_sys_group;
CREATE TABLE t_sys_group (
  GROUP_ID varchar(32) NOT NULL COMMENT '组ID',
  GROUP_NAME varchar(100) DEFAULT NULL COMMENT '组名称',
  REMARK varchar(200) DEFAULT NULL COMMENT '组描述',
  GROUP_TYPE varchar(4) DEFAULT NULL COMMENT '组类别 ',
  PARENT_GROUPID varchar(32) DEFAULT NULL COMMENT '父组ID ',
  create_user_id varchar(32) DEFAULT NULL COMMENT '创建人',
  create_time varchar(32) DEFAULT NULL COMMENT '创建时间',
  update_user_id varchar(32) DEFAULT NULL COMMENT '修改人',
  update_time varchar(32) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (GROUP_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组表';

-- ----------------------------
-- Table structure for t_sys_log
-- ----------------------------
DROP TABLE IF EXISTS t_sys_log;
CREATE TABLE t_sys_log (
  id varchar(32) NOT NULL,
  username varchar(50) DEFAULT NULL COMMENT '用户名',
  operation varchar(50) DEFAULT NULL COMMENT '用户操作',
  method varchar(200) DEFAULT NULL COMMENT '请求方法',
  params text DEFAULT NULL COMMENT '请求参数',
  ip varchar(64) DEFAULT NULL COMMENT 'IP地址',
  create_date varchar(20) DEFAULT NULL COMMENT '创建时间',
  log_type int(11) DEFAULT NULL COMMENT '日志类型，0为操作日志，1为异常日志',
  request_uri varchar(200) DEFAULT NULL COMMENT '请求URI',
  exception_code varchar(100) DEFAULT NULL COMMENT '异常码',
  exception_detail varchar(2000) DEFAULT NULL COMMENT '异常描述',
  time_consuming bigint(20) DEFAULT NULL COMMENT '请求耗时',
  user_agent varchar(500) DEFAULT NULL COMMENT '客户端信息',
  app_id varchar(32) DEFAULT NULL COMMENT '应用系统',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';


-- ----------------------------
-- Table structure for t_sys_org
-- ----------------------------
-- ----------------------------
-- Table structure for t_sys_org
-- ----------------------------
DROP TABLE IF EXISTS t_sys_org;
CREATE TABLE t_sys_org (
  ORG_ID varchar(32) NOT NULL COMMENT '机构ID',
  ORG_SN int(11) DEFAULT '0' COMMENT '机构排序ID',
  ORG_NAME varchar(100) NOT NULL COMMENT '机构名称',
  ORG_CODE varchar(100) DEFAULT NULL COMMENT '机构编号',
  ORG_XZQM varchar(20) DEFAULT NULL COMMENT '行政区码',
  ORG_SHOWNAME varchar(100) NOT NULL COMMENT '机构显示名称',
  PARENT_ID varchar(32) NOT NULL COMMENT '父机构ID',
  LAYER int(11) DEFAULT NULL COMMENT '层（阶次）',
  REMARK varchar(300) DEFAULT NULL COMMENT '单位简介',
  ORG_TYPE varchar(2) DEFAULT NULL COMMENT '机构类型:1、部门 2、公司',
  ADDR varchar(200) DEFAULT NULL COMMENT '通讯地址',
  ZIP varchar(10) DEFAULT NULL COMMENT '邮政编码',
  EMAIL varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  LEADER varchar(32) DEFAULT NULL COMMENT '机构负责人',
  PHONE varchar(30) DEFAULT NULL COMMENT '办公电话',
  FAX varchar(20) DEFAULT NULL COMMENT '传真号码',
  STATE varchar(1) DEFAULT NULL COMMENT '状态:1有效 2、停用',
  MOBILE varchar(14) DEFAULT NULL COMMENT '负责人手机号码',
  JP varchar(40) DEFAULT NULL COMMENT '简拼',
  QP varchar(40) DEFAULT NULL COMMENT '全拼',
  ANCESTRY varchar(500) DEFAULT NULL COMMENT '排序',
  create_user_id varchar(32) DEFAULT NULL,
  create_time varchar(32) DEFAULT NULL,
  update_user_id varchar(32) DEFAULT NULL,
  update_time varchar(32) DEFAULT NULL,
  PRIMARY KEY (ORG_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构表';


-- ----------------------------
-- Table structure for t_sys_parameter
-- ----------------------------
DROP TABLE IF EXISTS t_sys_parameter;
CREATE TABLE t_sys_parameter (
  PARAID varchar(32) NOT NULL,
  ISDEFAULT varchar(2) DEFAULT NULL COMMENT '是否默认',
  PARANAME varchar(20) DEFAULT NULL COMMENT '参数名称',
  PARANO varchar(100) DEFAULT NULL COMMENT '参数编码',
  PARA_KEY varchar(60) DEFAULT NULL COMMENT '参数值',
  PARA_TYPE varchar(20) DEFAULT NULL COMMENT '参数类型',
  PARAORDER int(11) DEFAULT NULL COMMENT '排序号',
  REMARK varchar(100) DEFAULT NULL COMMENT '描述',
  DISPLAYSORT varchar(10) DEFAULT NULL COMMENT '显示方式：下拉(select)、复选(checkbox)、单选(radio)',
  create_user_id varchar(32) DEFAULT NULL COMMENT '创建人',
  create_time varchar(32) DEFAULT NULL COMMENT '创建时间',
  update_user_id varchar(32) DEFAULT NULL COMMENT '修改人',
  update_time varchar(32) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (PARAID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='参数配置';

-- ----------------------------
-- Table structure for t_sys_post
-- ----------------------------
DROP TABLE IF EXISTS t_sys_post;
CREATE TABLE t_sys_post (
  POST_ID varchar(32) NOT NULL COMMENT '岗位ID',
  POST_NAME varchar(100) DEFAULT NULL COMMENT '岗位名称',
  REMARK varchar(200) DEFAULT NULL COMMENT '岗位描述',
  POST_TYPE varchar(4) DEFAULT NULL COMMENT '岗位类别:字典中定义',
  PARENT_POSTID varchar(32) DEFAULT NULL COMMENT '父岗位ID',
  create_user_id varchar(32) DEFAULT NULL COMMENT '创建人',
  create_time varchar(32) DEFAULT NULL COMMENT '创建时间',
  update_user_id varchar(32) DEFAULT NULL COMMENT '修改人',
  update_time varchar(32) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (POST_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='岗位表';

-- ----------------------------
-- Table structure for t_sys_resource
-- ----------------------------
DROP TABLE IF EXISTS t_sys_resource;
CREATE TABLE t_sys_resource (
  menu_id varchar(32) NOT NULL,
  parent_id varchar(32) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  name varchar(50) DEFAULT NULL COMMENT '菜单名称',
  url varchar(200) DEFAULT NULL COMMENT '菜单URL',
  perms varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  type int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  icon varchar(50) DEFAULT NULL COMMENT '菜单图标',
  order_num int(11) DEFAULT NULL COMMENT '排序',
  ORG_ID varchar(32) DEFAULT NULL COMMENT '机构ID',
  RESOURCE_CLASS int(11) DEFAULT NULL COMMENT '层级',
  REMARK varchar(255) DEFAULT NULL COMMENT '资源描述',
  DISPLAY varchar(2) DEFAULT NULL COMMENT '是否可见',
  create_user_id varchar(32) DEFAULT NULL COMMENT '创建人',
  create_time varchar(32) DEFAULT NULL COMMENT '创建时间',
  update_user_id varchar(32) DEFAULT NULL COMMENT '修改人',
  update_time varchar(32) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS t_sys_role;
CREATE TABLE t_sys_role (
  role_id varchar(32) NOT NULL,
  role_name varchar(100) DEFAULT NULL COMMENT '角色名称',
  remark varchar(100) DEFAULT NULL COMMENT '备注',
  create_user_id varchar(32) DEFAULT NULL COMMENT '创建人',
  create_time varchar(32) DEFAULT NULL COMMENT '创建时间',
  update_user_id varchar(32) DEFAULT NULL COMMENT '修改人',
  update_time varchar(32) DEFAULT NULL COMMENT '修改时间',
  role_type varchar(4) DEFAULT NULL COMMENT '角色类型：公有、私有',
  org_id varchar(32) DEFAULT NULL COMMENT '所属机构',
  data_scope varchar(4) DEFAULT NULL COMMENT '数据范围:01所有数据 02所在机构及以下数据; 03本级数据 04自定义明细 05所在部门数据06所在部门及以下数据 07本人数据',
  status varchar(2) DEFAULT NULL COMMENT '角色状态：启用、禁用',
  PRIMARY KEY (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Table structure for t_sys_roleprivilege
-- ----------------------------
DROP TABLE IF EXISTS t_sys_roleprivilege;
CREATE TABLE t_sys_roleprivilege (
  id varchar(32) NOT NULL,
  role_id varchar(32) DEFAULT NULL COMMENT '角色ID',
  menu_id varchar(32) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Table structure for t_sys_serialno
-- ----------------------------
DROP TABLE IF EXISTS t_sys_serialno;
CREATE TABLE t_sys_serialno (
  SERIALNO_ID varchar(32) NOT NULL,
  SERIAL_NAME varchar(100) DEFAULT NULL COMMENT '名称',
  SECOUND_NAME varchar(150) DEFAULT NULL COMMENT '字段名',
  FORMULAR_REGX varchar(200) DEFAULT NULL COMMENT '规则 {yyyy}{MM}{DD}-{NO}  NO:000001开始',
  CREATE_TYPE varchar(10) DEFAULT NULL COMMENT '生成方式',
  SERIAL_LENGTH int(11) DEFAULT NULL COMMENT '流水号长度',
  STEP int(11) DEFAULT NULL COMMENT '步长',
  INIT_VALUE varchar(20) DEFAULT NULL COMMENT '初始值',
  CURRENT_VALUE varchar(20) DEFAULT NULL COMMENT '当前值',
  REMARK varchar(200) DEFAULT NULL COMMENT '备注说明',
  TAB_NAME varchar(200) DEFAULT NULL COMMENT '表名',
  SFBL varchar(4) DEFAULT NULL COMMENT '是否补零',
  create_user_id varchar(32) DEFAULT NULL COMMENT '创建人',
  create_time varchar(32) DEFAULT NULL COMMENT '创建时间',
  update_user_id varchar(32) DEFAULT NULL COMMENT '修改人',
  update_time varchar(32) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (SERIALNO_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流水号生成规则表';

-- ----------------------------
-- Table structure for t_sys_userinfo
-- ----------------------------
DROP TABLE IF EXISTS t_sys_userinfo;
CREATE TABLE t_sys_userinfo (
  user_id varchar(32) NOT NULL,
  username varchar(50) NOT NULL COMMENT '用户名',
  password varchar(100) DEFAULT NULL COMMENT '密码',
  salt varchar(20) DEFAULT NULL COMMENT '盐',
  email varchar(100) DEFAULT NULL COMMENT '邮箱',
  mobile varchar(100) DEFAULT NULL COMMENT '手机号',
  status tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  create_user_id varchar(32) DEFAULT NULL COMMENT '创建者ID',
  create_time varchar(32) DEFAULT NULL COMMENT '创建时间',
  userimg varchar(255) DEFAULT NULL COMMENT '用户头像',
  zip varchar(10) DEFAULT NULL COMMENT '邮政编码',
  sort_num int(11) DEFAULT NULL COMMENT '排序号',
  user_type varchar(10) DEFAULT NULL COMMENT '用户类型',
  post_id varchar(32) DEFAULT NULL COMMENT '所属岗位',
  sex varchar(4) DEFAULT NULL COMMENT '性别',
  USER_REALNAME varchar(50) DEFAULT NULL COMMENT '真实姓名',
  user_theme varchar(255) DEFAULT NULL COMMENT '用户选择皮肤',
  user_card varchar(18) DEFAULT NULL COMMENT '身份证号码',
  birthday varchar(20) DEFAULT NULL COMMENT '出生年月',
  native_place varchar(255) DEFAULT NULL COMMENT '家庭住址',
  nation varchar(255) DEFAULT NULL COMMENT '民族',
  update_user_id varchar(32) DEFAULT NULL COMMENT '创建者ID',
  update_time varchar(32) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (user_id),
  UNIQUE KEY username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Table structure for t_sys_userprivilege
-- ----------------------------
DROP TABLE IF EXISTS t_sys_userprivilege;
CREATE TABLE t_sys_userprivilege (
  USERPRVIID varchar(32) NOT NULL COMMENT '用户特权ID',
  MENU_ID varchar(32) DEFAULT NULL COMMENT '资源ID',
  USERID varchar(32) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (USERPRVIID),
  KEY FK_Reference_17 (MENU_ID),
  CONSTRAINT FK_Reference_17 FOREIGN KEY (MENU_ID) REFERENCES t_sys_resource (menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户特权:主要用来解决不通过角色,单独给用户授权';

-- ----------------------------
-- Table structure for t_user_group
-- ----------------------------
DROP TABLE IF EXISTS t_user_group;
CREATE TABLE t_user_group (
  USERGROUP_ID varchar(32) NOT NULL,
  GROUP_ID varchar(32) DEFAULT NULL COMMENT '组ID',
  USER_ID varchar(32) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (USERGROUP_ID),
  KEY FK_Reference_10 (USER_ID),
  KEY FK_Reference_9 (GROUP_ID),
  CONSTRAINT t_user_group_ibfk_1 FOREIGN KEY (USER_ID) REFERENCES t_sys_userinfo (user_id),
  CONSTRAINT t_user_group_ibfk_2 FOREIGN KEY (GROUP_ID) REFERENCES t_sys_group (GROUP_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组与用户关联表';

-- ----------------------------
-- Table structure for t_user_post
-- ----------------------------
DROP TABLE IF EXISTS t_user_post;
CREATE TABLE t_user_post (
  USER_JOBID varchar(32) NOT NULL,
  USER_ID varchar(32) DEFAULT NULL COMMENT '用户ID',
  POST_ID varchar(32) DEFAULT NULL COMMENT '岗位ID',
  IS_MAIN varchar(2) NOT NULL DEFAULT '0' COMMENT '是否主岗位（0：否，1：是）',
  PRIMARY KEY (USER_JOBID),
  KEY FK_Reference_14 (POST_ID),
  KEY FK_Reference_8 (USER_ID),
  CONSTRAINT t_user_post_ibfk_1 FOREIGN KEY (POST_ID) REFERENCES t_sys_post (POST_ID),
  CONSTRAINT t_user_post_ibfk_2 FOREIGN KEY (USER_ID) REFERENCES t_sys_userinfo (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户岗位关联表';
-- ----------------------------
-- Table structure for t_sys_attach:平台通用附件表
-- ----------------------------
DROP TABLE IF EXISTS t_sys_attach;
CREATE TABLE t_sys_attach (
  ATTACH_ID varchar(32) NOT NULL,
  create_user_id varchar(32) DEFAULT NULL COMMENT '创建人',
  create_time varchar(32) DEFAULT NULL COMMENT '创建时间',
  update_user_id varchar(32) DEFAULT NULL COMMENT '修改人',
  update_time varchar(32) DEFAULT NULL COMMENT '修改时间',
  FILE_URL varchar(200) DEFAULT NULL COMMENT '文件路径',
  FILE_NAME varchar(200) DEFAULT NULL COMMENT '文件名称',
  REMARK varchar(200) DEFAULT NULL COMMENT '文件备注',
  SECOND_NAME varchar(100) DEFAULT NULL COMMENT '文件别名',
  FILE_TYPE varchar(2) DEFAULT NULL COMMENT '附件分类',
  PKID varchar(32) DEFAULT NULL COMMENT '关联ID',
  FILE_SIZE varchar(10) DEFAULT NULL COMMENT '文件大小',
  FILE_SUFFIX varchar(20) DEFAULT NULL COMMENT '文件后缀',
  LJ_URL varchar(1000) DEFAULT NULL COMMENT '链接地址',
  FILE_NO varchar(100) DEFAULT NULL COMMENT '文件编号',
  FILE_STATE int(11) DEFAULT NULL COMMENT '文件状态',
  FILE_ORERNO int(11) DEFAULT NULL COMMENT '文件排序号',
  PRIMARY KEY (ATTACH_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据附件表';
-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS t_user_role;
CREATE TABLE t_user_role (
  id varchar(32) NOT NULL,
  user_id varchar(32) DEFAULT NULL COMMENT '用户ID',
  role_id varchar(32) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Table structure for t_user_token
-- ----------------------------
DROP TABLE IF EXISTS t_user_token;
CREATE TABLE t_user_token (
  user_id varchar(32) NOT NULL,
  token varchar(100) NOT NULL COMMENT 'token',
  expire_time varchar(20) DEFAULT NULL COMMENT '过期时间',
  update_time varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (user_id),
  UNIQUE KEY token (token)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户Token';



-- 定时任务
-- ----------------------------
-- Table structure for t_schedule_job
-- ----------------------------
DROP TABLE IF EXISTS t_schedule_job;
CREATE TABLE t_schedule_job (
  job_id varchar(32) NOT NULL COMMENT '任务id',
  bean_name varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  method_name varchar(100) DEFAULT NULL COMMENT '方法名',
  params varchar(2000) DEFAULT NULL COMMENT '参数',
  cron_expression varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  status tinyint(4) DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  remark varchar(255) DEFAULT NULL COMMENT '备注',
  create_time varchar(32) DEFAULT NULL COMMENT '创建时间',
  create_user_id varchar(32) DEFAULT NULL,
  update_user_id varchar(32) DEFAULT NULL,
  update_time varchar(32) DEFAULT NULL,
  PRIMARY KEY (job_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务';
-- 定时任务日志
DROP TABLE IF EXISTS t_schedule_job_log;
CREATE TABLE t_schedule_job_log (
  log_id varchar(32) NOT NULL  COMMENT '任务日志id',
  job_id varchar(32) NOT NULL COMMENT '任务id',
  bean_name varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  method_name varchar(100) DEFAULT NULL COMMENT '方法名',
  params varchar(2000) DEFAULT NULL COMMENT '参数',
  status tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  error varchar(2000) DEFAULT NULL COMMENT '失败信息',
  times int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  create_time varchar(32) DEFAULT NULL COMMENT '创建时间',
  create_user_id varchar(32) DEFAULT NULL,
  update_user_id varchar(32) DEFAULT NULL,
  update_time varchar(32) DEFAULT NULL,
  PRIMARY KEY (log_id),
  KEY job_id (job_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务日志';
--  quartz自带表结构
DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
CREATE TABLE QRTZ_JOB_DETAILS(
SCHED_NAME VARCHAR(120) NOT NULL,
JOB_NAME VARCHAR(200) NOT NULL,
JOB_GROUP VARCHAR(200) NOT NULL,
DESCRIPTION VARCHAR(250) NULL,
JOB_CLASS_NAME VARCHAR(250) NOT NULL,
IS_DURABLE VARCHAR(1) NOT NULL,
IS_NONCONCURRENT VARCHAR(1) NOT NULL,
IS_UPDATE_DATA VARCHAR(1) NOT NULL,
REQUESTS_RECOVERY VARCHAR(1) NOT NULL,
JOB_DATA BLOB NULL,
PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP))
ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS QRTZ_TRIGGERS;
CREATE TABLE QRTZ_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
JOB_NAME VARCHAR(200) NOT NULL,
JOB_GROUP VARCHAR(200) NOT NULL,
DESCRIPTION VARCHAR(250) NULL,
NEXT_FIRE_TIME BIGINT(13) NULL,
PREV_FIRE_TIME BIGINT(13) NULL,
PRIORITY INTEGER NULL,
TRIGGER_STATE VARCHAR(16) NOT NULL,
TRIGGER_TYPE VARCHAR(8) NOT NULL,
START_TIME BIGINT(13) NOT NULL,
END_TIME BIGINT(13) NULL,
CALENDAR_NAME VARCHAR(200) NULL,
MISFIRE_INSTR SMALLINT(2) NULL,
JOB_DATA BLOB NULL,
PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP))
ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
CREATE TABLE QRTZ_SIMPLE_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
REPEAT_COUNT BIGINT(7) NOT NULL,
REPEAT_INTERVAL BIGINT(12) NOT NULL,
TIMES_TRIGGERED BIGINT(10) NOT NULL,
PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
CREATE TABLE QRTZ_CRON_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
CRON_EXPRESSION VARCHAR(120) NOT NULL,
TIME_ZONE_ID VARCHAR(80),
PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
CREATE TABLE QRTZ_SIMPROP_TRIGGERS
  (          
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    STR_PROP_1 VARCHAR(512) NULL,
    STR_PROP_2 VARCHAR(512) NULL,
    STR_PROP_3 VARCHAR(512) NULL,
    INT_PROP_1 INT NULL,
    INT_PROP_2 INT NULL,
    LONG_PROP_1 BIGINT NULL,
    LONG_PROP_2 BIGINT NULL,
    DEC_PROP_1 NUMERIC(13,4) NULL,
    DEC_PROP_2 NUMERIC(13,4) NULL,
    BOOL_PROP_1 VARCHAR(1) NULL,
    BOOL_PROP_2 VARCHAR(1) NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP) 
    REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
CREATE TABLE QRTZ_BLOB_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
BLOB_DATA BLOB NULL,
PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
INDEX (SCHED_NAME,TRIGGER_NAME, TRIGGER_GROUP),
FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS QRTZ_CALENDARS;
CREATE TABLE QRTZ_CALENDARS (
SCHED_NAME VARCHAR(120) NOT NULL,
CALENDAR_NAME VARCHAR(200) NOT NULL,
CALENDAR BLOB NOT NULL,
PRIMARY KEY (SCHED_NAME,CALENDAR_NAME))
ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP))
ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
CREATE TABLE QRTZ_FIRED_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
ENTRY_ID VARCHAR(95) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
INSTANCE_NAME VARCHAR(200) NOT NULL,
FIRED_TIME BIGINT(13) NOT NULL,
SCHED_TIME BIGINT(13) NOT NULL,
PRIORITY INTEGER NOT NULL,
STATE VARCHAR(16) NOT NULL,
JOB_NAME VARCHAR(200) NULL,
JOB_GROUP VARCHAR(200) NULL,
IS_NONCONCURRENT VARCHAR(1) NULL,
REQUESTS_RECOVERY VARCHAR(1) NULL,
PRIMARY KEY (SCHED_NAME,ENTRY_ID))
ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
CREATE TABLE QRTZ_SCHEDULER_STATE (
SCHED_NAME VARCHAR(120) NOT NULL,
INSTANCE_NAME VARCHAR(200) NOT NULL,
LAST_CHECKIN_TIME BIGINT(13) NOT NULL,
CHECKIN_INTERVAL BIGINT(13) NOT NULL,
PRIMARY KEY (SCHED_NAME,INSTANCE_NAME))
ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS QRTZ_LOCKS;
CREATE TABLE QRTZ_LOCKS (
SCHED_NAME VARCHAR(120) NOT NULL,
LOCK_NAME VARCHAR(40) NOT NULL,
PRIMARY KEY (SCHED_NAME,LOCK_NAME))
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE INDEX IDX_QRTZ_J_REQ_RECOVERY ON QRTZ_JOB_DETAILS(SCHED_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_J_GRP ON QRTZ_JOB_DETAILS(SCHED_NAME,JOB_GROUP);

CREATE INDEX IDX_QRTZ_T_J ON QRTZ_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_JG ON QRTZ_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_C ON QRTZ_TRIGGERS(SCHED_NAME,CALENDAR_NAME);
CREATE INDEX IDX_QRTZ_T_G ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_T_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_G_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NEXT_FIRE_TIME ON QRTZ_TRIGGERS(SCHED_NAME,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE_GRP ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

CREATE INDEX IDX_QRTZ_FT_TRIG_INST_NAME ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME);
CREATE INDEX IDX_QRTZ_FT_INST_JOB_REQ_RCVRY ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_FT_J_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_JG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_T_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_FT_TG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);