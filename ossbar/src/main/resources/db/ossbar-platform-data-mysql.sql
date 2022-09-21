SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token`  (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL,
  `refresh_token` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(0) NULL DEFAULT NULL,
  `refresh_token_validity` int(0) NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES (''creatorblue-platform-core'', ''cb-platform-core'', ''{noop}s9sd8uf9ewj9egwejgewgj0'', ''all'', ''password,authorization_code,refresh_token,implicit,client_credentials'', ''http://localhost:9084/console/login,http://www.baidu.com/console/login'', ''ROLE_USER13'', NULL, NULL, NULL, ''true'');
INSERT INTO `oauth_client_details` VALUES (''creatorblue-platform-core1'', ''cb-platform-core'', ''{noop}s9sd8uf9ewj9egwejgewgj01'', ''all'', ''password,authorization_code,refresh_token,implicit,client_credentials'', ''http://localhost:9084/console/login,http://www.baidu.com/console/login'', ''ROLE_USER13'', NULL, NULL, NULL, ''true'');

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token`  (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication` blob NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `BLOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES (''CreatorblueScheduler'', ''TASK_0293303f0cd34877b75576edcf45e4f6'', ''DEFAULT'', ''0 0/30 * * * ?'', ''Asia/Shanghai'');
INSERT INTO `qrtz_cron_triggers` VALUES (''CreatorblueScheduler'', ''TASK_c5e92c817aa144019860c1b64e8ae301'', ''DEFAULT'', ''*/5 * * * * ?'', ''Asia/Shanghai'');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `FIRED_TIME` bigint(0) NOT NULL,
  `SCHED_TIME` bigint(0) NOT NULL,
  `PRIORITY` int(0) NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TRIG_INST_NAME`(`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY`(`SCHED_NAME`, `INSTANCE_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_FT_J_G`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_T_G`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TG`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_J_REQ_RECOVERY`(`SCHED_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_J_GRP`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES (''CreatorblueScheduler'', ''TASK_0293303f0cd34877b75576edcf45e4f6'', ''DEFAULT'', NULL, ''com.ossbar.modules.job.utils.ScheduleJob'', ''0'', ''1'', ''0'', ''0'', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597401227B226A6F624964223A223032393333303366306364333438373762373535373665646366343565346636222C226265616E4E616D65223A22746573745461736B222C226D6574686F644E616D65223A2274657374222C22706172616D73223A2263726561746F72626C7565222C2263726F6E45787072657373696F6E223A223020302F3330202A202A202A203F222C22737461747573223A312C2272656D61726B223A22E68891E6ADA3E6B58BE8AF95E4B8AD31222C22637265617465557365724964223A2231222C2263726561746554696D65223A22323031392D30362D32312030383A33373A3435222C22757064617465557365724964223A2231222C2275706461746554696D65223A22323031392D30372D30312030383A32363A3532227D7800);
INSERT INTO `qrtz_job_details` VALUES (''CreatorblueScheduler'', ''TASK_c5e92c817aa144019860c1b64e8ae301'', ''DEFAULT'', NULL, ''com.ossbar.modules.job.utils.ScheduleJob'', ''0'', ''1'', ''0'', ''0'', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597400F97B226A6F624964223A226335653932633831376161313434303139383630633162363465386165333031222C226265616E4E616D65223A22746573745461736B222C226D6574686F644E616D65223A2274657374222C22706172616D73223A22222C2263726F6E45787072657373696F6E223A222A2F35202A202A202A202A203F222C22737461747573223A302C2272656D61726B223A22E6AF8FE5A4A9E6AF8F35E7A792E689A7E8A18CE4B880E6ACA1222C2263726561746554696D65223A22323032322D30392D30362031343A33323A3536222C2275706461746554696D65223A22323032322D30392D30362031343A33323A3536227D7800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES (''CreatorblueScheduler'', ''TRIGGER_ACCESS'');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint(0) NOT NULL,
  `CHECKIN_INTERVAL` bigint(0) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `REPEAT_COUNT` bigint(0) NOT NULL,
  `REPEAT_INTERVAL` bigint(0) NOT NULL,
  `TIMES_TRIGGERED` bigint(0) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `INT_PROP_1` int(0) NULL DEFAULT NULL,
  `INT_PROP_2` int(0) NULL DEFAULT NULL,
  `LONG_PROP_1` bigint(0) NULL DEFAULT NULL,
  `LONG_PROP_2` bigint(0) NULL DEFAULT NULL,
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(0) NULL DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(0) NULL DEFAULT NULL,
  `PRIORITY` int(0) NULL DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `START_TIME` bigint(0) NOT NULL,
  `END_TIME` bigint(0) NULL DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MISFIRE_INSTR` smallint(0) NULL DEFAULT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_J`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_C`(`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_T_G`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_STATE`(`SCHED_NAME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_STATE`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_G_STATE`(`SCHED_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME`(`SCHED_NAME`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST`(`SCHED_NAME`, `TRIGGER_STATE`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES (''CreatorblueScheduler'', ''TASK_0293303f0cd34877b75576edcf45e4f6'', ''DEFAULT'', ''TASK_0293303f0cd34877b75576edcf45e4f6'', ''DEFAULT'', NULL, 1662372000000, -1, 5, ''PAUSED'', ''CRON'', 1662370619000, 0, NULL, 2, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597401227B226A6F624964223A223032393333303366306364333438373762373535373665646366343565346636222C226265616E4E616D65223A22746573745461736B222C226D6574686F644E616D65223A2274657374222C22706172616D73223A2263726561746F72626C7565222C2263726F6E45787072657373696F6E223A223020302F3330202A202A202A203F222C22737461747573223A312C2272656D61726B223A22E68891E6ADA3E6B58BE8AF95E4B8AD31222C22637265617465557365724964223A2231222C2263726561746554696D65223A22323031392D30362D32312030383A33373A3435222C22757064617465557365724964223A2231222C2275706461746554696D65223A22323031392D30372D30312030383A32363A3532227D7800);
INSERT INTO `qrtz_triggers` VALUES (''CreatorblueScheduler'', ''TASK_c5e92c817aa144019860c1b64e8ae301'', ''DEFAULT'', ''TASK_c5e92c817aa144019860c1b64e8ae301'', ''DEFAULT'', NULL, 1662445980000, -1, 5, ''PAUSED'', ''CRON'', 1662445976000, 0, NULL, 2, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597400FA7B226A6F624964223A226335653932633831376161313434303139383630633162363465386165333031222C226265616E4E616D65223A22746573745461736B222C226D6574686F644E616D65223A227465737432222C22706172616D73223A22222C2263726F6E45787072657373696F6E223A222A2F35202A202A202A202A203F222C22737461747573223A312C2272656D61726B223A22E6AF8FE5A4A9E6AF8F35E7A792E689A7E8A18CE4B880E6ACA1222C2263726561746554696D65223A22323032322D30392D30362031343A33323A3536222C2275706461746554696D65223A22323032322D30392D30362031343A35333A3332227D7800);

-- ----------------------------
-- Table structure for t_api_token
-- ----------------------------
DROP TABLE IF EXISTS `t_api_token`;
CREATE TABLE `t_api_token`  (
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''主键'',
  `token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''token编码'',
  `expire_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''过期时间'',
  `update_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''更新时间'',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `token`(`token`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''用户Token'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_api_user
-- ----------------------------
DROP TABLE IF EXISTS `t_api_user`;
CREATE TABLE `t_api_user`  (
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''主键'',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''用户名'',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''手机号'',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''密码'',
  `create_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建时间'',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''用户'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_org_user
-- ----------------------------
DROP TABLE IF EXISTS `t_org_user`;
CREATE TABLE `t_org_user`  (
  `orguser_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''主键'',
  `ORG_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''机构ID'',
  `USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''用户ID'',
  `IS_MAIN` int(0) NOT NULL DEFAULT 0 COMMENT ''是否主机构（0：否，1：是）'',
  PRIMARY KEY (`orguser_id`) USING BTREE,
  INDEX `FK_Reference_11`(`ORG_ID`) USING BTREE,
  INDEX `FK_Reference_12`(`USER_ID`) USING BTREE,
  CONSTRAINT `t_org_user_ibfk_1` FOREIGN KEY (`ORG_ID`) REFERENCES `t_sys_org` (`ORG_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_org_user_ibfk_2` FOREIGN KEY (`USER_ID`) REFERENCES `t_sys_userinfo` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''机构用户关联表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_org_user
-- ----------------------------
INSERT INTO `t_org_user` VALUES (''113636b8044e4c37bd3f913fb00773c0'', ''10865'', NULL, 0);
INSERT INTO `t_org_user` VALUES (''3c7d3ca02ba844d98002f266cd0aed3b'', ''10865'', NULL, 1);
INSERT INTO `t_org_user` VALUES (''4b2b8c2c2131417aa37acb633a171a2d'', ''845786f2bfbf46398e3b495f6c7014bc'', NULL, 1);
INSERT INTO `t_org_user` VALUES (''5f248a4b84b0415a887548da8eec254d'', ''fccade7bb8ac4dcb8c56e5341437c786'', NULL, 0);
INSERT INTO `t_org_user` VALUES (''64cac2ed5aca4cc5b5e2ec48f278d6c7'', ''bec753ae9b4c4d6d856dcaa6e4526b2b'', NULL, 0);
INSERT INTO `t_org_user` VALUES (''b19df25579e54c8883255f284714f285'', ''bec753ae9b4c4d6d856dcaa6e4526b2b'', NULL, 1);
INSERT INTO `t_org_user` VALUES (''b3aac079f9aa4caba4a33f3a8897edc2'', ''fccade7bb8ac4dcb8c56e5341437c786'', NULL, 1);
INSERT INTO `t_org_user` VALUES (''e3b65d26052c417c9abe077464f57406'', ''1881b7c655014abea780d23da0f00ebb'', NULL, 0);

-- ----------------------------
-- Table structure for t_role_post
-- ----------------------------
DROP TABLE IF EXISTS `t_role_post`;
CREATE TABLE `t_role_post`  (
  `ROLE_POSTID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''主键'',
  `POST_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''岗位ID'',
  `ROLE_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''角色ID'',
  PRIMARY KEY (`ROLE_POSTID`) USING BTREE,
  INDEX `FK_Reference_15`(`POST_ID`) USING BTREE,
  INDEX `FK_Reference_16`(`ROLE_ID`) USING BTREE,
  CONSTRAINT `t_role_post_ibfk_1` FOREIGN KEY (`POST_ID`) REFERENCES `t_sys_post` (`POST_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_role_post_ibfk_2` FOREIGN KEY (`ROLE_ID`) REFERENCES `t_sys_role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''岗位角色关联表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `t_schedule_job`;
CREATE TABLE `t_schedule_job`  (
  `job_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ''主键'',
  `bean_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''spring bean名称'',
  `method_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''方法名'',
  `params` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''参数'',
  `cron_expression` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''cron表达式'',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT ''任务状态  0：正常  1：暂停'',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''备注'',
  `create_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''创建时间'',
  `create_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''创建人'',
  `update_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''修改时间'',
  PRIMARY KEY (`job_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = ''定时任务'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_schedule_job
-- ----------------------------
INSERT INTO `t_schedule_job` VALUES (''0293303f0cd34877b75576edcf45e4f6'', ''testTask'', ''test'', ''creatorblue'', ''0 0/30 * * * ?'', 1, ''我正测试中1'', ''2019-06-21 08:37:45'', ''1'', ''1'', ''2019-07-01 08:26:52'');
INSERT INTO `t_schedule_job` VALUES (''c5e92c817aa144019860c1b64e8ae301'', ''testTask'', ''test2'', '''', ''*/5 * * * * ?'', 1, ''每天每5秒执行一次'', ''2022-09-06 14:32:56'', NULL, NULL, ''2022-09-06 14:53:32'');

-- ----------------------------
-- Table structure for t_schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `t_schedule_job_log`;
CREATE TABLE `t_schedule_job_log`  (
  `log_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ''主键'',
  `job_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ''任务id'',
  `bean_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''spring bean名称'',
  `method_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''方法名'',
  `params` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''参数'',
  `status` tinyint(0) NOT NULL COMMENT ''任务状态    0：成功    1：失败'',
  `error` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''失败信息'',
  `times` int(0) NOT NULL COMMENT ''耗时(单位：毫秒)'',
  `create_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''创建时间'',
  `create_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''创建人'',
  `update_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''修改时间'',
  PRIMARY KEY (`log_id`) USING BTREE,
  INDEX `job_id`(`job_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = ''定时任务日志'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_schedule_job_log
-- ----------------------------
INSERT INTO `t_schedule_job_log` VALUES (''0811d04da4334b4189325d0cab2ceee0'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 0, ''2022-09-06 14:30:05'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''0906a1ea194d479d8400165dd0ca179e'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 0, ''2022-09-06 14:30:15'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''1706be95f6214f79985be5c8214f0046'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 1, ''2022-09-06 14:31:00'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''206c74ced01645529ae0474f236896f8'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 1, ''2022-09-06 14:29:50'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''2a4c65ad46f943039a4e012503806795'', ''c5e92c817aa144019860c1b64e8ae301'', ''testTask'', ''test'', '''', 1, ''java.lang.NoSuchMethodException: com.ossbar.modules.job.task.TestTask.test()'', 0, ''2022-09-06 14:34:05'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''2a5181eb388944c38672308ecdcf29a9'', ''0293303f0cd34877b75576edcf45e4f6'', ''testTask'', ''test'', ''creatorblue'', 0, NULL, 1047, ''2022-09-06 14:17:02'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''2cc4c1a7ef214395ae7c507a3088cf0d'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 1, ''2022-09-06 14:29:40'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''2d08ab6f5ead4f3a84ec3436807f1a3d'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 0, ''2022-09-06 14:30:00'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''2f8ed65bdedb496da94d3d1765b50f2d'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 0, ''2022-09-06 14:30:40'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''3088b8a17b914f43b4ed53e79fcee37b'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 0, ''2022-09-06 14:32:00'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''332ae2ddbfca4c5db1c3e7ff938f318e'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 0, ''2022-09-06 14:30:30'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''36436966cd0d4c03af59ec7d1d29dd7f'', ''c5e92c817aa144019860c1b64e8ae301'', ''testTask'', ''test'', '''', 1, ''java.lang.NoSuchMethodException: com.ossbar.modules.job.task.TestTask.test()'', 0, ''2022-09-06 14:33:25'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''404e4c4247164b868938b30108628bc2'', ''c5e92c817aa144019860c1b64e8ae301'', ''testTask'', ''test'', '''', 1, ''java.lang.NoSuchMethodException: com.ossbar.modules.job.task.TestTask.test()'', 0, ''2022-09-06 14:33:50'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''44ce7ec363f44fb0b3d6fc4549cfd6f9'', ''c5e92c817aa144019860c1b64e8ae301'', ''testTask'', ''test'', '''', 1, ''java.lang.NoSuchMethodException: com.ossbar.modules.job.task.TestTask.test()'', 0, ''2022-09-06 14:33:20'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''47f46b97975149029be28b7e732492a4'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 0, ''2022-09-06 14:31:50'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''5cf577d74a52400cbda036762d681f75'', ''0293303f0cd34877b75576edcf45e4f6'', ''testTask'', ''test'', ''creatorblue'', 0, NULL, 1011, ''2022-09-06 14:30:00'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''5d32bea6cfae4a2ba09b477f344922c2'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 1, ''2022-09-06 14:31:15'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''5f8a8ff054e746099a48283463d5825d'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 1, ''2022-09-06 14:31:45'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''6460ea68bcdb4657811a915f11f0c0e3'', ''c5e92c817aa144019860c1b64e8ae301'', ''testTask'', ''test'', '''', 1, ''java.lang.NoSuchMethodException: com.ossbar.modules.job.task.TestTask.test()'', 0, ''2022-09-06 14:34:10'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''67d77e54528744918a93ee35ca3ac916'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 2, ''2022-09-06 14:29:30'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''6a24e84bd9254708b2a4e4716a00d192'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 1, ''2022-09-06 14:30:25'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''6b9091bf58bb46a5946ea10e3953c050'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 0, ''2022-09-06 14:31:30'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''6ba76429d837418e889bec33cfc3696b'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 0, ''2022-09-06 14:29:55'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''7106aa925bfb47ee9313393702b6bf8b'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 0, ''2022-09-06 14:30:20'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''7382ca4c39234a45ba2b14cc36c650d3'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 2, ''2022-09-06 14:29:35'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''743f346088a1451e87f0e0d9b4ecbc06'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 1, ''2022-09-06 14:30:45'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''74ee943c05eb40f2ba13426f77506fe2'', ''c5e92c817aa144019860c1b64e8ae301'', ''testTask'', ''test'', '''', 1, ''java.lang.NoSuchMethodException: com.ossbar.modules.job.task.TestTask.test()'', 0, ''2022-09-06 14:34:00'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''75f4f6fbd3354505823fb001908630d8'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 0, ''2022-09-06 14:31:10'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''7c9cb5e3bfbb4071abb67e3dec4e5cf0'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 1, ''2022-09-06 14:31:25'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''9b419ada6de34680867f3e652941eb2b'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 1, ''2022-09-06 14:29:45'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''9c72e274ef6c4188aa9ecbe3432a02a0'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 0, ''2022-09-06 14:30:10'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''a3ccd9e68dfe4de585c8e94f526d18b5'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 0, ''2022-09-06 14:31:20'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''adc74937eab3446e8337737252cbf86a'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 0, ''2022-09-06 14:31:35'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''b435579321e04c4292c19f1301a6a17e'', ''c5e92c817aa144019860c1b64e8ae301'', ''testTask'', ''test'', '''', 1, ''java.lang.NoSuchMethodException: com.ossbar.modules.job.task.TestTask.test()'', 0, ''2022-09-06 14:33:55'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''be2e2dd5d721490c8d9ffa3e7c4aed2c'', ''c5e92c817aa144019860c1b64e8ae301'', ''testTask'', ''test'', '''', 1, ''java.lang.NoSuchMethodException: com.ossbar.modules.job.task.TestTask.test()'', 0, ''2022-09-06 14:33:35'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''c466ec0d64e7400d987a9ef54b802071'', ''c5e92c817aa144019860c1b64e8ae301'', ''testTask'', ''test'', '''', 1, ''java.lang.NoSuchMethodException: com.ossbar.modules.job.task.TestTask.test()'', 1, ''2022-09-06 14:34:15'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''cfa60136aed7434c953d9517b9c57304'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 0, ''2022-09-06 14:30:55'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''d1f10704f58f4e52bf22583dca5fde22'', ''c5e92c817aa144019860c1b64e8ae301'', ''testTask'', ''test'', '''', 1, ''java.lang.NoSuchMethodException: com.ossbar.modules.job.task.TestTask.test()'', 0, ''2022-09-06 14:33:40'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''d2f576c721ff4313871e2e11e233dec1'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 0, ''2022-09-06 14:30:35'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''d374a036da5e418ca7087608bb84dfeb'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 0, ''2022-09-06 14:31:55'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''d7f5b4fe3acb49d58c9ee0a9ab453200'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 0, ''2022-09-06 14:31:05'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''d8be9b60a45d46cc9849636ac70dec00'', ''c5e92c817aa144019860c1b64e8ae301'', ''testTask'', ''test'', '''', 1, ''java.lang.NoSuchMethodException: com.ossbar.modules.job.task.TestTask.test()'', 0, ''2022-09-06 14:33:42'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''e524756bdcb9434ebd578d74550c00dd'', ''c5e92c817aa144019860c1b64e8ae301'', ''testTask'', ''test'', '''', 1, ''java.lang.NoSuchMethodException: com.ossbar.modules.job.task.TestTask.test()'', 0, ''2022-09-06 14:33:45'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''e9c66daeb5c240c2b76b25d9a591c3c2'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 0, ''2022-09-06 14:30:50'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''ee64e9daa9774ba599854340cdd1b7cb'', ''0293303f0cd34877b75576edcf45e4f6'', ''testTask'', ''test'', ''creatorblue'', 0, NULL, 1018, ''2022-09-06 14:18:38'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''fa466430f8d44dc08b91c47f4c018d07'', ''3aae8a4890414cec95f1dfb58c4c2fe4'', ''12'', ''31'', ''131'', 1, ''org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named \''12\'' available'', 0, ''2022-09-06 14:31:40'', NULL, NULL, NULL);
INSERT INTO `t_schedule_job_log` VALUES (''fb6e719a7fe54c1aaa3190302ac51d34'', ''c5e92c817aa144019860c1b64e8ae301'', ''testTask'', ''test'', '''', 1, ''java.lang.NoSuchMethodException: com.ossbar.modules.job.task.TestTask.test()'', 1, ''2022-09-06 14:33:30'', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for t_sys_attach
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_attach`;
CREATE TABLE `t_sys_attach`  (
  `ATTACH_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''主键'',
  `FILE_URL` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''文件路径'',
  `FILE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''文件名称'',
  `REMARK` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''文件备注'',
  `SECOND_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''文件别名'',
  `FILE_TYPE` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''附件分类'',
  `PKID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''关联ID'',
  `FILE_SIZE` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''文件大小'',
  `FILE_SUFFIX` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''文件后缀'',
  `LJ_URL` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''链接地址'',
  `FILE_NO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''文件编号'',
  `FILE_STATE` int(0) NULL DEFAULT NULL COMMENT ''文件状态0是未提交,1是已提交绑定'',
  `FILE_ORERNO` int(0) NULL DEFAULT NULL COMMENT ''文件排序号'',
  `create_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建人'',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''修改时间'',
  PRIMARY KEY (`ATTACH_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''数据附件表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_attach
-- ----------------------------
INSERT INTO `t_sys_attach` VALUES (''16810677538d459fadd6588a1f688a3e'', ''E:/uploads//settings/fd17df07-504f-48c5-af71-de9938405fd4.jpeg'', ''image/jpeg'', NULL, NULL, ''3'', ''23ec7635acdc438592171e0a5fccaf'', ''12325'', ''.jpeg'', ''fd17df07-504f-48c5-af71-de9938405fd4.jpeg'', NULL, 1, NULL, ''1'', ''2022-09-14 09:19:41'', NULL, ''2022-09-14 09:19:41'');
INSERT INTO `t_sys_attach` VALUES (''8907d5adb9cf40d8b027ff529bdc4fe4'', ''E:/uploads//sys-user-img/d8879dfa-e1af-418e-a48b-150165b2cbca.jpeg'', ''image/jpeg'', NULL, NULL, ''2'', NULL, ''670020'', ''.jpeg'', ''d8879dfa-e1af-418e-a48b-150165b2cbca.jpeg'', NULL, 0, NULL, ''1'', ''2022-09-14 09:06:28'', NULL, NULL);
INSERT INTO `t_sys_attach` VALUES (''a0c190d414704e9dba17c5c2624274aa'', ''E:/uploads//settings/59e66dd8-d562-4dd4-82bc-2876a7ed8ed2.jpeg'', ''image/jpeg'', NULL, NULL, ''3'', ''23ec7635acdc438592171e0a5fccaf'', ''291124'', ''.jpeg'', ''59e66dd8-d562-4dd4-82bc-2876a7ed8ed2.jpeg'', NULL, 1, NULL, ''1'', ''2022-09-14 09:26:43'', NULL, ''2022-09-14 09:26:43'');
INSERT INTO `t_sys_attach` VALUES (''a470efbc61614c0cb5065c49272abf85'', ''E:/uploads//sys-user-img/12a10d95-0ef1-4992-abdc-5e5a2c105a28.jpeg'', ''image/jpeg'', NULL, NULL, ''2'', NULL, ''85024'', ''.jpeg'', ''12a10d95-0ef1-4992-abdc-5e5a2c105a28.jpeg'', NULL, 0, NULL, ''1'', ''2022-09-14 09:09:25'', NULL, NULL);
INSERT INTO `t_sys_attach` VALUES (''c4ac2ebd66dd4d39be803c2987ee25e5'', ''E:/uploads//settings/863cba20-c943-4b88-a90e-0f222ade6caa.jpeg'', ''image/jpeg'', NULL, NULL, ''3'', ''23ec7635acdc438592171e0a5fccaf'', ''53020'', ''.jpeg'', ''863cba20-c943-4b88-a90e-0f222ade6caa.jpeg'', NULL, 1, NULL, ''1'', ''2022-09-14 09:30:09'', NULL, ''2022-09-14 09:30:09'');

-- ----------------------------
-- Table structure for t_sys_codebuilder
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_codebuilder`;
CREATE TABLE `t_sys_codebuilder`  (
  `CODEID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `PRI` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''是否主键'',
  `TABLENAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''表名'',
  `FIELDS` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''字段'',
  `QUERYCONDITION` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''是否为查询条件Y，N'',
  `FILL` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''是否必填'',
  `DESCRIPT` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''描述'',
  `CONTROL` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''所属控件:select ,textarea,text,date'',
  PRIMARY KEY (`CODEID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''框架代码辅助表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_sys_dataprivilege
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dataprivilege`;
CREATE TABLE `t_sys_dataprivilege`  (
  `ROLE_ORGID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''主键'',
  `ORG_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''机构ID'',
  `ROLE_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''角色ID'',
  PRIMARY KEY (`ROLE_ORGID`) USING BTREE,
  INDEX `FK_Reference_13`(`ROLE_ID`) USING BTREE,
  INDEX `FK_Reference_4`(`ORG_ID`) USING BTREE,
  CONSTRAINT `t_sys_dataprivilege_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `t_sys_role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_sys_dataprivilege_ibfk_2` FOREIGN KEY (`ORG_ID`) REFERENCES `t_sys_org` (`ORG_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''角色数据权限表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict`;
CREATE TABLE `t_sys_dict`  (
  `DICT_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DICT_TYPE` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''字典分类'',
  `DICT_NAME` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''字典分类名称'',
  `DICT_VALUE` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''字典值'',
  `DICT_CODE` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''字典编码'',
  `REMARK` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''字典描述'',
  `DICT_SORT` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''1、平台内2、平台外'',
  `SORT_NUM` int(0) NULL DEFAULT NULL COMMENT ''排序号'',
  `PARENT_TYPE` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''父分类'',
  `DISPLAY_SORT` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''字典展现分类:下拉类型(select)树形(tree) 复选型(checkbox)单选radio'',
  `DICT_CLASS` int(0) NULL DEFAULT NULL,
  `MULTI_TYPE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''单选或多选：主要针对树形控件'',
  `ORG_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''所属机构'',
  `ISDEFAULT` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''默认值'',
  `displaying` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''是否显示'',
  `create_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建人'',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''修改时间'',
  `dict_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''字典图标'',
  PRIMARY KEY (`DICT_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_sys_dict
-- ----------------------------
INSERT INTO `t_sys_dict` VALUES (''0717782e383d41f4bed7c3b1ca5f0956'', ''group_member_identity'', ''课堂小组成员身份'', NULL, NULL, NULL, ''1'', 1, ''0'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2021-05-09 16:27:32'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''0bbbefd660604f6faa0d4f04f7c19cd0'', ''activityQuestionType'', ''题目类型(活动)'', ''单选'', ''1'', NULL, ''2'', 1, ''688e4ec4b811411596632f0a80323aad'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2020-06-28 09:14:11'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''0ef2ba05d7ea43198ba40ec5cd2f2e08'', ''newsStatus'', ''新闻资讯状态'', ''已发布'', ''2'', NULL, ''2'', 2, ''864de58f9cd247c8a829bf385cdcfb9d'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2019-07-23 10:32:26'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''10bf48e67a9841c6bf5cea319497cd9c'', ''newsStatus'', ''新闻资讯状态'', ''待审核'', ''1'', NULL, ''2'', 1, ''864de58f9cd247c8a829bf385cdcfb9d'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2019-07-23 10:32:18'', ''1'', ''2019-08-16 17:47:04'', NULL);
INSERT INTO `t_sys_dict` VALUES (''19741519c789413da99f3ecc5377ffca'', ''activityState'', ''活动状态'', NULL, NULL, NULL, ''1'', 1, ''0'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2020-06-22 15:47:30'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''1f2093f0213a4dac99afd3f9f3cea804'', ''activityState'', ''活动状态'', ''未开始'', ''0'', NULL, ''2'', 1, ''19741519c789413da99f3ecc5377ffca'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2020-06-22 15:47:38'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''2372235176de4ba680825a094bc1745a'', ''group_member_identity'', ''课堂小组成员身份'', ''技术总监'', ''2'', NULL, ''2'', 2, ''0717782e383d41f4bed7c3b1ca5f0956'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2021-05-09 17:41:37'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''2a9f8587be334d67a5f8b9ba0c0a13ab'', ''deployMainType'', ''教学包发布方大类'', ''创蓝教育'', ''3'', NULL, ''2'', 3, ''85e08b917c8a4da2ad2356680395315f'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2019-07-26 14:58:36'', ''1'', ''2021-02-25 10:14:02'', NULL);
INSERT INTO `t_sys_dict` VALUES (''2ed464e15afb458989936854275e0fe7'', ''group_member_identity'', ''课堂小组成员身份'', ''测试人员'', ''4'', NULL, ''2'', 4, ''0717782e383d41f4bed7c3b1ca5f0956'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2021-05-09 17:42:03'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''315814c9e3454e60af2ac86dd6bf295e'', ''term'', ''所属阶段'', ''第二阶段'', ''2'', NULL, ''2'', 2, ''a4d5f60d75d54864aef8b54cadfe8123'', ''1'', NULL, NULL, NULL, ''0'', ''1'', ''1'', ''2019-07-31 10:23:18'', ''1'', ''2021-11-30 13:24:05'', NULL);
INSERT INTO `t_sys_dict` VALUES (''3c11c00eb3ed4ac2b923846bf553633a'', ''a'', ''a'', '''', '''', NULL, ''1'', 2, ''0'', ''1'', NULL, NULL, NULL, NULL, ''1'', ''1'', ''2022-09-05 11:51:09'', NULL, NULL, ''745159ee-5a64-457f-84c5-88e81a55e0dc.jpeg'');
INSERT INTO `t_sys_dict` VALUES (''54d41108ae3e44d6ab05f4375dae007d'', ''term'', ''所属阶段'', ''第一阶段'', ''1'', NULL, ''2'', 1, ''a4d5f60d75d54864aef8b54cadfe8123'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2019-07-31 10:23:04'', ''1'', ''2021-11-30 13:23:37'', NULL);
INSERT INTO `t_sys_dict` VALUES (''614711011f414c53a863c83d7ddb960e'', ''activityQuestionType'', ''题目类型(活动)'', ''多选'', ''2'', NULL, ''2'', 2, ''688e4ec4b811411596632f0a80323aad'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2020-06-28 09:14:18'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''63af3b1847d44850a81c9c5c933afebc'', ''group_member_identity'', ''课堂小组成员身份'', ''UI设计师'', ''5'', NULL, ''2'', 5, ''0717782e383d41f4bed7c3b1ca5f0956'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2021-05-09 17:42:12'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''65c110365c48411daba8926d0b133c9d'', ''asda'', ''asdas'', '''', '''', NULL, ''1'', 6, ''0'', ''1'', NULL, NULL, NULL, NULL, ''1'', ''1'', ''2022-09-05 14:29:07'', NULL, NULL, ''a43f812b-a8b0-468f-9579-7b7b210ddbc2.jpeg'');
INSERT INTO `t_sys_dict` VALUES (''688e4ec4b811411596632f0a80323aad'', ''activityQuestionType'', ''题目类型(活动)'', NULL, NULL, NULL, ''1'', 1, ''0'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2020-06-28 09:13:55'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''6bb8768c3e4c45a7bcd817b6ef61d016'', ''group_member_identity'', ''课堂小组成员身份'', ''项目经理'', ''1'', NULL, ''2'', 1, ''0717782e383d41f4bed7c3b1ca5f0956'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2021-05-09 16:27:41'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''7095779dee82453f9b20e380fd11131d'', ''group_member_identity'', ''课堂小组成员身份'', ''开发人员'', ''3'', NULL, ''2'', 3, ''0717782e383d41f4bed7c3b1ca5f0956'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2021-05-09 17:41:44'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''7d9cfc5a9b884987991bb00ea82cecf9'', ''deployMainType'', ''教学包发布方大类'', ''个人'', ''2'', NULL, ''2'', 2, ''85e08b917c8a4da2ad2356680395315f'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2019-07-26 14:58:28'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''81fb19c2a78a486caf1ab6c88e24e7ba'', ''pkgLimit'', ''教学包使用限制'', ''授权'', ''1'', NULL, ''2'', 1, ''e80e9e4659ba48d7aaa60bf61955d91b'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2019-07-26 14:54:39'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''84c337cbfa414507a678a32a22f924dc'', ''sex'', ''性别'', '''', '''', '''', ''1'', 1, ''0'', ''1'', NULL, '''', '''', ''1'', ''1'', ''1'', ''2018-03-02 09:31:41'', ''1'', '''', '''');
INSERT INTO `t_sys_dict` VALUES (''85e08b917c8a4da2ad2356680395315f'', ''deployMainType'', ''教学包发布方大类'', NULL, NULL, NULL, ''1'', 1, ''0'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2019-07-26 14:58:10'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''864de58f9cd247c8a829bf385cdcfb9d'', ''newsStatus'', ''新闻资讯状态'', NULL, NULL, NULL, ''1'', 1, ''0'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2019-07-23 10:31:52'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''88fb3ac5ecfe4dfa8f99274088c5a3df'', ''a'', ''a'', ''asdadasda'', ''aa'', NULL, NULL, 1, ''3c11c00eb3ed4ac2b923846bf553633a'', ''1'', NULL, NULL, ''fccade7bb8ac4dcb8c56e5341437c786'', ''1'', ''1'', ''1'', ''2022-09-05 11:51:16'', NULL, NULL, ''743f11c8-4e0b-4414-8bd0-1370c32c861c.jpeg'');
INSERT INTO `t_sys_dict` VALUES (''8b768ecc67ee45508322c5bcf09eb0ee'', ''asda'', ''asds'', '''', '''', NULL, ''1'', 7, ''0'', ''1'', NULL, NULL, NULL, NULL, ''1'', ''1'', ''2022-09-05 14:29:11'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''94d903d7664948298930839063833966'', ''pkgLevel'', ''教学包适用层次'', ''中职'', ''3'', NULL, ''2'', 3, ''ea0c6f1698fb4c96b46c58996f0064fd'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2019-07-26 14:39:06'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''95c1b201e8bd41c9960129d47c683731'', ''sex'', ''性别'', ''女'', ''2'', '''', ''2'', 2, ''84c337cbfa414507a678a32a22f924dc'', ''1'', NULL, '''', '''', ''0'', ''1'', ''1'', ''2018-03-02 09:32:22'', ''1'', '''', '''');
INSERT INTO `t_sys_dict` VALUES (''9aa2211d4582435686e67c8a8a4c7d03'', ''pkgLimit'', ''教学包使用限制'', ''免费'', ''2'', NULL, ''2'', 2, ''e80e9e4659ba48d7aaa60bf61955d91b'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2019-07-26 14:54:59'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''a1afc63f06af4e54acf17b6a626456a9'', ''d12'', ''d12'', '''', '''', NULL, ''1'', 5, ''0'', ''1'', NULL, NULL, ''e1ba0dbcf3cc4944a4845bb132e9726c'', NULL, ''1'', ''1'', ''2022-09-05 14:06:37'', ''1'', ''2022-09-05 14:25:10'', ''2f8e8f7c-c2cf-47da-b5f4-7a16f1a1875a.jpeg'');
INSERT INTO `t_sys_dict` VALUES (''a4d5f60d75d54864aef8b54cadfe8123'', ''term'', ''所属阶段'', NULL, NULL, NULL, ''1'', 1, ''0'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2019-07-31 10:22:44'', ''1'', ''2021-11-30 13:23:17'', NULL);
INSERT INTO `t_sys_dict` VALUES (''a58edf2c8a8d4c39a0b300b61d613198'', ''activityState'', ''活动状态'', ''进行中'', ''1'', NULL, ''2'', 2, ''19741519c789413da99f3ecc5377ffca'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2020-06-22 15:47:52'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''adca6bcc181b4e9fb333f8d78b3b2de9'', ''pkgLevel'', ''教学包适用层次'', ''高职'', ''2'', NULL, ''2'', 2, ''ea0c6f1698fb4c96b46c58996f0064fd'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2019-07-26 14:38:54'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''b2187623988749189b7e36d595d9e5c9'', ''newsStatus'', ''新闻资讯状态'', ''未通过'', ''4'', NULL, ''2'', 4, ''864de58f9cd247c8a829bf385cdcfb9d'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2019-08-16 16:27:28'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''bba6021a8344454fb23e8c75fefda4e0'', ''activityQuestionType'', ''题目类型(活动)'', ''简答'', ''3'', NULL, ''2'', 3, ''688e4ec4b811411596632f0a80323aad'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2020-07-08 16:36:39'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''c06d94b8e2944887beb2277bb8957dd1'', ''pkgLevel'', ''教学包适用层次'', ''本科'', ''1'', NULL, ''2'', 1, ''ea0c6f1698fb4c96b46c58996f0064fd'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2019-07-26 14:38:44'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''c5225891d6534da4b91581fe25924ddf'', ''sex'', ''性别'', ''保密'', ''0'', '''', ''2'', 3, ''84c337cbfa414507a678a32a22f924dc'', ''1'', NULL, '''', '''', ''1'', ''1'', ''1'', ''2018-05-04 17:13:35'', ''1'', '''', '''');
INSERT INTO `t_sys_dict` VALUES (''d38bfc9575b844caa24c0b56da56b07b'', ''activityState'', ''活动状态'', ''已结束'', ''2'', NULL, ''2'', 3, ''19741519c789413da99f3ecc5377ffca'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2020-06-22 15:48:07'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''d696266f71cc40dc99298e32de8ba90c'', ''term'', ''所属阶段'', ''第三阶段'', ''3'', NULL, ''2'', 3, ''a4d5f60d75d54864aef8b54cadfe8123'', ''1'', NULL, NULL, NULL, ''0'', ''1'', ''1'', ''2021-11-30 13:23:57'', ''1'', ''2021-11-30 13:23:17'', NULL);
INSERT INTO `t_sys_dict` VALUES (''db0d74b1e0884d3f9344021d695b2a84'', ''b'', ''b'', '''', '''', NULL, ''1'', 3, ''0'', ''1'', NULL, NULL, NULL, NULL, ''1'', ''1'', ''2022-09-05 14:04:54'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''e094c6ae52904d9bbd8a914081cb4728'', ''group_member_identity'', ''课堂小组成员身份'', ''无'', ''0'', NULL, ''2'', 1, ''0717782e383d41f4bed7c3b1ca5f0956'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''ec7a75b6122d4f9b8c7b08459b3c4b3f'', ''2021-09-22 08:49:56'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''e191edccc281479dadee1ca08b608f0f'', ''c'', ''c'', '''', '''', NULL, ''1'', 4, ''0'', ''1'', NULL, NULL, NULL, NULL, ''1'', ''1'', ''2022-09-05 14:05:54'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''e2f6d38e76194503a72ee2f2b37219f3'', ''sex'', ''性别'', ''男'', ''1'', '''', ''2'', 1, ''84c337cbfa414507a678a32a22f924dc'', ''1'', NULL, '''', '''', ''1'', ''1'', ''1'', ''2018-03-02 09:32:03'', ''1'', '''', '''');
INSERT INTO `t_sys_dict` VALUES (''e80e9e4659ba48d7aaa60bf61955d91b'', ''pkgLimit'', ''教学包使用限制'', NULL, NULL, NULL, ''1'', 1, ''0'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2019-07-26 14:54:25'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''e9c9a42d1574469ea8506b4243c58474'', ''pkgLevel'', ''教学包适用层次'', ''专升本'', ''4'', NULL, ''2'', 4, ''ea0c6f1698fb4c96b46c58996f0064fd'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2021-02-25 10:11:58'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''ea0c6f1698fb4c96b46c58996f0064fd'', ''pkgLevel'', ''教学包适用层次'', NULL, NULL, NULL, ''1'', 1, ''0'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2019-07-26 14:38:28'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''f30254b9da664de3a2d76ccc69d75458'', ''deployMainType'', ''教学包发布方大类'', ''学校'', ''1'', NULL, ''2'', 1, ''85e08b917c8a4da2ad2356680395315f'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2019-07-26 14:58:23'', NULL, NULL, NULL);
INSERT INTO `t_sys_dict` VALUES (''fbfbed1fa506439d9113e1290099cac3'', ''newsStatus'', ''新闻资讯状态'', ''已删除'', ''3'', NULL, ''2'', 3, ''864de58f9cd247c8a829bf385cdcfb9d'', ''1'', NULL, NULL, NULL, ''1'', ''1'', ''1'', ''2019-07-23 10:32:35'', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for t_sys_docs
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_docs`;
CREATE TABLE `t_sys_docs`  (
  `doc_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''主键ID'',
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''父节点'',
  `system_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''所属子系统id'',
  `main_doc` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''所属文档'',
  `menu_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''菜单id'',
  `org_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''机构ID'',
  `doc_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''文档名称'',
  `doc_summary` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''文档简介'',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT ''文档内容'',
  `doc_history` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT ''文档更新历史轨迹说明'',
  `doc_type` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''文档类型(01帮助手册，02api文档)'',
  `doc_class` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''层级'',
  `doc_sort` int(0) NULL DEFAULT NULL COMMENT ''序号'',
  `doc_icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''文档封面图'',
  `display` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''是否可见'',
  `doc_downnum` int(0) NULL DEFAULT NULL COMMENT ''文档下载量'',
  `doc_sharenum` int(0) NULL DEFAULT NULL COMMENT ''文档分享量'',
  `doc_viewnum` int(0) NULL DEFAULT NULL COMMENT ''文档阅读量'',
  `doc_likenum` int(0) NULL DEFAULT NULL COMMENT ''文档点赞量'',
  `create_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建人'',
  `create_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''修改时间'',
  PRIMARY KEY (`doc_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''系统文档'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_sys_group
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_group`;
CREATE TABLE `t_sys_group`  (
  `GROUP_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''组ID'',
  `GROUP_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''组名称'',
  `REMARK` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''组描述'',
  `GROUP_TYPE` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''组类别 '',
  `PARENT_GROUPID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''父组ID '',
  `create_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建人'',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''修改时间'',
  PRIMARY KEY (`GROUP_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''用户组表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_sys_org
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_org`;
CREATE TABLE `t_sys_org`  (
  `ORG_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''机构ID'',
  `ORG_SN` int(0) NULL DEFAULT 0 COMMENT ''机构排序ID'',
  `ORG_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''机构名称'',
  `ORG_CODE` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''机构编号'',
  `ORG_XZQM` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''行政区码'',
  `ORG_SHOWNAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''机构显示名称'',
  `PARENT_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''父机构ID'',
  `LAYER` int(0) NULL DEFAULT NULL COMMENT ''层（阶次）'',
  `REMARK` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''单位简介'',
  `ORG_TYPE` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''机构类型:1、部门 2、公司'',
  `ADDR` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''通讯地址'',
  `ZIP` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''邮政编码'',
  `EMAIL` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''电子邮箱'',
  `LEADER` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''机构负责人'',
  `PHONE` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''办公电话'',
  `FAX` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''传真号码'',
  `STATE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''状态:1有效 2、停用'',
  `MOBILE` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''负责人手机号码'',
  `JP` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''简拼'',
  `QP` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''全拼'',
  `ANCESTRY` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''排序'',
  `create_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建人'',
  `create_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''修改时间'',
  `cover_pic` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''封面图'',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT ''机构描述'',
  `college_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''学院代码'',
  `major_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''专业代码'',
  PRIMARY KEY (`ORG_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''机构表'' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_sys_org
-- ----------------------------
INSERT INTO `t_sys_org` VALUES (''0615494a26cb400c8bf40b693cc30a47'', NULL, ''望城研发部'', ''010102'', ''412300'', ''望城研发部'', ''0b25462a631e4ccf93dddc2b58037738'', NULL, NULL, ''0'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''WCYF'', ''WangChengYanFabu'', NULL, ''1'', ''2019-07-26 08:41:09'', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''0b25462a631e4ccf93dddc2b58037738'', 1, ''研发中心'', ''0101'', ''412300'', ''研发中心'', ''845786f2bfbf46398e3b495f6c7014bc'', NULL, NULL, ''0'', '''', '''', '''', ''朱强'', '''', '''', ''1'', ''13055190923'', ''YFZ'', ''YanFaZhongxin'', NULL, ''1'', ''2018-02-27 20:35:16'', ''1'', ''2020-04-23 20:28:40'', NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''10865'', NULL, ''湖南信息职业技术学院'', ''03'', ''123456'', ''信息学院'', ''-1'', NULL, NULL, ''1'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''HN*SX'', ''HuNan**jiShuXueyuan'', NULL, ''1'', ''2020-09-01 16:55:50'', ''1'', ''2020-10-15 09:17:01'', NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''1881b7c655014abea780d23da0f00ebb'', NULL, ''湖南电子科技职业学院'', ''08'', ''412300'', ''电子科技职院'', ''-1'', NULL, NULL, ''1'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, NULL, NULL, NULL, ''1'', ''2021-05-31 22:19:59'', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''199aba6d71d84c72ab06d61bae90891d'', NULL, ''啊撒旦艰苦拉萨'', ''010010101'', ''321232'', ''1232131'', ''f192eb50c07847d28a0bc02d3cf7532d'', NULL, NULL, ''0'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''ASDJKL'', ''ASaDanJianKuLasa'', NULL, ''1'', ''2022-09-13 10:26:47'', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''2116113e62d04f1bb5306b72b8ec963c'', NULL, ''百家姓'', ''010'', ''123456'', ''百家姓'', ''-1'', NULL, NULL, ''0'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''BJ'', ''BaiJiaxing'', NULL, ''1'', ''2022-09-13 10:03:23'', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''26b8b09e79c4422cbaf7789dbf7dfc78'', 3, ''财务部'', ''0105'', ''412300'', ''财务部'', ''845786f2bfbf46398e3b495f6c7014bc'', NULL, NULL, ''0'', '''', '''', '''', ''黄玉兰'', '''', '''', ''1'', ''18229923839'', ''CWB'', ''CaiWuBu'', NULL, ''1'', ''2018-02-27 20:34:25'', ''1'', ''2019-06-17 15:12:43'', NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''3242e174551c42f9bd737afb80fdb7d2'', NULL, ''吉林大学'', ''05'', ''412305'', ''吉林大学'', ''-1'', NULL, NULL, ''1'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''JLD'', ''JiLinDaxue'', NULL, ''1'', ''2020-09-23 10:42:59'', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''38de976ab44b4648afc19be5881da08d'', NULL, ''扎萨达'', ''0306'', ''123456'', ''阿大撒'', ''10865'', NULL, NULL, ''0'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''ZSD'', ''ZhaSaDa'', NULL, ''1'', ''2022-09-09 10:56:37'', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''408ea3a61e754f14900a15284020208f'', NULL, ''湖南工业职业技术学院'', ''09'', ''412300'', ''湖南工业职业技术学院'', ''-1'', NULL, NULL, ''1'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''HNGYZYJSX'', ''HuNanGongYeZhiYeJiShuXueyuan'', NULL, ''1'', ''2021-10-25 11:46:13'', ''1'', ''2021-10-25 11:46:36'', NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''57d8fa68a2ca4700b4a6b3465aac2e95'', NULL, ''阳光100研发部'', ''010101'', ''412300'', ''阳光100研发部'', ''0b25462a631e4ccf93dddc2b58037738'', NULL, NULL, ''0'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''YG1F'', ''YangGuang100yanFabu'', NULL, ''1'', ''2019-07-26 08:40:44'', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''8297365bd47d4c788b2ad2e830914bff'', 6, ''人力资源部'', ''0104'', ''410023'', ''人力资源部'', ''845786f2bfbf46398e3b495f6c7014bc'', NULL, NULL, ''1'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''RLZYB'', ''RenLiZiYuanBu'', NULL, ''1'', ''2019-06-06 19:25:35'', ''1'', ''2019-06-27 10:18:32'', NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''845786f2bfbf46398e3b495f6c7014bc'', 1, ''湖南创蓝信息科技有限公司'', ''01'', ''410017'', ''创蓝信息'', ''-1'', NULL, NULL, ''0'', ''阳光100国际新城1-34栋'', '''', '''', '''', ''0731-89913439'', '''', ''1'', '''', ''HNCLXXKJYXG'', ''HuNanChuangLanXinXiKeJiYouXianGongsi'', NULL, ''1'', NULL, ''1'', ''2021-02-25 11:04:47'', NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''8cd0f3ccc6c044cfbde040b6c3a98998'', NULL, ''行政部'', ''0107'', ''412309'', ''行政部'', ''845786f2bfbf46398e3b495f6c7014bc'', NULL, NULL, ''0'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''XZB'', ''XingZhengBu'', NULL, ''1'', ''2019-06-17 19:22:07'', ''1'', ''2019-06-17 19:22:41'', NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''8e5043e881534abca617043ce08b17f5'', NULL, ''长沙理工大学'', ''04'', ''430222'', ''长沙理工'', ''-1'', NULL, NULL, ''1'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''ZSLGD'', ''ZhangShaLiGongDaxue'', NULL, ''1'', ''2020-09-23 10:42:06'', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''a645cf35f68f4620a3d3753677dd98d3'', 2, ''教育中心'', ''0103'', ''410017'', ''教育中心'', ''845786f2bfbf46398e3b495f6c7014bc'', NULL, NULL, ''0'', '''', ''410017'', ''28449472@qq.com'', ''欧阳庆'', ''0731-89913439'', ''0731-89913439'', ''1'', ''15078307127'', ''JYZ'', ''JiaoYuZhongxin'', NULL, ''1'', NULL, ''1'', ''2019-06-27 10:55:23'', NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''a6b7853d61104c8da0cb757af80aacfd'', NULL, ''213'', ''0100101010101'', ''123456'', ''123'', ''dd7c053dafd74cdf9433bb3d1e4ab775'', NULL, NULL, ''0'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''2'', ''213'', NULL, ''1'', ''2022-09-13 10:27:07'', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''ab4dca41d4a24ba49dbd60850dfd8a78'', NULL, ''国防科技大学'', ''02'', ''412309'', ''国防科大'', ''-1'', NULL, NULL, ''1'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''GFKJD'', ''GuoFangKeJiDaxue'', NULL, ''1'', ''2020-09-02 17:12:34'', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''acb356043ff94b0ab51d921d6eabcb25'', NULL, ''湖南大学'', ''07'', ''412304'', ''湖南大学'', ''-1'', NULL, NULL, ''1'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''HND'', ''HuNanDaxue'', NULL, ''1'', ''2020-09-23 10:44:47'', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''ba341773011f4bacb917859516ff54de'', NULL, ''湖南工业大学'', ''010'', ''412300'', ''湖南工业大学'', ''-1'', NULL, NULL, ''1'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''HNGYD'', ''HuNanGongYeDaxue'', NULL, ''1'', ''2021-10-25 11:47:44'', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''bec753ae9b4c4d6d856dcaa6e4526b2b'', NULL, ''网络空间安全学院'', ''0302'', ''412300'', ''网络空间安全学院'', ''10865'', NULL, NULL, ''0'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''WLKJAQX'', ''WangLuoKongJianAnQuanXueyuan'', NULL, ''1'', ''2020-09-26 20:55:59'', ''1'', ''2020-09-26 20:57:08'', NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''c58056a52099439cb1c527b76b5e0aab'', NULL, ''人工智能与软件工程学院'', ''0801'', ''412300'', ''软件学院'', ''1881b7c655014abea780d23da0f00ebb'', NULL, NULL, ''0'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''RGZNHRJX'', ''RenGongZhiNengHeRuanJianXueyuan'', NULL, ''1'', ''2021-05-31 22:23:00'', ''1'', ''2022-03-12 15:36:07'', NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''d10e17d929d2496b89c7475fad1fffbd'', 2, ''市场部'', ''0102'', ''412300'', ''市场部'', ''845786f2bfbf46398e3b495f6c7014bc'', NULL, NULL, ''1'', ''1'', '''', '''', ''朱建武'', ''0731-89913439'', '''', ''1'', ''18229923839'', ''SC'', ''ShiChangbu'', NULL, ''1'', ''2018-02-27 20:33:55'', ''1'', ''2019-06-24 10:20:16'', NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''dd7c053dafd74cdf9433bb3d1e4ab775'', NULL, ''艰苦撒旦艰苦拉萨'', ''01001010101'', ''123456'', ''啊实打实'', ''199aba6d71d84c72ab06d61bae90891d'', NULL, NULL, ''1'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''JKSDJKL'', ''JianKuSaDanJianKuLasa'', NULL, ''1'', ''2022-09-13 10:26:57'', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''df3288d2098943cb8d71fa984d3ac0b9'', 7, ''中南教育中心'', ''010302'', ''412300'', ''中南教育中心'', ''a645cf35f68f4620a3d3753677dd98d3'', NULL, NULL, ''0'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''ZNJYZ'', ''ZhongNanJiaoYuZhongxin'', NULL, ''1'', ''2019-06-06 19:27:55'', ''1'', ''2019-06-06 19:33:04'', NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''e1ba0dbcf3cc4944a4845bb132e9726c'', NULL, ''信息工程学院'', ''0901'', ''412300'', ''信息工程学院'', ''408ea3a61e754f14900a15284020208f'', NULL, NULL, ''0'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''XXGCX'', ''XinXiGongChengXueyuan'', NULL, ''1'', ''2021-10-25 11:47:08'', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''f192eb50c07847d28a0bc02d3cf7532d'', NULL, ''张三儿子'', ''0100101'', ''123456'', ''张三儿子'', ''f63cd50bc14f43e2865d1a0c9a325307'', NULL, NULL, ''0'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''ZSEZ'', ''ZhangSanErZi'', NULL, ''1'', ''2022-09-13 10:26:34'', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''f63cd50bc14f43e2865d1a0c9a325307'', NULL, ''张三'', ''01001'', ''123456'', ''张三'', ''2116113e62d04f1bb5306b72b8ec963c'', NULL, NULL, ''0'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''Z'', ''Zhangsan'', NULL, ''1'', ''2022-09-13 10:03:38'', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''fac783801284490fb3cce1dc06c54e53'', NULL, ''中南大学'', ''06'', ''412304'', ''中南大学'', ''-1'', NULL, NULL, ''1'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''ZND'', ''ZhongNanDaxue'', NULL, ''1'', ''2020-09-23 10:44:34'', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''fccade7bb8ac4dcb8c56e5341437c786'', NULL, ''软件学院'', ''0301'', ''123456'', ''软件学院'', ''10865'', NULL, NULL, ''0'', NULL, NULL, NULL, NULL, NULL, NULL, ''1'', NULL, ''RJX'', ''RuanJianXueyuan'', NULL, ''1'', ''2020-09-24 11:31:17'', ''1'', ''2020-09-26 20:55:18'', NULL, NULL, NULL, NULL);
INSERT INTO `t_sys_org` VALUES (''fd5f006eacee4aafab29248649af2a92'', 8, ''望城教育中心'', ''010303'', ''430223'', ''望城教育中心'', ''a645cf35f68f4620a3d3753677dd98d3'', NULL, NULL, ''0'', ''长沙望城区郭亮南路100号'', '''', '''', '''', '''', '''', ''1'', '''', ''WCJYZ'', ''WangChengJiaoYuZhongxin'', NULL, ''1'', ''2018-02-27 20:28:08'', ''1'', ''2019-08-31 11:31:13'', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for t_sys_parameter
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_parameter`;
CREATE TABLE `t_sys_parameter`  (
  `PARAID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''主键'',
  `ISDEFAULT` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''是否默认'',
  `PARANAME` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''参数名称'',
  `PARANO` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''参数编码'',
  `PARA_KEY` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''参数值'',
  `PARA_TYPE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''参数类型'',
  `PARAORDER` int(0) NULL DEFAULT NULL COMMENT ''排序号'',
  `REMARK` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''描述'',
  `DISPLAYSORT` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''显示方式：下拉(select)、复选(checkbox)、单选(radio)'',
  `create_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建人'',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''修改时间'',
  PRIMARY KEY (`PARAID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''参数配置'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_parameter
-- ----------------------------
INSERT INTO `t_sys_parameter` VALUES (''03b0074668b3425ea832021b1cc0f9c0'', ''1'', ''岗位类型'', ''3'', ''编外人员'', ''postType'', 3, NULL, ''1'', ''1'', ''2019-08-31 11:35:24'', NULL, NULL);
INSERT INTO `t_sys_parameter` VALUES (''091c43797e324d7c9e444b411bcdaa0c'', ''0'', ''参数是否显示'', ''0'', ''隐藏'', ''displaying'', 2, '''', ''3'', ''1'', NULL, ''1'', NULL);
INSERT INTO `t_sys_parameter` VALUES (''2634443fe4bf41b588282d4bd47b7139'', ''1'', ''附件类型'', ''6'', ''新闻logo'', ''fileType'', 6, NULL, ''1'', ''1'', ''2019-07-25 11:22:41'', ''1'', ''2019-07-25 17:58:42'');
INSERT INTO `t_sys_parameter` VALUES (''2843901e86c94bc0a3740388429a98d1'', ''1'', ''参数是否显示'', ''1'', ''显示'', ''displaying'', 1, '''', ''3'', ''1'', NULL, ''1'', NULL);
INSERT INTO `t_sys_parameter` VALUES (''3a2b51854e32411c991bfa43f69d9e35'', ''0'', ''用户类型'', ''1'', ''外部'', ''userType'', 2, '''', ''1'', ''1'', NULL, ''1'', ''2019-06-18 11:14:15'');
INSERT INTO `t_sys_parameter` VALUES (''4a3c63fe93284958910c4befce1cd300'', ''1'', ''字典展示形式'', ''1'', ''列表结构'', ''displaySort'', 1, '''', ''1'', ''1'', NULL, ''1'', NULL);
INSERT INTO `t_sys_parameter` VALUES (''506305e60cb94814bf39e4604e151189'', ''1'', ''附件类型'', ''5'', ''广告图片'', ''fileType'', 5, NULL, ''1'', ''1'', ''2019-07-20 16:19:02'', ''1'', ''2019-07-25 17:58:14'');
INSERT INTO `t_sys_parameter` VALUES (''65df1fd9b2364407af798d62da9ce19b'', ''1'', ''附件类型'', ''8'', ''明星头像'', ''fileType'', 8, NULL, ''1'', ''1'', ''2019-07-25 18:00:06'', NULL, NULL);
INSERT INTO `t_sys_parameter` VALUES (''77685ec159e14656b1d0da2c16263375'', ''1'', ''附件类型'', ''10'', ''活教材封面图'', ''fileType'', 10, NULL, ''1'', ''1'', ''2019-07-26 14:23:51'', ''1'', ''2019-07-26 14:24:02'');
INSERT INTO `t_sys_parameter` VALUES (''9246fff7de4f47b593ba88c47beddd89'', ''1'', ''附件类型'', ''9'', ''企业logo'', ''fileType'', 9, NULL, ''1'', ''1'', ''2019-07-25 18:00:24'', NULL, NULL);
INSERT INTO `t_sys_parameter` VALUES (''9284990dadbe49f4861ada58dc1ff0a0'', ''0'', ''用户状态'', ''0'', ''禁用'', ''status'', 2, '''', ''1'', ''1'', NULL, ''1'', ''2019-06-19 11:25:40'');
INSERT INTO `t_sys_parameter` VALUES (''954ad30fb3074bae8ce090fc218c53ba'', ''1'', ''附件类型'', ''7'', ''教师头像'', ''fileType'', 7, NULL, ''1'', ''1'', ''2019-07-25 17:58:57'', NULL, NULL);
INSERT INTO `t_sys_parameter` VALUES (''9ce77bd7d6d14c2382b6f292a1dcfc4a'', ''1'', ''附件类型'', ''3'', ''系统设置'', ''fileType'', 3, NULL, ''1'', ''1'', ''2019-07-26 08:52:48'', NULL, NULL);
INSERT INTO `t_sys_parameter` VALUES (''a4710d4144604d398f10f58b26dac383'', ''1'', ''用户状态'', ''1'', ''正常'', ''status'', 1, '''', ''1'', ''1'', NULL, ''1'', ''2019-06-19 11:25:45'');
INSERT INTO `t_sys_parameter` VALUES (''ab556ac473cb494eaa9002f3c047b2cb'', ''0'', ''字典展示形式'', ''2'', ''树型结构'', ''displaySort'', 1, '''', ''1'', ''1'', NULL, ''1'', NULL);
INSERT INTO `t_sys_parameter` VALUES (''ad2ddbc0498b4eb2938eade892eda6e0'', ''1'', ''参数默认值'', ''1'', ''是'', ''isdefault'', 1, '''', ''3'', ''1'', NULL, ''1'', NULL);
INSERT INTO `t_sys_parameter` VALUES (''af8e5651b8ee431ebe1286b5f32cae65'', ''1'', ''编码每级长度'', ''3'', ''orgCodeLength'', ''codeLength'', NULL, '''', ''1'', ''1'', NULL, ''1'', NULL);
INSERT INTO `t_sys_parameter` VALUES (''b66380c0fa434502a17cf74d22764bb0'', ''1'', ''附件类型'', ''4'', ''站点栏目图片'', ''fileType'', 4, NULL, ''3'', ''1'', ''2019-07-20 11:07:39'', ''1'', ''2019-07-25 17:54:10'');
INSERT INTO `t_sys_parameter` VALUES (''d4cd23eae6024374a5b09c9d6194fe6b'', ''0'', ''参数默认值'', ''0'', ''否'', ''isdefault'', 2, '''', ''3'', ''1'', NULL, ''1'', NULL);
INSERT INTO `t_sys_parameter` VALUES (''e817c856f7e04991b25bbb036e8b4527'', ''1'', ''岗位类型'', ''1'', ''普通员工'', ''postType'', 1, '''', ''3'', ''1'', NULL, ''1'', ''2019-06-19 11:21:15'');
INSERT INTO `t_sys_parameter` VALUES (''f42db395ec624bafab493f0b535e4d40'', ''1'', ''用户类型'', ''0'', ''内部'', ''userType'', 1, '''', ''1'', ''1'', NULL, ''1'', ''2019-06-18 11:14:07'');
INSERT INTO `t_sys_parameter` VALUES (''fe57d9cb875443d6b45202a8f56d3695'', ''0'', ''岗位类型'', ''0'', ''公司领导'', ''postType'', 2, '''', ''3'', ''1'', NULL, ''1'', NULL);
INSERT INTO `t_sys_parameter` VALUES (''fe57d9cb875443d6b45892a8f53vgf95'', ''1'', ''附件类型'', ''2'', ''用户头像附件'', ''fileType'', 2, '''', ''3'', ''1'', ''2018-05-29 17:37:02'', ''1'', ''2019-05-29 09:13:11'');
INSERT INTO `t_sys_parameter` VALUES (''fe57d9cb875443d6b45892a8f53voif5'', ''1'', ''附件状态'', ''1'', ''已关联绑定'', ''fileState'', 2, '''', ''3'', ''1'', ''2018-05-29 17:37:02'', ''1'', ''2019-05-29 09:13:11'');
INSERT INTO `t_sys_parameter` VALUES (''fe57d9cb875443d6b45892a8f56d3520'', ''1'', ''附件状态'', ''0'', ''未关联绑定'', ''fileState'', 1, '''', ''3'', ''1'', ''2018-05-29 17:37:02'', ''1'', ''2019-05-29 09:13:11'');
INSERT INTO `t_sys_parameter` VALUES (''fe57d9cb875443d6b45892a8f56d3695'', ''1'', ''附件类型'', ''1'', ''字典附件'', ''fileType'', 1, '''', NULL, ''1'', ''2018-05-29 17:37:02'', ''1'', ''2019-07-20 16:19:30'');

-- ----------------------------
-- Table structure for t_sys_post
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_post`;
CREATE TABLE `t_sys_post`  (
  `POST_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''岗位ID'',
  `POST_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''岗位名称'',
  `REMARK` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''岗位描述'',
  `POST_TYPE` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''岗位类别:字典中定义'',
  `PARENT_POSTID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''父岗位ID'',
  `create_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建人'',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''修改时间'',
  `sort` int(0) NULL DEFAULT NULL COMMENT ''排序号'',
  PRIMARY KEY (`POST_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''岗位表'' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_sys_post
-- ----------------------------
INSERT INTO `t_sys_post` VALUES (''0beda90a28a7437aabc0809d8a1256dc'', ''2131321'', '''', ''2'', NULL, ''1'', ''2022-08-22 14:55:43'', NULL, NULL, 3);
INSERT INTO `t_sys_post` VALUES (''1680bfa034874a4093ddd581352b8fad'', ''231'', ''213'', ''2'', NULL, ''1'', ''2022-08-27 08:45:33'', ''1'', ''2022-09-06 14:49:49'', 5);
INSERT INTO `t_sys_post` VALUES (''1bf9f454de1744a68981d422e882114f'', ''12'', '''', ''3'', NULL, ''9ff07c2aaa2f49949c6c04e43741f8f4'', ''2022-09-13 11:30:59'', NULL, NULL, 7);
INSERT INTO `t_sys_post` VALUES (''230a9fd6d7f3409587f3bb594ca84294'', ''12'', '''', ''3'', NULL, ''9ff07c2aaa2f49949c6c04e43741f8f4'', ''2022-09-13 11:30:56'', NULL, NULL, 7);
INSERT INTO `t_sys_post` VALUES (''2e44bffe2b024c7db14be9eba381bd67'', ''12'', '''', ''3'', NULL, ''9ff07c2aaa2f49949c6c04e43741f8f4'', ''2022-09-13 11:31:00'', NULL, NULL, 7);
INSERT INTO `t_sys_post` VALUES (''3e57640b358542e3b79bb189df99ce4e'', ''12'', '''', ''3'', NULL, ''1'', ''2022-09-13 11:05:26'', NULL, NULL, 7);
INSERT INTO `t_sys_post` VALUES (''4dd67da715104cdfbe76f9c2ee401327'', ''12'', '''', ''3'', NULL, ''9ff07c2aaa2f49949c6c04e43741f8f4'', ''2022-09-13 11:20:30'', NULL, NULL, 7);
INSERT INTO `t_sys_post` VALUES (''6b669cec35c04cc5a936388eeefe6ffd'', ''12'', '''', ''3'', NULL, ''9ff07c2aaa2f49949c6c04e43741f8f4'', ''2022-09-13 11:09:12'', NULL, NULL, 7);
INSERT INTO `t_sys_post` VALUES (''a0ca81ecc17947f4b5e91a906f583b8a'', ''12'', '''', ''3'', NULL, ''9ff07c2aaa2f49949c6c04e43741f8f4'', ''2022-09-13 11:08:32'', NULL, NULL, 7);
INSERT INTO `t_sys_post` VALUES (''a0ecdc8e6b0742508cc76b1923201c73'', ''1231'', ''1231'', ''1'', NULL, ''1'', ''2022-08-27 08:45:28'', NULL, NULL, 4);
INSERT INTO `t_sys_post` VALUES (''b403833405d1412ca3170c09974e97ed'', ''12'', '''', ''3'', NULL, ''1'', ''2022-09-13 11:04:53'', NULL, NULL, 7);
INSERT INTO `t_sys_post` VALUES (''b9bd200881f84b0c867fa4f7bb93be31'', ''12313'', '''', ''3'', NULL, ''1'', ''2022-09-01 15:09:34'', NULL, NULL, 6);
INSERT INTO `t_sys_post` VALUES (''c43019ab3e0b4037ac540ac015e95fcf'', ''12'', '''', ''3'', NULL, ''9ff07c2aaa2f49949c6c04e43741f8f4'', ''2022-09-13 11:11:24'', NULL, NULL, 7);
INSERT INTO `t_sys_post` VALUES (''c9cbcb4c59f146d9901c0d2bd2ce67f8'', ''12311'', '''', ''3'', NULL, ''1'', ''2022-09-01 15:09:38'', NULL, NULL, 1);
INSERT INTO `t_sys_post` VALUES (''dd5a71cdfd764e91b1d2c52fdc62771c'', ''12'', '''', ''3'', NULL, ''9ff07c2aaa2f49949c6c04e43741f8f4'', ''2022-09-13 11:30:59'', NULL, NULL, 7);
INSERT INTO `t_sys_post` VALUES (''e8ff4fb0ef30419cb39ae942924284ed'', ''董事长'', '''', ''1'', NULL, ''1'', ''2022-08-22 14:55:47'', ''1'', ''2022-08-22 14:56:03'', 1);
INSERT INTO `t_sys_post` VALUES (''f40eb97dc098426bbd32d82cdb3614b6'', ''总经理'', '''', ''1'', NULL, ''1'', ''2022-08-17 14:01:29'', ''1'', ''2022-08-22 14:56:21'', 2);

-- ----------------------------
-- Table structure for t_sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_resource`;
CREATE TABLE `t_sys_resource`  (
  `menu_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''主键'',
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''父菜单ID，一级菜单为0'',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''菜单名称'',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''菜单URL'',
  `perms` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''授权(多个用逗号分隔，如：user:list,user:create)'',
  `type` int(0) NULL DEFAULT NULL COMMENT ''类型   0：目录   1：菜单   2：按钮'',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''菜单图标'',
  `order_num` int(0) NULL DEFAULT NULL COMMENT ''排序'',
  `ORG_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''机构ID'',
  `RESOURCE_CLASS` int(0) NULL DEFAULT NULL COMMENT ''层级'',
  `REMARK` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT ''资源描述'',
  `DISPLAY` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''是否可见'',
  `create_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建人'',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''修改时间'',
  `org_sn` int(0) NULL DEFAULT NULL COMMENT ''排序号'',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''菜单管理'' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_sys_resource
-- ----------------------------
INSERT INTO `t_sys_resource` VALUES (''009ec0088551486d8e6d94d1a51df3b4'', ''f9e8bed5cf6a485db552e0602741564c'', ''查看'', NULL, ''sys:tsysdict:view'', 2, NULL, 1, NULL, NULL, NULL, ''1'', ''1'', ''2018-03-28 11:58:38'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''04f3562771a744bdb70511f6ca63e1a7'', ''cfea91bab6b34fb396c0e37486f35bbd'', ''新增'', NULL, ''forum:tevglforumfriend:add'', 2, NULL, 1, NULL, NULL, NULL, ''1'', ''1'', ''2021-03-08 14:40:00'', ''1'', ''2021-03-08 14:40:17'', NULL);
INSERT INTO `t_sys_resource` VALUES (''05c93a7893754c279d7549e1e8c6270b'', ''26715d0dc99f4c92b61d97b9bc074312'', ''新增'', NULL, ''site:tevglsiteresourceext:add'', 2, NULL, 1, NULL, NULL, NULL, ''1'', ''1'', ''2019-07-16 08:57:25'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''05e3f0068784445582b521efd708e85d'', ''89bbe647e4a34ae98df0e31dfdff48d0'', ''删除'', NULL, ''site:tevglsiteupdatelog:remove'', 2, NULL, 3, NULL, NULL, NULL, ''1'', ''1'', ''2020-09-01 09:30:11'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''062e19317bf04d0dbb4c21a228de690a'', ''89bbe647e4a34ae98df0e31dfdff48d0'', ''修改'', NULL, ''site:tevglsiteupdatelog:edit'', 2, NULL, 2, NULL, NULL, NULL, ''1'', ''1'', ''2020-09-01 09:30:11'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''0dd44379d5224cd994f51cfcdb613027'', ''9243558bee9548bcb08ca596c37e8a5b'', ''查看'', NULL, ''site:tevglsitenews:view'', 2, NULL, 4, NULL, NULL, NULL, ''1'', ''1'', ''2019-07-20 14:10:24'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''0e0a4e29e34b4646a66d22cd5b876713'', ''b43e1950c19d40deb8b9b701ffec5f65'', ''栏目图片'', ''/site/tevglsiteavd'', ''site:tevglsiteavd:list,site:tevglsiteavd:query'', 1, ''fa fa-area-chart'', 2, '''', 0, NULL, ''1'', ''1'', ''2019-07-15 10:54:48'', ''1'', ''2019-07-22 10:18:49'', NULL);
INSERT INTO `t_sys_resource` VALUES (''0e32bdae84a94c5f9bf81c619aa300bd'', ''82c786f2bfbf46398e3b495f6c70156c'', ''上下移'', NULL, ''sys:menu:move'', 2, NULL, 1, NULL, 0, NULL, ''1'', ''1'', ''2019-06-18 09:18:59'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''0e8caaa28af741f2ad4fba17725fb5c0'', ''75b1c7687ba341e1b2d5f16992d03dde'', ''修改'', NULL, ''tch:tevgltchclassroom:edit'', 2, NULL, 2, NULL, NULL, NULL, ''1'', ''1'', ''2021-09-11 17:54:15'', ''1'', ''2021-09-11 17:54:33'', NULL);
INSERT INTO `t_sys_resource` VALUES (''1'', ''19c786f2bfbf46398e3b495f6c7014b1'', ''系统管理'', '''', '''', 0, ''fa  fa-th-large'', 2, '''', NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2019-06-19 08:33:10'', NULL);
INSERT INTO `t_sys_resource` VALUES (''11bc5f0d88e94f1d817aa535892b2530'', ''538ecb94406f46f4862748f0c4ed847c'', ''新增'', NULL, ''tch:tevgltchclasstrainee:add'', 2, NULL, 1, NULL, NULL, NULL, ''1'', ''1'', ''2020-10-27 15:42:39'', ''1'', ''2020-10-27 15:42:49'', NULL);
INSERT INTO `t_sys_resource` VALUES (''11c786f2bfbf46398e3b495f6c7014bc'', ''99c786f2bfbf46398e3b495f6c7014bc'', ''暂停'', NULL, ''sys:job:pause'', 2, NULL, 0, NULL, NULL, NULL, ''1'', ''1'', NULL, ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''12c786f2bfbf46398e3b495f6c7014bc'', ''99c786f2bfbf46398e3b495f6c7014bc'', ''恢复'', NULL, ''sys:job:resume'', 2, NULL, 0, NULL, NULL, NULL, ''1'', ''1'', NULL, ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''137d86ffc32a44b4ae2b43b2f014c5c8'', ''dc86ce2ecef34cbea905ad78305e9e10'', ''删除'', NULL, ''sys:tsyspost:remove'', 2, NULL, 4, NULL, NULL, NULL, ''1'', ''1'', ''2019-06-12 14:51:52'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''13c786f2bfbf46398e3b495f6c7014bc'', ''99c786f2bfbf46398e3b495f6c7014bc'', ''立即执行'', NULL, ''sys:job:run'', 2, NULL, 0, NULL, NULL, NULL, ''1'', ''1'', NULL, ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''14c786f2bfbf46398e3b495f6c7014bc'', ''99c786f2bfbf46398e3b495f6c7014bc'', ''日志列表'', NULL, ''sys:job:log'', 2, NULL, 0, NULL, NULL, NULL, ''1'', ''1'', NULL, ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''1561d13cabc648b29ec45d2b9764b69d'', ''93d786f2bfbf46398e3b495f6c70156c'', ''查询'', ''sys/role/query'', ''sys:role:query'', 2, '''', 1, '''', NULL, NULL, ''1'', ''1'', ''2018-03-28 11:42:44'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''15d786f2bfbf46398e3b495f6c70156c'', ''92c786f2bfbf46398e3b495f6c70156c'', ''查看'', NULL, ''sys:tsysuserinfo:list,sys:user:info'', 2, NULL, 0, NULL, NULL, NULL, ''1'', ''1'', NULL, ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''16d786f2bfbf46398e3b495f6c70156c'', ''92c786f2bfbf46398e3b495f6c70156c'', ''新增'', '''', ''sys:tsysuserinfo:add,sys:role:view'', 2, '''', 1, '''', NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2018-03-28 11:02:56'', NULL);
INSERT INTO `t_sys_resource` VALUES (''17d786f2bfbf46398e3b495f6c70156c'', ''92c786f2bfbf46398e3b495f6c70156c'', ''修改'', NULL, ''sys:tsysuserinfo:edit,sys:role:view'', 2, NULL, 0, NULL, NULL, NULL, ''1'', ''1'', NULL, ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''180108b6b3a044c9bfb0123fdf60ab88'', ''d552fe2536d64166b69b29705e3bf6b8'', ''删除'', NULL, ''tch:tevgltchteacher:remove'', 2, NULL, 3, NULL, NULL, NULL, ''1'', ''1'', ''2019-07-22 15:46:36'', ''1'', ''2019-07-22 15:54:07'', NULL);
INSERT INTO `t_sys_resource` VALUES (''18d786f2bfbf46398e3b495f6c70156c'', ''92c786f2bfbf46398e3b495f6c70156c'', ''删除'', NULL, ''sys:tsysuserinfo:remove'', 2, NULL, 0, NULL, NULL, NULL, ''1'', ''1'', NULL, ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''19c786f2bfbf46398e3b495f6c7014b1'', ''-1'', ''教学实训云平台'', '''', '''', -1, ''fa  fa-windows fa-lg fa-spin'', 1, '''', NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2021-11-19 20:22:24'', NULL);
INSERT INTO `t_sys_resource` VALUES (''19d786f2bfbf46398e3b495f6c70156c'', ''93d786f2bfbf46398e3b495f6c70156c'', ''查看'', NULL, ''sys:role:list,sys:role:info,sys:role:edit'', 2, NULL, 1, NULL, NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2019-07-02 14:52:37'', NULL);
INSERT INTO `t_sys_resource` VALUES (''1cc48ea31aa14f58a3a55b004568aa81'', ''cfea91bab6b34fb396c0e37486f35bbd'', ''保存分类'', NULL, ''forum:tevglforumfriendtype:add'', 2, NULL, 5, NULL, 0, NULL, ''1'', ''1'', ''2021-04-15 14:56:05'', ''1'', ''2021-04-15 15:04:22'', NULL);
INSERT INTO `t_sys_resource` VALUES (''20d786f2bfbf46398e3b495f6c70156c'', ''93d786f2bfbf46398e3b495f6c70156c'', ''新增'', '''', ''sys:role:add,sys:menu:perms'', 2, '''', 1, '''', NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2018-03-28 11:35:18'', NULL);
INSERT INTO `t_sys_resource` VALUES (''2222226398e3b495f6c7014b1'', ''-1'', ''实训资源库'', '''', '''', -1, ''fa  fa-jsfiddle fa-lg fa-spin'', 2, '''', NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2019-06-14 17:42:30'', NULL);
INSERT INTO `t_sys_resource` VALUES (''22364ccf144d4488b0a5d646f2334438'', ''92c786f2bfbf46398e3b495f6c70156c'', ''清空权限'', ''sys/tsysuserinfo/clearpermissions'', ''sys:tsysuserinfo:clear'', 2, '''', 8, '''', NULL, NULL, ''1'', ''1'', ''2018-03-28 11:24:16'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''23b7fb277d5e4027b2ee225597e64b82'', ''e517523468cf41bab3d77b7aa3fa4065'', ''行业资讯'', '''', '''', 4, ''fa fa-linode'', 1, '''', NULL, NULL, ''1'', ''1'', ''2019-07-20 14:20:19'', ''1'', ''2019-08-08 16:44:32'', NULL);
INSERT INTO `t_sys_resource` VALUES (''244e028e577949d7881c9db60419bf55'', ''75b1c7687ba341e1b2d5f16992d03dde'', ''查看'', NULL, ''tch:tevgltchclassroom:view'', 2, NULL, 4, NULL, NULL, NULL, ''1'', ''1'', ''2021-09-11 17:54:15'', ''1'', ''2021-09-11 17:55:13'', NULL);
INSERT INTO `t_sys_resource` VALUES (''24e2dd79b05046de99da63abb8a3ba39'', ''f9e8bed5cf6a485db552e0602741894c'', ''查看'', NULL, ''sys:tsysloginlog:view'', 2, NULL, 1, NULL, NULL, NULL, ''0'', ''1'', ''2019-05-28 06:54:01'', ''1'', ''2019-06-15 08:57:35'', NULL);
INSERT INTO `t_sys_resource` VALUES (''2521e2ec0aeb40ed8730389bd1b79502'', ''99c786f2bfbf46399e3b495f6c7014bc'', ''修改'', NULL, ''sys:tsysorg:edit'', 2, NULL, 3, NULL, NULL, NULL, ''1'', ''1'', ''2018-03-28 11:11:04'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''25d786f2bfbf46398e3b495f6c70156c'', ''82c786f2bfbf46398e3b495f6c70156c'', ''修改'', NULL, ''sys:menu:edit,sys:menu:view'', 2, NULL, 0, NULL, NULL, NULL, ''1'', ''1'', NULL, ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''26715d0dc99f4c92b61d97b9bc074312'', ''b43e1950c19d40deb8b9b701ffec5f65'', ''站点栏目'', ''/site/tevglsiteresourceext'', ''site:tevglsiteresourceext:list,site:tevglsiteresourceext:query'', 1, ''fa fa-edge'', 1, '''', 0, NULL, ''1'', ''1'', ''2019-07-15 11:07:05'', ''1'', ''2019-07-22 10:18:25'', NULL);
INSERT INTO `t_sys_resource` VALUES (''26e0c47fd2694249af81767d45f25a40'', ''92c786f2bfbf46398e3b495f6c70156c'', ''重置密码'', ''sys/tsysuserinfo/resetpassword'', ''sys:tsysuserinfo:reset'', 2, '''', 6, '''', NULL, NULL, ''1'', ''1'', ''2018-03-28 11:23:21'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''2716eca246ee42bfb19b129ccdf569b6'', ''0e0a4e29e34b4646a66d22cd5b876713'', ''修改'', NULL, ''site:tevglsiteavd:edit'', 2, NULL, 2, NULL, NULL, NULL, ''1'', ''1'', ''2019-07-15 10:59:24'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''28077fd8d6d24bdebbb28a3aa4c195ce'', ''f9e8bed5cf6a485db552e0602741845c'', ''查询'', '''', ''sys:tsysattach:query'', 2, '''', 1, '''', NULL, NULL, ''1'', ''1'', ''2018-03-28 11:57:26'', ''1'', ''2018-03-28 11:57:53'', NULL);
INSERT INTO `t_sys_resource` VALUES (''29ce103136694ccdaf437d160a939aaa'', ''cb09c1957521474dae30b4768b74bc8e'', ''修改'', NULL, ''medu:tmedumediaavd:edit'', 2, NULL, 2, NULL, NULL, NULL, ''1'', ''1'', ''2020-09-02 11:55:30'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''2b64ea278d23403f8b30d4405c2d9673'', ''5b53877cef0f4eeaadd7f96dc87273ae'', ''新增'', NULL, ''book:tevglbookmajor:add'', 2, NULL, 1, NULL, NULL, NULL, ''1'', ''1'', ''2019-07-30 17:07:02'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''316b953d58604d2c8405dc5951de787c'', ''5bb687aa97dd4084871bdc700789b5c4'', ''活教材'', ''https://www.budaos.com/#/library'', '''', 4, ''fa fa-file'', 2, '''', NULL, NULL, ''1'', ''1'', ''2019-07-18 09:16:20'', ''1'', ''2019-08-31 09:46:17'', NULL);
INSERT INTO `t_sys_resource` VALUES (''3409b6acc15d46caad2feb7348c77693'', ''5b53877cef0f4eeaadd7f96dc87273ae'', ''维护活教材'', NULL, ''book:tevglbookmajor:content'', 2, NULL, 5, NULL, 0, NULL, ''1'', ''ec7a75b6122d4f9b8c7b08459b3c4b3f'', ''2021-11-08 18:07:41'', ''ec7a75b6122d4f9b8c7b08459b3c4b3f'', ''2021-11-08 18:08:00'', NULL);
INSERT INTO `t_sys_resource` VALUES (''34d786f2bfbf46398e3b495f6c70156c'', ''82c786f2bfbf46398e3b495f6c70156c'', ''新增'', NULL, ''sys:menu:save,sys:menu:view,sys:menu:add'', 2, NULL, 0, NULL, NULL, NULL, ''1'', ''1'', NULL, ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''3639e81f68a3469aae5254c7510ccf1a'', ''83d786f2bfbf46398e3b495f6c70156c'', ''新增'', NULL, ''sys:tsysparameter:add'', 2, NULL, 2, NULL, NULL, NULL, ''1'', ''1'', ''2018-03-28 11:59:41'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''371d98578b7c4be1867aa6b5e2d67031'', ''e0904dc937b2446ab4e4f04512c00070'', ''班级管理'', ''/res/tevgltchclass'', ''tch:tevgltchclass:query'', 1, ''fa fa-id-card-o'', 3, NULL, 0, NULL, ''1'', ''1'', ''2019-08-19 17:49:14'', ''1'', ''2020-10-15 09:10:16'', NULL);
INSERT INTO `t_sys_resource` VALUES (''3f1bfc13843b43efb6ec6ee97eb0b06f'', ''538ecb94406f46f4862748f0c4ed847c'', ''导入'', ''1'', ''tch:tevgltchclasstrainee:import'', 2, NULL, 5, NULL, 0, NULL, ''1'', ''1'', ''2020-10-27 15:43:31'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''3fb6ba51ca0d492da54fe37b8184e83c'', ''99c786f2bfbf46399e3b495f6c7014bc'', ''删除'', NULL, ''sys:tsysorg:remove'', 2, NULL, 4, NULL, NULL, NULL, ''1'', ''1'', ''2018-03-28 11:11:04'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''415052f1f6344a37af12a2a3d024b1a9'', ''371d98578b7c4be1867aa6b5e2d67031'', ''新增'', NULL, ''tch:tevgltchclass:add'', 2, NULL, 1, NULL, NULL, NULL, ''1'', ''1'', ''2019-08-19 17:49:32'', ''1'', ''2019-08-19 17:49:40'', NULL);
INSERT INTO `t_sys_resource` VALUES (''42f49a7a91f24a0aa81e2eb48ebfcb83'', ''f9e8bed5cf6a485db552e0602741894c'', ''新增'', NULL, ''sys:tsysloginlog:add'', 2, NULL, 2, NULL, NULL, NULL, ''1'', ''1'', ''2019-05-28 06:54:01'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''4427179c78ba46aa9c08cda5eab868f8'', ''e517523468cf41bab3d77b7aa3fa4065'', ''站内公告'', '''', '''', 4, ''fa fa-meetup'', 1, '''', NULL, NULL, ''1'', ''1'', ''2019-07-20 14:19:56'', ''1'', ''2019-08-03 09:40:20'', NULL);
INSERT INTO `t_sys_resource` VALUES (''44729d3a6b7841cfa6fc4fbaa6bf0c03'', ''9243558bee9548bcb08ca596c37e8a5b'', ''审核'', NULL, ''site:tevglsitenews:check'', 2, NULL, 5, NULL, 0, NULL, ''1'', ''1'', ''2019-08-17 08:29:07'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''44d786f2bfbf46398e3b495f6c70156c'', ''93d786f2bfbf46398e3b495f6c70156c'', ''修改'', NULL, ''sys:role:edit,sys:menu:perms'', 2, NULL, 0, NULL, NULL, NULL, ''1'', ''1'', NULL, ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''46cb1e473c064d0c817d7093fb1c6fa8'', ''5b53877cef0f4eeaadd7f96dc87273ae'', ''修改'', NULL, ''book:tevglbookmajor:edit'', 2, NULL, 2, NULL, NULL, NULL, ''1'', ''1'', ''2019-07-30 17:07:02'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''4928266986db47dbbe11380b6455010a'', ''bcc3290a69c94b1dbeb6e042ffa75780'', ''新增'', NULL, ''site:tevglsitevideo:add'', 2, NULL, 1, NULL, NULL, NULL, ''1'', ''1'', ''2021-09-11 17:51:30'', ''1'', ''2021-09-11 17:51:51'', NULL);
INSERT INTO `t_sys_resource` VALUES (''4a32326f63a14145bc9ed60421014305'', ''-1'', ''智能分析与评价'', NULL, NULL, -1, ''fa fa-bar-chart fa-lg fa-spin'', 4, '''', 0, NULL, ''1'', ''1'', ''2019-06-19 17:27:10'', ''1'', ''2019-06-19 17:33:13'', NULL);
INSERT INTO `t_sys_resource` VALUES (''4bee5249bda9446f9a6364d2c2fe485e'', ''5b53877cef0f4eeaadd7f96dc87273ae'', ''移交教学包'', NULL, ''pkg:tevglpkginfo:changePackage'', 2, NULL, 6, NULL, 0, NULL, ''1'', ''ec7a75b6122d4f9b8c7b08459b3c4b3f'', ''2021-11-06 17:28:30'', ''ec7a75b6122d4f9b8c7b08459b3c4b3f'', ''2021-11-08 18:08:05'', NULL);
INSERT INTO `t_sys_resource` VALUES (''4ee18677f7c5488280fb5638b56b0790'', ''f9e8bed5cf6a485db552e0602741894c'', ''修改'', NULL, ''sys:tsysloginlog:edit'', 2, NULL, 3, NULL, NULL, NULL, ''1'', ''1'', ''2019-05-28 06:54:01'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''4f878d16bad64263870afb77132ce93c'', ''9243558bee9548bcb08ca596c37e8a5b'', ''新增'', NULL, ''site:tevglsitenews:add'', 2, NULL, 1, NULL, NULL, NULL, ''1'', ''1'', ''2019-07-20 14:10:24'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''52d786f2bfbf46398e3b495f6c70156c'', ''93d786f2bfbf46398e3b495f6c70156c'', ''删除'', NULL, ''sys:role:remove'', 2, NULL, 0, NULL, NULL, NULL, ''1'', ''1'', NULL, ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''5339287c76674b449f7d9e2879e49758'', ''e0904dc937b2446ab4e4f04512c00070'', ''课表编排'', ''/res/Tevgltchschedule'', NULL, 1, ''fa fa-calendar'', 9, NULL, 0, NULL, ''1'', ''1'', ''2021-11-17 14:34:21'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''538ecb94406f46f4862748f0c4ed847c'', ''e0904dc937b2446ab4e4f04512c00070'', ''班级成员管理'', ''/res/tevgltchclasstrainee'', ''tch:tevgltchclasstrainee:query'', 1, ''fa fa-grav'', 4, NULL, 0, NULL, ''1'', ''1'', ''2020-10-15 20:40:44'', ''1'', ''2020-10-27 15:42:35'', NULL);
INSERT INTO `t_sys_resource` VALUES (''5752544b0992428184173fa0e6e1056b'', ''bcc3290a69c94b1dbeb6e042ffa75780'', ''查看'', NULL, ''site:tevglsitevideo:view'', 2, NULL, 4, NULL, NULL, NULL, ''1'', ''1'', ''2021-09-11 17:51:31'', ''1'', ''2021-09-11 17:53:31'', NULL);
INSERT INTO `t_sys_resource` VALUES (''5a786f8eb6b94f0a8eff667da8d0f6b6'', ''5bb687aa97dd4084871bdc700789b5c4'', ''我要学习'', ''https://www.budaos.com/#/HearClass'', '''', 4, ''fa fa-user-circle'', 4, '''', NULL, NULL, ''1'', ''1'', ''2019-07-18 09:18:11'', ''1'', ''2020-09-28 17:24:53'', NULL);
INSERT INTO `t_sys_resource` VALUES (''5a9d2b6fa08840ce8951304416e6bbce'', ''9243558bee9548bcb08ca596c37e8a5b'', ''删除'', NULL, ''site:tevglsitenews:remove'', 2, NULL, 3, NULL, NULL, NULL, ''1'', ''1'', ''2019-07-20 14:10:24'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''5b53877cef0f4eeaadd7f96dc87273ae'', ''e0904dc937b2446ab4e4f04512c00070'', ''职业课程'', ''/res/tevglbookmajor'', ''book:tevglbookmajor:query'', 1, ''fa fa-cubes'', 1, NULL, 0, NULL, ''1'', ''1'', ''2019-07-30 17:06:32'', ''1'', ''2020-10-15 09:06:58'', NULL);
INSERT INTO `t_sys_resource` VALUES (''5b6c71fcefb643d6807d411b95690d43'', ''f9e8bed5cf6a485db552e0602741894c'', ''删除'', NULL, ''sys:tsysloginlog:remove'', 2, NULL, 4, NULL, NULL, NULL, ''0'', ''1'', ''2019-05-28 06:54:01'', ''1'', ''2019-06-15 08:57:41'', NULL);
INSERT INTO `t_sys_resource` VALUES (''5bb687aa97dd4084871bdc700789b5c4'', ''0'', ''布道师'', '''', '''', 3, '''', 1, '''', NULL, NULL, ''1'', ''1'', ''2019-07-18 09:15:31'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''5ffcaf9d565b44ba9c5f58468678437d'', ''83d786f2bfbf46398e3b495f6c70156c'', ''修改'', NULL, ''sys:tsysparameter:edit'', 2, NULL, 3, NULL, NULL, NULL, ''1'', ''1'', ''2018-03-28 11:59:41'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''60896726a3024aedad02b405677c20f5'', ''9243558bee9548bcb08ca596c37e8a5b'', ''修改'', NULL, ''site:tevglsitenews:edit'', 2, NULL, 2, NULL, NULL, NULL, ''1'', ''1'', ''2019-07-20 14:10:24'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''64d756f2b12f46111e3b495f6c70156c'', ''82c786f2bfbf46398e3b495f6c70156c'', ''复制'', NULL, ''sys:menu:list,sys:menu:copy'', 2, NULL, 0, NULL, NULL, NULL, ''1'', ''1'', NULL, ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''64d756f2b12f46458e3b495f6c70156c'', ''82c786f2bfbf46398e3b495f6c70156c'', ''生成'', NULL, ''sys:menu:list,sys:menu:init'', 2, NULL, 0, NULL, NULL, NULL, ''1'', ''1'', NULL, ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''64d756f2bfbf46398e3b495f6c70156c'', ''82c786f2bfbf46398e3b495f6c70156c'', ''查看'', NULL, ''sys:menu:list,sys:menu:info'', 2, NULL, 0, NULL, NULL, NULL, ''1'', ''1'', NULL, ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''67474aaee9794fbc8bf766148a81cf91'', ''99c786f2bfbf46399e3b495f6c7014bc'', ''新增'', NULL, ''sys:tsysorg:add'', 2, NULL, 2, NULL, NULL, NULL, ''1'', ''1'', ''2018-03-28 11:11:04'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''692e2140dbe249e4bb508e2184a3dff9'', ''92c786f2bfbf46398e3b495f6c70156c'', ''分配特权'', ''sys/tsysuserinfo/to_permpage'', ''sys:tsysuserinfo:perm'', 2, '''', 9, '''', NULL, NULL, ''1'', ''1'', ''2018-03-28 11:25:05'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''70b76a59ba8044a3ba2fac6024057aa4'', ''f9e8bed5cf6a485db552e0602741845c'', ''删除'', NULL, ''sys:tsysattach:remove'', 2, NULL, 4, NULL, NULL, NULL, ''1'', ''1'', ''2018-03-28 11:57:26'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''7231e9cc583840bc85a432616b9061b9'', ''d552fe2536d64166b69b29705e3bf6b8'', ''查看'', NULL, ''tch:tevgltchteacher:view'', 2, NULL, 4, NULL, NULL, NULL, ''1'', ''1'', ''2019-07-22 15:46:36'', ''1'', ''2019-07-22 15:54:14'', NULL);
INSERT INTO `t_sys_resource` VALUES (''73d786f2bfbf46398e3b495f6c70256c'', ''cc3e5f98fb9d4c83bbb29332f4b0d85e'', ''操作日志'', ''/sys/log'', ''sys:tsyslog:query,sys:tsyslog:remove,sys:tsyslog:add'', 1, ''el-icon-document'', 7, '''', NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2019-06-01 08:49:06'', NULL);
INSERT INTO `t_sys_resource` VALUES (''741e47570019484ab7498e5515bb817c'', ''26715d0dc99f4c92b61d97b9bc074312'', ''修改'', NULL, ''site:tevglsiteresourceext:edit'', 2, NULL, 2, NULL, NULL, NULL, ''1'', ''1'', ''2019-07-16 08:57:25'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''75b1c7687ba341e1b2d5f16992d03dde'', ''e0904dc937b2446ab4e4f04512c00070'', ''课堂管理'', ''/res/tevgltchclassroom'', ''tch:tevgltchclassroom:query'', 1, ''fa fa-microchip'', 8, NULL, 0, NULL, ''1'', ''1'', ''2021-09-11 17:54:06'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''771c483b89e248a1adb261777ebd9d98'', ''dc86ce2ecef34cbea905ad78305e9e10'', ''查看'', NULL, ''sys:tsyspost:view'', 2, NULL, 1, NULL, NULL, NULL, ''1'', ''1'', ''2019-06-12 14:51:52'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''7a745f3f764e4e29925bd153680b534a'', ''75b1c7687ba341e1b2d5f16992d03dde'', ''移交'', NULL, ''tch:tevgltchclassroom:turnover'', 2, NULL, 5, NULL, 0, NULL, ''1'', ''1'', ''2021-09-11 17:55:03'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''7c140a2910214d449c891b88991f0850'', ''cb09c1957521474dae30b4768b74bc8e'', ''删除'', NULL, ''medu:tmedumediaavd:remove'', 2, NULL, 3, NULL, NULL, NULL, ''1'', ''1'', ''2020-09-02 11:55:30'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''7e83b9c3d46c47aa96c233bc4bb6ff3d'', ''538ecb94406f46f4862748f0c4ed847c'', ''删除'', NULL, ''tch:tevgltchclasstrainee:remove'', 2, NULL, 3, NULL, NULL, NULL, ''1'', ''1'', ''2020-10-27 15:42:39'', ''1'', ''2020-10-27 15:43:03'', NULL);
INSERT INTO `t_sys_resource` VALUES (''7f27e168a219489b97c9bb50c59b562f'', ''cb09c1957521474dae30b4768b74bc8e'', ''新增'', NULL, ''medu:tmedumediaavd:add'', 2, NULL, 1, NULL, NULL, NULL, ''1'', ''1'', ''2020-09-02 11:55:30'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''82c786f2bfbf46398e3b495f6c70156c'', ''cc3e5f98fb9d4c83bbb29332f4b0d85e'', ''资源模块'', ''/sys/menu'', '''', 1, ''el-icon-service'', 3, '''', NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2019-06-19 08:31:45'', NULL);
INSERT INTO `t_sys_resource` VALUES (''83d786f2bfbf46398e3b495f6c70156c'', ''cc3e5f98fb9d4c83bbb29332f4b0d85e'', ''全局参数'', ''/sys/parameter'', ''sys:tsysparameter:query'', 1, ''el-icon-s-tools'', 6, '''', NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2019-06-19 08:31:57'', NULL);
INSERT INTO `t_sys_resource` VALUES (''8830ad960cc64a6e925e92a924c12743'', ''83d786f2bfbf46398e3b495f6c70156c'', ''全局配置'', NULL, ''sys:settings:edit'', 2, NULL, 5, NULL, 0, NULL, ''1'', ''1'', ''2019-06-21 11:39:37'', ''1'', ''2019-06-24 15:03:25'', NULL);
INSERT INTO `t_sys_resource` VALUES (''88935b2549474f5b97635a94a76961f9'', ''83d786f2bfbf46398e3b495f6c70156c'', ''删除'', NULL, ''sys:tsysparameter:remove'', 2, NULL, 4, NULL, NULL, NULL, ''1'', ''1'', ''2018-03-28 11:59:41'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''89bbe647e4a34ae98df0e31dfdff48d0'', ''dfbb34201ac74e48b24585de9c5cc9da'', ''更新日志'', ''/site/tevglsiteupdatelog'', ''site:tevglsiteupdatelog:query'', 1, ''fa fa-list-ol'', 7, NULL, 0, NULL, ''1'', ''1'', ''2020-09-01 09:30:08'', ''1'', ''2020-09-23 10:01:07'', NULL);
INSERT INTO `t_sys_resource` VALUES (''8d257582b0c54e0b987118903613d427'', ''dc86ce2ecef34cbea905ad78305e9e10'', ''新增'', NULL, ''sys:tsyspost:add'', 2, NULL, 2, NULL, NULL, NULL, ''1'', ''1'', ''2019-06-12 14:51:52'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''8f79304e60d0490a809842b42714e9d8'', ''75b1c7687ba341e1b2d5f16992d03dde'', ''设置'', NULL, ''tch:tevgltchclassroom:set'', 2, NULL, 6, NULL, 0, NULL, ''1'', ''ec7a75b6122d4f9b8c7b08459b3c4b3f'', ''2022-04-23 11:01:59'', ''1'', ''2022-04-23 15:08:26'', NULL);
INSERT INTO `t_sys_resource` VALUES (''909a6a0f44634a5bbaff870171042414'', ''99c786f2bfbf46399e3b495f6c7014bc'', ''上移'', NULL, ''sys:tsysorg:move'', 2, NULL, 1, NULL, 0, NULL, ''1'', ''1'', ''2019-06-17 16:12:48'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''910786f2bfbf46398e3b495f6c7014bc'', ''99c786f2bfbf46398e3b495f6c7014bc'', ''删除'', NULL, ''sys:job:remove'', 2, NULL, 0, NULL, NULL, NULL, ''1'', ''1'', NULL, ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''91d786f2bfbf46398e3b495f6c7014bc'', ''99c786f2bfbf46398e3b495f6c7014bc'', ''新增'', NULL, ''sys:job:add'', 2, NULL, 1, NULL, NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2019-06-28 09:40:17'', NULL);
INSERT INTO `t_sys_resource` VALUES (''921786f2bfbf46398e3b495f6c7014bc'', ''99c786f2bfbf46398e3b495f6c7014bc'', ''修改'', NULL, ''sys:job:edit'', 2, NULL, 0, NULL, NULL, NULL, ''1'', ''1'', NULL, ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''9243558bee9548bcb08ca596c37e8a5b'', ''b43e1950c19d40deb8b9b701ffec5f65'', ''新闻资讯'', ''/site/tevglsitenews'', ''site:tevglsitenews:list,site:tevglsitenews:query'', 1, ''fa fa-superpowers'', 3, '''', 0, NULL, ''1'', ''1'', ''2019-07-20 14:03:38'', ''1'', ''2019-07-22 10:18:58'', NULL);
INSERT INTO `t_sys_resource` VALUES (''92c786f2bfbf46398e3b495f6c70156c'', ''1'', ''用户管理'', ''/sys/user'', ''sys:tsysuserinfo:query,sys:tsysuserinfo:view,sys:tsysuserinfo:list'', 1, ''fa fa-address-card-o'', 3, '''', NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2019-06-19'', NULL);
INSERT INTO `t_sys_resource` VALUES (''93d2420cda4042a0a4efef67a07b7f1f'', ''d552fe2536d64166b69b29705e3bf6b8'', ''新增'', NULL, ''tch:tevgltchteacher:add'', 2, NULL, 1, NULL, NULL, NULL, ''1'', ''1'', ''2019-07-22 15:46:36'', ''1'', ''2019-07-22 15:53:52'', NULL);
INSERT INTO `t_sys_resource` VALUES (''93d786f2bfbf46398e3b495f6c70156c'', ''1'', ''角色管理'', ''/sys/role'', ''sys:role:list'', 1, ''fa fa-user-circle'', 3, '''', NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2019-06-19'', NULL);
INSERT INTO `t_sys_resource` VALUES (''93d787f2bfbf46398e3b495f6c70156c'', ''82c786f2bfbf46398e3b495f6c70156c'', ''删除'', NULL, ''sys:menu:remove'', 2, NULL, 0, NULL, NULL, NULL, ''1'', ''1'', NULL, ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''95y786f2bfbf46398e3b495f6c7014bc'', ''99c786f2bfbf46398e3b495f6c7014bc'', ''查看'', NULL, ''sys:job:list,sys:job:info'', 2, NULL, 0, NULL, NULL, NULL, ''1'', ''1'', NULL, ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''99c786f2bfbf46398e3b495f6c7014bc'', ''cc3e5f98fb9d4c83bbb29332f4b0d85e'', ''定时任务'', ''/sys/job'', ''sys:job:query'', 1, ''el-icon-date'', 5, '''', NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2018-03-28 11:59:31'', NULL);
INSERT INTO `t_sys_resource` VALUES (''99c786f2bfbf46399e3b495f6c7014bc'', ''1'', ''组织机构'', ''/sys/dept'', ''sys:tsysorg:query,sys:tsysorg:view'', 1, ''fa  fa-sitemap'', 1, '''', NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2019-06-27 09:01:09'', NULL);
INSERT INTO `t_sys_resource` VALUES (''9d980791b9fc4d76805df6450a3602a1'', ''538ecb94406f46f4862748f0c4ed847c'', ''修改'', NULL, ''tch:tevgltchclasstrainee:edit'', 2, NULL, 2, NULL, NULL, NULL, ''1'', ''1'', ''2020-10-27 15:42:39'', ''1'', ''2020-10-27 15:42:56'', NULL);
INSERT INTO `t_sys_resource` VALUES (''9e9f07dd3ade46619c91bb9ef36167cc'', ''d552fe2536d64166b69b29705e3bf6b8'', ''修改'', NULL, ''tch:tevgltchteacher:edit'', 2, NULL, 2, NULL, NULL, NULL, ''1'', ''1'', ''2019-07-22 15:46:36'', ''1'', ''2019-07-22 15:54:00'', NULL);
INSERT INTO `t_sys_resource` VALUES (''9ea26f0b383f4648aafba86ba96c9f46'', ''75b1c7687ba341e1b2d5f16992d03dde'', ''结束'', NULL, ''tch:tevgltchclassroom:end'', 2, NULL, 3, NULL, NULL, NULL, ''1'', ''1'', ''2021-09-11 17:54:15'', ''1'', ''2021-09-11 17:54:49'', NULL);
INSERT INTO `t_sys_resource` VALUES (''a2112c6792aa45ba8855dc8acbc88b4a'', ''538ecb94406f46f4862748f0c4ed847c'', ''查看'', NULL, ''tch:tevgltchclasstrainee:view'', 2, NULL, 4, NULL, NULL, NULL, ''1'', ''1'', ''2020-10-27 15:42:39'', ''1'', ''2020-10-27 15:43:09'', NULL);
INSERT INTO `t_sys_resource` VALUES (''a5f98a3c6d15451d931398bf529424e8'', ''5bb687aa97dd4084871bdc700789b5c4'', ''我要开课'', ''https://www.budaos.com/#/beginClass'', '''', 4, ''fa fa-american-sign-language-interpreting'', 3, '''', NULL, NULL, ''1'', ''1'', ''2019-07-18 09:17:21'', ''1'', ''2020-09-28 17:24:28'', NULL);
INSERT INTO `t_sys_resource` VALUES (''aa1873b519bb406e851a181e7d826153'', ''5b53877cef0f4eeaadd7f96dc87273ae'', ''查看'', NULL, ''book:tevglbookmajor:view'', 2, NULL, 4, NULL, NULL, NULL, ''1'', ''1'', ''2019-07-30 17:07:02'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''ac227164c3304bf0a4f03c5cbd71e4ba'', ''5b53877cef0f4eeaadd7f96dc87273ae'', ''删除'', NULL, ''book:tevglbookmajor:remove'', 2, NULL, 3, NULL, NULL, NULL, ''1'', ''1'', ''2019-07-30 17:07:02'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''b43e1950c19d40deb8b9b701ffec5f65'', ''19c786f2bfbf46398e3b495f6c7014b1'', ''门户管理'', NULL, NULL, 0, ''fa fa-balance-scale'', 4, '''', 0, NULL, ''1'', ''1'', ''2019-07-09 15:35:10'', ''1'', ''2020-10-15 09:05:00'', NULL);
INSERT INTO `t_sys_resource` VALUES (''b4e13f4ee6b0466ba37bd13f5420e6ba'', ''89bbe647e4a34ae98df0e31dfdff48d0'', ''新增'', NULL, ''site:tevglsiteupdatelog:add'', 2, NULL, 1, NULL, NULL, NULL, ''1'', ''1'', ''2020-09-01 09:30:10'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''b8e76db567db4bf9b78e5277fdce6ff6'', ''83d786f2bfbf46398e3b495f6c70156c'', ''查看'', NULL, ''sys:tsysparameter:view'', 2, NULL, 1, NULL, NULL, NULL, ''1'', ''1'', ''2018-03-28 11:59:41'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''ba561d9fc4d840db83986447c6e7d2fb'', ''bcc3290a69c94b1dbeb6e042ffa75780'', ''修改'', NULL, ''site:tevglsitevideo:edit'', 2, NULL, 2, NULL, NULL, NULL, ''1'', ''1'', ''2021-09-11 17:51:30'', ''1'', ''2021-09-11 17:52:10'', NULL);
INSERT INTO `t_sys_resource` VALUES (''bb3b180c44224f219235455278aa1184'', ''371d98578b7c4be1867aa6b5e2d67031'', ''删除'', NULL, ''tch:tevgltchclass:remove'', 2, NULL, 3, NULL, NULL, NULL, ''1'', ''1'', ''2019-08-19 17:49:32'', ''1'', ''2019-08-19 17:49:49'', NULL);
INSERT INTO `t_sys_resource` VALUES (''bcc3290a69c94b1dbeb6e042ffa75780'', ''e0904dc937b2446ab4e4f04512c00070'', ''视频管理'', ''/site/tevglsitevideo'', ''site:tevglsitevideo:query'', 1, ''fa fa-youtube-play'', 7, NULL, 0, NULL, ''1'', ''1'', ''2021-09-11 17:51:25'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''bfe52d8339744e6caa8211c6f6fbdcce'', ''5bb687aa97dd4084871bdc700789b5c4'', ''首页'', ''https://www.budaos.com'', '''', 4, ''fa fa-snowflake-o'', 1, ''0b25462a631e4ccf93dddc2b58037738'', NULL, NULL, ''1'', ''1'', ''2019-07-18 09:15:42'', ''1'', ''2019-08-31 09:45:59'', NULL);
INSERT INTO `t_sys_resource` VALUES (''c0a8e83d390d46ea94fc6c52b01c18ff'', ''f9e8bed5cf6a485db552e0602741564c'', ''删除'', NULL, ''sys:tsysdict:remove'', 2, NULL, 4, NULL, NULL, NULL, ''1'', ''1'', ''2018-03-28 11:58:38'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''c2ec049934cf4f7ca38bf752dedb33cf'', ''0e0a4e29e34b4646a66d22cd5b876713'', ''查看'', NULL, ''site:tevglsiteavd:view'', 2, NULL, 4, NULL, NULL, NULL, ''1'', ''1'', ''2019-07-15 10:59:24'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''c41d8e6bdca6459bbcb704d242383e1b'', ''f9e8bed5cf6a485db552e0602741564c'', ''新增'', NULL, ''sys:tsysdict:add'', 2, NULL, 2, NULL, NULL, NULL, ''1'', ''1'', ''2018-03-28 11:58:38'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''c44ba41ac85248b893ff76f1d210bb52'', ''89bbe647e4a34ae98df0e31dfdff48d0'', ''查看'', NULL, ''site:tevglsiteupdatelog:view'', 2, NULL, 4, NULL, NULL, NULL, ''1'', ''1'', ''2020-09-01 09:30:11'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''c7b1b3c928fc4b0f95739be0a940d0d6'', ''371d98578b7c4be1867aa6b5e2d67031'', ''查看'', NULL, ''tch:tevgltchclass:view'', 2, NULL, 4, NULL, NULL, NULL, ''1'', ''1'', ''2019-08-19 17:49:32'', ''1'', ''2019-08-19 17:49:55'', NULL);
INSERT INTO `t_sys_resource` VALUES (''cb09c1957521474dae30b4768b74bc8e'', ''dfbb34201ac74e48b24585de9c5cc9da'', ''广告轮播'', ''/medu/tmedumediaavd'', ''medu:tmedumediaavd:list,medu:tmedumediaavd:query'', 1, ''fa fa-envira'', 1, NULL, 0, NULL, ''1'', ''1'', ''2020-09-02 11:55:23'', ''1'', ''2021-12-30 11:37:58'', NULL);
INSERT INTO `t_sys_resource` VALUES (''cbd6cf9b9b634f4b986bfbe05ae9591c'', ''cb09c1957521474dae30b4768b74bc8e'', ''查看'', NULL, ''medu:tmedumediaavd:view'', 2, NULL, 4, NULL, NULL, NULL, ''1'', ''1'', ''2020-09-02 11:55:30'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''cc3e5f98fb9d4c83bbb29332f4b0d85e'', ''19c786f2bfbf46398e3b495f6c7014b1'', ''安全管理'', ''#1'', '''', 0, ''el-icon-setting'', 3, '''', NULL, NULL, ''0'', ''1'', NULL, ''1'', ''2020-10-11 20:40:37'', NULL);
INSERT INTO `t_sys_resource` VALUES (''cc96adff96a34790892d2555b71ed8fd'', ''82c786f2bfbf46398e3b495f6c70156c'', ''子系统'', NULL, ''sys:menu:subsystem'', 2, NULL, 2, NULL, 0, NULL, ''1'', ''1'', ''2019-06-19 16:37:24'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''cfea91bab6b34fb396c0e37486f35bbd'', ''b43e1950c19d40deb8b9b701ffec5f65'', ''友情社区'', ''/site/Tevglforumfriend'', ''forum:tevglforumfriend:query'', 1, ''fa fa-bars'', 5, NULL, 0, NULL, ''1'', ''1'', ''2021-03-08 14:39:55'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''d552fe2536d64166b69b29705e3bf6b8'', ''e0904dc937b2446ab4e4f04512c00070'', ''教师管理'', ''/res/tevgltchteacher'', ''tch:tevgltchteacher:query'', 1, ''fa fa-graduation-cap'', 2, NULL, 0, NULL, ''1'', ''1'', ''2019-07-22 15:45:41'', ''1'', ''2020-10-15 09:07:03'', NULL);
INSERT INTO `t_sys_resource` VALUES (''d96c5e75c14c41acbf6c9ff0a41112d1'', ''bcc3290a69c94b1dbeb6e042ffa75780'', ''删除'', NULL, ''site:tevglsitevideo:delete'', 2, NULL, 3, NULL, NULL, NULL, ''1'', ''1'', ''2021-09-11 17:51:31'', ''1'', ''2021-09-11 17:52:22'', NULL);
INSERT INTO `t_sys_resource` VALUES (''dbafab4c31d3463b8dc2c26ee91edeb3'', ''99c786f2bfbf46399e3b495f6c7014bc'', ''查看'', NULL, ''sys:tsysorg:view'', 2, NULL, 1, NULL, NULL, NULL, ''1'', ''1'', ''2018-03-28 11:11:04'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''dc86ce2ecef34cbea905ad78305e9e10'', ''1'', ''岗位管理'', ''/sys/post'', ''sys:tsyspost:query'', 1, ''fa fa-id-badge'', 4, NULL, 0, NULL, ''1'', ''1'', ''2019-06-03 16:23:09'', ''1'', ''2019-06-19 10:33:41'', NULL);
INSERT INTO `t_sys_resource` VALUES (''dc8edd650b9f41a3a4708db33e3892fb'', ''0e0a4e29e34b4646a66d22cd5b876713'', ''新增'', NULL, ''site:tevglsiteavd:add'', 2, NULL, 1, NULL, NULL, NULL, ''1'', ''1'', ''2019-07-15 10:59:24'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''ddef9f1960a7416390727e4a717cae13'', ''371d98578b7c4be1867aa6b5e2d67031'', ''修改'', NULL, ''tch:tevgltchclass:edit'', 2, NULL, 2, NULL, NULL, NULL, ''1'', ''1'', ''2019-08-19 17:49:32'', ''1'', ''2019-08-19 17:49:45'', NULL);
INSERT INTO `t_sys_resource` VALUES (''dfbb34201ac74e48b24585de9c5cc9da'', ''19c786f2bfbf46398e3b495f6c7014b1'', ''小程序管理'', NULL, NULL, 0, ''fa fa-weixin'', 6, '''', 0, NULL, ''1'', ''1'', ''2020-09-02 11:54:05'', ''1'', ''2020-10-15 09:10:31'', NULL);
INSERT INTO `t_sys_resource` VALUES (''e0904dc937b2446ab4e4f04512c00070'', ''19c786f2bfbf46398e3b495f6c7014b1'', ''教务管理'', NULL, ''f'', 0, ''fa fa-database'', 5, NULL, 0, NULL, ''0'', ''1'', ''2019-07-22 15:41:19'', ''1'', ''2020-10-15 09:09:35'', NULL);
INSERT INTO `t_sys_resource` VALUES (''e517523468cf41bab3d77b7aa3fa4065'', ''5bb687aa97dd4084871bdc700789b5c4'', ''新闻公告'', '''', '''', 4, ''fa fa-superpowers'', 7, '''', NULL, NULL, ''0'', ''1'', ''2019-07-20 14:19:20'', ''1'', ''2019-08-08 16:47:17'', NULL);
INSERT INTO `t_sys_resource` VALUES (''e660d3450c594c8c93a64d55c6914e7c'', ''93d786f2bfbf46398e3b495f6c70156c'', ''分配用户'', ''sys/role/setUser'', ''sys:role:setUser,sys:role:save,sys:tsysuserinfo:query,sys:tsysuserinfo:list'', 2, '''', 5, '''', NULL, NULL, ''1'', ''1'', ''2018-03-28 11:38:58'', ''1'', ''2018-03-28 11:55:19'', NULL);
INSERT INTO `t_sys_resource` VALUES (''e790b82b969a47a1b053551d0735ce43'', ''cfea91bab6b34fb396c0e37486f35bbd'', ''查看'', NULL, ''forum:tevglforumfriend:view'', 2, NULL, 4, NULL, NULL, NULL, ''1'', ''1'', ''2021-03-08 14:40:01'', ''1'', ''2021-03-08 14:40:35'', NULL);
INSERT INTO `t_sys_resource` VALUES (''e81599e7d0644d39ae62fc0950dd5e7d'', ''cfea91bab6b34fb396c0e37486f35bbd'', ''删除分类'', NULL, ''forum:tevglforumfriendtype:delete'', 2, NULL, 6, NULL, 0, NULL, ''1'', ''1'', ''2021-04-15 15:04:46'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''ecd2af61fc2f49588a16f0512ae49d5e'', ''cfea91bab6b34fb396c0e37486f35bbd'', ''删除'', NULL, ''forum:tevglforumfriend:delete'', 2, NULL, 3, NULL, NULL, NULL, ''1'', ''1'', ''2021-03-08 14:40:01'', ''1'', ''2021-03-08 14:40:29'', NULL);
INSERT INTO `t_sys_resource` VALUES (''ed9d0544f6ac488aa1d65bf9126a03c2'', ''f9e8bed5cf6a485db552e0602741564c'', ''修改'', NULL, ''sys:tsysdict:edit'', 2, NULL, 3, NULL, NULL, NULL, ''1'', ''1'', ''2018-03-28 11:58:38'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''f03b877ac9814f99b3a371ee35ab3ec9'', ''-1'', ''智能客服系统'', NULL, NULL, -1, ''fa  fa-phone fa-lg fa-spin'', 3, '''', 0, NULL, ''1'', ''1'', ''2019-06-18 09:44:54'', ''1'', ''2019-06-19 17:26:29'', NULL);
INSERT INTO `t_sys_resource` VALUES (''f221b6aa4a69447084dc390c4d85511c'', ''92c786f2bfbf46398e3b495f6c70156c'', ''分配角色'', ''sys/tsysuserinfo/to_grantrole'', ''sys:tsysuserinfo:role'', 2, '''', 5, '''', NULL, NULL, ''1'', ''1'', ''2018-03-28 11:22:27'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''f3ee4494e48a47c9ac0eda896759d32e'', ''26715d0dc99f4c92b61d97b9bc074312'', ''查看'', NULL, ''site:tevglsiteresourceext:view'', 2, NULL, 4, NULL, NULL, NULL, ''1'', ''1'', ''2019-07-16 08:57:25'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''f4fd46dd59be413ab38b8f94f8af340c'', ''0e0a4e29e34b4646a66d22cd5b876713'', ''删除'', NULL, ''site:tevglsiteavd:remove'', 2, NULL, 3, NULL, NULL, NULL, ''1'', ''1'', ''2019-07-15 10:59:24'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''f6abfd7d572f46ddac72dbbd853360d7'', ''26715d0dc99f4c92b61d97b9bc074312'', ''删除'', NULL, ''site:tevglsiteresourceext:remove'', 2, NULL, 3, NULL, NULL, NULL, ''1'', ''1'', ''2019-07-16 08:57:25'', NULL, NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''f9e8bed5cf6a485db552e0602741564c'', ''1'', ''数据字典'', ''/sys/dict'', ''sys:tsysdict:query'', 1, ''fa fa-database'', 5, '''', NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2019-06-19 10:33:53'', NULL);
INSERT INTO `t_sys_resource` VALUES (''f9e8bed5cf6a485db552e0602741845c'', ''cc3e5f98fb9d4c83bbb29332f4b0d85e'', ''附件管理'', ''/sys/attach'', ''sys:tsysattach:query'', 1, ''el-icon-paperclip'', 5, '''', NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2019-06-01 08:40:11'', NULL);
INSERT INTO `t_sys_resource` VALUES (''f9e8bed5cf6a485db552e0602741894c'', ''cc3e5f98fb9d4c83bbb29332f4b0d85e'', ''登录日志'', ''/sys/loginLog'', ''sys:tsysloginlog:query,sys:tsysloginlog:remove'', 1, ''el-icon-user-solid'', 1, '''', NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2019-06-01 08:52:48'', NULL);
INSERT INTO `t_sys_resource` VALUES (''fb105e3a917a4cb39ba42717e82c3045'', ''5bb687aa97dd4084871bdc700789b5c4'', ''我要合作'', ''javascript:alert(\''敬请期待\'')'', '''', 4, ''fa fa-envelope-open-o'', 7, ''0b25462a631e4ccf93dddc2b58037738'', NULL, NULL, ''1'', ''1'', ''2019-07-18 09:18:21'', ''1'', ''2020-09-28 17:25:35'', NULL);
INSERT INTO `t_sys_resource` VALUES (''fb7ddf7af18e4d639266d74a90540cd3'', ''dc86ce2ecef34cbea905ad78305e9e10'', ''修改'', NULL, ''sys:post:edit,sys:tsyspost:edit'', 2, NULL, 3, NULL, NULL, NULL, ''1'', ''1'', ''2019-06-12 14:51:52'', ''1'', NULL, NULL);
INSERT INTO `t_sys_resource` VALUES (''ff6e7475f8a54cf28c4c8aa8a7bc05da'', ''cfea91bab6b34fb396c0e37486f35bbd'', ''修改'', NULL, ''forum:tevglforumfriend:edit'', 2, NULL, 2, NULL, NULL, NULL, ''1'', ''1'', ''2021-03-08 14:40:00'', ''1'', ''2021-03-08 14:40:23'', NULL);

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role`  (
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''主键'',
  `role_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''角色名称'',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''备注'',
  `create_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建人'',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''修改时间'',
  `role_type` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''角色类型：公有、私有'',
  `org_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''所属机构'',
  `data_scope` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''数据范围:01所有数据 02所在机构及以下数据; 03本级数据 04自定义明细 05所在部门数据06所在部门及以下数据 07本人数据'',
  `status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''角色状态：启用、禁用'',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''角色'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
INSERT INTO `t_sys_role` VALUES (''076a6751978140bf86c00079852b5d95'', ''a3'', '''', ''1'', ''2022-08-29 11:21:55'', NULL, ''2022-08-29 11:21:55'', NULL, ''bec753ae9b4c4d6d856dcaa6e4526b2b'', ''1'', ''1'');
INSERT INTO `t_sys_role` VALUES (''579e631603194e47a37d452df119f0ef'', ''a2'', '''', ''1'', ''2022-08-29 11:21:17'', NULL, ''2022-08-29 11:21:17'', NULL, ''bec753ae9b4c4d6d856dcaa6e4526b2b'', ''1'', ''1'');
INSERT INTO `t_sys_role` VALUES (''904833575bab45d793e829f047dde248'', ''a1'', '''', ''1'', ''2022-08-29 11:20:25'', NULL, ''2022-08-29 11:20:25'', NULL, ''bec753ae9b4c4d6d856dcaa6e4526b2b'', ''1'', ''1'');
INSERT INTO `t_sys_role` VALUES (''b1dbc8db116d42538d67e80329b471f0'', ''开发'', '''', ''1'', ''2022-09-13 10:57:49'', ''1'', ''2022-09-13 11:08:51'', NULL, ''845786f2bfbf46398e3b495f6c7014bc'', ''1'', NULL);

-- ----------------------------
-- Table structure for t_sys_roleprivilege
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_roleprivilege`;
CREATE TABLE `t_sys_roleprivilege`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''主键'',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''角色ID'',
  `menu_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''菜单ID'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''角色与菜单对应关系'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_roleprivilege
-- ----------------------------
INSERT INTO `t_sys_roleprivilege` VALUES (''012d75ff52744862aa7e1ee87262a66d'', ''579e631603194e47a37d452df119f0ef'', ''f6abfd7d572f46ddac72dbbd853360d7'');
INSERT INTO `t_sys_roleprivilege` VALUES (''025d26ec5a4e4c34bf3a167865a50e3d'', ''579e631603194e47a37d452df119f0ef'', ''df58d5855e4e459186da60adf73c77cb'');
INSERT INTO `t_sys_roleprivilege` VALUES (''04c5d413ce8a44d6926a0a5a2691770a'', ''904833575bab45d793e829f047dde248'', ''26715d0dc99f4c92b61d97b9bc074312'');
INSERT INTO `t_sys_roleprivilege` VALUES (''04c8948f7bdd4e429a1514efbfc7e2fa'', ''579e631603194e47a37d452df119f0ef'', ''741e47570019484ab7498e5515bb817c'');
INSERT INTO `t_sys_roleprivilege` VALUES (''060269870471461ba5a07e76f2bdd322'', ''076a6751978140bf86c00079852b5d95'', ''93d786f2bfbf46398e3b495f6c70156c'');
INSERT INTO `t_sys_roleprivilege` VALUES (''061a75781e2946e1a8606b70136fbbaa'', ''579e631603194e47a37d452df119f0ef'', ''f0701944bc1d491393733fd9743a0633'');
INSERT INTO `t_sys_roleprivilege` VALUES (''0c77cf64cd474e6880cf12ab47f3e136'', ''579e631603194e47a37d452df119f0ef'', ''0e0a4e29e34b4646a66d22cd5b876713'');
INSERT INTO `t_sys_roleprivilege` VALUES (''0eb54d963eb04d9bbfe931347ca11877'', ''904833575bab45d793e829f047dde248'', ''93d786f2bfbf46398e3b495f6c70156c'');
INSERT INTO `t_sys_roleprivilege` VALUES (''10611481139246409a9a638f6693b32a'', ''904833575bab45d793e829f047dde248'', ''f4ddeb5c322e4ef497ec8c9ea6874d0e'');
INSERT INTO `t_sys_roleprivilege` VALUES (''109739be9c054bf6b6637195fc6d7f2d'', ''579e631603194e47a37d452df119f0ef'', ''8ef88ce3a44f4896acd3da685b17b789'');
INSERT INTO `t_sys_roleprivilege` VALUES (''10a64b07006e4627ba20ef29b5110486'', ''076a6751978140bf86c00079852b5d95'', ''26715d0dc99f4c92b61d97b9bc074312'');
INSERT INTO `t_sys_roleprivilege` VALUES (''120121afb18e4ba8927119456dd0af06'', ''904833575bab45d793e829f047dde248'', ''1561d13cabc648b29ec45d2b9764b69d'');
INSERT INTO `t_sys_roleprivilege` VALUES (''15ea9140455f4240bb147aaefee9a005'', ''904833575bab45d793e829f047dde248'', ''df58d5855e4e459186da60adf73c77cb'');
INSERT INTO `t_sys_roleprivilege` VALUES (''169537c470c640ef99d8166908436d32'', ''076a6751978140bf86c00079852b5d95'', ''1cc48ea31aa14f58a3a55b004568aa81'');
INSERT INTO `t_sys_roleprivilege` VALUES (''16afc8d6a5344e4c88827e14bfb5108a'', ''904833575bab45d793e829f047dde248'', ''9243558bee9548bcb08ca596c37e8a5b'');
INSERT INTO `t_sys_roleprivilege` VALUES (''18e1536c525b42a58d64a857ea249a35'', ''579e631603194e47a37d452df119f0ef'', ''2716eca246ee42bfb19b129ccdf569b6'');
INSERT INTO `t_sys_roleprivilege` VALUES (''1929ef5bb8eb456b88d308480cebdd3b'', ''579e631603194e47a37d452df119f0ef'', ''26715d0dc99f4c92b61d97b9bc074312'');
INSERT INTO `t_sys_roleprivilege` VALUES (''199b27fe1f324a12a2a283ccc4156c73'', ''076a6751978140bf86c00079852b5d95'', ''72564b01bf504573b4118c458eab96d5'');
INSERT INTO `t_sys_roleprivilege` VALUES (''1d25e5bcec4b4691919ea86a058d5638'', ''b1dbc8db116d42538d67e80329b471f0'', ''19c786f2bfbf46398e3b495f6c7014b1'');
INSERT INTO `t_sys_roleprivilege` VALUES (''1d27876cc69e41de9df1e091e5b8d5d7'', ''076a6751978140bf86c00079852b5d95'', ''ff6e7475f8a54cf28c4c8aa8a7bc05da'');
INSERT INTO `t_sys_roleprivilege` VALUES (''1e266a561d1a4bba89b7f45ebd0e97de'', ''076a6751978140bf86c00079852b5d95'', ''c4ccaafcd1ca4ae88695928940b00768'');
INSERT INTO `t_sys_roleprivilege` VALUES (''1ed0fff61ff14587af47ad99e0a93b65'', ''904833575bab45d793e829f047dde248'', ''f6abfd7d572f46ddac72dbbd853360d7'');
INSERT INTO `t_sys_roleprivilege` VALUES (''209a329a2d914b2f8911b20bbc38fbc4'', ''076a6751978140bf86c00079852b5d95'', ''2716eca246ee42bfb19b129ccdf569b6'');
INSERT INTO `t_sys_roleprivilege` VALUES (''20e11c9445a340d2b16298631daf5576'', ''076a6751978140bf86c00079852b5d95'', ''1'');
INSERT INTO `t_sys_roleprivilege` VALUES (''231b46859e294516a79ea7c8b41ec262'', ''076a6751978140bf86c00079852b5d95'', ''20d786f2bfbf46398e3b495f6c70156c'');
INSERT INTO `t_sys_roleprivilege` VALUES (''24dea1a9f5094182b6b0699e1b207e85'', ''904833575bab45d793e829f047dde248'', ''0dd44379d5224cd994f51cfcdb613027'');
INSERT INTO `t_sys_roleprivilege` VALUES (''283270b26fd3442897d6f8aa79619053'', ''579e631603194e47a37d452df119f0ef'', ''05c93a7893754c279d7549e1e8c6270b'');
INSERT INTO `t_sys_roleprivilege` VALUES (''286506db73204d778f496183a5fa6eba'', ''579e631603194e47a37d452df119f0ef'', ''f4ddeb5c322e4ef497ec8c9ea6874d0e'');
INSERT INTO `t_sys_roleprivilege` VALUES (''2c6563a281ad4d3caf58e3aa28fd1d96'', ''904833575bab45d793e829f047dde248'', ''60896726a3024aedad02b405677c20f5'');
INSERT INTO `t_sys_roleprivilege` VALUES (''2ce53d42788d4df1b1253e504a5ba90e'', ''076a6751978140bf86c00079852b5d95'', ''dc86ce2ecef34cbea905ad78305e9e10'');
INSERT INTO `t_sys_roleprivilege` VALUES (''2dddf105b4f741e2a582a4ab68fcb055'', ''076a6751978140bf86c00079852b5d95'', ''9243558bee9548bcb08ca596c37e8a5b'');
INSERT INTO `t_sys_roleprivilege` VALUES (''2e482f3124c1496e96b2e70a66a3f151'', ''579e631603194e47a37d452df119f0ef'', ''f3ee4494e48a47c9ac0eda896759d32e'');
INSERT INTO `t_sys_roleprivilege` VALUES (''310b9353fe0b45f39d29f11a741ef666'', ''579e631603194e47a37d452df119f0ef'', ''c4ccaafcd1ca4ae88695928940b00768'');
INSERT INTO `t_sys_roleprivilege` VALUES (''384a0bda4d0c4f5caa6c5f6701d13a9c'', ''579e631603194e47a37d452df119f0ef'', ''fb7ddf7af18e4d639266d74a90540cd3'');
INSERT INTO `t_sys_roleprivilege` VALUES (''3c8a9b5756144693b2bc124095948cb9'', ''579e631603194e47a37d452df119f0ef'', ''5a9d2b6fa08840ce8951304416e6bbce'');
INSERT INTO `t_sys_roleprivilege` VALUES (''3cffb959b074402c8bad5283c95bc75e'', ''904833575bab45d793e829f047dde248'', ''f875467acb374a38b4b59a9ddc1e206d'');
INSERT INTO `t_sys_roleprivilege` VALUES (''3d36796bd84e4586b1285fdc6429ec96'', ''076a6751978140bf86c00079852b5d95'', ''741e47570019484ab7498e5515bb817c'');
INSERT INTO `t_sys_roleprivilege` VALUES (''3d5cf065c60d43529a9f6799d43abc7a'', ''904833575bab45d793e829f047dde248'', ''8ef88ce3a44f4896acd3da685b17b789'');
INSERT INTO `t_sys_roleprivilege` VALUES (''40362f1a02ba4bbcbc40a55f6dfc2c59'', ''076a6751978140bf86c00079852b5d95'', ''84a837fc59124d5d98d1252681b5b1fe'');
INSERT INTO `t_sys_roleprivilege` VALUES (''41ed2b5934c74ed48ea30f665ab8e5f7'', ''076a6751978140bf86c00079852b5d95'', ''e790b82b969a47a1b053551d0735ce43'');
INSERT INTO `t_sys_roleprivilege` VALUES (''42f0267eccd948c3a29968dc59776a36'', ''904833575bab45d793e829f047dde248'', ''0ea5f5fa3ba042049fe5b09ab3f546b1'');
INSERT INTO `t_sys_roleprivilege` VALUES (''44d109de70354f61a269a51d05f23d8d'', ''579e631603194e47a37d452df119f0ef'', ''137d86ffc32a44b4ae2b43b2f014c5c8'');
INSERT INTO `t_sys_roleprivilege` VALUES (''44ebcaaab4e1438b855c181a7ddd5cbe'', ''579e631603194e47a37d452df119f0ef'', ''f875467acb374a38b4b59a9ddc1e206d'');
INSERT INTO `t_sys_roleprivilege` VALUES (''45a4748e0d9c4d0fb9e348a5b5ef7ac8'', ''579e631603194e47a37d452df119f0ef'', ''d98c07d3d5f844f59207f24e3a0af3aa'');
INSERT INTO `t_sys_roleprivilege` VALUES (''48a425ec503a4d57ac4fdd03478fd0d8'', ''076a6751978140bf86c00079852b5d95'', ''8e449f3473114318b50ff24eb7f35dd0'');
INSERT INTO `t_sys_roleprivilege` VALUES (''4c8dca1eaf3b4bdb9cd43348a4fd7eac'', ''076a6751978140bf86c00079852b5d95'', ''cfea91bab6b34fb396c0e37486f35bbd'');
INSERT INTO `t_sys_roleprivilege` VALUES (''4db6087120e84508a3278dcb7201fd49'', ''579e631603194e47a37d452df119f0ef'', ''f4fd46dd59be413ab38b8f94f8af340c'');
INSERT INTO `t_sys_roleprivilege` VALUES (''4eb01a6a7dae4dec8e7f114c4e5411fa'', ''904833575bab45d793e829f047dde248'', ''dc86ce2ecef34cbea905ad78305e9e10'');
INSERT INTO `t_sys_roleprivilege` VALUES (''4f94c269f8174f20b956c2563152b715'', ''904833575bab45d793e829f047dde248'', ''72564b01bf504573b4118c458eab96d5'');
INSERT INTO `t_sys_roleprivilege` VALUES (''51939905e4ad428bae5cec49f52cc37d'', ''579e631603194e47a37d452df119f0ef'', ''0dd44379d5224cd994f51cfcdb613027'');
INSERT INTO `t_sys_roleprivilege` VALUES (''52306124133b48329a2591424a4552fd'', ''579e631603194e47a37d452df119f0ef'', ''4f878d16bad64263870afb77132ce93c'');
INSERT INTO `t_sys_roleprivilege` VALUES (''532efabd7d9940c6b87d2e0de80b521b'', ''904833575bab45d793e829f047dde248'', ''8e449f3473114318b50ff24eb7f35dd0'');
INSERT INTO `t_sys_roleprivilege` VALUES (''53f4c9ad32fb4a1a9d15fff5a8e1f441'', ''904833575bab45d793e829f047dde248'', ''8d257582b0c54e0b987118903613d427'');
INSERT INTO `t_sys_roleprivilege` VALUES (''5ce6e34f6a124b70bd36a47d59199a28'', ''076a6751978140bf86c00079852b5d95'', ''137d86ffc32a44b4ae2b43b2f014c5c8'');
INSERT INTO `t_sys_roleprivilege` VALUES (''5deee72e20be489a92a4ff86675d9803'', ''579e631603194e47a37d452df119f0ef'', ''c2ec049934cf4f7ca38bf752dedb33cf'');
INSERT INTO `t_sys_roleprivilege` VALUES (''5e8292e49e254cdba74f37611ae112cf'', ''076a6751978140bf86c00079852b5d95'', ''0ea5f5fa3ba042049fe5b09ab3f546b1'');
INSERT INTO `t_sys_roleprivilege` VALUES (''6077e66f1c364bcd82e3a0fc86439778'', ''076a6751978140bf86c00079852b5d95'', ''c2ec049934cf4f7ca38bf752dedb33cf'');
INSERT INTO `t_sys_roleprivilege` VALUES (''60af3b2dac8e426596ad669c4bc6f0f2'', ''076a6751978140bf86c00079852b5d95'', ''1561d13cabc648b29ec45d2b9764b69d'');
INSERT INTO `t_sys_roleprivilege` VALUES (''6167ec4964574ef8a2e3835f4073db08'', ''076a6751978140bf86c00079852b5d95'', ''f4ddeb5c322e4ef497ec8c9ea6874d0e'');
INSERT INTO `t_sys_roleprivilege` VALUES (''61a150abf4f44f5a953401768eea2a2e'', ''076a6751978140bf86c00079852b5d95'', ''8ef88ce3a44f4896acd3da685b17b789'');
INSERT INTO `t_sys_roleprivilege` VALUES (''62b1cfcca8094687ab993b6abc22d0da'', ''904833575bab45d793e829f047dde248'', ''d98c07d3d5f844f59207f24e3a0af3aa'');
INSERT INTO `t_sys_roleprivilege` VALUES (''62f9f6425d684f1397dafca3a6c18f85'', ''579e631603194e47a37d452df119f0ef'', ''1561d13cabc648b29ec45d2b9764b69d'');
INSERT INTO `t_sys_roleprivilege` VALUES (''651f67b681ab4134b7889c306159974e'', ''579e631603194e47a37d452df119f0ef'', ''44729d3a6b7841cfa6fc4fbaa6bf0c03'');
INSERT INTO `t_sys_roleprivilege` VALUES (''66bb90b6e62b47d0aefcc7006b1e0770'', ''b1dbc8db116d42538d67e80329b471f0'', ''1'');
INSERT INTO `t_sys_roleprivilege` VALUES (''683f22a7d49e4732ad58d36ba42eee5d'', ''904833575bab45d793e829f047dde248'', ''2716eca246ee42bfb19b129ccdf569b6'');
INSERT INTO `t_sys_roleprivilege` VALUES (''6ab3aa6f7dbb4ef4a49ad7cef3a623a5'', ''579e631603194e47a37d452df119f0ef'', ''04f3562771a744bdb70511f6ca63e1a7'');
INSERT INTO `t_sys_roleprivilege` VALUES (''6d889bbd971a497199f02950291fc9c1'', ''579e631603194e47a37d452df119f0ef'', ''b43e1950c19d40deb8b9b701ffec5f65'');
INSERT INTO `t_sys_roleprivilege` VALUES (''6fbc772c8bb641948b12273ed4671977'', ''579e631603194e47a37d452df119f0ef'', ''e81599e7d0644d39ae62fc0950dd5e7d'');
INSERT INTO `t_sys_roleprivilege` VALUES (''72026a439e1d4c78a0595f63bd9f37e0'', ''904833575bab45d793e829f047dde248'', ''ecd2af61fc2f49588a16f0512ae49d5e'');
INSERT INTO `t_sys_roleprivilege` VALUES (''730b461e7fb949fcbdecfedb6d40cefa'', ''579e631603194e47a37d452df119f0ef'', ''ff6e7475f8a54cf28c4c8aa8a7bc05da'');
INSERT INTO `t_sys_roleprivilege` VALUES (''73aaa187deb8432b9ac832bd31ad860b'', ''579e631603194e47a37d452df119f0ef'', ''fdf0939eafc14733a673648a14388e19'');
INSERT INTO `t_sys_roleprivilege` VALUES (''7438c6a859634cef885535bf2d89833d'', ''076a6751978140bf86c00079852b5d95'', ''fdf0939eafc14733a673648a14388e19'');
INSERT INTO `t_sys_roleprivilege` VALUES (''74fec05ab2b647299fa10e8a8e9ac317'', ''076a6751978140bf86c00079852b5d95'', ''0e0a4e29e34b4646a66d22cd5b876713'');
INSERT INTO `t_sys_roleprivilege` VALUES (''75f592dc7fa142e1a0b4a780c5bc626c'', ''579e631603194e47a37d452df119f0ef'', ''11788e342aba4f389138713f0b510bd9'');
INSERT INTO `t_sys_roleprivilege` VALUES (''774867d5191045f593b20aca0df95aef'', ''904833575bab45d793e829f047dde248'', ''90506dda4f4f4ad6bd43822761d68098'');
INSERT INTO `t_sys_roleprivilege` VALUES (''7961044b7a0640c686b50602a06bf6ac'', ''579e631603194e47a37d452df119f0ef'', ''84a837fc59124d5d98d1252681b5b1fe'');
INSERT INTO `t_sys_roleprivilege` VALUES (''79b4f883f9544e20837ce8a143495000'', ''076a6751978140bf86c00079852b5d95'', ''f35a84c12aca429c9803f72c5acdb550'');
INSERT INTO `t_sys_roleprivilege` VALUES (''7be78f3ebf5f423eacd5b8f50e1162be'', ''904833575bab45d793e829f047dde248'', ''f3ee4494e48a47c9ac0eda896759d32e'');
INSERT INTO `t_sys_roleprivilege` VALUES (''7cf0efef19274e63aefdf34f1cf6476b'', ''904833575bab45d793e829f047dde248'', ''cfea91bab6b34fb396c0e37486f35bbd'');
INSERT INTO `t_sys_roleprivilege` VALUES (''7e780416e70549c1959bf4d8f6664122'', ''904833575bab45d793e829f047dde248'', ''e790b82b969a47a1b053551d0735ce43'');
INSERT INTO `t_sys_roleprivilege` VALUES (''814fb1a06af94f62ad36760d97d3e4a8'', ''076a6751978140bf86c00079852b5d95'', ''b43e1950c19d40deb8b9b701ffec5f65'');
INSERT INTO `t_sys_roleprivilege` VALUES (''822693afb4f849aea0ac7953da6a68d4'', ''076a6751978140bf86c00079852b5d95'', ''df58d5855e4e459186da60adf73c77cb'');
INSERT INTO `t_sys_roleprivilege` VALUES (''8318982d3c0848eaa97cbf4efec0c0c1'', ''904833575bab45d793e829f047dde248'', ''c2ec049934cf4f7ca38bf752dedb33cf'');
INSERT INTO `t_sys_roleprivilege` VALUES (''857ee062c7dd4f3892aa93cb390b4919'', ''579e631603194e47a37d452df119f0ef'', ''771c483b89e248a1adb261777ebd9d98'');
INSERT INTO `t_sys_roleprivilege` VALUES (''85e9445553224abbb9f8b96801ce3852'', ''076a6751978140bf86c00079852b5d95'', ''60896726a3024aedad02b405677c20f5'');
INSERT INTO `t_sys_roleprivilege` VALUES (''8c7857360b4d471d82310e53d2f14f59'', ''904833575bab45d793e829f047dde248'', ''fb7ddf7af18e4d639266d74a90540cd3'');
INSERT INTO `t_sys_roleprivilege` VALUES (''8c7f6ab56f7649f2b20709dcb8907aff'', ''904833575bab45d793e829f047dde248'', ''fdf0939eafc14733a673648a14388e19'');
INSERT INTO `t_sys_roleprivilege` VALUES (''8d36fc78c6fb4fbfa72d5efaae818b8e'', ''579e631603194e47a37d452df119f0ef'', ''3025f524de014e6e905b8e061eb72890'');
INSERT INTO `t_sys_roleprivilege` VALUES (''8edb2777adbc410d96120a89d6fd3d17'', ''076a6751978140bf86c00079852b5d95'', ''05c93a7893754c279d7549e1e8c6270b'');
INSERT INTO `t_sys_roleprivilege` VALUES (''8f84fe17dbb342098970ae33919dceb5'', ''076a6751978140bf86c00079852b5d95'', ''771c483b89e248a1adb261777ebd9d98'');
INSERT INTO `t_sys_roleprivilege` VALUES (''952dbf9a2181441ab7fbcea698516288'', ''579e631603194e47a37d452df119f0ef'', ''dc86ce2ecef34cbea905ad78305e9e10'');
INSERT INTO `t_sys_roleprivilege` VALUES (''95d46ebc284f49feaee19d84f44cbccd'', ''076a6751978140bf86c00079852b5d95'', ''ecd2af61fc2f49588a16f0512ae49d5e'');
INSERT INTO `t_sys_roleprivilege` VALUES (''987ba1e0bbd34fa183860b96ed17301e'', ''904833575bab45d793e829f047dde248'', ''741e47570019484ab7498e5515bb817c'');
INSERT INTO `t_sys_roleprivilege` VALUES (''9a560d36faab45b09204013886ce3ad8'', ''579e631603194e47a37d452df119f0ef'', ''64341b7a30ab43ae9b22bcf252abcab4'');
INSERT INTO `t_sys_roleprivilege` VALUES (''9a946200b64746b9bd49ff9452f0aed7'', ''904833575bab45d793e829f047dde248'', ''5a9d2b6fa08840ce8951304416e6bbce'');
INSERT INTO `t_sys_roleprivilege` VALUES (''9be772c00fc2408aabecf6e9dafb6e09'', ''904833575bab45d793e829f047dde248'', ''1'');
INSERT INTO `t_sys_roleprivilege` VALUES (''9eb100baa45a4791a762e48a499e8a9e'', ''904833575bab45d793e829f047dde248'', ''137d86ffc32a44b4ae2b43b2f014c5c8'');
INSERT INTO `t_sys_roleprivilege` VALUES (''a2ef4306850a431c823335cd60aef182'', ''904833575bab45d793e829f047dde248'', ''771c483b89e248a1adb261777ebd9d98'');
INSERT INTO `t_sys_roleprivilege` VALUES (''a50a771b05964163b77ce590007a0e54'', ''b1dbc8db116d42538d67e80329b471f0'', ''771c483b89e248a1adb261777ebd9d98'');
INSERT INTO `t_sys_roleprivilege` VALUES (''a51ea1e487d24ceb896840fc640c243b'', ''904833575bab45d793e829f047dde248'', ''f35a84c12aca429c9803f72c5acdb550'');
INSERT INTO `t_sys_roleprivilege` VALUES (''aaa5b169a8ae4cc4971a58f67d2b7b9b'', ''076a6751978140bf86c00079852b5d95'', ''8d257582b0c54e0b987118903613d427'');
INSERT INTO `t_sys_roleprivilege` VALUES (''abea473c895646e29ed10ee94d9ef083'', ''579e631603194e47a37d452df119f0ef'', ''cfea91bab6b34fb396c0e37486f35bbd'');
INSERT INTO `t_sys_roleprivilege` VALUES (''aed09a55395f4c768c367fc4065580e9'', ''904833575bab45d793e829f047dde248'', ''3025f524de014e6e905b8e061eb72890'');
INSERT INTO `t_sys_roleprivilege` VALUES (''af2caee7deaf495c9c576957b1773d54'', ''076a6751978140bf86c00079852b5d95'', ''19c786f2bfbf46398e3b495f6c7014b1'');
INSERT INTO `t_sys_roleprivilege` VALUES (''b097454985f24c36852eb0c345c3f6cc'', ''076a6751978140bf86c00079852b5d95'', ''dc8edd650b9f41a3a4708db33e3892fb'');
INSERT INTO `t_sys_roleprivilege` VALUES (''b0a078ce7a8946738a222621c9615565'', ''904833575bab45d793e829f047dde248'', ''0e0a4e29e34b4646a66d22cd5b876713'');
INSERT INTO `t_sys_roleprivilege` VALUES (''b0f92ef723ae459697278ed0f7609bd7'', ''904833575bab45d793e829f047dde248'', ''b43e1950c19d40deb8b9b701ffec5f65'');
INSERT INTO `t_sys_roleprivilege` VALUES (''b15368d759e4474c945cb6da2e09650e'', ''904833575bab45d793e829f047dde248'', ''19c786f2bfbf46398e3b495f6c7014b1'');
INSERT INTO `t_sys_roleprivilege` VALUES (''b257ebd795604b2793a963156ad8f407'', ''579e631603194e47a37d452df119f0ef'', ''1cc48ea31aa14f58a3a55b004568aa81'');
INSERT INTO `t_sys_roleprivilege` VALUES (''b474082cda4d4bac8d617df65a198705'', ''076a6751978140bf86c00079852b5d95'', ''fb7ddf7af18e4d639266d74a90540cd3'');
INSERT INTO `t_sys_roleprivilege` VALUES (''b7785c798b5346d2a3c465cd9bb463f2'', ''579e631603194e47a37d452df119f0ef'', ''ecd2af61fc2f49588a16f0512ae49d5e'');
INSERT INTO `t_sys_roleprivilege` VALUES (''b7b27dbf84834df68312be726daf90e9'', ''076a6751978140bf86c00079852b5d95'', ''04f3562771a744bdb70511f6ca63e1a7'');
INSERT INTO `t_sys_roleprivilege` VALUES (''bcd9af43d86e4e9684c6780a4c0c1029'', ''579e631603194e47a37d452df119f0ef'', ''e790b82b969a47a1b053551d0735ce43'');
INSERT INTO `t_sys_roleprivilege` VALUES (''bd70f96fc66a4efdbaa8deb4dd65fc39'', ''076a6751978140bf86c00079852b5d95'', ''e81599e7d0644d39ae62fc0950dd5e7d'');
INSERT INTO `t_sys_roleprivilege` VALUES (''bf8d9e6b29f5469f8988ae21be1810c2'', ''076a6751978140bf86c00079852b5d95'', ''0dd44379d5224cd994f51cfcdb613027'');
INSERT INTO `t_sys_roleprivilege` VALUES (''c2d36a74aacb4f569354e5958648c0bf'', ''076a6751978140bf86c00079852b5d95'', ''d98c07d3d5f844f59207f24e3a0af3aa'');
INSERT INTO `t_sys_roleprivilege` VALUES (''c49290fcdf8c496398068ebf8609493c'', ''904833575bab45d793e829f047dde248'', ''ff6e7475f8a54cf28c4c8aa8a7bc05da'');
INSERT INTO `t_sys_roleprivilege` VALUES (''cbd0100d9cfd4c309e5f779b2a87e9e4'', ''579e631603194e47a37d452df119f0ef'', ''dc8edd650b9f41a3a4708db33e3892fb'');
INSERT INTO `t_sys_roleprivilege` VALUES (''cdb8106f9e9847019b4e78e0b4dbc951'', ''579e631603194e47a37d452df119f0ef'', ''f35a84c12aca429c9803f72c5acdb550'');
INSERT INTO `t_sys_roleprivilege` VALUES (''d031a61b9ccc4509bcc671cb37d91255'', ''076a6751978140bf86c00079852b5d95'', ''44729d3a6b7841cfa6fc4fbaa6bf0c03'');
INSERT INTO `t_sys_roleprivilege` VALUES (''d1c47a2ba0fa4e8aa94257e580ea5896'', ''904833575bab45d793e829f047dde248'', ''11788e342aba4f389138713f0b510bd9'');
INSERT INTO `t_sys_roleprivilege` VALUES (''d265375819fd4a99868c514677a04ac4'', ''904833575bab45d793e829f047dde248'', ''05c93a7893754c279d7549e1e8c6270b'');
INSERT INTO `t_sys_roleprivilege` VALUES (''d3f94176b8454739ab82070a0a25a55b'', ''904833575bab45d793e829f047dde248'', ''44729d3a6b7841cfa6fc4fbaa6bf0c03'');
INSERT INTO `t_sys_roleprivilege` VALUES (''da75fb2d73c640ca839bc2b74a47e611'', ''076a6751978140bf86c00079852b5d95'', ''5a9d2b6fa08840ce8951304416e6bbce'');
INSERT INTO `t_sys_roleprivilege` VALUES (''dae0423be1cd4d49a3e9ae5a12805348'', ''076a6751978140bf86c00079852b5d95'', ''f0701944bc1d491393733fd9743a0633'');
INSERT INTO `t_sys_roleprivilege` VALUES (''dbd735bfdbb243e59ba3a20d5fd98e13'', ''579e631603194e47a37d452df119f0ef'', ''20d786f2bfbf46398e3b495f6c70156c'');
INSERT INTO `t_sys_roleprivilege` VALUES (''de43f024e0544751b89bd9591000a257'', ''076a6751978140bf86c00079852b5d95'', ''f6abfd7d572f46ddac72dbbd853360d7'');
INSERT INTO `t_sys_roleprivilege` VALUES (''de8336f7154142b9a907f86e0d5d4f94'', ''904833575bab45d793e829f047dde248'', ''1cc48ea31aa14f58a3a55b004568aa81'');
INSERT INTO `t_sys_roleprivilege` VALUES (''dfc6a1c639ea49948ccd01fa40f14b5f'', ''579e631603194e47a37d452df119f0ef'', ''8d257582b0c54e0b987118903613d427'');
INSERT INTO `t_sys_roleprivilege` VALUES (''e0f51a5836614cb39db1af1df1974a22'', ''579e631603194e47a37d452df119f0ef'', ''60896726a3024aedad02b405677c20f5'');
INSERT INTO `t_sys_roleprivilege` VALUES (''e34df0ceef6746ce92b0d713548a3bf0'', ''579e631603194e47a37d452df119f0ef'', ''90506dda4f4f4ad6bd43822761d68098'');
INSERT INTO `t_sys_roleprivilege` VALUES (''e474da68fd3341b883a0cf67c1d59918'', ''076a6751978140bf86c00079852b5d95'', ''64341b7a30ab43ae9b22bcf252abcab4'');
INSERT INTO `t_sys_roleprivilege` VALUES (''e4debac081db420aaec99cbab2842909'', ''579e631603194e47a37d452df119f0ef'', ''19c786f2bfbf46398e3b495f6c7014b1'');
INSERT INTO `t_sys_roleprivilege` VALUES (''e4fb35cde2494768b58ce1ba203c8482'', ''579e631603194e47a37d452df119f0ef'', ''1'');
INSERT INTO `t_sys_roleprivilege` VALUES (''e581d321195842ccb532e44efff7e4f4'', ''904833575bab45d793e829f047dde248'', ''f4fd46dd59be413ab38b8f94f8af340c'');
INSERT INTO `t_sys_roleprivilege` VALUES (''e59f534352d64021ac5c76f23b2e5f40'', ''904833575bab45d793e829f047dde248'', ''dc8edd650b9f41a3a4708db33e3892fb'');
INSERT INTO `t_sys_roleprivilege` VALUES (''e64015119c3e4ced8671cdbc4ee57aa9'', ''904833575bab45d793e829f047dde248'', ''c4ccaafcd1ca4ae88695928940b00768'');
INSERT INTO `t_sys_roleprivilege` VALUES (''e786f0005db94370a9503d9dfb71e6f0'', ''076a6751978140bf86c00079852b5d95'', ''f875467acb374a38b4b59a9ddc1e206d'');
INSERT INTO `t_sys_roleprivilege` VALUES (''e886c5c60a5e4d2da8d5b866130004bd'', ''904833575bab45d793e829f047dde248'', ''f0701944bc1d491393733fd9743a0633'');
INSERT INTO `t_sys_roleprivilege` VALUES (''e942d4fd92a746e2bc54976386787296'', ''579e631603194e47a37d452df119f0ef'', ''0ea5f5fa3ba042049fe5b09ab3f546b1'');
INSERT INTO `t_sys_roleprivilege` VALUES (''eb178187836049468ead9dc374639b38'', ''904833575bab45d793e829f047dde248'', ''04f3562771a744bdb70511f6ca63e1a7'');
INSERT INTO `t_sys_roleprivilege` VALUES (''eb38f02464cc4470be52ac69a92814c2'', ''076a6751978140bf86c00079852b5d95'', ''90506dda4f4f4ad6bd43822761d68098'');
INSERT INTO `t_sys_roleprivilege` VALUES (''ee34d977b1da436abc9827b144c7d7f1'', ''076a6751978140bf86c00079852b5d95'', ''f4fd46dd59be413ab38b8f94f8af340c'');
INSERT INTO `t_sys_roleprivilege` VALUES (''f06667f8881347d29f1270603b1c537f'', ''076a6751978140bf86c00079852b5d95'', ''11788e342aba4f389138713f0b510bd9'');
INSERT INTO `t_sys_roleprivilege` VALUES (''f15984f823214174a3b988508670fb37'', ''579e631603194e47a37d452df119f0ef'', ''93d786f2bfbf46398e3b495f6c70156c'');
INSERT INTO `t_sys_roleprivilege` VALUES (''f1f21af4dcab4f0d9862e0af85dcb9aa'', ''076a6751978140bf86c00079852b5d95'', ''4f878d16bad64263870afb77132ce93c'');
INSERT INTO `t_sys_roleprivilege` VALUES (''f2d06ae87d5c43699a7e1ee83229109b'', ''904833575bab45d793e829f047dde248'', ''e81599e7d0644d39ae62fc0950dd5e7d'');
INSERT INTO `t_sys_roleprivilege` VALUES (''f34f4103a09e4bb2855cbcd5188eb27c'', ''904833575bab45d793e829f047dde248'', ''84a837fc59124d5d98d1252681b5b1fe'');
INSERT INTO `t_sys_roleprivilege` VALUES (''f3eaeaeb602f4061b341947ffb1e4def'', ''904833575bab45d793e829f047dde248'', ''64341b7a30ab43ae9b22bcf252abcab4'');
INSERT INTO `t_sys_roleprivilege` VALUES (''f727db470fc540d48dab6ed190ad7887'', ''904833575bab45d793e829f047dde248'', ''4f878d16bad64263870afb77132ce93c'');
INSERT INTO `t_sys_roleprivilege` VALUES (''f7ed98d91bb24515aefa6e92b6099a83'', ''579e631603194e47a37d452df119f0ef'', ''9243558bee9548bcb08ca596c37e8a5b'');
INSERT INTO `t_sys_roleprivilege` VALUES (''f8fbfe5ee37a4859870f935cba0b9388'', ''904833575bab45d793e829f047dde248'', ''20d786f2bfbf46398e3b495f6c70156c'');
INSERT INTO `t_sys_roleprivilege` VALUES (''f9c6dbcc7035494fb9b61785a00f784c'', ''076a6751978140bf86c00079852b5d95'', ''f3ee4494e48a47c9ac0eda896759d32e'');
INSERT INTO `t_sys_roleprivilege` VALUES (''fb1f1c67eafd4fc087d08a5f349c6f7f'', ''b1dbc8db116d42538d67e80329b471f0'', ''dc86ce2ecef34cbea905ad78305e9e10'');
INSERT INTO `t_sys_roleprivilege` VALUES (''fbbb6e7dfdd94fd6b2de85b184e88e6d'', ''579e631603194e47a37d452df119f0ef'', ''8e449f3473114318b50ff24eb7f35dd0'');
INSERT INTO `t_sys_roleprivilege` VALUES (''fd114926afc1456bbb5a849f240d2417'', ''076a6751978140bf86c00079852b5d95'', ''3025f524de014e6e905b8e061eb72890'');
INSERT INTO `t_sys_roleprivilege` VALUES (''fed31de0196f4d99895a440f878edace'', ''579e631603194e47a37d452df119f0ef'', ''72564b01bf504573b4118c458eab96d5'');

-- ----------------------------
-- Table structure for t_sys_settings
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_settings`;
CREATE TABLE `t_sys_settings`  (
  `setting_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''主键'',
  `setting_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''属性类型(sys系统级user用户级)'',
  `setting_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''属性编码'',
  `setting_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''属性名称'',
  `setting_value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT ''属性值'',
  `setting_group` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''属性分组'',
  `setting_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''属性类型为user用户级时的用户id'',
  `state` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''状态(Y有效N无效)'',
  `create_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建人'',
  `create_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''修改人'',
  `update_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''修改时间'',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''备注'',
  PRIMARY KEY (`setting_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''系统全局配置表'' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_sys_settings
-- ----------------------------
INSERT INTO `t_sys_settings` VALUES (''23ec7635acdc438592171e0a5f444aaf'', ''sys'', ''logoTitle'', ''图标标题'', ''教学实训云平台'', NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2020-10-15 08:56:57'', NULL);
INSERT INTO `t_sys_settings` VALUES (''23ec7635acdc438592171e0a5f44af'', ''sys'', ''companyInfo'', ''公司信息'', ''湖南创蓝信息科技有限公司'', NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2020-10-15 09:02:04'', NULL);
INSERT INTO `t_sys_settings` VALUES (''23ec7635acdc438592171e0a5fccaf'', ''sys'', ''cbLogo'', ''创蓝图标'', ''863cba20-c943-4b88-a90e-0f222ade6caa.jpeg'', NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2022-09-14 09:30:09'', NULL);
INSERT INTO `t_sys_settings` VALUES (''23ec7635acdc438592171e0a5fyyaf'', ''sys'', ''contactInfo'', ''联系电话'', ''0731-89913439 '', NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2019-07-05 08:40:08'', NULL);
INSERT INTO `t_sys_settings` VALUES (''23ec7635acdc438592171e0abnfccaf'', ''sys'', ''loginBgImg'', ''登录背景图'', NULL, NULL, NULL, ''1'', ''1'', NULL, ''1'', ''2019-06-28 17:38:27'', NULL);
INSERT INTO `t_sys_settings` VALUES (''wechat-official-account'', ''official'', ''weChatOfficialAccountAccessToken'', ''创蓝微信公众号AccessToken'', ''54_76jFh4JC9d3VaSJT4icKocTZh0MhlLiM_v0ZQCcVv7OH6DN1H2F2FEIB-Q4YKT9HFGk3OWaZDW4e7VklKtvEFSnPVM1aHJupoLZNA6BSysF6qaZp9vGeXbZibpStBACql2Idhn9ZJZkpkVTBVQRgAIAQLM'', NULL, NULL, NULL, NULL, ''2022-01-18 17:30:00'', NULL, ''2022-03-11 17:00:00'', NULL);
INSERT INTO `t_sys_settings` VALUES (''wxaccesstoken'', ''wx'', ''wxAccessToken'', ''布道师小程序AccessToken'', ''58_McJThGTHdSDtYwcMxG0Rf8MNjA5_F5fZ-OKDstalyRsZW7jPod2wjXGUdSzUqiX_PB6Lv7Hkw2qWDLy6ExgPyd2LIYyMTj5lIzH_2-b0MnAF4A5p1DeM1z8Uh8lNMX1ONfzEVbNld8ExDgvaJPXiAEAZFN'', NULL, NULL, ''1'', NULL, ''2020-08-03 10:43:01'', ''3338de437ceb492fa253e7d50bbce107'', ''2022-07-16 16:00:11'', NULL);

-- ----------------------------
-- Table structure for t_sys_userinfo
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_userinfo`;
CREATE TABLE `t_sys_userinfo`  (
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''主键'',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''用户名'',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''密码'',
  `salt` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''盐'',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''邮箱'',
  `mobile` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''手机号'',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT ''状态  0：禁用   1：正常'',
  `create_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建者ID'',
  `create_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建时间'',
  `userimg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''用户头像'',
  `zip` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''邮政编码'',
  `sort_num` int(0) NULL DEFAULT NULL COMMENT ''排序号'',
  `user_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''用户类型'',
  `post_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''所属岗位'',
  `sex` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''性别'',
  `USER_REALNAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''真实姓名'',
  `user_theme` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''用户选择皮肤'',
  `user_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''身份证号码'',
  `birthday` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''出生年月'',
  `native_place` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''家庭住址'',
  `nation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''民族'',
  `update_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建者ID'',
  `update_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''创建时间'',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''系统用户'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_userinfo
-- ----------------------------
INSERT INTO `t_sys_userinfo` VALUES (''1'', ''admin'', ''0163b9a73b7022c99590ae5d9d33c735'', '''', ''zhujianwu@creatorblue.com'', ''18229923839'', 1, ''1'', ''2016-11-11 11:11:11'', ''http://localhost:19084/console/uploads/sys-user-img/12a10d95-0ef1-4992-abdc-5e5a2c105a28.jpeg'', NULL, 1, ''1'', NULL, ''1'', ''系统管理员'', '''', ''430223198811111111'', NULL, ''账号密码 admin/admin'', ''汉族'', ''1'', ''2022-09-14 09:30:11'');

-- ----------------------------
-- Table structure for t_sys_userprivilege
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_userprivilege`;
CREATE TABLE `t_sys_userprivilege`  (
  `USERPRVIID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''用户特权ID'',
  `MENU_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''资源ID'',
  `USERID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''用户ID'',
  PRIMARY KEY (`USERPRVIID`) USING BTREE,
  INDEX `FK_Reference_17`(`MENU_ID`) USING BTREE,
  CONSTRAINT `FK_Reference_17` FOREIGN KEY (`MENU_ID`) REFERENCES `t_sys_resource` (`menu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''用户特权:主要用来解决不通过角色,单独给用户授权'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_group
-- ----------------------------
DROP TABLE IF EXISTS `t_user_group`;
CREATE TABLE `t_user_group`  (
  `USERGROUP_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''主键'',
  `GROUP_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''组ID'',
  `USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''用户ID'',
  PRIMARY KEY (`USERGROUP_ID`) USING BTREE,
  INDEX `FK_Reference_10`(`USER_ID`) USING BTREE,
  INDEX `FK_Reference_9`(`GROUP_ID`) USING BTREE,
  CONSTRAINT `t_user_group_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `t_sys_userinfo` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_user_group_ibfk_2` FOREIGN KEY (`GROUP_ID`) REFERENCES `t_sys_group` (`GROUP_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''用户组与用户关联表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_post
-- ----------------------------
DROP TABLE IF EXISTS `t_user_post`;
CREATE TABLE `t_user_post`  (
  `USER_JOBID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''主键'',
  `USER_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''用户ID'',
  `POST_ID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''岗位ID'',
  `IS_MAIN` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT ''0'' COMMENT ''是否主岗位（0：否，1：是）'',
  PRIMARY KEY (`USER_JOBID`) USING BTREE,
  INDEX `FK_Reference_14`(`POST_ID`) USING BTREE,
  INDEX `FK_Reference_8`(`USER_ID`) USING BTREE,
  CONSTRAINT `t_user_post_ibfk_1` FOREIGN KEY (`POST_ID`) REFERENCES `t_sys_post` (`POST_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_user_post_ibfk_2` FOREIGN KEY (`USER_ID`) REFERENCES `t_sys_userinfo` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''用户岗位关联表'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''主键'',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''用户ID'',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''角色ID'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''用户与角色对应关系'' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_token
-- ----------------------------
DROP TABLE IF EXISTS `t_user_token`;
CREATE TABLE `t_user_token`  (
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''主键'',
  `token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT ''token编码'',
  `expire_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''过期时间'',
  `update_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT ''更新时间'',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `token`(`token`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ''系统用户Token'' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
