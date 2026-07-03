@echo off
chcp 65001 >nul
title 课堂投票系统 CVS
cd /d "%~dp0"

:: 检查 Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [错误] 未找到 Java，请先安装 JDK 21
    echo 下载地址: https://adoptium.net/download/
    pause
    exit /b 1
)

:: 如果 JAR 不存在，自动构建（使用 Maven Wrapper，无需安装 Maven）
if not exist "target\cvs-app.jar" (
    echo [1/2] 首次运行，正在构建项目（需要下载依赖，约 1-2 分钟）...
    call mvnw.cmd clean package -DskipTests -q
    if %errorlevel% neq 0 (
        echo [错误] 构建失败，请检查网络连接
        pause
        exit /b 1
    )
    echo [1/2] 构建完成！
)

:: 防火墙提示
netsh advfirewall firewall show rule name="CVS-Port8080" >nul 2>&1
if %errorlevel% neq 0 (
    echo.
    echo ╔══════════════════════════════════════════════╗
    echo ║  如需局域网访问，请以管理员身份运行:         ║
    echo ║  netsh advfirewall firewall add rule         ║
    echo ║  name="CVS-Port8080" dir=in action=allow     ║
    echo ║  protocol=TCP localport=8080                  ║
    echo ╚══════════════════════════════════════════════╝
    echo.
)

:: 启动
echo [2/2] 启动服务中...
echo.
echo ╔══════════════════════════════════════════════╗
echo ║  课堂投票系统已启动！                        ║
echo ║  本地访问: http://localhost:8080              ║
echo ║  教师账号: teacher1 / 123456                  ║
echo ║  学生账号: student1 / 123456                 ║
echo ╚══════════════════════════════════════════════╝
echo.

echo 按 Ctrl+C 停止服务
echo.
java -jar target\cvs-app.jar

echo.
echo 服务已停止。
pause
