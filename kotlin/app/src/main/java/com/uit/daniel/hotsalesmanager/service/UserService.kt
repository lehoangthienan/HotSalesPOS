package com.uit.daniel.hotsalesmanager.service

import android.content.Context
import com.uit.daniel.hotsalesmanager.data.request.PhoneNumberRequest
import com.uit.daniel.hotsalesmanager.data.request.UserAvatarRequest
import com.uit.daniel.hotsalesmanager.data.request.UserNameRequest
import com.uit.daniel.hotsalesmanager.data.request.UserRequest
import com.uit.daniel.hotsalesmanager.data.response.UserResponse
import com.uit.daniel.hotsalesmanager.utils.ArgumentSingletonHolder
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {

    @POST(ApiEndpoint.CREATE_USER)
    fun signIn(@Body user: UserRequest): Single<UserResponse>

    @PUT(ApiEndpoint.UPDATE_USER)
    fun updatePhoneNumber(@Path("userId") userId: String, @Body phoneNumberRequest: PhoneNumberRequest): Single<UserResponse>

    @PUT(ApiEndpoint.UPDATE_USER)
    fun updateName(@Path("userId") userId: String, @Body userNameRequest: UserNameRequest): Single<UserResponse>

    @PUT(ApiEndpoint.UPDATE_USER)
    fun updateAvatar(@Path("userId") userId: String, @Body userAvatarRequest: UserAvatarRequest): Single<UserResponse>

}

class UserService private constructor(context: Context) {

    companion object : ArgumentSingletonHolder<UserService, Context>(::UserService)

    private val apiClient: Retrofit =
        ApiService.Factory.getRetrofitBuilder(context).baseUrl(ApiEndpoint.BASE_URL).build()

    private val api: UserApi = apiClient.create(UserApi::class.java)

    fun signInRequest(user: UserRequest) = api.signIn(user)

    fun updatePhoneNumberRequest(userId: String, phoneNumberRequest: PhoneNumberRequest) =
        api.updatePhoneNumber(userId, phoneNumberRequest)

    fun updateNameRequest(userId: String, userNameRequest: UserNameRequest) = api.updateName(userId, userNameRequest)

    fun updateAvatarRequest(userId: String, userAvatarRequest: UserAvatarRequest) =
        api.updateAvatar(userId, userAvatarRequest)

}