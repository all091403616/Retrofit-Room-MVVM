package com.example.retrofittest.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.retrofittest.NewsApp
import com.example.retrofittest.R
import com.example.retrofittest.databinding.NewsDetailsFragmentBinding
import com.example.retrofittest.viewmodel.DetailsFragmentViewModel
import com.example.retrofittest.viewmodel.DetailsViewModelFactory

class DetailsFragment:Fragment() {
    private val viewModel: DetailsFragmentViewModel by viewModels {
        DetailsViewModelFactory((requireActivity().application as NewsApp).repository)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding=NewsDetailsFragmentBinding.inflate(inflater,container,false)
        binding.viewModel=viewModel
        binding.lifecycleOwner=this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id:Long
        if (requireActivity().intent?.dataString!=null)
            id=requireActivity().intent?.data?.getQueryParameter("id")?.toLongOrNull()?:0
        else {
            id=navArgs<DetailsFragmentArgs>().value.id
            setHasOptionsMenu(true)
        }
        viewModel.getData(id)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_top_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.delete_menu){
            AlertDialog.Builder(requireContext())
                    .setMessage("Delete?")
                    .setNegativeButton("No"){dialog,_->dialog.dismiss()}
                    .setPositiveButton("Yes"){dialog,_->
                        dialog.dismiss()
                        viewModel.delete()
                        findNavController().navigateUp()
                    }.show()
        }else if (item.itemId==R.id.share_news_menu){
            val intent=Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT,"${(requireActivity().application as NewsApp).link}${viewModel.getId()}")
                type="text/*"
            }
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}