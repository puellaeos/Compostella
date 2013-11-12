package com.achal.compostella;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/**
 * Class qui définit la page d'acceuil de l'application - première vue affichée au lancement de l'application.
 * L'utilisateur peut accéder aux activités PreparationActivity, PelerinageActivity, OutilsActivity ou JournalActivity avec les boutons
 * L'utilisateur peut accéder à l'activité AProposActivity via le bouton menu
 * @author Aurore
 *
 */
public class CompostelaMenuActivity extends Activity {
	
	private static final int MENU_ITEM1 = 1;
	private Button bPreparation, bPelerinage, bOutils, bCaminoVox ;
	private TextView tvTitreView;
	protected ProgressDialog mProgressDialog;
	private static final int WAIT_RESPONSE_TIMEOUT = 12000;
	private static final int CONNECTION_TIMEOUT = 12000;
	private InputStream is;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        setContentView(R.layout.layout_compostellamenu_activity);
        bPreparation = (Button) findViewById(R.id.ButtonPreparation);
        bPelerinage = (Button) findViewById(R.id.ButtonPelerinage);
        bOutils = (Button) findViewById(R.id.ButtonOutils);
        bCaminoVox = (Button) findViewById(R.id.ButtonJournal);
        tvTitreView = (TextView) findViewById(R.id.textViewTitreCompostellaMenu);
        
        
        
        Typeface font2 = Typeface.createFromAsset(this.getAssets(), "font2.ttf");
        bPreparation.setTypeface(font2);
        bPelerinage.setTypeface(font2);
        bOutils.setTypeface(font2);
        bCaminoVox.setTypeface(font2); 
        tvTitreView.setTypeface(font2); 
       
        
        
        bPreparation.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
						Intent intent = new Intent(CompostelaMenuActivity.this, PreparationActivity.class);
						startActivity(intent);
						finish();
						CompostelaMenuActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
			}
		});


         bPelerinage.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(CompostelaMenuActivity.this, PelerinageActivity.class);
				startActivity(intent);
				finish();
				CompostelaMenuActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
			}
		});
        
        bOutils.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(CompostelaMenuActivity.this, OutilsActivity.class);
				startActivity(intent);
				finish();
				CompostelaMenuActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
			}
		});
        
        bCaminoVox.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mProgressDialog = ProgressDialog.show(CompostelaMenuActivity.this, "","Please wait", true);
				new Thread(new Runnable() {
					public void run() {
						//TODO recuperer la reponse de la requete - ici listString
						ArrayList<String> listCity = new ArrayList<String>();
						listCity = getListCity();
						if(listCity!=null){
							Bundle b = new Bundle();
							b.putStringArrayList("listCity", listCity);
							Intent intent = new Intent(CompostelaMenuActivity.this, CaminoVoxListCityActivity.class);
							intent.putExtras(b);
							Log.i("menuActivity", "startActivity-CaminoVoxListCity");
							mProgressDialog.dismiss();
							startActivity(intent);
							finish();
							CompostelaMenuActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);							
						}else{
							mProgressDialog.dismiss();
						}
					}}).start();
			}
		});
        
        
    }
    
    
	/**
	 * retourne la liste des villes du serveur
	 * @return
	 */
	protected ArrayList<String> getListCity(){
		Log.i("info_tag", "requete envoyée");
		ArrayList<String> listCityTemp ;
		String reponseHttp=null;
		try{
			 HttpPost httppost = null;
			 ArrayList<NameValuePair> nameValuePairs = null;
			 httppost = new HttpPost("http://maxime-riviere.com/buen_camino/getListVilles.php");
			 //Parametres de connexion
			 HttpParams httpParameters = new BasicHttpParams();
			 HttpConnectionParams.setConnectionTimeout(httpParameters, CONNECTION_TIMEOUT);
			 HttpConnectionParams.setSoTimeout(httpParameters, WAIT_RESPONSE_TIMEOUT);
			 HttpConnectionParams.setTcpNoDelay(httpParameters, true);
			 HttpClient httpclient = new DefaultHttpClient(httpParameters);
			 //Execution de la requete
			 HttpResponse response = httpclient.execute(httppost);
			 //Récuperation de la requete POST
			 HttpEntity entity = response.getEntity();
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
 				//  le format doit etre{success =true/false ; msg=[{listVilles}]}
 				String success = json_object.getString("success");
 				if(success.equalsIgnoreCase("true")){
 					String msg = json_object.getString("msg");
 					if(msg.equals("null")){
 						/* runOnUiThread(new Runnable() {
 							public void run() {
 								//TODO pas d'info -> cas existant ?
 						}});System.out.println("cas du message null"); */
						return null;
 					}else{
 						listCityTemp = new ArrayList<String>();
 					JSONArray jArrayMsg = new JSONArray(msg);
 					JSONObject job= new JSONObject();
 					for(int i=0; i<jArrayMsg.length() ; i++){
 						job = jArrayMsg.getJSONObject(i);
 						listCityTemp.add(job.getString("Ville"));
 						Log.i("getListCity", "item "+i+" : "+job.getString("Ville"));
 					}
 					return listCityTemp;
 					}
 				}/*else{ //success==false
              		AlertDialog.Builder adb2 = new AlertDialog.Builder(CaminoVoxListCityActivity.this);
      		   		adb2.setTitle("Votre connection a échouée");
      		   		adb2.setPositiveButton("Ok",  new DialogInterface.OnClickListener() {
      		            public void onClick(DialogInterface dialog, int which) {
      		            	//TODO que  faire
      		          } });
      		   		adb2.show(); 
					return null;
             	}*/
 			} catch (JSONException e) {
 				Log.e("log_tag", "Error converting result "+e.toString());
 			}		 		
		return null;		
	}
	
 
	
    /**
	* Méthode qui se déclenchera lorsque vous appuierez sur le bouton menu du téléphone
	*/
    public boolean onCreateOptionsMenu(Menu menu) {
    	String titre = getResources().getString(R.string.TitreAPropos);
    	menu.add(0, MENU_ITEM1, Menu.NONE, titre); 

         return true;
     }
 
    /**
	* Méthode qui se déclenchera au clic sur un item
	*/
    public boolean onOptionsItemSelected(MenuItem item) {
         //On regarde quel item a été cliqué grâce à son id et on déclenche une action
         switch (item.getItemId()) {
            case MENU_ITEM1:	Intent intent = new Intent(CompostelaMenuActivity.this, AProposActivity.class);
								startActivity(intent);
								finish();
								CompostelaMenuActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
				return true;
            
         }
         return false;
       }
}