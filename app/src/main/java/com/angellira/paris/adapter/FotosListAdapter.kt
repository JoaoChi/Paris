package com.angellira.petvital1.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.angellira.paris.databinding.ActivityRecyclerBinding
import com.angellira.paris.model.SportPhoto

class FotosListAdapter(
    private val posts: List<SportPhoto>,
    private val onItemClickListener: (String, String, String) -> Unit
) : RecyclerView.Adapter<FotosListAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val preferencesUsuarioBinding: ActivityRecyclerBinding
    ) :
        RecyclerView.ViewHolder(preferencesUsuarioBinding.root) {

            private lateinit var post: SportPhoto

            init {
                preferencesUsuarioBinding.root.setOnClickListener {
                    if (::post.isInitialized) {
                        onItemClickListener(post.img, post.name, post.description)
                    }
                }
            }

        fun bind(preferencia: SportPhoto) {
            this.post = preferencia
            preferencesUsuarioBinding.imageRecycler.load(post.img)
            preferencesUsuarioBinding.itemTitleMarsTextView.setText(post.name)
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

    override fun getItemCount(): Int = posts.size


    override fun onBindViewHolder(holder: ViewHolder,   position: Int) {
        val preferencia = posts[position]
        holder.bind(preferencia!!)
    }
}