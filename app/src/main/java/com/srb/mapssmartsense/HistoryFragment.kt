package com.srb.mapssmartsense

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.srb.mapssmartsense.adapter.RecyclerAdapter
import com.srb.mapssmartsense.databinding.ActivityHistoryBinding
import com.srb.mapssmartsense.db.AppEntity

class HistoryFragment : Fragment() {


    private lateinit var binding: ActivityHistoryBinding
    private val mViewModel by viewModels<MapViewModel>()
    private val mAdapter = RecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityHistoryBinding.inflate(layoutInflater)

        binding.historyRv.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        mViewModel.getAllData.observe(viewLifecycleOwner,{

            if(it.isEmpty()){
                binding.historyNoData.visibility = View.VISIBLE
                binding.historyNoDataTv.visibility = View.VISIBLE
                binding.historyDelete.visibility = View.GONE
                binding.historyRv.visibility = View.GONE
            }else {
                binding.historyNoData.visibility = View.GONE
                binding.historyNoDataTv.visibility = View.GONE
                binding.historyDelete.visibility = View.VISIBLE
                binding.historyRv.visibility = View.VISIBLE
                mAdapter.setData(it)
                Log.i("uni", it.toString())
            }
        })


        binding.historyDelete.setOnClickListener {
            mViewModel.deleteAllAppData()
        }


        return binding.root

    }



}