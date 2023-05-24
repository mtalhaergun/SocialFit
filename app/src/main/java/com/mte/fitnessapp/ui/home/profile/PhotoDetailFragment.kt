package com.mte.fitnessapp.ui.home.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import androidx.navigation.fragment.navArgs
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.FragmentPhotoDetailBinding
import com.squareup.picasso.Picasso

class PhotoDetailFragment : Fragment() {

    private var _binding : FragmentPhotoDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: PhotoDetailFragmentArgs by navArgs()
        val data = args.post
        binding.publisherUserNamePost.text=data.userName
        binding.publisher.text=data.userName
        binding.postCaption.text=data.caption
        Picasso.get().load(data.imageUrl).into(binding.postImage)
        binding.menuButton.setOnClickListener {
            val popUpMenu = PopupMenu(requireContext(),it)
            popUpMenu.inflate(R.menu.photo_menu)

            popUpMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.deleteMenu -> {
                        true
                    }
                    else -> false
                }
            }
            popUpMenu.show()
        }
    }

}