package com.mte.fitnessapp.ui.home.utility

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.FragmentBmiBinding

class BmiFragment : Fragment() {

    private var _binding : FragmentBmiBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBmiBinding.inflate(inflater,container,false)
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

            if((binding.checkBoxMale.isChecked || binding.checkBoxFemale.isChecked) && binding.editTextAge.text.toString() != ""){
                if(binding.editTextAge.text.toString().toInt() > 12 && binding.editTextAge.text.toString().toInt() < 100){
                    binding.let {
                        var weight = it.weight.text.toString().toDouble()
                        var height : Double = it.height.text.toString().toDouble() / 100

                        var bmi = weight/(height*height)

                        if(bmi < 16){
                            binding.bmitext2.text = "(Severe Thinness)"
                            binding.bmitext2.setTextColor(Color.parseColor("#FFE40000"))
                        }else if (bmi >= 16 && bmi < 18.5){
                            binding.bmitext2.text = "(Mild Thinness)"
                            binding.bmitext2.setTextColor(Color.parseColor("#FFFF9800"))
                        }else if(bmi >= 18.5 && bmi < 25){
                            binding.bmitext2.text = "(Normal)"
                            binding.bmitext2.setTextColor(Color.parseColor("#FF3FFA47"))
                        }else if(bmi >= 25 && bmi < 30){
                            binding.bmitext2.text = "(Overweight)"
                            binding.bmitext2.setTextColor(Color.parseColor("#FFFF9800"))
                        }else if(bmi >= 30 && bmi < 35){
                            binding.bmitext2.text = "(Obese Class I)"
                            binding.bmitext2.setTextColor(Color.parseColor("#FFFF8E8E"))
                        }else if(bmi >= 35 && bmi < 40){
                            binding.bmitext2.text = "(Obese Class II)"
                            binding.bmitext2.setTextColor(Color.parseColor("#FFFF5D5D"))
                        }else if(bmi >= 40){
                            binding.bmitext2.text = "(Obese Class III)"
                            binding.bmitext2.setTextColor(Color.parseColor("#FFE40000"))
                        }

                        it.bmi.text = String.format("%.1f",bmi)
                    }
                }else{
                    Toast.makeText(context,"Please enter a valid age!",Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(context,"You must fill in all the information!",Toast.LENGTH_SHORT).show()
            }
        }

    }

}