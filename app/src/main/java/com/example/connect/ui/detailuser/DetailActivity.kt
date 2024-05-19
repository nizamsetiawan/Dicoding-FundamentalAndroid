
package com.example.connect.ui.detailuser

import PageAdapter
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.connect.databinding.ActivityDetailBinding
import com.example.connect.ui.home.ItemListAdapter
import com.example.connect.utils.Loading
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels<DetailViewModel>()
    private lateinit var resultFollowers: FollowersViewModel
    private lateinit var resultFollowing: FollowingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(ItemListAdapter.USERNAME_KEY) ?: ""
        val id = intent.getIntExtra(ItemListAdapter.ID_KEY, 0)
        val avatarUrl = intent.getStringExtra(ItemListAdapter.AVATAR_KEY) ?: ""
        var type = intent.getStringExtra(ItemListAdapter.TYPE_KEY) ?: ""
        viewModel.isLoading.observe(this, Observer { isLoading ->
            isLoading?.let {
                if (it) {
                    Loading.showProgressDialog(this)
                } else {
                    Loading.hideProgressDialog()
                }
            }
        })
        binding.fabShare.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "Bagikan melalui"))
        }


        var isFavorite = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkFavoriteUser(id)
            withContext(Dispatchers.Main) {
                if (count!! >0) {
                    binding.fabFavorite.isChecked = true
                    isFavorite = true
                } else {
                    binding.fabFavorite.isChecked = false
                    isFavorite = false
                }
            }
        }
        binding.fabFavorite.setOnClickListener {
            isFavorite = !isFavorite
            if (isFavorite) {
                viewModel.addToFavorite(username, id, avatarUrl, type)
            } else {
                viewModel.removeFavorite(id)
            }
            binding.fabFavorite.isChecked = isFavorite
        }






        resultFollowers = ViewModelProvider(this).get(FollowersViewModel::class.java)
        resultFollowing = ViewModelProvider(this).get(FollowingViewModel::class.java)

        viewModel.detailUser.observe(this, Observer { detailUser ->
            detailUser?.let {
                Glide.with(this).load(detailUser.avatarUrl).into(binding.ivAvatar)

                binding.tvUsername.text = detailUser.login ?: "-"
                binding.tvName.text = detailUser.name ?: "-"
                binding.tvCompany.text = detailUser.company ?: "-"
                binding.tvLocation.text = detailUser.location ?: "-"
                binding.tvEmail.text = (detailUser.email ?: "-").toString()
                binding.tvTwitter.text = (detailUser.twitterUsername ?: "-").toString()
                binding.tvGistCount.text = (detailUser.publicGists ?: "-").toString()
                binding.tvRepositoryCount.text = (detailUser.publicRepos ?: "-").toString()
                binding.tvFollowersCount.text = (detailUser.followers ?: "-").toString()
                binding.tvFollowingCount.text = (detailUser.following ?:"-").toString()
            }
        })

        viewModel.getDetailUser(username)

        val sectionsPagerAdapter = PageAdapter(this)
        sectionsPagerAdapter.username = username
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> {
                    resultFollowers.getFollowers(username)
                    "Pengikut"
                }
                1 -> {
                   resultFollowing.getFollowing(username)
                    "Mengikuti"
                }
                else -> ""
            }
        }.attach()
    }

}
