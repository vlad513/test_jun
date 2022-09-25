package com.example.testprojeckt.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.testprojeckt.R
import com.example.testprojeckt.Server.DataUser
import com.example.testprojeckt.databinding.ItemListUserBinding
import com.squareup.picasso.Picasso

class AdapterRecicler(private var callback: (DataUser) -> Unit) :
    RecyclerView.Adapter<AdapterRecicler.RepozHolder>() {
    val repozList = ArrayList<DataUser>()

    class RepozHolder(item: View, private var callback: (DataUser) -> Unit) :
        RecyclerView.ViewHolder(item) {
        val binding = ItemListUserBinding.bind(item)
        fun bind(repoz: DataUser) = with(binding) {
            if (repoz.avatar == null) {
                imageView2.setImageResource(R.drawable.cee2686dad9a4221694469b5d7f7f8ed)
            } else {
                Picasso.with(itemView.context)
                    .load(repoz.avatar)
                    .into(binding.imageView2)
            }
            tvLogin.text = repoz.login
            tvId.text = repoz.id
            CardView.setOnClickListener { callback.invoke(repoz) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepozHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_user, parent, false)
        return RepozHolder(view, callback)
    }

    override fun onBindViewHolder(holder: RepozHolder, position: Int) {
        holder.bind(repozList[position])
    }

    override fun getItemCount(): Int {
        return repozList.size
    }

    fun addRepoz(repoz: List<DataUser>) {
        repozList.clear()
        repozList.addAll(repoz)
        notifyDataSetChanged()
    }
}
