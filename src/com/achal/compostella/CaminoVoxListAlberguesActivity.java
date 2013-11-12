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
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


/**
 * Affiche la liste des albergues pour une ville
 * @author Aurore
 *
 */
public class CaminoVoxListAlberguesActivity extends Activity  {


	protected ProgressDialog mProgressDialog;
	private static final int WAIT_RESPONSE_TIMEOUT = 12000;
	private static final int CONNECTION_TIMEOUT = 12000;
	private InputStream is;
	private ArrayList<String> listAlbergue;
	private ArrayList<Integer> listIdAlbergue;
	private int _itemSelected=0;
 	private String nameCity=null;
	private CaminoVoxListAdapter lvAdapter;
	private static final int MENU_CREATE_ALBERGUE = 1;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_caminovox_listalbergue_activity);
		
		TextView tvTitre = (TextView) findViewById(R.id.tvCaminovoxListAlberguesTitre);
		TextView tvTexte = (TextView) findViewById(R.id.tvCaminovoxListAlberguesTexte);
		Typeface font2 = Typeface.createFromAsset(getAssets(), "font2.ttf");
		tvTitre.setTypeface(font2);	
		tvTexte.setTypeface(font2);	
		 
		ListView lvListAlbergue = (ListView) findViewById(R.id.lvCaminovoxListAlbergues);
		
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null && bundle.containsKey("listAlbergues") && bundle.containsKey("listIdAlbergues") && bundle.containsKey("nameCity")){			
			nameCity = bundle.getString("nameCity");
			Log.i("CaminoVoxListAlberguesActivity", "nameCity="+nameCity);
			tvTitre.setText(nameCity);
		
			listAlbergue = new ArrayList<String>();
			listIdAlbergue = new ArrayList<Integer>();
			listAlbergue = bundle.getStringArrayList("listAlbergues");
			listIdAlbergue = bundle.getIntegerArrayList("listIdAlbergues");
			lvAdapter = new CaminoVoxListAdapter(this.getBaseContext(),listAlbergue);
			lvListAlbergue.setAdapter(lvAdapter);
			lvListAlbergue.setOnItemClickListener(new OnItemClickListener() {
	
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
					_itemSelected=arg2;
					mProgressDialog = ProgressDialog.show(CaminoVoxListAlberguesActivity.this, "","Please wait", true);
					new Thread(new Runnable() {
						public void run() {
							Intent intent = new Intent(CaminoVoxListAlberguesActivity.this, CaminoVoxAlbergueActivity.class);
							Bundle bundle = new Bundle();
							Albergue al = new Albergue();
							al=getAlbergue(listIdAlbergue.get(_itemSelected).toString());
							if(al!=null){
								bundle.putParcelable("albergue", al);
								intent.putExtras(bundle);
								Log.i("ListAlberguesActivity", "startActivity-CaminoVoxAlbergue");
								mProgressDialog.dismiss();
								startActivity(intent); //Ne pas quitter la vue pour eviter de rechercher a nouveau les villes
								CaminoVoxListAlberguesActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
							}else{
							mProgressDialog.dismiss();
							}
					}}).start();
				}
			});
			
			EditText etFilter = (EditText) findViewById(R.id.etCaminovoxListAlberguesFilter);
			etFilter.addTextChangedListener(new TextWatcher() {
				
				public void onTextChanged(CharSequence s, int start, int before, int count) { 
					lvAdapter.getFilter().filter(s.toString());
				}
				
				public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
				
				public void afterTextChanged(Editable s) { }
			});
			
		
		}
	}
	
	protected Albergue getAlbergue(String idAlbergue) {
		Albergue al_temp= null;
		String reponseHttp=null;
		try{
			 HttpPost httppost = null;
			 ArrayList<NameValuePair> nameValuePairs = null;
			 // TODO modifier la requete + preciser les param requetes
			 httppost = new HttpPost("http://maxime-riviere.com/buen_camino/getAlbergue.php");
			 //Ajout des parametres de la requete
			 nameValuePairs = new ArrayList<NameValuePair>();
			 nameValuePairs.add(new BasicNameValuePair("id_albergue", idAlbergue));			 
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
			 Log.i("log_tag", "reponse recue");
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
			JSONObject json_object2 = (JSONObject) new JSONTokener(reponseHttp).nextValue();
			Log.i("log_tag", "JSON objet=" + json_object2.toString());
			// le format doit etre{success =true/false ; msg={listalbergues}}
			String success = json_object2.getString("success");
			if(success.equalsIgnoreCase("true")){
				String msg = json_object2.getString("msg");
				if(msg.equals("null")){
					runOnUiThread(new Runnable() {
						public void run() {
							//TODO pas d'info -> cas existant ?
					}});System.out.println("cas du message null");
				}else{
					JSONObject json_object = new JSONArray(msg).getJSONObject(0);
					al_temp= new Albergue();
					al_temp.setId(Integer.parseInt(json_object.getString("id_albergue")));
					al_temp.setNom(json_object.getString("nom"));
					al_temp.setVille(json_object.getString("ville"));
					al_temp.setPrix(json_object.getString("prix"));
					al_temp.setNbplaces(json_object.getString("places"));
					al_temp.setNote(json_object.getString("note"));
					al_temp.setType(json_object.getString("type"));
					al_temp.setCuisine(json_object.getString("cuisine"));
					al_temp.setLavelinge(json_object.getString("lave_linge"));
					al_temp.setSechelinge(json_object.getString("seche_linge"));
					al_temp.setInternet(json_object.getString("internet"));
					al_temp.setWifi(json_object.getString("wifi"));
					return al_temp;		
				}
			}else{ //success==false
				return null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}		 		
		return null;	
	}


	  /**
		* Méthode qui se déclenchera lorsque vous appuierez sur le bouton menu du téléphone
		*/
	    public boolean onCreateOptionsMenu(Menu menu) {
	    	String menu1 = getResources().getString(R.string.MenuCaminoVoxAlbergueCreate);
	    	 menu.add(0, MENU_CREATE_ALBERGUE, Menu.NONE, menu1); 
	    	return true;
	     }
		 
	    /**
		* Méthode qui se déclenchera au clic sur un item
		*/
	    public boolean onOptionsItemSelected(MenuItem item) {
	         //On regarde quel item a été cliqué grâce à son id et on déclenche une action
	    	Intent intent = new Intent(CaminoVoxListAlberguesActivity.this, CaminoVoxNewAlbergueActivity.class);
			Bundle b = new Bundle();							
	         switch (item.getItemId()) {
	            case MENU_CREATE_ALBERGUE: b.putBoolean("isNewStep", true);
	            	intent.putExtras(b);
					startActivity(intent);
					finish();
					CaminoVoxListAlberguesActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
					return true; 
	         }
	         return false;
	       }
		  





  	
	
}
