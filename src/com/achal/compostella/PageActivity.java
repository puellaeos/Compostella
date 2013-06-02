package com.achal.compostella;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * class qui gère l'affichage des différentes vues de l'application
 * Elle est appelée par les activités PelerinageActivity, PreparationActivity
 * @author Aurore
 *
 */
public class PageActivity extends Activity{
	
	private static final int Id_Page_Erreur =0;
	private static final int Id_Page_Papiers =1;
	private static final int Id_Page_Partir =2;
	private static final int Id_Page_Sequiper =3;
	private static final int Id_Page_Manger =4;
	private static final int Id_Page_Dormir =5;
	private static final int Id_Page_Histoire =6;
	private static final int Id_Page_Chemins =7;	
	private static final int Id_Page_MotsCles =8;
	private static final int Id_PelerinageActivity =20;
	private static final int Id_PreparationActivity =21;
	
	
	private TextView tvTitre, tvTexte;
	private ImageButton bPrecedent, bSuivant;
	private View vueTexte;
	private ScrollView scrollViewTexte;
	private ProgressBar pBarPage;
	private int idPage =0, maxPages=0, numPage=0, idParentActivity=0;	
	private GestureDetector mGestureDetector;
	private OnTouchListener mGestureListener;
	
	private GestureDetector.SimpleOnGestureListener simpleGestureDetector = new GestureDetector.SimpleOnGestureListener() {
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			Display display = getWindowManager().getDefaultDisplay(); 
			int width_min = display.getWidth()/4;
			 if(e2.getX() - e1.getX() > width_min ){
				//fling vers la gauche = precedent
				 Log.i("gestureDetector", "slide precedent  TEST");
				 if(numPage>0){
					 Log.i("gestureDetector", "slide precedent");
					 numPage=numPage-1;
					 afficher_texte(false, idPage, numPage);
					 afficher_progressBar(numPage);
					 afficher_button(numPage);
				 }else{
					 numPage=0;
				 }
			 }else if(e1.getX() - e2.getX() > width_min){
				//fling vers la droite = suivant
				 Log.i("gestureDetector", "slide suivant  TEST");
				 if(numPage<maxPages-1){
					 Log.i("gestureDetector", "slide suivant");
					 numPage=numPage+1;
					 afficher_texte(false, idPage, numPage);
					 afficher_progressBar(numPage);
					 afficher_button(numPage);
				 }else{
					 numPage=maxPages-1;
				 }
			}
			return true;
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_page_activity);
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null && bundle.containsKey("button")){
			idPage = getIntent().getIntExtra("button", 0);
			if(idPage!=Id_Page_Erreur){
				tvTexte = (TextView) findViewById(R.id.tvTextePageActivity);
				tvTitre = (TextView) findViewById(R.id.tvTitrePageActivity);
				vueTexte = findViewById(R.id.layoutTextPage);
				scrollViewTexte = (ScrollView) findViewById(R.id.scrollviewTextPage);
				bPrecedent = (ImageButton) findViewById(R.id.ibPreviousPageActivity);
				bSuivant = (ImageButton) findViewById(R.id.ibNextPageActivity);
				pBarPage = (ProgressBar) findViewById(R.id.progressBarLecturePage);
				
				Typeface font2 = Typeface.createFromAsset(getAssets(), "font2.ttf");
				tvTexte.setTypeface(font2);
				tvTitre.setTypeface(font2);
				
				bPrecedent.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						if(numPage>0){
							 Log.i("button", "slide precedent");
							 numPage=numPage-1;
							 afficher_texte(false, idPage, numPage);
							 afficher_progressBar(numPage);
							 afficher_button(numPage);
						 }else{
							 numPage=0;
						 }
					}
				});
				bSuivant.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						if(numPage<maxPages-1){
							 Log.i("button", "slide suivant");
							 numPage=numPage+1;
							 afficher_texte(false, idPage, numPage);
							 afficher_progressBar(numPage);
							 afficher_button(numPage);
						 }else{
							 numPage=maxPages-1;
						 }
					}
				});
				
				
		        mGestureDetector = new GestureDetector(simpleGestureDetector);
		        mGestureListener = new View.OnTouchListener() { 
		             public boolean onTouch(View v, MotionEvent event) { 
		                Log.i("listener", "mGestureListener,mGestureDetector.onTouchEvent(event)="+mGestureDetector.onTouchEvent(event));
		        		return mGestureDetector.onTouchEvent(event) ;
		             } 
		         };
		         vueTexte.setOnTouchListener(mGestureListener);			
				afficher_texte(true, idPage, 0);
				afficher_progressBar(0);
				afficher_button(0);
			}
			
		}
	}

	protected void afficher_button(int num) {
		Log.i("PageActivity", "numPage="+num);
		if(num==0){
			bPrecedent.setVisibility(View.INVISIBLE);
			bSuivant.setVisibility(View.VISIBLE);
		}else if(num==maxPages-1){
			bPrecedent.setVisibility(View.VISIBLE);
			bSuivant.setVisibility(View.INVISIBLE);
		}else{
			bPrecedent.setVisibility(View.VISIBLE);
			bSuivant.setVisibility(View.VISIBLE);
		}
	}

	public boolean onTouchEvent(MotionEvent ev) {
		Log.i("event", "ontoucheEvent, mGestureDetector.onTouchEvent(ev)="+mGestureDetector.onTouchEvent(ev));
		return mGestureDetector.onTouchEvent(ev);
	}

	
	public boolean dispatchTouchEvent(MotionEvent ev){
	    super.dispatchTouchEvent(ev);    
	    return mGestureDetector.onTouchEvent(ev); 
	}
	
	/**
	 * 
	 * @param firstView boolean, indiquant si c'est la première fois que la vue s'affiche
	 * @param id_page int, indiquant l'identifiant de la page parent
	 * @param num  int, indiquant le numero de la page
	 */
	private void afficher_texte(boolean firstView,int id_page, int num) {
		Log.i("afficherTexte", "firstView="+firstView+", id_page="+id_page+", num="+num);
		String[] arrayText=null;
		switch (id_page) {
		case Id_Page_Papiers: 
			if(firstView){
				tvTitre.setText(R.string.TitrePapiers);
				idParentActivity = Id_PreparationActivity;}
			arrayText= getResources().getStringArray(R.array.TextePapiers);
			break;
		case Id_Page_Partir: 
			if(firstView){
				tvTitre.setText(R.string.TitreMarcher);
				idParentActivity = Id_PreparationActivity;}
			arrayText= getResources().getStringArray(R.array.TexteMarcher);
			break;
		case Id_Page_Sequiper:
			if(firstView){
				tvTitre.setText(R.string.TitreSequiper);
				idParentActivity = Id_PreparationActivity;}
			arrayText= getResources().getStringArray(R.array.TexteSequiper);
			break;
		case Id_Page_Manger:
			if(firstView){
				tvTitre.setText(R.string.TitreManger);
				idParentActivity = Id_PreparationActivity;}
			arrayText= getResources().getStringArray(R.array.TexteManger);
			break;
		case Id_Page_Dormir:  
			if(firstView){
				tvTitre.setText(R.string.TitreDormir);
				idParentActivity = Id_PreparationActivity;}
			arrayText= getResources().getStringArray(R.array.TexteDormir);
			break;
		case Id_Page_Histoire: 
			if(firstView){
				tvTitre.setText(R.string.TitreHistoire);
				idParentActivity = Id_PelerinageActivity;}
			arrayText= getResources().getStringArray(R.array.TexteHistoire);
			break;
		case Id_Page_Chemins: 
			if(firstView){
				tvTitre.setText(R.string.TitreChemins);
				idParentActivity = Id_PelerinageActivity;}
			arrayText= getResources().getStringArray(R.array.TexteChemins);
			break;
		case Id_Page_MotsCles:
			if(firstView){
				tvTitre.setText(R.string.TitreMotsCles);
			idParentActivity = Id_PelerinageActivity;}
			arrayText= getResources().getStringArray(R.array.TexteMotsCles);
			break;
		default:break;
		};

		tvTexte.setText(arrayText[num]);	
		//remettre la vue sur le haut du texte :
		scrollViewTexte.fullScroll(View.FOCUS_UP);
		maxPages=arrayText.length;
	}
	
	private void afficher_progressBar(int num) {
		if(num==0)
			pBarPage.setMax(maxPages);	
		pBarPage.setProgress(num);
		pBarPage.setSecondaryProgress(num+1);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 if (keyCode == KeyEvent.KEYCODE_BACK) {
		Intent intent = null;
		switch (idParentActivity) {
		case Id_PreparationActivity:
			intent = new Intent(PageActivity.this, PreparationActivity.class);	
			break;
		case Id_PelerinageActivity:
			intent = new Intent(PageActivity.this, PelerinageActivity.class);	
			break;
		}

		startActivity(intent);
		finish();
		PageActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);		
		 }return super.onKeyDown(keyCode, event);
	}
	
	

}
