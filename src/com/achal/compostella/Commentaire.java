package com.achal.compostella;

import android.os.Parcel;
import android.os.Parcelable;

public class Commentaire implements Parcelable{
	
	private int idCommentaire, idAlbergue, idLangue, toUpdate;
	private String date;
	private float note;
	
	public Commentaire() {}
	
	public Commentaire(int idAlbergue, int idLangue, int toUpdate, String date,	float note) {
		super();
		this.idAlbergue = idAlbergue;
		this.idLangue = idLangue;
		this.toUpdate = toUpdate;
		this.date = date;
		this.note = note;
	}
	
	public Commentaire(int idCommentaire, int idAlbergue, int idLangue,int toUpdate, String date, float note) {
		super();
		this.idCommentaire = idCommentaire;
		this.idAlbergue = idAlbergue;
		this.idLangue = idLangue;
		this.toUpdate = toUpdate;
		this.date = date;
		this.note = note;
	}


	public Commentaire (Parcel in) {
		this.idCommentaire = in.readInt();
		this.idAlbergue = in.readInt();
		this.idLangue = in.readInt();
		this.toUpdate = in.readInt();
		this.date = in.readString();
		this.note = in.readFloat();
	}

	public int describeContents() {
		return 0;
	}
	
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(idCommentaire);
		dest.writeInt(idAlbergue);
		dest.writeInt(idLangue);
		dest.writeInt(toUpdate);
		dest.writeString(date);
		dest.writeFloat(note);		
	}

	public int getIdCommentaire() {
		return idCommentaire;
	}

	public void setIdCommentaire(int idCommentaire) {
		this.idCommentaire = idCommentaire;
	}

	public int getIdAlbergue() {
		return idAlbergue;
	}

	public void setIdAlbergue(int idAlbergue) {
		this.idAlbergue = idAlbergue;
	}

	public int getIdLangue() {
		return idLangue;
	}

	public void setIdLangue(int idLangue) {
		this.idLangue = idLangue;
	}

	public int getToUpdate() {
		return toUpdate;
	}

	public void setToUpdate(int toUpdate) {
		this.toUpdate = toUpdate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public float getNote() {
		return note;
	}

	public void setNote(float note) {
		this.note = note;
	}
	

	
	
}
