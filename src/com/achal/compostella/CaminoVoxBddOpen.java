package com.achal.compostella;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CaminoVoxBddOpen extends SQLiteOpenHelper {
	
	/**J'ai fait une modif !! -- à supprimer après comit test**/
	
	
	private static final String TABLE_ALBERGUES= "albergues";
	private static final String COLONNE_ALBERGUE_ID ="id";
	private static final String COLONNE_ALBERGUE_NOM ="nom";
	private static final String COLONNE_ALBERGUE_VILLE ="ville";
	private static final String COLONNE_ALBERGUE_NBPLACES ="nbplaces";
	private static final String COLONNE_ALBERGUE_PRIX ="prix";
	private static final String COLONNE_ALBERGUE_TYPE ="type";
	private static final String COLONNE_ALBERGUE_NOTE ="note";
	private static final String COLONNE_ALBERGUE_CUISINE ="cuisine";
	private static final String COLONNE_ALBERGUE_LAVELINGE ="lavelinge";
	private static final String COLONNE_ALBERGUE_SECHELINGE ="secheligne";
	private static final String COLONNE_ALBERGUE_INTERNET ="internet";
	private static final String COLONNE_ALBERGUE_WIFI ="wifi";
	private static final String COLONNE_ALBERGUE_PERIODE ="periode";
	private static final String COLONNE_ALBERGUE_TO_UPDATE ="toupdate";
	
	
	private static final String TABLE_COMMENTAIRES= "commentaires";
	private static final String COLONNE_COMMENTAIRE_ID ="id";
	private static final String COLONNE_COMMENTAIRE_ID_ALBERGUE ="idalbergue";
	private static final String COLONNE_COMMENTAIRE_NOTE ="note";
	private static final String COLONNE_COMMENTAIRE_DATE ="date";
	private static final String COLONNE_COMMENTAIRE_ID_LANGUE ="idlangue";
	private static final String COLONNE_COMMENTAIRE_TO_UPDATE ="toupdate";
	
	
	private static final String REQUETE_CREATE_ALBERGUES = "create table " +TABLE_ALBERGUES+" ( "
	+COLONNE_ALBERGUE_ID+" integer primary key autoincrement, "+COLONNE_ALBERGUE_NOM+" text not null, "+COLONNE_ALBERGUE_VILLE+" text not null, "
	+COLONNE_ALBERGUE_NBPLACES+" text not null, "+COLONNE_ALBERGUE_PRIX+" text not null, "+COLONNE_ALBERGUE_TYPE+" text not null, "+
	COLONNE_ALBERGUE_NOTE+" text not null, "+COLONNE_ALBERGUE_CUISINE+" text not null, "+COLONNE_ALBERGUE_LAVELINGE+" text not null, "+
	COLONNE_ALBERGUE_SECHELINGE+" text not null, "+COLONNE_ALBERGUE_INTERNET+" text not null, "+COLONNE_ALBERGUE_WIFI+" text not null, "+
	COLONNE_ALBERGUE_PERIODE+" text not null, "+COLONNE_ALBERGUE_TO_UPDATE+" text not null);" ;
			
			
	
	private static final String REQUETE_CREATE_COMMENTAIRES = "create table " +TABLE_COMMENTAIRES+" ( "
	+COLONNE_COMMENTAIRE_ID+" integer primary key autoincrement, "+COLONNE_COMMENTAIRE_ID_ALBERGUE+" integer not null, "+COLONNE_COMMENTAIRE_NOTE+" float not null, "
	+COLONNE_COMMENTAIRE_DATE+" text not null, "+COLONNE_COMMENTAIRE_ID_LANGUE+" integer not null, "+COLONNE_COMMENTAIRE_TO_UPDATE+" integer not null);" ;
			
	

	public CaminoVoxBddOpen(Context context, String name,CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(REQUETE_CREATE_ALBERGUES);
		db.execSQL(REQUETE_CREATE_COMMENTAIRES);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE "+TABLE_ALBERGUES+" ;");
		db.execSQL("DROP TABLE "+TABLE_COMMENTAIRES+" ;");
	}

}
