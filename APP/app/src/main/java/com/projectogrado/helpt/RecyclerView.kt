package com.projectogrado.helpt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerView {
    data class Mensaje(
        val contenido: String = "",
        val remitente: String = ""
    )

    class MensajeAdapter(private val listaMensajes: List<Mensaje>) :
        RecyclerView.Adapter<MensajeAdapter.MensajeViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajeViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mensaje, parent, false)
            return MensajeViewHolder(view)
        }

        override fun onBindViewHolder(holder: MensajeViewHolder, position: Int) {
            val mensaje = listaMensajes[position]
            holder.bind(mensaje)
        }

        override fun getItemCount(): Int = listaMensajes.size

        class MensajeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            // private val tvContenido: TextView = itemView.findViewById(R.id.tvContenido)
            //private val tvRemitente: TextView = itemView.findViewById(R.id.tvRemitente)

            fun bind(mensaje: Mensaje) {
                //    tvContenido.text = mensaje.contenido
                //   tvRemitente.text = mensaje.remitente
            }
        }
    }
}