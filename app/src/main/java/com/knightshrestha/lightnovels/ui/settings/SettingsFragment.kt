package com.knightshrestha.lightnovels.ui.settings

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.knightshrestha.lightnovels.R

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var folderPreference: Preference
    private lateinit var folderPickerLauncher: ActivityResultLauncher<Intent>

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        folderPreference = findPreference("base_path")!!
        folderPreference.summary = activity?.getPreferences(Context.MODE_PRIVATE)?.getString("root_path", "")

        folderPickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri: Uri? = result.data?.data
                if (uri != null) {
                    val folderPath = uri.path
                    val replacedPath = folderPath!!.replace("/tree/", "/storage/").replace(":","/")

                    folderPreference.summary = replacedPath
                    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return@registerForActivityResult
                    with (sharedPref.edit()) {
                        putString("root_path", replacedPath)
                        apply()
                    }
                }
            }
        }

        folderPreference.setOnPreferenceClickListener {
            openDocumentTree()
            true
        }
    }

    private fun openDocumentTree() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        folderPickerLauncher.launch(intent)
    }
}
