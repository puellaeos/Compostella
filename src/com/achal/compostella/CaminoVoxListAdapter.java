package com.achal.compostella;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class CaminoVoxListAdapter extends BaseAdapter implements Filterable {

	private LayoutInflater inflater;
	private Context context;
	private ArrayList<String> listItem, originListItem;
	private TextView tvItem;
	private ListFilter listFilter;
	
	
	public CaminoVoxListAdapter(Context context,  ArrayList<String> listItem) {
		super();
		this.context = context;
		this.listItem = listItem;
		this.originListItem = listItem;
		this.inflater = LayoutInflater.from(context); 
	}
	
	public View getView(int position, View convertView, ViewGroup parentView) {
		View v=null;
		if(convertView!=null)
			v= convertView;
		else
			v= inflater.inflate(R.layout.layout_caminovox_list_item, parentView, false);
	
		tvItem = (TextView) v.findViewById(R.id.tvCaminovoxListItemName);
		Typeface font2 = Typeface.createFromAsset(context.getAssets(), "font2.ttf");
		tvItem.setTypeface(font2);		
		tvItem.setText(listItem.get(position));		  
		return v;
	}

	public int getCount() {		
		return listItem.size();
	}

	public Object getItem(int arg0) {
		return listItem.get(arg0);
	}

	public long getItemId(int arg0) { 
		return arg0;
	}


	/*
	 * We create our filter	
	 */
 
	public Filter getFilter() {
		if (listFilter == null)
			listFilter = new ListFilter();
		return listFilter;
	}

	
	private class ListFilter extends Filter{

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			// We implement here the filter logic
			if (constraint == null || constraint.length() == 0) {
				// No filter implemented we return all the list
				results.values = originListItem;
				results.count = originListItem.size();
			}
			else {
				// We perform filtering operation
				ArrayList<String> nList = new ArrayList<String>();

				for (String s : listItem) {
					if (s.toUpperCase().contains(constraint.toString().toUpperCase()))
						nList.add(s);
				}

				results.values = nList;
				results.count = nList.size();

			}
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			// Now we have to inform the adapter about the new list filtered
						if (results.count == 0)
							notifyDataSetInvalidated();
						else {
							listItem = (ArrayList<String>) results.values;
							notifyDataSetChanged();
						} 
		}
		
		
	}
	
	
	
}
