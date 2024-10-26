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
    private val compositeDisposable = CompositeDisposable()

    private lateinit var edtEmail: MaterialEditText
    private lateinit var edtPassword: MaterialEditText
    private lateinit var btnRegister: MaterialButton
    private lateinit var btnLogin: MaterialButton
    private lateinit var btnNext: MaterialButton

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
        val retrofit: Retrofit = RetrofitClient.getInstance()
        myAPI = retrofit.create(INodeJS::class.java)

        // View 초기화
        btnLogin = findViewById(R.id.login_button)
        btnRegister = findViewById(R.id.register_button)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnNext = findViewById(R.id.im)

        // 이벤트 설정
        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            // 입력 값 체크
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter your email and password", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        }

        btnRegister.setOnClickListener {
            val intent = Intent(applicationContext, SighUp1Activity::class.java)
            startActivity(intent)
        }

        btnNext.setOnClickListener {
            val intent = Intent(applicationContext, RealMainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        compositeDisposable.add(
            myAPI.loginUser(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.contains("encrypted_password")) {
                        Toast.makeText(this@MainActivity, "Login Success", Toast.LENGTH_SHORT).show()
                        // 로그인 성공 후 처리 추가 가능
                    } else {
                        Toast.makeText(this@MainActivity, response, Toast.LENGTH_SHORT).show()
                    }
                }, { throwable ->
                    Toast.makeText(this@MainActivity, "Login failed: ${throwable.message}", Toast.LENGTH_SHORT).show()
                    // 에러 처리
                })
        )
    }
}