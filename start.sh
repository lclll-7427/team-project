#!/usr/bin/env bash
set -e

cd "$(dirname "$0")"

# 检查 Java
if ! java -version >/dev/null 2>&1; then
  echo "❌ 未找到 Java，请先安装 JDK 21"
  echo "下载: https://adoptium.net/download/"
  exit 1
fi

# 首次构建
if [ ! -f "target/cvs-app.jar" ]; then
  echo "🔨 首次运行，正在构建项目..."
  chmod +x mvnw 2>/dev/null || true
  ./mvnw clean package -DskipTests -q
  echo "✅ 构建完成！"
fi

# 启动
echo ""
echo "╔══════════════════════════════════════╗"
echo "║  课堂投票系统启动！                  ║"
echo "║  访问: http://localhost:8080          ║"
echo "║  教师: teacher1 / 123456             ║"
echo "║  学生: student1 / 123456             ║"
echo "╚══════════════════════════════════════╝"
echo ""

java -jar target/cvs-app.jar
