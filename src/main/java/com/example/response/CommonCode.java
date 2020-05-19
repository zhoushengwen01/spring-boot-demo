package com.example.response;

import lombok.ToString;


@ToString
public enum CommonCode implements ResultCode {

    SUCCESS(true, 111, "操作成功！"),
    FAIL(false, 222, "操作失败！"),
    FORESEE_EXCEPTION(false, 333, "预知异常"),
    NOT_FORESEE_EXCEPTION(false, 444, "不可预知异常");


    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    private CommonCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }


}
