package com.vension.frame.http;

/**
 * @author 王元_Trump
 * @contact 861316155@qq.com
 * @time 2018/4/4 15:27
 * @desc 自定义http异常处理
 */
public class ApiException extends RuntimeException {
    private int code;
    private String msg;

    public ApiException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getmsg() {
        return msg;
    }

    public void setmsg(String msg) {
        this.msg = msg;
    }
}
