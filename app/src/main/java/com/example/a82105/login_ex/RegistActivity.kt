package com.example.a82105.login_ex

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a82105.login_ex.Retrofit.INodeJS
import com.example.a82105.login_ex.Retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RegistActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var myAPI: INodeJS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regist)

        val retrofit = RetrofitClient.getInstance()
        myAPI = retrofit.create(INodeJS::class.java)

        // 장르 버튼에 리스너 설정
        setGenreButtonListener(R.id.sectionramance, "로맨스")
        setGenreButtonListener(R.id.sectionfantasy, "판타지")
        setGenreButtonListener(R.id.sectionThriller, "스릴러")
        setGenreButtonListener(R.id.sectionDrama, "드라마")
        setGenreButtonListener(R.id.sectionMA, "무협/사극")
        setGenreButtonListener(R.id.sectionComic, "일상/개그")
        setGenreButtonListener(R.id.sectionSports, "스포츠/액션")
    }

    private fun setGenreButtonListener(buttonId: Int, genre: String) {
        val button = findViewById<Button>(buttonId)
        button.setOnClickListener {
            val request = INodeJS.GenreRequest(genre)
            compositeDisposable.add(
                myAPI.getWebtoonsByGenre(request)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ webtoons ->
                        // 받은 웹툰 데이터를 다음 Fragment로 전달
                        val fragment = RegistSelecViewFragment.newInstance(webtoons)
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit()
                    }, { throwable ->
                        Toast.makeText(this, "Error: ${throwable.message}", Toast.LENGTH_SHORT).show()
                    })
            )
        }
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}