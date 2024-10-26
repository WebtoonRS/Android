package com.example.a82105.login_ex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.a82105.login_ex.Retrofit.INodeJS.Webtoon

class RegistSelecViewFragment : Fragment() {
    private var webtoonList: List<Webtoon>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            webtoonList = arguments!!.getParcelableArrayList(ARG_WEBTOONS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_regist_selec_view, container, false)
        val gridLayout = view.findViewById<GridLayout>(R.id.grid_layout_webtoons)

        // 웹툰 목록을 GridLayout에 추가
        val rows = 10 // 10 rows
        val columns = 3 // 3 columns

        for (i in 0 until rows * columns) {
            if (i >= webtoonList!!.size) break

            val webtoon = webtoonList!![i]
            val imageView = ImageView(context)
            val params = GridLayout.LayoutParams()
            params.width = GridLayout.LayoutParams.WRAP_CONTENT
            params.height = GridLayout.LayoutParams.WRAP_CONTENT
            params.setMargins(8, 8, 8, 8)
            imageView.layoutParams = params
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP

            // Glide를 사용하여 썸네일 이미지 로드
            Glide.with(view)
                .load(webtoon.thumbnail_link)
                .error(R.drawable.error_image) // 에러 발생 시 보여줄 이미지
                .into(imageView)

            // GridLayout에 추가
            gridLayout.addView(imageView)
        }

        return view
    }

    companion object {
        private const val ARG_WEBTOONS = "webtoons"
        fun newInstance(webtoons: List<Webtoon>?): RegistSelecViewFragment {
            val fragment = RegistSelecViewFragment()
            val args = Bundle()
            args.putParcelableArrayList(ARG_WEBTOONS, ArrayList(webtoons))
            fragment.arguments = args
            return fragment
        }
    }
}
