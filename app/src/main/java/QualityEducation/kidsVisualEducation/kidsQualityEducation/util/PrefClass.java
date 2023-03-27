package QualityEducation.kidsVisualEducation.kidsQualityEducation.util;

import android.content.Context;
import android.content.SharedPreferences;


public class PrefClass {

	public static Context context;
	static SharedPreferences pref;
	private static SharedPreferences.Editor edit;

	public PrefClass(Context context) {
		PrefClass.context = context;
		pref = context.getSharedPreferences("SETTINGS", Context.MODE_PRIVATE);
	}

	public static void saveColor(int color) {
		edit = pref.edit();
		edit.putInt("COLOR", color);
		edit.commit();
	}

	public static void saveHint(int hint) {
		edit = pref.edit();
		edit.putInt("HINT", hint);
		edit.commit();
	}

	public static void setLastSharedTime(long time) {
		edit = pref.edit();
		edit.putLong("LASTSHARED", time);
		edit.commit();
	}

	public static long getLastSharedTime() {
		return pref.getLong("LASTSHARED", 0);
	}

	public static int getHint() {
		return pref.getInt("HINT", 5);
	}

	public static int getFileIndex() {
		return pref.getInt("FILE", 1);
	}

	public static void setFileIndex(int value) {
		edit = pref.edit();
		edit.putInt("FILE", value);
		edit.commit();
	}

	public static int getAdsVar() {
		return pref.getInt("ADSVAR", 0);
	}


	public static boolean isPremium() {
		return pref.getBoolean("ISPREMIUM", false);
	}

	public static boolean getSound() {
		return pref.getBoolean("SOUND", true);
	}

	public static void setSound(boolean sound) {
		edit = pref.edit();
		edit.putBoolean("SOUND", sound);
		edit.commit();
	}

	public static String getTotalLevel() {
		return pref.getString("LEVEl", "");
	}

	public static void setTotalLevel(String value) {
		edit = pref.edit();
		edit.putString("LEVEl", value);
		edit.commit();
	}
	public static void setMedia(boolean media){
		edit.putBoolean("MEDIA", media);
		edit.commit();
	}

	public static boolean getMedia() {
		return pref.getBoolean("MEDIA", false);
	}

}
