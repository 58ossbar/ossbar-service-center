SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('ossbar-platform-core', 'cb-platform-core', '{noop}s9sd8uf9ewj9egwejgewgj0', 'all', 'password,authorization_code,refresh_token,implicit,client_credentials', 'http://localhost:9084/console/login,http://www.baidu.com/console/login', 'ROLE_USER13', NULL, NULL, NULL, 'true');
INSERT INTO `oauth_client_details` VALUES ('ossbar-platform-core1', 'cb-platform-core', '{noop}s9sd8uf9ewj9egwejgewgj01', 'all', 'password,authorization_code,refresh_token,implicit,client_credentials', 'http://localhost:9084/console/login,http://www.baidu.com/console/login', 'ROLE_USER13', NULL, NULL, NULL, 'true');

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('CreatorblueScheduler', 'TASK_0293303f0cd34877b75576edcf45e4f6', 'DEFAULT', '0 0/30 * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('CreatorblueScheduler', 'TASK_c5e92c817aa144019860c1b64e8ae301', 'DEFAULT', '*/5 * * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('CreatorblueScheduler', 'TASK_0293303f0cd34877b75576edcf45e4f6', 'DEFAULT', NULL, 'com.ossbar.modules.job.utils.ScheduleJob', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597401227B226A6F624964223A223032393333303366306364333438373762373535373665646366343565346636222C226265616E4E616D65223A22746573745461736B222C226D6574686F644E616D65223A2274657374222C22706172616D73223A2263726561746F72626C7565222C2263726F6E45787072657373696F6E223A223020302F3330202A202A202A203F222C22737461747573223A312C2272656D61726B223A22E68891E6ADA3E6B58BE8AF95E4B8AD31222C22637265617465557365724964223A2231222C2263726561746554696D65223A22323031392D30362D32312030383A33373A3435222C22757064617465557365724964223A2231222C2275706461746554696D65223A22323031392D30372D30312030383A32363A3532227D7800);
INSERT INTO `qrtz_job_details` VALUES ('CreatorblueScheduler', 'TASK_c5e92c817aa144019860c1b64e8ae301', 'DEFAULT', NULL, 'com.ossbar.modules.job.utils.ScheduleJob', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597400F97B226A6F624964223A226335653932633831376161313434303139383630633162363465386165333031222C226265616E4E616D65223A22746573745461736B222C226D6574686F644E616D65223A2274657374222C22706172616D73223A22222C2263726F6E45787072657373696F6E223A222A2F35202A202A202A202A203F222C22737461747573223A302C2272656D61726B223A22E6AF8FE5A4A9E6AF8F35E7A792E689A7E8A18CE4B880E6ACA1222C2263726561746554696D65223A22323032322D30392D30362031343A33323A3536222C2275706461746554696D65223A22323032322D30392D30362031343A33323A3536227D7800);

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('CreatorblueScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('CreatorblueScheduler', 'TASK_0293303f0cd34877b75576edcf45e4f6', 'DEFAULT', 'TASK_0293303f0cd34877b75576edcf45e4f6', 'DEFAULT', NULL, 1662372000000, -1, 5, 'PAUSED', 'CRON', 1662370619000, 0, NULL, 2, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597401227B226A6F624964223A223032393333303366306364333438373762373535373665646366343565346636222C226265616E4E616D65223A22746573745461736B222C226D6574686F644E616D65223A2274657374222C22706172616D73223A2263726561746F72626C7565222C2263726F6E45787072657373696F6E223A223020302F3330202A202A202A203F222C22737461747573223A312C2272656D61726B223A22E68891E6ADA3E6B58BE8AF95E4B8AD31222C22637265617465557365724964223A2231222C2263726561746554696D65223A22323031392D30362D32312030383A33373A3435222C22757064617465557365724964223A2231222C2275706461746554696D65223A22323031392D30372D30312030383A32363A3532227D7800);
INSERT INTO `qrtz_triggers` VALUES ('CreatorblueScheduler', 'TASK_c5e92c817aa144019860c1b64e8ae301', 'DEFAULT', 'TASK_c5e92c817aa144019860c1b64e8ae301', 'DEFAULT', NULL, 1662445980000, -1, 5, 'PAUSED', 'CRON', 1662445976000, 0, NULL, 2, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597400FA7B226A6F624964223A226335653932633831376161313434303139383630633162363465386165333031222C226265616E4E616D65223A22746573745461736B222C226D6574686F644E616D65223A227465737432222C22706172616D73223A22222C2263726F6E45787072657373696F6E223A222A2F35202A202A202A202A203F222C22737461747573223A312C2272656D61726B223A22E6AF8FE5A4A9E6AF8F35E7A792E689A7E8A18CE4B880E6ACA1222C2263726561746554696D65223A22323032322D30392D30362031343A33323A3536222C2275706461746554696D65223A22323032322D30392D30362031343A35333A3332227D7800);

-- ----------------------------
-- Records of t_org_user
-- ----------------------------
INSERT INTO `t_org_user` VALUES ('113636b8044e4c37bd3f913fb00773c0', '10865', NULL, 0);
INSERT INTO `t_org_user` VALUES ('3c7d3ca02ba844d98002f266cd0aed3b', '10865', NULL, 1);
INSERT INTO `t_org_user` VALUES ('4b2b8c2c2131417aa37acb633a171a2d', '845786f2bfbf46398e3b495f6c7014bc', NULL, 1);
INSERT INTO `t_org_user` VALUES ('530b99601db244bf8305776cc32ee51f', '845786f2bfbf46398e3b495f6c7014bc', 'a52ca4e97d2d46129674051ae6fb6c3d', 0);
INSERT INTO `t_org_user` VALUES ('582fbe78ba374aa89441eeaec6ca0484', '845786f2bfbf46398e3b495f6c7014bc', 'a52ca4e97d2d46129674051ae6fb6c3d', 1);
INSERT INTO `t_org_user` VALUES ('5f248a4b84b0415a887548da8eec254d', 'fccade7bb8ac4dcb8c56e5341437c786', NULL, 0);
INSERT INTO `t_org_user` VALUES ('64cac2ed5aca4cc5b5e2ec48f278d6c7', 'bec753ae9b4c4d6d856dcaa6e4526b2b', NULL, 0);
INSERT INTO `t_org_user` VALUES ('a23355585f6b49cd899bc645e14a3baa', '845786f2bfbf46398e3b495f6c7014bc', 'fc2c324bff174045a1209c5675656eb4', 0);
INSERT INTO `t_org_user` VALUES ('b003de9565844ebab28158ff0af292c1', '845786f2bfbf46398e3b495f6c7014bc', 'b3d705d7a388413aaa8a5669e0039481', 0);
INSERT INTO `t_org_user` VALUES ('b19df25579e54c8883255f284714f285', 'bec753ae9b4c4d6d856dcaa6e4526b2b', NULL, 1);
INSERT INTO `t_org_user` VALUES ('b3aac079f9aa4caba4a33f3a8897edc2', 'fccade7bb8ac4dcb8c56e5341437c786', NULL, 1);
INSERT INTO `t_org_user` VALUES ('cbfd2e53f58c4573a12c27f0679a1fe6', '845786f2bfbf46398e3b495f6c7014bc', 'b3d705d7a388413aaa8a5669e0039481', 1);
INSERT INTO `t_org_user` VALUES ('e3b65d26052c417c9abe077464f57406', '1881b7c655014abea780d23da0f00ebb', NULL, 0);
INSERT INTO `t_org_user` VALUES ('fdbbd23383c8474ca4f2a2cae616959b', '845786f2bfbf46398e3b495f6c7014bc', 'fc2c324bff174045a1209c5675656eb4', 1);

-- ----------------------------
-- Records of t_schedule_job
-- ----------------------------
INSERT INTO `t_schedule_job` VALUES ('0293303f0cd34877b75576edcf45e4f6', 'testTask', 'test', 'creatorblue', '0 0/30 * * * ?', 1, '我正测试中1', '2019-06-21 08:37:45', '1', '1', '2019-07-01 08:26:52');
INSERT INTO `t_schedule_job` VALUES ('c5e92c817aa144019860c1b64e8ae301', 'testTask', 'test2', '', '*/5 * * * * ?', 1, '每天每5秒执行一次', '2022-09-06 14:32:56', NULL, NULL, '2022-09-06 14:53:32');

-- ----------------------------
-- Records of t_sys_attach
-- ----------------------------
INSERT INTO `t_sys_attach` VALUES ('313a9e7bb4854831932f7b45d5b0fe61', 'e:/uploads//sys-user-img/4599baf7-fe4d-4e4f-9f6d-28b6c3813dfc.jpeg', 'image/jpeg', NULL, NULL, '2', '1', '5471', '.jpeg', '4599baf7-fe4d-4e4f-9f6d-28b6c3813dfc.jpeg', NULL, 1, NULL, '1', '2022-09-28 09:13:13', NULL, '2022-09-28 09:13:14');
INSERT INTO `t_sys_attach` VALUES ('dfbcccfe87c34c21ba3f3abb170df245', 'e:/uploads//settings/c93da23e-e226-487b-b984-ac33a4b6f539.png', 'image/png', NULL, NULL, '3', '23ec7635acdc438592171e0a5fccaf', '2458', '.png', 'c93da23e-e226-487b-b984-ac33a4b6f539.png', NULL, 1, NULL, '1', '2022-09-28 09:06:49', NULL, '2022-09-28 09:06:49');

-- ----------------------------
-- Records of t_sys_dict
-- ----------------------------
INSERT INTO `t_sys_dict` VALUES ('0382eb64e9334987942ca7e79d915db2', 'teacher_state', '教师状态', '在职', 'Y', '', '2', 1, 'dd5250d847214de093b437335df54f92', '1', NULL, '', '', '1', '1', '1', '2018-12-14 16:56:58', '1', '2019-07-25 08:27:38', '');
INSERT INTO `t_sys_dict` VALUES ('0717782e383d41f4bed7c3b1ca5f0956', 'group_member_identity', '课堂小组成员身份', NULL, NULL, NULL, '1', 1, '0', '1', NULL, NULL, NULL, '1', '1', '1', '2021-05-09 16:27:32', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('0bbbefd660604f6faa0d4f04f7c19cd0', 'activityQuestionType', '题目类型(活动)', '单选', '1', NULL, '2', 1, '688e4ec4b811411596632f0a80323aad', '1', NULL, NULL, NULL, '1', '1', '1', '2020-06-28 09:14:11', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('0ef2ba05d7ea43198ba40ec5cd2f2e08', 'newsStatus', '新闻资讯状态', '已发布', '2', NULL, '2', 2, '864de58f9cd247c8a829bf385cdcfb9d', '1', NULL, NULL, NULL, '1', '1', '1', '2019-07-23 10:32:26', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('10bf48e67a9841c6bf5cea319497cd9c', 'newsStatus', '新闻资讯状态', '待审核', '1', NULL, '2', 1, '864de58f9cd247c8a829bf385cdcfb9d', '1', NULL, NULL, NULL, '1', '1', '1', '2019-07-23 10:32:18', '1', '2019-08-16 17:47:04', NULL);
INSERT INTO `t_sys_dict` VALUES ('19741519c789413da99f3ecc5377ffca', 'activityState', '活动状态', NULL, NULL, NULL, '1', 1, '0', '1', NULL, NULL, NULL, '1', '1', '1', '2020-06-22 15:47:30', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('1f2093f0213a4dac99afd3f9f3cea804', 'activityState', '活动状态', '未开始', '0', NULL, '2', 1, '19741519c789413da99f3ecc5377ffca', '1', NULL, NULL, NULL, '1', '1', '1', '2020-06-22 15:47:38', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('2372235176de4ba680825a094bc1745a', 'group_member_identity', '课堂小组成员身份', '技术总监', '2', NULL, '2', 2, '0717782e383d41f4bed7c3b1ca5f0956', '1', NULL, NULL, NULL, '1', '1', '1', '2021-05-09 17:41:37', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('2a9f8587be334d67a5f8b9ba0c0a13ab', 'deployMainType', '教学包发布方大类', '创蓝教育', '3', NULL, '2', 3, '85e08b917c8a4da2ad2356680395315f', '1', NULL, NULL, NULL, '1', '1', '1', '2019-07-26 14:58:36', '1', '2021-02-25 10:14:02', NULL);
INSERT INTO `t_sys_dict` VALUES ('2ed464e15afb458989936854275e0fe7', 'group_member_identity', '课堂小组成员身份', '测试人员', '4', NULL, '2', 4, '0717782e383d41f4bed7c3b1ca5f0956', '1', NULL, NULL, NULL, '1', '1', '1', '2021-05-09 17:42:03', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('306ce177b75c4533af988ef23619c9b2', 'teacher_state', '教师状态', '离职', 'N', '', '2', 2, 'dd5250d847214de093b437335df54f92', '1', NULL, '', '', '1', '1', '1', '2018-12-14 16:57:06', '1', '2022-09-22 09:12:05', '');
INSERT INTO `t_sys_dict` VALUES ('315814c9e3454e60af2ac86dd6bf295e', 'term', '所属阶段', '第二阶段', '2', NULL, '2', 2, 'a4d5f60d75d54864aef8b54cadfe8123', '1', NULL, NULL, NULL, '0', '1', '1', '2019-07-31 10:23:18', '1', '2021-11-30 13:24:05', NULL);
INSERT INTO `t_sys_dict` VALUES ('3257b60e547c42bf838852787816dc1a', 'state1', '状态(是/否)', NULL, NULL, NULL, '1', 1, '0', '1', NULL, NULL, '845786f2bfbf46398e3b495f6c7014bc', '1', '1', '1', '2019-07-24 09:10:52', '1', '2019-07-24 09:13:43', NULL);
INSERT INTO `t_sys_dict` VALUES ('3fd9b5ea277b46e19d20ef76e554aae0', 'state1', '状态(是/否)', '是', 'Y', NULL, '2', 1, '3257b60e547c42bf838852787816dc1a', '1', NULL, NULL, '845786f2bfbf46398e3b495f6c7014bc', '1', '1', '1', '2019-07-24 09:11:29', NULL, '2019-07-24 09:13:43', NULL);
INSERT INTO `t_sys_dict` VALUES ('54d41108ae3e44d6ab05f4375dae007d', 'term', '所属阶段', '第一阶段', '1', NULL, '2', 1, 'a4d5f60d75d54864aef8b54cadfe8123', '1', NULL, NULL, NULL, '1', '1', '1', '2019-07-31 10:23:04', '1', '2021-11-30 13:23:37', NULL);
INSERT INTO `t_sys_dict` VALUES ('614711011f414c53a863c83d7ddb960e', 'activityQuestionType', '题目类型(活动)', '多选', '2', NULL, '2', 2, '688e4ec4b811411596632f0a80323aad', '1', NULL, NULL, NULL, '1', '1', '1', '2020-06-28 09:14:18', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('63af3b1847d44850a81c9c5c933afebc', 'group_member_identity', '课堂小组成员身份', 'UI设计师', '5', NULL, '2', 5, '0717782e383d41f4bed7c3b1ca5f0956', '1', NULL, NULL, NULL, '1', '1', '1', '2021-05-09 17:42:12', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('688e4ec4b811411596632f0a80323aad', 'activityQuestionType', '题目类型(活动)', NULL, NULL, NULL, '1', 1, '0', '1', NULL, NULL, NULL, '1', '1', '1', '2020-06-28 09:13:55', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('6bb8768c3e4c45a7bcd817b6ef61d016', 'group_member_identity', '课堂小组成员身份', '项目经理', '1', NULL, '2', 1, '0717782e383d41f4bed7c3b1ca5f0956', '1', NULL, NULL, NULL, '1', '1', '1', '2021-05-09 16:27:41', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('7095779dee82453f9b20e380fd11131d', 'group_member_identity', '课堂小组成员身份', '开发人员', '3', NULL, '2', 3, '0717782e383d41f4bed7c3b1ca5f0956', '1', NULL, NULL, NULL, '1', '1', '1', '2021-05-09 17:41:44', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('7883d890d08947509d82e30bfe6c3d37', 'state1', '状态(是/否)', '否', 'N', NULL, '2', 2, '3257b60e547c42bf838852787816dc1a', '1', NULL, NULL, '845786f2bfbf46398e3b495f6c7014bc', '0', '1', '1', '2019-07-24 09:11:44', NULL, '2019-07-24 09:13:43', NULL);
INSERT INTO `t_sys_dict` VALUES ('7d9cfc5a9b884987991bb00ea82cecf9', 'deployMainType', '教学包发布方大类', '个人', '2', NULL, '2', 2, '85e08b917c8a4da2ad2356680395315f', '1', NULL, NULL, NULL, '1', '1', '1', '2019-07-26 14:58:28', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('81fb19c2a78a486caf1ab6c88e24e7ba', 'pkgLimit', '教学包使用限制', '授权', '1', NULL, '2', 1, 'e80e9e4659ba48d7aaa60bf61955d91b', '1', NULL, NULL, NULL, '1', '1', '1', '2019-07-26 14:54:39', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('84c337cbfa414507a678a32a22f924dc', 'sex', '性别', '', '', '', '1', 1, '0', '1', NULL, '', '', '1', '1', '1', '2018-03-02 09:31:41', '1', '', '');
INSERT INTO `t_sys_dict` VALUES ('85e08b917c8a4da2ad2356680395315f', 'deployMainType', '教学包发布方大类', NULL, NULL, NULL, '1', 1, '0', '1', NULL, NULL, NULL, '1', '1', '1', '2019-07-26 14:58:10', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('864de58f9cd247c8a829bf385cdcfb9d', 'newsStatus', '新闻资讯状态', NULL, NULL, NULL, '1', 1, '0', '1', NULL, NULL, NULL, '1', '1', '1', '2019-07-23 10:31:52', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('94d903d7664948298930839063833966', 'pkgLevel', '教学包适用层次', '中职', '3', NULL, '2', 3, 'ea0c6f1698fb4c96b46c58996f0064fd', '1', NULL, NULL, NULL, '1', '1', '1', '2019-07-26 14:39:06', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('95c1b201e8bd41c9960129d47c683731', 'sex', '性别', '女', '2', '', '2', 2, '84c337cbfa414507a678a32a22f924dc', '1', NULL, '', '', '0', '1', '1', '2018-03-02 09:32:22', '1', '', '');
INSERT INTO `t_sys_dict` VALUES ('9aa2211d4582435686e67c8a8a4c7d03', 'pkgLimit', '教学包使用限制', '免费', '2', NULL, '2', 2, 'e80e9e4659ba48d7aaa60bf61955d91b', '1', NULL, NULL, NULL, '1', '1', '1', '2019-07-26 14:54:59', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('a4d5f60d75d54864aef8b54cadfe8123', 'term', '所属阶段', NULL, NULL, NULL, '1', 1, '0', '1', NULL, NULL, NULL, '1', '1', '1', '2019-07-31 10:22:44', '1', '2021-11-30 13:23:17', NULL);
INSERT INTO `t_sys_dict` VALUES ('a58edf2c8a8d4c39a0b300b61d613198', 'activityState', '活动状态', '进行中', '1', NULL, '2', 2, '19741519c789413da99f3ecc5377ffca', '1', NULL, NULL, NULL, '1', '1', '1', '2020-06-22 15:47:52', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('adca6bcc181b4e9fb333f8d78b3b2de9', 'pkgLevel', '教学包适用层次', '高职', '2', NULL, '2', 2, 'ea0c6f1698fb4c96b46c58996f0064fd', '1', NULL, NULL, NULL, '1', '1', '1', '2019-07-26 14:38:54', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('b2187623988749189b7e36d595d9e5c9', 'newsStatus', '新闻资讯状态', '未通过', '4', NULL, '2', 4, '864de58f9cd247c8a829bf385cdcfb9d', '1', NULL, NULL, NULL, '1', '1', '1', '2019-08-16 16:27:28', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('bba6021a8344454fb23e8c75fefda4e0', 'activityQuestionType', '题目类型(活动)', '简答', '3', NULL, '2', 3, '688e4ec4b811411596632f0a80323aad', '1', NULL, NULL, NULL, '1', '1', '1', '2020-07-08 16:36:39', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('c06d94b8e2944887beb2277bb8957dd1', 'pkgLevel', '教学包适用层次', '本科', '1', NULL, '2', 1, 'ea0c6f1698fb4c96b46c58996f0064fd', '1', NULL, NULL, NULL, '1', '1', '1', '2019-07-26 14:38:44', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('c5225891d6534da4b91581fe25924ddf', 'sex', '性别', '保密', '0', '', '2', 3, '84c337cbfa414507a678a32a22f924dc', '1', NULL, '', '', '1', '1', '1', '2018-05-04 17:13:35', '1', '', '');
INSERT INTO `t_sys_dict` VALUES ('d38bfc9575b844caa24c0b56da56b07b', 'activityState', '活动状态', '已结束', '2', NULL, '2', 3, '19741519c789413da99f3ecc5377ffca', '1', NULL, NULL, NULL, '1', '1', '1', '2020-06-22 15:48:07', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('d696266f71cc40dc99298e32de8ba90c', 'term', '所属阶段', '第三阶段', '3', NULL, '2', 3, 'a4d5f60d75d54864aef8b54cadfe8123', '1', NULL, NULL, NULL, '0', '1', '1', '2021-11-30 13:23:57', '1', '2021-11-30 13:23:17', NULL);
INSERT INTO `t_sys_dict` VALUES ('dd5250d847214de093b437335df54f92', 'teacher_state', '教师状态', '', '', '', '1', 1, '0', '1', NULL, '', '', '1', '1', '1', '2018-12-14 16:56:46', '1', '', '');
INSERT INTO `t_sys_dict` VALUES ('e094c6ae52904d9bbd8a914081cb4728', 'group_member_identity', '课堂小组成员身份', '无', '0', NULL, '2', 1, '0717782e383d41f4bed7c3b1ca5f0956', '1', NULL, NULL, NULL, '1', '1', 'ec7a75b6122d4f9b8c7b08459b3c4b3f', '2021-09-22 08:49:56', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('e2f6d38e76194503a72ee2f2b37219f3', 'sex', '性别', '男', '1', '', '2', 1, '84c337cbfa414507a678a32a22f924dc', '1', NULL, '', '', '1', '1', '1', '2018-03-02 09:32:03', '1', '', '');
INSERT INTO `t_sys_dict` VALUES ('e80e9e4659ba48d7aaa60bf61955d91b', 'pkgLimit', '教学包使用限制', NULL, NULL, NULL, '1', 1, '0', '1', NULL, NULL, NULL, '1', '1', '1', '2019-07-26 14:54:25', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('e9c9a42d1574469ea8506b4243c58474', 'pkgLevel', '教学包适用层次', '专升本', '4', NULL, '2', 4, 'ea0c6f1698fb4c96b46c58996f0064fd', '1', NULL, NULL, NULL, '1', '1', '1', '2021-02-25 10:11:58', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('ea0c6f1698fb4c96b46c58996f0064fd', 'pkgLevel', '教学包适用层次', NULL, NULL, NULL, '1', 1, '0', '1', NULL, NULL, NULL, '1', '1', '1', '2019-07-26 14:38:28', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('f30254b9da664de3a2d76ccc69d75458', 'deployMainType', '教学包发布方大类', '学校', '1', NULL, '2', 1, '85e08b917c8a4da2ad2356680395315f', '1', NULL, NULL, NULL, '1', '1', '1', '2019-07-26 14:58:23', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES ('fbfbed1fa506439d9113e1290099cac3', 'newsStatus', '新闻资讯状态', '已删除', '3', NULL, '2', 3, '864de58f9cd247c8a829bf385cdcfb9d', '1', NULL, NULL, NULL, '1', '1', '1', '2019-07-23 10:32:35', NULL, NULL, NULL);

-- ----------------------------
-- Records of t_sys_org
-- ----------------------------
INSERT INTO `t_sys_org` VALUES ('10865', NULL, '湖南信息职业技术学院', '03', '123456', '信息学院', '-1', NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, 'HN*SX', 'HuNan**jiShuXueyuan', NULL, '1', '2020-09-01 16:55:50', '1', '2020-10-15 09:17:01', NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES ('1881b7c655014abea780d23da0f00ebb', NULL, '湖南电子科技职业学院', '08', '412300', '电子科技职院', '-1', NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, '1', '2021-05-31 22:19:59', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES ('3242e174551c42f9bd737afb80fdb7d2', NULL, '吉林大学', '05', '412305', '吉林大学', '-1', NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, 'JLD', 'JiLinDaxue', NULL, '1', '2020-09-23 10:42:59', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES ('408ea3a61e754f14900a15284020208f', NULL, '湖南工业职业技术学院', '09', '412300', '湖南工业职业技术学院', '-1', NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, 'HNGYZYJSX', 'HuNanGongYeZhiYeJiShuXueyuan', NULL, '1', '2021-10-25 11:46:13', '1', '2021-10-25 11:46:36', NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES ('845786f2bfbf46398e3b495f6c7014bc', 1, '开源吧', '01', '410017', '开源吧', '-1', NULL, NULL, '0', '阳光100国际新城1-34栋', '', '', '', '0731-89913439', '', '1', '', 'KYB', 'KaiYuanBa', NULL, '1', NULL, '1', '2022-09-28 09:34:46', NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES ('8e5043e881534abca617043ce08b17f5', NULL, '长沙理工大学', '04', '430222', '长沙理工', '-1', NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, 'ZSLGD', 'ZhangShaLiGongDaxue', NULL, '1', '2020-09-23 10:42:06', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES ('ab4dca41d4a24ba49dbd60850dfd8a78', NULL, '国防科技大学', '02', '412309', '国防科大', '-1', NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, 'GFKJD', 'GuoFangKeJiDaxue', NULL, '1', '2020-09-02 17:12:34', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES ('acb356043ff94b0ab51d921d6eabcb25', NULL, '湖南大学', '07', '412304', '湖南大学', '-1', NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, 'HND', 'HuNanDaxue', NULL, '1', '2020-09-23 10:44:47', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES ('ba341773011f4bacb917859516ff54de', NULL, '湖南工业大学', '010', '412300', '湖南工业大学', '-1', NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, 'HNGYD', 'HuNanGongYeDaxue', NULL, '1', '2021-10-25 11:47:44', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES ('bec753ae9b4c4d6d856dcaa6e4526b2b', NULL, '网络空间安全学院', '0302', '412300', '网络空间安全学院', '10865', NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, 'WLKJAQX', 'WangLuoKongJianAnQuanXueyuan', NULL, '1', '2020-09-26 20:55:59', '1', '2020-09-26 20:57:08', NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES ('c58056a52099439cb1c527b76b5e0aab', NULL, '人工智能与软件工程学院', '0801', '412300', '软件学院', '1881b7c655014abea780d23da0f00ebb', NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, 'RGZNHRJX', 'RenGongZhiNengHeRuanJianXueyuan', NULL, '1', '2021-05-31 22:23:00', '1', '2022-03-12 15:36:07', NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES ('e1ba0dbcf3cc4944a4845bb132e9726c', NULL, '信息工程学院', '0901', '412300', '信息工程学院', '408ea3a61e754f14900a15284020208f', NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, 'XXGCX', 'XinXiGongChengXueyuan', NULL, '1', '2021-10-25 11:47:08', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES ('fac783801284490fb3cce1dc06c54e53', NULL, '中南大学', '06', '412304', '中南大学', '-1', NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, 'ZND', 'ZhongNanDaxue', NULL, '1', '2020-09-23 10:44:34', NULL, '2022-09-28 09:29:37', NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES ('fccade7bb8ac4dcb8c56e5341437c786', NULL, '软件学院', '0301', '123456', '软件学院', '10865', NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, 'RJX', 'RuanJianXueyuan', NULL, '1', '2020-09-24 11:31:17', '1', '2020-09-26 20:55:18', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Records of t_sys_parameter
-- ----------------------------
INSERT INTO `t_sys_parameter` VALUES ('03b0074668b3425ea832021b1cc0f9c0', '1', '岗位类型', '3', '编外人员', 'postType', 3, NULL, '1', '1', '2019-08-31 11:35:24', NULL, NULL);
INSERT INTO `t_sys_parameter` VALUES ('05164f14b6e44effa0e85627a7f1c0ab', '0', '性别', '1', '女', 'sex', 2, '', '3', '1', NULL, '1', '2019-06-19 11:27:10');
INSERT INTO `t_sys_parameter` VALUES ('091c43797e324d7c9e444b411bcdaa0c', '0', '参数是否显示', '0', '隐藏', 'displaying', 2, '', '3', '1', NULL, '1', NULL);
INSERT INTO `t_sys_parameter` VALUES ('2634443fe4bf41b588282d4bd47b7139', '1', '附件类型', '6', '新闻logo', 'fileType', 6, NULL, '1', '1', '2019-07-25 11:22:41', '1', '2019-07-25 17:58:42');
INSERT INTO `t_sys_parameter` VALUES ('2843901e86c94bc0a3740388429a98d1', '1', '参数是否显示', '1', '显示', 'displaying', 1, '', '3', '1', NULL, '1', NULL);
INSERT INTO `t_sys_parameter` VALUES ('3a2b51854e32411c991bfa43f69d9e35', '0', '用户类型', '1', '外部', 'userType', 2, '', '1', '1', NULL, '1', '2019-06-18 11:14:15');
INSERT INTO `t_sys_parameter` VALUES ('4a3c63fe93284958910c4befce1cd300', '1', '字典展示形式', '1', '列表结构', 'displaySort', 1, '', '1', '1', NULL, '1', NULL);
INSERT INTO `t_sys_parameter` VALUES ('506305e60cb94814bf39e4604e151189', '1', '附件类型', '5', '广告图片', 'fileType', 5, NULL, '1', '1', '2019-07-20 16:19:02', '1', '2019-07-25 17:58:14');
INSERT INTO `t_sys_parameter` VALUES ('65df1fd9b2364407af798d62da9ce19b', '1', '附件类型', '8', '明星头像', 'fileType', 8, NULL, '1', '1', '2019-07-25 18:00:06', NULL, NULL);
INSERT INTO `t_sys_parameter` VALUES ('77685ec159e14656b1d0da2c16263375', '1', '附件类型', '10', '活教材封面图', 'fileType', 10, NULL, '1', '1', '2019-07-26 14:23:51', '1', '2019-07-26 14:24:02');
INSERT INTO `t_sys_parameter` VALUES ('9246fff7de4f47b593ba88c47beddd89', '1', '附件类型', '9', '企业logo', 'fileType', 9, NULL, '1', '1', '2019-07-25 18:00:24', NULL, NULL);
INSERT INTO `t_sys_parameter` VALUES ('9284990dadbe49f4861ada58dc1ff0a0', '0', '用户状态', '0', '禁用', 'status', 2, '', '1', '1', NULL, '1', '2019-06-19 11:25:40');
INSERT INTO `t_sys_parameter` VALUES ('954ad30fb3074bae8ce090fc218c53ba', '1', '附件类型', '7', '教师头像', 'fileType', 7, NULL, '1', '1', '2019-07-25 17:58:57', NULL, NULL);
INSERT INTO `t_sys_parameter` VALUES ('9ce77bd7d6d14c2382b6f292a1dcfc4a', '1', '附件类型', '3', '系统设置', 'fileType', 3, NULL, '1', '1', '2019-07-26 08:52:48', NULL, NULL);
INSERT INTO `t_sys_parameter` VALUES ('a4710d4144604d398f10f58b26dac383', '1', '用户状态', '1', '正常', 'status', 1, '', '1', '1', NULL, '1', '2019-06-19 11:25:45');
INSERT INTO `t_sys_parameter` VALUES ('ab556ac473cb494eaa9002f3c047b2cb', '0', '字典展示形式', '2', '树型结构', 'displaySort', 1, '', '1', '1', NULL, '1', NULL);
INSERT INTO `t_sys_parameter` VALUES ('ad2ddbc0498b4eb2938eade892eda6e0', '1', '参数默认值', '1', '是', 'isdefault', 1, '', '3', '1', NULL, '1', NULL);
INSERT INTO `t_sys_parameter` VALUES ('addcbe5b660d4d12b075731ef5a24e0e', '1', '性别', '0', '男', 'sex', 1, '', '3', '1', NULL, '1', '2022-09-28 09:56:26');
INSERT INTO `t_sys_parameter` VALUES ('af8e5651b8ee431ebe1286b5f32cae65', '1', '编码每级长度', '3', 'orgCodeLength', 'codeLength', NULL, '', '1', '1', NULL, '1', NULL);
INSERT INTO `t_sys_parameter` VALUES ('b66380c0fa434502a17cf74d22764bb0', '1', '附件类型', '4', '站点栏目图片', 'fileType', 4, NULL, '3', '1', '2019-07-20 11:07:39', '1', '2019-07-25 17:54:10');
INSERT INTO `t_sys_parameter` VALUES ('d4cd23eae6024374a5b09c9d6194fe6b', '0', '参数默认值', '0', '否', 'isdefault', 2, '', '3', '1', NULL, '1', NULL);
INSERT INTO `t_sys_parameter` VALUES ('e817c856f7e04991b25bbb036e8b4527', '1', '岗位类型', '1', '普通员工', 'postType', 1, '', '3', '1', NULL, '1', '2019-06-19 11:21:15');
INSERT INTO `t_sys_parameter` VALUES ('f42db395ec624bafab493f0b535e4d40', '1', '用户类型', '0', '内部', 'userType', 1, '', '1', '1', NULL, '1', '2019-06-18 11:14:07');
INSERT INTO `t_sys_parameter` VALUES ('fe57d9cb875443d6b45202a8f56d3695', '0', '岗位类型', '0', '公司领导', 'postType', 2, '', '3', '1', NULL, '1', NULL);
INSERT INTO `t_sys_parameter` VALUES ('fe57d9cb875443d6b45892a8f53vgf95', '1', '附件类型', '2', '用户头像附件', 'fileType', 2, '', '3', '1', '2018-05-29 17:37:02', '1', '2019-05-29 09:13:11');
INSERT INTO `t_sys_parameter` VALUES ('fe57d9cb875443d6b45892a8f53voif5', '1', '附件状态', '1', '已关联绑定', 'fileState', 2, '', '3', '1', '2018-05-29 17:37:02', '1', '2019-05-29 09:13:11');
INSERT INTO `t_sys_parameter` VALUES ('fe57d9cb875443d6b45892a8f56d3520', '1', '附件状态', '0', '未关联绑定', 'fileState', 1, '', '3', '1', '2018-05-29 17:37:02', '1', '2019-05-29 09:13:11');
INSERT INTO `t_sys_parameter` VALUES ('fe57d9cb875443d6b45892a8f56d3695', '1', '附件类型', '1', '字典附件', 'fileType', 1, '', NULL, '1', '2018-05-29 17:37:02', '1', '2019-07-20 16:19:30');

-- ----------------------------
-- Records of t_sys_post
-- ----------------------------
INSERT INTO `t_sys_post` VALUES ('a8bd124536ec41978b78eb6f01f70c87', '总经理', '', '0', NULL, '1', '2022-09-28 09:15:14', NULL, NULL, 2);
INSERT INTO `t_sys_post` VALUES ('b7442f40fc6c48c0b1834b15b93b3511', '前台', '', '1', NULL, '1', '2022-09-28 09:15:31', NULL, NULL, 3);
INSERT INTO `t_sys_post` VALUES ('c637a910f1ae4aa485f6b7977c662ad5', '测试人员', '', '1', NULL, '1', '2022-09-28 09:15:47', NULL, NULL, 4);
INSERT INTO `t_sys_post` VALUES ('f5c6aeb01cfa4cb4a1b820d56ba9bb05', '董事长', '', '0', NULL, '1', '2022-09-28 09:15:03', NULL, NULL, 1);

-- ----------------------------
-- Records of t_sys_resource
-- ----------------------------
INSERT INTO `t_sys_resource` VALUES ('009ec0088551486d8e6d94d1a51df3b4', 'f9e8bed5cf6a485db552e0602741564c', '查看', NULL, 'sys:tsysdict:view', 2, NULL, 1, NULL, NULL, NULL, '1', '1', '2018-03-28 11:58:38', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('04f3562771a744bdb70511f6ca63e1a7', 'cfea91bab6b34fb396c0e37486f35bbd', '新增', NULL, 'forum:tevglforumfriend:add', 2, NULL, 1, NULL, NULL, NULL, '1', '1', '2021-03-08 14:40:00', '1', '2021-03-08 14:40:17', NULL);
INSERT INTO `t_sys_resource` VALUES ('05c93a7893754c279d7549e1e8c6270b', '26715d0dc99f4c92b61d97b9bc074312', '新增', NULL, 'site:tevglsiteresourceext:add', 2, NULL, 1, NULL, NULL, NULL, '1', '1', '2019-07-16 08:57:25', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('05e3f0068784445582b521efd708e85d', '89bbe647e4a34ae98df0e31dfdff48d0', '删除', NULL, 'site:tevglsiteupdatelog:remove', 2, NULL, 3, NULL, NULL, NULL, '1', '1', '2020-09-01 09:30:11', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('062e19317bf04d0dbb4c21a228de690a', '89bbe647e4a34ae98df0e31dfdff48d0', '修改', NULL, 'site:tevglsiteupdatelog:edit', 2, NULL, 2, NULL, NULL, NULL, '1', '1', '2020-09-01 09:30:11', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('0dd44379d5224cd994f51cfcdb613027', '9243558bee9548bcb08ca596c37e8a5b', '查看', NULL, 'site:tevglsitenews:view', 2, NULL, 4, NULL, NULL, NULL, '1', '1', '2019-07-20 14:10:24', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('0e0a4e29e34b4646a66d22cd5b876713', 'b43e1950c19d40deb8b9b701ffec5f65', '栏目图片', '/site/tevglsiteavd', 'site:tevglsiteavd:list,site:tevglsiteavd:query', 1, 'fa fa-area-chart', 2, '', 0, NULL, '1', '1', '2019-07-15 10:54:48', '1', '2019-07-22 10:18:49', NULL);
INSERT INTO `t_sys_resource` VALUES ('0e32bdae84a94c5f9bf81c619aa300bd', '82c786f2bfbf46398e3b495f6c70156c', '上下移', NULL, 'sys:menu:move', 2, NULL, 1, NULL, 0, NULL, '1', '1', '2019-06-18 09:18:59', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('1', '19c786f2bfbf46398e3b495f6c7014b1', '系统管理', '', '', 0, 'fa  fa-th-large', 2, '', NULL, NULL, '1', '1', NULL, '1', '2019-06-19 08:33:10', NULL);
INSERT INTO `t_sys_resource` VALUES ('11bc5f0d88e94f1d817aa535892b2530', '538ecb94406f46f4862748f0c4ed847c', '新增', NULL, 'tch:tevgltchclasstrainee:add', 2, NULL, 1, NULL, NULL, NULL, '1', '1', '2020-10-27 15:42:39', '1', '2020-10-27 15:42:49', NULL);
INSERT INTO `t_sys_resource` VALUES ('11c786f2bfbf46398e3b495f6c7014bc', '99c786f2bfbf46398e3b495f6c7014bc', '暂停', NULL, 'sys:job:pause', 2, NULL, 0, NULL, NULL, NULL, '1', '1', NULL, '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('12c786f2bfbf46398e3b495f6c7014bc', '99c786f2bfbf46398e3b495f6c7014bc', '恢复', NULL, 'sys:job:resume', 2, NULL, 0, NULL, NULL, NULL, '1', '1', NULL, '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('137d86ffc32a44b4ae2b43b2f014c5c8', 'dc86ce2ecef34cbea905ad78305e9e10', '删除', NULL, 'sys:tsyspost:remove', 2, NULL, 4, NULL, NULL, NULL, '1', '1', '2019-06-12 14:51:52', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('13c786f2bfbf46398e3b495f6c7014bc', '99c786f2bfbf46398e3b495f6c7014bc', '立即执行', NULL, 'sys:job:run', 2, NULL, 0, NULL, NULL, NULL, '1', '1', NULL, '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('14c786f2bfbf46398e3b495f6c7014bc', '99c786f2bfbf46398e3b495f6c7014bc', '日志列表', NULL, 'sys:job:log', 2, NULL, 0, NULL, NULL, NULL, '1', '1', NULL, '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('1561d13cabc648b29ec45d2b9764b69d', '93d786f2bfbf46398e3b495f6c70156c', '查询', 'sys/role/query', 'sys:role:query', 2, '', 1, '', NULL, NULL, '1', '1', '2018-03-28 11:42:44', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('15d786f2bfbf46398e3b495f6c70156c', '92c786f2bfbf46398e3b495f6c70156c', '查看', NULL, 'sys:tsysuserinfo:list,sys:user:info', 2, NULL, 0, NULL, NULL, NULL, '1', '1', NULL, '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('16d786f2bfbf46398e3b495f6c70156c', '92c786f2bfbf46398e3b495f6c70156c', '新增', '', 'sys:tsysuserinfo:add,sys:role:view', 2, '', 1, '', NULL, NULL, '1', '1', NULL, '1', '2018-03-28 11:02:56', NULL);
INSERT INTO `t_sys_resource` VALUES ('17d786f2bfbf46398e3b495f6c70156c', '92c786f2bfbf46398e3b495f6c70156c', '修改', NULL, 'sys:tsysuserinfo:edit,sys:role:view', 2, NULL, 0, NULL, NULL, NULL, '1', '1', NULL, '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('180108b6b3a044c9bfb0123fdf60ab88', 'd552fe2536d64166b69b29705e3bf6b8', '删除', NULL, 'tch:tevgltchteacher:remove', 2, NULL, 3, NULL, NULL, NULL, '1', '1', '2019-07-22 15:46:36', '1', '2019-07-22 15:54:07', NULL);
INSERT INTO `t_sys_resource` VALUES ('18d786f2bfbf46398e3b495f6c70156c', '92c786f2bfbf46398e3b495f6c70156c', '删除', NULL, 'sys:tsysuserinfo:remove', 2, NULL, 0, NULL, NULL, NULL, '1', '1', NULL, '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('19c786f2bfbf46398e3b495f6c7014b1', '-1', '教学实训云平台', '', '', -1, 'fa  fa-windows fa-lg fa-spin', 1, '', NULL, NULL, '1', '1', NULL, '1', '2021-11-19 20:22:24', NULL);
INSERT INTO `t_sys_resource` VALUES ('19d786f2bfbf46398e3b495f6c70156c', '93d786f2bfbf46398e3b495f6c70156c', '查看', NULL, 'sys:role:list,sys:role:info,sys:role:edit', 2, NULL, 1, NULL, NULL, NULL, '1', '1', NULL, '1', '2019-07-02 14:52:37', NULL);
INSERT INTO `t_sys_resource` VALUES ('1cc48ea31aa14f58a3a55b004568aa81', 'cfea91bab6b34fb396c0e37486f35bbd', '保存分类', NULL, 'forum:tevglforumfriendtype:add', 2, NULL, 5, NULL, 0, NULL, '1', '1', '2021-04-15 14:56:05', '1', '2021-04-15 15:04:22', NULL);
INSERT INTO `t_sys_resource` VALUES ('20d786f2bfbf46398e3b495f6c70156c', '93d786f2bfbf46398e3b495f6c70156c', '新增', '', 'sys:role:add,sys:menu:perms', 2, '', 1, '', NULL, NULL, '1', '1', NULL, '1', '2018-03-28 11:35:18', NULL);
INSERT INTO `t_sys_resource` VALUES ('2222226398e3b495f6c7014b1', '-1', '实训资源库', '', '', -1, 'fa  fa-jsfiddle fa-lg fa-spin', 2, '', NULL, NULL, '1', '1', NULL, '1', '2019-06-14 17:42:30', NULL);
INSERT INTO `t_sys_resource` VALUES ('22364ccf144d4488b0a5d646f2334438', '92c786f2bfbf46398e3b495f6c70156c', '清空权限', 'sys/tsysuserinfo/clearpermissions', 'sys:tsysuserinfo:clear', 2, '', 8, '', NULL, NULL, '1', '1', '2018-03-28 11:24:16', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('23b7fb277d5e4027b2ee225597e64b82', 'e517523468cf41bab3d77b7aa3fa4065', '行业资讯', '', '', 4, 'fa fa-linode', 1, '', NULL, NULL, '1', '1', '2019-07-20 14:20:19', '1', '2019-08-08 16:44:32', NULL);
INSERT INTO `t_sys_resource` VALUES ('24e2dd79b05046de99da63abb8a3ba39', 'f9e8bed5cf6a485db552e0602741894c', '查看', NULL, 'sys:tsysloginlog:view', 2, NULL, 1, NULL, NULL, NULL, '0', '1', '2019-05-28 06:54:01', '1', '2019-06-15 08:57:35', NULL);
INSERT INTO `t_sys_resource` VALUES ('2521e2ec0aeb40ed8730389bd1b79502', '99c786f2bfbf46399e3b495f6c7014bc', '修改', NULL, 'sys:tsysorg:edit', 2, NULL, 3, NULL, NULL, NULL, '1', '1', '2018-03-28 11:11:04', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('25d786f2bfbf46398e3b495f6c70156c', '82c786f2bfbf46398e3b495f6c70156c', '修改', NULL, 'sys:menu:edit,sys:menu:view', 2, NULL, 0, NULL, NULL, NULL, '1', '1', NULL, '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('26715d0dc99f4c92b61d97b9bc074312', 'b43e1950c19d40deb8b9b701ffec5f65', '站点栏目', '/site/tevglsiteresourceext', 'site:tevglsiteresourceext:list,site:tevglsiteresourceext:query', 1, 'fa fa-edge', 1, '', 0, NULL, '1', '1', '2019-07-15 11:07:05', '1', '2019-07-22 10:18:25', NULL);
INSERT INTO `t_sys_resource` VALUES ('26e0c47fd2694249af81767d45f25a40', '92c786f2bfbf46398e3b495f6c70156c', '重置密码', 'sys/tsysuserinfo/resetpassword', 'sys:tsysuserinfo:reset', 2, '', 6, '', NULL, NULL, '1', '1', '2018-03-28 11:23:21', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('2716eca246ee42bfb19b129ccdf569b6', '0e0a4e29e34b4646a66d22cd5b876713', '修改', NULL, 'site:tevglsiteavd:edit', 2, NULL, 2, NULL, NULL, NULL, '1', '1', '2019-07-15 10:59:24', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('28077fd8d6d24bdebbb28a3aa4c195ce', 'f9e8bed5cf6a485db552e0602741845c', '查询', '', 'sys:tsysattach:query', 2, '', 1, '', NULL, NULL, '1', '1', '2018-03-28 11:57:26', '1', '2018-03-28 11:57:53', NULL);
INSERT INTO `t_sys_resource` VALUES ('29ce103136694ccdaf437d160a939aaa', 'cb09c1957521474dae30b4768b74bc8e', '修改', NULL, 'medu:tmedumediaavd:edit', 2, NULL, 2, NULL, NULL, NULL, '1', '1', '2020-09-02 11:55:30', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('2b64ea278d23403f8b30d4405c2d9673', '5b53877cef0f4eeaadd7f96dc87273ae', '新增', NULL, 'book:tevglbookmajor:add', 2, NULL, 1, NULL, NULL, NULL, '1', '1', '2019-07-30 17:07:02', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('316b953d58604d2c8405dc5951de787c', '5bb687aa97dd4084871bdc700789b5c4', '活教材', 'https://www.budaos.com/#/library', '', 4, 'fa fa-file', 2, '', NULL, NULL, '1', '1', '2019-07-18 09:16:20', '1', '2019-08-31 09:46:17', NULL);
INSERT INTO `t_sys_resource` VALUES ('3409b6acc15d46caad2feb7348c77693', '5b53877cef0f4eeaadd7f96dc87273ae', '维护活教材', NULL, 'book:tevglbookmajor:content', 2, NULL, 5, NULL, 0, NULL, '1', 'ec7a75b6122d4f9b8c7b08459b3c4b3f', '2021-11-08 18:07:41', 'ec7a75b6122d4f9b8c7b08459b3c4b3f', '2021-11-08 18:08:00', NULL);
INSERT INTO `t_sys_resource` VALUES ('34d786f2bfbf46398e3b495f6c70156c', '82c786f2bfbf46398e3b495f6c70156c', '新增', NULL, 'sys:menu:save,sys:menu:view,sys:menu:add', 2, NULL, 0, NULL, NULL, NULL, '1', '1', NULL, '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('3639e81f68a3469aae5254c7510ccf1a', '83d786f2bfbf46398e3b495f6c70156c', '新增', NULL, 'sys:tsysparameter:add', 2, NULL, 2, NULL, NULL, NULL, '1', '1', '2018-03-28 11:59:41', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('371d98578b7c4be1867aa6b5e2d67031', 'e0904dc937b2446ab4e4f04512c00070', '班级管理', '/res/tevgltchclass', 'tch:tevgltchclass:query', 1, 'fa fa-id-card-o', 3, NULL, 0, NULL, '1', '1', '2019-08-19 17:49:14', '1', '2020-10-15 09:10:16', NULL);
INSERT INTO `t_sys_resource` VALUES ('3f1bfc13843b43efb6ec6ee97eb0b06f', '538ecb94406f46f4862748f0c4ed847c', '导入', '1', 'tch:tevgltchclasstrainee:import', 2, NULL, 5, NULL, 0, NULL, '1', '1', '2020-10-27 15:43:31', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('3fb6ba51ca0d492da54fe37b8184e83c', '99c786f2bfbf46399e3b495f6c7014bc', '删除', NULL, 'sys:tsysorg:remove', 2, NULL, 4, NULL, NULL, NULL, '1', '1', '2018-03-28 11:11:04', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('415052f1f6344a37af12a2a3d024b1a9', '371d98578b7c4be1867aa6b5e2d67031', '新增', NULL, 'tch:tevgltchclass:add', 2, NULL, 1, NULL, NULL, NULL, '1', '1', '2019-08-19 17:49:32', '1', '2019-08-19 17:49:40', NULL);
INSERT INTO `t_sys_resource` VALUES ('42f49a7a91f24a0aa81e2eb48ebfcb83', 'f9e8bed5cf6a485db552e0602741894c', '新增', NULL, 'sys:tsysloginlog:add', 2, NULL, 2, NULL, NULL, NULL, '1', '1', '2019-05-28 06:54:01', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('4427179c78ba46aa9c08cda5eab868f8', 'e517523468cf41bab3d77b7aa3fa4065', '站内公告', '', '', 4, 'fa fa-meetup', 1, '', NULL, NULL, '1', '1', '2019-07-20 14:19:56', '1', '2019-08-03 09:40:20', NULL);
INSERT INTO `t_sys_resource` VALUES ('44729d3a6b7841cfa6fc4fbaa6bf0c03', '9243558bee9548bcb08ca596c37e8a5b', '审核', NULL, 'site:tevglsitenews:check', 2, NULL, 5, NULL, 0, NULL, '1', '1', '2019-08-17 08:29:07', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('44d786f2bfbf46398e3b495f6c70156c', '93d786f2bfbf46398e3b495f6c70156c', '修改', NULL, 'sys:role:edit,sys:menu:perms', 2, NULL, 0, NULL, NULL, NULL, '1', '1', NULL, '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('46cb1e473c064d0c817d7093fb1c6fa8', '5b53877cef0f4eeaadd7f96dc87273ae', '修改', NULL, 'book:tevglbookmajor:edit', 2, NULL, 2, NULL, NULL, NULL, '1', '1', '2019-07-30 17:07:02', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('4928266986db47dbbe11380b6455010a', 'bcc3290a69c94b1dbeb6e042ffa75780', '新增', NULL, 'site:tevglsitevideo:add', 2, NULL, 1, NULL, NULL, NULL, '1', '1', '2021-09-11 17:51:30', '1', '2021-09-11 17:51:51', NULL);
INSERT INTO `t_sys_resource` VALUES ('4a32326f63a14145bc9ed60421014305', '-1', '智能分析与评价', NULL, NULL, -1, 'fa fa-bar-chart fa-lg fa-spin', 4, '', 0, NULL, '1', '1', '2019-06-19 17:27:10', '1', '2019-06-19 17:33:13', NULL);
INSERT INTO `t_sys_resource` VALUES ('4bee5249bda9446f9a6364d2c2fe485e', '5b53877cef0f4eeaadd7f96dc87273ae', '移交教学包', NULL, 'pkg:tevglpkginfo:changePackage', 2, NULL, 6, NULL, 0, NULL, '1', 'ec7a75b6122d4f9b8c7b08459b3c4b3f', '2021-11-06 17:28:30', 'ec7a75b6122d4f9b8c7b08459b3c4b3f', '2021-11-08 18:08:05', NULL);
INSERT INTO `t_sys_resource` VALUES ('4ee18677f7c5488280fb5638b56b0790', 'f9e8bed5cf6a485db552e0602741894c', '修改', NULL, 'sys:tsysloginlog:edit', 2, NULL, 3, NULL, NULL, NULL, '1', '1', '2019-05-28 06:54:01', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('4f878d16bad64263870afb77132ce93c', '9243558bee9548bcb08ca596c37e8a5b', '新增', NULL, 'site:tevglsitenews:add', 2, NULL, 1, NULL, NULL, NULL, '1', '1', '2019-07-20 14:10:24', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('52d786f2bfbf46398e3b495f6c70156c', '93d786f2bfbf46398e3b495f6c70156c', '删除', NULL, 'sys:role:remove', 2, NULL, 0, NULL, NULL, NULL, '1', '1', NULL, '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('538ecb94406f46f4862748f0c4ed847c', 'e0904dc937b2446ab4e4f04512c00070', '班级成员管理', '/res/tevgltchclasstrainee', 'tch:tevgltchclasstrainee:query', 1, 'fa fa-grav', 4, NULL, 0, NULL, '1', '1', '2020-10-15 20:40:44', '1', '2020-10-27 15:42:35', NULL);
INSERT INTO `t_sys_resource` VALUES ('5752544b0992428184173fa0e6e1056b', 'bcc3290a69c94b1dbeb6e042ffa75780', '查看', NULL, 'site:tevglsitevideo:view', 2, NULL, 4, NULL, NULL, NULL, '1', '1', '2021-09-11 17:51:31', '1', '2021-09-11 17:53:31', NULL);
INSERT INTO `t_sys_resource` VALUES ('5a786f8eb6b94f0a8eff667da8d0f6b6', '5bb687aa97dd4084871bdc700789b5c4', '我要学习', 'https://www.budaos.com/#/HearClass', '', 4, 'fa fa-user-circle', 4, '', NULL, NULL, '1', '1', '2019-07-18 09:18:11', '1', '2020-09-28 17:24:53', NULL);
INSERT INTO `t_sys_resource` VALUES ('5a9d2b6fa08840ce8951304416e6bbce', '9243558bee9548bcb08ca596c37e8a5b', '删除', NULL, 'site:tevglsitenews:remove', 2, NULL, 3, NULL, NULL, NULL, '1', '1', '2019-07-20 14:10:24', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('5b53877cef0f4eeaadd7f96dc87273ae', 'e0904dc937b2446ab4e4f04512c00070', '职业课程', '/res/tevglbookmajor', 'book:tevglbookmajor:query', 1, 'fa fa-cubes', 1, NULL, 0, NULL, '1', '1', '2019-07-30 17:06:32', '1', '2020-10-15 09:06:58', NULL);
INSERT INTO `t_sys_resource` VALUES ('5b6c71fcefb643d6807d411b95690d43', 'f9e8bed5cf6a485db552e0602741894c', '删除', NULL, 'sys:tsysloginlog:remove', 2, NULL, 4, NULL, NULL, NULL, '0', '1', '2019-05-28 06:54:01', '1', '2019-06-15 08:57:41', NULL);
INSERT INTO `t_sys_resource` VALUES ('5bb687aa97dd4084871bdc700789b5c4', '0', '布道师', '', '', 3, '', 1, '', NULL, NULL, '1', '1', '2019-07-18 09:15:31', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('5ffcaf9d565b44ba9c5f58468678437d', '83d786f2bfbf46398e3b495f6c70156c', '修改', NULL, 'sys:tsysparameter:edit', 2, NULL, 3, NULL, NULL, NULL, '1', '1', '2018-03-28 11:59:41', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('60896726a3024aedad02b405677c20f5', '9243558bee9548bcb08ca596c37e8a5b', '修改', NULL, 'site:tevglsitenews:edit', 2, NULL, 2, NULL, NULL, NULL, '1', '1', '2019-07-20 14:10:24', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('64d756f2b12f46111e3b495f6c70156c', '82c786f2bfbf46398e3b495f6c70156c', '复制', NULL, 'sys:menu:list,sys:menu:copy', 2, NULL, 0, NULL, NULL, NULL, '1', '1', NULL, '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('64d756f2b12f46458e3b495f6c70156c', '82c786f2bfbf46398e3b495f6c70156c', '生成', NULL, 'sys:menu:list,sys:menu:init', 2, NULL, 0, NULL, NULL, NULL, '1', '1', NULL, '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('64d756f2bfbf46398e3b495f6c70156c', '82c786f2bfbf46398e3b495f6c70156c', '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0, NULL, NULL, NULL, '1', '1', NULL, '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('67474aaee9794fbc8bf766148a81cf91', '99c786f2bfbf46399e3b495f6c7014bc', '新增', NULL, 'sys:tsysorg:add', 2, NULL, 2, NULL, NULL, NULL, '1', '1', '2018-03-28 11:11:04', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('692e2140dbe249e4bb508e2184a3dff9', '92c786f2bfbf46398e3b495f6c70156c', '分配特权', 'sys/tsysuserinfo/to_permpage', 'sys:tsysuserinfo:perm', 2, '', 9, '', NULL, NULL, '1', '1', '2018-03-28 11:25:05', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('70b76a59ba8044a3ba2fac6024057aa4', 'f9e8bed5cf6a485db552e0602741845c', '删除', NULL, 'sys:tsysattach:remove', 2, NULL, 4, NULL, NULL, NULL, '1', '1', '2018-03-28 11:57:26', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('7231e9cc583840bc85a432616b9061b9', 'd552fe2536d64166b69b29705e3bf6b8', '查看', NULL, 'tch:tevgltchteacher:view', 2, NULL, 4, NULL, NULL, NULL, '1', '1', '2019-07-22 15:46:36', '1', '2019-07-22 15:54:14', NULL);
INSERT INTO `t_sys_resource` VALUES ('73d786f2bfbf46398e3b495f6c70256c', 'cc3e5f98fb9d4c83bbb29332f4b0d85e', '操作日志', '/sys/log', 'sys:tsyslog:query,sys:tsyslog:remove,sys:tsyslog:add', 1, 'el-icon-document', 7, '', NULL, NULL, '1', '1', NULL, '1', '2019-06-01 08:49:06', NULL);
INSERT INTO `t_sys_resource` VALUES ('741e47570019484ab7498e5515bb817c', '26715d0dc99f4c92b61d97b9bc074312', '修改', NULL, 'site:tevglsiteresourceext:edit', 2, NULL, 2, NULL, NULL, NULL, '1', '1', '2019-07-16 08:57:25', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('771c483b89e248a1adb261777ebd9d98', 'dc86ce2ecef34cbea905ad78305e9e10', '查看', NULL, 'sys:tsyspost:view', 2, NULL, 1, NULL, NULL, NULL, '1', '1', '2019-06-12 14:51:52', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('7c140a2910214d449c891b88991f0850', 'cb09c1957521474dae30b4768b74bc8e', '删除', NULL, 'medu:tmedumediaavd:remove', 2, NULL, 3, NULL, NULL, NULL, '1', '1', '2020-09-02 11:55:30', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('7e83b9c3d46c47aa96c233bc4bb6ff3d', '538ecb94406f46f4862748f0c4ed847c', '删除', NULL, 'tch:tevgltchclasstrainee:remove', 2, NULL, 3, NULL, NULL, NULL, '1', '1', '2020-10-27 15:42:39', '1', '2020-10-27 15:43:03', NULL);
INSERT INTO `t_sys_resource` VALUES ('7f27e168a219489b97c9bb50c59b562f', 'cb09c1957521474dae30b4768b74bc8e', '新增', NULL, 'medu:tmedumediaavd:add', 2, NULL, 1, NULL, NULL, NULL, '1', '1', '2020-09-02 11:55:30', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('82c786f2bfbf46398e3b495f6c70156c', 'cc3e5f98fb9d4c83bbb29332f4b0d85e', '资源模块', '/sys/menu', '', 1, 'el-icon-service', 3, '', NULL, NULL, '1', '1', NULL, '1', '2019-06-19 08:31:45', NULL);
INSERT INTO `t_sys_resource` VALUES ('83d786f2bfbf46398e3b495f6c70156c', 'cc3e5f98fb9d4c83bbb29332f4b0d85e', '全局参数', '/sys/parameter', 'sys:tsysparameter:query', 1, 'el-icon-s-tools', 6, '', NULL, NULL, '1', '1', NULL, '1', '2019-06-19 08:31:57', NULL);
INSERT INTO `t_sys_resource` VALUES ('8830ad960cc64a6e925e92a924c12743', '83d786f2bfbf46398e3b495f6c70156c', '全局配置', NULL, 'sys:settings:edit', 2, NULL, 5, NULL, 0, NULL, '1', '1', '2019-06-21 11:39:37', '1', '2019-06-24 15:03:25', NULL);
INSERT INTO `t_sys_resource` VALUES ('88935b2549474f5b97635a94a76961f9', '83d786f2bfbf46398e3b495f6c70156c', '删除', NULL, 'sys:tsysparameter:remove', 2, NULL, 4, NULL, NULL, NULL, '1', '1', '2018-03-28 11:59:41', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('89bbe647e4a34ae98df0e31dfdff48d0', 'dfbb34201ac74e48b24585de9c5cc9da', '更新日志', '/site/tevglsiteupdatelog', 'site:tevglsiteupdatelog:query', 1, 'fa fa-list-ol', 7, NULL, 0, NULL, '1', '1', '2020-09-01 09:30:08', '1', '2020-09-23 10:01:07', NULL);
INSERT INTO `t_sys_resource` VALUES ('8d257582b0c54e0b987118903613d427', 'dc86ce2ecef34cbea905ad78305e9e10', '新增', NULL, 'sys:tsyspost:add', 2, NULL, 2, NULL, NULL, NULL, '1', '1', '2019-06-12 14:51:52', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('909a6a0f44634a5bbaff870171042414', '99c786f2bfbf46399e3b495f6c7014bc', '上移', NULL, 'sys:tsysorg:move', 2, NULL, 1, NULL, 0, NULL, '1', '1', '2019-06-17 16:12:48', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('910786f2bfbf46398e3b495f6c7014bc', '99c786f2bfbf46398e3b495f6c7014bc', '删除', NULL, 'sys:job:remove', 2, NULL, 0, NULL, NULL, NULL, '1', '1', NULL, '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('91d786f2bfbf46398e3b495f6c7014bc', '99c786f2bfbf46398e3b495f6c7014bc', '新增', NULL, 'sys:job:add', 2, NULL, 1, NULL, NULL, NULL, '1', '1', NULL, '1', '2019-06-28 09:40:17', NULL);
INSERT INTO `t_sys_resource` VALUES ('921786f2bfbf46398e3b495f6c7014bc', '99c786f2bfbf46398e3b495f6c7014bc', '修改', NULL, 'sys:job:edit', 2, NULL, 0, NULL, NULL, NULL, '1', '1', NULL, '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('9243558bee9548bcb08ca596c37e8a5b', 'b43e1950c19d40deb8b9b701ffec5f65', '新闻资讯', '/site/tevglsitenews', 'site:tevglsitenews:list,site:tevglsitenews:query', 1, 'fa fa-superpowers', 3, '', 0, NULL, '1', '1', '2019-07-20 14:03:38', '1', '2019-07-22 10:18:58', NULL);
INSERT INTO `t_sys_resource` VALUES ('92c786f2bfbf46398e3b495f6c70156c', '1', '用户管理', '/sys/user', 'sys:tsysuserinfo:query,sys:tsysuserinfo:view,sys:tsysuserinfo:list', 1, 'fa fa-address-card-o', 3, '', NULL, NULL, '1', '1', NULL, '1', '2019-06-19', NULL);
INSERT INTO `t_sys_resource` VALUES ('93d2420cda4042a0a4efef67a07b7f1f', 'd552fe2536d64166b69b29705e3bf6b8', '新增', NULL, 'tch:tevgltchteacher:add', 2, NULL, 1, NULL, NULL, NULL, '1', '1', '2019-07-22 15:46:36', '1', '2019-07-22 15:53:52', NULL);
INSERT INTO `t_sys_resource` VALUES ('93d786f2bfbf46398e3b495f6c70156c', '1', '角色管理', '/sys/role', 'sys:role:list', 1, 'fa fa-user-circle', 3, '', NULL, NULL, '1', '1', NULL, '1', '2019-06-19', NULL);
INSERT INTO `t_sys_resource` VALUES ('93d787f2bfbf46398e3b495f6c70156c', '82c786f2bfbf46398e3b495f6c70156c', '删除', NULL, 'sys:menu:remove', 2, NULL, 0, NULL, NULL, NULL, '1', '1', NULL, '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('95y786f2bfbf46398e3b495f6c7014bc', '99c786f2bfbf46398e3b495f6c7014bc', '查看', NULL, 'sys:job:list,sys:job:info', 2, NULL, 0, NULL, NULL, NULL, '1', '1', NULL, '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('99c786f2bfbf46398e3b495f6c7014bc', 'cc3e5f98fb9d4c83bbb29332f4b0d85e', '定时任务', '/sys/job', 'sys:job:query', 1, 'el-icon-date', 5, '', NULL, NULL, '1', '1', NULL, '1', '2018-03-28 11:59:31', NULL);
INSERT INTO `t_sys_resource` VALUES ('99c786f2bfbf46399e3b495f6c7014bc', '1', '组织机构', '/sys/dept', 'sys:tsysorg:query,sys:tsysorg:view', 1, 'fa  fa-sitemap', 1, '', NULL, NULL, '1', '1', NULL, '1', '2019-06-27 09:01:09', NULL);
INSERT INTO `t_sys_resource` VALUES ('9d980791b9fc4d76805df6450a3602a1', '538ecb94406f46f4862748f0c4ed847c', '修改', NULL, 'tch:tevgltchclasstrainee:edit', 2, NULL, 2, NULL, NULL, NULL, '1', '1', '2020-10-27 15:42:39', '1', '2020-10-27 15:42:56', NULL);
INSERT INTO `t_sys_resource` VALUES ('9e9f07dd3ade46619c91bb9ef36167cc', 'd552fe2536d64166b69b29705e3bf6b8', '修改', NULL, 'tch:tevgltchteacher:edit', 2, NULL, 2, NULL, NULL, NULL, '1', '1', '2019-07-22 15:46:36', '1', '2019-07-22 15:54:00', NULL);
INSERT INTO `t_sys_resource` VALUES ('a2112c6792aa45ba8855dc8acbc88b4a', '538ecb94406f46f4862748f0c4ed847c', '查看', NULL, 'tch:tevgltchclasstrainee:view', 2, NULL, 4, NULL, NULL, NULL, '1', '1', '2020-10-27 15:42:39', '1', '2020-10-27 15:43:09', NULL);
INSERT INTO `t_sys_resource` VALUES ('a5f98a3c6d15451d931398bf529424e8', '5bb687aa97dd4084871bdc700789b5c4', '我要开课', 'https://www.budaos.com/#/beginClass', '', 4, 'fa fa-american-sign-language-interpreting', 3, '', NULL, NULL, '1', '1', '2019-07-18 09:17:21', '1', '2020-09-28 17:24:28', NULL);
INSERT INTO `t_sys_resource` VALUES ('aa1873b519bb406e851a181e7d826153', '5b53877cef0f4eeaadd7f96dc87273ae', '查看', NULL, 'book:tevglbookmajor:view', 2, NULL, 4, NULL, NULL, NULL, '1', '1', '2019-07-30 17:07:02', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('ac227164c3304bf0a4f03c5cbd71e4ba', '5b53877cef0f4eeaadd7f96dc87273ae', '删除', NULL, 'book:tevglbookmajor:remove', 2, NULL, 3, NULL, NULL, NULL, '1', '1', '2019-07-30 17:07:02', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('b43e1950c19d40deb8b9b701ffec5f65', '19c786f2bfbf46398e3b495f6c7014b1', '门户管理', NULL, NULL, 0, 'fa fa-balance-scale', 4, '', 0, NULL, '1', '1', '2019-07-09 15:35:10', '1', '2020-10-15 09:05:00', NULL);
INSERT INTO `t_sys_resource` VALUES ('b4e13f4ee6b0466ba37bd13f5420e6ba', '89bbe647e4a34ae98df0e31dfdff48d0', '新增', NULL, 'site:tevglsiteupdatelog:add', 2, NULL, 1, NULL, NULL, NULL, '1', '1', '2020-09-01 09:30:10', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('b8e76db567db4bf9b78e5277fdce6ff6', '83d786f2bfbf46398e3b495f6c70156c', '查看', NULL, 'sys:tsysparameter:view', 2, NULL, 1, NULL, NULL, NULL, '1', '1', '2018-03-28 11:59:41', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('ba561d9fc4d840db83986447c6e7d2fb', 'bcc3290a69c94b1dbeb6e042ffa75780', '修改', NULL, 'site:tevglsitevideo:edit', 2, NULL, 2, NULL, NULL, NULL, '1', '1', '2021-09-11 17:51:30', '1', '2021-09-11 17:52:10', NULL);
INSERT INTO `t_sys_resource` VALUES ('bb3b180c44224f219235455278aa1184', '371d98578b7c4be1867aa6b5e2d67031', '删除', NULL, 'tch:tevgltchclass:remove', 2, NULL, 3, NULL, NULL, NULL, '1', '1', '2019-08-19 17:49:32', '1', '2019-08-19 17:49:49', NULL);
INSERT INTO `t_sys_resource` VALUES ('bcc3290a69c94b1dbeb6e042ffa75780', 'e0904dc937b2446ab4e4f04512c00070', '视频管理', '/site/tevglsitevideo', 'site:tevglsitevideo:query', 1, 'fa fa-youtube-play', 7, NULL, 0, NULL, '1', '1', '2021-09-11 17:51:25', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('bfe52d8339744e6caa8211c6f6fbdcce', '5bb687aa97dd4084871bdc700789b5c4', '首页', 'https://www.budaos.com', '', 4, 'fa fa-snowflake-o', 1, '0b25462a631e4ccf93dddc2b58037738', NULL, NULL, '1', '1', '2019-07-18 09:15:42', '1', '2019-08-31 09:45:59', NULL);
INSERT INTO `t_sys_resource` VALUES ('c0a8e83d390d46ea94fc6c52b01c18ff', 'f9e8bed5cf6a485db552e0602741564c', '删除', NULL, 'sys:tsysdict:remove', 2, NULL, 4, NULL, NULL, NULL, '1', '1', '2018-03-28 11:58:38', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('c2ec049934cf4f7ca38bf752dedb33cf', '0e0a4e29e34b4646a66d22cd5b876713', '查看', NULL, 'site:tevglsiteavd:view', 2, NULL, 4, NULL, NULL, NULL, '1', '1', '2019-07-15 10:59:24', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('c41d8e6bdca6459bbcb704d242383e1b', 'f9e8bed5cf6a485db552e0602741564c', '新增', NULL, 'sys:tsysdict:add', 2, NULL, 2, NULL, NULL, NULL, '1', '1', '2018-03-28 11:58:38', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('c44ba41ac85248b893ff76f1d210bb52', '89bbe647e4a34ae98df0e31dfdff48d0', '查看', NULL, 'site:tevglsiteupdatelog:view', 2, NULL, 4, NULL, NULL, NULL, '1', '1', '2020-09-01 09:30:11', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('c7b1b3c928fc4b0f95739be0a940d0d6', '371d98578b7c4be1867aa6b5e2d67031', '查看', NULL, 'tch:tevgltchclass:view', 2, NULL, 4, NULL, NULL, NULL, '1', '1', '2019-08-19 17:49:32', '1', '2019-08-19 17:49:55', NULL);
INSERT INTO `t_sys_resource` VALUES ('cb09c1957521474dae30b4768b74bc8e', 'dfbb34201ac74e48b24585de9c5cc9da', '广告轮播', '/medu/tmedumediaavd', 'medu:tmedumediaavd:list,medu:tmedumediaavd:query', 1, 'fa fa-envira', 1, NULL, 0, NULL, '1', '1', '2020-09-02 11:55:23', '1', '2021-12-30 11:37:58', NULL);
INSERT INTO `t_sys_resource` VALUES ('cbd6cf9b9b634f4b986bfbe05ae9591c', 'cb09c1957521474dae30b4768b74bc8e', '查看', NULL, 'medu:tmedumediaavd:view', 2, NULL, 4, NULL, NULL, NULL, '1', '1', '2020-09-02 11:55:30', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('cc3e5f98fb9d4c83bbb29332f4b0d85e', '19c786f2bfbf46398e3b495f6c7014b1', '安全管理', '#1', '', 0, 'el-icon-setting', 3, '', NULL, NULL, '0', '1', NULL, '1', '2020-10-11 20:40:37', NULL);
INSERT INTO `t_sys_resource` VALUES ('cc96adff96a34790892d2555b71ed8fd', '82c786f2bfbf46398e3b495f6c70156c', '子系统', NULL, 'sys:menu:subsystem', 2, NULL, 2, NULL, 0, NULL, '1', '1', '2019-06-19 16:37:24', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('cfea91bab6b34fb396c0e37486f35bbd', 'b43e1950c19d40deb8b9b701ffec5f65', '友情社区', '/site/Tevglforumfriend', 'forum:tevglforumfriend:query', 1, 'fa fa-bars', 5, NULL, 0, NULL, '1', '1', '2021-03-08 14:39:55', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('d552fe2536d64166b69b29705e3bf6b8', 'e0904dc937b2446ab4e4f04512c00070', '教师管理', '/res/tevgltchteacher', 'tch:tevgltchteacher:query', 1, 'fa fa-graduation-cap', 2, NULL, 0, NULL, '1', '1', '2019-07-22 15:45:41', '1', '2020-10-15 09:07:03', NULL);
INSERT INTO `t_sys_resource` VALUES ('d96c5e75c14c41acbf6c9ff0a41112d1', 'bcc3290a69c94b1dbeb6e042ffa75780', '删除', NULL, 'site:tevglsitevideo:delete', 2, NULL, 3, NULL, NULL, NULL, '1', '1', '2021-09-11 17:51:31', '1', '2021-09-11 17:52:22', NULL);
INSERT INTO `t_sys_resource` VALUES ('dbafab4c31d3463b8dc2c26ee91edeb3', '99c786f2bfbf46399e3b495f6c7014bc', '查看', NULL, 'sys:tsysorg:view', 2, NULL, 1, NULL, NULL, NULL, '1', '1', '2018-03-28 11:11:04', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('dc86ce2ecef34cbea905ad78305e9e10', '1', '岗位管理', '/sys/post', 'sys:tsyspost:query', 1, 'fa fa-id-badge', 4, NULL, 0, NULL, '1', '1', '2019-06-03 16:23:09', '1', '2019-06-19 10:33:41', NULL);
INSERT INTO `t_sys_resource` VALUES ('dc8edd650b9f41a3a4708db33e3892fb', '0e0a4e29e34b4646a66d22cd5b876713', '新增', NULL, 'site:tevglsiteavd:add', 2, NULL, 1, NULL, NULL, NULL, '1', '1', '2019-07-15 10:59:24', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('ddef9f1960a7416390727e4a717cae13', '371d98578b7c4be1867aa6b5e2d67031', '修改', NULL, 'tch:tevgltchclass:edit', 2, NULL, 2, NULL, NULL, NULL, '1', '1', '2019-08-19 17:49:32', '1', '2019-08-19 17:49:45', NULL);
INSERT INTO `t_sys_resource` VALUES ('dfbb34201ac74e48b24585de9c5cc9da', '19c786f2bfbf46398e3b495f6c7014b1', '小程序管理', NULL, NULL, 0, 'fa fa-weixin', 6, '', 0, NULL, '1', '1', '2020-09-02 11:54:05', '1', '2020-10-15 09:10:31', NULL);
INSERT INTO `t_sys_resource` VALUES ('e0904dc937b2446ab4e4f04512c00070', '19c786f2bfbf46398e3b495f6c7014b1', '教务管理', NULL, 'f', 0, 'fa fa-database', 5, NULL, 0, NULL, '0', '1', '2019-07-22 15:41:19', '1', '2020-10-15 09:09:35', NULL);
INSERT INTO `t_sys_resource` VALUES ('e517523468cf41bab3d77b7aa3fa4065', '5bb687aa97dd4084871bdc700789b5c4', '新闻公告', '', '', 4, 'fa fa-superpowers', 7, '', NULL, NULL, '0', '1', '2019-07-20 14:19:20', '1', '2019-08-08 16:47:17', NULL);
INSERT INTO `t_sys_resource` VALUES ('e660d3450c594c8c93a64d55c6914e7c', '93d786f2bfbf46398e3b495f6c70156c', '分配用户', 'sys/role/setUser', 'sys:role:setUser,sys:role:save,sys:tsysuserinfo:query,sys:tsysuserinfo:list', 2, '', 5, '', NULL, NULL, '1', '1', '2018-03-28 11:38:58', '1', '2018-03-28 11:55:19', NULL);
INSERT INTO `t_sys_resource` VALUES ('e790b82b969a47a1b053551d0735ce43', 'cfea91bab6b34fb396c0e37486f35bbd', '查看', NULL, 'forum:tevglforumfriend:view', 2, NULL, 4, NULL, NULL, NULL, '1', '1', '2021-03-08 14:40:01', '1', '2021-03-08 14:40:35', NULL);
INSERT INTO `t_sys_resource` VALUES ('e81599e7d0644d39ae62fc0950dd5e7d', 'cfea91bab6b34fb396c0e37486f35bbd', '删除分类', NULL, 'forum:tevglforumfriendtype:delete', 2, NULL, 6, NULL, 0, NULL, '1', '1', '2021-04-15 15:04:46', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('ecd2af61fc2f49588a16f0512ae49d5e', 'cfea91bab6b34fb396c0e37486f35bbd', '删除', NULL, 'forum:tevglforumfriend:delete', 2, NULL, 3, NULL, NULL, NULL, '1', '1', '2021-03-08 14:40:01', '1', '2021-03-08 14:40:29', NULL);
INSERT INTO `t_sys_resource` VALUES ('ed9d0544f6ac488aa1d65bf9126a03c2', 'f9e8bed5cf6a485db552e0602741564c', '修改', NULL, 'sys:tsysdict:edit', 2, NULL, 3, NULL, NULL, NULL, '1', '1', '2018-03-28 11:58:38', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('f03b877ac9814f99b3a371ee35ab3ec9', '-1', '智能客服系统', NULL, NULL, -1, 'fa  fa-phone fa-lg fa-spin', 3, '', 0, NULL, '1', '1', '2019-06-18 09:44:54', '1', '2019-06-19 17:26:29', NULL);
INSERT INTO `t_sys_resource` VALUES ('f221b6aa4a69447084dc390c4d85511c', '92c786f2bfbf46398e3b495f6c70156c', '分配角色', 'sys/tsysuserinfo/to_grantrole', 'sys:tsysuserinfo:role', 2, '', 5, '', NULL, NULL, '1', '1', '2018-03-28 11:22:27', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('f3ee4494e48a47c9ac0eda896759d32e', '26715d0dc99f4c92b61d97b9bc074312', '查看', NULL, 'site:tevglsiteresourceext:view', 2, NULL, 4, NULL, NULL, NULL, '1', '1', '2019-07-16 08:57:25', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('f4fd46dd59be413ab38b8f94f8af340c', '0e0a4e29e34b4646a66d22cd5b876713', '删除', NULL, 'site:tevglsiteavd:remove', 2, NULL, 3, NULL, NULL, NULL, '1', '1', '2019-07-15 10:59:24', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('f6abfd7d572f46ddac72dbbd853360d7', '26715d0dc99f4c92b61d97b9bc074312', '删除', NULL, 'site:tevglsiteresourceext:remove', 2, NULL, 3, NULL, NULL, NULL, '1', '1', '2019-07-16 08:57:25', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('f9e8bed5cf6a485db552e0602741564c', '1', '数据字典', '/sys/dict', 'sys:tsysdict:query', 1, 'fa fa-database', 5, '', NULL, NULL, '1', '1', NULL, '1', '2019-06-19 10:33:53', NULL);
INSERT INTO `t_sys_resource` VALUES ('f9e8bed5cf6a485db552e0602741845c', 'cc3e5f98fb9d4c83bbb29332f4b0d85e', '附件管理', '/sys/attach', 'sys:tsysattach:query', 1, 'el-icon-paperclip', 5, '', NULL, NULL, '1', '1', NULL, '1', '2019-06-01 08:40:11', NULL);
INSERT INTO `t_sys_resource` VALUES ('f9e8bed5cf6a485db552e0602741894c', 'cc3e5f98fb9d4c83bbb29332f4b0d85e', '登录日志', '/sys/loginLog', 'sys:tsysloginlog:query,sys:tsysloginlog:remove', 1, 'el-icon-user-solid', 1, '', NULL, NULL, '1', '1', NULL, '1', '2019-06-01 08:52:48', NULL);
INSERT INTO `t_sys_resource` VALUES ('fb105e3a917a4cb39ba42717e82c3045', '5bb687aa97dd4084871bdc700789b5c4', '我要合作', 'javascript:alert(\'敬请期待\')', '', 4, 'fa fa-envelope-open-o', 7, '0b25462a631e4ccf93dddc2b58037738', NULL, NULL, '1', '1', '2019-07-18 09:18:21', '1', '2020-09-28 17:25:35', NULL);
INSERT INTO `t_sys_resource` VALUES ('fb7ddf7af18e4d639266d74a90540cd3', 'dc86ce2ecef34cbea905ad78305e9e10', '修改', NULL, 'sys:post:edit,sys:tsyspost:edit', 2, NULL, 3, NULL, NULL, NULL, '1', '1', '2019-06-12 14:51:52', '1', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES ('ff6e7475f8a54cf28c4c8aa8a7bc05da', 'cfea91bab6b34fb396c0e37486f35bbd', '修改', NULL, 'forum:tevglforumfriend:edit', 2, NULL, 2, NULL, NULL, NULL, '1', '1', '2021-03-08 14:40:00', '1', '2021-03-08 14:40:23', NULL);

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
INSERT INTO `t_sys_role` VALUES ('13e23e09fb8b4fffa7099d6d558af21c', '长理教务', '', '1', '2022-09-28 09:16:37', '1', '2022-09-28 09:42:19', NULL, '8e5043e881534abca617043ce08b17f5', '3', '1');
INSERT INTO `t_sys_role` VALUES ('9a8ef5b24c1c4026a5b986a64d073fa9', '网站维护', '', '1', '2022-09-28 09:17:12', '1', '2022-09-28 09:42:23', NULL, '845786f2bfbf46398e3b495f6c7014bc', '1', '1');
INSERT INTO `t_sys_role` VALUES ('a2ee62cc30ae411c826227cc15b1e235', '游客', '', '1', '2022-09-22 09:40:27', '1', '2022-09-28 09:42:15', NULL, '845786f2bfbf46398e3b495f6c7014bc', '1', '1');
INSERT INTO `t_sys_role` VALUES ('b1dbc8db116d42538d67e80329b471f0', '开发', '', '1', '2022-09-13 10:57:49', '1', '2022-09-28 09:42:06', NULL, '845786f2bfbf46398e3b495f6c7014bc', '1', '1');

-- ----------------------------
-- Records of t_sys_roleprivilege
-- ----------------------------
INSERT INTO `t_sys_roleprivilege` VALUES ('0024020756f546e5a8fc2de95ddb4fb1', '13e23e09fb8b4fffa7099d6d558af21c', 'dfbb34201ac74e48b24585de9c5cc9da');
INSERT INTO `t_sys_roleprivilege` VALUES ('0269aa24815c4210815c9e7a2d852084', '9a8ef5b24c1c4026a5b986a64d073fa9', '04f3562771a744bdb70511f6ca63e1a7');
INSERT INTO `t_sys_roleprivilege` VALUES ('0514580ad2424cff8b6b1a98e50d24c8', '9a8ef5b24c1c4026a5b986a64d073fa9', '741e47570019484ab7498e5515bb817c');
INSERT INTO `t_sys_roleprivilege` VALUES ('0e563feffad34791aa1936421093652f', '13e23e09fb8b4fffa7099d6d558af21c', '7c140a2910214d449c891b88991f0850');
INSERT INTO `t_sys_roleprivilege` VALUES ('0f7e7e040843407f9c4ad841703f38f6', '9a8ef5b24c1c4026a5b986a64d073fa9', '44729d3a6b7841cfa6fc4fbaa6bf0c03');
INSERT INTO `t_sys_roleprivilege` VALUES ('12d2b1c25804479384a8f0110d1fca80', '13e23e09fb8b4fffa7099d6d558af21c', '3f1bfc13843b43efb6ec6ee97eb0b06f');
INSERT INTO `t_sys_roleprivilege` VALUES ('12fa8c0386444356b8a6e708c0b767b9', '9a8ef5b24c1c4026a5b986a64d073fa9', '0dd44379d5224cd994f51cfcdb613027');
INSERT INTO `t_sys_roleprivilege` VALUES ('1b035c2da6864408bf70edd827634eee', '9a8ef5b24c1c4026a5b986a64d073fa9', 'c2ec049934cf4f7ca38bf752dedb33cf');
INSERT INTO `t_sys_roleprivilege` VALUES ('2556b7ff75af44a298ce1184ed372a81', '13e23e09fb8b4fffa7099d6d558af21c', 'e0904dc937b2446ab4e4f04512c00070');
INSERT INTO `t_sys_roleprivilege` VALUES ('2a9279a6240d478cbd33b1f942a3a880', '9a8ef5b24c1c4026a5b986a64d073fa9', '9243558bee9548bcb08ca596c37e8a5b');
INSERT INTO `t_sys_roleprivilege` VALUES ('2c641f27fe5a45dd8011fca4973fa32a', '9a8ef5b24c1c4026a5b986a64d073fa9', 'b43e1950c19d40deb8b9b701ffec5f65');
INSERT INTO `t_sys_roleprivilege` VALUES ('300cd9d68f5542a995edf6a16d1d5344', '13e23e09fb8b4fffa7099d6d558af21c', 'cb09c1957521474dae30b4768b74bc8e');
INSERT INTO `t_sys_roleprivilege` VALUES ('369ef14f86a54c8b9611ce0a35c7cd58', 'b1dbc8db116d42538d67e80329b471f0', 'dc86ce2ecef34cbea905ad78305e9e10');
INSERT INTO `t_sys_roleprivilege` VALUES ('37149a23bc5347c6988baf269e718432', '9a8ef5b24c1c4026a5b986a64d073fa9', 'cfea91bab6b34fb396c0e37486f35bbd');
INSERT INTO `t_sys_roleprivilege` VALUES ('3b838f8e417640f887b607f74be6b0e0', '13e23e09fb8b4fffa7099d6d558af21c', 'd552fe2536d64166b69b29705e3bf6b8');
INSERT INTO `t_sys_roleprivilege` VALUES ('3fa98ef0e5034facbf844139dbc5aef1', '9a8ef5b24c1c4026a5b986a64d073fa9', 'f3ee4494e48a47c9ac0eda896759d32e');
INSERT INTO `t_sys_roleprivilege` VALUES ('440d3da14a504e4eb5f337c4ff7cbca8', '9a8ef5b24c1c4026a5b986a64d073fa9', 'f4fd46dd59be413ab38b8f94f8af340c');
INSERT INTO `t_sys_roleprivilege` VALUES ('47e375f5ec524e97af9e75a5ac7d6e4c', '13e23e09fb8b4fffa7099d6d558af21c', '9d980791b9fc4d76805df6450a3602a1');
INSERT INTO `t_sys_roleprivilege` VALUES ('47e4bc4f09684a0e8c0572bdde0bc0f9', '13e23e09fb8b4fffa7099d6d558af21c', '3409b6acc15d46caad2feb7348c77693');
INSERT INTO `t_sys_roleprivilege` VALUES ('4a9575c788f24f8b8d446c6155b1a9f5', '9a8ef5b24c1c4026a5b986a64d073fa9', '5a9d2b6fa08840ce8951304416e6bbce');
INSERT INTO `t_sys_roleprivilege` VALUES ('4d8a3c038fe1430fae1d64147f7657e4', '13e23e09fb8b4fffa7099d6d558af21c', 'c7b1b3c928fc4b0f95739be0a940d0d6');
INSERT INTO `t_sys_roleprivilege` VALUES ('529ad657075249619e534f5213aea128', '13e23e09fb8b4fffa7099d6d558af21c', 'b4e13f4ee6b0466ba37bd13f5420e6ba');
INSERT INTO `t_sys_roleprivilege` VALUES ('56537d4213384e608cad7574a44a432d', '9a8ef5b24c1c4026a5b986a64d073fa9', 'ff6e7475f8a54cf28c4c8aa8a7bc05da');
INSERT INTO `t_sys_roleprivilege` VALUES ('568fed8b81864a1eaa8e8dea4d8e2e16', '13e23e09fb8b4fffa7099d6d558af21c', '46cb1e473c064d0c817d7093fb1c6fa8');
INSERT INTO `t_sys_roleprivilege` VALUES ('5931747d58074ef8a3f9f88e31681172', 'b1dbc8db116d42538d67e80329b471f0', '1');
INSERT INTO `t_sys_roleprivilege` VALUES ('5f4283a3cb4a434ab3380028e956d990', '13e23e09fb8b4fffa7099d6d558af21c', 'bcc3290a69c94b1dbeb6e042ffa75780');
INSERT INTO `t_sys_roleprivilege` VALUES ('6140ee2ac04e4dbbb073c53592a1cfbe', '13e23e09fb8b4fffa7099d6d558af21c', 'c44ba41ac85248b893ff76f1d210bb52');
INSERT INTO `t_sys_roleprivilege` VALUES ('62e0433c33ff4b78b1e5c42dc3f6ab26', 'a2ee62cc30ae411c826227cc15b1e235', '24e2dd79b05046de99da63abb8a3ba39');
INSERT INTO `t_sys_roleprivilege` VALUES ('6306fdc132a841f3ae70c5f52e60d478', '13e23e09fb8b4fffa7099d6d558af21c', 'bb3b180c44224f219235455278aa1184');
INSERT INTO `t_sys_roleprivilege` VALUES ('6e1792e95588481d8229d31707a028aa', '13e23e09fb8b4fffa7099d6d558af21c', '5b53877cef0f4eeaadd7f96dc87273ae');
INSERT INTO `t_sys_roleprivilege` VALUES ('7bde8605b88445ccbbd298dc02b81217', '13e23e09fb8b4fffa7099d6d558af21c', '11bc5f0d88e94f1d817aa535892b2530');
INSERT INTO `t_sys_roleprivilege` VALUES ('842bf46c3b34413190acae728a028156', '9a8ef5b24c1c4026a5b986a64d073fa9', '0e0a4e29e34b4646a66d22cd5b876713');
INSERT INTO `t_sys_roleprivilege` VALUES ('8597b8dceec2411cabaebe95bf91431b', 'a2ee62cc30ae411c826227cc15b1e235', 'cc3e5f98fb9d4c83bbb29332f4b0d85e');
INSERT INTO `t_sys_roleprivilege` VALUES ('868fcb367bc84cb1bdc33fb620f2381f', 'a2ee62cc30ae411c826227cc15b1e235', 'f9e8bed5cf6a485db552e0602741894c');
INSERT INTO `t_sys_roleprivilege` VALUES ('8e38ef8ef15740048ad3b03b52e61a15', '13e23e09fb8b4fffa7099d6d558af21c', 'cbd6cf9b9b634f4b986bfbe05ae9591c');
INSERT INTO `t_sys_roleprivilege` VALUES ('901c3965292a42c1ae6bd95170bc1020', '9a8ef5b24c1c4026a5b986a64d073fa9', 'ecd2af61fc2f49588a16f0512ae49d5e');
INSERT INTO `t_sys_roleprivilege` VALUES ('911cc8fe89a747028a54d3a2563fbf70', '13e23e09fb8b4fffa7099d6d558af21c', '7231e9cc583840bc85a432616b9061b9');
INSERT INTO `t_sys_roleprivilege` VALUES ('95ddae027c644170a5009e1b7a151b01', '13e23e09fb8b4fffa7099d6d558af21c', 'd96c5e75c14c41acbf6c9ff0a41112d1');
INSERT INTO `t_sys_roleprivilege` VALUES ('98d6f3a108db46788cdaa197bde05c55', '9a8ef5b24c1c4026a5b986a64d073fa9', 'e790b82b969a47a1b053551d0735ce43');
INSERT INTO `t_sys_roleprivilege` VALUES ('9a6fb5529a4f4422a62e7ac296d461eb', '9a8ef5b24c1c4026a5b986a64d073fa9', '05c93a7893754c279d7549e1e8c6270b');
INSERT INTO `t_sys_roleprivilege` VALUES ('9ae790a852ab4a3cae0bcddcf34163d2', '13e23e09fb8b4fffa7099d6d558af21c', 'ac227164c3304bf0a4f03c5cbd71e4ba');
INSERT INTO `t_sys_roleprivilege` VALUES ('9ce005e3989e4e67a05b772cd89e129c', '13e23e09fb8b4fffa7099d6d558af21c', '538ecb94406f46f4862748f0c4ed847c');
INSERT INTO `t_sys_roleprivilege` VALUES ('a0d8734fe2a54e8895b1a3fb4620afc3', '9a8ef5b24c1c4026a5b986a64d073fa9', 'e81599e7d0644d39ae62fc0950dd5e7d');
INSERT INTO `t_sys_roleprivilege` VALUES ('a50b1e9f400b4bc58d240b33519e2d1c', '13e23e09fb8b4fffa7099d6d558af21c', 'ba561d9fc4d840db83986447c6e7d2fb');
INSERT INTO `t_sys_roleprivilege` VALUES ('a7bb8c7088864787b5d17e42a9f62cd6', '13e23e09fb8b4fffa7099d6d558af21c', 'a2112c6792aa45ba8855dc8acbc88b4a');
INSERT INTO `t_sys_roleprivilege` VALUES ('addd1977be8b454c88c1e0f72c17cc7e', '13e23e09fb8b4fffa7099d6d558af21c', '05e3f0068784445582b521efd708e85d');
INSERT INTO `t_sys_roleprivilege` VALUES ('b5cb2be2b6c240f7b55bbc121a147f28', '9a8ef5b24c1c4026a5b986a64d073fa9', '2716eca246ee42bfb19b129ccdf569b6');
INSERT INTO `t_sys_roleprivilege` VALUES ('b618ba67dd7f4a1bbb805302c8ab8f56', '13e23e09fb8b4fffa7099d6d558af21c', 'ddef9f1960a7416390727e4a717cae13');
INSERT INTO `t_sys_roleprivilege` VALUES ('b8bbd2d33fc148b18ff38eee92ade936', 'b1dbc8db116d42538d67e80329b471f0', '19c786f2bfbf46398e3b495f6c7014b1');
INSERT INTO `t_sys_roleprivilege` VALUES ('bd57a2131ca44e17a725d045f6ff0ef5', '13e23e09fb8b4fffa7099d6d558af21c', '2b64ea278d23403f8b30d4405c2d9673');
INSERT INTO `t_sys_roleprivilege` VALUES ('bf20cfc57a384029bf11b0c04aeea3f1', '9a8ef5b24c1c4026a5b986a64d073fa9', '19c786f2bfbf46398e3b495f6c7014b1');
INSERT INTO `t_sys_roleprivilege` VALUES ('c1d4e228c9ca405aaa1d8166dc99d81c', 'a2ee62cc30ae411c826227cc15b1e235', '19c786f2bfbf46398e3b495f6c7014b1');
INSERT INTO `t_sys_roleprivilege` VALUES ('c7814a87c5ef41dc9f893ae9131c5643', '9a8ef5b24c1c4026a5b986a64d073fa9', '60896726a3024aedad02b405677c20f5');
INSERT INTO `t_sys_roleprivilege` VALUES ('c86e37e55df344408a21fced76b0854d', '13e23e09fb8b4fffa7099d6d558af21c', '5752544b0992428184173fa0e6e1056b');
INSERT INTO `t_sys_roleprivilege` VALUES ('d09a87acc7f54f3bb10581fcbb7f5b7f', '13e23e09fb8b4fffa7099d6d558af21c', '93d2420cda4042a0a4efef67a07b7f1f');
INSERT INTO `t_sys_roleprivilege` VALUES ('d0f529c755b64bcd9eabd9163c369586', '13e23e09fb8b4fffa7099d6d558af21c', '4928266986db47dbbe11380b6455010a');
INSERT INTO `t_sys_roleprivilege` VALUES ('d7c2336af06f459199599c3b109bdf91', '13e23e09fb8b4fffa7099d6d558af21c', '19c786f2bfbf46398e3b495f6c7014b1');
INSERT INTO `t_sys_roleprivilege` VALUES ('d981eb216d96425ca1cd9a0a550be141', '13e23e09fb8b4fffa7099d6d558af21c', '371d98578b7c4be1867aa6b5e2d67031');
INSERT INTO `t_sys_roleprivilege` VALUES ('d9f0f7d244e1479ba790258778035876', '13e23e09fb8b4fffa7099d6d558af21c', '4bee5249bda9446f9a6364d2c2fe485e');
INSERT INTO `t_sys_roleprivilege` VALUES ('dabdbe35ec814fc4911af6eafc089f95', '13e23e09fb8b4fffa7099d6d558af21c', '89bbe647e4a34ae98df0e31dfdff48d0');
INSERT INTO `t_sys_roleprivilege` VALUES ('db9514a7f2884e648d1ee914b34bc799', '9a8ef5b24c1c4026a5b986a64d073fa9', 'dc8edd650b9f41a3a4708db33e3892fb');
INSERT INTO `t_sys_roleprivilege` VALUES ('df93837de2a945e68593704541782b5c', '13e23e09fb8b4fffa7099d6d558af21c', '7f27e168a219489b97c9bb50c59b562f');
INSERT INTO `t_sys_roleprivilege` VALUES ('e0cac9dbd6e44539a991b4ad5af23fad', '13e23e09fb8b4fffa7099d6d558af21c', '7e83b9c3d46c47aa96c233bc4bb6ff3d');
INSERT INTO `t_sys_roleprivilege` VALUES ('e544b5913e1e423ab81bc2ba932a1ccd', '9a8ef5b24c1c4026a5b986a64d073fa9', 'f6abfd7d572f46ddac72dbbd853360d7');
INSERT INTO `t_sys_roleprivilege` VALUES ('e6bd7723a8624fcc907e73b0f0f5c0e6', '13e23e09fb8b4fffa7099d6d558af21c', '29ce103136694ccdaf437d160a939aaa');
INSERT INTO `t_sys_roleprivilege` VALUES ('e893d1b5bb0b4f7b994771e290d04868', '13e23e09fb8b4fffa7099d6d558af21c', '9e9f07dd3ade46619c91bb9ef36167cc');
INSERT INTO `t_sys_roleprivilege` VALUES ('ecab3ec96b3a4fe1a4462f860235ee8c', '9a8ef5b24c1c4026a5b986a64d073fa9', '1cc48ea31aa14f58a3a55b004568aa81');
INSERT INTO `t_sys_roleprivilege` VALUES ('ed023c346c8347879ffe40321e54bc58', 'b1dbc8db116d42538d67e80329b471f0', '771c483b89e248a1adb261777ebd9d98');
INSERT INTO `t_sys_roleprivilege` VALUES ('ee5add517296492f81d4410a109e89f4', '13e23e09fb8b4fffa7099d6d558af21c', '062e19317bf04d0dbb4c21a228de690a');
INSERT INTO `t_sys_roleprivilege` VALUES ('f627af5dd1b3405aa8cb0ea6ea99ed82', '9a8ef5b24c1c4026a5b986a64d073fa9', '26715d0dc99f4c92b61d97b9bc074312');
INSERT INTO `t_sys_roleprivilege` VALUES ('f95f7d2c5f044de883abe6c0742fc803', '9a8ef5b24c1c4026a5b986a64d073fa9', '4f878d16bad64263870afb77132ce93c');
INSERT INTO `t_sys_roleprivilege` VALUES ('fd7d5aed9da14f92bc65ba139164d945', '13e23e09fb8b4fffa7099d6d558af21c', '180108b6b3a044c9bfb0123fdf60ab88');
INSERT INTO `t_sys_roleprivilege` VALUES ('fd9ff98b8458474087ce180ad1c47632', '13e23e09fb8b4fffa7099d6d558af21c', '415052f1f6344a37af12a2a3d024b1a9');
INSERT INTO `t_sys_roleprivilege` VALUES ('fee3c9a188f74d1aa7926e0a9a368ea1', '13e23e09fb8b4fffa7099d6d558af21c', 'aa1873b519bb406e851a181e7d826153');

-- ----------------------------
-- Records of t_sys_settings
-- ----------------------------
INSERT INTO `t_sys_settings` VALUES ('23ec7635acdc438592171e0a5f444aaf', 'sys', 'logoTitle', '图标标题', '教学实训云平台', NULL, NULL, '1', '1', NULL, '1', '2022-09-28 09:04:58', NULL);
INSERT INTO `t_sys_settings` VALUES ('23ec7635acdc438592171e0a5f44af', 'sys', 'companyInfo', '公司信息', '开源吧', NULL, NULL, '1', '1', NULL, '1', '2022-09-28 09:02:19', NULL);
INSERT INTO `t_sys_settings` VALUES ('23ec7635acdc438592171e0a5fccaf', 'sys', 'cbLogo', '创蓝图标', 'c93da23e-e226-487b-b984-ac33a4b6f539.png', NULL, NULL, '1', '1', NULL, '1', '2022-09-28 09:06:49', NULL);
INSERT INTO `t_sys_settings` VALUES ('23ec7635acdc438592171e0a5fyyaf', 'sys', 'contactInfo', '联系电话', '0731', NULL, NULL, '1', '1', NULL, '1', '2022-09-28 09:05:42', NULL);
INSERT INTO `t_sys_settings` VALUES ('23ec7635acdc438592171e0abnfccaf', 'sys', 'loginBgImg', '登录背景图', NULL, NULL, NULL, '1', '1', NULL, '1', '2019-06-28 17:38:27', NULL);
INSERT INTO `t_sys_settings` VALUES ('wechat-official-account', 'official', 'weChatOfficialAccountAccessToken', '创蓝微信公众号AccessToken', '54_76jFh4JC9d3VaSJT4icKocTZh0MhlLiM_v0ZQCcVv7OH6DN1H2F2FEIB-Q4YKT9HFGk3OWaZDW4e7VklKtvEFSnPVM1aHJupoLZNA6BSysF6qaZp9vGeXbZibpStBACql2Idhn9ZJZkpkVTBVQRgAIAQLM', NULL, NULL, NULL, NULL, '2022-01-18 17:30:00', NULL, '2022-03-11 17:00:00', NULL);
INSERT INTO `t_sys_settings` VALUES ('wxaccesstoken', 'wx', 'wxAccessToken', '布道师小程序AccessToken', '58_McJThGTHdSDtYwcMxG0Rf8MNjA5_F5fZ-OKDstalyRsZW7jPod2wjXGUdSzUqiX_PB6Lv7Hkw2qWDLy6ExgPyd2LIYyMTj5lIzH_2-b0MnAF4A5p1DeM1z8Uh8lNMX1ONfzEVbNld8ExDgvaJPXiAEAZFN', NULL, NULL, '1', NULL, '2020-08-03 10:43:01', '3338de437ceb492fa253e7d50bbce107', '2022-07-16 16:00:11', NULL);

-- ----------------------------
-- Records of t_sys_userinfo
-- ----------------------------
INSERT INTO `t_sys_userinfo` VALUES ('1', 'admin', '0163b9a73b7022c99590ae5d9d33c735', '', 'zhujianwu@creatorblue.com', '18229923839', 1, '1', '2016-11-11 11:11:11', '', NULL, 1, '1', NULL, '1', '系统管理员', '', '430223198811111111', NULL, '账号密码 admin/admin', '汉族', '1', '2022-09-28 09:13:14');
INSERT INTO `t_sys_userinfo` VALUES ('a52ca4e97d2d46129674051ae6fb6c3d', 'zhangs', '078ca34b57023f961dded6d5fa484b80', NULL, '', '15200530003', 1, '1', '2022-09-28 09:57:12', '', '', 3, '0', NULL, '1', '张三', 'black', '', '', '', '', '1', '2022-09-28 10:02:10');
INSERT INTO `t_sys_userinfo` VALUES ('b3d705d7a388413aaa8a5669e0039481', 'liuy', '078ca34b57023f961dded6d5fa484b80', NULL, '', '15200530001', 1, '1', '2022-09-28 09:38:20', '', '', 1, '0', NULL, '0', '刘一', 'black', '', '', '', '', '1', '2022-09-28 10:02:02');
INSERT INTO `t_sys_userinfo` VALUES ('fc2c324bff174045a1209c5675656eb4', 'chene', '078ca34b57023f961dded6d5fa484b80', NULL, '', '15200530002', 1, '1', '2022-09-28 09:42:55', '', '', 2, '0', NULL, '0', '陈二', 'black', '', '', '', '', '1', '2022-09-28 10:02:06');

SET FOREIGN_KEY_CHECKS = 1;
