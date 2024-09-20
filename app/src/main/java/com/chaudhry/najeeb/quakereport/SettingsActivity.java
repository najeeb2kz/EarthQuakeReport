package com.chaudhry.najeeb.quakereport;

        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.preference.ListPreference;
        import android.preference.Preference;
        import android.preference.PreferenceFragment;
        import android.preference.PreferenceManager;
        import androidx.appcompat.app.AppCompatActivity;


public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }


    public static class EarthquakePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            // Finds a Preference based on its key.  findPreference() is in PreferenceFragment class
            Preference minMagnitude = findPreference(getString(R.string.settings_min_magnitude_key));
            bindPreferenceSummaryToValue(minMagnitude);

            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            bindPreferenceSummaryToValue(orderBy);
        }


        // This is the only callback method in Preference.OnPreferenceChangeListener static interface
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }


        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            // All preferences are saved as key-value pairs in the default SharedPreferences with the
            // key specified through the xml above.  Retrieve an instance of those preferences
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            // getKey() is in Preference class.  Gets the key for this Preference, which is also the
            // key used for storing values into SharedPreferences.
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }
    }
}
