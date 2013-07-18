package com.xiaoyudi.util;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

public class VibratorEngine {
	
	private static VibratorEngine vibratorEngine;
	
	private Vibrator vibrator;
	
	private VibratorEngine(Context context) {
		vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
	}
	
	public static VibratorEngine getInstance(Context context) {
		synchronized(VibratorEngine.class) {
			if(vibratorEngine == null) {
				synchronized(VibratorEngine.class) {
					vibratorEngine = new VibratorEngine(context);
				}
			}
			return vibratorEngine;
		}
	}
	
	public void vibrate() {
		vibrator.vibrate(new long[]{100,5,5,10},-1); 
	}
	
	

}
