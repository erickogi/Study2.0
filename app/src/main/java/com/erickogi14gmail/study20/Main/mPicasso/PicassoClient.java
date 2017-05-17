package com.erickogi14gmail.study20.Main.mPicasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.erickogi14gmail.study20.R;
import com.squareup.picasso.Picasso;

/**
 * Created by kimani kogi on 4/22/2017.
 */

public class PicassoClient {

    public static void LoadImage(final Context c, String url, ImageView img, final TextView textView) {


        if (url != null && url.length() > 0) {
            try {
                Picasso.with(c).load(url).placeholder(R.drawable.news).into(img);


                BitmapDrawable p = (BitmapDrawable) img.getDrawable();
                Bitmap bitmap = p.getBitmap();

                Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        try {
                            int bgColor = palette.getMutedColor(c.getResources().getColor(android.R.color.black));
                            textView.setBackgroundColor(bgColor);
                        } catch (Exception n) {

                        }

                    }
                });
            } catch (Exception b) {
                Picasso.with(c).load(R.drawable.news).into(img);
            }


        } else {
            img.setVisibility(View.INVISIBLE);

        }
    }

    public static void LoadImageWebView(final Context c, String url, ImageView img, final Toolbar toolbar) {


        if (url != null && url.length() > 0) {
            try {
                Picasso.with(c).load(url).placeholder(R.drawable.news).into(img);

                BitmapDrawable p = (BitmapDrawable) img.getDrawable();
                Bitmap bitmap = p.getBitmap();

                Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        try {
                            int bgColor = palette.getMutedColor(c.getResources().getColor(android.R.color.black));
                            toolbar.setBackgroundColor(bgColor);


                        } catch (Exception n) {

                        }

                    }
                });
            } catch (Exception b) {
                Picasso.with(c).load(R.drawable.news).into(img);
            }


        } else {
            img.setVisibility(View.INVISIBLE);

        }
    }
}
