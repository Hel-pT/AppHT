package com.projectogrado.helpt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ResultadoAdapter(private val listaResultados: List<Resultado>) :
    RecyclerView.Adapter<ResultadoAdapter.ResultadoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultadoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resultado, parent, false)
        return ResultadoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultadoViewHolder, position: Int) {
        val resultado = listaResultados[position]
        holder.bind(resultado)
    }

    override fun getItemCount(): Int {
        return listaResultados.size
    }

    class ResultadoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombreTerapia: TextView = itemView.findViewById(R.id.tvNombreTerapia)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        private val tvPorcentaje: TextView = itemView.findViewById(R.id.tvPorcentaje)

        fun bind(resultado: Resultado) {
            tvNombreTerapia.text = resultado.nombreTerapia
            progressBar.progress = resultado.porcentajeProgreso.toInt()
            tvPorcentaje.text = "${resultado.porcentajeProgreso.toInt()}%"
        }
    }
}
