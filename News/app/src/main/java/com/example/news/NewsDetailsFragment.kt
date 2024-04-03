package com.example.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val NEWS_TITLE = "news_title"
private const val NEWS_IMAGE = "image"
private const val NEWS_DATE = "date"
private const val NEWS_DESCRIPTION = "description"

private lateinit var news_image: ImageView
private lateinit var tv_title: TextView;
private lateinit var tv_date: TextView;
private lateinit var tv_decription: TextView;


/**
 * A simple [Fragment] subclass.
 * Use the [NewsDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsDetailsFragment : Fragment() {
    private var news_title: String? = null
    private var image: String? = null
    private var date: String? = null
    private var description: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            news_title = it.getString(NEWS_TITLE)
            image = it.getString(NEWS_IMAGE)
            date = it.getString(NEWS_DATE)
            description = it.getString(NEWS_DESCRIPTION)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_title = view.findViewById(R.id.tv_news_title)
        tv_decription = view.findViewById(R.id.tv_news_description)
        tv_date = view.findViewById(R.id.tv_news_date)
        news_image = view.findViewById(R.id.news_image)

        tv_title.text = news_title
        tv_decription.text = description
        tv_date.text = date?.subSequence(0,10)

        Glide.with(view.context)
            .load(image)
            .into(news_image)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewsDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsDetailsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}