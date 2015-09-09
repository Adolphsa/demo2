package com.dxytech.baidu;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.dxytech.demo2.R;

/**
 * Created by Administrator on 2015/9/2.
 */
public class Search_navigation extends bt_navigation implements OnGetPoiSearchResultListener,OnGetSuggestionResultListener {

   //返回
    private Button btn_clickback;

    private PoiSearch mPoiSearch;
    private SuggestionSearch mSuggestionSearch;

    //自动完成文本框
    private AutoCompleteTextView keyWorldView;
    private ArrayAdapter<String> sugAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sreach_navigation);

        btn_clickback = (Button)findViewById(R.id.btn_clickback);
        btn_clickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);

        //MVC模型
        keyWorldView = (AutoCompleteTextView)findViewById(R.id.et_sreach);
        sugAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line);
        keyWorldView.setAdapter(sugAdapter);
        keyWorldView.setDropDownVerticalOffset(20);
        keyWorldView.setDropDownHorizontalOffset(-30);

        keyWorldView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 0){
                    return;
                }
                mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
                        .keyword(s.toString())
                        .city("深圳"));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * POI搜索结果
     * @param poiResult
     */
    @Override
    public void onGetPoiResult(PoiResult poiResult) {

    }

    /**
     * POI详情结果
     * @param poiDetailResult
     */
    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    /**
     * 建议搜索结果
     * @param suggestionResult
     */
    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {
        if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
            return;
        }
        sugAdapter.clear();
        for (SuggestionResult.SuggestionInfo info : suggestionResult.getAllSuggestions()) {
            if (info.key != null)
                sugAdapter.add(info.key);
        }
        sugAdapter.notifyDataSetChanged();
    }
}
