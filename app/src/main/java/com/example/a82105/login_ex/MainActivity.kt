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

class MainActivity : AppCompatActivity() {
    var myAPI: INodeJS? = null
    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    var edt_email: MaterialEditText? = null
    var edt_password: MaterialEditText? = null
    var btn_register: MaterialButton? = null
    var btn_login: MaterialButton? = null
    var btn_next: MaterialButton? = null

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
        val retrofit = RetrofitClient.getInstance()
        myAPI = retrofit.create(INodeJS::class.java)

        // View
        btn_login = findViewById(R.id.login_button)
        btn_register = findViewById(R.id.register_button)

        edt_email = findViewById(R.id.edt_email)
        edt_password = findViewById(R.id.edt_password)

        // Event
        btn_login.setOnClickListener(View.OnClickListener { view: View? ->
            loginUser(
                edt_email.getText().toString(), edt_password.getText().toString()
            )
        })

        btn_register.setOnClickListener(View.OnClickListener { view: View? ->
            val intent = Intent(applicationContext, SighUp1Activity::class.java)
            startActivity(intent)
        })


        btn_next = findViewById(R.id.im)

        btn_next.setOnClickListener(View.OnClickListener { view: View? ->
            val intent = Intent(applicationContext, RealMainActivity::class.java)
            startActivity(intent)
        })
    }

    private fun loginUser(email: String, password: String) {
        compositeDisposable.add(
            myAPI!!.loginUser(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { s: String ->
                    if (s.contains("encrypted_password")) Toast.makeText(
                        this@MainActivity,
                        "Login Success",
                        Toast.LENGTH_SHORT
                    ).show()
                    else Toast.makeText(this@MainActivity, "" + s, Toast.LENGTH_SHORT).show()
                })
    } /*
    private void registerUser(String email, String password) {
        // with Register, we need one more step to enter name (+ Email password)
        View enter_name_view = LayoutInflater.from(this).inflate(R.layout.enter_name_layout, null);

        new MaterialDialog.Builder(this)
                .title("One More Step!")
                .customView(enter_name_view, true)
                .iconRes(R.drawable.ic_user)
                .negativeText("Cancel")
                .onNegative((dialog, which) -> dialog.dismiss())
                .positiveText("Register")
                .onPositive((dialog, which) -> {
                    MaterialEditText edt_name = enter_name_view.findViewById(R.id.edt_name);

                    compositeDisposable.add(myAPI.registerUser(email, edt_name.getText().toString(), password)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(s -> {
                                // 화면 전환 코드 추가
                                Intent intent = new Intent(MainActivity.this, RegistActivity.class);
                                intent.putExtra("response", s); // 응답 데이터를 전달하고 싶을 경우
                                startActivity(intent);
                            }, throwable -> {
                                // 에러 처리
                                Toast.makeText(MainActivity.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }));
                })
                .show();
    }
    */
}
