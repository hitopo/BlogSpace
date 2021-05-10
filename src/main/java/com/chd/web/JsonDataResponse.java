package com.chd.web;

import com.google.common.base.MoreObjects;

/**
 * 自己分装的json返回信息类
 * 作为server返回给client的JSON格式信息
 */
public class JsonDataResponse {
    // 是否成功
    private boolean success;

    // 原因（失败）
    private String reason;


    public JsonDataResponse(boolean success) {
        this.success = success;
        this.reason = "失败";
    }

    public JsonDataResponse() {
        this.success = true;
        this.reason = "成功";
    }

    public JsonDataResponse(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("success", success)
                .add("reason", reason)
                .toString();
    }
}
