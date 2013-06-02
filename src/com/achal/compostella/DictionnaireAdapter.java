package com.achal.compostella;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Class qui définit la vue du dictionnaire en dynamique
 */
public class DictionnaireAdapter extends BaseAdapter {
	

	private LayoutInflater inflater;
	private Context context;
	private ArrayList<HashMap<String,String>> listMots;
	private TextView tvMot1, tvMot2;
	
	
	
	public DictionnaireAdapter(Context context, ArrayList<HashMap<String, String>> listMots) {
		super();
		this.context = context;
		this.listMots = listMots;
		this.inflater = LayoutInflater.from(context);
		Log.i("DictionnaireAdapter", "constructer");;
	}
	public View getView(int position, View convertView, ViewGroup parentView) {
		Log.i("DictionnaireAdapter", "getview");
		
		View v=null;
		if(convertView!=null)
			v= convertView;
		else
			v= inflater.inflate(R.layout.layout_dictionnaire_item, parentView, false);
			
		
		
		tvMot1 = (TextView) v.findViewById(R.id.tvLangue1ItemDictionnaire);
		tvMot2 = (TextView) v.findViewById(R.id.tvLangue2ItemDictionnaire);
		
		Typeface font2 = Typeface.createFromAsset(context.getAssets(), "font2.ttf");
		tvMot1.setTypeface(font2);
		tvMot2.setTypeface(font2);
		
		
		tvMot1.setText(listMots.get(position).get("motL1"));
		tvMot2.setText(listMots.get(position).get("motL2"));
		
		Log.i("DictionnaireAdapter", "getview, mot1="+listMots.get(position).get(1));
		
		return v;
	}
	public int getCount() {
		return listMots.size();
	}
	public Object getItem(int arg0) {
		// pas besoin de selection d'un item
		return listMots.get(arg0);
	}
	public long getItemId(int arg0) {
		// pas besoin de selection d'un item
		return arg0;
	}
	
	

}
