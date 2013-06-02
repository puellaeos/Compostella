package com.achal.compostella;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
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
	private Button bPreparation, bPelerinage, bOutils, bJournal ;
	private TextView tvTitreView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        setContentView(R.layout.layout_compostellamenu_activity);
        bPreparation = (Button) findViewById(R.id.ButtonPreparation);
        bPelerinage = (Button) findViewById(R.id.ButtonPelerinage);
        bOutils = (Button) findViewById(R.id.ButtonOutils);
        bJournal = (Button) findViewById(R.id.ButtonJournal);
        tvTitreView = (TextView) findViewById(R.id.textViewTitreCompostellaMenu);
        
        
        
        Typeface font2 = Typeface.createFromAsset(this.getAssets(), "font2.ttf");
        bPreparation.setTypeface(font2);
        bPelerinage.setTypeface(font2);
        bOutils.setTypeface(font2);
        bJournal.setTypeface(font2); 
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
        
        bJournal.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(CompostelaMenuActivity.this, JournalActivity.class);
				startActivity(intent);
				finish();
				CompostelaMenuActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
			}
		});
        
        
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