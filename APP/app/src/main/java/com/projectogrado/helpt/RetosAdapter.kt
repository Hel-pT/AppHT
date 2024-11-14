package com.projectogrado.helpt


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RetosAdapter (
    private val recompensaList: List<Retos>,
    private val onItemClick: (Retos) -> Unit
) : RecyclerView.Adapter<RetosAdapter.RecompensaViewHolder>() {

    inner class RecompensaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombreReto: TextView = itemView.findViewById(R.id.tvNombreReto)


        fun bind(recompensa: Retos) {
            tvNombreReto.text = recompensa.nombre
            itemView.setOnClickListener { onItemClick(recompensa) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecompensaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_retos, parent, false)
        return RecompensaViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecompensaViewHolder, position: Int) {
        holder.bind(recompensaList[position])
    }

    override fun getItemCount(): Int = recompensaList.size
}
