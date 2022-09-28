package gr.aytn.islamicapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatDelegate
import gr.aytn.islamicapp.R
import gr.aytn.islamicapp.databinding.FragmentChapterSettingsBinding
import gr.aytn.islamicapp.prefs


class ChapterSettingsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChapterSettingsBinding.inflate(inflater,container,false)
        val root = binding.root

        val settingsTranslation = binding.translationSettings
        val tvSelectedTranslation = binding.selectedTranslation

        val settingsTheme = binding.themeSettings
        val tvSelectedTheme = binding.selectedTheme

        val settingsTranslationFontSize = binding.translationFontSizeSettings
        val tvSelectedTranslationFontSize = binding.selectedTranslationFontSize

        val settingsArabicFontSize = binding.arabicFontSizeSettings
        val tvSelectedArabicFontSize = binding.selectedArabicFontSize
        tvSelectedTheme.text = prefs.theme
        tvSelectedTranslation.text= prefs.selected_translation

        tvSelectedArabicFontSize.text = prefs.arabic_font_size.toString() + "sp"
        tvSelectedTranslationFontSize.text = prefs.translation_font_size.toString() + "sp"

        val langRadioGroup: RadioGroup = binding.LangRGroup
        val langAr = binding.ar
        val langAz = binding.az
        val langArAz = binding.araz

        when(prefs.selected_text_language){
            "ar" -> langAr.isChecked = true
            "az" -> langAz.isChecked = true
            "araz" -> langArAz.isChecked = true
        }

        langRadioGroup.setOnCheckedChangeListener { _, i ->
            when(i){
                R.id.ar -> prefs.selected_text_language = "ar"
                R.id.az -> prefs.selected_text_language = "az"
                R.id.araz -> prefs.selected_text_language = "araz"
            }
        }

        settingsArabicFontSize.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(context,R.style.AlertDialogStyle)
                .create()
            val view = layoutInflater.inflate(R.layout.arabic_font_checkbox_dialog, null)
            val radioGroup: RadioGroup = view.findViewById(R.id.RGroup)
            val s26RadioBtn : RadioButton = view.findViewById(R.id.s26)
            val s28RadioBtn : RadioButton = view.findViewById(R.id.s28)
            val s30RadioBtn : RadioButton = view.findViewById(R.id.s30)
            val s32RadioBtn : RadioButton = view.findViewById(R.id.s32)
            var selectedSize = 0

            when(prefs.arabic_font_size){
                26 -> s26RadioBtn.isChecked = true
                28 -> s28RadioBtn.isChecked = true
                30 -> s30RadioBtn.isChecked = true
                32 -> s32RadioBtn.isChecked = true
            }
            radioGroup.setOnCheckedChangeListener { _, i ->
                when(i){
                    R.id.s26 -> selectedSize = 26
                    R.id.s28 -> selectedSize = 28
                    R.id.s30 -> selectedSize = 30
                    R.id.s32 -> selectedSize = 32
                }
            }
            val okBtn: Button = view.findViewById(R.id.ok_button)
            val cancelBtn: Button = view.findViewById(R.id.cancel_button)
            okBtn.setOnClickListener {
                prefs.arabic_font_size = selectedSize
                tvSelectedArabicFontSize.text = prefs.arabic_font_size.toString() + "sp"
                builder.dismiss()
            }
            cancelBtn.setOnClickListener {
                builder.dismiss()
            }
            builder.setView(view)
            builder.show()
        }

        settingsTranslationFontSize.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(context,R.style.AlertDialogStyle)
                .create()
            val view = layoutInflater.inflate(R.layout.translation_font_checkbox_dialog, null)
            val radioGroup: RadioGroup = view.findViewById(R.id.RGroup)
            val s16RadioBtn : RadioButton = view.findViewById(R.id.s16)
            val s18RadioBtn : RadioButton = view.findViewById(R.id.s18)
            val s20RadioBtn : RadioButton = view.findViewById(R.id.s20)
            val s22RadioBtn : RadioButton = view.findViewById(R.id.s22)
            val s24RadioBtn : RadioButton = view.findViewById(R.id.s24)
            var selectedSize = 0

            when(prefs.translation_font_size){
                16 -> s16RadioBtn.isChecked = true
                18 -> s18RadioBtn.isChecked = true
                20 -> s20RadioBtn.isChecked = true
                22 -> s22RadioBtn.isChecked = true
                24 -> s24RadioBtn.isChecked = true
            }
            radioGroup.setOnCheckedChangeListener { _, i ->
                when(i){
                    R.id.s16 -> selectedSize = 16
                    R.id.s18 -> selectedSize = 18
                    R.id.s20 -> selectedSize = 20
                    R.id.s22 -> selectedSize = 22
                    R.id.s24 -> selectedSize = 24
                }
            }
            val okBtn: Button = view.findViewById(R.id.ok_button)
            val cancelBtn: Button = view.findViewById(R.id.cancel_button)
            okBtn.setOnClickListener {
                prefs.translation_font_size = selectedSize
                tvSelectedTranslationFontSize.text = prefs.translation_font_size.toString() + "sp"
                builder.dismiss()
            }
            cancelBtn.setOnClickListener {
                builder.dismiss()
            }
            builder.setView(view)
            builder.show()
        }
        settingsTranslation.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(context,R.style.AlertDialogStyle)
                .create()
            val view = layoutInflater.inflate(R.layout.translation_checkbox_dialog, null)
            val radioGroup: RadioGroup = view.findViewById(R.id.RGroup)
            val alikhanRadioBtn : RadioButton = view.findViewById(R.id.alikhan)
            val vasimRadioBtn : RadioButton = view.findViewById(R.id.vasim)
            var selectedTranslation = ""

            if(prefs.selected_translation == "Əlixan Musayev"){
                alikhanRadioBtn.isChecked = true
            }else if(prefs.selected_translation == "Vasim Məmmədəliyev və Ziya Bünyadov") {
                vasimRadioBtn.isChecked = true
            }
            radioGroup.setOnCheckedChangeListener { _, i ->
                if (i == R.id.alikhan) {
                    selectedTranslation = "Əlixan Musayev"
                } else if (i == R.id.vasim) {
                    selectedTranslation = "Vasim Məmmədəliyev və Ziya Bünyadov"
                }
            }
            val okBtn: Button = view.findViewById(R.id.ok_button)
            val cancelBtn: Button = view.findViewById(R.id.cancel_button)
            okBtn.setOnClickListener {
                prefs.selected_translation = selectedTranslation
                if (prefs.selected_translation == "Əlixan Musayev"){
                    tvSelectedTranslation.text = "Əlixan Musayev"
                }else if (prefs.selected_translation == "Vasim Məmmədəliyev və Ziya Bünyadov"){
                    tvSelectedTranslation.text = "Vasim Məmmədəliyev və Ziya Bünyadov"
                }
                builder.dismiss()
            }
            cancelBtn.setOnClickListener {
                builder.dismiss()
            }
            builder.setView(view)
            builder.show()
        }
        settingsTheme.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(context,R.style.AlertDialogStyle)
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
            builder.show()
        }



        return root
    }

}