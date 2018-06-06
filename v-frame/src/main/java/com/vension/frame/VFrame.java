package com.vension.frame;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.DisplayMetrics;

import com.vension.frame.base.ProxyAvtivity;
import com.vension.frame.http.RetrofitFactory;
import com.vension.frame.imageloader.ImageLoader;
import com.vension.frame.imageloader.VImage;
import com.vension.frame.stacks.observers.ActivityObserver;
import com.vension.frame.utils.VCrashUtil;
import com.vension.frame.utils.VObsoleteUtil;
import com.vension.frame.utils.VToastUtil;
import com.vension.frame.utils.log.VLog;

import java.util.List;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/16 15:17
 * 描  述：
 * ========================================================
 */

public class VFrame {

	public final static String TAG = VFrame.class.getSimpleName();
	//以下属性应用于整个应用程序，合理利用资源，减少资源浪费
	private static Application vApplication;
	private static Context vContext;//上下文
	//显示在代理Activity 的Fragment key
	public static String PROXY_FRAGMENT_CLASS_KEY = "Proxy Fragment class key";
	public static boolean isDebug = true;

	private static Thread mMainThread;//主线程
	private static long mMainThreadId;//主线程id
	private static Looper mMainLooper;//循环队列
	private static Handler mHandler;//主线程Handler


	public VFrame() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}

	/**
	 * 初始化VFrame 工具类
	 *
	 * @param mApplication
	 */
	public static void init(@NonNull final Application mApplication){
		VFrame.vApplication = mApplication;
		VFrame.vContext = mApplication.getApplicationContext();
		mApplication.registerActivityLifecycleCallbacks(mCallbacks);
	}

    public static void initLog(){
		VLog.init();
	}

	public static void initToast(){
		VToastUtil.init(getContext());
	}

	public static void initCrash() {
		VCrashUtil.init();
	}

	public static void initImageLoader(ImageLoader loader) {
		VImage.init(loader);
	}

	public static void initHttpConfig(String baseUrl) {
		RetrofitFactory.INSTANCE.initConfig(baseUrl);
	}


	/**获取ApplicationContext*/
	public static Context getContext(){
		synchronized (VFrame.class){
			if (VFrame.vContext == null)
				throw new NullPointerException("Call VFrame.init(context) within your Application onCreate() method." +
						"Or extends V_Application");
			return VFrame.vContext;
		}
	}


	/**
	 * 获取Application
	 *
	 * @return Application
	 */
	public static Application getApplication() {
		if (vApplication != null) return vApplication;
		throw new NullPointerException("u should init first");
	}

	public static Resources getResources(){
		return VFrame.getContext().getResources();
	}

	public static Thread getMainThread(){
		return mMainThread = Thread.currentThread();
	}
	public static long getMainThreadId(){
		return mMainThreadId = android.os.Process.myTid();
	}
	public static Handler getMainHandler(){
		return mHandler = new Handler();
	}

	public static String getString(@StringRes int id){
		return getResources().getString(id);
	}

	public static Resources.Theme getTheme(){
		return VFrame.getContext().getTheme();
	}

	public static AssetManager getAssets(){
		return VFrame.getContext().getAssets();
	}

	public static Drawable getDrawable(@DrawableRes int id){
		return VObsoleteUtil.getDrawable(id);
	}

	public static int getColor(@ColorRes int id){
		return VObsoleteUtil.getColor(id);
	}

	public static Object getSystemService(String name){
		return VFrame.getContext().getSystemService(name);
	}

	public static Configuration getConfigguration(){
		return VFrame.getResources().getConfiguration();
	}

	public static DisplayMetrics getDisplayMetrics(){
		return VFrame.getResources().getDisplayMetrics();
	}


	/**应用生命周期监听器*/
	private static Application.ActivityLifecycleCallbacks mCallbacks = new Application.ActivityLifecycleCallbacks() {
		@Override
		public void onActivityCreated(Activity activity, Bundle bundle) {
			ActivityObserver.getInstance().regist(activity);//activity进栈
		}

		@Override
		public void onActivityStarted(Activity activity) {
		}

		@Override
		public void onActivityResumed(Activity activity) {
		}

		@Override
		public void onActivityPaused(Activity activity) {

		}

		@Override
		public void onActivityStopped(Activity activity) {

		}

		@Override
		public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

		}

		@Override
		public void onActivityDestroyed(Activity activity) {
			ActivityObserver.getInstance().unregist(activity);//activity出栈
		}
	};

	static public List<Activity> getActivityList() {
		return ActivityObserver.getInstance().getObserverList();
	}
	static public Activity getActivityListStackTop() {
		return ActivityObserver.getInstance().getCurrentObserver();
	}

	/**
	 * 创建Fragment Intent对象
	 */
	static public Intent createProxyIntent(Context context,Class<?> fragment) {
		return createProxyIntent(context,fragment, new Bundle());
	}

	/**
	 * 创建Fragment Intent对象
	 */
	static public Intent createProxyIntent(Context context,Class<?> fragment, Bundle _Bundle) {
		_Bundle.putString(PROXY_FRAGMENT_CLASS_KEY, fragment.getName()); // com.project.app.activity.*
		Intent _Intent = new Intent(context, ProxyAvtivity.class);
		_Intent.putExtras(_Bundle);
		return _Intent;
	}

	/**
	 * 退出应用程序
	 * 此方法经测试在某些机型上并不能完全杀死 App 进程, 几乎试过市面上大部分杀死进程的方式, 但都发现没卵用, 所以此
	 * 方法如果不能百分之百保证能杀死进程, 就不能贸然调用 释放资源, 否则会造成其他问题, 如果您
	 * 有测试通过的并能适用于绝大多数机型的杀死进程的方式, 望告知
	 */
	public void appExit() {
		try {
			// 清理
			ActivityObserver.getInstance().clear();
			// 杀死进程
			android.os.Process.killProcess(android.os.Process.myPid());
			// 退出程序
			System.exit(0);
			// 通知系统回收
			System.gc();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}





}
