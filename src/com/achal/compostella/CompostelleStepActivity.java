package com.achal.compostella;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


/**
 * Class définissant l'activité étape, qui détaille l'étape sélectionnée depuis la list (CompostelleListStepActivity)
 * @author Aurore
 *
 */
public class CompostelleStepActivity extends Activity {
	
	private Step s;
	private Button bPublish, bModificate;
	private TextView tvTitre, tvDate, tvStart, tvEnd, tvDistance, tvComment, 
					tvDateTitre, tvStartTitre, tvEndTitre, tvDistanceTitre, tvCommentTitre;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_compostelle_step_activity2);

		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			tvTitre = (TextView) findViewById(R.id.tvTitreCompostelleStepActivity);
			tvDate = (TextView) findViewById(R.id.tvDateCompostelleStepActivity);
			tvStart = (TextView) findViewById(R.id.tvStartCompostelleStepActivity);
			tvEnd = (TextView) findViewById(R.id.tvEndCompostelleStepActivity);
			tvDistance = (TextView) findViewById(R.id.tvDistanceCompostelleStepActivity);
			tvComment = (TextView) findViewById(R.id.tvCommentCompostelleStepActivity);
			tvDateTitre = (TextView) findViewById(R.id.tvDateTitreCompostelleStepActivity);
			tvStartTitre = (TextView) findViewById(R.id.tvStartTitreCompostelleStepActivity);
			tvEndTitre = (TextView) findViewById(R.id.tvEndTitreCompostelleStepActivity);
			tvDistanceTitre = (TextView) findViewById(R.id.tvDistanceTitreCompostelleStepActivity);
			tvCommentTitre = (TextView) findViewById(R.id.tvCommentTitreCompostelleStepActivity);
			bPublish = (Button) findViewById(R.id.bPublishCompostelleStepActivity);
			bModificate = (Button) findViewById(R.id.bModificateCompostelleStepActivity);
			
			setCustomTypeface();

			s = new Step();
			s = getIntent().getExtras().getParcelable("step");

			tvDate.setText(s.getDate());
			tvStart.setText(s.getStart());
			tvEnd.setText(s.getEnd());
			tvDistance.setText(String.valueOf(s.getDistance()));
			Log.i("CompostelleStepActivity", "distance="+s.getDistance());
			
			tvComment.setText(s.getComment());			
			bPublish.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// ecrire la phrase et publier sur FB, twitter...
     		   			Intent intent = new Intent(Intent.ACTION_SEND);
     		        	intent.setType("text/plain");
     		        	intent.putExtra(Intent.EXTRA_TEXT, "message test application1");
     		        	CompostelleStepActivity.this.startActivity(Intent.createChooser(intent, "Partager avec..."));
				}
			}); 
			bModificate.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// modifier, appel new Step
					Intent intent = new Intent(CompostelleStepActivity.this, CompostelleNewStepActivity.class);
					Bundle b = new Bundle();
					b.putBoolean("isNewStep", false);
					intent.putExtras(b);
					intent.putExtra("step",s);
					startActivity(intent);
					finish();
					CompostelleStepActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
				
				}
			}); 
		}
	}
	
	/**
	 * Methode qui customise la police des objets de la vue
	 */
	  private void setCustomTypeface() {
			Typeface font2 = Typeface.createFromAsset(getAssets(), "font2.ttf");
			tvTitre.setTypeface(font2);
			tvDate.setTypeface(font2);
			tvStart.setTypeface(font2);
			tvEnd.setTypeface(font2);
			tvDistance.setTypeface(font2);
			tvComment.setTypeface(font2);
			tvDateTitre.setTypeface(font2);
			tvStartTitre.setTypeface(font2);
			tvEndTitre.setTypeface(font2);
			tvDistanceTitre.setTypeface(font2);
			tvCommentTitre.setTypeface(font2); 
			bPublish.setTypeface(font2); 
			bModificate.setTypeface(font2); 
		}

	    /**
	     * methode permettant de gérer le bouton retour du mobile
	     */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if (keyCode == KeyEvent.KEYCODE_BACK) {
			 Intent intent = new Intent(CompostelleStepActivity.this,CompostelleListStepActivity.class);
			 startActivity(intent);
			 finish();
			 CompostelleStepActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
		 }return super.onKeyDown(keyCode, event);
	}

}
