package com.example.connect.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.connect.data.remote.response.ItemResponse
import com.example.connect.databinding.FragmentFavoriteBinding
import com.example.connect.ui.home.ItemListAdapter

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: ItemListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        favoriteAdapter = ItemListAdapter()
        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorite.adapter = favoriteAdapter

        favoriteViewModel.getFavoriteListUser()?.observe(viewLifecycleOwner) { favoriteList ->
            if (favoriteList.isEmpty()) {
                favoriteAdapter.submitList(null)
            } else {
                val itemList: List<ItemResponse> = favoriteList.map { favorite ->
                    ItemResponse(
                        id = favorite.id,
                        login = favorite.login,
                        avatarUrl = favorite.avatarUrl,
                        type = favorite.type
                    )
                }
                favoriteAdapter.submitList(itemList)
            }
        }

    }
    override fun onResume() {
        super.onResume()
        favoriteViewModel.getFavoriteListUser()?.observe(viewLifecycleOwner) { favoriteList ->
            if (favoriteList.isEmpty()) {
                favoriteAdapter.submitList(null)
            } else {
                val itemList: List<ItemResponse> = favoriteList.map { favorite ->
                    ItemResponse(
                        id = favorite.id,
                        login = favorite.login,
                        avatarUrl = favorite.avatarUrl,
                        type = favorite.type
                    )
                }
                favoriteAdapter.submitList(itemList)
            }
        }
    }










    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}


