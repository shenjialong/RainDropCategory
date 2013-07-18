package com.xiaoyudi.raindrop;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.xiaoyudi.customview.NavigationBar;
import com.xiaoyudi.util.Contants;
import com.xiaoyudi.util.SystemExitBroadcastReceiver;


public class SplashActivity extends Activity {

	NavigationBar myNavigationbar;
	ListView questionTypeLV;
	SystemExitBroadcastReceiver exitReceiver;
	
	int [][] layoutParams={
			{2,2},{3,2},{4,2},{2,3},{2,4},{3,3},{3,4},{4,4}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		init();
	}
	
	public void init(){
		exitReceiver=new SystemExitBroadcastReceiver();
		initNavigationbar();
		initQuestionTypeListview();
	}
	
	public void initNavigationbar(){
		myNavigationbar=(NavigationBar)findViewById(R.id.helpNb);
		myNavigationbar.setBtnRightVisble(false);
		myNavigationbar.setBtnLeftVisble(false);
		myNavigationbar.setTvTitle("选择卡片布局");
	}
	
	@Override
	public void onResume(){
		super.onResume();
		IntentFilter filter=new IntentFilter();
		filter.addAction(Contants.EXIT);
		this.registerReceiver(exitReceiver, filter);
	}
	
	public void initQuestionTypeListview(){
//		String [] adapterSource=new String[]{"2*2布局","3*2布局","4*2布局","2*3布局","2*4布局","3*3布局","3*4布局","4*4布局"};
		String [] adapterSource=new String[]{"2*2布局","3*2布局","4*2布局"};
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,adapterSource);
		questionTypeLV=(ListView)findViewById(R.id.questionTypeLV);
		questionTypeLV.setAdapter(adapter);
		questionTypeLV.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int layoutType,
					long arg3) {
				SharedPreferences layout_settings = getSharedPreferences("layout_settings", 0);
				
				int rows=layoutParams[layoutType][0];
				int columns=layoutParams[layoutType][1];
//				本测试中  只涉及到了 单词条测试 所以layers固定为2 by sjl 2013 07 16
				int layers=1;
				
				Editor editor=layout_settings.edit();
				editor.putInt("columns", columns);
				editor.putInt("layers", layers);
				editor.putInt("rows", rows).commit();
				
				Intent intent =new Intent(SplashActivity.this, MainActivity.class);
				startActivity(intent);
//				SplashActivity.this.finish();
				
				overridePendingTransition(R.anim.fadein, R.anim.fadeout); 
			}
		});
	}
	

}
