package com.crawl.videosite.exception;

import com.alibaba.fastjson.JSON;

/**
 * 返回的json数据为空的异常
 * <p>
 * Created by qianhaibin on 2018/3/3.
 */
public class JsonDataEmptyException extends RuntimeException {

    public JsonDataEmptyException(String message,Throwable e){
        super(message,e);
    }

    public JsonDataEmptyException(String message){
        super(message);
    }
}
