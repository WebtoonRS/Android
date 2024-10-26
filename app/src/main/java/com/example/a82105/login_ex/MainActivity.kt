package com.example.a82105.login_ex;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import android.content.Intent;

import com.example.a82105.login_ex.Retrofit.INodeJS;
import com.example.a82105.login_ex.Retrofit.RetrofitClient;
import com.google.android.material.button.MaterialButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    MaterialEditText edt_email, edt_password;
    MaterialButton btn_register, btn_login, btn_next;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        // View
        btn_login = findViewById(R.id.login_button);
        btn_register = findViewById(R.id.register_button);

        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);

        // Event
        btn_login.setOnClickListener(view -> loginUser(edt_email.getText().toString(), edt_password.getText().toString()));

        btn_register.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SighUp1Activity.class);
            startActivity(intent);
        });


        btn_next = findViewById(R.id.im);

        btn_next.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RealMainActivity.class);
            startActivity(intent);
        });



    }

    private void loginUser(String email, String password) {
        compositeDisposable.add(myAPI.loginUser(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (s.contains("encrypted_password"))
                        Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, "" + s, Toast.LENGTH_SHORT).show();
                }));
    }


     /*
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
