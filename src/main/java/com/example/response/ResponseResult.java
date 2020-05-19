package com.example.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class ResponseResult<T> implements Response {

    private static final long serialVersionUID = 1L;

    //操作是否成功
    boolean success = SUCCESS;

    //操作状态码
    int code = SUCCESS_CODE;

    //提示信息
    String message;

    //携带数据
    T data;

    public ResponseResult(ResultCode resultCode, T data) {
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = data;
    }

    public ResponseResult(ResultCode resultCode) {
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }


    public static ResponseResult SUCCESS() {
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public static ResponseResult FAIL() {
        return new ResponseResult(CommonCode.FAIL);
    }

}
