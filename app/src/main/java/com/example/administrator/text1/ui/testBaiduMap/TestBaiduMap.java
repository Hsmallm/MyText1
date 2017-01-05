package com.example.administrator.text1.ui.testBaiduMap;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.Point;
import com.example.administrator.text1.R;
import com.seaway.android.common.toast.Toast;

import java.util.List;

/**
 * 功能描述：百度地图相关功能的引用
 * 1、设置地图的初始比例、实现多形式地图切换
 * 2、定位当前位置
 * 3、实现方向传感
 * 4、地图模式的切换
 * 5、覆盖物的添加、及覆盖物的点击处理
 * Created by hzhm on 2016/6/30.
 */
public class TestBaiduMap extends Activity {

    private MapView mapView;
    private BaiduMap baiduMap;

    //定位相关
    private LocationClient mLocationClient;
    private MyLocationListener mLocationListener;
    private boolean isFirst = true;
    private double mLatitude;
    private double mLongtitude;

    //自定义定位图标，实现方向传感
    private BitmapDescriptor mIconLocation;
    private MyOrientationListener myOrientationListener;
    private float mCurrentX;

    //地图模式
    private MyLocationConfiguration.LocationMode mLocationMode;

    //覆盖物相关
    private BitmapDescriptor mMark;
    private RelativeLayout mMarkerLy;
    private boolean handler = new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            baiduMap.hideInfoWindow();
        }
    },3000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //(注:该方法要再setContentView方法之前实现)
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_test_map);

        init();
        //初始化定位
        initLocation();
        //初始化覆盖物相关
        initMake();

        //5.3、点击marker标记监听
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //获取bundle传过来的信息，并完成相关设置
                Bundle bundle = marker.getExtraInfo();
                Info info = (Info) bundle.getSerializable("info");
                ImageView iv = (ImageView) mMarkerLy.findViewById(R.id.image111);
                TextView name = (TextView) mMarkerLy.findViewById(R.id.name);
                TextView distance = (TextView) mMarkerLy.findViewById(R.id.distance);
                TextView num = (TextView) mMarkerLy.findViewById(R.id.num);

                iv.setImageResource(info.getImgId());
                name.setText(info.getName());
                distance.setText(info.getDistance());
                num.setText(info.getZan()+"");

                ///5.3.1、点击覆盖物显示文本信息
                InfoWindow infoWindow;
                //初始化TextView对象
                TextView tv = new TextView(TestBaiduMap.this);
                tv.setBackgroundColor(Color.parseColor("#868686"));
                tv.setPadding(60,10,60,10);
                tv.setText(info.getName());
                tv.setTextSize(12);
                tv.setTextColor(Color.parseColor("#ffffff"));

                //获取到当前marker的经纬度，再转化为point坐标点
                final LatLng latLng = marker.getPosition();
                android.graphics.Point p = baiduMap.getProjection().toScreenLocation(latLng);
                p.y -= 110;
                //然后再将point坐标点转化为marker经纬度，并设置到当前infoWindow对象中，显示
                LatLng ll = baiduMap.getProjection().fromScreenLocation(p);
                infoWindow = new InfoWindow(tv,ll,11);
                baiduMap.showInfoWindow(infoWindow);
                //将文本设置延时3s消失
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        baiduMap.hideInfoWindow();
                    }
                },3000);

                mMarkerLy.setVisibility(View.VISIBLE);
                return true;
            }
        });

        //点击map地图监听
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                baiduMap.hideInfoWindow();
                mMarkerLy.setVisibility(View.GONE);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }



    private void init() {
        mapView = (MapView) this.findViewById(R.id.bmapView);
        //1.1、设置地图的初始比例
        baiduMap = mapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        baiduMap.setMapStatus(msu);
    }

    /**
     * 2.1、初始化定位对象、注册监听
     */
    private void initLocation() {
        //4.1、初始化地图模式对象
        mLocationMode = MyLocationConfiguration.LocationMode.NORMAL;
        mLocationClient = new LocationClient(this);
        mLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mLocationListener);

        //实例化定位坐标并设置
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");//坐标类型
        option.setIsNeedAddress(true);//返回地址设置为true
        option.setOpenGps(true);//打开GPS
        option.setScanSpan(1000);//每隔1s请求一次当前位置
        mLocationClient.setLocOption(option);

        //3.1、初始化图标、实例化传感器对象并设置方向改变监听
        mIconLocation = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);

        myOrientationListener = new MyOrientationListener(this);
        myOrientationListener.setOnOrienattionListener(new MyOrientationListener.OnOrienattionListener() {
            @Override
            public void OnOrienattionChange(float x) {
                mCurrentX = x;
            }
        });
    }

    /**
     * 5.1、初始化覆盖物相关
     */
    private void initMake() {
        //获取定位图标
        mMark = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
        mMarkerLy = (RelativeLayout) findViewById(R.id.mark_ly);
    }

    /**
     * 创建并实例化菜单项
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 1.2、设置菜单项选择监听(实现多模式地图切换)
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map_common:
                baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;

            case R.id.map_site:
                baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;

            case R.id.map_traffic:
                if(baiduMap.isTrafficEnabled()){
                    baiduMap.setTrafficEnabled(false);
                    item.setTitle("实时交通(off)");
                }else {
                    baiduMap.setTrafficEnabled(true);
                    item.setTitle("实时交通(on)");
                }
                break;

            case R.id.map_location:
                returnToCenter();
                break;

            case R.id.map_mode_common:
                mLocationMode = MyLocationConfiguration.LocationMode.NORMAL;
                break;

            case R.id.map_mode_following:
                mLocationMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                break;

            case R.id.map_mode_compass:
                mLocationMode = MyLocationConfiguration.LocationMode.COMPASS;
                break;
            //添加覆盖物
            case R.id.map_add_marks:
                addOverLay(Info.infos);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 5.2、添加覆盖物
     */
    private void addOverLay(List<Info> infos) {
        baiduMap.clear();
        LatLng latLng = null;
        Marker marker = null;
        OverlayOptions   options;
        for(Info info:infos){
            //经纬度
            latLng = new LatLng(info.getLatitude(),info.getLongtitude());
            //图标
            options = new MarkerOptions().position(latLng).icon(mMark).zIndex(5);
            marker = (Marker) baiduMap.addOverlay(options);
            //将info对象放到bundle再设置到marker中
            Bundle bundle = new Bundle();
            bundle.putSerializable("info",info);
            marker.setExtraInfo(bundle);
        }
        //通过当前经纬度更新当前定位状态，将定位显示屏幕中央
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.setMapStatus(msu);
    }

    /**
     * 回到我的当前定位
     */
    private void returnToCenter(){
        //获取到当前经纬度
        LatLng l = new LatLng(mLatitude,mLongtitude);
        //通过当前经纬度更新当前定位状态
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(l);
        baiduMap.animateMapStatus(msu);
    }

    /**
     * 2.2、实例化BDLocationListener定位接口，实现定位成功后返回监听
     */
    private class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //当前位置数据转化为MyLocationData类型
            MyLocationData data = new MyLocationData.Builder()
                    .direction(mCurrentX)//3.2、不断更新当前方向
                    .accuracy(bdLocation.getRadius())
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude())
                    .build();

            //将当前转化的定位数据设置到Map中
            baiduMap.setMyLocationData(data);
            //3.2、设置自定义图标 //4.2、设置地图模式
            MyLocationConfiguration config = new MyLocationConfiguration(mLocationMode,
                    true,mIconLocation);
            baiduMap.setMyLocationConfigeration(config);

            //实时获取当前定位坐标
            mLatitude = bdLocation.getLatitude();
            mLongtitude = bdLocation.getLongitude();

            if(isFirst){
                //获取到当前经纬度
                LatLng l = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                //通过当前经纬度更新当前定位状态
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(l);
                baiduMap.animateMapStatus(msu);
                isFirst = false;
                Toast.showToast(TestBaiduMap.this,bdLocation.getAddrStr());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开启页面时开启定位
        baiduMap.setMyLocationEnabled(true);
        if(!mLocationClient.isStarted())
            mLocationClient.start();
        //开启方向传感器
        myOrientationListener.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止页面时停止定位
        baiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        //停止方向传感器
        myOrientationListener.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
    }
}
