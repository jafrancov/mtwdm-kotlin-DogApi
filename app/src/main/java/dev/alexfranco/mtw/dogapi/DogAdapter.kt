package dev.alexfranco.mtw.dogapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.dog_item.view.*
import org.json.JSONArray

class DogAdapter(var list: JSONArray): RecyclerView.Adapter<DogAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bindItems(data: String) {
            Glide.with(itemView.context).load(data).into(itemView.imvImagen)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.dog_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.length()
    }

    override fun onBindViewHolder(holder: DogAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position].toString())
    }
}