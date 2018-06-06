package com.vension.frame.imageloader;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/3/30 16:38
 * 描  述：
 * ========================================================
 */

public class VImage implements ImageLoader {

    private static ImageLoader imageLoader;
    private static VImage vImage;

    public static void init(ImageLoader loader) {
        imageLoader = loader;
    }

    public static VImage getInstance() {
        if (imageLoader==null){
            throw new NullPointerException("Call XFrame.initXImageLoader(ImageLoader loader) within your Application onCreate() method." +
                    "Or extends XApplication");
        }
        if (vImage == null) {
            vImage = new VImage();
        }
        return vImage;
    }

    @Override
    public void load(ImageView imageView, Object imageUrl) {
        imageLoader.load(imageView, imageUrl);
    }

    @Override
    public void load(ImageView imageView, Object imageUrl, int defaultImage) {
        imageLoader.load(imageView, imageUrl, defaultImage);
    }

    @Override
    public void load(ImageView imageView, Object imageUrl, Object transformation) {
        imageLoader.load(imageView, imageUrl, transformation);
    }

    @Override
    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        imageLoader.loadThumbnail(context,resize,placeholder,imageView,uri);
    }

    @Override
    public void loadAnimatedGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        imageLoader.loadAnimatedGifThumbnail(context,resize,placeholder,imageView,uri);
    }

    @Override
    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        imageLoader.loadImage(context,resizeX,resizeY,imageView,uri);
    }

    @Override
    public void loadAnimatedGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
         imageLoader.loadAnimatedGifImage(context,resizeX,resizeY,imageView,uri);
    }

    @Override
    public boolean supportAnimatedGif() {
        return false;
    }

}
