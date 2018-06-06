package com.vension.app.utils.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.vension.frame.imageloader.ImageLoader;


public class GlideImageLoader implements ImageLoader {

    private Context mContext;
    public static GlideCircleTransform circleTransform;

    public GlideImageLoader(Context context) {
        this.mContext = context;
        circleTransform = new GlideCircleTransform(mContext);
    }


    @Override
    public void load(ImageView imageView, Object imageUrl) {
        Glide.with(mContext)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade()) // 动画渐变加载
                .into(imageView);
    }

    @Override
    public void load(ImageView imageView, Object imageUrl, int defaultImage) {
        Glide.with(mContext)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade()) // 动画渐变加载
                .apply(new RequestOptions().placeholder(defaultImage))
                .into(imageView);
    }

    @Override
    public void load(ImageView imageView, Object imageUrl, Object transformation) {
        Glide.with(mContext)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade()) // 动画渐变加载
                .apply(new RequestOptions().transform((BitmapTransformation) transformation))
                .into(imageView);
    }

    @Override
    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        Glide.with(context)
                .load(uri)
                .apply(new RequestOptions().centerCrop().placeholder(placeholder).override(resize,resize))
                .into(imageView);
    }

    @Override
    public void loadAnimatedGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        Glide.with(context)
                .asGif()
                .load(uri)
                .apply(new RequestOptions().centerCrop().placeholder(placeholder).override(resize,resize))
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        Glide.with(context)
                .load(uri)
                .apply(new RequestOptions().centerCrop().override(resizeX,resizeY))
                .into(imageView);
    }

    @Override
    public void loadAnimatedGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        Glide.with(context)
                .asBitmap()
                .apply(new RequestOptions().centerCrop().override(resizeX,resizeY))
                .load(uri)
                .into(imageView);
    }

    @Override
    public boolean supportAnimatedGif() {
        return false;
    }


}
