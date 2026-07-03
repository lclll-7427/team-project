# 更新日志

## [1.0.0] - 2026-07-03

### 🎉 首次发布

#### 新增功能
- **用户管理**：注册/登录，支持教师和学生两种角色
- **课程管理**：教师创建课程，学生通过课程 ID 加入
- **知识点管理**：为课程添加/删除知识点，构建知识体系
- **投票管理**：教师发起投票（设置选项与正确答案），学生参与投票
- **数据统计**：课程概览、知识点掌握度、学生排名，Chart.js 图表可视化
- **一键启动**：`start.bat` / `start.sh` 脚本，`build.bat` 构建脚本
- **Maven Wrapper**：无需单独安装 Maven，`mvnw` 自动下载
- **GitHub Actions CI/CD**：自动编译、测试、打包
- **示例数据**：内置教师/学生账号、课程、知识点、投票示例

#### 技术架构
- Spring Boot 3.4 + Spring Data JPA
- H2 内存数据库（开发/演示）
- 三层架构：Controller → Service → Repository
- RESTful API 设计（15 个端点）
- 7 个 JPA Entity + 7 个 Repository
- 5 个 Service + 5 个 Controller
- 10 个 DTO/VO 类
- 原生 HTML/CSS/JS 前端 + Chart.js

#### 演示账号
| 用户名 | 密码 | 角色 |
|--------|------|------|
| teacher1 | 123456 | 教师 |
| student1 | 123456 | 学生 |
| student2 | 123456 | 学生 |
| student3 | 123456 | 学生 |
