package com.achal.compostella;

import java.text.DateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Class qui définit l'activité pour ajouter une nouvelle étape
 * @author Aurore
 *
 */
public class CompostelleNewStepActivity extends Activity{
	
	private Step s;
	private EditText  etStart, etEnd, etDistance, etComment;
	private Button bAddStep, bDate;
	private TextView tvTitre, tvDate, tvStart, tvEnd, tvDistance, tvComment;
	private boolean isNewStep;
	private final static int ID_DATEPICKER=1;
	
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
         public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        	Calendar c = Calendar.getInstance();
 	        c.set(year, monthOfYear, dayOfMonth);
 	        String date =formaterDate(c);
	        bDate.setText(date);
         }
     };
	
     
     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_compostelle_newstep_activity);
		
		tvTitre = (TextView) findViewById(R.id.tvTitreCompostelleNewStepActivity);
		tvDate = (TextView) findViewById(R.id.tvNewStepDate);
		tvStart = (TextView) findViewById(R.id.tvNewStepStart);
		tvEnd = (TextView) findViewById(R.id.tvNewStepEnd);
		tvDistance = (TextView) findViewById(R.id.tvNewStepDistance);
		tvComment = (TextView) findViewById(R.id.tvNewStepCommentaire); 
		etStart = (EditText) findViewById(R.id.etNewStepStart);
		etEnd = (EditText) findViewById(R.id.etNewStepEnd);
		etDistance = (EditText) findViewById(R.id.etNewStepDistance);
		etComment = (EditText) findViewById(R.id.etNewStepCommentaire);
		bAddStep = (Button) findViewById(R.id.bNewStepAddStep);
		bDate = (Button) findViewById(R.id.bNewStepDate);

		setCustomTypeface();
		
		isNewStep=true;
		Bundle b = getIntent().getExtras();
		if (b != null && b.containsKey("isNewStep")) {
		Log.i("AddNewStep","isNewStep="+isNewStep);
		isNewStep = b.getBoolean("isNewStep");
		if(isNewStep==false){
			s = new Step();
			s = getIntent().getExtras().getParcelable("step");
			bDate.setText(s.getDate());
			etStart.setText(s.getStart());
			etEnd.setText(s.getEnd());
			etDistance.setText(s.getDistance());
			etComment.setText(s.getComment());
			Log.i("AddNewStep","step updated");
			
		}else{
			Calendar c = Calendar.getInstance();
		  	String dateInit=formaterDate(c);
			bDate.setText(dateInit);
		}
		bDate.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {showDialog(ID_DATEPICKER); }
		});	  	
		bAddStep.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String date = bDate.getText().toString();
				String start = etStart.getText().toString();
				String end = etEnd.getText().toString();
				String comment = etComment.getText().toString();
				String sDistance = etDistance.getText().toString();
				
				Log.i("AddNewStep","step:"+ date+","+start+","+end+","+sDistance+","+comment+".");
				
				if(date.matches("") || start.matches("") || end.matches("") ||sDistance.matches("")){// distance==0){
					String text = null;
					text = getResources().getString(R.string.TexteStepMandatory);
					Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG).show();
				}else{
					if(sDistance.startsWith(".")){
						sDistance = "0"+sDistance ;					
					}if(sDistance.endsWith(".")){
						sDistance = sDistance+"0" ;					
					}
					
					StepBDDAdapter bddAdapter = new StepBDDAdapter(getApplicationContext());
					bddAdapter.open();
					if(isNewStep){
						Step step = new Step(date, start, end, comment, sDistance);						
						bddAdapter.insertStep(step);
					}else{
						s.setDate(date);
						s.setStart(start);
						s.setEnd(end);
						s.setComment(comment);
						s.setDistance(sDistance);
						bddAdapter.updateStep(s.getId(), s);
						Log.i("AddNewStep","step updated:"+ date+","+start+","+end+","+sDistance+","+comment+".");
					}					
					bddAdapter.close();
					retourCompostelle();
				}
				
			}
		});
		}
	}
	/**
	 * Methode pour modifier la police des textes de chaque objet
	 * 
	 */
	  private void setCustomTypeface() {
		Typeface font2 = Typeface.createFromAsset(getAssets(), "font2.ttf");
		tvTitre.setTypeface(font2);
		tvDate.setTypeface(font2);
		tvStart.setTypeface(font2);
		tvEnd.setTypeface(font2);
		tvDistance.setTypeface(font2);
		tvComment.setTypeface(font2);
		bDate.setTypeface(font2);
		etStart.setTypeface(font2);
		etEnd.setTypeface(font2);
		etDistance.setTypeface(font2);
		etComment.setTypeface(font2);
		bAddStep.setTypeface(font2);
	}

	  
	  @Override
	  /**
	   *  Methode pour la creation du DatePickerDialog
	   */	  
		protected Dialog onCreateDialog(int id) {
		  if(id==ID_DATEPICKER){
		  	Calendar c = Calendar.getInstance();
	        int year = c.get(Calendar.YEAR);
	        int month = c.get(Calendar.MONTH);
	        int day = c.get(Calendar.DAY_OF_MONTH);
			return new DatePickerDialog(this, mDateSetListener , year, month, day);
		  }else{return null;}
		}
	  
	  /**
	   * Methode pour formater la date 
	   * @param c Calendar utiliser dans l'application et qui sera formatter
	   * @return date, String contenant la date formater depuis le calendar fournit en input
	   */
	  private String formaterDate(Calendar c){
	        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
	        df.setCalendar(c);
	        String date = df.format(c.getTime()); 
	        return date;
	  }
	  

	  /**
	   * Retour a la vue parent CompostelleActivity
	   */
	  private void retourCompostelle() {
			Intent intent = new Intent(CompostelleNewStepActivity.this,OutilsActivity.class);
			startActivity(intent);
			finish();
			CompostelleNewStepActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
			
		}
	    /**
	     * methode permettant de gérer le bouton retour du mobile
	     */
	@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if (keyCode == KeyEvent.KEYCODE_BACK) {	
			 retourCompostelle();
		 }return super.onKeyDown(keyCode, event);
		}
		

}
