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
 * Class qui affiche la vue PreparationActivity. Elle fait appel à la vue PageActivity pour l'affichage des vues filles
 * Papiers, equipement, partir, se nourrir, dormir. 
 * En cliquant sur le bouton back, l'application lance la vue CompostelleMenu
 * @author Aurore
 *
 */
public class PreparationActivity extends Activity{
	
	private Button buttonPapiers, buttonEquipement,  buttonPartir, buttonSeNourrir, buttonDormir;
	private TextView tvTitre;
	
	
	private static final int Code_Erreur =0;
	
	private static final int Code_Papiers =1;
	private static final int Code_Partir =2;	
	private static final int Code_Equipement =3;
	private static final int Code_SeNourrir =4;
	private static final int Code_Dormir =5;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_preparation_activity);
		
		buttonPapiers = (Button) findViewById(R.id.ButtonPapiers);
		buttonEquipement  = (Button) findViewById(R.id.ButtonEquipement);
		buttonPartir = (Button) findViewById(R.id.ButtonPartir);
		buttonSeNourrir = (Button) findViewById(R.id.buttonRestaurant);
		buttonDormir = (Button) findViewById(R.id.ButtonLogement);
		tvTitre = (TextView) findViewById(R.id.tvTitrePreparationActivity);
		
        Typeface font2 = Typeface.createFromAsset(this.getAssets(), "font2.ttf");
        buttonPapiers.setTypeface(font2);
		buttonEquipement.setTypeface(font2);
		buttonPartir.setTypeface(font2);
		buttonSeNourrir.setTypeface(font2);
		buttonDormir.setTypeface(font2);
		tvTitre.setTypeface(font2);
		
		buttonPapiers.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(PreparationActivity.this, PageActivity.class);
				Bundle b = new Bundle();
				b.putInt("button",Code_Papiers);
				intent.putExtras(b);
				startActivity(intent);
				finish();				
			}
		});
		buttonEquipement.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(PreparationActivity.this, PageActivity.class);
				Bundle b = new Bundle();
				b.putInt("button", Code_Equipement);
				intent.putExtras(b);
				startActivity(intent);
				finish();				
			}
		});
		buttonPartir.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(PreparationActivity.this, PageActivity.class);
				Bundle b = new Bundle();
				b.putInt("button", Code_Partir);
				intent.putExtras(b);
				startActivity(intent);
				finish();				
			}
		});
		buttonSeNourrir.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(PreparationActivity.this, PageActivity.class);
				Bundle b = new Bundle();
				b.putInt("button", Code_SeNourrir);
				intent.putExtras(b);
				startActivity(intent);
				finish();				
			}
		});
		buttonDormir.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(PreparationActivity.this, PageActivity.class);
				Bundle b = new Bundle();
				b.putInt("button", Code_Dormir);
				intent.putExtras(b);
				startActivity(intent);
				finish();				
			}
		});
		
		
		
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if (keyCode == KeyEvent.KEYCODE_BACK) {
		Intent intent = new Intent(PreparationActivity.this, CompostelaMenuActivity.class);
		startActivity(intent);
		finish();
 		PreparationActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
		 }return super.onKeyDown(keyCode, event);
	}
	
	
	
}
