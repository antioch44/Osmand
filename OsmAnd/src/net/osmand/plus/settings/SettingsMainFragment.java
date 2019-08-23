package net.osmand.plus.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.osmand.plus.ApplicationMode;
import net.osmand.plus.R;
import net.osmand.plus.activities.MapActivity;
import net.osmand.plus.profiles.SettingsProfileActivity;
import net.osmand.util.Algorithms;

import static net.osmand.plus.profiles.EditProfileFragment.MAP_CONFIG;
import static net.osmand.plus.profiles.EditProfileFragment.OPEN_CONFIG_ON_MAP;
import static net.osmand.plus.profiles.EditProfileFragment.SELECTED_ITEM;

public class SettingsMainFragment extends BaseSettingsFragment {

	public static final String TAG = "SettingsMainFragment";

	@Override
	protected int getPreferencesResId() {
		return R.xml.settings_main_screen;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);

		return view;
	}

	@Override
	protected int getToolbarResId() {
		return R.layout.global_preference_toolbar;
	}

	protected String getToolbarTitle() {
		return getString(R.string.shared_string_settings);
	}

	@Override
	protected void setupPreferences() {
		PreferenceScreen screen = getPreferenceScreen();

		Preference personal_account = findPreference("personal_account");
		Preference global_settings = findPreference("global_settings");
		Preference browse_map = findPreference("browse_map");
		Preference configure_profile = findPreference("configure_profile");
		Preference manage_profiles = findPreference("manage_profiles");

		personal_account.setIcon(getContentIcon(R.drawable.ic_person));
		global_settings.setIcon(getContentIcon(R.drawable.ic_action_settings));
		browse_map.setIcon(getContentIcon(R.drawable.ic_world_globe_dark));
		manage_profiles.setIcon(getContentIcon(R.drawable.ic_action_manage_profiles));

		final ApplicationMode selectedMode = getSelectedAppMode();

		int iconRes = selectedMode.getIconRes();
		int iconColor = selectedMode.getIconColorInfo().getColor(!getSettings().isLightContent());
		String title = selectedMode.isCustomProfile() ? selectedMode.getCustomProfileName() : getResources().getString(selectedMode.getNameKeyResource());

		String profileType;
		if (selectedMode.isCustomProfile()) {
			profileType = String.format(getString(R.string.profile_type_descr_string), Algorithms.capitalizeFirstLetterAndLowercase(selectedMode.getParent().toHumanString(getContext())));
		} else {
			profileType = getString(R.string.profile_type_base_string);
		}

		configure_profile.setIcon(getIcon(iconRes, iconColor));
		configure_profile.setTitle(title);
		configure_profile.setSummary(profileType);
	}

	public static boolean showInstance(FragmentManager fragmentManager) {
		try {
			SettingsMainFragment settingsMainFragment = new SettingsMainFragment();
			fragmentManager.beginTransaction()
					.replace(R.id.fragmentContainer, settingsMainFragment, SettingsMainFragment.TAG)
					.addToBackStack(SettingsMainFragment.TAG)
					.commitAllowingStateLoss();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public int getStatusBarColorId() {
		return getSettings().isLightContent() ? R.color.status_bar_color_light : R.color.status_bar_color_dark;
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (preference.getKey().equals("browse_map")) {
			Intent intent = new Intent(getActivity(), MapActivity.class);
			intent.putExtra(OPEN_CONFIG_ON_MAP, MAP_CONFIG);
			intent.putExtra(SELECTED_ITEM, getSelectedAppMode().getStringKey());
			startActivity(intent);
			getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
			return true;
		} else if (preference.getKey().equals("manage_profiles")) {
			startActivity(new Intent(getActivity(), SettingsProfileActivity.class));
			return true;
		}
		return super.onPreferenceClick(preference);
	}
}