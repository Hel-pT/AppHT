package com.projectogrado.helpt


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TerapiaAdapter(
    private val listaTerapias: List<Terapia>,
    private val onItemClick: (Terapia) -> Unit
) : RecyclerView.Adapter<TerapiaAdapter.TerapiaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TerapiaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_terapia, parent, false)
        return TerapiaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TerapiaViewHolder, position: Int) {
        val terapia = listaTerapias[position]
        holder.bind(terapia, onItemClick)
    }

    override fun getItemCount(): Int {
        return listaTerapias.size
    }

    class TerapiaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombreTerapia: TextView = itemView.findViewById(R.id.tvNombreTerapia)

        fun bind(terapia: Terapia, onItemClick: (Terapia) -> Unit) {
            tvNombreTerapia.text = terapia.nombre
            itemView.setOnClickListener { onItemClick(terapia) }
        }
    }
}
