package ua.com.tabarkevych.pecoade_app.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ua.com.tabarkevych.pecoade_app.data.s.entity.Page
import ua.com.tabarkevych.pecoade_app.databinding.ItemViewPagerBinding

class ViewPagerAdapter(
    private var listener: EventListener
) : ListAdapter<Page, RecyclerView.ViewHolder>(PageDiffCallback)
{
     var pagesList = emptyList<Page>()

    interface EventListener {
        fun onNotificationClicked(position: Int)
        fun onButtonIncreaseClicked(position: Int)
        fun onButtonDecreaseClicked(lastItem: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerItemViewHolder {
        return ViewPagerItemViewHolder(
            ItemViewPagerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setData(pages: List<Page>) {
        this.pagesList = pages
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh =  (holder as ViewPagerItemViewHolder)
        with(vh.binding) {
            textNotification.setOnClickListener {
                listener.onNotificationClicked(position + 1)
            }

            imageDecrease.visibility = if (position == 0) View.GONE else View.VISIBLE
            imageRectangle.text = (position + 1).toString()

            imageIncrease.setOnClickListener { listener.onButtonIncreaseClicked(itemCount + 1) }
            imageDecrease.setOnClickListener { listener.onButtonDecreaseClicked(itemCount) }

        }
    }

    override fun getItemCount(): Int {
        return pagesList.size
    }

    class ViewPagerItemViewHolder(var binding: ItemViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root)


}

private object PageDiffCallback : DiffUtil.ItemCallback<Page>() {
    override fun areItemsTheSame(
        oldItem: Page,
        newItem: Page
    ): Boolean {
        val oldItemId = oldItem.id
        val newItemId = newItem.id
        return oldItemId == newItemId
    }

    override fun areContentsTheSame(
        oldItem: Page,
        newItem: Page
    ): Boolean {
        return oldItem == newItem
    }
}
