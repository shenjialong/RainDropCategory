package com.xiaoyudi.raindrop ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.xiaoyudi.customadapter.GridViewDetailAdapter;
import com.xiaoyudi.customview.NavigationBar;
import com.xiaoyudi.util.GridViewSettings;
import com.xiaoyudi.util.SoundPoolEngine;
import com.xiaoyudi.util.VibratorEngine;

public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.style1);
		soundPoolEngine = SoundPoolEngine.getInstance(this);
		vibratorEngine = VibratorEngine.getInstance(this);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_detail, menu);
        return true;
    }
    

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
	        int x=(int) event.getX();
	        int y=(int) event.getY();
	        Log.i("sjl", "点击的坐标值， X:"+x+"Y:"+y);

	        return super.onTouchEvent(event);
	}
	SoundPoolEngine soundPoolEngine;
	VibratorEngine vibratorEngine;
	ArrayList<Integer> mSoundIdList;
	LinearLayout ll;
	NavigationBar navigationBar;
	GridView gridview_low;
	GridView gridview_up;
	GridViewSettings gvsettings;
	int []images;
	
//	int[][]textsArray={{R.array.daily_life_diet},
//			{R.array.daily_life_self_care},
//			
//			{R.array.daily_life_physical_condition},
//			{R.array.daily_life_going_out},
//			
//			{R.array.daily_life_dress},
//			{R.array.daily_life_relaxation}
//			};
	
	int[][]textsArray={
			{R.array.daily_life_diet},
			{R.array.daily_life_self_care},
			{R.array.daily_life_diet},
			{R.array.express_love},
			{R.array.daily_life_dress},
			{R.array.daily_life_relaxation},
			{R.array.daily_life_physical_condition},
			{R.array.daily_life_going_out}
			};
	
	int[] []imagesArray={
			{
//				我想喝水  无    属于常用词条
			},{
//				我想吃零食   无  属于常用词条
			},{
//				饮食
				R.drawable.p1_11, R.drawable.p1_12, 
				 R.drawable.p1_13, R.drawable.p1_14, 
				 R.drawable.p1_15, R.drawable.p1_16, 
				 R.drawable.p1_17, R.drawable.p1_18
			},{
//				情感
				R.drawable.p2_31, 
				 R.drawable.p2_32, 
				 R.drawable.p2_33, R.drawable.p2_34, 
				 R.drawable.p2_35
			},{
				R.drawable.p1_51, 
				 R.drawable.p1_52, 
				 R.drawable.p1_53, R.drawable.p1_54, 
				 R.drawable.p1_55, R.drawable.p1_56
			},{
				R.drawable.p1_61, 
				 R.drawable.p1_62, 
				 R.drawable.p1_63, R.drawable.p1_64, 
				 R.drawable.p1_65, R.drawable.p1_66
			},{
				R.drawable.p1_31, 
				 R.drawable.p1_32, 
				 R.drawable.p1_33, R.drawable.p1_34, 
				 R.drawable.p1_35, R.drawable.p1_36
			},{
				R.drawable.p1_41, 
				 R.drawable.p1_42, 
				 R.drawable.p1_43, R.drawable.p1_44, 
				 R.drawable.p1_45, R.drawable.p1_46, R.drawable.p1_47, R.drawable.p1_48, R.drawable.p1_49
			}
	};
	String [] texts;
	
	int [][] soundIdArray = { 
			{},{},
			{R.raw.s1_11, R.raw.s1_12, R.raw.s1_13, 
				R.raw.s1_14, R.raw.s1_15, R.raw.s1_16,
				R.raw.s1_17, R.raw.s1_18 },
				
				{R.raw.s2_31, R.raw.s2_32, R.raw.s2_33, 
				R.raw.s2_34, 
				R.raw.s2_35},
			
			{R.raw.s1_51, R.raw.s1_52, R.raw.s1_53, 
				R.raw.s1_54, R.raw.s1_55, R.raw.s1_56 },
				
				{R.raw.s1_61, R.raw.s1_62, R.raw.s1_63, 
				R.raw.s1_64, R.raw.s1_65, R.raw.s1_66},
				
				{R.raw.s1_31, R.raw.s1_32, R.raw.s1_33, 
					R.raw.s1_34, R.raw.s1_35, R.raw.s1_36},
					
					{R.raw.s1_42, R.raw.s1_43, 
						R.raw.s1_44, R.raw.s1_45, R.raw.s1_46,
						R.raw.s1_47, R.raw.s1_48, R.raw.s1_49}
			
	};
	@Override
	protected void onPause() {
		soundPoolEngine.release();
//		MobclickAgent.onPause(this);
		super.onPause();
	}
	public void init(){
		
		gridview_low=(GridView)findViewById(R.id.gridView_style1_low);
		gridview_up=(GridView)findViewById(R.id.gridView_style1_up);
		navigationBar=(NavigationBar)findViewById(R.id.navigationBar_style1);
		
		initNavigationBar();
		initGridView();
		
	}
	
	public void initSettings(){
		SharedPreferences layout_settings = getSharedPreferences("layout_settings", 0);
		int rows=layout_settings.getInt("rows", 4);
		int columns=layout_settings.getInt("columns", 4);
		int layers=layout_settings.getInt("layers", 1);
		gvsettings=new  GridViewSettings(columns,rows,layers);
	}
	
	
	public void initGridView(){
		
		String [] from ={"image","text"};
		int [] to ={R.id.imageView_item,R.id.textView_item};
		initSettings();
		if(gvsettings.getLayers()==2){
			gridview_up.setVisibility(View.VISIBLE);	
			gridview_up.setNumColumns(gvsettings.columns_layer_up);
			List <Map<String,Object>> dataSourceForLayerUp=getDataSourceForLayerUp();
			
			GridViewDetailAdapter adapterForLayer2=new GridViewDetailAdapter(DetailActivity.this, dataSourceForLayerUp, 
					R.layout.gridviewitem, from, to,gvsettings,this.gridview_up,soundPoolEngine,mSoundIdList,vibratorEngine);
			
			gridview_up.setAdapter(adapterForLayer2);
			
			List <Map<String,Object>> dataSource=getDataSourceForLayer1(2);
			GridViewDetailAdapter adapter=new GridViewDetailAdapter(DetailActivity.this, dataSource, R.layout.gridviewitem, 
					from, to,gvsettings,this.gridview_low,soundPoolEngine,mSoundIdList,vibratorEngine);
			gridview_low.setNumColumns(gvsettings.columns_layer_down);
			gridview_low.setAdapter(adapter);
			
		}else{
			List <Map<String,Object>> dataSource=getDataSourceForLayerUp();
			
			Log.i("sjl", "dataSource is num:"+dataSource.size());
			GridViewDetailAdapter adapter=new GridViewDetailAdapter(DetailActivity.this, dataSource, R.layout.gridviewitem, 
					from, to,gvsettings,this.gridview_low,soundPoolEngine,mSoundIdList,vibratorEngine);
			gridview_low.setNumColumns(gvsettings.columns);
			gridview_low.setAdapter(adapter);
			gridview_up.setVisibility(View.GONE);
		}
	}
	
	
	public List <Map<String,Object>> getDataSourceForLayerUp(){
		List <Map<String,Object>> datasource=new ArrayList<Map<String,Object>>();
		int totalItems=gvsettings.rows_layer_up*gvsettings.columns_layer_up;
		Map<String,Object> item;
		for(int i=0;i<totalItems;i++){
			if(i<images.length){
				item=new HashMap<String, Object>();
				item.put("image", images[i]);
				item.put("text", texts[i]);
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
				item.put("text", texts[i+begin]);
				datasource.add(item);
			}
		}
		return datasource;
	}
