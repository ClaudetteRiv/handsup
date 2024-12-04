    import android.content.Intent
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.Button
    import android.widget.ImageView
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView
    import com.example.handsup.R
    import com.example.handsup.VideoActivity

    import com.example.handsup.model.Lesson

    class LessonAdapter(
        private val lessons: List<Lesson>,
        private val onClick: (Lesson) -> Unit
    ) : RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lesson, parent, false)
            return LessonViewHolder(view)
        }

        override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
            val lesson = lessons[position]
            holder.bind(lesson)
        }

        override fun getItemCount(): Int {
            return lessons.size
        }

        inner class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val titleTextView: TextView = itemView.findViewById(R.id.tv_title)
            private val descriptionTextView: TextView = itemView.findViewById(R.id.tv_description)
            private val button: Button = itemView.findViewById(R.id.btn_start)
            private val imageView: ImageView = itemView.findViewById(R.id.iv_image)  // Agregar ImageView

            fun bind(lesson: Lesson) {
                // Asignar los valores de cada lección a las vistas
                titleTextView.text = lesson.title
                descriptionTextView.text = lesson.description
                button.text = lesson.buttonText

                // Asignar la imagen a la ImageView
                imageView.setImageResource(lesson.imageResource)  // Cargar la imagen

                // Manejar el clic en el botón
                button.setOnClickListener {
                    onClick(lesson)
                }
            }
        }
    }