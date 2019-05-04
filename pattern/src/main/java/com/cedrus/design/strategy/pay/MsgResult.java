package com.cedrus.design.strategy.pay;

/**
 * 支付完成以后的状态
 * @author Cedrus
 * @date 2019/3/17
 */
public class MsgResult {
    private int code;
    private Object data;
    private String msg;

    public MsgResult(int code, String msg, Object data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    @Override
    public String toString(){
        return ("支付状态：[" + code + "]," + msg + ",交易详情：" + data);
    }
}
