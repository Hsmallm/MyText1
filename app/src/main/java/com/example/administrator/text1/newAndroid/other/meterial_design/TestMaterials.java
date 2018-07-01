package com.example.administrator.text1.newAndroid.other.meterial_design;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.newAndroid.other.MyApplication;
import com.seaway.android.common.toast.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author HuangMing on 2017/11/30.
 *         功能描述：
 *         Material Design：由Google公司2014年的I/O大会上推出的一款全新的设计语言，但是了普及不是很顺利，因为他只是一个推荐的设计规范，主要是面向UI设计人员
 *                          而不是开发人员，而且也没有提供相应的API；2015年的I/O大会上推出了一个Design Support库，这里面就封装了Material Design最具代表性质的控件和效果...
 *         ToolBar：他不仅继承了ActionBar所有的功能，而且他的灵活性很高，也可以配合其他的恐惧完成Material Design效果(ActionBar:每个活动页顶部的标题栏就是...)
 *         xmlns:app：表示指定一个新的命名空间，由于Material Design实在Android5.0出现的，很多Material Design的属性以前的版本是不存在的，因此这个就是为了兼容老版本的
 *         DrawerLayout：滑动菜单是一个布局控件，允许放入两个直接的子控件；第一个控件为主屏显示的内容，第二个为滑动菜单显示的内容
 *         NavigationView：是由Design Support库所提供的一个控件，也是严格的按照material design要求设计的，可以让滑动菜单的实现更加简单...
 *                         准备两个文件menu显示具体的菜单项和headerLayout显示头部布局
 *         Snackbar：提示工具类，相关于Toast的升级版，允许提示中加入一个可交互的按钮
 *         CoordinatorLayout：FrameLayout的加强版，可以监听子控件的各种事件，并帮助我们做出合理的反应
 *         CardView：它也是一个FrameLayout，只是额外的提供了一些圆角的阴影的效果
 *         AppBarLayout：他是一个垂直方向的LinearLayput,内部做了许多滚动事件的封装
 */

public class TestMaterials extends AppCompatActivity {

    private DrawerLayout layout;

    private List<Fruit> list = new ArrayList<>();
    private Fruit[] fruits = {new Fruit("Apple",R.drawable.image1),new Fruit("Banana",R.drawable.image2),new Fruit("Orange",R.drawable.image3),
            new Fruit("Pear",R.drawable.image4),new Fruit("Grape",R.drawable.image5),new Fruit("Cherry",R.drawable.image1),
            new Fruit("Pineapple",R.drawable.image2),
            new Fruit("Mango",R.drawable.image3)};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_materilas);
        //设置ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //悬浮按钮
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floating);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //允许提示中加入一个可交互的按钮
                Snackbar.make(v,"Are You Sure!",Snackbar.LENGTH_SHORT)
                        .setAction("delete", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.showToast(MyApplication.getContext(),"check");
                            }
                        }).show();
            }
        });
        //滑动控件（可以嵌套两个直接子视图：主视图和左侧滑动菜单）
        layout = (DrawerLayout) findViewById(R.id.drawer);
        //左侧滑动菜单
        NavigationView navigationView = (NavigationView) findViewById(R.id.design_navigation_view);
        navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                layout.closeDrawers();
                return true;
            }
        });
        //设置ActionBar左上角图标
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.trc_category);
        }

        initFruit();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        FruitAdapter adapter = new FruitAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initFruit(){
        list.clear();
        for (int i = 0; i<50; i++){
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            list.add(fruits[index]);
        }
    }

    /**
     * 菜单视图初始化
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 选择点击菜单监听
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.back) {
            Toast.showToast(MyApplication.getContext(), "back");
        } else if (item.getItemId() == R.id.delete) {
            Toast.showToast(MyApplication.getContext(), "delete");
        } else if (item.getItemId() == R.id.setting) {
            Toast.showToast(MyApplication.getContext(), "setting");
        } else if (item.getItemId() == android.R.id.home) {
            layout.openDrawer(GravityCompat.START);
        }
        return true;
    }

    class FruitAdapter extends RecyclerView.Adapter<Holder>{

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(TestMaterials.this).inflate(R.layout.adapter_fruit_item,parent,false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            final Fruit fruit = list.get(position);
            holder.tv.setText(fruit.getName());
            holder.iv.setImageResource(fruit.getImageId());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TestMaterials.this,TestMaterials2.class);
                    intent.putExtra(TestMaterials2.FRUIT_NAME,fruit.getName());
                    intent.putExtra(TestMaterials2.FRUIT_IMAGE_ID,fruit.getImageId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class Holder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView iv;
        TextView tv;

        public Holder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            iv = (ImageView) itemView.findViewById(R.id.imageView);
            tv = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
