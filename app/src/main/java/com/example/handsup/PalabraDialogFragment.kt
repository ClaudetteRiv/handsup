package com.example.handsup.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.handsup.R
import com.example.handsup.palabras

class PalabraDialogFragment(private val palabra: palabras) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_palabra, container, false)

        val ivImage: ImageView = view.findViewById(R.id.iv_palabra_image)
        val tvName: TextView = view.findViewById(R.id.tv_palabra_name)
        val btnClose: Button = view.findViewById(R.id.btn_close)

        tvName.text = palabra.name

        Glide.with(requireContext())
            .load(palabra.rol) // URL de la imagen
            .placeholder(R.drawable.numeros) // Imagen de placeholder
            .into(ivImage)

        // Botón para cerrar el diálogo
        btnClose.setOnClickListener { dismiss() }

        return view
    }
}