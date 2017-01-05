package com.example.administrator.text1.ui.testOther;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.administrator.text1.R;

/**
 * Created by Administrator on 2016/3/4.
 */
public class TextGridViewActivity extends Activity {

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        gridView = (GridView) findViewById(R.id.grid);
        final Integer[] ids = {R.drawable.untitled,R.drawable.untitled2,
                R.drawable.untitled,R.drawable.untitled2,
                R.drawable.untitled,R.drawable.untitled2,
                R.drawable.untitled,R.drawable.untitled2,
                R.drawable.untitled,R.drawable.untitled2,
                R.drawable.untitled,R.drawable.untitled2,
                R.drawable.untitled,R.drawable.untitled2,
                R.drawable.untitled,R.drawable.untitled2};
        gridView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return ids.length;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView imageView = new ImageView(TextGridViewActivity.this);
                imageView.setLayoutParams(new GridView.LayoutParams(200,200));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setImageResource(ids[position]);
                return imageView;
            }
        });
    }
}
