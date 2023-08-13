package com.sp.studylah;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class preferencesFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        Preference aboutPreference = findPreference("About");
        assert aboutPreference != null;
        aboutPreference.setOnPreferenceClickListener(preference -> {
            startActivity(new Intent(requireContext(), AboutActivity.class));
            return true;
        });
    }
}
