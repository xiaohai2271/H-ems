# 员工管理系统(Employee Management System)

> 禾几海

- 用户登录
- 用户注册
- 提供系统查询，信息修改等基本功能
- 提供登录，注册信息的加密传输

## 技术栈
Springboot + Spring Data Jpa + Mysql + Thymeleaf + LayUI


### 密码加密
> AES / ECB / PKCS5Padding

传输: password 用Email做密钥进行AES对称加密

数据库存储: MD5( Email + 解密后的密码 + 全局应用盐 )


## 运行

```bash
.\mvnw spring-boot:run
```


## 文件链接
数据库文件

[建表DDL](src/main/resources/sql/schema.sql) 

[sql测试数据](src/main/resources/sql/data.sql)   

[前端文件`static` / `template`](src/main/resources)