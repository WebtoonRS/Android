package com.example.a82105.login_ex

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a82105.login_ex.Retrofit.INodeJS
import com.example.a82105.login_ex.Retrofit.RetrofitClient
import com.google.android.material.button.MaterialButton
import com.rengwuxian.materialedittext.MaterialEditText
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private lateinit var myAPI: INodeJS
    private var compositeDisposable = CompositeDisposable()

    private lateinit var edt_email: MaterialEditText
    private lateinit var edt_password: MaterialEditText
    private lateinit var btn_register: MaterialButton
    private lateinit var btn_login: MaterialButton
    private lateinit var btn_next: MaterialButton

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init API
        val retrofit: Retrofit = RetrofitClient.instance!!
        myAPI = retrofit.create(INodeJS::class.java)

        // View 초기화
        btn_login = findViewById(R.id.login_button)
        btn_register = findViewById(R.id.register_button)
        edt_email = findViewById(R.id.edt_email)
        edt_password = findViewById(R.id.edt_password)
        btn_next = findViewById(R.id.im)

        // 이벤트 처리
        btn_login.setOnClickListener {
            loginUser(edt_email.text.toString(), edt_password.text.toString())
        }

        btn_register.setOnClickListener {
            val intent = Intent(applicationContext, SighUp1Activity::class.java)
            startActivity(intent)
        }

        btn_next.setOnClickListener {
            val intent = Intent(applicationContext, RealMainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        myAPI?.loginUser(email, password)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { s ->
                    s?.let {
                        if (it.contains("encrypted_password")) {
                            Toast.makeText(this@MainActivity, "Login Success", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                { throwable ->
                    // 오류 처리
                    Toast.makeText(
                        this@MainActivity,
                        "Error: ${throwable.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )?.let { compositeDisposable.add(it) }
    }
}