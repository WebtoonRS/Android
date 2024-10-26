package com.example.a82105.login_ex

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a82105.login_ex.Retrofit.INodeJS
import com.example.a82105.login_ex.Retrofit.RetrofitClient
import com.google.android.material.button.MaterialButton
import com.rengwuxian.materialedittext.MaterialEditText
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SighUp1Activity : AppCompatActivity() {
    var myAPI: INodeJS? = null
    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    var R_edt_email: MaterialEditText? = null
    var R_edt_password: MaterialEditText? = null
    var R_edt_name: MaterialEditText? = null
    var btn_register_M: MaterialButton? = null

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
        setContentView(R.layout.activity_sigh_up1)

        // Init API
        val retrofit = RetrofitClient.instance  // 수정된 부분
        myAPI = retrofit?.create(INodeJS::class.java)

        // View
        btn_register_M = findViewById(R.id.register_button_M)
        R_edt_email = findViewById(R.id.R_edt_email)
        R_edt_password = findViewById(R.id.R_edt_password)
        R_edt_name = findViewById(R.id.R_edt_name)

        // Event
        btn_register_M?.setOnClickListener { view: View? ->
            registerUser(
                R_edt_email?.text.toString(),
                R_edt_password?.text.toString(),
                R_edt_name?.text.toString()
            )
        }
    }

    private fun registerUser(email: String, password: String, name: String) {
        // Nullable 처리 추가
        myAPI?.registerUser(email, password, name)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ s: String? ->
                // 화면 전환 코드 추가
                val intent = Intent(this@SighUp1Activity, RegistActivity::class.java)
                intent.putExtra("response", s) // 응답 데이터를 전달하고 싶을 경우
                startActivity(intent)
            }, { throwable: Throwable ->
                // 에러 처리
                Toast.makeText(
                    this@SighUp1Activity,
                    "Error: ${throwable.message}",
                    Toast.LENGTH_SHORT
                ).show()
            })?.let { compositeDisposable.add(it) }  // nullable 처리 후 compositeDisposable에 추가
    }
}