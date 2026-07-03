# 贡献指南

感谢你对 Classroom Vote System (CVS) 的关注！我们欢迎任何形式的贡献。

## 行为准则

- 尊重所有贡献者，保持友善和专业的沟通
- 建设性地提出意见，专注于技术讨论
- 帮助新手，耐心回答问题

## 如何贡献

### 🐛 报告 Bug

1. 在 [Issues](https://github.com/lclll-7427/team-project/issues) 中搜索是否已有相同问题
2. 如果没有，创建新 Issue，包含：
   - 清晰的问题描述
   - 复现步骤
   - 预期行为和实际行为
   - 环境信息（操作系统、JDK 版本、浏览器）
   - 相关截图或日志

### 💡 功能建议

1. 在 Issues 中创建 Feature Request
2. 描述你希望的功能和使用场景
3. 说明为什么这个功能对项目有价值

### 💻 代码贡献

#### 开发流程

```bash
# 1. Fork 并 Clone
git clone https://github.com/你的用户名/team-project.git
cd team-project
git remote add upstream https://github.com/lclll-7427/team-project.git

# 2. 创建特性分支
git checkout -b feature/功能描述
# 或 fix/问题描述、docs/文档描述

# 3. 开发
# 确保代码符合项目规范

# 4. 运行测试
./mvnw test

# 5. 提交（使用约定式提交格式）
git commit -m "feat: 添加某某功能"

# 6. 同步上游
git fetch upstream
git rebase upstream/main

# 7. 推送
git push origin feature/功能描述
```

#### 提交信息规范

使用 [约定式提交](https://www.conventionalcommits.org/zh-hans/) 格式：

```
<类型>: <简短描述>

类型：
  feat     新功能
  fix      Bug 修复
  docs     文档更新
  style    代码格式（不影响逻辑）
  refactor 重构（既非新功能也非修复）
  test     测试相关
  chore    构建/工具/依赖更新
```

#### 代码规范

**Java：**
- Controller 层：只处理请求参数和响应，不写业务逻辑
- Service 层：业务逻辑 + 事务管理
- Repository 层：只定义数据访问接口
- DTO 用于请求/响应，VO 用于视图展示
- 统一使用 `ApiResponse` 封装返回值
- 异常信息使用中文，面向用户友好

**前端：**
- HTML/CSS/JS 放在 `src/main/resources/static/`
- 使用原生 JavaScript，不引入前端框架
- API 调用统一使用 `js/api.js` 中的封装
- 样式使用 CSS 变量（定义在 `:root`）

### 📝 文档贡献

文档同样重要！可以帮助：
- 修正 README 中的错误
- 添加 API 使用示例
- 翻译文档
- 完善注释

## Pull Request 流程

1. 确保你的分支基于最新的 `main`
2. 填写 PR 模板中的描述
3. 关联相关 Issue（如 `Closes #12`）
4. 等待 CI 检查通过
5. 至少一位维护者审核通过后合并

## 项目结构速查

```
src/main/java/com/cvs/
├── config/       # 配置类
├── controller/   # REST API 控制器（不写业务逻辑）
├── service/      # 业务逻辑层
├── repository/   # JPA 数据访问层
├── model/        # Entity 实体类
└── dto/          # 数据传输对象

src/main/resources/
├── application.yml  # 应用配置
├── data.sql         # 初始化数据
└── static/          # 前端静态资源
```

## 获取帮助

- 在 Issue 中 @ 维护者
- 在 Discussions 中发起技术讨论
- 查看 [README.md](README.md) 了解项目详情
