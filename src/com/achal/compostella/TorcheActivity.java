package com.achal.compostella;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.preference.ListPreference;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Class qui définit la vue Torche. Cette vue est appelée depuis l'activité OutilsActivity. 
 * @author Aurore
 *
 */
public class TorcheActivity extends Activity{

	private Camera camera = null ;
	private ImageButton ibScreenLight, ibFlash;
	private TextView tvTitre, tvTexte;
	private float brightness;
	private Boolean cameraIsCompatible= false, screenBright=false, flashBright=false; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_torche_activity);

		  Log.i("TorcheActivity", "onCreate() ");
		ibScreenLight = (ImageButton) findViewById(R.id.ibScreenLight);
		ibFlash = (ImageButton) findViewById(R.id.ibFlash);
		tvTexte = (TextView) findViewById(R.id.tvTexteTorcheActivity);
		tvTitre = (TextView) findViewById(R.id.tvTitreTorcheActivity);
		
		Typeface font =  Typeface.createFromAsset(this.getAssets(), "font2.ttf");
		tvTitre.setTypeface(font);
		tvTexte.setTypeface(font);
		
		final WindowManager.LayoutParams layoutParam = getWindow().getAttributes();
		brightness =layoutParam.screenBrightness;
		
		ibScreenLight.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				if (screenBright) {//revenir a la normal
					layoutParam.screenBrightness = brightness;
					getWindow().setAttributes(layoutParam);
					ibScreenLight.setImageResource(R.drawable.screen_on1);
					screenBright=false;
				}else{//lumiere à mettre au max
					layoutParam.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL; 
					getWindow().setAttributes(layoutParam);
					ibScreenLight.setImageResource(R.drawable.screen_on3);
					screenBright=true;
				}				
			}
		});


		cameraIsCompatible = checkCameraTorchCompatibility(getApplicationContext());
		if(cameraIsCompatible){
			ibFlash.setImageResource(R.drawable.flash_off);		
			tvTexte.setText(getResources().getString(R.string.TexteTorcheCompatible));
		}else{
			ibFlash.setImageResource(R.drawable.flash_out);		
			tvTexte.setText(getResources().getString(R.string.TexteTorcheNotCompatible));
		}
		
		
		ibFlash.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				if(cameraIsCompatible){//activer le flash					
					Parameters paramCamera = camera.getParameters();	
					if(flashBright){//light on - need to switch off
						flashBright=false;
						ibFlash.setImageResource(R.drawable.flash_off);
						paramCamera.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
						camera.setParameters(paramCamera);
						camera.startPreview();
					}else{//light off - need to switch on
						try {
							ibFlash.setImageResource(R.drawable.flash_on3);
							paramCamera.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
							camera.setParameters(paramCamera);
							camera.startPreview();
							flashBright=true;
						} catch (Exception e) {	e.printStackTrace();}
					}
					
				}
//					else{//afficher que le telephone n'est pas compatible
//					Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.TexteTorche), Toast.LENGTH_SHORT); 
//					toast.show();
//				}
			}
		});
	}
	
	/**
	 *   Check if this device has a camera and Torch mode
	 * @param context
	 * @return true device get Torch mode, false if not
	 */
	private boolean checkCameraTorchCompatibility(Context context) {
	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
	        // this device has a camera
	    	camera = Camera.open();
			Parameters paramCamera = camera.getParameters();
			List<String> listParam = paramCamera.getSupportedFlashModes();
			Log.i("flahsmode", "flash : "+listParam);
			
			for(int i=0; i<listParam.size();i++){
				if(listParam.get(i).equals(Camera.Parameters.FLASH_MODE_TORCH)){
					return true;
				}
			}
	        camera.release();
			return false;	        
	    } else {       // no camera on this device
	    	camera.release();
	        return false;
	    }
	}

	@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
		  if (keyCode == KeyEvent.KEYCODE_BACK) {	
			  if(cameraIsCompatible){
			  	camera.stopPreview();
				camera.release();
			}
		  	Intent intent = new Intent(TorcheActivity.this, OutilsActivity.class);
			startActivity(intent);
			finish();
			TorcheActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
		  }return super.onKeyDown(keyCode, event);
		}	
}
