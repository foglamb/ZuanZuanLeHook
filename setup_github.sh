#!/bin/bash

# GitHub 自动构建快速启动脚本

echo "========================================="
echo "  赚赚乐 Hook 模块 - GitHub 自动构建"
echo "========================================="
echo ""

# 检查是否在项目目录
if [ ! -f "settings.gradle.kts" ]; then
    echo "❌ 错误: 请在项目根目录运行此脚本"
    exit 1
fi

# 检查 git 是否安装
if ! command -v git &> /dev/null; then
    echo "❌ 错误: Git 未安装"
    echo "   请先安装: sudo apt install git"
    exit 1
fi

echo "✅ 检测到项目文件"
echo ""

# 初始化 Git（如果未初始化）
if [ ! -d ".git" ]; then
    echo "📝 初始化 Git 仓库..."
    git init
    git add .
    git commit -m "Initial commit: 赚赚乐 Hook 模块"
    echo "✅ Git 仓库初始化完成"
else
    echo "✅ Git 仓库已存在"
fi

echo ""
echo "========================================="
echo "  下一步操作"
echo "========================================="
echo ""
echo "1️⃣  在 GitHub 上创建新仓库:"
echo "   - 访问: https://github.com/new"
echo "   - 仓库名: ZuanZuanLeHook"
echo "   - 可以设为 Private（私有）"
echo ""
echo "2️⃣  推送到 GitHub:"
echo "   git remote add origin https://github.com/你的用户名/ZuanZuanLeHook.git"
echo "   git branch -M main"
echo "   git push -u origin main"
echo ""
echo "3️⃣  触发自动构建:"
echo "   - 进入 GitHub 仓库 → Actions 标签"
echo "   - 点击 'Run workflow' → 'Run workflow'"
echo ""
echo "4️⃣  下载 APK:"
echo "   - 构建完成后（约2-3分钟）"
echo "   - 在 Actions 页面下载 'ZuanZuanLeHook-APK'"
echo ""
echo "========================================="
echo ""
echo "📖 详细说明请查看: GITHUB_BUILD_GUIDE.md"
echo ""
