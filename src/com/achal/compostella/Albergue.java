package com.achal.compostella;

import android.os.Parcel;
import android.os.Parcelable;

public class Albergue implements Parcelable {
	
	
	private int id;
	private String nom, ville, nbplaces, prix, type, note, cuisine, lavelinge, sechelinge, internet, wifi/*, periode, toupdate*/;
	

	public Albergue(){}
	public Albergue(int id, String nom, String ville, String nbplaces,
			String prix, String type, String note, String cuisine,
			String lavelinge, String sechelinge, String internet, String wifi/*,
			String periode, String toupdate*/) {
		super();
		this.id = id;
		this.nom = nom;
		this.ville = ville;
		this.nbplaces = nbplaces;
		this.prix = prix;
		this.type = type;
		this.note = note;
		this.cuisine = cuisine;
		this.lavelinge = lavelinge;
		this.sechelinge = sechelinge;
		this.internet = internet;
		this.wifi = wifi;
//		this.periode = periode;
//		this.toupdate = toupdate;
	}


	public Albergue(String nom, String ville, String nbplaces, String prix,
			String type, String note, String cuisine, String lavelinge,
			String sechelinge, String internet, String wifi/*, String periode,
			String toupdate*/) {
		super();
		this.nom = nom;
		this.ville = ville;
		this.nbplaces = nbplaces;
		this.prix = prix;
		this.type = type;
		this.note = note;
		this.cuisine = cuisine;
		this.lavelinge = lavelinge;
		this.sechelinge = sechelinge;
		this.internet = internet;
		this.wifi = wifi;
//		this.periode = periode;
//		this.toupdate = toupdate;
	}

	public static final Parcelable.Creator<Albergue> CREATOR = new Parcelable.Creator<Albergue>()
			{
			    public Albergue createFromParcel(Parcel source)
			    {
			        return new Albergue(source);
			    }

			    public Albergue[] newArray(int size)
			    {
				return new Albergue[size];
			    }
			};
			
			
	public Albergue(Parcel in) {
		this.id = in.readInt();
		this.nom = in.readString();		
		this.ville = in.readString();
		this.nbplaces = in.readString();
		this.prix = in.readString();
		this.type = in.readString();
		this.note = in.readString();
		this.cuisine = in.readString();
		this.lavelinge = in.readString();
		this.sechelinge = in.readString();
		this.internet = in.readString();
		this.wifi = in.readString();
//		this.periode = in.readString();
//		this.toupdate = in.readString();
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(nom);
		dest.writeString(ville);
		dest.writeString(nbplaces);
		dest.writeString(prix);
		dest.writeString(type);
		dest.writeString(note);
		dest.writeString(cuisine);
		dest.writeString(lavelinge);
		dest.writeString(sechelinge);
		dest.writeString(internet);
		dest.writeString(wifi);
//		dest.writeString(periode);
//		dest.writeString(toupdate);	
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getVille() {
		return ville;
	}


	public void setVille(String ville) {
		this.ville = ville;
	}


	public String getNbplaces() {
		return nbplaces;
	}


	public void setNbplaces(String nbplaces) {
		this.nbplaces = nbplaces;
	}


	public String getPrix() {
		return prix;
	}


	public void setPrix(String prix) {
		this.prix = prix;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public String getCuisine() {
		return cuisine;
	}


	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}


	public String getLavelinge() {
		return lavelinge;
	}


	public void setLavelinge(String laveligne) {
		this.lavelinge = laveligne;
	}


	public String getSechelinge() {
		return sechelinge;
	}


	public void setSechelinge(String secheligne) {
		this.sechelinge = secheligne;
	}


	public String getInternet() {
		return internet;
	}


	public void setInternet(String internet) {
		this.internet = internet;
	}
	public String getWifi() {
		return wifi;
	}
	public void setWifi(String wifi) {
		this.wifi = wifi;
	}

//	public String getPeriode() {
//		return periode;
//	}
//
//
//	public void setPeriode(String periode) {
//		this.periode = periode;
//	}


//	public String getToupdate() {
//		return toupdate;
//	}
//
//
//	public void setToupdate(String toupdate) {
//		this.toupdate = toupdate;
//	}
	
}
