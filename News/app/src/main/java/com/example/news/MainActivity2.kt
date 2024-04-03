package com.example.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        replaceFragment(NewsFragment())
    }

    private fun replaceFragment(newsFragment: Fragment){
        val fragementManager = supportFragmentManager
        val fragmentTransaction = fragementManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, newsFragment)
//        fragmentTransaction.addToBackStack("news_list")
//        Log.d("----------", "onBackPressed: ${supportFragmentManager.backStackEntryCount}")
        fragmentTransaction.commit()

    }

}

//[news_list, details]