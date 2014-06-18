package org.anddev.amatidev.pvb;

import org.amatidev.activity.AdGameActivity;
import org.amatidev.util.AdEnviroment;
import org.anddev.amatidev.pvb.singleton.GameData;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.SplashScene;

import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

public class PlantsVsBugs extends AdGameActivity implements
		GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener {
	private AdView mAdView;
	private InterstitialAd interstitial;

	// Request code to use when launching the resolution activity
	private static final int REQUEST_RESOLVE_ERROR = 1001;
	// Bool to track whether the app is already resolving an error
	private boolean mResolvingError = false;

	private GoogleApiClient mGoogleApiClient;

	public GoogleApiClient getApiClient() {
		return mGoogleApiClient;
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Log.e("Game Service", "Not connected " + result.getErrorCode());

		if (mResolvingError) {
			// Already attempting to resolve an error.
			return;
		} else if (result.hasResolution()) {
			try {
				mResolvingError = true;
				result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
			} catch (SendIntentException e) {
				// There was an error with the resolution intent. Try again.
				mGoogleApiClient.connect();
			}
		} else {
			// Show dialog using GooglePlayServicesUtil.getErrorDialog()
			// showErrorDialog(result.getErrorCode());

			mResolvingError = true;
		}
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// Toast.makeText(this, "Connected", Toast.LENGTH_LONG);
	}

	@Override
	public void onConnectionSuspended(int cause) {
		// TODO Stub di metodo generato automaticamente

	}

	@Override
	public void onResume() {
		super.onResume();
		mGoogleApiClient.connect();
	}

	@Override
	protected void onPause() {
		mGoogleApiClient.disconnect();
		super.onPause();
	}

	@Override
	public void onStart() {
		super.onStart();
		if (!mResolvingError) {
			mGoogleApiClient.connect();
		}
	}

	@Override
	protected void onStop() {
		mGoogleApiClient.disconnect();
		super.onStop();
	}

	@Override
	protected int getLayoutID() {
		return R.layout.main;
	}

	@Override
	protected int getRenderSurfaceViewID() {
		return R.id.xmllayoutexample_rendersurfaceview;
	}

	@Override
	protected void onCreate(final Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);

		// create an instance of Google API client and specify the Play services
		// and scopes to use. In this example, we specify that the app wants
		// access to the Games, Plus, and Cloud Save services and scopes.
		mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Games.API)
				.addScope(Games.SCOPE_GAMES).addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).build();
	}

	// Invoke displayInterstitial() when you are ready to display an
	// interstitial.
	public void displayInterstitial() {
		if (interstitial.isLoaded()) {
			interstitial.show();
		}
	}

	@Override
	public void onLoadComplete() {
		/*
		 * try { Map<String, Object> options = new HashMap<String, Object>();
		 * options.put(OpenFeintSettings.SettingCloudStorageCompressionStrategy,
		 * OpenFeintSettings.CloudStorageCompressionStrategyDefault); // use the
		 * below line to set orientation
		 * options.put(OpenFeintSettings.RequestedOrientation,
		 * ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); OpenFeintSettings
		 * settings = new OpenFeintSettings(getString(R.string.app_name),
		 * getString(R.string.productKey), getString(R.string.productSecret),
		 * getString(R.string.clientAppID), options);
		 * 
		 * OpenFeint.initialize(this, settings, new OpenFeintDelegate() { }); }
		 * catch (Exception e) {
		 * 
		 * }
		 */

		// classic ads
		mAdView = new AdView(this);
		mAdView.setAdUnitId(getResources().getString(R.string.ad_unit_id));
		mAdView.setAdSize(AdSize.SMART_BANNER);
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.myAds);
		layout.addView(mAdView);
		mAdView.loadAd(new AdRequest.Builder().build());
	}

	@Override
	public Engine onLoadEngine() {
		return AdEnviroment.createEngine(ScreenOrientation.LANDSCAPE, true,
				false);
	}

	@Override
	public void onLoadResources() {
		GameData.getInstance().initData();
	}

	@Override
	public Scene onLoadScene() {
		SplashScene splashScene = new SplashScene(this.mEngine.getCamera(),
				GameData.getInstance().mSplash, 0f, 1f, 1f);
		splashScene.registerUpdateHandler(new TimerHandler(7f,
				new ITimerCallback() {
					@Override
					public void onTimePassed(final TimerHandler pTimerHandler) {
						AdEnviroment.getInstance().setScene(new MainMenu());

						runOnUiThread(new Runnable() {
							public void run() {
								// Create the interstitial.
								interstitial = new InterstitialAd(
										PlantsVsBugs.this);
								interstitial.setAdUnitId(getResources()
										.getString(R.string.ad_unit_id_inter));

								// Create ad request.
								AdRequest adRequest = new AdRequest.Builder()
										.build();

								// Begin loading your interstitial.
								interstitial.loadAd(adRequest);

								interstitial.setAdListener(new AdListener() {
									public void onAdLoaded() {
										displayInterstitial();
									}
								});
							}
						});
					}
				}));
		return splashScene;
		// return new Game();
	}

}