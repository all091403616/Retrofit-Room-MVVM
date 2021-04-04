package com.example.retrofittest.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofittest.NewsApp
import com.example.retrofittest.R
import com.example.retrofittest.databinding.ListFragmentBinding
import com.example.retrofittest.model.datamodel.News
import com.example.retrofittest.utils.NewsListAdapter
import com.example.retrofittest.utils.States
import com.example.retrofittest.viewmodel.ListViewModel
import com.example.retrofittest.viewmodel.ListViewModelFactory

class ListFragment:Fragment() , SearchView.OnQueryTextListener{
    private lateinit var binding: ListFragmentBinding
    private val viewModel: ListViewModel by viewModels {
        ListViewModelFactory(
                (requireActivity().application as NewsApp).checkConnection,
                ((requireActivity().application as NewsApp).repository)
        )
    }
    private lateinit var adapter:NewsListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchNewData()
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding= ListFragmentBinding.inflate(inflater,container,false)
        binding.viewModel=viewModel
        binding.lifecycleOwner=this
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= NewsListAdapter(null)
        linearLayoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.recyclerView.layoutManager=linearLayoutManager
        binding.recyclerView.adapter=adapter
        setupObservers()

        binding.refreshLayout.setOnRefreshListener {
            viewModel.fetchNewData()
            binding.refreshLayout.isRefreshing=false
        }
    }

    private fun setupObservers(){
        viewModel.news.observe(this.viewLifecycleOwner, {
            adapter.refresh(it as ArrayList<News>)
        })

        viewModel.message.observe(this.viewLifecycleOwner,{
            if (it!=null){
                Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
                viewModel.messageReceived()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_top_menu,menu)
        val searchView:SearchView=menu.findItem(R.id.search_menu).actionView as SearchView
        searchView.isSubmitButtonEnabled=false
        searchView.queryHint=viewModel.getSearchHint()
        searchView.setOnQueryTextListener(this)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.searchNews(newText?:"")
        return true
    }
}