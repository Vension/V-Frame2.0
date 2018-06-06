package com.vension.frame.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/25 10:44
 * 描  述：框架全局图片加载接口
 * ========================================================
 */

public interface ImageLoader {

    /**
     * 图片加载方法
     *
     * （默认图片在实现类中实现，此方法主要是全局调用，默认图片统一，避免每次都要传入默认图片）
     *
     * @param imageView
     * @param imageUrl 类型为Object原因：因为你的图片链接可以是string、uri、file等多中类型
     */
    void load(ImageView imageView, Object imageUrl);

    /**
     * 图片加载方法
     *
     * （默认图片可以自己每次单独设置，主要满足软件一些地方可能默认图片不一样的情况）
     *
     * @param imageView
     * @param imageUrl
     * @param defaultImage
     */
    void load(ImageView imageView, Object imageUrl, int defaultImage);

    /**
     * 加载不同形状图片
     * @param imageView
     * @param imageUrl
     * @param transformation 传入你要加载的图片形状实现类
     */
    void load(ImageView imageView, Object imageUrl, Object transformation);

    /**
     * Load thumbnail of a static image resource.
     *
     * @param context     Context
     * @param resize      Desired size of the origin image
     * @param placeholder Placeholder drawable when image is not loaded yet
     * @param imageView   ImageView widget
     * @param uri         Uri of the loaded image
     */
    void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri);

    /**
     * Load thumbnail of a gif image resource. You don't have to load an animated gif when it's only
     * a thumbnail tile.
     *
     * @param context     Context
     * @param resize      Desired size of the origin image
     * @param placeholder Placeholder drawable when image is not loaded yet
     * @param imageView   ImageView widget
     * @param uri         Uri of the loaded image
     */
    void loadAnimatedGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri);

    /**
     * Load a static image resource.
     *
     * @param context   Context
     * @param resizeX   Desired x-size of the origin image
     * @param resizeY   Desired y-size of the origin image
     * @param imageView ImageView widget
     * @param uri       Uri of the loaded image
     */
    void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri);

    /**
     * Load a gif image resource.
     *
     * @param context   Context
     * @param resizeX   Desired x-size of the origin image
     * @param resizeY   Desired y-size of the origin image
     * @param imageView ImageView widget
     * @param uri       Uri of the loaded image
     */
    void loadAnimatedGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri);

    /**
     * Whether this implementation supports animated gif. Just knowledge of it, convenient for users.
     *
     * @return true support animated gif, false do not support animated gif.
     */
    boolean supportAnimatedGif();
}
