package com.achal.compostella;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Class qui affiche la vue PelerinageActivity. Elle fait appel à la vue PageActivity pour l'affichage des vues filles
 * histoire, les chemins, les mots clés. 
 * En cliquant sur le bouton back, l'application lance la vue CompostelleMenu
 * @author Aurore
 *
 */
public class PelerinageActivity extends Activity{

	private Button bHistoire, bLesChemins, bMotsCles;
	private TextView tvTitreView;
	
	private static final int Code_Erreur =0;
	private static final int Code_Histoire =6;
	private static final int Code_Chemins =7;	
	private static final int Code_MotsCles =8;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_pelerinage_activity);
		
		
			tvTitreView = (TextView) findViewById(R.id.textViewTitrePelerinageActivity);
			bHistoire= (Button) findViewById(R.id.buttonHistoire);
	        bLesChemins = (Button) findViewById(R.id.buttonLesChemins);
	        bMotsCles = (Button) findViewById(R.id.buttonMotsCles);
	        
	        //Modification de la police
	        Typeface font = Typeface.createFromAsset(getAssets(), "font2.ttf");
	        tvTitreView.setTypeface(font);
	        bHistoire.setTypeface(font);
	        bLesChemins.setTypeface(font);
	        bMotsCles.setTypeface(font);
	        
	        
	        
	        bHistoire.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(PelerinageActivity.this, PageActivity.class);
					Bundle b= new Bundle();
					b.putInt("button",Code_Histoire );
					intent.putExtras(b);
					startActivity(intent);
					finish();
					PelerinageActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
					
				}
			});


	         bLesChemins.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(PelerinageActivity.this, PageActivity.class);
					Bundle b= new Bundle();
					b.putInt("button",Code_Chemins );
					intent.putExtras(b);
					startActivity(intent);
					finish();
					PelerinageActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
				}
			});
	        
	        bMotsCles.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(PelerinageActivity.this, PageActivity.class);
					Bundle b= new Bundle();
					b.putInt("button",Code_MotsCles );
					intent.putExtras(b);
					startActivity(intent);
					finish();
					PelerinageActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
				}
			});
	}

	
	
	  
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	 if (keyCode == KeyEvent.KEYCODE_BACK) {
    	Intent intent = new Intent(PelerinageActivity.this,CompostelaMenuActivity.class);
		startActivity(intent);
		finish();
		PelerinageActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
    	 }return super.onKeyDown(keyCode, event);
	}

}
