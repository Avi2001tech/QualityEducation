package QualityEducation.kidsVisualEducation.kidsQualityEducation.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtils {

    public static final String PREF_SOUND = "pref_sound";
    public static final String PREF_MUSIC = "pref_music";


    public static boolean isSoundOn(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_SOUND, true);
    }

    public static void markSoundOn(final Context context, boolean b) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_SOUND, b).commit();
    }

    public static boolean isMusicOn(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_MUSIC, true);
    }

    public static boolean markMusicOn(final Context context, boolean b) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_MUSIC, b).commit();
        return b;
    }

}