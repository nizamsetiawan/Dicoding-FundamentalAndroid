package com.example.connect.ui.detailuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.connect.databinding.FragmentFollowBinding
import com.example.connect.ui.home.HomeViewModel
import com.example.connect.ui.home.ItemListAdapter


class FollowFragment : Fragment() {

    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var followersViewModel: FollowersViewModel
    private lateinit var homeViewModel: HomeViewModel

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        position = requireArguments().getInt(ARG_POSITION)
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        followingViewModel = ViewModelProvider(requireActivity())[FollowingViewModel::class.java]
        followersViewModel = ViewModelProvider(requireActivity())[FollowersViewModel::class.java]

        binding.rvFollow.layoutManager = LinearLayoutManager(requireContext())

        if (position == 1) {
            followersViewModel.listFollowers.observe(viewLifecycleOwner) { followersList ->
                followersList?.let {
                    binding.rvFollow.adapter = ItemListAdapter().apply {
                        submitList(followersList)
                    }
                }
            }
        } else {
            followingViewModel.listFollowing.observe(viewLifecycleOwner) { followingList ->
                followingList?.let {
                    binding.rvFollow.adapter = ItemListAdapter().apply {
                        submitList(followingList)
                    }

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_POSITION: String = "position"
    }
}
