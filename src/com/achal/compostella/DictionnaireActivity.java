package com.achal.compostella;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Class qui définit l'activité dictionnaire appelé depuis la class OutilsActivity
 * @author Aurore
 *
 */
public class DictionnaireActivity extends Activity{
	
	private Spinner sCategorie, sLangue1, sLangue2;
	private TextView tvTitre;
	private ListView lvTranslate;
	private int nbLangues;
	private DictionnaireAdapter lvAdapter;
	private ArrayList<HashMap<String, String>> listTranslate ;
	private ArrayList<Mot> listMots;
	private SharedPreferences preferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_dictionnaire_activity);
		
		sCategorie = (Spinner) findViewById(R.id.spCategorieDictionnaireActivity);
		sLangue1 = (Spinner) findViewById(R.id.spLangue1DictionnaireActivity);
		sLangue2 = (Spinner) findViewById(R.id.spLangue2DictionnaireActivity);
		lvTranslate = (ListView) findViewById(R.id.lvMotsDictionnaireActivity);
		
		tvTitre = (TextView) findViewById(R.id.tvTitreDictionnaireActivity);
		Typeface font2 = Typeface.createFromAsset(getAssets(), "font2.ttf");
		tvTitre.setTypeface(font2);
		
		String[] listCategorie =  getResources().getStringArray(R.array.SpinnerCategorie);
		ArrayAdapter<String> adapterCategorie = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item, listCategorie){
	         public View getView(int position, View convertView, ViewGroup parent) {
	                 View v = super.getView(position, convertView, parent);
	                 Typeface externalFont=Typeface.createFromAsset(getAssets(), "font2.ttf");
	                 ((TextView) v).setTypeface(externalFont);
	                 ((TextView) v).setTextSize(22);
	                 return v;
	         }
	         public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
	                  View v =super.getDropDownView(position, convertView, parent);
	                 Typeface externalFont=Typeface.createFromAsset(getAssets(), "font2.ttf");
	                 ((TextView) v).setTypeface(externalFont);
	                 ((TextView) v).setTextSize(22);
	                 return v;
	         }
		};
		adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sCategorie.setAdapter(adapterCategorie);
		
		String[] listLangue =  getResources().getStringArray(R.array.SpinnerLangueItem);
		ArrayAdapter<String> adapterLangue = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item, listLangue){
	         public View getView(int position, View convertView, ViewGroup parent) {
	                 View v = super.getView(position, convertView, parent);
	                 Typeface externalFont=Typeface.createFromAsset(getAssets(), "font2.ttf");
	                 ((TextView) v).setTypeface(externalFont);
	                 ((TextView) v).setTextSize(22);
	                 return v;
	         }
	         public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
	                  View v =super.getDropDownView(position, convertView, parent);
	                 Typeface externalFont=Typeface.createFromAsset(getAssets(), "font2.ttf");
	                 ((TextView) v).setTypeface(externalFont);
	                 ((TextView) v).setTextSize(22);
	                 return v;
	         }
		};
		adapterLangue.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sLangue1.setAdapter(adapterLangue);
		sLangue2.setAdapter(adapterLangue);
		
		preferences = getSharedPreferences("prefLangues",  Context.MODE_PRIVATE);
		sLangue1.setSelection(preferences.getInt("Langue1", 0));
		sLangue2.setSelection(preferences.getInt("Langue2", 0));
		
		nbLangues = getResources().getStringArray(R.array.SpinnerLangueItem).length;

		listMots = new ArrayList<Mot>();
		listTranslate = new ArrayList<HashMap<String,String>>();
		lvAdapter = new DictionnaireAdapter(this.getBaseContext(), listTranslate);		
		lvTranslate.setAdapter(lvAdapter);
		
		
		sLangue1.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				int id2 = (int) sLangue2.getSelectedItemId();
				int idCategorie = (int) sCategorie.getSelectedItemId();
				afficherlisteTraduction(idCategorie, position, id2);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putInt("Langue1", position);
				editor.commit();
			}
			public void onNothingSelected(AdapterView<?> arg0) { }
		});
		sLangue2.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				int id1 = (int) sLangue1.getSelectedItemId();
				int idCategorie = (int) sCategorie.getSelectedItemId();
				afficherlisteTraduction(idCategorie, id1, position);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putInt("Langue2", position);
				editor.commit();
			}
			public void onNothingSelected(AdapterView<?> arg0) { }
		});
		sCategorie.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				int id1 = (int) sLangue1.getSelectedItemId();
				int id2 = (int) sLangue2.getSelectedItemId();
				afficherlisteTraduction(position, id1, id2);
			}
			public void onNothingSelected(AdapterView<?> arg0) { }
		});

	}


	  @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
		  if (keyCode == KeyEvent.KEYCODE_BACK) {
			    Intent intent = new Intent(DictionnaireActivity.this, OutilsActivity.class);
				startActivity(intent);
				finish();
				DictionnaireActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
		  }return super.onKeyDown(keyCode, event);
		}

	private void afficherlisteTraduction( int idCategorie, int idLangue1, int idLangue2) {
			//if(idLangue1!=idLangue2){
				listMots.clear();
				listTranslate.clear();
				HashMap<String, String> map ;
				String[] list =null;
				int nbMots=0;
				switch (idCategorie) {
					case 0: //nourriture
						list =  getResources().getStringArray(R.array.MotsCategorieNourriture);
						break;
					case 1: //communication
						list =  getResources().getStringArray(R.array.MotsCategorieCommunication);
						break;
					case 2: //lieux
						list =  getResources().getStringArray(R.array.MotsCategorieLieux);
						break;
					case 3: //se deplacer
						list =  getResources().getStringArray(R.array.MotsCategorieSeDeplacer);
						break;
				}
				nbMots =list.length;
				
				for (int i=0; i<(nbMots/nbLangues); i++){
					Mot mot = new Mot(list[5*i], list[5*i+1], list[5*i+2], list[5*i+3], list[5*i+4]);
					listMots.add(mot);
				}
				Collections.sort(listMots, new MotComparator(idLangue1));
				
				String mot1, mot2;				
				for (int i=0; i<listMots.size();i++){
					switch (idLangue1) {
						case 0: mot1 = listMots.get(i).getMotL1();
							break;
						case 1: mot1 = listMots.get(i).getMotL2();
							break;
						case 2: mot1 = listMots.get(i).getMotL3();
							break;
						case 3: mot1 = listMots.get(i).getMotL4();
							break;
						case 4: mot1 = listMots.get(i).getMotL5();
							break;
						default: mot1 = listMots.get(i).getMotL1();
							break;
					}switch (idLangue2) {
						case 0: mot2 = listMots.get(i).getMotL1();
							break;
						case 1: mot2 = listMots.get(i).getMotL2();
							break;
						case 2: mot2 = listMots.get(i).getMotL3();
							break;
						case 3: mot2 = listMots.get(i).getMotL4();
							break;
						case 4: mot2 = listMots.get(i).getMotL5();
							break;
						default: mot2 = listMots.get(i).getMotL1();
							break;
					}
					Log.i("DictionnaireActivity", "mot1="+mot1+", mot2="+mot2);
					map = new HashMap<String, String>();
					map.put("motL1", mot1);
					map.put("motL2", mot2);
					listTranslate.add(map);
				}
				lvAdapter.notifyDataSetChanged();
		//	}
	}

}
