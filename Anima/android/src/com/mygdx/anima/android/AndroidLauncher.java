package com.mygdx.anima.android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.mygdx.anima.AnimaRPG;
import com.mygdx.anima.tools.AdsController;

public class AndroidLauncher extends AndroidApplication implements AdsController,RewardedVideoAdListener {
    private static final String BANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917";
    private static final String INTERSTITIAL_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";
    AdView bannerAd;
    InterstitialAd interstitialAd;
    RewardedVideoAd mRewardedVideoAd;
    private boolean rewardedVideoAdFinished;
    @Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        // Create a gameView and a bannerAd AdView
        View gameView = initializeForView(new AnimaRPG(this), config);
        setupAds();

        // Create a rewarding Video
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);


        // Define the Layout
        RelativeLayout layout = new RelativeLayout(this);
        layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layout.addView(bannerAd, params);

        setContentView(layout);
	}
    @Override
    public void showBannerAd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bannerAd.setVisibility(View.VISIBLE);
                AdRequest.Builder builder = new AdRequest.Builder();
                AdRequest ad = builder.build();
                bannerAd.loadAd(ad);
            }
        });
    }

    @Override
    public void hideBannerAd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bannerAd.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void setupAds() {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(INTERSTITIAL_UNIT_ID);

        AdRequest.Builder builder = new AdRequest.Builder();
        AdRequest ad = builder.build();
        interstitialAd.loadAd(ad);
        bannerAd = new AdView(this);
        bannerAd.setVisibility(View.INVISIBLE);
        bannerAd.setBackgroundColor(0xff000000); // black
        bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
        bannerAd.setAdSize(AdSize.SMART_BANNER);
    }

    @Override
    public boolean isWifiConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return (ni != null && ni.isConnected());
    }
    @Override
    public void showInterstitialAd(final Runnable then) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (then != null) {
                    interstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Gdx.app.postRunnable(then);
                            AdRequest.Builder builder = new AdRequest.Builder();
                            AdRequest ad = builder.build();
                            interstitialAd.loadAd(ad);
                        }
                    });
                }
                interstitialAd.show();
            }
        });
    }
    public void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
    }
    public void showRewardedVideoAd(){
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }

    @Override
    public void setRewardedVideoAdFinished(boolean value) {
        rewardedVideoAdFinished=value;
    }

    @Override
    public boolean getRewardedVideoAdFinished() {
        return rewardedVideoAdFinished;
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }
    @Override
    public void onRewardedVideoAdOpened() {

    }
    @Override
    public void onRewardedVideoStarted() {

    }
    @Override
    public void onRewardedVideoAdClosed() {

    }
    @Override
    public void onRewarded(RewardItem reward) {
        Toast.makeText(this, "onRewarded! currency: " + reward.getType() + "  amount: " +
                reward.getAmount(), Toast.LENGTH_SHORT).show();
        rewardedVideoAdFinished=true;
        // Reward the user.
    }
    @Override
    public void onRewardedVideoAdLeftApplication() {

    }
    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

}
