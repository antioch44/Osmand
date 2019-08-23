package net.osmand.plus.settings;

import android.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;

import net.osmand.plus.ApplicationMode;
import net.osmand.plus.OsmandApplication;
import net.osmand.plus.R;
import net.osmand.plus.dialogs.ConfigureMapMenu;

public class GlobalSettingsFragment extends BaseSettingsFragment {

	public static final String TAG = "GlobalSettingsFragment";

	@Override
	protected int getPreferencesResId() {
		return R.xml.global_settings;
	}

	@Override
	protected int getToolbarResId() {
		return R.layout.global_preference_toolbar;
	}

	@Override
	protected String getToolbarTitle() {
		return getString(R.string.osmand_settings);
	}

	@Override
	protected void setupPreferences() {
		PreferenceScreen screen = getPreferenceScreen();

		Preference defaultApplicationMode = findPreference("default_application_mode_string");
		Preference preferredLocale = findPreference("preferred_locale");
		Preference externalStorageDir = findPreference("external_storage_dir");
		Preference sendAnonymousData = findPreference("send_anonymous_data");
		Preference doNotShowStartupMessages = findPreference("do_not_show_startup_messages");
		Preference enableProxy = findPreference("enable_proxy");

		final ApplicationMode selectedMode = getSelectedAppMode();

		int iconRes = selectedMode.getIconRes();
		String title = selectedMode.toHumanString(getContext());

		defaultApplicationMode.setIcon(getContentIcon(iconRes));
		defaultApplicationMode.setSummary(title);

		preferredLocale.setIcon(getContentIcon(R.drawable.ic_action_map_language));
		preferredLocale.setSummary(settings.PREFERRED_LOCALE.get());

		externalStorageDir.setIcon(getContentIcon(R.drawable.ic_action_folder));
//		sendAnonymousData.setIcon(getContentIcon(R.drawable.ic_world_globe_dark));
//		doNotShowStartupMessages.setIcon(getContentIcon(R.drawable.ic_action_manage_profiles));
		enableProxy.setIcon(getContentIcon(R.drawable.ic_action_proxy));
	}

	private void setupDefaultAppModePref(){
		String[] entries;
		String[] entrieValues;
		OsmandApplication app = getMyApplication();
		settings = app.getSettings();

		ApplicationMode[] appModes = ApplicationMode.values(app).toArray(new ApplicationMode[0]);
		entries = new String[appModes.length];
		for (int i = 0; i < entries.length; i++) {
			entries[i] = appModes[i].toHumanString(app);
		}
//		registerListPreference(settings.DEFAULT_APPLICATION_MODE, screen, entries, appModes);
	}

