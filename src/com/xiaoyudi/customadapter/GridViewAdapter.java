package com.xiaoyudi.customadapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoyudi.raindrop.DetailActivity;
import com.xiaoyudi.raindrop.R;
import com.xiaoyudi.util.GridViewSettings;
import com.xiaoyudi.util.SoundPoolEngine;
import com.xiaoyudi.util.VibratorEngine;

public class GridViewAdapter extends BaseAdapter {
	
	
	Context  context;
	List<Map<String,Object>> dataSource;
	String [] from;
	int[] to; 
	LayoutInflater layoutInflater;
	int resource;
	GridViewSettings gvsettings;
	int itemHeigh;
	GridView gridview;
	ArrayList<Integer> mSoundIdList;
	SoundPoolEngine soundPoolEngine;
	VibratorEngine vibratorEngine;
//	���Խ׶��ȷŶ�ʮ��
    
	public void initItemHeigh(){
		DisplayMetrics dm = new DisplayMetrics();   
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		Log.i("sjl", "��Ļ�Ŀ�ȣ�"+dm.widthPixels);
		Log.i("sjl", "��Ļ�ĸ߶ȣ�"+dm.heightPixels);
		Log.i("sjl", "��Ļ��xdpi��"+dm.xdpi);
		Log.i("sjl", "��Ļ��ydpi��"+dm.ydpi);
		int heigh=dm.heightPixels;
		int rows=gvsettings.rows;
		this.itemHeigh=((heigh*8)/(rows*9));
	}
	
	public GridViewAdapter(Context context,List<Map <String,Object>> dataSource,int resource,String[] from,
			int [] to,GridViewSettings gvsettings,GridView gridview,SoundPoolEngine soundPoolEngine,
			ArrayList<Integer> mSoundIdList,VibratorEngine vibratorEngine){
		this.vibratorEngine=vibratorEngine;
		this.mSoundIdList=mSoundIdList;
		this.soundPoolEngine=soundPoolEngine;
		this.context=context;
		this.dataSource=dataSource;
		this.from=from;
		this.to=to; 
		this.layoutInflater=LayoutInflater.from(context);
		this.resource=resource;
		this.gvsettings=gvsettings;
		this.gridview=gridview;
		initItemHeigh();
	}
	@Override
	public int getCount() {
		return dataSource.size();
	}

	@Override
	public Object getItem(int position) {
		return dataSource.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position,  View convertView, ViewGroup parent) {
		
		if(convertView==null){
			convertView=layoutInflater.inflate(this.resource, null);
			AbsListView.LayoutParams param = new AbsListView.LayoutParams(
	                android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
	                this.itemHeigh);
	        convertView.setLayoutParams(param);
	        
	        convertView.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					
					float x=event.getX();
					float y=event.getY();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
					Log.i("sjl", "convertView:"+df.format(new Date()).toString()+"x:"+x);
					Log.i("sjl", "convertView:"+df.format(new Date()).toString()+"y:"+y);
//					System.out.println(df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
//					SharedPreferences layout_settings = context.getSharedPreferences("testWidthAndHeight", 0);
//					Editor editor=layout_settings.edit();
//					editor.putFloat(df.format(new Date()).toString()+"x", x);
//					editor.putFloat(df.format(new Date()).toString()+"y", y).commit();
					return false;
				}
			});
	        
		}
		final ImageView imageview=(ImageView)convertView.findViewById(to[0]);
		TextView textview=(TextView)convertView.findViewById(to[1]);
		
		if(position<dataSource.size()){
			imageview.setImageResource((Integer)dataSource.get(position).get(from[0]));
			textview.setText((String)dataSource.get(position).get(from[1]));
			
			imageview.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					
					float x=event.getX();
					float y=event.getY();
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
					Log.i("sjl", "imageview:"+df.format(new Date()).toString()+"x:"+x);
					Log.i("sjl", "imageview:"+df.format(new Date()).toString()+"y:"+y);
//					float x=event.getX();
//					float y=event.getY();
//					
//					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
//					System.out.println(df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��  testWidthAndHeight
//					
//					SharedPreferences layout_settings = context.getSharedPreferences("testWidthAndHeight", 0);
//					Editor editor=layout_settings.edit();
//					editor.putFloat(df.format(new Date()).toString()+"x", x);
//					editor.putFloat(df.format(new Date()).toString()+"y", y).commit();
					
//					int height=imageview.getHeight();
//					int width=imageview.getWidth();
//					Log.i("sjl", "ͼƬposition:"+position);
//					Log.i("sjl", "imageview height:"+height);
//					Log.i("sjl", "imageview width:"+width);
					
					return false;
				}
			});
			
			
			
				imageview.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
//						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
//						SharedPreferences layout_settings = context.getSharedPreferences("testWidthAndHeight", 0);
//						Editor editor=layout_settings.edit();
//						editor.putFloat(df.format(new Date()).toString()+"���ͼƬλ��", position).commit();
						Log.i("sjl", "��ҳͼƬ :"+position);
						if(position<2){
							if(soundPoolEngine==null){
								Log.i("sjl", "soundPoolEngine is null");
							}
							if(mSoundIdList==null){
								Log.i("sjl", "mSoundIdList is null");
							}
							if(mSoundIdList.get(position)==null){
								Log.i("sjl", "mSoundIdList.get(position) is null");
							}
							
							soundPoolEngine.play(mSoundIdList.get(position));
							vibratorEngine.vibrate();
						}else{
							Intent intent =new  Intent();
							intent.setClass(context, DetailActivity.class);
							intent.putExtra("category", (String)dataSource.get(position).get(from[1]));
							intent.putExtra("categoryIndex", position);
							context.startActivity(intent);
							((Activity)context).overridePendingTransition(R.anim.fadein, R.anim.fadeout); 
						}
					}
				});
			
		}
		return convertView;
		
	}


}
