package com.uit.daniel.hotsalesmanager.service

import android.content.Context
import com.uit.daniel.hotsalesmanager.data.request.PhoneNumberRequest
import com.uit.daniel.hotsalesmanager.data.request.UserRequest
import com.uit.daniel.hotsalesmanager.data.response.UserResponse
import com.uit.daniel.hotsalesmanager.utils.ArgumentSingletonHolder
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApi {

    @POST(ApiEndpoint.CREATE_USER)
    fun signIn(@Body user: UserRequest): Single<UserResponse>

    @PUT(ApiEndpoint.UPDATE_USER_PHONE_NUMBER)
    fun updatePhoneNumber(@Body phoneNumberRequest: PhoneNumberRequest): Single<UserResponse>

}

class UserService private constructor(context: Context) {

    companion object : ArgumentSingletonHolder<UserService, Context>(::UserService)

    private val apiClient: Retrofit =
        ApiService.Factory.getRetrofitBuilder(context).baseUrl(ApiEndpoint.BASE_URL).build()

    private val api: UserApi = apiClient.create(UserApi::class.java)

    fun signInRequest(user: UserRequest) = api.signIn(user)

    fun updatePhoneNumberRequest(phoneNumberRequest: PhoneNumberRequest) = api.updatePhoneNumber(phoneNumberRequest)
}