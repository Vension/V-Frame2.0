package com.vension.frame.rx;

import com.google.gson.JsonParseException;
import com.vension.frame.http.ApiException;
import com.vension.frame.http.BaseResponse;
import com.vension.frame.utils.NetworkUtil;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/24 15:54
 * 描  述：统一线程处理结果数据 针对 Flowable
 * ========================================================
 */

public class RxUtil {

    //++++++++++++++++++++++++++  Flowable ++++++++++++++++++++++++++

    /**
     * 统一线程处理结果数据
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return observable -> observable
				/*指定线程*/
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				/*失败后的retry配置*/
				//.retryWhen(new RetryWhenNetworkException())
				/*回调线程*/
				.observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> FlowableTransformer<BaseResponse<T>, T> handleResult() {
        return upstream -> upstream
				.onErrorResumeNext(throwable -> {
					//非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
					return PublishProcessor.error(handleException(throwable));
				})
				/*过滤数据*/
				.flatMap(mBaseResponse -> {
					//服务其返回的数据解析
					//正常服务器返回数据和服务器可能返回的exception
					int code = mBaseResponse.getCode();
					String message = mBaseResponse.getMessage();
					mBaseResponse.setSuccess(true);
					if (mBaseResponse.isSuccess()) {
						return PublishProcessor.just(mBaseResponse.getData());
					} else {
						if (code == 500){
							return PublishProcessor.error(new ApiException(5005, "登录失效"));
						}
						return PublishProcessor.error(new ApiException(code, message));
					}
				});
    }


    /**
     * 生成Flowable
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(emitter -> {
			try {
				emitter.onNext(t);
				emitter.onComplete();
			} catch (Exception e) {
				emitter.onError(e);
			}
		}, BackpressureStrategy.BUFFER);
    }


    private static ApiException handleException(Throwable e) {
		ApiException ex;
        if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //解析错误
            ex = new ApiException(5000, /*e.getMessage()*/"数据解析错误");
            return ex;
        } else if (e instanceof ConnectException) {
            //网络错误
            ex = new ApiException(5001, "网络连接异常");
            return ex;
        } else if (e instanceof UnknownHostException || e instanceof SocketTimeoutException) {
            //连接错误
            ex = new ApiException(5002, "服务器连接异常");
            return ex;
        } else if (e instanceof IllegalArgumentException){
			//参数错误
			ex = new ApiException(5003, "参数错误");
			return ex;
		}else {
            //未知错误
            if (NetworkUtil.isNetworkConnected())
                ex = new ApiException(5999, e.getMessage());
            else
                ex = new ApiException(5004, "当前网络不可用");
            return ex;
        }
    }


}
