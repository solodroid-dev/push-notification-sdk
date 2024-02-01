package com.solodroid.push.sdk.provider;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;
import com.onesignal.notifications.INotification;
import com.solodroid.push.sdk.utils.OnNotificationClickListener;

import org.json.JSONObject;

public class OneSignalPush {

    public static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 22;

    public static class Builder {

        private static final String TAG = "OneSignalPush";
        Context context;
        private String oneSignalAppId = "";
        private String notificationId = "";
        private String notificationTitle = "";
        private String notificationMessage = "";
        private String notificationBigImage = "";
        private String notificationLaunchUrl = "";
        private String uniqueId = "";
        private String postId = "";
        private int postID;
        private String link = "";

        public Builder(Context context) {
            this.context = context;
        }

        public Builder build(OnNotificationClickListener onNotificationClickListener) {
            initNotification(onNotificationClickListener);
            return this;
        }

        public Builder create(OnNotificationClickListener onNotificationClickListener) {
            buildNotification(onNotificationClickListener);
            return this;
        }

        public Builder setOneSignalAppId(String oneSignalAppId) {
            this.oneSignalAppId = oneSignalAppId;
            return this;
        }

        public void initNotification(OnNotificationClickListener onNotificationClickListener) {

            OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);
            OneSignal.initWithContext(context, oneSignalAppId);
            OneSignal.getUser().getPushSubscription().optIn();

            OneSignal.getNotifications().addClickListener(result -> {
                INotification notification = result.getNotification();
                JSONObject data = notification.getAdditionalData();

                notificationId = result.getNotification().getNotificationId();
                notificationTitle = result.getNotification().getTitle();
                notificationMessage = result.getNotification().getBody();
                notificationBigImage = result.getNotification().getBigPicture();
                notificationLaunchUrl = result.getNotification().getLaunchURL();
                OneSignalPush.Data.id = notificationId;
                OneSignalPush.Data.title = notificationTitle;
                OneSignalPush.Data.message = notificationMessage;
                OneSignalPush.Data.bigImage = notificationBigImage;
                OneSignalPush.Data.launchUrl = notificationLaunchUrl;
                try {
                    if (data != null) {
                        uniqueId = data.getString(OneSignalPush.EXTRA_UNIQUE_ID);
                        postId = data.getString(OneSignalPush.EXTRA_POST_ID);
                        link = data.getString(OneSignalPush.EXTRA_LINK);
                        OneSignalPush.AdditionalData.uniqueId = uniqueId;
                        OneSignalPush.AdditionalData.postId = postId;
                        OneSignalPush.AdditionalData.link = link;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "error: " + e.getMessage());
                }

                onNotificationClickListener.onComplete();

            });
        }

        public void buildNotification(OnNotificationClickListener onNotificationClickListener) {

            OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);
            OneSignal.initWithContext(context, oneSignalAppId);
            OneSignal.getUser().getPushSubscription().optIn();

            OneSignal.getNotifications().addClickListener(result -> {
                INotification notification = result.getNotification();
                JSONObject data = notification.getAdditionalData();

                notificationId = result.getNotification().getNotificationId();
                notificationTitle = result.getNotification().getTitle();
                notificationMessage = result.getNotification().getBody();
                notificationBigImage = result.getNotification().getBigPicture();
                notificationLaunchUrl = result.getNotification().getLaunchURL();
                OneSignalPush.Data.id = notificationId;
                OneSignalPush.Data.title = notificationTitle;
                OneSignalPush.Data.message = notificationMessage;
                OneSignalPush.Data.bigImage = notificationBigImage;
                OneSignalPush.Data.launchUrl = notificationLaunchUrl;
                try {
                    if (data != null) {
                        postID = data.getInt(OneSignalPush.EXTRA_POST_ID);
                        OneSignalPush.AdditionalData.postID = postID;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "error: " + e.getMessage());
                }

                onNotificationClickListener.onComplete();

            });
        }

        public void requestNotificationPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, OneSignalPush.NOTIFICATION_PERMISSION_REQUEST_CODE);
                }
            }
        }

    }

    public static class Data {
        public static String id = "";
        public static String title = "";
        public static String message = "";
        public static String bigImage = "";
        public static String launchUrl = "";
    }

    public static class AdditionalData {
        public static String uniqueId = "";
        public static String postId = "";
        public static int postID;
        public static String link = "";
    }

    public static String EXTRA_ID = "id";
    public static String EXTRA_TITLE = "title";
    public static String EXTRA_MESSAGE = "message";
    public static String EXTRA_IMAGE = "image";
    public static String EXTRA_LAUNCH_URL = "launch_url";
    public static String EXTRA_UNIQUE_ID = "unique_id";
    public static String EXTRA_POST_ID = "post_id";
    public static String EXTRA_LINK = "link";

}
