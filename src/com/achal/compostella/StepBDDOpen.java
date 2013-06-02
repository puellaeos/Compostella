package com.achal.compostella;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class permettant l'ouverture de la BDD ou sa création si la BDD n'existe pas. 
 * @author Aurore
 *
 */
public class StepBDDOpen extends SQLiteOpenHelper{
	
	private static final String TABLE_STEP= "step";
	
	private static final String COLONNE_ID ="id";
	private static final String COLONNE_DATE ="date";
	private static final String COLONNE_STARTSTEP ="startstep";
	private static final String COLONNE_ENDSTEP ="endstep";
	private static final String COLONNE_COMMENT ="comment";
	private static final String COLONNE_DISTANCE ="distance";
	
	private static final String REQUEST_CREATE_TABLE = "create table " +TABLE_STEP+" ( "
	+COLONNE_ID+" integer primary key autoincrement, "+COLONNE_DATE+" text not null, "+COLONNE_STARTSTEP+" text not null, "
	+COLONNE_ENDSTEP+" text not null, "+COLONNE_COMMENT+" text not null, "+COLONNE_DISTANCE+" text not null);" ;
	
	

	public StepBDDOpen(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version); 
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(REQUEST_CREATE_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE "+TABLE_STEP+" ;");
		
	}
	


	
	

}
