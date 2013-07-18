package com.xiaoyudi.raindrop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.GridView;

import com.xiaoyudi.customadapter.GridViewAdapter;
import com.xiaoyudi.customview.NavigationBar;
import com.xiaoyudi.util.DeviceUtil;
import com.xiaoyudi.util.GridViewSettings;
import com.xiaoyudi.util.SoundPoolEngine;
import com.xiaoyudi.util.VibratorEngine;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DeviceUtil.init(this);
		soundPoolEngine = SoundPoolEngine.getInstance(this);
		vibratorEngine = VibratorEngine.getInstance(this);
		setContentView(R.layout.style1);
		init();
	}
	@Override
	protected void onPause() {
		soundPoolEngine.release();
//		MobclickAgent.onPause(this);
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		int [] soundIds = { R.raw.s1_12,
				R.raw.s1_17
		};
//		int [] soundIds = {R.raw.s1_11, R.raw.s1_12, R.raw.s1_13, 
//				R.raw.s1_14, R.raw.s1_15, R.raw.s1_16,
//				R.raw.s1_17, R.raw.s1_18 ,
//				
//				R.raw.s1_21, R.raw.s1_22, R.raw.s1_23, 
//				R.raw.s1_24, R.raw.s1_25, R.raw.s1_26,
//				R.raw.s1_27, R.raw.s1_28, R.raw.s1_29 ,
//				
//				R.raw.s1_31, R.raw.s1_32, R.raw.s1_33, 
//				
//		};
//		这需要重新加载一遍声音文件   因为DetailActivity页面返回的时候 把相关的资源给release掉了
		mSoundIdList = soundPoolEngine.loadRes(soundIds);
		
//		initGridView();
		super.onResume();
	}
	
	NavigationBar navigationBar;
	GridView gridview_low;
	GridView gridview_up;
	GridViewSettings gvsettings;
	SoundPoolEngine soundPoolEngine;
	VibratorEngine vibratorEngine;
	ArrayList<Integer> mSoundIdList;
	int[] images={R.drawable.p1_12, R.drawable.p1_17, 
			 R.drawable.p1_1, R.drawable.p2_3, 
			 R.drawable.p1_5, R.drawable.p1_6, 
			 R.drawable.p1_3,R.drawable.p1_4
			};
	
	int[] commons={R.drawable.p1_11, R.drawable.p1_12 };
	String [] categories;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void init(){
		categories=getResources().getStringArray(R.array.daily_life);
		gridview_low=(GridView)findViewById(R.id.gridView_style1_low);
		gridview_up=(GridView)findViewById(R.id.gridView_style1_up);
		navigationBar=(NavigationBar)findViewById(R.id.navigationBar_style1);
		int [] soundIds = { R.raw.s1_12,
				R.raw.s1_17
		};
//		int [] soundIds = {R.raw.s1_11, R.raw.s1_12, R.raw.s1_13, 
//				R.raw.s1_14, R.raw.s1_15, R.raw.s1_16,
//				R.raw.s1_17, R.raw.s1_18 ,
//				
//				R.raw.s1_21, R.raw.s1_22, R.raw.s1_23, 
//				R.raw.s1_24, R.raw.s1_25, R.raw.s1_26,
//				R.raw.s1_27, R.raw.s1_28, R.raw.s1_29 ,
//				
//				R.raw.s1_31, R.raw.s1_32, R.raw.s1_33, 
//				
//		};
		mSoundIdList = soundPoolEngine.loadRes(soundIds);
		
		initGridView();
		initNavigationBar();
	}
	
	public void initSettings(){
		SharedPreferences layout_settings = getSharedPreferences("layout_settings", 0);
		int rows=layout_settings.getInt("rows", 4);
		int columns=layout_settings.getInt("columns", 4);
		int layers=layout_settings.getInt("layers", 1);
		
		Map<String,?> map=layout_settings.getAll();
		Set<String> keys=map.keySet();
		Iterator<String> it=keys.iterator();
		while(it.hasNext()){
			Log.i("sjl", "keys:"+it.next());
		}
		
		gvsettings=new  GridViewSettings(columns,rows,layers);
	}
	public void initGridView(){
		String [] from ={"image","text"};
		int [] to ={R.id.imageView_item,R.id.textView_item};
		initSettings();
		if(gvsettings.getLayers()==2){
//			不可见  且不占用布局空间 
			gridview_up.setVisibility(View.VISIBLE);	
			gridview_up.setNumColumns(gvsettings.columns_layer_up);
			List <Map<String,Object>> dataSourceForLayerUp=getDataSourceForLayerUp();
			GridViewAdapter adapterForLayer2=new GridViewAdapter(MainActivity.this, dataSourceForLayerUp, 
					R.layout.gridviewitem, from, to,gvsettings,this.gridview_up,soundPoolEngine,mSoundIdList,vibratorEngine);
			
			gridview_up.setAdapter(adapterForLayer2);
			
			List <Map<String,Object>> dataSource=getDataSourceForLayerDown();
			GridViewAdapter adapter=new GridViewAdapter(MainActivity.this, dataSource, R.layout.gridviewitem, 
					from, to,gvsettings,this.gridview_low,soundPoolEngine,mSoundIdList,vibratorEngine);
			gridview_low.setNumColumns(gvsettings.columns_layer_down);
			gridview_low.setAdapter(adapter);
			
		}else{
			List <Map<String,Object>> dataSource=getDataSourceForLayer1(1);
			GridViewAdapter adapter=new GridViewAdapter(MainActivity.this, dataSource, R.layout.gridviewitem, 
					from, to,gvsettings,this.gridview_low,soundPoolEngine,mSoundIdList,vibratorEngine);
			gridview_low.setNumColumns(gvsettings.columns);
			gridview_low.setAdapter(adapter);
			gridview_up.setVisibility(View.GONE);
		}
	}
	
	
	public List <Map<String,Object>> getDataSourceForLayerDown(){
		List <Map<String,Object>> datasource=new ArrayList<Map<String,Object>>();
		int totalItems=gvsettings.rows_layer_down*gvsettings.columns_layer_down;
		Map<String,Object> item;
		for(int i=0;i<totalItems;i++){
			if(i<commons.length){
				item=new HashMap<String, Object>();
				item.put("image", commons[i]);
				item.put("text", categories[i]);
				datasource.add(item);
			}
		}
		return datasource;
	}
	
	
	public List <Map<String,Object>> getDataSourceForLayerUp(){
		List <Map<String,Object>> datasource=new ArrayList<Map<String,Object>>();
		int totalItems=gvsettings.rows_layer_up*gvsettings.columns_layer_up;
		Map<String,Object> item;
		for(int i=0;i<totalItems;i++){
			if(i<images.length){
				item=new HashMap<String, Object>();
				item.put("image", images[i]);
				item.put("text", categories[i]);
				datasource.add(item);
			}
		}
		return datasource;
	}
	
	public List <Map<String,Object>> getDataSourceForLayer1(int type){
		List <Map<String,Object>> datasource=new ArrayList<Map<String,Object>>();
		int totalItems;
		
		int begin;
		
		if(type==1){
			begin=0;
			totalItems=gvsettings.rows*gvsettings.columns;
		}else{
			begin=gvsettings.rows_layer_up*gvsettings.columns_layer_up;
			totalItems=gvsettings.rows_layer_down*gvsettings.columns_layer_down;
		}
		
		Map<String,Object> item;
		for(int i=0;i<totalItems;i++){
				if(i<images.length){
					item=new HashMap<String, Object>();
					item.put("image", images[i+begin]);
					item.put("text", categories[i+begin]);
					datasource.add(item);
				}
		}
		return datasource;
	}
	
	public void initNavigationBar(){
		navigationBar.setTvTitle("小雨滴");
		navigationBar.setBtnLeftVisble(false);
		navigationBar.setBtnRightVisble(false);
		
	}

}
