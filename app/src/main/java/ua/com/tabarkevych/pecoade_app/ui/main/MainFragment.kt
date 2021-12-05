package ua.com.tabarkevych.pecoade_app.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import ua.com.tabarkevych.pecoade_app.data.s.entity.Page
import ua.com.tabarkevych.pecoade_app.databinding.FragmentMainBinding
import ua.com.tabarkevych.pecoade_app.util.NotificationUtil
import ua.com.tabarkevych.pecoade_app.ui.base.BaseFragment

@AndroidEntryPoint
class MainFragment(
) : BaseFragment<FragmentMainBinding>(), ViewPagerAdapter.EventListener {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentMainBinding::inflate
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MaterialFadeThrough().let {
            enterTransition = it
            exitTransition = it
        }
        scrollToNotificationPage()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        updateViewPager()
    }


    private fun initViewPager() {
        binding.viewPager.adapter = ViewPagerAdapter(this@MainFragment)
    }

    private fun scrollToNotificationPage(){
        Handler(Looper.getMainLooper()).postDelayed({
            val notificationPage = arguments?.let { MainFragmentArgs.fromBundle(it).position } ?: 0
            binding.viewPager.setCurrentItem(notificationPage, true)
        },100)
    }

    private fun updateViewPager() {
        binding.viewPager.apply {
            viewModel.readAllData.observe(viewLifecycleOwner, { pages ->
                val adapter = (adapter as ViewPagerAdapter)
                if (pages.isEmpty()) viewModel.addPage(Page(0))
                adapter.setData(pages)
                adapter.submitList(pages)
            })
        }
    }

    override fun onNotificationClicked(position: Int) {
        NotificationUtil.createViewPagerNotification(position , requireContext())
    }

    override fun onButtonIncreaseClicked(position: Int) {
        viewModel.addPage(Page(position))
        updateViewPager()
        Handler(Looper.getMainLooper()).postDelayed({binding.viewPager.setCurrentItem(position+1, true)},100)
    }

    override fun onButtonDecreaseClicked(lastItem: Int) {
        viewModel.deletePage(Page(id = lastItem))
        updateViewPager()
    }
}