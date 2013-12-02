package com.achal.compostella;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CaminoVoxNewAlbergueActivity extends Activity{
	
	private TextView tvTitre,tvNom,tvVille,tvType,tvPrix,tvPlaces,tvNote,tvServices,tvCuisine, tvLaveLinge, tvSecheLinge, tvInternet, tvWifi;
    private EditText etNom, etVille, etPrix, etPlaces;
/*     private ImageButton ibCuisine, ibLaveLinge, ibSecheLinge, ibInternet, ibWifi; */
    private CheckBox cbCuisine, cbLaveLinge, cbSecheLinge, cbInternet, cbWifi;
	private RatingBar rbNote;
	private Spinner spType;
    private Button bCreate;
    private static final int WAIT_RESPONSE_TIMEOUT = 12000;
	private static final int CONNECTION_TIMEOUT = 12000;
	private boolean isNewStep ;
	private Albergue al;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_caminovox_new_albergue_activity);
        
        //remarque: les textes seront les meme que pour CaminoVoxAlbergueActivity 
        setCustomTypeface();        
        etNom = (EditText) findViewById(R.id.etCaminovoxNewAlbergueNom);
        etVille= (EditText) findViewById(R.id.etCaminovoxNewAlbergueVille);
        etPrix= (EditText) findViewById(R.id.etCaminovoxNewAlberguePrix);
        etPlaces= (EditText) findViewById(R.id.etCaminovoxNewAlberguePlaces);   
		spType = (Spinner) findViewById(R.id.spCaminovoxNewAlbergueType);
		
		String[] listType =  getResources().getStringArray(R.array.SpinnerAlbergueType);
		ArrayAdapter<String> adapterType = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item, listType){
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
		adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spType.setAdapter(adapterType);
		
		cbCuisine = (CheckBox) findViewById(R.id.cbCaminovoxNewAlbergueCuisine);
        cbLaveLinge = (CheckBox) findViewById(R.id.cbCaminovoxNewAlbergueLaveLinge);
        cbSecheLinge = (CheckBox) findViewById(R.id.cbCaminovoxNewAlbergueSecheLinge);
        cbInternet = (CheckBox) findViewById(R.id.cbCaminovoxNewAlbergueInternet);
        cbWifi = (CheckBox) findViewById(R.id.cbCaminovoxNewAlbergueWifi); 
		
		
        rbNote  = (RatingBar) findViewById(R.id.rbCaminovoxNewAlbergueNote);        
        bCreate  = (Button) findViewById(R.id.bCaminovoxNewAlbergueCreate);
        
        Bundle b=getIntent().getExtras();
        if(b != null && b.containsKey("isNewStep")) { 
    		 isNewStep = b.getBoolean("isNewStep");
    		if(isNewStep==false){
    			al = new Albergue();
    			al = getIntent().getExtras().getParcelable("albergue"); 
				etNom.setText(al.getNom());
				etVille.setText(al.getVille());
				etPrix.setText(al.getPrix());
				etPlaces.setText(al.getNbplaces());  
				spType.setSelection(Integer.parseInt(al.getType())); 
				rbNote.setRating(Float.parseFloat(al.getNote()));
				cbCuisine.setChecked(al.getCuisine().equals("0") ? false : true);
				cbLaveLinge.setChecked(al.getLavelinge().equals("0") ? false : true);
				cbSecheLinge.setChecked(al.getSechelinge().equals("0") ? false : true);
				cbInternet.setChecked(al.getInternet().equals("0") ? false : true);
				cbWifi.setChecked(al.getWifi().equals("0") ? false : true);
    		}
    	}
		
        bCreate.setOnClickListener(new OnClickListener() {
             public void onClick(View v){ //format de l'objet JSON {msg={}}
			    try {
			    	JSONObject json= new JSONObject();	 
	            	 if(!isNewStep){ //cas de modification
	            		 json.put("id_albergue", al.getId()); 
	            	 } 
					json.put("nom", etNom.getText().toString());
					json.put("ville", etVille.getText().toString());
					json.put("prix", etPrix.getText().toString() );
					json.put("places", etPlaces.getText().toString());
					json.put("note", rbNote.getRating());
					json.put("type", spType.getSelectedItemId()+""); //TODO spinner
					json.put("cuisine", cbCuisine.isChecked() ? "1" : "0");
					json.put("lave_linge", cbLaveLinge.isChecked() ? "1" : "0");
					json.put("seche_linge", cbSecheLinge.isChecked() ? "1" : "0");
					json.put("internet", cbInternet.isChecked() ? "1" : "0");
					json.put("wifi", cbWifi.isChecked() ? "1" : "0");
					JSONObject jsonP = new JSONObject();
					jsonP.put("msg", json);
					//creation de la requete
					HttpPost request = null;
					if(isNewStep){ //cas de creation
						request = new HttpPost("http://maxime-riviere.com/buen_camino_api/addAlbergue"); 
	            	 } else{//cas de modification
	            		 request = new HttpPost("http://maxime-riviere.com/buen_camino_api/updateAlbergue");
	            	 }
					Log.i("NewAlbergue", "json="+jsonP.toString());
					request.setEntity(new ByteArrayEntity(jsonP.toString().getBytes("UTF8")));
					request.setHeader("json", jsonP.toString());
					 //Parametres de connexion
					 HttpParams httpParameters = new BasicHttpParams();
					 HttpConnectionParams.setConnectionTimeout(httpParameters, CONNECTION_TIMEOUT);
					 HttpConnectionParams.setSoTimeout(httpParameters, WAIT_RESPONSE_TIMEOUT);
					 HttpClient httpclient = new DefaultHttpClient(httpParameters);
					 //execution de la requete + recup reponse
					 HttpResponse response = httpclient.execute(request);
					 HttpEntity entity = response.getEntity();
					 Log.i("log_tag", "reponse recue"); 
					 if(entity.getContent()!=null){
						 //enregistrement reussi
						 Toast.makeText(getBaseContext(), getResources().getString(R.string.TexteCaminoVoxNewAlbergueSaved), Toast.LENGTH_LONG).show();
					 }
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) { 
					e.printStackTrace();
				} catch (ClientProtocolException e) { 
					e.printStackTrace();
				} catch (IOException e) { 
					e.printStackTrace();
				}
			    finish();
            }
        });        
    } 
    
    public void setCustomTypeface(){    
    	tvTitre = (TextView) findViewById(R.id.tvCaminovoxNewAlbergueTitre);
        tvNom = (TextView) findViewById(R.id.tvCaminovoxNewAlbergueNom);
        tvVille= (TextView) findViewById(R.id.tvCaminovoxNewAlbergueVille);
        tvType= (TextView) findViewById(R.id.tvCaminovoxNewAlbergueType);
        tvPrix= (TextView) findViewById(R.id.tvCaminovoxNewAlberguePrix);
        tvPlaces= (TextView) findViewById(R.id.tvCaminovoxNewAlberguePlaces);
        tvNote= (TextView) findViewById(R.id.tvCaminovoxNewAlbergueNote);
        tvServices= (TextView) findViewById(R.id.tvCaminovoxNewAlbergueServices); 
		tvCuisine = (TextView) findViewById(R.id.tvCaminovoxNewAlbergueCuisine);
        tvLaveLinge = (TextView) findViewById(R.id.tvCaminovoxNewAlbergueLaveLinge);
        tvSecheLinge = (TextView) findViewById(R.id.tvCaminovoxNewAlbergueSecheLinge);
        tvInternet = (TextView) findViewById(R.id.tvCaminovoxNewAlbergueInternet);
        tvWifi = (TextView) findViewById(R.id.tvCaminovoxNewAlbergueWifi); 
        Typeface font2 = Typeface.createFromAsset(getAssets(), "font2.ttf");
        tvTitre.setTypeface(font2);
        tvNom.setTypeface(font2);
        tvVille.setTypeface(font2);
        tvType.setTypeface(font2);
        tvPrix.setTypeface(font2);
        tvPlaces.setTypeface(font2);
        tvNote.setTypeface(font2);
        tvServices.setTypeface(font2); 
		tvCuisine.setTypeface(font2); 
        tvLaveLinge.setTypeface(font2); 
        tvSecheLinge.setTypeface(font2); 
        tvInternet.setTypeface(font2); 
        tvWifi.setTypeface(font2); 
    } 



	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(CaminoVoxNewAlbergueActivity.this,CompostelaMenuActivity.class);
	    	startActivity(intent);
			finish();
			CaminoVoxNewAlbergueActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
		 }return super.onKeyDown(keyCode, event);
	}


}
