package com.example.a82105.login_ex;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import android.annotation.SuppressLint;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.annotation.NonNull;

import android.view.MenuItem;


public class RealMainActivity extends AppCompatActivity {

    // BottomNavigationView 변수 선언
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_main);  // 레이아웃 설정

        // BottomNavigationView 초기화
        bottomNavigationView = findViewById(R.id.main_bottom);

        // 기본 프래그먼트 설정 (홈 프래그먼트)
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, new HomeFragment()).commit();

        // 네비게이션 아이템 선택 리스너 설정
        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            // 선택된 메뉴 항목에 따라 프래그먼트를 교체
            int itemId = menuItem.getItemId();
            if (itemId == R.id.bottom_home) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new HomeFragment()).commit();
            } else if (itemId == R.id.bottom_like) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new LikeFragment()).commit();
            } else if (itemId == R.id.bottom_mypage) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new MyPageFragment()).commit();
            }
            return true;  // 선택된 메뉴 항목이 처리되었음을 알림
        });
    }


}
