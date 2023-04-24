package com.kevin.habitroops.ui.introscreen


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevin.habitroops.R
import com.kevin.habitroops.data.models.IntroView
import com.kevin.habitroops.databinding.IntroItemPageBinding

class ViewPagerAdapter(private val introViews: List<IntroView>) : RecyclerView.Adapter<ViewPagerAdapter.IntroViewHolder>() {

    class IntroViewHolder(private val binding: IntroItemPageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentView: IntroView) {
            binding.ivImageIntro.setImageResource(currentView.imageId)
            binding.tvDescriptionIntro.text = currentView.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroViewHolder {
        val binding = IntroItemPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IntroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        val currentView = introViews[position]
        holder.bind(currentView)
    }

    override fun getItemCount(): Int {
        return introViews.size
    }
}
