package com.achal.compostella;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * Class qui définit listStepActivity. Cette vue montre la  liste des steps qui indiques la totale distance and et pour chaque step :
 * date, lieu de départ, lieu d'arrivée, distance.  Toutes les informations sont sauvegardées dans la base de données du telephone 
 * Quand l'utilisateur clique sur un item de la liste, app lance CompostelleStepActivity.
 * Quand l'utilisateur clique sur back button, app lance CompostelleActivity
 * @author Aurore
 *
 */
public class CompostelleListStepActivity extends Activity{
	
	private ListView lvListStep ;
	private TextView tvTitre, tvItemDate, tvItemEnd,tvItemDistance, tvTotalDistance;
	private ArrayList<Step> listStepBdd;
	private Step step;
	private CompostelleListStepAdapter listAdapter;
	
	private float distanceTotal=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_compostelle_liststep_activity);
		
		lvListStep = (ListView) findViewById(R.id.lvListStep);
		tvTitre = (TextView) findViewById(R.id.tvTitreCompostelleListActivity);
		tvTotalDistance = (TextView) findViewById(R.id.tvDistanceTotalCompostelleListActivity);
		
		Typeface font2 = Typeface.createFromAsset(getAssets(), "font2.ttf");
		tvTitre.setTypeface(font2);
		tvTotalDistance.setTypeface(font2);
		tvTotalDistance.setText("0 km");
		
		StepBDDAdapter bddAdapter = new StepBDDAdapter(getApplicationContext());
		bddAdapter.open();
		listStepBdd = new ArrayList<Step>();
		listStepBdd = bddAdapter.getAllSteps();
		bddAdapter.close();
		if(listStepBdd!=null){
			//affichage de la distance totale
		distanceTotal = calculDistanceTotal(listStepBdd);
		tvTotalDistance.setText(distanceTotal+" km");

		listAdapter = new CompostelleListStepAdapter(this.getBaseContext(), listStepBdd);
		lvListStep.setAdapter(listAdapter);
		
		lvListStep.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				step = new Step();
				step = listStepBdd.get(position);
				Intent intent = new Intent(CompostelleListStepActivity.this, CompostelleStepActivity.class);
				intent.putExtra("step",step);
				startActivity(intent);
				finish();
				CompostelleListStepActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
			}
		});}
		
	}
	

	/**
	 * Methode pour calculer la distance totale parcourue
	 * @param list, contient la liste des step pour obtenir la distance totale
	 * @return flaot la distance totale parcourue
	 */
	private float calculDistanceTotal(ArrayList<Step> list) {
		float d=0;
		String distanceTemp = null; 
		for (int i=0; i<list.size(); i++){			
			distanceTemp =list.get(i).getDistance();
			//corrige le bug du . si apparue avant v3.0
			if(distanceTemp.startsWith(".")){				
				distanceTemp = "0"+distanceTemp;					
			}if(distanceTemp.endsWith(".")){
				distanceTemp = distanceTemp+"0" ;					
			}				
			d = d+ Float.valueOf(distanceTemp);			
		}
		return d;
	}


    /**
     * methode permettant de gérer le bouton retour du mobile
     */
	@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(CompostelleListStepActivity.this,CompostelleActivity.class);
			startActivity(intent);
			finish();
			CompostelleListStepActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
		 }return super.onKeyDown(keyCode, event);
		}


}
