# Classroom Vote System (CVS)

课堂投票与知识点掌握度分析系统 — 基于 Spring Boot 三层架构的全栈应用。

## 🏗️ 技术架构

```
┌─────────────────────────────────────────┐
│         Controller 层 (REST API)         │
│  Auth / Course / KnowledgePoint         │
│  Vote / Statistics                      │
├─────────────────────────────────────────┤
│           Service 层 (业务逻辑)           │
│  权限校验、投票计算、掌握度统计            │
├─────────────────────────────────────────┤
│         Repository 层 (JPA 数据访问)      │
│  Spring Data JPA + H2 Database          │
└─────────────────────────────────────────┘
```

| 层级 | 技术栈 |
|------|--------|
| 后端框架 | Spring Boot 3.4 + Spring Data JPA |
| 数据库 | H2 (内存数据库, 开发/演示) / MySQL (生产) |
| 构建工具 | Maven |
| 前端 | 原生 HTML/CSS/JS + Chart.js |
| Java 版本 | JDK 21 |

## 🚀 快速开始

```bash
# 克隆项目
git clone https://github.com/lclll-7427/team-project.git
cd team-project

# 启动应用 (默认端口 8080)
./mvnw spring-boot:run

# 或者打包运行
./mvnw clean package -DskipTests
java -jar target/cvs-app.jar
```

访问 http://localhost:8080 即可使用系统。

### 演示账号

| 用户名 | 密码 | 角色 |
|--------|------|------|
| teacher1 | 123456 | 教师 |
| student1 | 123456 | 学生 |
| student2 | 123456 | 学生 |
| student3 | 123456 | 学生 |

## 📋 功能模块

### 用户管理
- 登录 / 注册（区分教师与学生角色）
- 基于角色的权限控制

### 课程管理
- 教师：创建/删除课程
- 学生：通过课程ID加入课程

### 知识点管理
- 为课程添加/删除知识点
- 构建课程知识体系

### 投票管理
- 教师：发起投票（关联知识点，设置选项与正确答案）
- 学生：参与投票（单选）
- 教师：关闭投票
- 实时票数统计

### 数据统计分析
- 课程概览（知识点数、投票数、学生数、总体掌握度）
- 各知识点掌握度（柱状图 + 进度条）
- 各学生掌握度排名（横向柱状图）
- Chart.js 可视化图表

## 📡 REST API

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/auth/login` | 用户登录 |
| POST | `/api/auth/register` | 用户注册 |
| GET | `/api/courses?userId=&role=` | 课程列表 |
| POST | `/api/courses` | 创建课程 |
| POST | `/api/courses/{id}/enroll` | 加入课程 |
| DELETE | `/api/courses/{id}?teacherId=` | 删除课程 |
| GET | `/api/courses/{id}/knowledge-points` | 知识点列表 |
| POST | `/api/courses/{id}/knowledge-points` | 添加知识点 |
| DELETE | `/api/knowledge-points/{id}?teacherId=` | 删除知识点 |
| POST | `/api/vote-sessions?teacherId=` | 创建投票 |
| GET | `/api/vote-sessions/{id}` | 投票详情 |
| POST | `/api/vote-sessions/{id}/vote` | 提交投票 |
| PUT | `/api/vote-sessions/{id}/close?teacherId=` | 关闭投票 |
| GET | `/api/statistics/courses/{id}/overview` | 课程概览 |
| GET | `/api/statistics/courses/{id}/knowledge-points` | 知识点掌握度 |
| GET | `/api/statistics/courses/{id}/students` | 学生掌握度 |

## 📁 项目结构

```
team-project/
├── pom.xml
├── src/main/java/com/cvs/
│   ├── CvsApplication.java          # 启动类
│   ├── config/WebConfig.java        # CORS 配置
│   ├── model/                       # Entity: User, Course, KnowledgePoint,
│   │                                  VoteSession, VoteOption, VoteRecord...
│   ├── repository/                  # JPA Repository 接口
│   ├── service/                     # 业务逻辑层
│   ├── controller/                  # REST API 控制器
│   └── dto/                         # 数据传输对象
├── src/main/resources/
│   ├── application.yml              # 应用配置
│   ├── data.sql                     # 示例数据
│   └── static/                      # 前端页面
│       ├── index.html               # 入口跳转
│       ├── login.html               # 登录/注册
│       ├── courses.html             # 课程列表
│       ├── course-detail.html       # 课程详情 + 投票
│       ├── statistics.html          # 数据统计图表
│       ├── css/style.css            # 全局样式
│       └── js/api.js                # API 封装
└── .github/workflows/
    ├── ci.yml                       # CI: Maven 编译测试
    └── deploy.yml                   # CD: 打包 JAR 产物
```

## 🔄 GitHub Actions 自动化

| 工作流 | 触发 | 功能 |
|--------|------|------|
| **CI** | push / PR to main | JDK 21 → Maven 编译 → 测试 → 打包 → 上传 JAR |
| **Deploy** | push to main | 测试 → 打包 → 上传 JAR (30天保留) |
