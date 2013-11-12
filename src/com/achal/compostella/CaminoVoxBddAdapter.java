package com.achal.compostella;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CaminoVoxBddAdapter {
	
	
	/***** ***** ouverture/fermeture de la bdd ***** *****/	
	private SQLiteDatabase bdd;
	private CaminoVoxBddOpen bddOpen; 
	private static final int BASE_VERSION=1;
	private static final String BASE_NOM = "caminovox.db";

	public CaminoVoxBddAdapter(Context ctx) {
		bddOpen = new CaminoVoxBddOpen(ctx, BASE_NOM, null, BASE_VERSION);
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
	
	
	/*************************************************** ***** lecture/ecriture de la table Albergue ***** *************************************************************/
	
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
	
	private static final int COLONNE_ALBERGUE_ID_ID = 0;
	private static final int COLONNE_ALBERGUE_NOM_ID =1;
	private static final int COLONNE_ALBERGUE_VILLE_ID =2;
	private static final int COLONNE_ALBERGUE_NBPLACES_ID =3;
	private static final int COLONNE_ALBERGUE_PRIX_ID =4;
	private static final int COLONNE_ALBERGUE_TYPE_ID =5;
	private static final int COLONNE_ALBERGUE_NOTE_ID =6;
	private static final int COLONNE_ALBERGUE_CUISINE_ID =7;
	private static final int COLONNE_ALBERGUE_LAVELINGE_ID =8;
	private static final int COLONNE_ALBERGUE_SECHELINGE_ID =9;
	private static final int COLONNE_ALBERGUE_INTERNET_ID =10;
	private static final int COLONNE_ALBERGUE_WIFI_ID =11;
	private static final int COLONNE_ALBERGUE_PERIODE_ID =12;
	private static final int COLONNE_ALBERGUE_TO_UPDATE_ID =13;
	
	
	


	public Albergue getAlbergue(int id){
		Cursor c = bdd.query(TABLE_ALBERGUES, new String[]{COLONNE_ALBERGUE_ID,COLONNE_ALBERGUE_NOM, COLONNE_ALBERGUE_VILLE, COLONNE_ALBERGUE_NBPLACES,
				COLONNE_ALBERGUE_PRIX,COLONNE_ALBERGUE_TYPE, COLONNE_ALBERGUE_NOTE,COLONNE_ALBERGUE_CUISINE, COLONNE_ALBERGUE_LAVELINGE, COLONNE_ALBERGUE_SECHELINGE,
				COLONNE_ALBERGUE_INTERNET, COLONNE_ALBERGUE_WIFI, COLONNE_ALBERGUE_PERIODE, COLONNE_ALBERGUE_TO_UPDATE}, COLONNE_ALBERGUE_ID+ " = "+id, null, null, null, null);
		return cursorToAlbergue(c);
	}

	
	public ArrayList<Albergue> getAllAlbergues(){
		Cursor c = bdd.query(TABLE_ALBERGUES, new String[]{COLONNE_ALBERGUE_ID,COLONNE_ALBERGUE_NOM, COLONNE_ALBERGUE_VILLE, COLONNE_ALBERGUE_NBPLACES,
				COLONNE_ALBERGUE_PRIX,COLONNE_ALBERGUE_TYPE, COLONNE_ALBERGUE_NOTE,COLONNE_ALBERGUE_CUISINE, COLONNE_ALBERGUE_LAVELINGE, COLONNE_ALBERGUE_SECHELINGE,
				COLONNE_ALBERGUE_INTERNET, COLONNE_ALBERGUE_WIFI, COLONNE_ALBERGUE_PERIODE, COLONNE_ALBERGUE_TO_UPDATE}, null, null, null, null, null);
		return cursorToAlbergues(c);
	}
	
	public int getLastAlbergueId(){
		Cursor c = bdd.query(TABLE_ALBERGUES, new String[]{COLONNE_ALBERGUE_ID}, null, null, null, null, null);
		c.moveToLast();
		int lastId = c.getInt(COLONNE_ALBERGUE_ID_ID);
		c.close();
		return lastId;
	}
	
	public long insertAlbergue(Albergue albergue){
		ContentValues values = new ContentValues();
		values.put(COLONNE_ALBERGUE_NOM, albergue.getNom());
		values.put(COLONNE_ALBERGUE_VILLE, albergue.getVille());
		values.put(COLONNE_ALBERGUE_NBPLACES, albergue.getNbplaces());
		values.put(COLONNE_ALBERGUE_PRIX, albergue.getPrix());
		values.put(COLONNE_ALBERGUE_TYPE, albergue.getType());
		values.put(COLONNE_ALBERGUE_NOTE, albergue.getNote());
		values.put(COLONNE_ALBERGUE_CUISINE, albergue.getCuisine());
		values.put(COLONNE_ALBERGUE_LAVELINGE, albergue.getLavelinge());
		values.put(COLONNE_ALBERGUE_SECHELINGE, albergue.getSechelinge());
		values.put(COLONNE_ALBERGUE_INTERNET, albergue.getInternet());
		values.put(COLONNE_ALBERGUE_WIFI, albergue.getWifi());
//		values.put(COLONNE_ALBERGUE_PERIODE, albergue.getPeriode());
//		values.put(COLONNE_ALBERGUE_TO_UPDATE, albergue.getToupdate());		
		return bdd.insert(TABLE_ALBERGUES, null, values);
	}
	
	/**
	 * Update information about an albergue 
	 */
	public long updateAlbergue(int id, Albergue albergue){
		ContentValues values = new ContentValues();
		values.put(COLONNE_ALBERGUE_NOM, albergue.getNom());
		values.put(COLONNE_ALBERGUE_VILLE, albergue.getVille());
		values.put(COLONNE_ALBERGUE_NBPLACES, albergue.getNbplaces());
		values.put(COLONNE_ALBERGUE_PRIX, albergue.getPrix());
		values.put(COLONNE_ALBERGUE_TYPE, albergue.getType());
		values.put(COLONNE_ALBERGUE_NOTE, albergue.getNote());
		values.put(COLONNE_ALBERGUE_CUISINE, albergue.getCuisine());
		values.put(COLONNE_ALBERGUE_LAVELINGE, albergue.getLavelinge());
		values.put(COLONNE_ALBERGUE_SECHELINGE, albergue.getSechelinge());
		values.put(COLONNE_ALBERGUE_INTERNET, albergue.getInternet());
		values.put(COLONNE_ALBERGUE_WIFI, albergue.getWifi());
//		values.put(COLONNE_ALBERGUE_PERIODE, albergue.getPeriode());
//		values.put(COLONNE_ALBERGUE_TO_UPDATE, albergue.getToupdate());		
		return bdd.update(TABLE_ALBERGUES, values, COLONNE_ALBERGUE_ID+" = "+id, null);
	}
	
	
	private Albergue cursorToAlbergue(Cursor c) {
		if(c.getCount()==0){
			return null;
		}else{
			Albergue retAlbergue = new Albergue();
			c.moveToFirst();
			retAlbergue.setId(c.getInt(COLONNE_ALBERGUE_ID_ID));			
			retAlbergue.setNom(c.getString(COLONNE_ALBERGUE_NOM_ID));
			retAlbergue.setVille(c.getString(COLONNE_ALBERGUE_VILLE_ID));
			retAlbergue.setNbplaces(c.getString(COLONNE_ALBERGUE_NBPLACES_ID));
			retAlbergue.setPrix(c.getString(COLONNE_ALBERGUE_PRIX_ID));
			retAlbergue.setType(c.getString(COLONNE_ALBERGUE_TYPE_ID));
			retAlbergue.setNote(c.getString(COLONNE_ALBERGUE_NOTE_ID));
			retAlbergue.setCuisine(c.getString(COLONNE_ALBERGUE_CUISINE_ID));
			retAlbergue.setLavelinge(c.getString(COLONNE_ALBERGUE_LAVELINGE_ID));
			retAlbergue.setSechelinge(c.getString(COLONNE_ALBERGUE_SECHELINGE_ID));
			retAlbergue.setInternet(c.getString(COLONNE_ALBERGUE_INTERNET_ID));
			retAlbergue.setWifi(c.getString(COLONNE_ALBERGUE_WIFI_ID));
//			retAlbergue.setPeriode(c.getString(COLONNE_ALBERGUE_PERIODE_ID));
//			retAlbergue.setToupdate(c.getString(COLONNE_ALBERGUE_TO_UPDATE_ID));
			c.close();
			return retAlbergue;
		}
	}
	
	private ArrayList<Albergue> cursorToAlbergues(Cursor c) {
		if(c.getCount()==0){
			return null;
		}else{
			ArrayList<Albergue> retAlbergues = new ArrayList<Albergue>();			
			c.moveToFirst();			
			do{
				Albergue retAlbergue = new Albergue();
				retAlbergue.setId(c.getInt(COLONNE_ALBERGUE_ID_ID));			
				retAlbergue.setNom(c.getString(COLONNE_ALBERGUE_NOM_ID));
				retAlbergue.setVille(c.getString(COLONNE_ALBERGUE_VILLE_ID));
				retAlbergue.setNbplaces(c.getString(COLONNE_ALBERGUE_NBPLACES_ID));
				retAlbergue.setPrix(c.getString(COLONNE_ALBERGUE_PRIX_ID));
				retAlbergue.setType(c.getString(COLONNE_ALBERGUE_TYPE_ID));
				retAlbergue.setNote(c.getString(COLONNE_ALBERGUE_NOTE_ID));
				retAlbergue.setCuisine(c.getString(COLONNE_ALBERGUE_CUISINE_ID));
				retAlbergue.setLavelinge(c.getString(COLONNE_ALBERGUE_LAVELINGE_ID));
				retAlbergue.setSechelinge(c.getString(COLONNE_ALBERGUE_SECHELINGE_ID));
				retAlbergue.setInternet(c.getString(COLONNE_ALBERGUE_INTERNET_ID));
				retAlbergue.setWifi(c.getString(COLONNE_ALBERGUE_WIFI_ID));
//				retAlbergue.setPeriode(c.getString(COLONNE_ALBERGUE_PERIODE_ID));
//				retAlbergue.setToupdate(c.getString(COLONNE_ALBERGUE_TO_UPDATE_ID));				
				retAlbergues.add(retAlbergue);
			}while(c.moveToNext());			
			c.close();
			return retAlbergues;
		}
	}
	

	/*************************************************** ***** lecture/ecriture de la table Commentaires ***** *************************************************************/
	
	private static final String TABLE_COMMENTAIRES= "commentaires";
	private static final String COLONNE_COMMENTAIRE_ID ="id";
	private static final String COLONNE_COMMENTAIRE_ID_ALBERGUE ="idalbergue";
	private static final String COLONNE_COMMENTAIRE_NOTE ="note";
	private static final String COLONNE_COMMENTAIRE_DATE ="date";
	private static final String COLONNE_COMMENTAIRE_ID_LANGUE ="idlangue";
	private static final String COLONNE_COMMENTAIRE_TO_UPDATE ="toupdate";
	
	private static final int COLONNE_COMMENTAIRE_ID_ID =0;
	private static final int COLONNE_COMMENTAIRE_ID_ALBERGUE_ID =1;
	private static final int COLONNE_COMMENTAIRE_NOTE_ID =2;
	private static final int COLONNE_COMMENTAIRE_DATE_ID =3;
	private static final int COLONNE_COMMENTAIRE_ID_LANGUE_ID =4;
	private static final int COLONNE_COMMENTAIRE_TO_UPDATE_ID =5;
	
	


	public Commentaire getCommentaire(int id){
		Cursor c = bdd.query(TABLE_COMMENTAIRES, new String[]{COLONNE_COMMENTAIRE_ID, COLONNE_COMMENTAIRE_ID_ALBERGUE, COLONNE_COMMENTAIRE_NOTE, COLONNE_COMMENTAIRE_DATE, COLONNE_COMMENTAIRE_ID_LANGUE, COLONNE_COMMENTAIRE_TO_UPDATE}, COLONNE_COMMENTAIRE_ID+ " = "+id, null, null, null, null);
		return cursorToCommentaire(c);
	}

	
	public ArrayList<Commentaire> getAllCommentaires(){
		Cursor c = bdd.query(TABLE_COMMENTAIRES, new String[]{COLONNE_COMMENTAIRE_ID, COLONNE_COMMENTAIRE_ID_ALBERGUE, COLONNE_COMMENTAIRE_NOTE, COLONNE_COMMENTAIRE_DATE, COLONNE_COMMENTAIRE_ID_LANGUE, COLONNE_COMMENTAIRE_TO_UPDATE}, null, null, null, null, null);
		return cursorToCommentaires(c);
	}
	
	public int getLastCommentaireId(){
		Cursor c = bdd.query(TABLE_COMMENTAIRES, new String[]{COLONNE_COMMENTAIRE_ID}, null, null, null, null, null);
		c.moveToLast();
		int lastId = c.getInt(COLONNE_COMMENTAIRE_ID_ID);
		c.close();
		return lastId;
	}
	
	public long insertCommentaire(Commentaire commentaire){
		ContentValues values = new ContentValues();
		values.put(COLONNE_COMMENTAIRE_ID_ALBERGUE, commentaire.getIdAlbergue());
		values.put(COLONNE_COMMENTAIRE_ID_LANGUE, commentaire.getIdLangue());
		values.put(COLONNE_COMMENTAIRE_NOTE, commentaire.getNote());
		values.put(COLONNE_COMMENTAIRE_DATE, commentaire.getDate());
		values.put(COLONNE_COMMENTAIRE_TO_UPDATE, commentaire.getToUpdate());
		return bdd.insert(TABLE_COMMENTAIRES, null, values);
	}
	
	/**
	 * Update information about a commentaire 
	 */
	public long updateCommentaire(int id, Commentaire commentaire){
		ContentValues values = new ContentValues();
		values.put(COLONNE_COMMENTAIRE_ID_ALBERGUE, commentaire.getIdAlbergue());
		values.put(COLONNE_COMMENTAIRE_ID_LANGUE, commentaire.getIdLangue());
		values.put(COLONNE_COMMENTAIRE_NOTE, commentaire.getNote());
		values.put(COLONNE_COMMENTAIRE_DATE, commentaire.getDate());
		values.put(COLONNE_COMMENTAIRE_TO_UPDATE, commentaire.getToUpdate());
		return bdd.update(TABLE_COMMENTAIRES, values, COLONNE_COMMENTAIRE_ID+" = "+id, null);
	}
	
	
	private Commentaire cursorToCommentaire(Cursor c) {
		if(c.getCount()==0){
			return null;
		}else{
			Commentaire retCommentaire= new Commentaire();
			c.moveToFirst();
			retCommentaire.setIdCommentaire(c.getInt(COLONNE_COMMENTAIRE_ID_ID));
			retCommentaire.setIdAlbergue(c.getInt(COLONNE_COMMENTAIRE_ID_ALBERGUE_ID));
			retCommentaire.setIdLangue(c.getInt(COLONNE_COMMENTAIRE_ID_LANGUE_ID));
			retCommentaire.setNote(c.getFloat(COLONNE_COMMENTAIRE_NOTE_ID));
			retCommentaire.setDate(c.getString(COLONNE_COMMENTAIRE_DATE_ID));
			retCommentaire.setToUpdate(c.getInt(COLONNE_COMMENTAIRE_TO_UPDATE_ID));
			c.close();
			return retCommentaire;
		}
	}
	
	private ArrayList<Commentaire> cursorToCommentaires(Cursor c) {
		if(c.getCount()==0){
			return null;
		}else{
			ArrayList<Commentaire> retCommentaires = new ArrayList<Commentaire>();			
			c.moveToFirst();			
			do{
				Commentaire retCommentaire= new Commentaire();
				retCommentaire.setIdCommentaire(c.getInt(COLONNE_COMMENTAIRE_ID_ID));
				retCommentaire.setIdAlbergue(c.getInt(COLONNE_COMMENTAIRE_ID_ALBERGUE_ID));
				retCommentaire.setIdLangue(c.getInt(COLONNE_COMMENTAIRE_ID_LANGUE_ID));
				retCommentaire.setNote(c.getFloat(COLONNE_COMMENTAIRE_NOTE_ID));
				retCommentaire.setDate(c.getString(COLONNE_COMMENTAIRE_DATE_ID));
				retCommentaire.setToUpdate(c.getInt(COLONNE_COMMENTAIRE_TO_UPDATE_ID));
				retCommentaires.add(retCommentaire);
			}while(c.moveToNext());			
			c.close();
			return retCommentaires;
		}
	}
	
	
}
