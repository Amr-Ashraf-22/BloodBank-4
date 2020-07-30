
package com.elatienda.kaytamarka.bloodbank.data.model.pivot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pivot {

    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("notification_id")
    @Expose
    private String notificationId;
    @SerializedName("is_read")
    @Expose
    private String isRead;
    @SerializedName("post_id")
    @Expose
    private String postId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

}
