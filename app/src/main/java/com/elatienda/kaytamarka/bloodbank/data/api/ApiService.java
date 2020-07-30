package com.elatienda.kaytamarka.bloodbank.data.api;

import com.elatienda.kaytamarka.bloodbank.data.model.donation_requests.DonationRequests;
import com.elatienda.kaytamarka.bloodbank.data.model.general_response.GeneralResponse;
import com.elatienda.kaytamarka.bloodbank.data.model.login.Login;
import com.elatienda.kaytamarka.bloodbank.data.model.new_password.NewPassword;
import com.elatienda.kaytamarka.bloodbank.data.model.notification_settings.NotificationSettings;
import com.elatienda.kaytamarka.bloodbank.data.model.notifications.Notifications;
import com.elatienda.kaytamarka.bloodbank.data.model.notifications_count.NotificationsCount;
import com.elatienda.kaytamarka.bloodbank.data.model.post.Post;
import com.elatienda.kaytamarka.bloodbank.data.model.reset_password.ResetPassword;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("blood-types")
    Call<GeneralResponse> getBloodTypes();

    @GET("governorates")
    Call<GeneralResponse> getGovernorates();

    @GET("cities")
    Call<GeneralResponse> getCities(@Query("governorate_id") int governorateId);

    @POST("signup")
    @FormUrlEncoded
    Call<Login> onRegister(@Field("name") String name,
                              @Field("email") String email,
                              @Field("birth_date") String birthDate,
                              @Field("city_id") int cityId,
                              @Field("phone") String phone,
                              @Field("donation_last_date") String donationLastDate,
                              @Field("password") String password,
                              @Field("password_confirmation") String passwordConfirmation,
                              @Field("blood_type_id") int bloodTypeId);

    @POST("login")
    @FormUrlEncoded
    Call<Login> onLogin(@Field("phone") String phone,
                        @Field("password") String password);

    @POST("reset-password")
    @FormUrlEncoded
    Call<ResetPassword> onResetPassword(@Field("phone") String phone);

    @POST("new-password")
    @FormUrlEncoded
    Call<NewPassword> setNewPassword(@Field("password") String password,
                                     @Field("password_confirmation") String passwordConfirmation,
                                     @Field("pin_code") int pinCode,
                                     @Field("phone") String phone);

    @GET("categories")
    Call<GeneralResponse> getCategories();

    @GET("posts")
    Call<Post> getAllPosts(@Query("api_token") String apiToken,
                           @Query("page") int page);

    @GET("posts")
    Call<Post> getAllPostsFilter(@Query("api_token") String apiToken,
                           @Query("page") int page,
                           @Query("keyword") String keyword,
                           @Query("category_id") int categoryId);

    @GET("donation-requests")
    Call<DonationRequests> getAllDonationRequests(@Query("api_token") String apiToken,
                                                  @Query("page") int page);

    @GET("donation-requests")
    Call<DonationRequests> getAllDonationRequestsFilter(@Query("api_token") String apiToken,
                                                  @Query("blood_type_id") int bloodTypeId,
                                                  @Query("governorate_id") int governorateId,
                                                  @Query("page") int page);

    @POST("profile")
    @FormUrlEncoded
    Call<Login> getProfileData(@Field("api_token") String apiToken);


    @GET("notifications")
    Call<Notifications> getAllNotifications(@Query("api_token") String apiToken,
                                               @Query("page") int page);

    @GET("my-favourites")
    Call<Post> getAllFavoritePosts(@Query("api_token") String apiToken,
                                            @Query("page") int page);

    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationSettings> getNotificationSettings(@Field("api_token") String apiToken);

    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationSettings> changeNotificationSettings(@Field("api_token") String apiToken,
                                                          @Field("governorates[]") List<Integer> governorates,
                                                          @Field("blood_types[]") List<Integer> bloodTypes);

    @GET("notifications-count")
    Call<NotificationsCount> getNotificationsCount(@Query("api_token") String apiToken);






}
