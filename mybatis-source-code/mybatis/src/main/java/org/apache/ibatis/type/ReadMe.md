**类型模块**

① MyBatis 为简化配置文件提供了**别名机制**，该机制是类型转换模块的主要功能之一。
② 类型转换模块的另一个功能是**实现 JDBC 类型与 Java 类型之间**的转换，该功能在为 SQL 语句绑定实参以及映射查询结果集时都会涉及：
- 在为 SQL 语句绑定实参时，会将数据由 Java 类型转换成 JDBC 类型。
- 而在映射结果集时，会将数据由 JDBC 类型转换成 Java 类型。