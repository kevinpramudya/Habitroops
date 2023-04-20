package com.kevin.habitroops.ui.fragments.habitlist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kevin.habitroops.R
import com.kevin.habitroops.data.models.Habit
import com.kevin.habitroops.databinding.FragmentHabitListBinding
import com.kevin.habitroops.ui.viewmodels.HabitViewModels


class HabitList : Fragment(R.layout.fragment_habit_list) {

    private lateinit var habitList: List<Habit>
    private lateinit var habitviewmodels: HabitViewModels
    //private lateinit var adapter: HabitListAdapter

    private var _binding: FragmentHabitListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHabitListBinding.bind(view)

//        adapter = HabitListAdapter()
//        binding.rvHabits.adapter = adapter
//        binding.rvHabits.layoutManager = LinearLayoutManager(context)

        //Instantiate and create viewmodel observers
        viewModels()

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_habbitList_to_createHabitItem)
        }

//        //Show the options menu in this fragment
//        setHasOptionsMenu(true)

//        binding.swipeToRefresh.setOnRefreshListener {
//            adapter.setData(habitList)
//            binding.swipeToRefresh.isRefreshing = false
//        }
    }

    private fun viewModels() {
//        habitViewModel = ViewModelProvider(this).get(HabitViewModel::class.java)
//
//        habitViewModel.getAllHabits.observe(viewLifecycleOwner, Observer {
//            adapter.setData(it)
//            habitList = it
//
//            if (it.isEmpty()) {
//                binding.rvHabits.visibility = View.GONE
//                binding.tvEmptyView.visibility = View.VISIBLE
//            } else {
//                binding.rvHabits.visibility = View.VISIBLE
//                binding.tvEmptyView.visibility = View.GONE
//            }
//        })
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.nav_menu, menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.nav_delete -> habitViewModel.deleteAllHabits()
//        }
//        return super.onOptionsItemSelected(item)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}