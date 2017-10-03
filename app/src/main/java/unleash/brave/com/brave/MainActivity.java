package unleash.brave.com.brave;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends LocationHandler {

    // Declare a RelativeLayout
    RelativeLayout relativeLayout;
    // Declare a textview
    TextView no_internet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Locate the textview and relativelayout in activity_main.xml
        no_internet = (TextView) findViewById(R.id.no_internet);
        relativeLayout = (RelativeLayout) findViewById(R.id.internet);
        // Call isNetworkAvailable class
        if (!isNetworkAvailable()) {
            // Set the relativelayout visibility
            relativeLayout.setVisibility(View.VISIBLE);
            // Set the textview visibility
            no_internet.setVisibility(View.VISIBLE);
            // rotate the textview in 45 degrees
            no_internet.setRotation(-45);

            // Create an Alert Dialog
            openDialog();
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            // Set the Alert Dialog Message
//            builder.setMessage("Internet Connection Required")
//                    .setCancelable(false)
//                    .setPositiveButton("Retry",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog,
//                                                    int id) {
//                                    // Restart the Activity
//                                    Intent intent = getIntent();
//                                    finish();
//                                    startActivity(intent);
//                                }
//                            });
//            AlertDialog alert = builder.create();
//            alert.show();
        } else {
            Log.e("ONLINE", String.valueOf(isOnline()));
            if (isOnline() == true)
            {
                // Set the relativelayout visibility
                relativeLayout.setVisibility(View.INVISIBLE);
                // Set the textview visibility
                no_internet.setVisibility(View.INVISIBLE);

                Location _location = requestForCurrentLocation();
                Double _LAT = _location.getLatitude();
                Double _LONG = _location.getLongitude();
                Bundle bundle = new Bundle();
                bundle.putString("LATLONG", String.valueOf(_LAT) + ", " + String.valueOf(_LONG));

                if (savedInstanceState == null) {
                    WeatherFragment fragobj = new WeatherFragment();
                    fragobj.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, fragobj)
                            .commit();
                }
            }
            else
                openDialog();
        }

    }

    //Public Dialog box
    public void openDialog() {
        final Dialog dialog = new Dialog(MainActivity.this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_box);
        RelativeLayout dialog_button1 = (RelativeLayout) dialog.findViewById(R.id.dialog_info);
        RelativeLayout dialog_button2 = (RelativeLayout) dialog.findViewById(R.id.dialog_info1);
        dialog_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Restart the Activity
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        dialog_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Restart the Activity
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        dialog.show();
    }

    // Public class internet access
    public boolean isOnline() {
        return doSomething();
    }

    @Background
    protected boolean doSomething()
    {
        return true;
    }

    // Private class isNetworkAvailable
    private boolean isNetworkAvailable() {
        // Using ConnectivityManager to check for Network Connection
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.change_city){
            showInputDialog();
        }
        return false;
    }

    private void showInputDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change city");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeCity(input.getText().toString());
            }
        });
        builder.show();
    }

    public void changeCity(String city){
        WeatherFragment wf = (WeatherFragment)getSupportFragmentManager()
                .findFragmentById(R.id.container);
        wf.changeCity(city);
        new CityPreference(this).setCity(city);
    }

}
