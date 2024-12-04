package com.example.handsup.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.handsup.TuAdapter
import com.example.handsup.databinding.FragmentDashboardBinding
import com.example.handsup.palabras
import com.google.firebase.firestore.FirebaseFirestore
import androidx.appcompat.widget.SearchView

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()
    private lateinit var adapter: TuAdapter
    private var palabraList: List<palabras> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        // Configurar RecyclerView
        val recyclerView = binding.letrasDato
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Inicializar Adapter con una lista vacía y manejar clics
        adapter = TuAdapter(emptyList()) { palabra ->
            // Acción al hacer clic en un elemento
            showToast("Seleccionaste: ${palabra.name}")
        }
        recyclerView.adapter = adapter

        // Configurar SearchView
        val searchView: SearchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Opcional: realizar alguna acción cuando se envíe la búsqueda
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filtrar las palabras según el texto ingresado
                newText?.let {
                    filterPalabras(it)
                }
                return true
            }
        })

        // Consultar datos desde Firestore
        loadPalabrasFromFirestore()

        return binding.root
    }

    private fun loadPalabrasFromFirestore() {
        db.collection("palabras")
            .get()
            .addOnSuccessListener { querySnapshot ->
                // Mapeamos los datos obtenidos de Firestore
                palabraList = querySnapshot.mapNotNull { document ->
                    val id = document.id
                    val name = document.getString("name")
                    val rol = document.getString("rol")
                    if (name != null && rol != null) palabras(id, name, rol) else null
                }

                // Ordenamos la lista de palabras alfabéticamente por el campo "name"
                palabraList = palabraList.sortedBy { it.name }

                // Actualizamos el Adapter con los datos obtenidos y ordenados
                adapter.updatePalabras(palabraList)

                // Log para depuración
                Log.d("DashboardFragment", "Total de palabras obtenidas: ${palabraList.size}")
            }
            .addOnFailureListener { exception ->
                Log.e("DashboardFragment", "Error al cargar las palabras", exception)
            }
    }


    private fun filterPalabras(query: String) {
        // Filtrar las palabras que coincidan con el texto ingresado
        val filteredList = palabraList.filter {
            it.name.contains(query, ignoreCase = true)
        }
        // Actualizar el Adapter con la lista filtrada
        adapter.updatePalabras(filteredList)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
