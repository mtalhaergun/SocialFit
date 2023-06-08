package com.mte.fitnessapp.ui.home.utility

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.FragmentBodyFatBinding
import kotlin.math.log

class BodyFatFragment : Fragment() {

    private var _binding : FragmentBodyFatBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBodyFatBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.checkBoxMale.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                binding.checkBoxFemale.isChecked = false
                binding.linearLayoutHip.isVisible = false
            }
        }
        binding.checkBoxFemale.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                binding.checkBoxMale.isChecked = false
                binding.linearLayoutHip.isVisible = true
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

            if((binding.checkBoxMale.isChecked || binding.checkBoxFemale.isChecked) && binding.editTextAge.text.toString() != "" && binding.editTextNeck.text.toString() != "" && binding.editTextWaist.text.toString() != ""){
                if(binding.editTextAge.text.toString().toInt() > 12 && binding.editTextAge.text.toString().toInt() < 100){
                    if(binding.editTextNeck.text.toString().toInt() > 19 && binding.editTextNeck.text.toString().toInt() < 61){
                        if(binding.editTextWaist.text.toString().toInt() > 49 && binding.editTextWaist.text.toString().toInt() < 151){
                            binding.apply {
                                var weight = weight.text.toString().toDouble()
                                var height : Double = height.text.toString().toDouble()
                                var waist = editTextWaist.text.toString().toDouble()
                                var neck = editTextNeck.text.toString().toDouble()

                                if (checkBoxMale.isChecked){
                                    var fat = (495/(1.0324-(0.19077* log(waist-neck, 10.0))+(0.15456*log(height,10.0))))-450

                                    if(fat >= 2 && fat < 5){
                                        binding.fattext2.text = "(Essential Fat)"
                                        binding.fattext2.setTextColor(Color.parseColor("#FFEB3B"))
                                        binding.fat.text = String.format("%.1f",fat)
                                    }else if(fat >= 5 && fat < 13){
                                        binding.fattext2.text = "(Athletes)"
                                        binding.fattext2.setTextColor(Color.parseColor("#24FF39"))
                                        binding.fat.text = String.format("%.1f",fat)
                                    }else if(fat >= 13 && fat < 17){
                                        binding.fattext2.text = "(Fitness)"
                                        binding.fattext2.setTextColor(Color.parseColor("#4CAF50"))
                                        binding.fat.text = String.format("%.1f",fat)
                                    }else if(fat >= 17 && fat < 24){
                                        binding.fattext2.text = "(Average)"
                                        binding.fattext2.setTextColor(Color.parseColor("#FFEB3B"))
                                        binding.fat.text = String.format("%.1f",fat)
                                    }else if(fat >= 24){
                                        binding.fattext2.text = "(Obese)"
                                        binding.fattext2.setTextColor(Color.parseColor("#9F0000"))
                                        binding.fat.text = String.format("%.1f",fat)
                                    }else{
                                        Toast.makeText(context,"Invalid value, enter different values", Toast.LENGTH_SHORT).show()
                                    }

                                }
                                if(checkBoxFemale.isChecked){
                                    var hip = editTextHip.text.toString().toDouble()
                                    if(editTextHip.text.toString().toInt() > 39 && editTextHip.text.toString().toInt() < 201){
                                        var fat = (495/(1.29579-(0.35004* log(waist+hip-neck, 10.0))+(0.22100*log(height,10.0))))-450

                                        if(fat >= 10 && fat < 13){
                                            binding.fattext2.text = "(Essential Fat)"
                                            binding.fattext2.setTextColor(Color.parseColor("#FFEB3B"))
                                            binding.fat.text = String.format("%.1f",fat)
                                        }else if(fat >= 13 && fat < 20){
                                            binding.fattext2.text = "(Athletes)"
                                            binding.fattext2.setTextColor(Color.parseColor("#24FF39"))
                                            binding.fat.text = String.format("%.1f",fat)
                                        }else if(fat >= 20 && fat < 24){
                                            binding.fattext2.text = "(Fitness)"
                                            binding.fattext2.setTextColor(Color.parseColor("#4CAF50"))
                                            binding.fat.text = String.format("%.1f",fat)
                                        }else if(fat >= 24 && fat < 31){
                                            binding.fattext2.text = "(Average)"
                                            binding.fattext2.setTextColor(Color.parseColor("#FFEB3B"))
                                            binding.fat.text = String.format("%.1f",fat)
                                        }else if(fat >= 31){
                                            binding.fattext2.text = "(Obese)"
                                            binding.fattext2.setTextColor(Color.parseColor("#9F0000"))
                                            binding.fat.text = String.format("%.1f",fat)
                                        }else{
                                            Toast.makeText(context,"Invalid value, enter different values", Toast.LENGTH_SHORT).show()
                                        }

                                    }else{
                                        Toast.makeText(context,"Please enter a valid hip size!", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }else{
                            Toast.makeText(context,"Please enter a valid waist size!", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(context,"Please enter a valid neck size!", Toast.LENGTH_SHORT).show()
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