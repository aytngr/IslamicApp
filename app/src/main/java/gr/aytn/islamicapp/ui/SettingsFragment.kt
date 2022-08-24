package gr.aytn.islamicapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.databinding.FragmentSettingsBinding
import gr.aytn.islamicapp.prefs


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
        val tvSelectedLocation = binding.selectedLocation

        val settingsAsrCalculation = binding.asrCalculationSettings
        val tvSelectedAsrCalculation = binding.selectedAsrLocation

        val settingsTranslation = binding.translationSettings
        val tvSelectedTranslation = binding.selectedTranslation

        val settingsTheme = binding.themeSettings
        val tvSelectedTheme = binding.selectedTheme

        tvSelectedTheme.text = prefs.theme

        tvSelectedLocation.text = prefs.selected_location

        settingsLocation.setOnClickListener {
            findNavController().navigate(R.id.locationFragment)
        }
        settingsAsrCalculation.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(context)
                .create()
            val view = layoutInflater.inflate(R.layout.checkbox_dialog, null)
            val radioGroup: RadioGroup = view.findViewById(R.id.RGroup)
            var selectedAsrCalculation = ""
            radioGroup.setOnCheckedChangeListener { _, i ->
                if (i == R.id.standard) {
                    selectedAsrCalculation = "standart"
                } else if (i == R.id.hanafi) {
                    selectedAsrCalculation = "hanafi"
                }
            }
            val okBtn: Button = view.findViewById(R.id.ok_button)
            val cancelBtn: Button = view.findViewById(R.id.cancel_button)
            okBtn.setOnClickListener {
                builder.dismiss()
            }
            cancelBtn.setOnClickListener {
                builder.dismiss()
            }
            builder.setView(view)
            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }

        settingsTheme.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(context)
                .create()
            val view = layoutInflater.inflate(R.layout.theme_checkbox_dialog, null)
            val radioGroup: RadioGroup = view.findViewById(R.id.RGroup)
            val lightRadioBtn : RadioButton = view.findViewById(R.id.light)
            val darkRadioBtn : RadioButton = view.findViewById(R.id.dark)
            var selectedTheme = ""

            if(prefs.theme == "Light"){
                lightRadioBtn.isChecked = true
            }else if(prefs.theme == "Dark") {
                darkRadioBtn.isChecked = true
            }
            radioGroup.setOnCheckedChangeListener { _, i ->
                if (i == R.id.light) {
                    selectedTheme = "Light"
                } else if (i == R.id.dark) {
                    selectedTheme = "Dark"
                }
            }
            val okBtn: Button = view.findViewById(R.id.ok_button)
            val cancelBtn: Button = view.findViewById(R.id.cancel_button)
            okBtn.setOnClickListener {
                prefs.theme = selectedTheme
                if (prefs.theme == "Light"){
                    tvSelectedTheme.text = prefs.theme
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }else if (prefs.theme == "Dark"){
                    tvSelectedTheme.text = prefs.theme
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                }
                builder.dismiss()
            }
            cancelBtn.setOnClickListener {
                builder.dismiss()
            }
            builder.setView(view)
            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }

        return root
    }

}