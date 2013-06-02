package com.achal.compostella;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Class qui définit la vue Journal et qui affiche une gallerie de photos.
 * Cette vue est accessible depuis la vue CompostelleMenu
 * @author Aurore
 *
 */
public class JournalActivity extends Activity {
	
	private TextView tvTitreView;
	private Gallery gImages;
	private ImageView imImage;
	private int [] images ={ R.drawable.im101c, R.drawable.im102c, R.drawable.im103c, R.drawable.im104c, R.drawable.im105c,
			R.drawable.im106c, R.drawable.im108c, R.drawable.im109c, R.drawable.im110c, R.drawable.im111c};
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_journal_activity);
        
        tvTitreView = (TextView) findViewById(R.id.tvTitreJournalActivity);
        gImages = (Gallery) findViewById(R.id.gJournalActivity);
        imImage = (ImageView) findViewById(R.id.imJournalActivity);
        Typeface font = Typeface.createFromAsset(getAssets(), "font2.ttf");
        tvTitreView.setTypeface(font);
        
        MyAdapter GalleryAdapter = new MyAdapter(this, images);
        gImages.setAdapter(GalleryAdapter);
        
        gImages.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,int id, long position) {
				 imImage.setImageResource(images[id]);
			}

			public void onNothingSelected(AdapterView<?> arg0) { }
		});
        
       
        
    }
    /**
     * Class permettant de personnaliser l'objet gallery
     * @author Aurore
     *
     */
    class MyAdapter extends BaseAdapter{

            private Context mContext;
        	
        	int[] images;
            
        	public MyAdapter(Context c, int[] images) {
                mContext = c;
        		this.images = images;
            }

            public int getCount() {
                return images.length;
            }

            public Object getItem(int position) {
                return position;
            }

            public long getItemId(int position) {
                return position;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
            	ImageView i = new ImageView(mContext);
                i.setImageResource(images[position]);
                
                Display display = getWindowManager().getDefaultDisplay();
                i.setLayoutParams(new Gallery.LayoutParams(display.getWidth()/2, display.getHeight()/5));//new Gallery.LayoutParams(150, 100));
                i.setScaleType(ImageView.ScaleType.FIT_XY);
                i.setBackgroundResource(R.color.MarronClair);
                return i;
            }
        }
    
    /**
     * methode permettant de gérer le bouton retour du mobile
     */
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	 if (keyCode == KeyEvent.KEYCODE_BACK) {
    		Intent intent = new Intent(JournalActivity.this,CompostelaMenuActivity.class);
	    	startActivity(intent);
			finish();
			JournalActivity.this.overridePendingTransition(R.anim.fondu_in, R.anim.fondu_out);
    	 }return super.onKeyDown(keyCode, event);
	}
    
}