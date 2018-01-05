package com.mygdx.anima.tools;

/**
 * Created by Steffen on 04.12.2017.
 */

public interface AdsController {
    public void showBannerAd();
    public void hideBannerAd();
    public void activateVideoFlag();
    public void deactiveVideoFlag();
    public boolean getVideoFlag();
    public boolean isWifiConnected();
    public boolean isMobileDataConnected();
    public void showInterstitialAd (Runnable then);
    public void loadRewardedVideoAd();
    public void showRewardedVideoAd();
    public void setRewardedVideoAdFinished(boolean value);
    public boolean getRewardedVideoAdFinished();
}
