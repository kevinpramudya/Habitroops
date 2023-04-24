package com.kevin.habitroops.ui.fragments.updatehabit


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kevin.habitroops.R
import com.kevin.habitroops.data.models.Habit
import com.kevin.habitroops.databinding.FragmentCreateHabitItemBinding
import com.kevin.habitroops.databinding.FragmentUpdateHabitItemBinding
import com.kevin.habitroops.ui.viewmodels.HabitViewModels
import com.kevin.habitroops.utils.Calculations
import java.util.*


class UpdateHabitItem : Fragment(R.layout.fragment_update_habit_item),
    TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private var _binding: FragmentUpdateHabitItemBinding? = null
    private val binding get() = _binding!!

    private var title = ""
    private var description = ""
    private var drawableSelected = 0
    private var timeStamp = ""


    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var cleanDate = ""
    private var cleanTime = ""

    private lateinit var habitViewModels: HabitViewModels
    private val args by navArgs<UpdateHabitItemArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        habitViewModels = ViewModelProvider(this).get(HabitViewModels::class.java)
        binding.etHabitTitleUpdate.setText(args.selectedHabit.habit_title)
        binding.etHabitDescriptionUpdate.setText(args.selectedHabit.habit_description)

        drawableSelected()

        pickDateAndTime()

        binding.btnConfirmUpdate.setOnClickListener {
            updateHabit()
        }
        setHasOptionsMenu(true)
    }

    private fun updateHabit() {
        title = binding.etHabitTitleUpdate.text.toString()
        description = binding.etHabitDescriptionUpdate.text.toString()

        timeStamp = "$cleanDate $cleanTime"
        if (!(title.isEmpty() || description.isEmpty() || timeStamp.isEmpty() || drawableSelected == 0)) {
            val habit = Habit(args.selectedHabit.id, title, description, timeStamp, drawableSelected)

            habitViewModels.updateHabit(habit)
            Toast.makeText(context, "Habit updated successfully!", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_updateHabitItem_to_habbitList)
        }else{
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }


    private fun drawableSelected() {
        binding.ivFastFoodSelectedUpdate.setOnClickListener {
            binding.ivFastFoodSelectedUpdate.isSelected =
                !binding.ivFastFoodSelectedUpdate.isSelected
            drawableSelected = R.drawable.ic_fastfood

            //de-select the other options when we pick an image
            binding.ivSmokingSelectedUpdate.isSelected = false
            binding.ivTeaSelectedUpdate.isSelected = false
        }

        binding.ivSmokingSelectedUpdate.setOnClickListener {
            binding.ivSmokingSelectedUpdate.isSelected = !binding.ivSmokingSelectedUpdate.isSelected
            drawableSelected = R.drawable.ic_smoking2

            //de-select the other options when we pick an image
            binding.ivFastFoodSelectedUpdate.isSelected = false
            binding.ivTeaSelectedUpdate.isSelected = false
        }

        binding.ivTeaSelectedUpdate.setOnClickListener {
            binding.ivTeaSelectedUpdate.isSelected = !binding.ivTeaSelectedUpdate.isSelected
            drawableSelected = R.drawable.ic_tea

            //de-select the other options when we pick an image
            binding.ivSmokingSelectedUpdate.isSelected = false
            binding.ivFastFoodSelectedUpdate.isSelected = false
        }


    }


    private fun pickDateAndTime() {
        binding.btnPickDateUpdate.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        binding.btnPickTimeUpdate.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(context, this, hour, minute, true).show()

        }
    }


    //get the current time
    private fun getTimeCalendar() {
        val cal = Calendar.getInstance()
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }

    //get the current date
    private fun getDateCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    override fun onDateSet(view: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {
        cleanDate = Calculations.cleanDate(dayX, monthX, yearX)
        binding.tvDateSelectedUpdate.text = "Date: $cleanDate"
    }


    override fun onTimeSet(view: TimePicker?, p1: Int, p2: Int) {
        //Log.d("Fragment", "Time: $hourOfDay:$minuteX")
        cleanTime = Calculations.cleanTime(p1, p2)
        binding.tvTimeSelectedUpdate.text = "Time: $cleanTime"
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.single_item_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_delete -> deleteHabit(args.selectedHabit)
        }


        return super.onOptionsItemSelected(item)
    }

    private fun deleteHabit(habit: Habit) {
        habitViewModels.deleteHabit(habit)
        Toast.makeText(context, "Habit successfully deleted!", Toast.LENGTH_SHORT).show()

        findNavController().navigate(R.id.action_updateHabitItem_to_habbitList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}