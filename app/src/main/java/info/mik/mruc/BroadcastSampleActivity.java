package info.mik.mruc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class BroadcastSampleActivity extends AppCompatActivity {

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.registerReceiver(this.mConnReceiver,
				new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
	}

	private BroadcastReceiver mConnReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			// this is kinda obsolete... no?
			//boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
			//String reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
			//boolean isFailover = intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false);

			// "'EXTRA_NETWORK_INFO' is deprecated as of API 16: Android 4.1"...
			//NetworkInfo currentNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
			//NetworkInfo otherNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);

			// ...so we have to use ConnectivityManager
			ConnectivityManager connectivityManager =
					(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

			boolean internetUp = false;

			if(activeNetwork != null && activeNetwork.isConnected()){
				internetUp = true;
			}

//			if (currentNetworkInfo.isConnected()) {
			if (internetUp) {
				// let's use modern SnackBar instead of old-fashioned Toast
				//Toast.makeText(getApplicationContext(), "Connected to the Internet!", Toast.LENGTH_LONG).show();

				// as per project requirements, notification will go away if the Internet is reconnected
                Snackbar.make(findViewById(android.R.id.content), "Connected to the Internet!", Snackbar.LENGTH_LONG).show();
			} else {
				//Toast.makeText(getApplicationContext(), "Not Connected to the Internet!", Toast.LENGTH_LONG).show();

				// as per project requirements, notification will stay up while the Internet is disconnected
                Snackbar.make(findViewById(android.R.id.content), "Not Connected to the Internet!", Snackbar.LENGTH_INDEFINITE).show();
			}
		}
	};
}
