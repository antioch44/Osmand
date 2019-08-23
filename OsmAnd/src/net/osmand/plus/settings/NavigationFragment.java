package net.osmand.plus.settings;

import android.os.Bundle;
import android.support.v14.preference.SwitchPreference;
import android.support.v4.app.FragmentManager;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.osmand.plus.ApplicationMode;
import net.osmand.plus.R;

import static net.osmand.plus.profiles.SettingsProfileFragment.PROFILE_STRING_KEY;

public class NavigationFragment extends BaseSettingsFragment {

	public static final String TAG = "NavigationFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);

		return view;
	}

	@Override
	protected int getPreferencesResId() {
		return R.xml.navigation_settings_new;
	}


	@Override
	protected int getToolbarResId() {
		return R.layout.profile_preference_toolbar_big;
	}

	protected String getToolbarTitle() {
		return getString(R.string.routing_settings_2);
	}

	@Override
	protected void setupPreferences() {
		PreferenceScreen screen = getPreferenceScreen();

		Preference route_parameters = findPreference("route_parameters");

		SwitchPreference show_routing_alarms = (SwitchPreference) findPreference("show_routing_alarms");
		SwitchPreference speak_routing_alarms = (SwitchPreference) findPreference("speak_routing_alarms");
		Preference vehicle_parameters = findPreference("vehicle_parameters");
		Preference map_during_navigation = findPreference("map_during_navigation");
		SwitchPreference turn_screen_on = (SwitchPreference) findPreference("turn_screen_on");

		route_parameters.setIcon(getContentIcon(R.drawable.ic_action_route_distance));
		show_routing_alarms.setIcon(getContentIcon(R.drawable.ic_action_alert));
		speak_routing_alarms.setIcon(getContentIcon(R.drawable.ic_action_volume_up));
		vehicle_parameters.setIcon(getContentIcon(R.drawable.ic_action_car_dark));
		map_during_navigation.setIcon(getContentIcon(R.drawable.ic_action_mapillary));
		turn_screen_on.setIcon(getContentIcon(R.drawable.ic_action_turn_screen_on));
	}

	public static boolean showInstance(FragmentManager fragmentManager, ApplicationMode mode) {
		try {
			Bundle args = new Bundle();
			args.putString(PROFILE_STRING_KEY, mode.getStringKey());

			NavigationFragment settingsNavigationFragment = new NavigationFragment();
			settingsNavigationFragment.setArguments(args);

			fragmentManager.beginTransaction()
					.add(R.id.fragmentContainer, settingsNavigationFragment, NavigationFragment.TAG)
					.addToBackStack(NavigationFragment.TAG)
					.commitAllowingStateLoss();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}