SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for t_mail_account
-- ----------------------------
DROP TABLE IF EXISTS t_mail_account;
CREATE TABLE t_mail_account (
  ma_id varchar(32) NOT NULL COMMENT '账户Id',
  ma_account varchar(100) DEFAULT NULL COMMENT '邮箱账号',
  ma_passwd varchar(100) DEFAULT NULL COMMENT '邮箱密码',
  ma_token varchar(100) DEFAULT NULL COMMENT '邮箱密钥',
  ma_sign longtext COMMENT '邮件签名模板',
  ma_sign_state varchar(5) DEFAULT NULL COMMENT '邮件签名状态',
  ma_showname varchar(50) DEFAULT NULL COMMENT '邮箱显示名称',
  ma_minute int(11) DEFAULT NULL COMMENT '收取邮件频率',
  ma_protocol varchar(10) DEFAULT NULL COMMENT '邮箱类型',
  ma_rece_server varchar(50) DEFAULT NULL COMMENT '收件服务器',
  ma_rs_port int(11) DEFAULT NULL COMMENT '收件服务器端口',
  ma_rs_isssl varchar(5) DEFAULT NULL COMMENT '收件服务器是否SSL',
  ma_send_server varchar(50) DEFAULT NULL COMMENT '发件服务器',
  ma_ss_isssl varchar(5) DEFAULT NULL COMMENT '发件服务器是否SSL',
  ma_ss_port int(11) DEFAULT NULL COMMENT '发件服务器端口',
  ma_back_type varchar(5) DEFAULT NULL COMMENT '服务器备份类型',
  ma_is_main varchar(5) DEFAULT NULL COMMENT '是否主账户',
  ma_custom varchar(32) DEFAULT NULL COMMENT '归属员工',
  org_id varchar(32) DEFAULT NULL COMMENT '组织机构',
  create_user_id varchar(32) DEFAULT NULL COMMENT '创建人',
  create_time varchar(20) DEFAULT NULL COMMENT '创建时间',
  update_user_id varchar(32) DEFAULT NULL COMMENT '修改人',
  update_time varchar(20) DEFAULT NULL COMMENT '修改时间',
  ma_index int(11) DEFAULT NULL COMMENT '邮件数量缀',
  ma_is_receiving varchar(5) DEFAULT NULL COMMENT '邮件是否正在收取',
  PRIMARY KEY (ma_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邮件账户表';

-- ----------------------------
-- Table structure for t_mail_attach
-- ----------------------------
DROP TABLE IF EXISTS t_mail_attach;
CREATE TABLE t_mail_attach (
  ma_id varchar(32) NOT NULL COMMENT '邮件附件id',
  m_id varchar(32) DEFAULT NULL COMMENT '邮件id',
  ma_url varchar(500) DEFAULT NULL COMMENT '文件路径',
  ma_name varchar(500) DEFAULT NULL COMMENT '文件名称',
  ma_second_name varchar(500) DEFAULT NULL COMMENT '文件别名',
  ma_type varchar(10) DEFAULT NULL COMMENT '文件类型',
  ma_size varchar(10) DEFAULT NULL COMMENT '文件大小',
  ma_suffix varchar(20) DEFAULT NULL COMMENT '文件后缀',
  ma_lj varchar(1000) DEFAULT NULL COMMENT '文件链接',
  ma_no varchar(100) DEFAULT NULL COMMENT '文件编号',
  ma_state varchar(5) DEFAULT NULL COMMENT '文件状态',
  ma_sort_no int(11) DEFAULT NULL COMMENT '文件排序号',
  PRIMARY KEY (ma_id),
  KEY FK_attach_detail (m_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邮件附件表';

-- ----------------------------
-- Table structure for t_mail_detail
-- ----------------------------
DROP TABLE IF EXISTS t_mail_detail;
CREATE TABLE t_mail_detail (
  m_id varchar(32) NOT NULL COMMENT '邮件id',
  ma_id varchar(32) DEFAULT NULL COMMENT '账户Id',
  org_id varchar(32) DEFAULT NULL COMMENT '组织机构',
  mf_id varchar(32) DEFAULT NULL COMMENT '所属个人文件夹',
  m_code varchar(50) DEFAULT NULL COMMENT '邮件编号',
  m_subject varchar(200) DEFAULT NULL COMMENT '邮件主题',
  m_content longtext COMMENT '邮件内容',
  m_size int(11) DEFAULT NULL COMMENT '邮件大小',
  m_sender varchar(100) DEFAULT NULL COMMENT '发件人',
  m_showname varchar(50) DEFAULT NULL COMMENT '发件人显示名称',
  m_send_time varchar(20) DEFAULT NULL COMMENT '发件时间',
  m_time varchar(20) DEFAULT NULL COMMENT '邮件时间',
  m_type varchar(20) DEFAULT NULL COMMENT '邮件类型(收，发，草，垃)',
  m_is_read varchar(5) DEFAULT NULL COMMENT '是否已读',
  m_is_star varchar(5) DEFAULT NULL COMMENT '是否星标',
  m_star_time varchar(20) DEFAULT NULL COMMENT '星标时间',
  create_user_id varchar(32) DEFAULT NULL COMMENT '创建人',
  create_time varchar(20) DEFAULT NULL COMMENT '创建时间',
  update_user_id varchar(32) DEFAULT NULL COMMENT '修改人',
  update_time varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (m_id),
  KEY FK_detail_account (ma_id),
  CONSTRAINT FK_detail_account FOREIGN KEY (ma_id) REFERENCES t_mail_account (ma_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邮件明细表';

-- ----------------------------
-- Table structure for t_mail_linkman
-- ----------------------------
DROP TABLE IF EXISTS t_mail_linkman;
CREATE TABLE t_mail_linkman (
  ml_id varchar(32) NOT NULL COMMENT '联系人id',
  ml_name varchar(100) DEFAULT NULL COMMENT '联系人姓名',
  ml_mail varchar(100) DEFAULT NULL COMMENT '联系人邮箱',
  ml_tel varchar(20) DEFAULT NULL COMMENT '联系人电话',
  ma_custom varchar(32) DEFAULT NULL COMMENT '归属员工',
  org_id varchar(32) DEFAULT NULL COMMENT '组织机构',
  remark varchar(1000) DEFAULT NULL COMMENT '备注',
  create_user_id varchar(32) DEFAULT NULL COMMENT '创建人',
  create_time varchar(20) DEFAULT NULL COMMENT '创建时间',
  update_user_id varchar(32) DEFAULT NULL COMMENT '修改人',
  update_time varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (ml_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通讯录';

-- ----------------------------
-- Table structure for t_mail_receiver
-- ----------------------------
DROP TABLE IF EXISTS t_mail_receiver;
CREATE TABLE t_mail_receiver (
  mr_id varchar(32) NOT NULL COMMENT '收件人主键id',
  m_id varchar(32) DEFAULT NULL COMMENT '邮件id',
  mr_name varchar(100) DEFAULT NULL COMMENT '收件人姓名',
  mr_mail varchar(100) DEFAULT NULL COMMENT '收件人邮箱',
  mr_type varchar(10) DEFAULT NULL COMMENT '收件人类型(收件，抄送，密送)',
  PRIMARY KEY (mr_id),
  KEY FK_receive_detail (m_id),
  CONSTRAINT FK_receive_detail FOREIGN KEY (m_id) REFERENCES t_mail_detail (m_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收件人列表(包括抄送，密送)';

-- ----------------------------
-- Table structure for t_mail_fold
-- ----------------------------
DROP TABLE IF EXISTS t_mail_fold;
CREATE TABLE t_mail_fold (
  mf_id varchar(32) NOT NULL COMMENT '文件夹id',
  ma_id varchar(32) DEFAULT NULL COMMENT '邮件账户id',
  parent_id varchar(32) DEFAULT NULL COMMENT '父级id',
  mf_name varchar(50) DEFAULT NULL COMMENT '文件夹名称',
  mf_state varchar(5) DEFAULT NULL COMMENT '文件夹状态',
  mf_sort_num int(11) DEFAULT NULL COMMENT '文件夹排序号',
  create_user_id varchar(32) DEFAULT NULL COMMENT '创建人',
  create_time varchar(20) DEFAULT NULL COMMENT '创建时间',
  update_user_id varchar(32) DEFAULT NULL COMMENT '修改人',
  update_time varchar(20) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (mf_id),
  KEY FK_accout_fold (ma_id),
  CONSTRAINT FK_accout_fold FOREIGN KEY (ma_id) REFERENCES t_mail_account (ma_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邮件个人文件夹';


INSERT INTO t_sys_resource VALUES ('a4a69ff0bf6a43a7b5d237945e7aa0ef', '19c786f2bfbf46398e3b495f6c7014b1', '邮件管理', '', '', 0, 'glyphicon glyphicon-th', 1, '', NULL, NULL, '1', '系统管理员', '2018-03-18 20:26:11', NULL, NULL);
INSERT INTO t_sys_resource VALUES ('744e18b77abd42ffae03211e7968e91f', 'a4a69ff0bf6a43a7b5d237945e7aa0ef', '账号设置', 'mail/tmailaccount/list', 'mail:tmailaccount:query,mail:tmailaccount:list', 1, 'glyphicon glyphicon-leaf', 1, '', NULL, NULL, '1', '系统管理员', '2018-03-18 22:18:07', NULL, NULL);
INSERT INTO t_sys_resource VALUES ('15d786f2bfbf46198e3b495f6c702561', '744e18b77abd42ffae03211e7968e91f', '查看', NULL, 'mail:tmailaccount:view', 2, NULL, 0, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL);
INSERT INTO t_sys_resource VALUES ('16d786f2bfbf46298e3b495f6c703562', '744e18b77abd42ffae03211e7968e91f', '新增', NULL, 'mail:tmailaccount:add', 2, NULL, 0, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL);
INSERT INTO t_sys_resource VALUES ('17d786f2bfbf46398e3b495f6c70w563', '744e18b77abd42ffae03211e7968e91f', '修改', NULL, 'mail:tmailaccount:edit', 2, NULL, 0, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL);
INSERT INTO t_sys_resource VALUES ('18d786f2bfbf46498e3b495f6c70g564', '744e18b77abd42ffae03211e7968e91f', '删除', NULL, 'mail:tmailaccount:remove', 2, NULL, 0, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL);
INSERT INTO t_sys_resource VALUES ('112b3ca3b22847cb9d120ae98cd2bc56', 'a4a69ff0bf6a43a7b5d237945e7aa0ef', '发送邮件', 'mail/tmaildetail/form', 'mail:tmaildetail:query,mail:tmaildetail:list', 1, 'glyphicon glyphicon-eye-open', 2, '', NULL, NULL, '1', '系统管理员', '2018-03-18 20:26:42', NULL, NULL);
INSERT INTO t_sys_resource VALUES ('15d786f2bfbf46198e3b495f6c701a11', '112b3ca3b22847cb9d120ae98cd2bc56', '发送', NULL, 'mail:tmailaccount:add', 2, NULL, 0, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL);
INSERT INTO t_sys_resource VALUES ('16d786f2bfbf46298e3b495f6c701b22', '112b3ca3b22847cb9d120ae98cd2bc56', '保存', NULL, 'mail:tmailaccount:edit', 2, NULL, 0, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL);
INSERT INTO t_sys_resource VALUES ('df2b3ca3b22847cb9da0ae98cd2bc526', 'a4a69ff0bf6a43a7b5d237945e7aa0ef', '邮件列表', 'mail/tmaildetail/list', 'mail:tmaildetail:query,mail:tmaildetail:list,mail:tmaildetail:view', 1, 'glyphicon glyphicon-globe', 3, '', NULL, NULL, '1', '系统管理员', '2018-03-18 20:26:42', NULL, NULL);
INSERT INTO t_sys_resource VALUES ('16d786f2bfbf46298e3b695f6c70c562', 'df2b3ca3b22847cb9da0ae98cd2bc526', '收取', NULL, 'mail:tmaildetail:receive', 2, NULL, 0, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL);
INSERT INTO t_sys_resource VALUES ('22d286f2bfbf46298e3b695f6c70c562', 'df2b3ca3b22847cb9da0ae98cd2bc526', '回复', NULL, 'mail:tmaildetail:replay', 2, NULL, 0, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL);
INSERT INTO t_sys_resource VALUES ('22d486f2bfbf46298e3b695f6c70c562', 'df2b3ca3b22847cb9da0ae98cd2bc526', '转发', NULL, 'mail:tmaildetail:transmit', 2, NULL, 0, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL);
INSERT INTO t_sys_resource VALUES ('22d486f2bfbf46298e3b695f6c70c112', 'df2b3ca3b22847cb9da0ae98cd2bc526', '删除', NULL, 'mail:tmaildetail:remove', 2, NULL, 0, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL);
INSERT INTO t_sys_resource VALUES ('22d786f2bfbf46298e3b695f6c70c562', 'df2b3ca3b22847cb9da0ae98cd2bc526', '已读', NULL, 'mail:tmaildetail:read', 2, NULL, 0, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL);
INSERT INTO t_sys_resource VALUES ('33d786f2bfbf46298e3b695f6c70c562', 'df2b3ca3b22847cb9da0ae98cd2bc526', '全部已读', NULL, 'mail:tmaildetail:readall', 2, NULL, 0, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL);
INSERT INTO t_sys_resource VALUES ('33d786f2bf1f46298e3b695f6c70c562', 'df2b3ca3b22847cb9da0ae98cd2bc526', '复制到', NULL, 'mail:tmaildetail:copy', 2, NULL, 0, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL);
INSERT INTO t_sys_resource VALUES ('33d786f2b22f46298e3b695f6c70c562', 'df2b3ca3b22847cb9da0ae98cd2bc526', '移动到', NULL, 'mail:tmaildetail:move', 2, NULL, 0, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL);
INSERT INTO t_sys_dict VALUES ('23968d79da5349c8bba182064b50b7b1', 'mailType', '邮箱类型', 'IMAP', 'IMAP', null, '2', '2', 'd4b5c5cbd8ee414db931a897712a3b36', '1', null, null, '', '1', '1', '系统管理员', '2018-03-22 14:34:58', null, null, null);
INSERT INTO t_sys_dict VALUES ('37cbb4dc74e44f149f86babed3fa7c77', 'mailType', '邮箱类型', 'POP3', 'POP3', null, '2', '1', 'd4b5c5cbd8ee414db931a897712a3b36', '1', null, null, '', '1', '1', '系统管理员', '2018-03-22 14:34:41', null, null, null);
INSERT INTO t_sys_dict VALUES ('d4b5c5cbd8ee414db931a897712a3b36', 'mailType', '邮箱类型', '', '', null, '1', '1', '0', '1', null, null, '', '1', '1', '系统管理员', '2018-03-22 14:34:14', null, null, null);

INSERT INTO t_schedule_job VALUES ('82c786f2bfbf46398e3b495f6c7014bd', 'receiveJob', 'executeScheduleUpdate', '', '0 0/5 *  * * ?', '0', '每5分钟收取一次邮件系统中账户的邮件', '2016-12-01 23:16:46', '', '', '');