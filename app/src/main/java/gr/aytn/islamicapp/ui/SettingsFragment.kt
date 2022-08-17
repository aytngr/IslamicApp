package gr.aytn.islamicapp.ui

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.databinding.FragmentHomeBinding
import gr.aytn.islamicapp.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var navHost: FragmentContainerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsBinding.inflate(inflater,container,false)
        val root: View = binding.root

        val settingsLocation = binding.locationSettings

        val listener: settingsLocationOnClickListener = activity as settingsLocationOnClickListener
        settingsLocation.setOnClickListener {
            val intent = Intent(activity!!,LocationsActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    interface settingsLocationOnClickListener{
        fun settingsLocationOnClick()
    }

}