package com.example.a82105.login_ex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 초기 설정이 필요하다면 여기에 추가
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // SearchView 초기화
        SearchView searchView = view.findViewById(R.id.search_view);


        // SearchView에 QueryTextListener 추가
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchWebtoon(query);  // 입력된 텍스트를 이용해 검색 수행
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 텍스트 변경 중 처리할 내용이 있으면 여기에 작성
                return false;
            }
        });

        return view;
    }

    //  검색 메서드 (추가로 구현 필요)
    private void searchWebtoon(String query) {
        // 검색 로직을 여기에 구현 (API 호출 또는 데이터 필터링 등)
    }
}