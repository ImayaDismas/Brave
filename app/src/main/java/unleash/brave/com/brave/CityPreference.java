package unleash.brave.com.brave;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by root on 10/2/17.
 */

public class CityPreference {

    SharedPreferences prefs;

    public CityPreference(Activity activity) {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    // If the user has not chosen a city yet, return
    // Sydney as the default city
    String getCity() {
        return prefs.getString("city", "Nairobi, Kenya");
    }

    void setCity(String city) {
        prefs.edit().putString("city", city).commit();
    }
}

