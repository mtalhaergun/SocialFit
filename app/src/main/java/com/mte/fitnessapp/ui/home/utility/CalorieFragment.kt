package com.mte.fitnessapp.ui.home.utility

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.FragmentCalorieBinding

class CalorieFragment : Fragment() {

    private var _binding : FragmentCalorieBinding? = null
    private val binding get() = _binding!!
    var selectedActivity : Int? = null

    val spinnerList = arrayListOf<String>(
        "No exercise",
        "Exercise 1-3 times a week",
        "Exercise 4-5 times a week",
        "Daily exercise"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalorieBinding.inflate(inflater,container,false)

        val adapter = ArrayAdapter(requireContext(),R.layout.spinner_item,spinnerList)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.spinner.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.checkBoxMale.setOnCheckedChangeListener { compoundButton, b ->
            if(b) binding.checkBoxFemale.isChecked = false
        }
        binding.checkBoxFemale.setOnCheckedChangeListener { compoundButton, b ->
            if(b) binding.checkBoxMale.isChecked = false
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedActivity = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.seekBarHeight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.height.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        binding.seekBarWeight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.weight.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        binding.buttonCalculate.setOnClickListener {

            if((binding.checkBoxMale.isChecked || binding.checkBoxFemale.isChecked) && binding.editTextAge.text.toString() != "" && selectedActivity != null){
                if(binding.editTextAge.text.toString().toInt() > 12 && binding.editTextAge.text.toString().toInt() < 100){
                    binding.apply {
                        var weight = weight.text.toString().toDouble()
                        var height : Double = height.text.toString().toDouble()
                        var age = editTextAge.text.toString().toInt()
                        var bmr : Double = 0.0

                        if (checkBoxMale.isChecked){
                            if(selectedActivity == 0){
                                bmr = ((10*weight)+(6.25*height)-(5*age)+5)*1.2
                            }else if (selectedActivity == 1){
                                bmr = ((10*weight)+(6.25*height)-(5*age)+5)*1.38
                            }else if (selectedActivity == 2){
                                bmr = ((10*weight)+(6.25*height)-(5*age)+5)*1.47
                            }else if (selectedActivity == 3){
                                bmr = ((10*weight)+(6.25*height)-(5*age)+5)*1.56
                            }
                        }else if(checkBoxFemale.isChecked){
                            if(selectedActivity == 0){
                                bmr = ((10*weight)+(6.25*height)-(5*age)-161)*1.2
                            }else if (selectedActivity == 1){
                                bmr = ((10*weight)+(6.25*height)-(5*age)-161)*1.38
                            }else if (selectedActivity == 2){
                                bmr = ((10*weight)+(6.25*height)-(5*age)-161)*1.47
                            }else if (selectedActivity == 3){
                                bmr = ((10*weight)+(6.25*height)-(5*age)-161)*1.56
                            }
                        }
                        calorie.text = bmr.toInt().toString()
                    }
                }else{
                    Toast.makeText(context,"Please enter a valid age!", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(context,"You must fill in all the information!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

    }



}