#仿百度文库完整项目

演示地址:https://wenku.izerofx.com:8000/

项目很多细节不完善，但大致可以用，主要用于学习参考用。

#技术框架
* 核心框架：Spring Boot 1.4.0
* 视图框架：Spring MVC 4.3.4
* 视图模版: Thymeleaf 3.0.2
* 持久层框架：Spring Data Jpa
* 数据库连接池：Druid 
* 日志管理：Log4j
* CSS框架：layui

#使用外部组件
* LibrOffice 用于把文档转为pdf
* SWFTools 用于把pdf转成swf
* zbus 用到了Zbus MQ处理转换的队列(ps:其实可以不用)

#以下是部分截图
![首页](http://git.oschina.net/uploads/images/2016/1116/162935_e8226545_1198.png "首页")
![文档页](http://git.oschina.net/uploads/images/2016/1116/162953_22323def_1198.png "文档页")
![阅读页](http://git.oschina.net/uploads/images/2016/1116/163011_725be631_1198.png "阅读页")
![用户主页](http://git.oschina.net/uploads/images/2016/1116/163030_7f6f9915_1198.png "用户主页")