package com.kevin.habitroops.ui.fragments.createhabit


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.kevin.habitroops.R
import com.kevin.habitroops.databinding.FragmentCreateHabitItemBinding
import com.kevin.habitroops.databinding.FragmentHabitListBinding
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
            //addHabitToDB()
        }

        pickDateAndTime()

        drawableSelected()

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
            android.app.DatePickerDialog(requireContext(), this, year, month, day).show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTimeSet(view: TimePicker?, p1: Int, p2: Int) {
        //Log.d("Fragment", "Time: $hourOfDay:$minuteX")
        cleanTime = Calculations.cleanTime(p1, p2)
        binding.tvTimeSelected.text = "Time: $cleanTime"
    }


}