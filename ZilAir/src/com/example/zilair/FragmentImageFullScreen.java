package com.example.zilair;

import com.example.util.TouchImageView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FragmentImageFullScreen extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Bundle bundle = getArguments();
	    int fleet_number = bundle.getInt("fleet_number");
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_image_full_screen,
				null);
		
		TouchImageView image_fullscreen = (TouchImageView)root.findViewById(R.id.image_fullscreen);
		//ImageView image = new ImageView(getActivity());
		Bitmap icon=null;
		try {
			if(fleet_number==0)
			{
				icon = BitmapFactory.decodeResource(this.getResources(),
						R.drawable.zilairexp_4);
			}
			else if(fleet_number==1)
			{
				icon = BitmapFactory.decodeResource(this.getResources(),
						R.drawable.zilairexp_6);
			}
			else if(fleet_number==2)
			{
				icon = BitmapFactory.decodeResource(this.getResources(),
						R.drawable.zilairexp_4);
			}
			else if(fleet_number==3)
			{
				icon = BitmapFactory.decodeResource(this.getResources(),
						R.drawable.locationmap);
			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		image_fullscreen.setImageBitmap(icon);
        //image_fullscreen.addView(image);		
		return root;

	}

}
