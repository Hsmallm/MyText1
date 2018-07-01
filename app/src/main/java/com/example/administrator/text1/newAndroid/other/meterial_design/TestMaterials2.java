package com.example.administrator.text1.newAndroid.other.meterial_design;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.text1.R;

/**
 * @author HuangMing on 2017/12/1.
 *         功能描述：
 *         CollapsingToolbarLayout：可折叠标题栏目布局控件，是作用与ToolBar之上的布局，可以让ToolBar的效果更加丰富
 *                                  （注：CollapsingToolbarLayout不能独立存在，只能作为AppBarLayout的直接子布局来使用；而AppBarLayout又必须是CoordinatorLayout的子布局）
 */

public class TestMaterials2 extends AppCompatActivity {

    public static final String FRUIT_NAME = "FRUIT_NAME";
    public static final String FRUIT_IMAGE_ID = "FRUIT_IMAGE_ID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_materilas2);

        Intent intent = getIntent();
        String fruit = intent.getStringExtra(FRUIT_NAME);
        int id = intent.getIntExtra(FRUIT_IMAGE_ID, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView textView = (TextView) findViewById(R.id.fruit_content);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(fruit);
        imageView.setImageResource(id);
        textView.setText(getFruitContent(fruit));
    }

    private String getFruitContent(String name) {
        StringBuilder fruitContent = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            fruitContent.append(name);
        }
        return fruitContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
