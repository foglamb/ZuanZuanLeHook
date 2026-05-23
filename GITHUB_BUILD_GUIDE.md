# GitHub Actions 自动构建指南

本项目配置了 GitHub Actions，可以自动编译 LSPosed Hook 模块 APK。

## 📦 使用步骤

### 1. 创建 GitHub 仓库
```bash
# 在 GitHub 网站上创建新仓库（可以设为 Private）
# 仓库名: ZuanZuanLeHook
```

### 2. 初始化 Git 仓库
```bash
cd /home/analyze_apk/ZuanZuanLeHook
git init
git add .
git commit -m "Initial commit: 赚赚乐 Hook 模块"
```

### 3. 推送到 GitHub
```bash
git remote add origin https://github.com/你的用户名/ZuanZuanLeHook.git
git branch -M main
git push -u origin main
```

### 4. 触发构建

#### 方式 A: 手动触发（推荐）
1. 进入 GitHub 仓库页面
2. 点击 "Actions" 标签
3. 选择 "Build ZuanZuanLeHook APK" 工作流
4. 点击 "Run workflow" → "Run workflow"

#### 方式 B: 自动触发
- 推送代码到 `main` 或 `master` 分支会自动触发构建

### 5. 下载 APK

构建完成后（约2-3分钟）：

1. 点击进入完成的 workflow run
2. 滚动到页面底部的 "Artifacts" 区域
3. 下载 `ZuanZuanLeHook-APK` 压缩包
4. 解压后获得 `app-debug.apk`

## 🔧 工作流说明

- **触发条件**: 推送到 main/master 分支、Pull Request、或手动触发
- **Java 版本**: 17
- **Android SDK**: 34
- **输出**: Debug APK
- **保留时间**: 30 天

## 📱 安装 APK

```bash
adb install app-debug.apk
```

或直接在手机上安装 APK 文件。

## ⚙️ LSPosed 配置

1. 打开 LSPosed Manager
2. 找到 "赚赚乐Hook" 模块
3. 勾选作用域: `com.yxrjhan.douxiong`
4. 重启赚赚乐应用

## 🔍 查看日志

```bash
adb logcat -s ZuanZuanLeHook
```

## ❓ 常见问题

**Q: 构建失败怎么办？**
A: 点击 Actions 中的红色 workflow run，查看详细日志。

**Q: 可以修改代码后重新构建吗？**
A: 可以！修改后 `git add/commit/push`，或直接手动触发 workflow。

**Q: APK 签名是什么？**
A: 使用的是默认的 debug keystore，安装时会提示未知来源，这是正常的。

**Q: 为什么选择 GitHub Actions？**
A: 免费使用，无需本地配置 Android SDK，构建环境稳定可复现。
