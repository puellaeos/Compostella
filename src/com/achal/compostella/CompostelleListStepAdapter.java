package com.achal.compostella;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Class qui définit dynamiquement le layout de CompostelleListStepActivity
 * @author Aurore
 *
 */
public class CompostelleListStepAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private Context context;
	private ArrayList<Step> listSteps;
	private Step step;
	private TextView tvItemId, tvItemDate , tvItemStart, tvItemEnd, tvItemComment, tvItemDistance ;

	
	public CompostelleListStepAdapter(Context ctx, ArrayList<Step> steps) {
		this.context= ctx;
		this.listSteps = steps;
		this.inflater = LayoutInflater.from(context);
		Log.i("CompostelleListStepAdapter", "constructer");
	}
	
	
	public int getCount() {
		return listSteps.size();
	}

	public Step getItem(int arg0) {		
		return listSteps.get(arg0);
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(int position, View convertView, ViewGroup parentView) {
		Log.i("CompostelleListStepAdapter", "position"+position);
		
		View v = null;
		if(convertView!=null)
			v = convertView;
		else
			v = inflater.inflate(R.layout.layout_compostelle_liststep_item, parentView, false);
			
		step= new Step();
		step=listSteps.get(position);
		tvItemDate = (TextView) v.findViewById(R.id.tvDateItemListStep);
		tvItemEnd = (TextView) v.findViewById(R.id.tvEndItemListStep);
		tvItemStart = (TextView) v.findViewById(R.id.tvStartItemListStep);
		tvItemDistance = (TextView) v.findViewById(R.id.tvDistanceItemListStep);
				
		Typeface font2 = Typeface.createFromAsset(this.context.getAssets(), "font2.ttf");
		tvItemDate.setTypeface(font2);
		tvItemEnd.setTypeface(font2);
		tvItemStart.setTypeface(font2);
		tvItemDistance.setTypeface(font2);
		
		
		tvItemDate.setText(step.getDate());
		tvItemEnd.setText(step.getEnd());
		tvItemStart.setText(step.getStart());
		tvItemDistance.setText(String.valueOf(step.getDistance()));

		Log.i("CompostelleListStepAdapter", "tvItemDate="+step.getDate());
		
		
		return v;
	}

}
