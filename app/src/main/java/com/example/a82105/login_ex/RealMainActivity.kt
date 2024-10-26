package com.example.a82105.login_ex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a82105.login_ex.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class RealMainActivity : AppCompatActivity() {

    // BottomNavigationView 변수 선언
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_main)  // 레이아웃 설정

        // BottomNavigationView 초기화
        bottomNavigationView = findViewById(R.id.main_bottom)

        // 기본 프래그먼트 설정 (홈 프래그먼트)
        supportFragmentManager.beginTransaction().add(R.id.main_fragment_container, HomeFragment()).commit()

        // 네비게이션 아이템 선택 리스너 설정
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, HomeFragment()).commit()
                }
                R.id.bottom_like -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, LikeFragment()).commit()
                }
                R.id.bottom_mypage -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, MyPageFragment()).commit()
                }
            }
            true  // 선택된 메뉴 항목이 처리되었음을 알림
        }
    }
}