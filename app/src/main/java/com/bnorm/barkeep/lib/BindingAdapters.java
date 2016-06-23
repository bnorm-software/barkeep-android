package com.bnorm.barkeep.lib;

import java.util.concurrent.atomic.AtomicInteger;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import com.bnorm.barkeep.R;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

public class BindingAdapters {

    private static Integer[] thumbIds = {
            R.drawable.cocktail_0, R.drawable.cocktail_1, R.drawable.cocktail_2, R.drawable.cocktail_3,
            R.drawable.cocktail_4, R.drawable.cocktail_5, R.drawable.cocktail_6, R.drawable.cocktail_7,
            R.drawable.cocktail_8,
            };

    private static final AtomicInteger position = new AtomicInteger();

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String url) {
        RequestManager with = Glide.with(view.getContext());
        DrawableTypeRequest<?> load;
        if (url != null) {
            load = with.load(url);
        } else {
            load = with.load(thumbIds[position.getAndIncrement() % thumbIds.length]);
        }
        //        int size = view.getWidth();
        //        if (size > 0) {
        //            load.override(size, size);
        //        }
        load.centerCrop().into(view);
    }
}
