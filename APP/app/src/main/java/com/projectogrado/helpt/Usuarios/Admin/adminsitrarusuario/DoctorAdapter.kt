package com.projectogrado.helpt.Usuarios.Admin.adminsitrarusuario

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projectogrado.helpt.Doctor
import com.projectogrado.helpt.R

class DoctorAdapter(
    private val doctorList: List<Doctor>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    class DoctorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombreDoctor: TextView = view.findViewById(R.id.tvNombreDoctor)
        val tvDocumentoDoctor: TextView = view.findViewById(R.id.tvDocumentoDoctor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_doctor, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctorList[position]

        // Setear los valores del doctor
        holder.tvNombreDoctor.text = doctor.nombre
        holder.tvDocumentoDoctor.text = doctor.documento

        // Configurar clic para modificar doctor
        holder.itemView.setOnClickListener {
            onItemClick(doctor.documento) // Pasar el ID del documento (documento)
        }
    }

    override fun getItemCount(): Int {
        return doctorList.size
    }
}
