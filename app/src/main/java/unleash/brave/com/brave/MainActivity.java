package unleash.brave.com.brave;


import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends LocationHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Location _location = requestForCurrentLocation();
        Double _LAT = _location.getLatitude();
        Double _LONG = _location.getLongitude();

        Address _address = getAddress(_LAT, _LONG);
        if (_address != null)
        {
            String _ADDR = _address.getAddressLine(0);
            String _NICK = _address.getFeatureName();
            Log.e("PICKUP", _ADDR);
        }
        else
        {
            String _ADDR = "Undefined";
            String _NICK = "Undefined";
            Log.e("PICKUP", _ADDR);
        }
    }
    public Address getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();

            return obj;
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
