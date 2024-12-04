package com.example.handsup.ui.home

import LessonAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.handsup.R
import com.example.handsup.model.Lesson
import com.example.handsup.VideoActivity // Asegúrate de importar VideoActivity

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflamos el layout del fragmento
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Inicializamos el RecyclerView
        recyclerView = view.findViewById(R.id.rv_lessons)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Datos de prueba para lecciones
        val lessons = listOf(
            Lesson("Abecedario", "Aprende el abecedario en Lengua de Señas Mexicana (LSM)",  R.drawable.maxresdefault, "Iniciar"),
            Lesson("Números", "Aprende los números en Lengua de Señas Mexicana (LSM)", R.drawable.numeros, "Iniciar"),
            Lesson("Colores", "Aprende los colores en Lengua de Señas Mexicana (LSM)", R.drawable.color, "Iniciar"),
            Lesson("Frutas", "Aprende los nombres de frutas en Lengua de Señas Mexicana (LSM)", R.drawable.fruta, "Iniciar"),
            Lesson("Animales", "Aprende los nombres de animales en Lengua de Señas Mexicana (LSM)", R.drawable.animal, "Iniciar")
        )

        recyclerView.adapter = LessonAdapter(lessons) { lesson ->
            // Acción al presionar el botón
            Toast.makeText(requireContext(), "Iniciando: ${lesson.title}", Toast.LENGTH_SHORT).show()

            // Crear el Intent para ir a VideoActivity
            val intent = Intent(requireContext(), VideoActivity::class.java).apply {
                putExtra("lesson_title", lesson.title) // Pasar el título de la lección
                putExtra("lesson_description", lesson.description) // Pasar la descripción
            }

            // Deshabilitar el botón para evitar múltiples clics
            recyclerView.isEnabled = false

            Log.d("HomeFragment", "Intent: $intent")
            startActivity(intent)


            // Habilitar el RecyclerView nuevamente después de un pequeño delay
            recyclerView.postDelayed({
                recyclerView.isEnabled = true
            }, 500) // Tiempo de espera para reactivar el RecyclerView
        }


        return view
    }
}
