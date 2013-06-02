package com.achal.compostella;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Class qui définit la vue APropos, elle est accessible depuis le menu de l'activité CompostelleMenuActivity
 * @author Aurore
 *
 */
public class AProposActivity extends Activity{
	
	private TextView tvTitre, tvTexte ;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_apropos);
        
        tvTitre = (TextView) findViewById(R.id.tvTitreAProposActivity);
        tvTexte = (TextView) findViewById(R.id.tvTexteAProposActivity);
        
        Typeface font = Typeface.createFromAsset(getAssets(), "font2.ttf");
        tvTitre.setTypeface(font);
        tvTexte.setTypeface(font);
	}
	 /**
     * methode permettant de gérer le bouton retour du mobile
     */
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	 if (keyCode == KeyEvent.KEYCODE_BACK) {
    		Intent intent = new Intent(AProposActivity.this,CompostelaMenuActivity.class);
	    	startActivity(intent);
			finish();
			AProposActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
    	 }return super.onKeyDown(keyCode, event);
	}
	
	

}
