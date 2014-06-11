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

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class PlantsVsBugs extends AdGameActivity {
	private AdView mAdView;
	private InterstitialAd interstitial;

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

		// Create the interstitial.
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId(getResources().getString(
				R.string.ad_unit_id_inter));

		// Create ad request.
		AdRequest adRequest = new AdRequest.Builder().build();

		// Begin loading your interstitial.
		interstitial.loadAd(adRequest);

		interstitial.setAdListener(new AdListener() {
			public void onAdLoaded() {
				displayInterstitial();
			}
		});

		// classic ads
		mAdView = new AdView(this);
		mAdView.setAdUnitId(getResources().getString(R.string.ad_unit_id));
		mAdView.setAdSize(AdSize.SMART_BANNER);
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.myAds);
		layout.addView(mAdView);
		mAdView.loadAd(new AdRequest.Builder().build());
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
					}
				}));
		return splashScene;
		// return new Game();
	}

}