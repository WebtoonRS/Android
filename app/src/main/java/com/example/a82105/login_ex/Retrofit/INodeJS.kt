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
        @Field("email") email: String?,
        @Field("password") password: String?,
        @Field("name") name: String?
    ): Observable<String?>?

    @POST("api/auth/login")
    @FormUrlEncoded
    fun loginUser(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Observable<String?>?


    // 장르에 따른 웹툰 가져오는 메서드
    @POST("api/recommend/get_webtoons")
    fun getWebtoonsByGenre(@Body genreRequest: GenreRequest?): Observable<List<Webtoon?>?>?


    // 요청 클래스
    class GenreRequest(var genre: String)

    class Webtoon : Parcelable {
        var title: String?
        var thumbnail_link: String?

        constructor(title: String?, thumbnail_link: String?) {
            this.title = title
            this.thumbnail_link = thumbnail_link
        }

        protected constructor(`in`: Parcel) {
            title = `in`.readString()
            thumbnail_link = `in`.readString()
        }

        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(title)
            dest.writeString(thumbnail_link)
        }

        companion object {
            val CREATOR: Parcelable.Creator<Webtoon> = object : Parcelable.Creator<Webtoon?> {
                override fun createFromParcel(`in`: Parcel): Webtoon? {
                    return Webtoon(`in`)
                }

                override fun newArray(size: Int): Array<Webtoon?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}