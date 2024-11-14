package com.projectogrado.helpt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MensajeAdapter(
    private val listaMensajes: List<Mensaje> // Lista de mensajes que recibirá el adaptador
) : RecyclerView.Adapter<MensajeAdapter.MensajeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mensaje, parent, false)
        return MensajeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MensajeViewHolder, position: Int) {
        val mensaje = listaMensajes[position]
        holder.bind(mensaje)
    }

    override fun getItemCount(): Int {
        return listaMensajes.size
    }

    class MensajeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //private val tvMensaje: TextView = itemView.findViewById(R.id.tvMensaje)
        // private val tvEmisor: TextView = itemView.findViewById(R.id.tvEmisor)

        fun bind(mensaje: Mensaje) {
            //  tvMensaje.text = mensaje.texto
            //  tvEmisor.text = mensaje.emisor
        }
    }
}