//	想延迟在这个方法中加载声音资源  缺
//	@Override
//	protected void onResume() {
//		int [] soundIds=soundIdArray[categoryIndex];
//		mSoundIdList = soundPoolEngine.loadRes(soundIds);
//		super.onResume();
//	}
//	int categoryIndex;
	public void initNavigationBar(){
		
		Intent intent=getIntent();
		
		String category=intent.getStringExtra("category");
		int categoryIndex=intent.getIntExtra("categoryIndex", 0);
		images=imagesArray[categoryIndex];
		
		texts=getResources().getStringArray(textsArray[categoryIndex][0]);
		int [] soundIds=soundIdArray[categoryIndex];
				mSoundIdList = soundPoolEngine.loadRes(soundIds);
				
//				this.categoryIndex=categoryIndex;
	
				navigationBar.setTvTitle(category);
		
		navigationBar.setBtnRightVisble(false);
		navigationBar.setBtnLeftClickListener(new  OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
			}
		});
		navigationBar.setBtnRightClickListener(new  OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder=new  AlertDialog.Builder(DetailActivity.this);
				builder.setTitle("选择切换的样式").setSingleChoiceItems(new String[]{"4*4布局","3*4布局","3*3布局","混合布局"}, 0
						, new AlertDialog.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
//								保存用户选择的布局设置参数
								SharedPreferences layout_settings = getSharedPreferences("layout_settings", 0);
								int rows=4;
								int columns=4;
								int layers=1;
								
								switch(which){
								case 0:
									rows=4;
									columns=4;
									break;
								case 1:
									rows=3;
									columns=4;
									break;
								case 2:
									rows=3;
									columns=3;
									break;
								case 3:
									layers=2;
									layout_settings.edit().putInt("layers", layers).commit();
									break;
								}
								
								Editor editor=layout_settings.edit();
								editor.putInt("columns", columns);
								editor.putInt("layers", layers);
								editor.putInt("rows", rows).commit();
								
								initGridView();
								dialog.dismiss();
								
							}
						}).show();
						
			}
		});
		
	}
    
}
