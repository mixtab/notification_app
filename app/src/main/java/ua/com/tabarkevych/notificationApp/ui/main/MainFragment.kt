package ua.com.tabarkevych.notificationApp.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import ua.com.tabarkevych.notificationApp.data.s.entity.Page
import ua.com.tabarkevych.notificationApp.databinding.FragmentMainBinding
import ua.com.tabarkevych.notificationApp.util.NotificationUtil
import ua.com.tabarkevych.notificationApp.ui.base.BaseFragment

@AndroidEntryPoint
class MainFragment(
) : BaseFragment<FragmentMainBinding>(), ViewPagerAdapter.EventListener {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentMainBinding::inflate
    private val viewModel: MainViewModel by viewModels()
    private var scrollToPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MaterialFadeThrough().let {
            enterTransition = it
            exitTransition = it
        }
        scrollToPage = arguments?.let { MainFragmentArgs.fromBundle(it).position } ?: 0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        Handler(Looper.getMainLooper()).postDelayed({ updateViewPager(true)},200)
    }


    private fun initViewPager() {
        binding.viewPager.adapter = ViewPagerAdapter(this@MainFragment)
    }

    private fun updateViewPager(withScroll: Boolean) {
        binding.viewPager.apply {
            viewModel.readAllData.observe(viewLifecycleOwner, { pages ->
                val adapter = (adapter as ViewPagerAdapter)
                if (pages.isEmpty()) viewModel.addPage(Page(0))
                adapter.setData(pages)
                adapter.submitList(pages)
            })
            if (withScroll) Handler(Looper.getMainLooper()).postDelayed({
                binding.viewPager.setCurrentItem(
                    scrollToPage,
                    true
                )
            }, 200)
        }
    }

    override fun onNotificationClicked(position: Int) {
        NotificationUtil.createViewPagerNotification(position, requireContext())
    }

    override fun onButtonIncreaseClicked(position: Int) {
        viewModel.addPage(Page(position))
        updateViewPager(true)
        scrollToPage = position + 1
    }

    override fun onButtonDecreaseClicked(lastItem: Int) {
        viewModel.deletePage(Page(id = lastItem))
        updateViewPager(false)
    }
}