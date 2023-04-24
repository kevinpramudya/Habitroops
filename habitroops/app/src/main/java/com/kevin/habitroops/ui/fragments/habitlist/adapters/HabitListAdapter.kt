package com.kevin.habitroops.ui.fragments.habitlist.adapters

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kevin.habitroops.R
import com.kevin.habitroops.data.models.Habit
import com.kevin.habitroops.databinding.FragmentCreateHabitItemBinding
import com.kevin.habitroops.databinding.FragmentHabitListBinding
import com.kevin.habitroops.databinding.RecyclerHabitItemBinding
import com.kevin.habitroops.ui.fragments.habitlist.HabitListDirections
import com.kevin.habitroops.utils.Calculations
import retrofit2.http.Tag

class HabitListAdapter : RecyclerView.Adapter<HabitListAdapter.MyViewHolder>() {

    var habitsList = emptyList<Habit>()

    inner class MyViewHolder(internal val binding: RecyclerHabitItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.cvCardView.setOnClickListener {
                val position = adapterPosition
                Log.d("HabitsListAdapter", "Item clicked at: $position")

                val action = HabitListDirections.actionHabbitListToUpdateHabitItem(habitsList[position])
                itemView.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RecyclerHabitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    //todo: initialise the recycler view and set it up to show data (part2)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentHabit = habitsList[position]
        holder.binding.ivHabitIcon.setImageResource(currentHabit.imageId)
        holder.binding.tvItemDescription.text = currentHabit.habit_description
        holder.binding.tvTimeElapsed.text =
            Calculations.calculateTimeBetweenDates(currentHabit.habit_startTime)
        holder.binding.tvItemCreatedTimeStamp.text = "Since: ${currentHabit.habit_startTime}"
        holder.binding.tvItemTitle.text = "${currentHabit.habit_title}"
    }

    override fun getItemCount(): Int {
        return habitsList.size
    }

    fun setData(habit: List<Habit>) {
        this.habitsList = habit
        notifyDataSetChanged()
    }
}
