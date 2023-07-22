-- ----------------------------
-- Records of t_sys_org
-- ----------------------------
INSERT INTO t_sys_org VALUES ('845786f2bfbf46398e3b495f6c7014bc', '1', '长沙市', '001', '410017', '创蓝科技', '-1', '1', null, '2', null, null, null, null, '1', null, '1', null, null, '10000', '.1.-1.', null, null, null, null);
INSERT INTO t_sys_org VALUES ('a645cf35f68f4620a3d3753677dd98d3', null, '开福区', '001001', '410017', '开福区', '845786f2bfbf46398e3b495f6c7014bc', null, null, '0', '长沙开福区', '410017', '28449472@qq.com', '朱建武', '0731-89913439', '0731-89913439', '1', '18229923839', 'KF', 'KaiFuqu', null, null, null, null, null);
-- ----------------------------
-- Records of t_sys_userinfo
-- ----------------------------
INSERT INTO t_sys_userinfo VALUES ('1', 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'zhujianwu@ossbar.com', '18229923839', 1, '1', '2016-11-11 11:11:11', NULL, NULL, NULL, NULL, NULL, NULL, '系统管理员', 'green', NULL, NULL, NULL, NULL, NULL, NULL);
-- ----------------------------
-- Records of t_sys_resource
-- ----------------------------
INSERT INTO t_sys_resource VALUES ('1', '19c786f2bfbf46398e3b495f6c7014b1', '系统管理', '', '', '0', 'glyphicon glyphicon-th-large', '1', '', null, null, null, '', null, '系统管理员', '2017-08-12 15:45:18');
INSERT INTO t_sys_resource VALUES ('11c786f2bfbf46398e3b495f6c7014bc', '99c786f2bfbf46398e3b495f6c7014bc', '暂停', null, 'sys:job:pause', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('12c786f2bfbf46398e3b495f6c7014bc', '99c786f2bfbf46398e3b495f6c7014bc', '恢复', null, 'sys:job:resume', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('13c786f2bfbf46398e3b495f6c7014bc', '99c786f2bfbf46398e3b495f6c7014bc', '立即执行', null, 'sys:job:run', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('14c786f2bfbf46398e3b495f6c7014bc', '99c786f2bfbf46398e3b495f6c7014bc', '日志列表', null, 'sys:job:log', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('15d786f2bfbf46398e3b495f6c70156c', '92c786f2bfbf46398e3b495f6c70156c', '查看', null, 'sys:user:list,sys:user:info', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('16d786f2bfbf46398e3b495f6c70156c', '92c786f2bfbf46398e3b495f6c70156c', '新增', null, 'sys:user:save,sys:role:view', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('17d786f2bfbf46398e3b495f6c70156c', '92c786f2bfbf46398e3b495f6c70156c', '修改', null, 'sys:user:edit,sys:role:view', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('18d786f2bfbf46398e3b495f6c70156c', '92c786f2bfbf46398e3b495f6c70156c', '删除', null, 'sys:user:remove', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('19c786f2bfbf46398e3b495f6c7014b1', '', '业务基础平台', '', '', '-1', 'glyphicon glyphicon-th', '1', '', null, null, null, '', null, '系统管理员', '2017-08-12 15:44:16');
INSERT INTO t_sys_resource VALUES ('19d786f2bfbf46398e3b495f6c70156c', '93d786f2bfbf46398e3b495f6c70156c', '查看', null, 'sys:role:list,sys:role:info', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('20d786f2bfbf46398e3b495f6c70156c', '93d786f2bfbf46398e3b495f6c70156c', '新增', null, 'sys:role:save,sys:menu:perms', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('25d786f2bfbf46398e3b495f6c70156c', '82c786f2bfbf46398e3b495f6c70156c', '修改', null, 'sys:menu:edit,sys:menu:view', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('34d786f2bfbf46398e3b495f6c70156c', '82c786f2bfbf46398e3b495f6c70156c', '新增', null, 'sys:menu:save,sys:menu:view', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('44d786f2bfbf46398e3b495f6c70156c', '93d786f2bfbf46398e3b495f6c70156c', '修改', null, 'sys:role:edit,sys:menu:perms', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('52d786f2bfbf46398e3b495f6c70156c', '93d786f2bfbf46398e3b495f6c70156c', '删除', null, 'sys:role:remove', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('64d756f2bfbf46398e3b495f6c70156c', '82c786f2bfbf46398e3b495f6c70156c', '查看', null, 'sys:menu:list,sys:menu:info', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('64d756f2b12f46458e3b495f6c70156c', '82c786f2bfbf46398e3b495f6c70156c', '生成', null, 'sys:menu:list,sys:menu:init', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('64d756f2b12f46111e3b495f6c70156c', '82c786f2bfbf46398e3b495f6c70156c', '复制', null, 'sys:menu:list,sys:menu:copy', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('68d786f2bfbf46398e3b495f6c70156c', 'cc3e5f98fb9d4c83bbb29332f4b0d85e', '资源监控', 'druid/sql.html', '', '1', 'glyphicon glyphicon-stats', '4', '', null, null, null, '', null, '系统管理员', '2017-08-12 15:51:35');
INSERT INTO t_sys_resource VALUES ('73d786f2bfbf46398e3b495f6c70256c', '77b24aa900c44d1e9d2f5cd4b2ffc81d', '操作日志', 'sys/tsyslog/list', 'sys:tsyslog:query,sys:tsyslog:remove,sys:tsyslog:add', '1', 'glyphicon glyphicon-tint', '7', '', null, null, null, '', null, '系统管理员', '2017-08-12 15:54:17');
INSERT INTO t_sys_resource VALUES ('77b24aa900c44d1e9d2f5cd4b2ffc81d', '19c786f2bfbf46398e3b495f6c7014b1', '日志管理', '', '', '0', 'glyphicon glyphicon-calendar', '3', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('82c786f2bfbf46398e3b495f6c70156c', 'cc3e5f98fb9d4c83bbb29332f4b0d85e', '资源管理', 'sys/menu/list', '', '1', 'glyphicon glyphicon-th-list', '3', '', null, null, null, '', null, '系统管理员', '2017-08-12 15:50:55');
INSERT INTO t_sys_resource VALUES ('83d786f2bfbf46398e3b495f6c70156c', 'cc3e5f98fb9d4c83bbb29332f4b0d85e', '参数管理', 'sys/tsysparameter/list', 'sys:tsysparameter:detail,sys:tsysparameter:query,sys:tsysparameter:add,sys:tsysparameter:view,sys:tsysparameter:save,sys:tsysparameter:edit,sys:tsysparameter:remove', '1', 'glyphicon glyphicon-magnet', '6', '', null, null, null, '', null, '系统管理员', '2017-08-12 15:52:45');
INSERT INTO t_sys_resource VALUES ('83d796f2bf2f46398e3b487f6c70156c', 'cc3e5f98fb9d4c83bbb29332f4b0d85e', '流水号管理', 'sys/tsysserialno/list', 'sys:tsysserialno:add,sys:tsysserialno:query,sys:tsysserialno:save,sys:tsysserialno:edit,sys:tsysserialno:list,sys:tsysserialno:remove', '1', 'glyphicon glyphicon-menu-hamburger', '7', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('83d796f2bf2f46398e3b487f6c99156c', 'cc3e5f98fb9d4c83bbb29332f4b0d85e', '岗位管理', 'sys/tsyspost/list', 'sys:tsyspost:add,sys:tsyspost:query,sys:tsyspost:save,sys:tsyspost:edit,sys:tsyspost:list,sys:tsyspost:remove', '1', 'glyphicon glyphicon-send', '7', '', null, null, null, '', null, '系统管理员', '2017-08-12 15:53:02');
INSERT INTO t_sys_resource VALUES ('910786f2bfbf46398e3b495f6c7014bc', '99c786f2bfbf46398e3b495f6c7014bc', '删除', null, 'sys:job:remove', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('91d786f2bfbf46398e3b495f6c7014bc', '99c786f2bfbf46398e3b495f6c7014bc', '新增', null, 'sys:job:save', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('921786f2bfbf46398e3b495f6c7014bc', '99c786f2bfbf46398e3b495f6c7014bc', '修改', null, 'sys:job:edit', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('92c786f2bfbf46398e3b495f6c70156c', '1', '用户管理', 'sys/tsysuserinfo/list', 'sys:tsysuserinfo:perm,sys:tsysuserinfo:reset,sys:tsysuserinfo:clear,sys:tsysuserinfo:role,sys:tsysuserinfo:view,sys:tsysuserinfo:add,sys:tsysuserinfo:query,sys:tsysuserinfo:save,sys:tsysuserinfo:edit,sys:tsysuserinfo:list,sys:tsysuserinfo:remove', '1', 'glyphicon glyphicon-user', '2', '', null, null, null, '', null, '系统管理员', '2017-08-12 15:47:27');
INSERT INTO t_sys_resource VALUES ('93d786f2bfbf46398e3b495f6c70156c', '1', '角色管理', 'sys/role/list', 'sys:role:list,sys:role:add,sys:role:edit,sys:role:save,sys:role:remove', '1', 'glyphicon glyphicon-eye-open', '2', '', null, null, null, '', null, '系统管理员', '2017-08-12 15:48:59');
INSERT INTO t_sys_resource VALUES ('93d787f2bfbf46398e3b495f6c70156c', '82c786f2bfbf46398e3b495f6c70156c', '删除', null, 'sys:menu:remove', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('95y786f2bfbf46398e3b495f6c7014bc', '99c786f2bfbf46398e3b495f6c7014bc', '查看', null, 'sys:job:list,sys:job:info', '2', null, '0', null, null, null, null, '', null, null, null);
INSERT INTO t_sys_resource VALUES ('99c786f2bfbf46398e3b495f6c7014bc', 'cc3e5f98fb9d4c83bbb29332f4b0d85e', '定时任务', 'sys/job/list', 'sys:job:query,sys:job:pause,sys:job:resume,sys:job:run,sys:job:log,sys:job:add,sys:job:edit', '1', 'glyphicon glyphicon-time', '5', '', null, null, null, '', null, '系统管理员', '2017-08-12 15:52:25');
INSERT INTO t_sys_resource VALUES ('99c786f2bfbf46399e3b495f6c7014bc', '1', '机构管理', 'sys/tsysorg/list', 'sys:tsysorg:add,sys:tsysorg:query,sys:tsysorg:save,sys:tsysorg:edit,sys:tsysorg:list,sys:tsysorg:remove,sys:tsysorg:view', '1', 'glyphicon glyphicon-indent-left', '1', '', null, null, null, '', null, '系统管理员', '2017-08-12 15:46:36');
INSERT INTO t_sys_resource VALUES ('cc3e5f98fb9d4c83bbb29332f4b0d85e', '19c786f2bfbf46398e3b495f6c7014b1', '资源管理', '#1', '', '0', 'glyphicon glyphicon-list-alt', '2', '', null, null, null, '', null, '系统管理员', '2017-08-12 15:50:15');
INSERT INTO t_sys_resource VALUES ('f9e8bed5cf6a485db552e0602741564c', '1', '字典管理', 'sys/tsysdict/list', 'sys:tsysdict:add,sys:tsysdict:query,sys:tsysdict:save,sys:tsysdict:edit,sys:tsysdict:remove', '1', 'glyphicon glyphicon-tasks', '6', '', null, null, null, '', null, '系统管理员', '2017-08-12 15:49:53');
INSERT INTO t_sys_resource VALUES ('f9e8bed5cf6a485db552e0602741845c', '1', '附件管理', 'sys/tsysattach/list', 'sys:tsysattach:query,sys:tsysattach:remove', '1', 'glyphicon glyphicon-paperclip', '5', '', null, null, null, '', null, '系统管理员', '2017-08-12 15:49:23');
INSERT INTO t_sys_resource VALUES ('f9e8bed5cf6a485db552e0602741894c', '77b24aa900c44d1e9d2f5cd4b2ffc81d', '登录日志', 'sys/tsysloginlog/list', 'sys:tsysloginlog:query,sys:tsysloginlog:remove', '1', 'glyphicon glyphicon-th', '1', '', null, null, null, '', null, '系统管理员', '2017-08-12 15:53:19');

-- ----------------------------
-- Records of t_schedule_job
-- ----------------------------
INSERT INTO t_schedule_job VALUES ('67c786f2bfbf46398e3b495f6c7014bc', 'testTask', 'test2', null, '0 0/30 * * * ?', '1', '无参数测试', '2016-12-03 14:55:56', null, null, null);
INSERT INTO t_schedule_job VALUES ('82c786f2bfbf46398e3b495f6c7014bc', 'testTask', 'test', 'ossbar', '0 0/30 * * * ?', '0', '有参数测试', '2016-12-01 23:16:46', null, null, null);

-- ----------------------------
-- Records of t_sys_parameter
-- ----------------------------
INSERT INTO t_sys_parameter VALUES ('05164f14b6e44effa0e85627a7f1c0ab', '0', '性别', '1', '女', 'sex', '2', '', '3', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('091c43797e324d7c9e444b411bcdaa0c', '0', '参数是否显示', '0', '隐藏', 'displaying', '2', '', '3', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('186538a36ae247e996bd1d04a02cc914', '1', '系统皮肤', 'black', '黑色风格', 'userTheme', '1', '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('197e6d4ae3774ebdb4fa2a06d28ff011', '1', '流水号生成方式', '5', '序列递增', 'createType', null, '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('1ff8be630b654aca8e6d6c598f9dc1df', '0', '系统皮肤', 'blue', '蓝色风格', 'userTheme', '2', '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('207fd581a1214400916cdc0649371cdb', '1', '流水号生成方式', '1', '递增', 'createType', null, '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('2843901e86c94bc0a3740388429a98d1', '1', '参数是否显示', '1', '显示', 'displaying', '1', '', '3', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('2b55d5bd5b794db6b19ac707e7f82a6c', '1', '机构类型', '0', '部门', 'orgType', '1', '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('3a2b51854e32411c991bfa43f69d9e35', '0', '用户类型', '1', '外部用户', 'userType', '2', '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('41a50abb9a354ddfa9278225850cdba7', '0', '流水号生成方式', '3', '每月递增', 'createType', null, '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('466c44a013414afc85bdb3070351c4e1', '1', '角色类型', '1', '公共', 'roleType', null, '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('4a3c63fe93284958910c4befce1cd300', '1', '字典展示形式', '1', '列表结构', 'displaySort', '1', '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('4b70538c199e4d48b4f5c758f634909e', '1', '参数显示方式', '1', '下拉列表', 'paradisplaysort', null, '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('4f48bee1a44440d1b036fbe010ee706e', '0', '角色类型', '2', '私有', 'roleType', null, '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('55fce603b2b94d989f681bf66561075f', '1', '字典类型', '2', '字典', 'dictSort', null, '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('59fc2a7ef3cc4b0da624cb0513ecc0d2', '0', '系统皮肤', 'gray', '灰色风格', 'userTheme', '3', '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('5df5d8dd96b640c880c62404aad4f5f7', '0', '资源类型', '1', '菜单', 'resourceType', '3', '', '3', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('8470321913454b3caf7bae987cf22c50', '1', '字典类型', '1', '目录', 'dictSort', null, '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('85a5f6db90e44887b59de0ca349590b1', '1', '状态', '2', '停用', 'state', '2', '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('9284990dadbe49f4861ada58dc1ff0a0', '0', '用户状态', '0', '禁用', 'status', '1', '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('98fd47c0e4ba46c58c05eb5002c3a3b7', '0', '参数显示方式', '2', '复选框', 'paradisplaysort', null, '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('a4710d4144604d398f10f58b26dac383', '1', '用户状态', '1', '正常', 'status', '1', '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('ab556ac473cb494eaa9002f3c047b2cb', '0', '字典展示形式', '2', '树型结构', 'displaySort', '1', '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('ad2ddbc0498b4eb2938eade892eda6e0', '1', '参数默认值', '1', '是', 'isdefault', '1', '', '3', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('addcbe5b660d4d12b075731ef5a24e0e', '1', '性别', '0', '男', 'sex', '1', '', '3', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('af8e5651b8ee431ebe1286b5f32cae64', '0', '参数显示方式', '3', '单选按钮', 'paradisplaysort', null, '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('af8e5651b8ee431ebe1286b5f32cae65', '1', '编码每级长度', '3', 'orgCodeLength', 'codeLength', null, '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('b971c42956ba4352b8be77739bfdad12', '1', '状态', '1', '有效', 'state', '1', '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('c3d230c31ad54620a0b8bc35e24c614a', '0', '流水号生成方式', '4', '每年递增', 'createType', null, '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('ccf27de4081942c582a348d8ed8f2b93', '0', '机构类型', '1', '公司', 'orgType', '2', '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('cd658b1b9c75423ca8011087de6f4797', '0', '流水号生成方式', '2', '每日递增', 'createType', null, '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('d4cd23eae6024374a5b09c9d6194fe6b', '0', '参数默认值', '0', '否', 'isdefault', '2', '', '3', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('d50090a03aba475998165f594c4dcd8f', '0', '资源类型', '2', '按钮', 'resourceType', '4', '', '3', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('e817c856f7e04991b25bbb036e8b4527', '1', '岗位类型', '1', '普通员工', 'postType', '1', '', '3', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('e817c856f7e04991b25bbb036e8b5327', '1', '资源类型', '-1', '子系统', 'resourceType', '1', '', '3', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('f42db395ec624bafab493f0b535e4d40', '1', '用户类型', '0', '内部用户', 'userType', '1', '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('f5d105aeaa9943bab9ad5d5fda84da19', '0', '系统皮肤', 'black', '黑色风格', 'userTheme', '4', '', '1', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('fe57d9cb875443d6b45202a8f56d2595', '0', '资源类型', '0', '目录', 'resourceType', '2', '', '3', null, null, null, null);
INSERT INTO t_sys_parameter VALUES ('fe57d9cb875443d6b45202a8f56d3695', '0', '岗位类型', '0', '公司领导', 'postType', '2', '', '3', null, null, null, null);

INSERT INTO t_user_token (user_id, token, expire_time, update_time) VALUES ('1', 'b16fdf264cb75795842ae2b3508a5812', '2017-07-25 21:25:00', '2017-07-25 09:25:00');
commit;
