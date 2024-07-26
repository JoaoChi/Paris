package com.angellira.petvital1.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.angellira.paris.databinding.ActivityRecyclerBinding
import com.angellira.paris.model.MarsPhoto

class FotosListAdapter(
    private val preferencias: List<MarsPhoto>,
    private val onItemClickListener: (String, String) -> Unit
) : RecyclerView.Adapter<FotosListAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val preferencesUsuarioBinding: ActivityRecyclerBinding
    ) :
        RecyclerView.ViewHolder(preferencesUsuarioBinding.root) {

            private lateinit var preferencia: MarsPhoto

            init {
                preferencesUsuarioBinding.root.setOnClickListener {
                    if (::preferencia.isInitialized) {
                        onItemClickListener(preferencia.img_src, preferencia.id)
                    }
                }
            }

        fun bind(preferencia: MarsPhoto) {
            this.preferencia = preferencia
            preferencesUsuarioBinding.imageRecycler.load(preferencia.img_src)
            preferencesUsuarioBinding.itemTitleMarsTextView.setText(preferencia.id)
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