package com.vension.frame.http;


import com.vension.frame.base.mvp.IBaseView;
import com.vension.frame.utils.VLogUtil;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/16 15:21
 * 描  述：Rxjava的请求封装
 * ========================================================
 */

public abstract class MySubscriber<T> extends ResourceSubscriber<T> {
    private IBaseView mView;
    private boolean isShowLoading;//页面上的loading加载动画
    private boolean isShowLoadingDialog;//dialog的loading加载动画


    /**
     * 默认开启loading页面
     * 默认关闭loadingDialog
     */
    protected MySubscriber(IBaseView view) {
        this(view,false);
    }
    protected MySubscriber(IBaseView view,boolean isShowLoadingDialog) {
        this(view,true,isShowLoadingDialog);
    }

    protected MySubscriber(IBaseView view, boolean isShowLoading,boolean isShowLoadingDialog) {
        this.mView = view;
        this.isShowLoading = isShowLoading;
        this.isShowLoadingDialog = isShowLoadingDialog;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isShowLoading && mView != null) {
            mView.showLoading();
        }
        if (isShowLoadingDialog && mView != null) {
            mView.showLoadingDialog();
        }
    }

    @Override
    public void onComplete() {
        if (mView == null) return;
        mView.dismissLoading();
        if (isShowLoadingDialog) {
            mView.dismissLoadingDialog();
        }
    }


    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        //http error   ||   code != 200
        if (mView == null) return;
        if (e instanceof ApiException) {
            int code = ((ApiException)e).getCode();
            VLogUtil.e("onError==》" + code);
            if (code == 5005){//登录失效
                mView.resetLogin();
            }
            onError((ApiException) e);
        }

        //onError就不走onComplete了
        mView.showError();
        if (isShowLoadingDialog) {
            mView.dismissLoadingDialog();
        }
    }

    protected abstract void onSuccess(T t);
    protected void onError(ApiException e) {
        mView.showToast(e.getmsg());
    }

}
