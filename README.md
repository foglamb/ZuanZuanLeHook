# 赚赚乐 Hook 模块

这是一个用于"赚赚乐"应用的 LSPosed/Xposed Hook 模块。

## 功能特性

1. **金币叠加上限修改** - 拦截并修改 forecast_gold 字段
2. **红包冷却移除** - 完全移除红包点击冷却时间
3. **Transid 上报拦截** - 拦截并记录所有 transid 上报请求
4. **广告奖励回调** - Hook Sigmob/GroMore/穿山甲广告回调
5. **网络请求拦截** - 拦截 API 请求和 JWT 签名过程
6. **Token 获取** - 自动获取并记录登录 token

## 编译方法

### 方式 1: 使用 Android Studio
1. 在 Android Studio 中打开项目
2. 配置 SDK 位置（需要 SDK 34+）
3. Build -> Make Project
4. 在 `app/build/outputs/apk/debug/` 获取 APK

### 方式 2: 使用 Gradle 命令行
```bash
./gradlew assembleDebug
```

## 安装方法

1. 确保已安装 LSPosed 或 Xposed 框架
2. 安装编译好的 APK
3. 在 LSPosed Manager 中启用此模块
4. 勾选"赚赚乐"应用（com.yxrjhan.douxiong）
5. 重启目标应用
6. 使用 logcat 查看日志：
```bash
adb logcat | grep ZuanZuanLeHook
```

## 日志说明

- `✦ 赚赚乐 Hook 已加载 ✦` - 模块成功加载
- `✂ 红包冷却已移除` - 红包冷却移除成功
- `📤 拦截上报: adTask` - 拦截到广告任务上报
- `🏆 GroMore rewardVerify 触发!` - 广告奖励验证触发
- `🔑 Token: xxx` - 获取到登录 token

## 注意事项

- 需要 Root 权限和 Xposed/LSPosed 框架
- 仅用于学习和研究目的
- 请勿用于商业用途或破坏应用正常服务

## 版本信息

- 版本: 1.0
- 目标应用: 赚赚乐 v4.1.0.99
- 包名: com.yxrjhan.douxiong
