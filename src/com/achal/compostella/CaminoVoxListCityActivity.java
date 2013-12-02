package com.achal.compostella;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


/**
 * Activité qui affiché la liste des villes enregistrées (serveur ou bdd - a definir)
 * @author Aurore
 *
 */
public class CaminoVoxListCityActivity extends ActionBarActivity {

	protected ProgressDialog mProgressDialog;
	private static final int WAIT_RESPONSE_TIMEOUT = 12000;
	private static final int CONNECTION_TIMEOUT = 12000;
	private InputStream is;
	private ArrayList<String> listCity=null;
	private int _idVilleSelected=0;
	private CaminoVoxListAdapter lvAdapter;
	private ArrayList<Integer> listIdAlbergue;
	private static final int MENU_CREATE_ALBERGUE = 1;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_caminovox_listcity_activity);
        
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null && bundle.containsKey("listCity")){					
			listCity = new ArrayList<String>();		
			listCity = bundle.getStringArrayList("listCity");
			Log.i("CaminoVoxListAlbergues", "listcity"+listCity.toString());
			
			TextView tvTitre = (TextView) findViewById(R.id.tvCaminovoxListCityTitre);
			TextView tvTexte = (TextView) findViewById(R.id.tvCaminovoxListCityTexte);
			Typeface font2 = Typeface.createFromAsset(getAssets(), "font2.ttf");
			tvTitre.setTypeface(font2);	
			tvTexte.setTypeface(font2);	
			
			ActionBar actionBar = getSupportActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
			
			ListView lvListCity = (ListView) findViewById(R.id.lvCaminovoxListCity); 
			lvAdapter = new CaminoVoxListAdapter(this.getBaseContext(),listCity);
			lvListCity.setAdapter(lvAdapter);
			
			lvListCity.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {		
					_idVilleSelected=arg2;
					mProgressDialog = ProgressDialog.show(CaminoVoxListCityActivity.this, "","Please wait", true);
					new Thread(new Runnable() {
						public void run() {
							// recuperer la reponse de la requete - ici listString
							ArrayList<String> listAlbergues = new ArrayList<String>();
							String nameCity = null;
							nameCity=listCity.get(_idVilleSelected);
							listAlbergues = getListAlbergues(nameCity); 
							if(listAlbergues!=null){
								Intent intent = new Intent(CaminoVoxListCityActivity.this, CaminoVoxListAlberguesActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("nameCity", nameCity);
								bundle.putStringArrayList("listAlbergues", listAlbergues);
								bundle.putIntegerArrayList("listIdAlbergues", listIdAlbergue);
								intent.putExtras(bundle);
								Log.i("listCityActivity", "startActivity-CaminoVoxListAlbergues, nameCity="+nameCity); 
								mProgressDialog.dismiss();
								startActivity(intent);//Ne pas quitter la vue pour eviter de rechercher a nouveau les villes
								CaminoVoxListCityActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
							}else{
								mProgressDialog.dismiss();
							}
					}}).start();
			}});
			EditText etFilter = (EditText) findViewById(R.id.etCaminovoxListCityFilter);
			etFilter.addTextChangedListener(new TextWatcher() {			
				public void onTextChanged(CharSequence s, int start, int before, int count) { 
					lvAdapter.getFilter().filter(s.toString());
				}			
				public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
				
				public void afterTextChanged(Editable s) { }
			});
		}
	}
	
	/**
	 * retourne la liste des albergues d'une ville du serveur
	 * @return
	 */
	protected ArrayList<String> getListAlbergues(String ville){
		ArrayList<String> listAlTemp =null;
		String reponseHttp=null;
		try{
			 HttpPost httppost = null;
			 ArrayList<NameValuePair> nameValuePairs = null;
			 // modifier la requete + preciser les param requetes
			 httppost = new HttpPost("http://maxime-riviere.com/buen_camino_api/getlistalbergues");
			 //Ajout des parametres de la requete
			 nameValuePairs = new ArrayList<NameValuePair>();
			 nameValuePairs.add(new BasicNameValuePair("ville", ville));			 
			 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			 //Parametres de connexion
			 HttpParams httpParameters = new BasicHttpParams();
			 HttpConnectionParams.setConnectionTimeout(httpParameters, CONNECTION_TIMEOUT);
			 HttpConnectionParams.setSoTimeout(httpParameters, WAIT_RESPONSE_TIMEOUT);
			 HttpConnectionParams.setTcpNoDelay(httpParameters, true);
			 HttpClient httpclient = new DefaultHttpClient(httpParameters);
			 //Execution de la requete
			 HttpResponse response = httpclient.execute(httppost);
			 Log.i("log_tag", "requete envoyée");
			 //Récuperation de la requete POST
			 HttpEntity entity = response.getEntity();
			 Log.i("log_tag", "requete envoyée");
			 is = entity.getContent();
	   	 }catch(Exception e){
			 Log.e("log_tag", "Erreur d'envoie de requete http "+e.toString());
			 return null;
		 }
		 
		 //Recuperation depuis le buffer a travers un objet JSON
	     try{
    	 	 BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    	 	 StringBuilder sb = new StringBuilder();
    	 	 String line = "";
    		 String NL = System.getProperty("line.separator");
    		 while ((line = reader.readLine()) != null) {
    			 sb.append(line + NL);
    		 } 
    		 reader.close();
    		 reponseHttp=sb.toString();
    		 is.close();
    	 }catch(Exception e){
    		 Log.e("log_tag", "Error converting result "+e.toString());
    		 return null;
    	 }
    		 try {
 				JSONObject json_object = (JSONObject) new JSONTokener(reponseHttp).nextValue();
 				 Log.i("log_tag", "JSON objet=" + json_object.toString());
 				// le format doit etre{success =true/false ; msg={listalbergues}}
 				String success = json_object.getString("success");
 				if(success.equalsIgnoreCase("true")){
 					String msg = json_object.getString("msg");
 					if(msg.equals("null")){
 						runOnUiThread(new Runnable() {
 							public void run() {
 								//TODO pas d'info -> cas existant ?
 						}});System.out.println("cas du message null");
 					}else{
 						listAlTemp = new ArrayList<String>();
 						listIdAlbergue = new ArrayList<Integer>();
 						JSONArray jArrayMsg = new JSONArray(msg);
 						JSONObject job = new JSONObject();
 						
 						for(int i=0; i<jArrayMsg.length();i++){
 							job= jArrayMsg.getJSONObject(i);
 							listAlTemp.add(job.getString("nom"));
 							listIdAlbergue.add(job.getInt("id_albergue"));
 							Log.i("getListAl", "item "+i+" : "+job.getString("nom"));
 						}
 						return listAlTemp;
						
 					}
 				}/*else{ //success==false
             		AlertDialog.Builder adb2 = new AlertDialog.Builder(CaminoVoxListCityActivity.this);
      		   		adb2.setTitle("Votre connection a échouée");
      		   		adb2.setPositiveButton("Ok",  new DialogInterface.OnClickListener() {
      		            public void onClick(DialogInterface dialog, int which) {
      		            	//TODO que  faire
      		          } });
      		   		adb2.show();
             	}*/
 			} catch (JSONException e) {
 				e.printStackTrace();
 			}		 		
		return null;		
	}
	
	
	  /**
		* Méthode qui se déclenchera lorsque vous appuierez sur le bouton menu du téléphone ou la bar d'action
		*/
	    public boolean onCreateOptionsMenu(Menu menu) {	    	
	    	getMenuInflater().inflate(R.menu.menu_caminovox_listcity, menu);
	    	MenuItem searchItem = menu.findItem(R.id.action_searchCity);
	        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
			return super.onCreateOptionsMenu(menu);
	     }
		 
	    /**
		* Méthode qui se déclenchera au clic sur un item
		*/
	    public boolean onOptionsItemSelected(MenuItem item) {
	         //On regarde quel item a été cliqué grâce à son id et on déclenche une action
	    	Intent intent; 
	    	switch (item.getItemId()) {
	            case R.id.action_addAlbergue: 
		            intent = new Intent(CaminoVoxListCityActivity.this, CaminoVoxNewAlbergueActivity.class);
					Bundle b = new Bundle();
					b.putBoolean("isNewStep", true);							
			        intent.putExtras(b);
					startActivity(intent);
					finish();
					CaminoVoxListCityActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
					return true; 
	            case android.R.id.home : //retour à la page d'accueil via actionBar	            	
	            	intent = new Intent(CaminoVoxListCityActivity.this,CompostelaMenuActivity.class);
	    	    	startActivity(intent);
	    			finish();
	    			CaminoVoxListCityActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
	            	
	         }
	         return false;
	       }
		  
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if (keyCode == KeyEvent.KEYCODE_BACK) {
//			Intent intent = new Intent(CaminoVoxListCityActivity.this,CompostelaMenuActivity.class);
//	    	startActivity(intent);
//			finish();
			 NavUtils.navigateUpFromSameTask(this);
			CaminoVoxListCityActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
		 }return super.onKeyDown(keyCode, event);
	}

}
