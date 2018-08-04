package com.tang.listviewslidedel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (SlideDelListView) findViewById(R.id.listView);
        // 不要直接Arrays.asList
        mDatas = new ArrayList<>(Arrays.asList("HelloWorld", "Welcome", "Java", "Android", "Servlet", "Struts",
                "Hibernate", "Spring", "HTML5", "Javascript", "Lucene", "HelloWorld", "Welcome", "Java", "Android", "Servlet", "Struts",
                "Hibernate", "Spring", "HTML5", "Javascript", "Lucene", "HelloWorld", "Welcome", "Java", "Android", "Servlet", "Struts",
                "Hibernate", "Spring", "HTML5", "Javascript", "Lucene"));

        initData();
    }

    private void initData() {
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mDatas);
        mListView.setAdapter(mAdapter);

//        mListView.setDelButtonClickListener(new DelButtonClickListener()
//        {
//            @Override
//            public void clickHappend(final int position)
//            {
//                Toast.makeText(MainActivity.this, position + " : " + mAdapter.getItem(position), 1).show();
//                mAdapter.remove(mAdapter.getItem(position));
//            }
//        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, position + " : " + mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

}