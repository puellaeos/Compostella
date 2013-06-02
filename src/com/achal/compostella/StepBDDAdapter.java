package com.achal.compostella;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Class qui contient l'ensemble des méthodes de l'applicatione effectuant des requetes en BDD
 * @author Aurore
 *
 */
public class StepBDDAdapter {

	
	/***** ***** ouverture/fermeture de la bdd ***** *****/	
	private SQLiteDatabase bdd;
	private StepBDDOpen bddOpen; 
	private static final int BASE_VERSION=1;
	private static final String BASE_NOM = "step.db";

	public StepBDDAdapter(Context ctx) {
		bddOpen = new StepBDDOpen(ctx, BASE_NOM, null, BASE_VERSION);
	}
	
	/**
	 * ouvre la base de données en ecritute	
	 */
	public SQLiteDatabase open(){
		bdd= bddOpen.getWritableDatabase();
		return bdd;
	}
	/**
	 * ferme la base de données
	 */
	public void close(){
		bdd.close();
		
	}
	/**
	 * retourne la base de donnees
	 * @return
	 */
	public SQLiteDatabase getBaseDonnees(){
		return bdd;
	}
	
	
	/***** ***** lecture/ecriture de la bdd ***** *****/

	private static final String TABLE_STEP= "step";
	private static final String COLONNE_ID ="id";
	private static final String COLONNE_DATE ="date";
	private static final String COLONNE_STARTSTEP ="startstep";
	private static final String COLONNE_ENDSTEP ="endstep";
	private static final String COLONNE_COMMENT ="comment";
	private static final String COLONNE_DISTANCE ="distance";
	
	private static final int COLONNE_ID_ID =0;
	private static final int COLONNE_DATE_ID =1;
	private static final int COLONNE_STARTSTEP_ID =2;
	private static final int COLONNE_ENDSTEP_ID =3;
	private static final int COLONNE_COMMENT_ID =4;
	private static final int COLONNE_DISTANCE_ID =5;
	
	

	public Step getStep(int id){
		Cursor c = bdd.query(TABLE_STEP, new String[]{COLONNE_ID,COLONNE_DATE, COLONNE_STARTSTEP, COLONNE_ENDSTEP, COLONNE_COMMENT,COLONNE_DISTANCE}, COLONNE_ID+ " = "+id, null, null, null, null);
		return cursorToStep(c);
	}

	
	public ArrayList<Step> getAllSteps(){
		Cursor c = bdd.query(TABLE_STEP, new String[]{COLONNE_ID,COLONNE_DATE, COLONNE_STARTSTEP, COLONNE_ENDSTEP, COLONNE_COMMENT,COLONNE_DISTANCE}, null, null, null, null, null);
		return cursorToSteps(c);
	}
	
	public int getLastId(){
		Cursor c = bdd.query(TABLE_STEP, new String[]{COLONNE_ID}, null, null, null, null, null);
		c.moveToLast();
		int lastId = c.getInt(COLONNE_ID_ID);
		c.close();
		return lastId;
	}
	
	public long insertStep(Step step){
		ContentValues values = new ContentValues();
		values.put(COLONNE_DATE, step.getDate());
		values.put(COLONNE_STARTSTEP, step.getStart());
		values.put(COLONNE_ENDSTEP, step.getEnd());
		values.put(COLONNE_COMMENT, step.getComment());
		values.put(COLONNE_DISTANCE, step.getDistance());
		return bdd.insert(TABLE_STEP, null, values);
	}
	
	/**
	 * Update information about a step 
	 * @param id, identification number of step
	 * @param step, step object to be updated
	 * @return
	 */
	public long updateStep(int id, Step step){
		ContentValues values = new ContentValues();
		values.put(COLONNE_DATE, step.getDate());
		values.put(COLONNE_STARTSTEP, step.getStart());
		values.put(COLONNE_ENDSTEP, step.getEnd());
		values.put(COLONNE_COMMENT, step.getComment());
		values.put(COLONNE_DISTANCE, step.getDistance());
		return bdd.update(TABLE_STEP, values, COLONNE_ID+" = "+id, null);
	}
	
	
	private Step cursorToStep(Cursor c) {
		if(c.getCount()==0){
			return null;
		}else{
			Step retStep= new Step();
			c.moveToFirst();
			retStep.setId(c.getInt(COLONNE_ID_ID));
			retStep.setDate(c.getString(COLONNE_DATE_ID));
			retStep.setStart(c.getString(COLONNE_STARTSTEP_ID));
			retStep.setEnd(c.getString(COLONNE_ENDSTEP_ID));
			retStep.setComment(c.getString(COLONNE_COMMENT_ID));
			retStep.setDistance(c.getString(COLONNE_DISTANCE_ID));
			c.close();
			return retStep;
		}
	}
	
	private ArrayList<Step> cursorToSteps(Cursor c) {
		if(c.getCount()==0){
			return null;
		}else{
			ArrayList<Step> retSteps = new ArrayList<Step>();			
			c.moveToFirst();			
			do{
				Step retStep= new Step();
				retStep.setId(c.getInt(COLONNE_ID_ID));
				retStep.setDate(c.getString(COLONNE_DATE_ID));
				retStep.setStart(c.getString(COLONNE_STARTSTEP_ID));
				retStep.setEnd(c.getString(COLONNE_ENDSTEP_ID));
				retStep.setComment(c.getString(COLONNE_COMMENT_ID));
				retStep.setDistance(c.getString(COLONNE_DISTANCE_ID));
				retSteps.add(retStep);
			}while(c.moveToNext());			
			c.close();
			return retSteps;
		}
	}
	
	
	
	
	
	

}
