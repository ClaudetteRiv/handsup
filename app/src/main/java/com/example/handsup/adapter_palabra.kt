package com.example.handsup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.handsup.ui.dashboard.PalabraDialogFragment

class TuAdapter(
    private var palabras: List<palabras>,
    private val onItemClick: (palabras) -> Unit
) : RecyclerView.Adapter<TuAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCircle: TextView = itemView.findViewById(R.id.tv_circle)
        val tvWord: TextView = itemView.findViewById(R.id.tv_word)

        fun bind(item: palabras) {
            tvCircle.text = item.name.firstOrNull()?.uppercase() ?: "?"
            tvWord.text = item.name

            itemView.setOnClickListener { onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_palabras, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = palabras[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            if (holder.itemView.context is FragmentActivity) {
                val activity = holder.itemView.context as FragmentActivity
                val dialog = PalabraDialogFragment(item)
                dialog.show(activity.supportFragmentManager, "PalabraDialog")
            }
        }
    }

    override fun getItemCount(): Int = palabras.size

    fun updatePalabras(newPalabras: List<palabras>) {
        palabras = newPalabras
        notifyDataSetChanged()
    }
}