package com.dxytech.baidu;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.dxytech.demo2.R;

/**
 * 一键导航
 * Created by Administrator on 2015/9/1.
 */
public class bt_navigation extends Activity {
    private final static String TGA = "bt_navigation";

    private Button btn_clickback; //返回
    private TextView tv_sreach; //搜索

    /**
     * 构造广播监听类，监听 SDK key 验证以及网络异常广播
     */
    public class SDKReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)){
                Log.d(TGA,"key 验证出错!");
                Toast.makeText(getApplicationContext(),"key 验证出错!",Toast.LENGTH_SHORT).show();
            }else if (action.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)){
                Log.d(TGA,"网络错误!");
                Toast.makeText(getApplicationContext(),"网络错误!",Toast.LENGTH_SHORT).show();
            }
        }
    }
    SDKReceiver mReceiver;

    //定位相关
    public MapView mMapView = null; //地图View
    public BaiduMap mBaiduMap; //地图实例
    public LocationClient mLocationClient; //客户端
    public MyLocationListener mMyLocationListener;

    //缩放控件
    private Button bt_zoomin;
    private Button bt_zoomout;

    //方向传感器
     MyOrientationListener mMyOrientationListener;
    public float mCurrentX;

    //是否是第一次定位
     volatile boolean isFristLocation = true;

    public double mCurrentLatitude;  //纬度
    public double mCurrentLongitude; //经度
    public float mCurrentAccuracy; //精度
    public LatLng ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.baidu_navigation);

        //返回
        btn_clickback = (Button)findViewById(R.id.btn_clickback);
        btn_clickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //搜索框
        tv_sreach = (TextView)findViewById(R.id.tv_sreach);
        tv_sreach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplication(), RoutePlanDemo.class);
                startActivity(intent);
            }
        });

        //缩放控件
        bt_zoomin = (Button)findViewById(R.id.zoomin);
        bt_zoomin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float zoomLevel = mBaiduMap.getMapStatus().zoom;

                Log.d("缩放等级+",Float.toString(zoomLevel));

                if(zoomLevel < mBaiduMap.getMaxZoomLevel()){
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn());
                    bt_zoomout.setEnabled(true);
                }else{
                    Toast.makeText(getApplication(), "已经放至最大！", Toast.LENGTH_SHORT).show();
                    bt_zoomin.setEnabled(false);
                }
            }
        });
        bt_zoomout = (Button)findViewById(R.id.zoomout);
        bt_zoomout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float zoomLevel = mBaiduMap.getMapStatus().zoom;
                Log.d("缩放等级-",Float.toString(zoomLevel));
                if(zoomLevel > mBaiduMap.getMinZoomLevel()){
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomOut());
                    bt_zoomin.setEnabled(true);
                }else{
                    bt_zoomout.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "已经缩至最小！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //注册SDKReceiver广播监听者
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mReceiver = new SDKReceiver();
        registerReceiver(mReceiver, iFilter);

        initView();
        initMyLocation();
        initOrientation();
    }
    public void initView(){
        isFristLocation = true;
        mMapView = (MapView)findViewById(R.id.mapView);
        mMapView.showZoomControls(false);
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);
        mBaiduMap.setMapStatus(msu);
    }
   public void initMyLocation(){
       //定位初始化
       mLocationClient = new LocationClient(getApplicationContext());
       mMyLocationListener = new MyLocationListener();
       //注册监听事件
       mLocationClient.registerLocationListener(mMyLocationListener);

       //设置定位相关的配置
       LocationClientOption option = new LocationClientOption();
       option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
       option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
       option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
       option.setIsNeedAddress(true);//返回的定位结果包含地址信息
       option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
       mLocationClient.setLocOption(option);
   }

    /**
     * 初始化传感器
     */
    public void initOrientation(){
        mMyOrientationListener = new MyOrientationListener(
                getApplicationContext());
        mMyOrientationListener
                .setOnOrientationListener(new MyOrientationListener.OnOrientationListener()
                {
                    @Override
                    public void onOrientationChanged(float x)
                    {
                        mCurrentX = (int) x;

                        // 构造定位数据
                        MyLocationData locData = new MyLocationData.Builder()
                                .accuracy(mCurrentAccuracy)
                                        // 此处设置开发者获取到的方向信息，顺时针0-360
                                .direction(mCurrentX)
                                .latitude(mCurrentLatitude)
                                .longitude(mCurrentLongitude).build();
                        // 设置定位数据
                        mBaiduMap.setMyLocationData(locData);

                        //设置标注
                        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true,null);
                        mBaiduMap.setMyLocationConfigeration(config);

                    }
                });
    }
    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null || mMapView == null){
                return;
            }

            //构造定位数据
            MyLocationData locationData = new MyLocationData.Builder()
                    .direction(mCurrentX)
                    .accuracy(bdLocation.getRadius())
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude())
                    .build();

            mBaiduMap.setMyLocationData(locationData);
            //记录当前的一些数据
            mCurrentAccuracy = bdLocation.getRadius();
            mCurrentLatitude = bdLocation.getLatitude();
            mCurrentLongitude = bdLocation.getLongitude();

            //设置标注
            MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true,null);
            mBaiduMap.setMyLocationConfigeration(config);
            //第一次定位时，将地图位置移到当前位置
            if(isFristLocation){
                isFristLocation = false;
                ll = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
                Toast.makeText(getApplicationContext(),bdLocation.getAddrStr(),Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        unregisterReceiver(mReceiver);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        //关闭图层定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        //关闭方向传感器
        mMyOrientationListener.stop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        //开启图层定位
        mBaiduMap.setMyLocationEnabled(true);
        if(!mLocationClient.isStarted()){
            mLocationClient.start();
        }
        //开启方向传感器
        mMyOrientationListener.start();
        super.onStart();
    }
}
