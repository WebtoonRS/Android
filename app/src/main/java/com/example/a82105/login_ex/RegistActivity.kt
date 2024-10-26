package com.example.a82105.login_ex;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.a82105.login_ex.Retrofit.INodeJS;
import com.example.a82105.login_ex.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RegistActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    INodeJS myAPI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);


        setGenreButtonListener(R.id.sectionramance,"로맨스" );
        setGenreButtonListener(R.id.sectionfantasy, "판타지");
        setGenreButtonListener( R.id.sectionThriller, "스릴러");
        setGenreButtonListener( R.id.sectionDrama, "드라마");
        setGenreButtonListener( R.id.sectionMA, "무협/사극");
        setGenreButtonListener( R.id.sectionComic, "일상/개그");
        setGenreButtonListener( R.id.sectionSports, "스포츠/액션");
    }


    private void setGenreButtonListener( int buttonId, String genre) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> {
            INodeJS.GenreRequest request = new INodeJS.GenreRequest(genre);
            compositeDisposable.add(myAPI.getWebtoonsByGenre(request)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(webtoons -> {
                        // 받은 웹툰 데이터를 다음 Fragment로 전달
                        RegistSelecViewFragment fragment = RegistSelecViewFragment.newInstance(webtoons);
                       getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack(null)
                                .commit();
                    }, throwable -> {
                        Toast.makeText(RegistActivity.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        // 에러 처리
                    }));
        });
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}

