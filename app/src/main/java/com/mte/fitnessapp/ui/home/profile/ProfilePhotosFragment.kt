package com.mte.fitnessapp.ui.home.profile

import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.FragmentProfilePhotosBinding
import com.mte.fitnessapp.model.post.Post

class ProfilePhotosFragment : Fragment() {

    private var _binding : FragmentProfilePhotosBinding? = null
    private val binding get() = _binding!!
    val db= Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private lateinit var adapterPhotos : PhotosRecyclerAdapter
    var list = ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfilePhotosBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db.collection("posts").document(auth.uid!!).collection("photos").orderBy("uploadDate",com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { value2, error1 ->

                if (value2 != null) {
                    value2.documents.forEach {
                        list.add(Post("${it.id}","${it.getField<String>("userName")}","${it.getField<String>("imageUrl")}","${it.getField<String>("caption")}"))
                        adapterPhotos.notifyDataSetChanged()
                    }
                }
            }

        adapterPhotos = PhotosRecyclerAdapter(list)
        binding.commentsRv.adapter = adapterPhotos

        val spacing = resources.getDimensionPixelSize(R.dimen.grid_spacing)
        binding.commentsRv.addItemDecoration(GridSpacingItemDecoration(3, spacing))
    }

    inner class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view)
            val column = position % spanCount

            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            if (position >= spanCount) {
                outRect.top = spacing
            }
        }
    }

}