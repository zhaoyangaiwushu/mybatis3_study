**IO 解析器模块**

解析器模块，主要提供了两个功能:

- 一个功能，是对 [XPath](http://www.w3school.com.cn/xpath/index.asp) 进行封装，为 MyBatis 初始化时解析 `mybatis-config.xml` 配置文件以及映射配置文件提供支持。
- 另一个功能，是为处理动态 SQL 语句中的占位符提供支持。