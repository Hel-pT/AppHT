package com.projectogrado.helpt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(
    private val listaMensajes: List<Mensaje>,
    private val usuarioActualId: String
) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mensaje, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val mensaje = listaMensajes[position]
        holder.bind(mensaje, usuarioActualId)
    }

    override fun getItemCount(): Int {
        return listaMensajes.size
    }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvMensaje: TextView = itemView.findViewById(R.id.tvMensaje)

        fun bind(mensaje: Mensaje, usuarioActualId: String) {
            // Mostrar el mensaje
            tvMensaje.text = mensaje.contenido

            // Cambiar el diseño según el remitente
            val layoutParams = tvMensaje.layoutParams as ViewGroup.MarginLayoutParams
            if (mensaje.remitente == usuarioActualId) {
                // Mensaje enviado por el usuario actual
                layoutParams.marginEnd = 16
                layoutParams.marginStart = 64
            } else {
                // Mensaje enviado por otro usuario (doctor o responsable)
                layoutParams.marginStart = 16
                layoutParams.marginEnd = 64
            }
            tvMensaje.layoutParams = layoutParams
        }
    }
}
