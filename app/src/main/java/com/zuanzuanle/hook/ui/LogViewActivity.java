package com.zuanzuanle.hook.ui;

import android.content.*;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import java.util.*;
import com.zuanzuanle.hook.*;
import com.zuanzuanle.hook.core.*;

public class LogViewActivity extends AppCompatActivity {
    
    private RecyclerView logList;
    private LogAdapter adapter;
    private Button clearBtn, refreshBtn;
    
    public static Intent newIntent(Context context) {
        return new Intent(context, LogViewActivity.class);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_view);
        
        logList = findViewById(R.id.log_list);
        clearBtn = findViewById(R.id.btn_clear_log);
        refreshBtn = findViewById(R.id.btn_refresh_log);
        
        logList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LogAdapter();
        logList.setAdapter(adapter);
        
        loadLogs();
        
        clearBtn.setOnClickListener(v -> {
            HookLogger.clear();
            loadLogs();
        });
        
        refreshBtn.setOnClickListener(v -> loadLogs());
    }
    
    private void loadLogs() {
        List<String> logs = HookLogger.getLogs();
        adapter.setLogs(logs);
        if (!logs.isEmpty()) logList.scrollToPosition(logs.size() - 1);
    }
    
    static class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {
        private List<String> logs = new ArrayList<>();
        
        void setLogs(List<String> logs) { this.logs = logs; notifyDataSetChanged(); }
        
        @Override public ViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
            TextView tv = new TextView(parent.getContext());
            tv.setPadding(8, 4, 8, 4);
            tv.setTextSize(10);
            tv.setTextIsSelectable(true);
            return new ViewHolder(tv);
        }
        
        @Override public void onBindViewHolder(ViewHolder holder, int pos) {
            String log = logs.get(pos);
            holder.textView.setText(log);
            if (log.contains("[ERROR]")) holder.textView.setTextColor(0xFFE53935);
            else if (log.contains("[WARN]")) holder.textView.setTextColor(0xFFFFA000);
            else if (log.contains("[OK]") || log.contains("[SUCCESS]")) holder.textView.setTextColor(0xFF43A047);
            else holder.textView.setTextColor(0xFF212121);
        }
        
        @Override public int getItemCount() { return logs.size(); }
        
        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            ViewHolder(TextView tv) { super(tv); textView = tv; }
        }
    }
}