	private void setupPreferedLocalePref(){
		// See language list and statistics at: https://hosted.weblate.org/projects/osmand/main/
		// Hardy maintenance 2016-05-29:
		//  - Include languages if their translation is >= ~10%    (but any language will be visible if it is the device's system locale)
		//  - Mark as "incomplete" if                    < ~80%
		String incompleteSuffix = " (" + getString(R.string.incomplete_locale) + ")";

		// Add " (Device language)" to system default entry in Latin letters, so it can be more easily identified if a foreign language has been selected by mistake
		String latinSystemDefaultSuffix = " (" + getString(R.string.system_locale_no_translate) + ")";

		//getResources().getAssets().getLocales();
		String[] entriesValues = new String[]{"",
				"en",
				"af",
				"ar",
				"ast",
				"az",
				"be",
				//"be_BY",
				"bg",
				"ca",
				"cs",
				"cy",
				"da",
				"de",
				"el",
				"en_GB",
				"eo",
				"es",
				"es_AR",
				"es_US",
				"eu",
				"fa",
				"fi",
				"fr",
				"gl",
				"he",
				"hr",
				"hsb",
				"hu",
				"hy",
				"is",
				"it",
				"ja",
				"ka",
				"kab",
				"kn",
				"ko",
				"lt",
				"lv",
				"ml",
				"mr",
				"nb",
				"nl",
				"nn",
				"oc",
				"pl",
				"pt",
				"pt_BR",
				"ro",
				"ru",
				"sc",
				"sk",
				"sl",
				"sr",
				"sr+Latn",
				"sv",
				"tr",
				"uk",
				"vi",
				"zh_CN",
				"zh_TW"};
		String[] entries = new String[]{getString(R.string.system_locale) + latinSystemDefaultSuffix,
				getString(R.string.lang_en),
				getString(R.string.lang_af) + incompleteSuffix,
				getString(R.string.lang_ar),
				getString(R.string.lang_ast) + incompleteSuffix,
				getString(R.string.lang_az),
				getString(R.string.lang_be),
				// getString(R.string.lang_be_by),
				getString(R.string.lang_bg),
				getString(R.string.lang_ca),
				getString(R.string.lang_cs),
				getString(R.string.lang_cy) + incompleteSuffix,
				getString(R.string.lang_da),
				getString(R.string.lang_de),
				getString(R.string.lang_el) + incompleteSuffix,
				getString(R.string.lang_en_gb),
				getString(R.string.lang_eo),
				getString(R.string.lang_es),
				getString(R.string.lang_es_ar),
				getString(R.string.lang_es_us),
				getString(R.string.lang_eu),
				getString(R.string.lang_fa),
				getString(R.string.lang_fi) + incompleteSuffix,
				getString(R.string.lang_fr),
				getString(R.string.lang_gl),
				getString(R.string.lang_he) + incompleteSuffix,
				getString(R.string.lang_hr) + incompleteSuffix,
				getString(R.string.lang_hsb) + incompleteSuffix,
				getString(R.string.lang_hu),
				getString(R.string.lang_hy),
				getString(R.string.lang_is),
				getString(R.string.lang_it),
				getString(R.string.lang_ja),
				getString(R.string.lang_ka) + incompleteSuffix,
				getString(R.string.lang_kab) + incompleteSuffix,
				getString(R.string.lang_kn) + incompleteSuffix,
				getString(R.string.lang_ko),
				getString(R.string.lang_lt),
				getString(R.string.lang_lv),
				getString(R.string.lang_ml) + incompleteSuffix,
				getString(R.string.lang_mr) + incompleteSuffix,
				getString(R.string.lang_nb),
				getString(R.string.lang_nl),
				getString(R.string.lang_nn) + incompleteSuffix,
				getString(R.string.lang_oc) + incompleteSuffix,
				getString(R.string.lang_pl),
				getString(R.string.lang_pt),
				getString(R.string.lang_pt_br),
				getString(R.string.lang_ro) + incompleteSuffix,
				getString(R.string.lang_ru),
				getString(R.string.lang_sc),
				getString(R.string.lang_sk),
				getString(R.string.lang_sl),
				getString(R.string.lang_sr) + incompleteSuffix,
				getString(R.string.lang_sr_latn) + incompleteSuffix,
				getString(R.string.lang_sv),
				getString(R.string.lang_tr),
				getString(R.string.lang_uk),
				getString(R.string.lang_vi) + incompleteSuffix,
				getString(R.string.lang_zh_cn) + incompleteSuffix,
				getString(R.string.lang_zh_tw)};
//		String[] valuesPl = ConfigureMapMenu.getSortedMapNamesIds(this, entries, entries);
//		String[] idsPl = ConfigureMapMenu.getSortedMapNamesIds(this, entriesValues, entries);
//		registerListPreference(settings.PREFERRED_LOCALE, screen, valuesPl, idsPl);
//
//		// Add " (Display language)" to menu title in Latin letters for all non-en languages
//		if (!getResources().getString(R.string.preferred_locale).equals(getResources().getString(R.string.preferred_locale_no_translate))) {
//			((ListPreference) screen.findPreference(settings.PREFERRED_LOCALE.getId())).setTitle(getString(R.string.preferred_locale) + " (" + getString(R.string.preferred_locale_no_translate) + ")");
//		}

		// This setting now only in "Confgure map" menu
		//String[] values = ConfigureMapMenu.getMapNamesValues(this, ConfigureMapMenu.mapNamesIds);
		//String[] ids = ConfigureMapMenu.getSortedMapNamesIds(this, ConfigureMapMenu.mapNamesIds, values);
		//registerListPreference(settings.MAP_PREFERRED_LOCALE, screen, ConfigureMapMenu.getMapNamesValues(this, ids), ids);
	}
}
