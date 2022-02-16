package com.my.apiproject.mvvm.view

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.my.apiproject.adapter.MainListAdapter
import com.my.apiproject.databinding.ActivityMainBinding
import com.my.apiproject.mvvm.model.Item
import com.my.apiproject.mvvm.viewmodel.DataViewModel


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageViewModel: DataViewModel
    private lateinit var adapter: MainListAdapter
    private lateinit var list: List<Item>
    private var backFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageViewModel = ViewModelProvider(this@MainActivity)[DataViewModel::class.java]

        imageViewModel.initVieModel()
        imageViewModel.getDataList()
        list = emptyList()

        binding.run {

            mainList.layoutManager = GridLayoutManager(this@MainActivity, 1)
            adapter = MainListAdapter(this@MainActivity)
            mainList.adapter = adapter

            imageViewModel.getDataResponse().observe(this@MainActivity, Observer {
                Log.i("dragon_test","MainList size : ${it.size}")
                list = it
                adapter.setList(list)
            })

        }


    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE || newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.mainList.adapter = adapter
            adapter.setList(list)
        }
    }

    override fun onResume() {
        super.onResume()

        backFlag = false
    }

    override fun onBackPressed() {
        if (backFlag) {
            finish()
        } else {
            backFlag = true
            Toast.makeText(this@MainActivity, "Press again to Exit", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroy() {
        if (_binding != null) {
            _binding = null
        }
        super.onDestroy()
    }

}