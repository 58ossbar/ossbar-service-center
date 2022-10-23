# ossbar-platform-service

为各终端提供服务：教务管理端、小程序端和教学实训云网站/门户端

# 平台优势

整体架构清晰、技术先进、源代码书写规范、易于维护、易于扩展、安全稳定。

# 技术选型

主框架：Spring Boot 2.1.7、Spring Framework 5.3、Apache Shiro 1.8

持久层：Apache MyBatis 3.5、Hibernate Validator 6.2、Alibaba Druid 1.2


# 系统需求

JDK >= 1.8

MySQL >= 8.0

Maven >= 3.0

Node >= 12

Redis >= 3

# 内置功能

用户管理：用户是系统操作者，该功能主要完成系统用户配置。

部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。

岗位管理：配置系统用户所属担任职务。

菜单管理：配置系统菜单，操作权限，按钮权限标识等。

角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。

字典管理：对系统中经常使用的一些较为固定的数据进行维护。

参数管理：对系统动态配置常用参数。

通知公告：系统通知公告信息发布维护。

操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。

登录日志：系统登录日志记录查询包含登录异常。

在线用户：当前系统中活跃用户状态监控。

定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。

系统接口：根据业务代码自动生成相关的api接口文档。

服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。

缓存监控：对系统的缓存信息查询，命令统计等。

连接池监视：监视当期系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。

# 体验地址
## 门户地址
https://frp.creatorblue.com/bds/
### 老师账号：18229923839 老师密码：cb123456
### 学生账号：13142149731  学生密码：123456
## 管理端访问地址：https://frp.creatorblue.com/bds-mgr/#/login
账号：admin
密码：888888
### 小程序
微信小程序中搜索布道师高校版

# 详细安装和部署手册

详见：https://www.ossbar.com/#/platformConsultingDetails?newsId=165
