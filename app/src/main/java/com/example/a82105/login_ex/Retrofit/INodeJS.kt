package com.example.a82105.login_ex.Retrofit;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import java.util.List;
import retrofit2.http.Body;

import android.os.Parcel;
import android.os.Parcelable;


public interface INodeJS {
    @POST("api/auth/register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("email") String email, @Field("password") String password, @Field("name") String name);

    @POST("api/auth/login")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("email") String email, @Field("password") String password);



    // 장르에 따른 웹툰 가져오는 메서드
    @POST("api/recommend/get_webtoons")
    Observable<List<Webtoon>> getWebtoonsByGenre(@Body GenreRequest genreRequest);



    // 요청 클래스
    class GenreRequest {

        private String genre;

        public GenreRequest(String genre) {
            this.genre = genre;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }
    }

    public class Webtoon implements Parcelable {
        String title;
        String thumbnail_link;

        public Webtoon(String title, String thumbnail_link) {
            this.title = title;
            this.thumbnail_link = thumbnail_link;
        }

        protected Webtoon(Parcel in) {
            title = in.readString();
            thumbnail_link = in.readString();
        }

        public static final Creator<Webtoon> CREATOR = new Creator<Webtoon>() {
            @Override
            public Webtoon createFromParcel(Parcel in) {
                return new Webtoon(in);
            }

            @Override
            public Webtoon[] newArray(int size) {
                return new Webtoon[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeString(thumbnail_link);
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumbnail_link() {
            return thumbnail_link;
        }

        public void setThumbnail_link(String thumbnail_link) {
            this.thumbnail_link = thumbnail_link;
        }
    }
}