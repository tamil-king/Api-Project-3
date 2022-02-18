package com.my.apiproject.mvvm.view

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.my.apiproject.R
import com.my.apiproject.adapter.MainListAdapter
import com.my.apiproject.databinding.ActivityMainBinding
import com.my.apiproject.mvvm.Result
import com.my.apiproject.mvvm.viewmodel.DataViewModel
import com.my.apiproject.room.entity.ItemsEntity
import com.my.apiproject.support.Constants
import com.my.apiproject.support.Utils


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataViewModel: DataViewModel
    private lateinit var adapter: MainListAdapter
    private lateinit var list: ArrayList<ItemsEntity>
    private lateinit var searchView: SearchView
    private var searchText = ""
    private var backFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        dataViewModel = ViewModelProvider(this@MainActivity)[DataViewModel::class.java]

        dataViewModel.initVieModel()
        dataViewModel.getDataList()
        list = arrayListOf()

        binding.run {

            setSupportActionBar(toolbar)

            swipeRefreshLayout.setOnRefreshListener {
                refreshListView()

                if (swipeRefreshLayout.isRefreshing) {
                    swipeRefreshLayout.isRefreshing = false
                }
            }

            tryAgainBtn.setOnClickListener {
                refreshListView()
            }


            mainList.layoutManager = GridLayoutManager(this@MainActivity, 1)
            adapter = MainListAdapter(this@MainActivity)
            mainList.adapter = adapter

            dataViewModel.getDataResponse().observe(this@MainActivity, Observer {
                if (it != null) {
                    when (it.status) {
                        Result.Status.SUCCESS -> {

                            mainList.visibility = View.VISIBLE
                            loadingProgressBar.visibility = View.GONE
                            errorLay.visibility = View.GONE

                            Log.i("dragon_test", "MainList size : ${it.data!!.size}")
                            list.addAll(it.data)
                            adapter.setList(list)

                        }

                        Result.Status.ERROR -> {

                            if (!Utils.isNetworkAvailable(this@MainActivity)) {
                                errorText.text = Constants.noInternetCon
                            } else {
                                errorText.text = it.message
                            }

                            errorLay.visibility = View.VISIBLE
                            loadingProgressBar.visibility = View.GONE
                            mainList.visibility = View.GONE
                        }

                        Result.Status.LOADING -> {
                            mainList.visibility = View.GONE
                            errorLay.visibility = View.GONE
                            loadingProgressBar.visibility = View.VISIBLE
                        }
                    }

                }
            })

        }


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)

        val searchMenuItem = menu!!.findItem(R.id.actionSearch)
        searchView = searchMenuItem.actionView as SearchView
        searchView.queryHint = "Search"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                dataViewModel.initVieModel()
                list.clear()
                searchText = "%$newText%"
                dataViewModel.getFilterData(searchText)

                return false
            }

        })

        return true
    }

    private fun refreshListView() {
        if (searchView.isIconified) {
            dataViewModel.initVieModel()
            dataViewModel.getDataList()
            list.clear()
        } else {
            dataViewModel.initVieModel()
            list.clear()
            dataViewModel.getFilterData(searchText)
        }

        //Toast.makeText(this@MainActivity,"hello : ${searchView.isIconified}",Toast.LENGTH_SHORT).show()
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