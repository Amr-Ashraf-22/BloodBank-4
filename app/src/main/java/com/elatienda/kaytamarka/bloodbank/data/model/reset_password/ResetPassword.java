
package com.elatienda.kaytamarka.bloodbank.data.model.reset_password;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPassword {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ResetPasswordData resetPasswordData;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResetPasswordData getData() {
        return resetPasswordData;
    }

    public void setData(ResetPasswordData resetPasswordData) {
        this.resetPasswordData = resetPasswordData;
    }

}
