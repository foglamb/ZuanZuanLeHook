package com.zuanzuanle.hook.ui;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
import com.zuanzuanle.hook.R;

public class SettingsActivity extends AppCompatActivity {
    
    private SwitchCompat enableCoinHook, enableRedPacketHook, enableAdHook, enableNetHook, enableTokenHook;
    private SwitchCompat enableLogOutput;
    private EditText coinLimitEdit;
    private Button logViewBtn, aboutBtn;
    private SharedPreferences prefs;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        
        enableCoinHook = findViewById(R.id.switch_coin_hook);
        enableRedPacketHook = findViewById(R.id.switch_redpacket_hook);
        enableAdHook = findViewById(R.id.switch_ad_hook);
        enableNetHook = findViewById(R.id.switch_net_hook);
        enableTokenHook = findViewById(R.id.switch_token_hook);
        enableLogOutput = findViewById(R.id.switch_log_output);
        coinLimitEdit = findViewById(R.id.edit_coin_limit);
        logViewBtn = findViewById(R.id.btn_log_view);
        aboutBtn = findViewById(R.id.btn_about);
        
        // 加载保存的设置
        loadSettings();
        
        // 保存开关状态
        enableCoinHook.setOnCheckedChangeListener((v, c) -> saveSettings());
        enableRedPacketHook.setOnCheckedChangeListener((v, c) -> saveSettings());
        enableAdHook.setOnCheckedChangeListener((v, c) -> saveSettings());
        enableNetHook.setOnCheckedChangeListener((v, c) -> saveSettings());
        enableTokenHook.setOnCheckedChangeListener((v, c) -> saveSettings());
        enableLogOutput.setOnCheckedChangeListener((v, c) -> saveSettings());
        
        // 金币上限输入
        coinLimitEdit.setOnEditorActionListener((v, a, e) -> {
            saveSettings();
            return false;
        });
        
        // 日志查看按钮
        logViewBtn.setOnClickListener(v -> {
            startActivity(LogViewActivity.newIntent(this));
        });
        
        // 关于按钮
        aboutBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                .setTitle("关于 赚赚乐Hook")
                .setMessage("版本: 1.0\n\n" +
                    "功能:\n" +
                    "• 金币叠加上限修改\n" +
                    "• 移除红包冷却\n" +
                    "• 自动拦截广告上报\n" +
                    "• 捕获广告奖励transId\n" +
                    "• 网络请求拦截\n" +
                    "• Token获取\n\n" +
                    "目标: com.yxrjhan.douxiong v4.1.0.99")
                .setPositiveButton("确定", null)
                .show();
        });
    }
    
    private void loadSettings() {
        enableCoinHook.setChecked(prefs.getBoolean("coin_hook", true));
        enableRedPacketHook.setChecked(prefs.getBoolean("redpacket_hook", true));
        enableAdHook.setChecked(prefs.getBoolean("ad_hook", true));
        enableNetHook.setChecked(prefs.getBoolean("net_hook", true));
        enableTokenHook.setChecked(prefs.getBoolean("token_hook", true));
        enableLogOutput.setChecked(prefs.getBoolean("log_output", false));
        coinLimitEdit.setText(String.valueOf(prefs.getInt("coin_limit", 999999)));
    }
    
    private void saveSettings() {
        prefs.edit()
            .putBoolean("coin_hook", enableCoinHook.isChecked())
            .putBoolean("redpacket_hook", enableRedPacketHook.isChecked())
            .putBoolean("ad_hook", enableAdHook.isChecked())
            .putBoolean("net_hook", enableNetHook.isChecked())
            .putBoolean("token_hook", enableTokenHook.isChecked())
            .putBoolean("log_output", enableLogOutput.isChecked())
            .putInt("coin_limit", parseIntSafe(coinLimitEdit.getText().toString(), 999999))
            .apply();
    }
    
    private int parseIntSafe(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }
}
