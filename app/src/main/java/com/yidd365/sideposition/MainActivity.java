package com.yidd365.sideposition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yidd365.siderbar.SiderBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    protected ListView listView;
    protected SiderBar siderBar;
    protected ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.main_lv);
        siderBar = (SiderBar) findViewById(R.id.sideBar);
        initData();
    }

    private void initData() {
        for(int i=0;i<39;i++){
            data.add((i+1)+"");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        siderBar.setSize(adapter.getCount());
        siderBar.setOnTouchingBarChangedListener(new SiderBar.OnTouchingBarChangedListener() {
            @Override
            public void OnTouchingBarChangedListener(int pos) {
                if (pos != -1) {
                    listView.setSelection(5*(pos));
                }
            }
        });
    }
}
