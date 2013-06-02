package com.achal.compostella;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Class qui définit la vue CompostelleActivity. 
 * This class est accessible depuis OutilsActivity .
 * L'utilisateur peut accéder à ListStepActivity and NewStepActivity via les boutons
 * @author Aurore
 *
 */
public class CompostelleActivity extends Activity{
	
	
	private Button bNewStep, bListStep;
	private TextView tvTitre;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_compostelle_activity);
		
		bNewStep = (Button) findViewById(R.id.bCompostelleNewStep);
		bListStep = (Button) findViewById(R.id.bCompostelleListStep);
		tvTitre = (TextView) findViewById(R.id.tvTitreCompostelleActivity);
		
		
		Typeface font2 = Typeface.createFromAsset(getAssets(), "font2.ttf");
		tvTitre.setTypeface(font2);
		bNewStep.setTypeface(font2);
		bListStep.setTypeface(font2);
		
		bNewStep.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(CompostelleActivity.this, CompostelleNewStepActivity.class);
				Bundle b = new Bundle();
				b.putBoolean("isNewStep", true);
				intent.putExtras(b);
				startActivity(intent);
				finish();
				CompostelleActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
			}
		});
		bListStep.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(CompostelleActivity.this, CompostelleListStepActivity.class);
				startActivity(intent);
				finish();
				CompostelleActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
			}
		});
		
	}

	
    /**
     * methode permettant de gérer le bouton retour du mobile
     */
	  @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
		  if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(CompostelleActivity.this, OutilsActivity.class);
			startActivity(intent);
			finish();
			CompostelleActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
		  }return super.onKeyDown(keyCode, event);
		}
		

}
