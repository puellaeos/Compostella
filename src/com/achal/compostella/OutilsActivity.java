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
 * Class utilisée pour afficher l'ensemble des outils de l'application.
 * L'utilisateur peut accéder aux vues via les boutons : Dictionnaire, Compostelle, et Torche.
 * En cliquant sur le bouton retour, l'application lance la vue CompostelleMenu
 * @author Aurore
 *
 */
public class OutilsActivity extends Activity {
	
	
	private Button bTorche, bBoussole, bDictionnaire, bStepRoad ;
	private TextView tvTitreView;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_outils_activity);
         
         
        tvTitreView = (TextView) findViewById(R.id.tvTitreOutilsActivity);
        bStepRoad = (Button) findViewById(R.id.buttonCompostelle);
        bDictionnaire = (Button) findViewById(R.id.buttonDictionnaire);
        bTorche = (Button) findViewById(R.id.buttonTorche);
        
        Typeface font = Typeface.createFromAsset(getAssets(), "font2.ttf");
        tvTitreView.setTypeface(font);
        bStepRoad.setTypeface(font);
        bDictionnaire.setTypeface(font);
        bTorche.setTypeface(font);
        
        
        bTorche.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(OutilsActivity.this, TorcheActivity.class);
				startActivity(intent);
				finish();
				OutilsActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
				
				
			}
		});
         bStepRoad.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(OutilsActivity.this, CompostelleActivity.class);
				startActivity(intent);
				finish();
				OutilsActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
			}
		});
        
   
        
        bDictionnaire.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(OutilsActivity.this, DictionnaireActivity.class);
				startActivity(intent);
				finish();
				OutilsActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
			}
		});
    }
    
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	 if (keyCode == KeyEvent.KEYCODE_BACK) {
    		 Intent intent = new Intent(OutilsActivity.this,CompostelaMenuActivity.class);
	    	startActivity(intent);
			finish();
			OutilsActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
		}return super.onKeyDown(keyCode, event);
	}
	
    
    
}