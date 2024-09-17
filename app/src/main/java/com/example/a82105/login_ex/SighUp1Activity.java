package com.example.a82105.login_ex;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


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


public class SighUp1Activity extends AppCompatActivity {

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    MaterialEditText R_edt_email, R_edt_password, R_edt_name ;
    MaterialButton btn_register_M;

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
        setContentView(R.layout.activity_sigh_up1);

        // Init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        // View

        btn_register_M = findViewById(R.id.register_button_M);

        R_edt_email = findViewById(R.id.R_edt_email);
        R_edt_password = findViewById(R.id.R_edt_password);
        R_edt_name = findViewById(R.id.R_edt_name);


        // Event
        btn_register_M.setOnClickListener(view -> registerUser(R_edt_email.getText().toString(),R_edt_password.getText().toString() ,R_edt_name.getText().toString() ));
    }

    private void registerUser(String email, String password, String name ) {

        compositeDisposable.add(myAPI.registerUser(email, password, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    // 화면 전환 코드 추가
                    Intent intent = new Intent(SighUp1Activity.this, RegistActivity.class);
                    intent.putExtra("response", s); // 응답 데이터를 전달하고 싶을 경우
                    startActivity(intent);
                }, throwable -> {
                    // 에러 처리
                    Toast.makeText(SighUp1Activity.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));
    }


}
