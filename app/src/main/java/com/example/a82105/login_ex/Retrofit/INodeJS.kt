package com.example.a82105.login_ex.Retrofit

import android.os.Parcel
import android.os.Parcelable
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface INodeJS {
    @POST("api/auth/register")
    @FormUrlEncoded
    fun registerUser(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String
    ): Observable<String>

    @POST("api/auth/login")
    @FormUrlEncoded
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Observable<String>

    // 장르에 따른 웹툰 가져오는 메서드
    @POST("api/recommend/get_webtoons")
    fun getWebtoonsByGenre(@Body genreRequest: GenreRequest): Observable<List<Webtoon>>

    // 요청 클래스
    data class GenreRequest(
        var genre: String
    )

    // Webtoon 클래스
    data class Webtoon(
        var title: String?,
        var thumbnail_link: String?
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(title)
            parcel.writeString(thumbnail_link)
        }

        override fun describeContents(): Int = 0

        companion object CREATOR : Parcelable.Creator<Webtoon> {
            override fun createFromParcel(parcel: Parcel): Webtoon {
                return Webtoon(parcel)
            }

            override fun newArray(size: Int): Array<Webtoon?> {
                return arrayOfNulls(size)
            }
        }
    }
}