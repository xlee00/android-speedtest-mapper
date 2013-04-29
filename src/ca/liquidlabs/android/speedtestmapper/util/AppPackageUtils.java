
package ca.liquidlabs.android.speedtestmapper.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;

import java.util.Locale;

/**
 * Collection of utility methods to perform actions.
 */
public class AppPackageUtils {
    private static final String LOG_TAG = AppPackageUtils.class.getSimpleName();

    /**
     * Get proper intent for SpeedTest app
     * 
     * @return Returns either a fully-qualified Intent, or null if the package
     *         does not contain such an activity.
     */
    public static Intent getSpeedTestAppIntent(Context context) {
        // get intent by KNOWN Package Name
        return context.getPackageManager().getLaunchIntentForPackage(
                AppConstants.PACKAGE_SPEEDTEST_APP);
    }

    /**
     * Helper method to quickly check if SpeedTest app is installed
     * 
     * @param context Application context
     * @return {@code true} if SpeedTest app exist, {@code false} otherwise
     */
    public static boolean isSpeedTestAppInstalled(Context context) {
        return isAppInstalled(context, AppConstants.PACKAGE_SPEEDTEST_APP);
    }

    /**
     * @param context Application context
     * @param packageName Fully qualified package name of app
     * @return {@code true} when installed, {@code false} otherwise
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            Tracer.debug(LOG_TAG, packageName + " - isAppInstalled : true");

            // app package exists
            return true;
        } catch (NameNotFoundException e) {
            Tracer.debug(LOG_TAG, packageName + " - isAppInstalled : false");

            // unable to get the app - its not installed
            return false;
        }
    }

    /**
     * Get drawable icon for application by package name. To avoid null value
     * you must call {@link #isSpeedTestAppInstalled(Context)} before
     * 
     * @param context Application context
     * @param packageName Fully qualified package name of app
     * @return {@code null} when no app is found, otherwise {@link Drawable}
     *         icon of app
     */
    public static Drawable getAppIcon(Context context, String packageName) {
        try {
            return context.getPackageManager().getApplicationIcon(packageName);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    /**
     * Util method to get app name and version.
     * 
     * @param context Application context
     * @param packageName Fully qualified package name of app
     * @return Human readable version info of the application
     */
    public static String getApplicationVersionInfo(Context context, String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_ACTIVITIES);
        } catch (NameNotFoundException e) {
            // unable to find application
            return "";
        }

        String appName = "N/A";
        if (packageInfo.applicationInfo != null) {
            appName = (String) context.getPackageManager().getApplicationLabel(
                    packageInfo.applicationInfo);
        }
        return String.format(Locale.US, "\nApp: %s\nVersion: %s\n", appName, packageInfo.versionName);
    }
}
