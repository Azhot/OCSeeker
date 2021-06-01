package fr.azhot.ocseeker.presentation.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import fr.azhot.ocseeker.databinding.CellContentBinding
import fr.azhot.ocseeker.domain.model.Content

class ContentListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Content>() {

        override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem.toString() == newItem.toString()
        }
    }
    private val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContentViewHolder(
            CellContentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ContentViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Content>) {
        differ.submitList(list)
    }

    inner class ContentViewHolder(
        private val binding: CellContentBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(content: Content) {
            binding.root.setOnClickListener {
                interaction?.onItemSelected(content)
            }

            binding.title.text = content.title
            binding.subtitle.text = content.subtitle

            Glide.with(itemView)
                .load("https://statics.ocs.fr${content.imageUrl}")
                .apply(
                    RequestOptions().transform(
                        CenterCrop(),
                        RoundedCorners(16)
                    )
                )
                .into(binding.image)
        }
    }

    interface Interaction {
        fun onItemSelected(content: Content)
    }
}