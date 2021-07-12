package ru.alfabank.sreenshot_library.service

import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface MatcherService {

    @Multipart
    @POST("compare")
    fun compare(
        @Part file: MultipartBody.Part,
        @Query("imageId") imageId: String,
        @Query("similarity") similarity: Double
    ): Single<MatchResponse>

    companion object {

        fun build(): MatcherService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://10.0.2.2:8080/")
                .build()

            return retrofit.create(MatcherService::class.java)
        }
    }
}