package net.osmand.plus.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.Preference;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.osmand.StateChangedListener;
import net.osmand.data.PointDescription;
import net.osmand.plus.OsmandSettings;
import net.osmand.plus.R;
import net.osmand.plus.base.MapViewTrackingUtilities;
import net.osmand.plus.settings.preferences.ListPreferenceEx;
import net.osmand.plus.settings.preferences.SwitchPreferenceEx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneralProfileSettings extends BaseSettingsFragment {

	public static final String TAG = "GeneralProfileSettings";

	@Override
	protected int getPreferencesResId() {
		return R.xml.general_profile_settings;
	}

	@Override
	protected int getToolbarResId() {
		return R.layout.profile_preference_toolbar_big;
	}

	protected String getToolbarTitle() {
		return getString(R.string.general_settings_2);
	}

	protected void setupPreferences() {
		setupAppThemePref();
		setupRotateMapPref();
		setupMapScreenOrientationPref();

		setupDrivingRegionPref();
		setupUnitsOfLengthPref();
		setupCoordinatesFormatPref();
		setupAngularUnitsPref();

		setupKalmanFilterPref();
		setupMagneticFieldSensorPref();
		setupMapEmptyStateAllowedPref();
		setupDoNotUseAnimationsPref();
		setupExternalInputDevicePref();
	}

	private void setupAppThemePref() {
		final ListPreferenceEx appTheme = (ListPreferenceEx) findPreference(settings.OSMAND_THEME.getId());
		appTheme.setEntries(new String[] {getString(R.string.dark_theme), getString(R.string.light_theme)});
		appTheme.setEntryValues(new Integer[] {OsmandSettings.OSMAND_DARK_THEME, OsmandSettings.OSMAND_LIGHT_THEME});
		appTheme.setIcon(getOsmandThemeIcon());

		settings.OSMAND_THEME.addListener(new StateChangedListener<Integer>() {
			@Override
			public void stateChanged(Integer change) {
				appTheme.setIcon(getOsmandThemeIcon());
			}
		});
	}

	private Drawable getOsmandThemeIcon() {
		return getIcon(settings.isLightContent() ? R.drawable.ic_action_sun : R.drawable.ic_action_moon);
	}

	private void setupRotateMapPref() {
		final ListPreferenceEx rotateMap = (ListPreferenceEx) findPreference(settings.ROTATE_MAP.getId());
		rotateMap.setEntries(new String[] {getString(R.string.rotate_map_none_opt), getString(R.string.rotate_map_bearing_opt), getString(R.string.rotate_map_compass_opt)});
		rotateMap.setEntryValues(new Integer[] {OsmandSettings.ROTATE_MAP_NONE, OsmandSettings.ROTATE_MAP_BEARING, OsmandSettings.ROTATE_MAP_COMPASS});
		rotateMap.setIcon(getRotateMapIcon());

		settings.ROTATE_MAP.addListener(new StateChangedListener<Integer>() {
			@Override
			public void stateChanged(Integer change) {
				rotateMap.setIcon(getRotateMapIcon());
			}
		});
	}

	private Drawable getRotateMapIcon() {
		switch (settings.ROTATE_MAP.get()) {
			case OsmandSettings.ROTATE_MAP_NONE:
				return getIcon(R.drawable.ic_action_direction_north);
			case OsmandSettings.ROTATE_MAP_BEARING:
				return getIcon(R.drawable.ic_action_direction_movement);
			default:
				return getIcon(R.drawable.ic_action_direction_compass);
		}
	}

	private void setupMapScreenOrientationPref() {
		final ListPreferenceEx mapScreenOrientation = (ListPreferenceEx) findPreference(settings.MAP_SCREEN_ORIENTATION.getId());
		mapScreenOrientation.setEntries(new String[] {getString(R.string.map_orientation_portrait), getString(R.string.map_orientation_landscape), getString(R.string.map_orientation_default)});
		mapScreenOrientation.setEntryValues(new Integer[] {ActivityInfo.SCREEN_ORIENTATION_PORTRAIT, ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE, ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED});
		mapScreenOrientation.setIcon(getMapScreenOrientationIcon());

		settings.MAP_SCREEN_ORIENTATION.addListener(new StateChangedListener<Integer>() {
			@Override
			public void stateChanged(Integer change) {
				mapScreenOrientation.setIcon(getMapScreenOrientationIcon());
			}
		});
	}

	private Drawable getMapScreenOrientationIcon() {
		switch (settings.MAP_SCREEN_ORIENTATION.get()) {
			case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
				return getIcon(R.drawable.ic_action_phone_portrait_orientation);
			case ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE:
				return getIcon(R.drawable.ic_action_phone_landscape_orientation);
			default:
				return getIcon(R.drawable.ic_action_phone_device_orientation);
		}
	}

	private void setupDrivingRegionPref() {
		Preference defaultDrivingRegion = findPreference(settings.DRIVING_REGION.getId());
		defaultDrivingRegion.setIcon(getContentIcon(R.drawable.ic_action_car_dark));
	}

	private void setupUnitsOfLengthPref() {
		OsmandSettings.MetricsConstants[] metricsConstants = OsmandSettings.MetricsConstants.values();
		String[] entries = new String[metricsConstants.length];
		Integer[] entryValues = new Integer[metricsConstants.length];

		for (int i = 0; i < entries.length; i++) {
			entries[i] = metricsConstants[i].toHumanString(app);
			entryValues[i] = metricsConstants[i].ordinal();
		}

		ListPreferenceEx unitsOfLength = (ListPreferenceEx) findPreference(settings.METRIC_SYSTEM.getId());
		unitsOfLength.setEntries(entries);
		unitsOfLength.setEntryValues(entryValues);
		unitsOfLength.setIcon(getIcon(R.drawable.ic_action_ruler_unit));
	}

	private void setupCoordinatesFormatPref() {
		Context ctx = getContext();
		if (ctx == null) {
			return;
		}

		ListPreferenceEx coordinatesFormat = (ListPreferenceEx) findPreference(settings.COORDINATES_FORMAT.getId());
		coordinatesFormat.setIcon(getContentIcon(R.drawable.ic_action_coordinates_widget));

		coordinatesFormat.setEntries(new String[] {
				PointDescription.formatToHumanString(ctx, PointDescription.FORMAT_DEGREES),
				PointDescription.formatToHumanString(ctx, PointDescription.FORMAT_MINUTES),
				PointDescription.formatToHumanString(ctx, PointDescription.FORMAT_SECONDS),
				PointDescription.formatToHumanString(ctx, PointDescription.UTM_FORMAT),
				PointDescription.formatToHumanString(ctx, PointDescription.OLC_FORMAT)
		});
		coordinatesFormat.setEntryValues(new Integer[] {
				PointDescription.FORMAT_DEGREES,
				PointDescription.FORMAT_MINUTES,
				PointDescription.FORMAT_SECONDS,
				PointDescription.UTM_FORMAT,
				PointDescription.OLC_FORMAT
		});
	}

	private void setupAngularUnitsPref() {
		OsmandSettings.AngularConstants[] ac = OsmandSettings.AngularConstants.values();
		String[] entries = new String[ac.length];
		Integer[] entryValues = new Integer[ac.length];

		for (int i = 0; i < entries.length; i++) {
			if (ac[i] == OsmandSettings.AngularConstants.DEGREES) {
				entries[i] = OsmandSettings.AngularConstants.DEGREES.toHumanString(app) + " 180";
				entryValues[i] = OsmandSettings.AngularConstants.DEGREES.ordinal();
			} else if (ac[i] == OsmandSettings.AngularConstants.DEGREES360) {
				entries[i] = OsmandSettings.AngularConstants.DEGREES.toHumanString(app) + " 360";
				entryValues[i] = OsmandSettings.AngularConstants.DEGREES360.ordinal();
			} else {
				entries[i] = ac[i].toHumanString(app);
				entryValues[i] = OsmandSettings.AngularConstants.MILLIRADS.ordinal();
			}
		}

		ListPreferenceEx angularUnits = (ListPreferenceEx) findPreference(settings.ANGULAR_UNITS.getId());
		angularUnits.setEntries(entries);
		angularUnits.setEntryValues(entryValues);
		angularUnits.setIcon(getContentIcon(R.drawable.ic_action_angular_unit));
	}

	private void setupKalmanFilterPref() {
		SwitchPreferenceEx kalmanFilterPref = (SwitchPreferenceEx) findPreference(settings.USE_KALMAN_FILTER_FOR_COMPASS.getId());
		kalmanFilterPref.setSummaryOn(R.string.shared_string_on);
		kalmanFilterPref.setSummaryOff(R.string.shared_string_off);
		kalmanFilterPref.setTitle(getString(R.string.use_kalman_filter_compass));
		kalmanFilterPref.setDescription(getString(R.string.use_kalman_filter_compass_descr));
	}

	private void setupMagneticFieldSensorPref() {
		SwitchPreferenceEx useMagneticSensorPref = (SwitchPreferenceEx) findPreference(settings.USE_MAGNETIC_FIELD_SENSOR_COMPASS.getId());
		useMagneticSensorPref.setSummaryOn(R.string.shared_string_on);
		useMagneticSensorPref.setSummaryOff(R.string.shared_string_off);
		useMagneticSensorPref.setTitle(getString(R.string.use_magnetic_sensor));
		useMagneticSensorPref.setDescription(getString(R.string.use_magnetic_sensor_descr));
	}

	private void setupMapEmptyStateAllowedPref() {
		SwitchPreferenceEx mapEmptyStateAllowedPref = (SwitchPreferenceEx) findPreference(settings.MAP_EMPTY_STATE_ALLOWED.getId());
		mapEmptyStateAllowedPref.setSummaryOn(R.string.shared_string_on);
		mapEmptyStateAllowedPref.setSummaryOff(R.string.shared_string_off);
		mapEmptyStateAllowedPref.setTitle(getString(R.string.tap_on_map_to_hide_interface));
		mapEmptyStateAllowedPref.setDescription(getString(R.string.tap_on_map_to_hide_interface_descr));
	}

	private void setupDoNotUseAnimationsPref() {
		SwitchPreference doNotUseAnimations = (SwitchPreference) findPreference(settings.DO_NOT_USE_ANIMATIONS.getId());
		doNotUseAnimations.setSummaryOn(R.string.shared_string_on);
		doNotUseAnimations.setSummaryOff(R.string.shared_string_off);
	}

	private void setupExternalInputDevicePref() {
		ListPreferenceEx externalInputDevice = (ListPreferenceEx) findPreference(settings.EXTERNAL_INPUT_DEVICE.getId());
		externalInputDevice.setEntries(new String[] {
				getString(R.string.sett_no_ext_input),
				getString(R.string.sett_generic_ext_input),
				getString(R.string.sett_wunderlinq_ext_input),
				getString(R.string.sett_parrot_ext_input)
		});

		externalInputDevice.setEntryValues(new Integer[] {
				OsmandSettings.NO_EXTERNAL_DEVICE,
				OsmandSettings.GENERIC_EXTERNAL_DEVICE,
				OsmandSettings.WUNDERLINQ_EXTERNAL_DEVICE,
				OsmandSettings.PARROT_EXTERNAL_DEVICE}
		);
	}

	private void showDrivingRegionDialog() {
		final AlertDialog.Builder b = new AlertDialog.Builder(getActivity());

		b.setTitle(getString(R.string.driving_region));

		final List<OsmandSettings.DrivingRegion> drs = new ArrayList<>();
		drs.add(null);
		drs.addAll(Arrays.asList(OsmandSettings.DrivingRegion.values()));
		int sel = -1;
		OsmandSettings.DrivingRegion selectedDrivingRegion = settings.DRIVING_REGION.get();
		if (settings.DRIVING_REGION_AUTOMATIC.get()) {
			sel = 0;
		}
		for (int i = 1; i < drs.size(); i++) {
			if (sel == -1 && drs.get(i) == selectedDrivingRegion) {
				sel = i;
				break;
			}
		}

		final int selected = sel;
		final ArrayAdapter<OsmandSettings.DrivingRegion> singleChoiceAdapter =
				new ArrayAdapter<OsmandSettings.DrivingRegion>(getActivity(), R.layout.single_choice_description_item, R.id.text1, drs) {
					@NonNull
					@Override
					public View getView(int position, View convertView, @NonNull ViewGroup parent) {
						View v = convertView;
						if (v == null) {
							LayoutInflater inflater = getActivity().getLayoutInflater();
							v = inflater.inflate(R.layout.single_choice_description_item, parent, false);
						}
						OsmandSettings.DrivingRegion item = getItem(position);
						AppCompatCheckedTextView title = (AppCompatCheckedTextView) v.findViewById(R.id.text1);
						TextView desc = (TextView) v.findViewById(R.id.description);
						if (item != null) {
							title.setText(getString(item.name));
							desc.setVisibility(View.VISIBLE);
							desc.setText(item.getDescription(v.getContext()));
						} else {
							title.setText(getString(R.string.driving_region_automatic));
							desc.setVisibility(View.GONE);
						}
						title.setChecked(position == selected);
						return v;
					}
				};

		b.setAdapter(singleChoiceAdapter, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (drs.get(which) == null) {
					settings.DRIVING_REGION_AUTOMATIC.set(true);
					MapViewTrackingUtilities mapViewTrackingUtilities = getMyApplication().getMapViewTrackingUtilities();
					if (mapViewTrackingUtilities != null) {
						mapViewTrackingUtilities.resetDrivingRegionUpdate();
					}
				} else {
					settings.DRIVING_REGION_AUTOMATIC.set(false);
					settings.DRIVING_REGION.set(drs.get(which));
				}
				updateAllSettings();
			}
		});

		b.setNegativeButton(R.string.shared_string_cancel, null);
		b.show();
	}


	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		String key = preference.getKey();
		if (key.equals(settings.OSMAND_THEME.getId())) {
			setupAppThemePref();
		} else if (key.equals(settings.ROTATE_MAP.getId())) {
			setupRotateMapPref();
		} else if (key.equals(settings.MAP_SCREEN_ORIENTATION.getId())) {
			setupMapScreenOrientationPref();
		}

		return super.onPreferenceChange(preference, newValue);
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (preference.getKey().equals(settings.DRIVING_REGION.getId())) {
			showDrivingRegionDialog();
			return true;
		}
		return super.onPreferenceClick(preference);
	}
}