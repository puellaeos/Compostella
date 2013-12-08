package com.achal.compostella;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


/**
 * Activité qui affiche le détail de l'albergue
 * @author Aurore
 *
 */
public class CaminoVoxAlbergueActivity extends ActionBarActivity {

	private TextView tvName, tvPrix, tvPlaces, tvType;
	private TextView tvTitrePrix, tvTitrePlaces, tvTitreType, tvTitreService, tvTitreNote;
	private ImageView imCuisine, imSecheLinge, imLaveLinge, imWifi, imInternet;
	private RatingBar rbNote;
	private Albergue albergue;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_caminovox_albergue_activity);
		
		Bundle b = getIntent().getExtras();
		if(b!=null && b.containsKey("albergue")){
			albergue = new Albergue();
			albergue=b.getParcelable("albergue"); 
			
			tvName = (TextView) findViewById(R.id.tvCaminovoxAlbergueName);
			tvPrix = (TextView) findViewById(R.id.tvCaminovoxAlberguePrix);
			tvPlaces = (TextView) findViewById(R.id.tvCaminovoxAlberguePlaces);
			tvType = (TextView) findViewById(R.id.tvCaminovoxAlbergueType);
			imCuisine = (ImageView) findViewById(R.id.ivCaminovoxAlbergueCuisine);
			imLaveLinge = (ImageView) findViewById(R.id.ivCaminovoxAlbergueLaveLinge);
			imSecheLinge = (ImageView) findViewById(R.id.ivCaminovoxAlbergueSecheLinge);
			imWifi = (ImageView) findViewById(R.id.ivCaminovoxAlbergueWifi);
			imInternet = (ImageView) findViewById(R.id.ivCaminovoxAlbergueInternet);
			rbNote = (RatingBar) findViewById(R.id.rbCaminovoxAlbergueNote);
			
			setCustomTypeface();
			
			ActionBar actionBar = getSupportActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true); 
			
			
			tvName.setText(albergue.getNom());
			tvPrix.setText(albergue.getPrix()+" €");
			tvPlaces.setText(albergue.getNbplaces());
			
			switch (Integer.parseInt(albergue.getType())) {
			case 1:tvType.setText(getResources().getString(R.string.TexteAlbergueTypeAl));
				break;
			case 2:tvType.setText(getResources().getString(R.string.TexteAlbergueTypeCasa));
				break;
			case 3:tvType.setText(getResources().getString(R.string.TexteAlbergueTypeHo));
				break; 
			default:tvType.setText(getResources().getString(R.string.TexteAlbergueTypeAl));
				break;
			}
			
			
			
			if(albergue.getCuisine().equals("0")) 
				imCuisine.setImageDrawable(getResources().getDrawable(R.drawable.ic_no_cuisine));
			if(albergue.getLavelinge().equals("0")) 
				imLaveLinge.setImageDrawable(getResources().getDrawable(R.drawable.ic_no_lavelinge));
			if(albergue.getSechelinge().equals("0")) 
				imSecheLinge.setImageDrawable(getResources().getDrawable(R.drawable.ic_no_sechelinge));
			if(albergue.getInternet().equals("0")) 
				imInternet.setImageDrawable(getResources().getDrawable(R.drawable.ic_no_internet));
			if(albergue.getWifi().equals("0")) 	
				imWifi.setImageDrawable(getResources().getDrawable(R.drawable.ic_no_wifi));
			if(albergue.getNote().equals("null") ||albergue.getNote().equals("")){
				rbNote.setRating(0);
			}else{
				rbNote.setRating(Float.parseFloat(albergue.getNote()));
			}
		}	
	}
	
	
	
	/**
	 * Methode qui customise la police des objets de la vue
	 */
	  private void setCustomTypeface() {
		  tvTitrePrix = (TextView) findViewById(R.id.tvCaminovoxAlbergueTitrePrix);
		  tvTitrePlaces= (TextView) findViewById(R.id.tvCaminovoxAlbergueTitrePlaces);
		  tvTitreType= (TextView) findViewById(R.id.tvCaminovoxAlbergueTitreType);
		  tvTitreService= (TextView) findViewById(R.id.tvCaminovoxAlbergueTitreServices);
		  tvTitreNote= (TextView) findViewById(R.id.tvCaminovoxAlbergueTitreNote);
		  
			Typeface font2 = Typeface.createFromAsset(getAssets(), "font2.ttf");
			tvName.setTypeface(font2);
			tvPrix.setTypeface(font2);
			tvPlaces.setTypeface(font2);
			tvType.setTypeface(font2);
			tvTitrePrix.setTypeface(font2);
			tvTitrePlaces.setTypeface(font2);
			tvTitreType.setTypeface(font2);
			tvTitreService.setTypeface(font2);
			tvTitreNote.setTypeface(font2);
		}

    /**
	* Méthode qui se déclenchera lorsque vous appuierez sur le bouton menu du téléphone
	*/
    public boolean onCreateOptionsMenu(Menu menu) {

    	getMenuInflater().inflate(R.menu.menu_caminovox_albergue, menu);
        return super.onCreateOptionsMenu(menu);
     }
	 
    /**
	* Méthode qui se déclenchera au clic sur un item
	*/
    public boolean onOptionsItemSelected(MenuItem item) {
         //On regarde quel item a été cliqué grâce à son id et on déclenche une action
    	Intent intent = new Intent(CaminoVoxAlbergueActivity.this, CaminoVoxNewAlbergueActivity.class);
		Bundle b = new Bundle();							
         switch (item.getItemId()) {
         case R.id.action_addAlbergue:
        	 	b.putBoolean("isNewStep", true);
         		intent.putExtras(b);
				startActivity(intent);
				finish();
				CaminoVoxAlbergueActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
				return true;
         case R.id.action_editAlbergue:
        	 b.putBoolean("isNewStep", false);
				b.putParcelable("albergue", albergue);
				intent.putExtras(b);
				startActivity(intent);
				finish();
				CaminoVoxAlbergueActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
				return true; 
         }
         return false;
       }
	  
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if (keyCode == KeyEvent.KEYCODE_BACK) {
				NavUtils.navigateUpFromSameTask(this);
				CaminoVoxAlbergueActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
		 }return super.onKeyDown(keyCode, event);
	}

	  
}
