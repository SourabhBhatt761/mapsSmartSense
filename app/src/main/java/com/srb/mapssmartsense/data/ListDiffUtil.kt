package com.srb.mapssmartsense.data

import androidx.recyclerview.widget.DiffUtil
import com.srb.mapssmartsense.db.AppEntity

class ListDiffUtil(
    private val oldList: List<AppEntity>,
    private val newList: List<AppEntity>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]

    }
}