package com.angellira.petvital1.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.angellira.paris.databinding.ActivityRecyclerBinding
import com.angellira.paris.model.MarsPhoto

class FotosListAdapter(
    private val preferencias: List<MarsPhoto>
) : RecyclerView.Adapter<FotosListAdapter.ViewHolder>() {

    class ViewHolder(
        private val preferencesUsuarioBinding: ActivityRecyclerBinding
    ) :
        RecyclerView.ViewHolder(preferencesUsuarioBinding.root) {

        fun bind(preferencia: MarsPhoto) {
            preferencesUsuarioBinding.imageRecycler.load(preferencia)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder =
        ViewHolder(
            ActivityRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = preferencias.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val preferencia = preferencias[position]
        holder.bind(preferencia)
    }
}