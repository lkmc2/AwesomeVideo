# AwesomeVideo
Spring Boot编写的倾心短视频小程序的后端

**项目使用的技术**：

- 框架：Spring Boot 2.0.5.RELEASE、Mybatis
- 数据库：MySQL、Redis
- API文档：Swagger2
- 插件：Druid连接池、Mybatis Generator、通用Mapper、PageHelper分页插件、Lombok、Spring Boot Devtools热部署

**项目运行方式**：

1. 创建数据库awesome_video。
2. 在数据库中运行src/main/resources下的awesome_video.sql文件。
3. 修改src/main/resources的application.properties配置文件中的的数据库用户名和密码。
4. 运行AwesomeVideoApplication.java启动项目。
5. 浏览器中打开http://localhost:8080/swagger-ui.html ，可访问到项目的API文档。

<img src="https://raw.githubusercontent.com/lkmc2/AwesomeVideo/master/picture/swagger2%E6%88%AA%E5%9B%BE.png"/>
