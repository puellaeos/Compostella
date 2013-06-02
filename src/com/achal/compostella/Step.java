package com.achal.compostella;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Class qui définit l'objet Step avec les différents constructeurs et getter/setter.
 * L'objet contient un identifiant (id), une date (date), un lieu de départ (start), un lieu d'arrivée (end), un commentaire (comment), une distance (distance)
 * @author Aurore
 *
 */
public class Step implements Parcelable{
	
	
	private int id ;
	private String date , start, end, comment, distance ;
	
	public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>()
			{
			    public Step createFromParcel(Parcel source)
			    {
			        return new Step(source);
			    }

			    public Step[] newArray(int size)
			    {
				return new Step[size];
			    }
			};
	

	public Step(Parcel in) {
		this.id = in.readInt();
		this.date=in.readString();
		this.start = in.readString();
		this.end = in.readString();
		this.comment = in.readString();
		this.distance = in.readString();
	}

	public Step(){};
	
	public Step(int id,String date, String start, String end, String comment, String distance) {
		super();
		this.id = id;
		this.date=date;
		this.start = start ;
		this.end = end ;
		this.comment = comment ;
		this.distance = distance ;
	}
	
	public Step(String date, String start, String end, String comment, String distance) {
		super();
		this.date=date;
		this.start = start ;
		this.end = end ;
		this.comment = comment ;
		this.distance = distance ;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getStart() {
		return start;
	}


	public void setStart(String start) {
		this.start = start;
	}


	public String getEnd() {
		return end;
	}


	public void setEnd(String end) {
		this.end = end;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public String getDistance() {
		return distance;
	}


	public void setDistance(String distance) {
		this.distance = distance;
	}


	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(date);
		dest.writeString(start);
		dest.writeString(end);
		dest.writeString(comment);
		dest.writeString(distance);
	}

}
