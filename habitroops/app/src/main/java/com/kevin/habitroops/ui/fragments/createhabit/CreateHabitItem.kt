package com.kevin.habitroops.ui.fragments.createhabit


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kevin.habitroops.R
import com.kevin.habitroops.data.models.Habit
import com.kevin.habitroops.databinding.FragmentCreateHabitItemBinding
import com.kevin.habitroops.ui.viewmodels.HabitViewModels
import com.kevin.habitroops.utils.Calculations
import java.util.*


class CreateHabitItem : Fragment(R.layout.fragment_create_habit_item), TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener {

    private var _binding: FragmentCreateHabitItemBinding? = null
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentCreateHabitItemBinding.bind(view)
        habitViewModels = ViewModelProvider(this).get(HabitViewModels::class.java)

        binding.btnConfirm.setOnClickListener {
            addHabitToDB()
        }

        pickDateAndTime()

        drawableSelected()




    }

    private fun addHabitToDB() {
        title = binding.etHabitTitle.text.toString()
        description = binding.etHabitDescription.text.toString()

        timeStamp = "$cleanDate $cleanTime"

        //Check that the form is complete before submitting data to the database
        if (!(title.isEmpty() || description.isEmpty() || timeStamp.isEmpty() || drawableSelected == 0)) {
            val habit = Habit(0, title, description, timeStamp, drawableSelected)

            //add the habit if all the fields are filled
            habitViewModels.addHabit(habit)
            Toast.makeText(context, "Habit created successfully!", Toast.LENGTH_SHORT).show()

            //navigate back to our home fragment
            findNavController().navigate(R.id.action_createHabitItem_to_habbitList)
        } else {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }
    }




    private fun drawableSelected(){
        binding.ivFastFoodSelected.setOnClickListener {
            binding.ivFastFoodSelected.isSelected = ! binding.ivFastFoodSelected.isSelected
            drawableSelected = R.drawable.ic_fastfood

            //de-select the other options when we pick an image
            binding.ivSmokingSelected.isSelected = false
            binding.ivTeaSelected.isSelected = false
        }

        binding.ivSmokingSelected.setOnClickListener {
            binding.ivSmokingSelected.isSelected = ! binding.ivSmokingSelected.isSelected
            drawableSelected = R.drawable.ic_smoking2

            //de-select the other options when we pick an image
            binding.ivFastFoodSelected.isSelected = false
            binding.ivTeaSelected.isSelected = false
        }

        binding.ivTeaSelected.setOnClickListener {
            binding.ivTeaSelected.isSelected = ! binding.ivTeaSelected.isSelected
            drawableSelected = R.drawable.ic_tea

            //de-select the other options when we pick an image
            binding.ivSmokingSelected.isSelected = false
            binding.ivFastFoodSelected.isSelected = false
        }


    }

    private fun pickDateAndTime(){
        binding.btnPickDate.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        binding.btnPickTime.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(context,this,hour,minute,true).show()

        }
    }



    override fun onDateSet(view: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {
        cleanDate = Calculations.cleanDate(dayX,monthX,yearX)
        binding.tvDateSelected.text = "Date: $cleanDate"
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



    override fun onTimeSet(view: TimePicker?, p1: Int, p2: Int) {
        //Log.d("Fragment", "Time: $hourOfDay:$minuteX")
        cleanTime = Calculations.cleanTime(p1, p2)
        binding.tvTimeSelected.text = "Time: $cleanTime"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}