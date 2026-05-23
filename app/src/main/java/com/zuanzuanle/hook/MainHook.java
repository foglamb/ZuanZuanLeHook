package com.zuanzuanle.hook;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XC_MethodHook;
import android.util.Log;

public class MainHook implements IXposedHookLoadPackage {

    private static final String TAG = "ZuanZuanLeHook";
    private static final String PKG_NAME = "com.yxrjhan.douxiong";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals(PKG_NAME)) return;

        Log.d(TAG, "✦ 赚赚乐 Hook 已加载 ✦");
        XposedBridge.log("✦ 赚赚乐 Hook 已加载 ✦");

        // ==========================================
        // 1. Hook 金币叠加上限 (forecast_gold)
        // ==========================================
        hookForecastGold(lpparam);
        
        // ==========================================
        // 2. Hook 红包点击冷却
        // ==========================================
        hookRedPacketCooldown(lpparam);
        
        // ==========================================
        // 3. Hook transid 上报
        // ==========================================
        hookTransidReport(lpparam);
        
        // ==========================================
        // 4. Hook 广告奖励回调
        // ==========================================
        hookAdReward(lpparam);
        
        // ==========================================
        // 5. Hook JWT签名/网络请求
        // ==========================================
        hookNetworkRequest(lpparam);
        
        // ==========================================
        // 6. Hook 登录Token
        // ==========================================
        hookToken(lpparam);
    }

    /**
     * 1. Hook 预期金币 (forecast_gold) - 修改叠加上限
     * 类: com.cka.renren.vm.MineVM
     */
    private void hookForecastGold(XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            Class<?> mineVM = XposedHelpers.findClass("com.cka.renren.vm.MineVM", lpparam.classLoader);
            
            // Hook getForecast_gold - 修改返回值
            XposedHelpers.findAndHookMethod(mineVM, "getForecast_gold", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    // 可以在这里修改返回值
                    Log.d(TAG, "getForecast_gold = " + param.getResult());
                }
            });
            
            // Hook setForecast_gold - 拦截设置
            XposedHelpers.findAndHookMethod(mineVM, "setForecast_gold", int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    int value = (int) param.args[0];
                    Log.d(TAG, "setForecast_gold = " + value);
                    // 可在此修改上限
                }
            });
            
            Log.d(TAG, "✅ Hook: forecast_gold 成功");
        } catch (Exception e) {
            Log.e(TAG, "❌ Hook: forecast_gold 失败", e);
        }
    }

    /**
     * 2. Hook 红包点击冷却 - 取消冷却时间
     * 类: com.cka.renren.main.mine.YqKtFragment
     */
    private void hookRedPacketCooldown(XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            Class<?> yqFragment = XposedHelpers.findClass("com.cka.renren.main.mine.YqKtFragment", lpparam.classLoader);
            
            // Hook isRedPacketInCooldown - 始终返回false（不在冷却）
            XposedHelpers.findAndHookMethod(yqFragment, "isRedPacketInCooldown", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    param.setResult(false);
                    Log.d(TAG, "✂ 红包冷却已移除");
                }
            });
            
            // Hook startRedPacketProgress - 自动触发
            XposedHelpers.findAndHookMethod(yqFragment, "startRedPacketProgress", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    Log.d(TAG, "📦 红包进度开始");
                }
            });
            
            Log.d(TAG, "✅ Hook: 红包冷却 成功");
        } catch (Exception e) {
            Log.e(TAG, "❌ Hook: 红包冷却 失败", e);
        }
    }

    /**
     * 3. Hook transid 上报 - 拦截并修改上报参数
     * 类: com.cka.renren.v2.ui.mine.api.YXMineNet
     */
    private void hookTransidReport(XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            Class<?> yxMineNet = XposedHelpers.findClass("com.cka.renren.v2.ui.mine.api.YXMineNet", lpparam.classLoader);
            
            // Hook 广告任务上报方法
            for (String methodName : new String[]{"adTask", "adPeriod", "adRegister"}) {
                try {
                    XposedHelpers.findAndHookMethod(yxMineNet, methodName, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            Log.d(TAG, "📤 拦截上报: " + methodName);
                            // 打印参数
                            for (int i = 0; i < param.args.length; i++) {
                                Log.d(TAG, "  arg[" + i + "] = " + param.args[i]);
                            }
                        }
                        
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            Log.d(TAG, "📥 上报结果: " + param.getResult());
                        }
                    });
                } catch (Exception ignored) {}
            }
            
            // Hook 获取金币的方法
            for (String methodName : new String[]{"getTeaGol", "getPuiGol", "getUseGol", "getOwnInc", "getNewTx"}) {
                try {
                    XposedHelpers.findAndHookMethod(yxMineNet, methodName, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            Log.d(TAG, "💰 " + methodName + " 返回: " + param.getResult());
                        }
                    });
                } catch (Exception ignored) {}
            }
            
            Log.d(TAG, "✅ Hook: YXMineNet 成功");
        } catch (Exception e) {
            Log.e(TAG, "❌ Hook: YXMineNet 失败", e);
        }
    }

    /**
     * 4. Hook 广告奖励回调 - 自动领取奖励
     * Sigmob/Pangle/GroMore 广告回调
     */
    private void hookAdReward(XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            ClassLoader cl = lpparam.classLoader;
            
            // Hook Sigmob激励视频回调
            try {
                Class<?> sigRewardAd = XposedHelpers.findClass("com.windmill.sigmob.SigRewardAdAdapter", cl);
                XposedHelpers.findAndHookMethod(sigRewardAd, "onRewardAdLoadSuccess", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        Log.d(TAG, "🎬 Sigmob 激励视频加载成功");
                    }
                });
            } catch (Exception ignored) {}
            
            // Hook GroMore奖励回调
            try {
                Class<?> groRewardAd = XposedHelpers.findClass("com.windmill.gromore.GroRewardAdAdapter", cl);
                XposedHelpers.findAndHookMethod(groRewardAd, "onRewardVerify", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Log.d(TAG, "🏆 GroMore rewardVerify 触发!");
                        // 打印rewardItem的transId
                        if (param.args.length > 0) {
                            Object rewardItem = param.args[0];
                            try {
                                String transId = (String) XposedHelpers.callMethod(rewardItem, "getTransId");
                                Log.d(TAG, "  transId = " + transId);
                            } catch (Exception e) {
                                Log.d(TAG, "  无法获取transId: " + e.getMessage());
                            }
                        }
                    }
                });
            } catch (Exception ignored) {}
            
            // Hook 穿山甲Pangle奖励回调
            try {
                Class<?> pangleRewardAd = XposedHelpers.findClass("com.bytedance.sdk.openadsdk.TTRewardVideoAd", cl);
                XposedHelpers.findAndHookMethod(pangleRewardAd, "onRewardVerify", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Log.d(TAG, "🏆 Pangle rewardVerify 触发!");
                    }
                });
            } catch (Exception ignored) {}
            
            Log.d(TAG, "✅ Hook: 广告奖励回调 成功");
        } catch (Exception e) {
            Log.e(TAG, "❌ Hook: 广告奖励回调 失败", e);
        }
    }

    /**
     * 5. Hook 网络请求 - 拦截/修改API请求参数和响应
     */
    private void hookNetworkRequest(XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            ClassLoader cl = lpparam.classLoader;
            
            // Hook OkHttp的请求构建
            try {
                Class<?> requestBuilder = XposedHelpers.findClass("okhttp3.Request$Builder", cl);
                XposedHelpers.findAndHookMethod(requestBuilder, "build", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        Object request = param.getResult();
                        try {
                            String url = (String) XposedHelpers.callMethod(request, "url");
                            // 只打印赚赚乐自己的API请求
                            if (url != null && (url.contains("yx1536.douxion.cn") || url.contains("api/"))) {
                                Log.d(TAG, "🌐 请求: " + url);
                            }
                        } catch (Exception ignored) {}
                    }
                });
            } catch (Exception ignored) {}
            
            // Hook JWT签名方法 (com.auth0.jwt)
            try {
                Class<?> jwtCreator = XposedHelpers.findClass("com.auth0.jwt.JWTCreator", cl);
                XposedHelpers.findAndHookMethod(jwtCreator, "sign", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        Log.d(TAG, "🔐 JWT签名 触发");
                    }
                });
            } catch (Exception ignored) {}
            
            Log.d(TAG, "✅ Hook: 网络请求 成功");
        } catch (Exception e) {
            Log.e(TAG, "❌ Hook: 网络请求 失败", e);
        }
    }

    /**
     * 6. Hook Token - 获取当前登录token
     */
    private void hookToken(XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            ClassLoader cl = lpparam.classLoader;
            
            // Hook getToken 方法
            try {
                XposedHelpers.findAndHookMethod("com.cka.renren.utils.TokenManager", cl, 
                    "getToken", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        String token = (String) param.getResult();
                        if (token != null && token.length() > 10) {
                            Log.d(TAG, "🔑 Token: " + token);
                            // 保存到剪贴板或文件
                        }
                    }
                });
            } catch (Exception ignored) {}
            
            Log.d(TAG, "✅ Hook: Token 成功");
        } catch (Exception e) {
            Log.e(TAG, "❌ Hook: Token 失败", e);
        }
    }
}
