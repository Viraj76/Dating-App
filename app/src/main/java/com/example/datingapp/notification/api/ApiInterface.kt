package com.example.datingapp.notification.api

import com.example.datingapp.notification.PushNotification
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {

    @Headers("Content-Type: application/json","Authorization: key=AAAAmoqx1Ss:APA91bE2foPo38rcNPedf5q092xjLmY1XBXuSrswe5Lfzh3vyhPJVteqz-7kS1L2rjFSlK9wxpCBYiLvFTVYP5O7puZhGNz3Ea2NKqYoCzT7ZGTfDk-03bxgafIz_hh4CqU7B5dbLCpG")
    @POST("fcm/send")
    fun sendNotification(@Body notification:PushNotification) : Call<PushNotification>
}