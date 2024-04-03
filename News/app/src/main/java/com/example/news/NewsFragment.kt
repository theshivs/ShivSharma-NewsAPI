package com.example.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var adapter: NewsAdapter
private lateinit var recyclerView: RecyclerView
private lateinit var newsArrayList: ArrayList<News>

private val BASE_URL: String = "https://newsapi.org/v2/"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.news_recyclerview)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

//        Log.d("API Error in adapter--", newsArrayList.size.toString())

        adapter = NewsAdapter(arrayListOf<News>())
        recyclerView.adapter = adapter

        initData()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun initData(){
        newsArrayList = arrayListOf<News>()

        getAllNews()
    }

    private fun getAllNews(){
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                // Switch to IO context for network operations
                val newsArrayList = withContext(Dispatchers.IO) {
                    val response = api.getNews()
//                    delay(3000) // Simulate delay
                    if (response.status == "ok") {
                        val articles = response.articles

                        for (article in articles) {
                            newsArrayList.add(
                                News(
                                    article.author ?: "Unknown",
                                    article.title ?: "Unknown",
                                    article.description ?: "Unknown",
                                    article.url ?: "Unknown",
                                    article.urlToImage ?: "Unknown",
                                    article.publishedAt ?: "Unknown",
                                    article.content ?: "Unknown"
                                )
                            )
                        }
                        newsArrayList
                    } else {
                        Log.e("API Error", "Error response: ${response.status}")
                        ArrayList(emptyList()) // Return empty list if there's an error
                    }
                }
                // Update adapter with new data on the main thread
                adapter.updateData(newsArrayList)
            } catch (e: Exception) {
                Log.e("API Error", "Exception: ${e.message}")
            }
        }
    }
}