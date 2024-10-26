package com.example.a82105.login_ex

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class RealMainActivity : AppCompatActivity() {
    // BottomNavigationView 변수 선언
    var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_main) // 레이아웃 설정

        // BottomNavigationView 초기화
        bottomNavigationView = findViewById(R.id.main_bottom)

        // 기본 프래그먼트 설정 (홈 프래그먼트)
        supportFragmentManager.beginTransaction().add(R.id.main_fragment_container, HomeFragment())
            .commit()

        // 네비게이션 아이템 선택 리스너 설정
        bottomNavigationView.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { menuItem: MenuItem ->
            // 선택된 메뉴 항목에 따라 프래그먼트를 교체
            val itemId = menuItem.itemId
            if (itemId == R.id.bottom_home) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, HomeFragment()).commit()
            } else if (itemId == R.id.bottom_like) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, LikeFragment()).commit()
            } else if (itemId == R.id.bottom_mypage) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, MyPageFragment()).commit()
            }
            true // 선택된 메뉴 항목이 처리되었음을 알림
        })
    }
}
