package com.example.a82105.login_ex;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.GridLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.a82105.login_ex.Retrofit.INodeJS;
import java.util.List;
import java.util.ArrayList;

public class RegistSelecViewFragment extends Fragment {

    private static final String ARG_WEBTOONS = "webtoons";
    private List<INodeJS.Webtoon> webtoonList;

    public static RegistSelecViewFragment newInstance(List<INodeJS.Webtoon> webtoons) {
        RegistSelecViewFragment fragment = new RegistSelecViewFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_WEBTOONS, new ArrayList<>(webtoons));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            webtoonList = getArguments().getParcelableArrayList(ARG_WEBTOONS);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_regist_selec_view, container, false);
        GridLayout gridLayout = view.findViewById(R.id.grid_layout_webtoons);

        // 웹툰 목록을 GridLayout에 추가
        int rows = 10; // 10 rows
        int columns = 3; // 3 columns

        for (int i = 0; i < rows * columns; i++) {
            if (i >= webtoonList.size()) break;

            INodeJS.Webtoon webtoon = webtoonList.get(i);
            ImageView imageView = new ImageView(getContext());
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.setMargins(8, 8, 8, 8);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // Glide를 사용하여 썸네일 이미지 로드
            Glide.with(view)
                    .load(webtoon.getThumbnail_link())
                    .error(R.drawable.error_image) // 에러 발생 시 보여줄 이미지
                    .into(imageView);

            // GridLayout에 추가
            gridLayout.addView(imageView);
        }

        return view;
    }
}
