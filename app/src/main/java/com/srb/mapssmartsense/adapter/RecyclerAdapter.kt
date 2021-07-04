package com.srb.mapssmartsense.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.srb.mapssmartsense.data.ListDiffUtil
import com.srb.mapssmartsense.databinding.ItemListBinding
import com.srb.mapssmartsense.db.AppEntity
import java.lang.Appendable

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var dataList: List<AppEntity> = listOf()

    class ViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
Log.i("unisex",dataList.toString())
        return ViewHolder(
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataList[position]

        holder.binding.itemId.text = currentItem.id.toString()
        holder.binding.itemLat.text = currentItem.latitude.toString()
        holder.binding.itemLong.text = currentItem.longitude.toString()
        holder.binding.itemPlace.text = currentItem.place
        holder.binding.itemTime.text = currentItem.timeClicked

    }

    override fun getItemCount(): Int  = dataList.size


    fun setData(appEntity: List<AppEntity>) {

        val listDiffUtil = ListDiffUtil(dataList, appEntity)
        val listDiffResult = DiffUtil.calculateDiff(listDiffUtil)
        this.dataList = appEntity
        listDiffResult.dispatchUpdatesTo(this)

    }
}