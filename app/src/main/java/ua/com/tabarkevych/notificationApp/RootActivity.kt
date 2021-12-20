package ua.com.tabarkevych.notificationApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import ua.com.tabarkevych.notificationApp.databinding.ActivityRootBinding

@AndroidEntryPoint
class RootActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRootBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setTheme(R.style.Theme_Pecoade_app)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}