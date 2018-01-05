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

import java.lang.reflect.Method;

public class AndroidLauncher extends AndroidApplication implements AdsController,RewardedVideoAdListener {


    // Test-IDs
    //private static final String BANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917";
    //private static final String INTERSTITIAL_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";
    //private static final String REWARDED_VIDEO_ID = "ca-app-pub-3940256099942544/5224354917";

    //Steffens IDs
    private static final String Steffen_Acc_ID="ca-app-pub-2997944130485417~6399308541";
    private static final String BANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917";
    private static final String INTERSTITIAL_UNIT_ID = "ca-app-pub-2997944130485417/5187257083";
    private static final String REWARDED_VIDEO_ID = "ca-app-pub-2997944130485417/4259381277";
    AdView bannerAd;
    InterstitialAd interstitialAd;
    RewardedVideoAd mRewardedVideoAd;
    private boolean rewardedVideoAdFinished,rewardedVideoAdWarteFlag;
    public RewardedVideoAdListener vel;
    AdRequest.Builder builder;


    @Override
	protected void onCreate (Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        // Create a gameView and a bannerAd AdView
        View gameView = initializeForView(new AnimaRPG(this), config);
        setupAds();
        MobileAds.initialize(this, Steffen_Acc_ID);
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
	// Für Banner, wird nicht genutzt
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

        builder = new AdRequest.Builder();
        builder.addTestDevice("08D5FA3AF0461D86E59908EA9535C581");
        AdRequest ad = builder.build();
        interstitialAd.loadAd(ad);
        loadRewardedVideoAd();
        bannerAd = new AdView(this);
        bannerAd.setVisibility(View.INVISIBLE);
        bannerAd.setBackgroundColor(0xff000000); // black
        bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
        bannerAd.setAdSize(AdSize.SMART_BANNER);
    }

    @Override
    public boolean isWifiConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo.isConnected();
        //networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        //boolean isMobileConn = networkInfo.isConnected();
       // System.out.println("WIFI Methode ist :"+(networkInfo != null && isWifiConn )+ " " +(networkInfo != null && isMobileConn )+"daraus entsteht"+ ((networkInfo != null && isWifiConn ) || (networkInfo != null && isMobileConn )));
       // return ((networkInfo != null && isWifiConn ) || (networkInfo != null && isMobileConn ));
        return ((networkInfo != null && isWifiConn ));
    }
    @Override
    public boolean isMobileDataConnected(){
        boolean mobileDataEnabled = false; // Assume disabled
    try {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        Class cmClass = Class.forName(cm.getClass().getName());
        Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
        method.setAccessible(true); // Make the method callable
        // get the setting for "mobile data"
        mobileDataEnabled = (Boolean)method.invoke(cm);
    } catch (Exception e) {
        // Some problem accessible private API
        // TODO do whatever error handling you want here
    }
        return mobileDataEnabled;
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
        if(isWifiConnected() || isMobileDataConnected()) {
           // mRewardedVideoAd.loadAd(REWARDED_VIDEO_ID,
             //       builder.build());
        }
    }
    public void showRewardedVideoAd(){
        runOnUiThread(new Runnable() {
            public void run() {
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                } else {
                    loadRewardedVideoAd();
                }
            }
        });
        /*if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }*/
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
        loadRewardedVideoAd();
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

    @Override
    public void activateVideoFlag() {
        rewardedVideoAdWarteFlag=true;
    }

    @Override
    public void deactiveVideoFlag() {
        rewardedVideoAdWarteFlag=false;
    }

    @Override
    public boolean getVideoFlag() {
        return rewardedVideoAdWarteFlag;
    }
}
