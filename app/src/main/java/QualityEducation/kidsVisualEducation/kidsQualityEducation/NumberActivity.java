package QualityEducation.kidsVisualEducation.kidsQualityEducation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;

import QualityEducation.kidsVisualEducation.kidsQualityEducation.util.BackgroundSoundService;

public class NumberActivity extends Activity implements OnClickListener, OnTouchListener {

    ImageView nextBtn = null;
    ImageView playBtn = null;
    ImageView prevBtn = null;
    ImageView itemImage = null;
    private int currentPosition = 0;
    private int totalItem = 0;
    private MediaPlayer mp = null;
   // private AdView adView;
    ResourcePool resourcePool = new ResourcePool();
   // private InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.taw_activity_number);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

//        adView = findViewById(R.id.ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);

        Intent svc = new Intent(this, BackgroundSoundService.class);
        svc.putExtra("isPlay", false);
        startService(svc);

//        interstitialAd = new InterstitialAd(this);
//        interstitialAd.setAdUnitId(getString(R.string.google_interstitial));
//        loadinterstitilal();
//        load1();


        nextBtn = (ImageView) findViewById(R.id.nextId);
        playBtn = (ImageView) findViewById(R.id.playId);
        prevBtn = (ImageView) findViewById(R.id.prevId);
        nextBtn.setOnTouchListener(this);
        playBtn.setOnTouchListener(this);
        prevBtn.setOnTouchListener(this);

        itemImage = (ImageView) findViewById(R.id.numberItemId);

        nextBtn.setOnClickListener(this);
        playBtn.setOnClickListener(this);
        prevBtn.setOnClickListener(this);
        itemImage.setOnClickListener(this);

        totalItem = resourcePool.numberImages.length;
        itemImage.setImageResource(resourcePool.numberImages[currentPosition]);
        updatePreviousButton();

        mp = MediaPlayer.create(NumberActivity.this, resourcePool.numberSounds[currentPosition]);
        mp.start();

    }

//    private void load1() {
//        if (!interstitialAd.isLoading() && !interstitialAd.isLoaded()) {
//            AdRequest adRequest1 = new AdRequest.Builder().build();
//            interstitialAd.loadAd(adRequest1);
//        }
//
//    }
//
//    private void loadinterstitilal() {
//        interstitialAd.setAdListener(
//                new AdListener() {
//                    @Override
//                    public void onAdLoaded() {
//                        //dosomething
//                    }
//
//                    @Override
//                    public void onAdFailedToLoad(LoadAdError loadAdError) {
//                       //dosomething
//
//                    }
//
//                    @Override
//                    public void onAdClosed() {
//                        load1();
//                    }
//                });
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tawmain, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextId:
                interad();
                gotoNext();
                break;
            case R.id.prevId:
                interad();
                gotoPrevious();
                break;
            case R.id.playId:
                mp = MediaPlayer.create(NumberActivity.this, resourcePool.numberSounds[currentPosition]);
                mp.start();
                break;
            case R.id.numberItemId:
                mp = MediaPlayer.create(NumberActivity.this, resourcePool.numberSounds[currentPosition]);
                mp.start();
                break;

            default:
                break;
        }
    }

    private void gotoNext() {
        currentPosition++;
        updateNextButton();
        updatePreviousButton();
        if (currentPosition >= 0 && currentPosition < totalItem) {
            itemImage.setImageResource(resourcePool.numberImages[currentPosition]);

            mp = MediaPlayer.create(NumberActivity.this, resourcePool.numberSounds[currentPosition]);
            mp.start();
        }
    }

    private void gotoPrevious() {
        currentPosition--;
        updateNextButton();
        updatePreviousButton();
        if (currentPosition >= 0 && currentPosition < totalItem) {
            itemImage.setImageResource(resourcePool.numberImages[currentPosition]);

            mp = MediaPlayer.create(NumberActivity.this, resourcePool.numberSounds[currentPosition]);
            mp.start();
        }
    }

    private void updateNextButton() {
        if (currentPosition == totalItem - 1) {
            nextBtn.setAlpha(0.5f);
            nextBtn.setClickable(false);
        } else {
            nextBtn.setAlpha(1f);
            nextBtn.setClickable(true);
        }
    }

    private void updatePreviousButton() {
        if (currentPosition == 0) {
            prevBtn.setAlpha(0.5f);
            prevBtn.setClickable(false);
        } else {
            prevBtn.setAlpha(1f);
            prevBtn.setClickable(true);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setAlpha(0.5f);
                break;
            case MotionEvent.ACTION_UP:
                v.setAlpha(1.0f);
                break;

            default:
                break;
        }
        return false;
    }

    private void interad() {

        SharedPreferences prefss = getSharedPreferences("counter123", MODE_PRIVATE);
        final String count = prefss.getString("count", "");

        SharedPreferences prefssss = getApplicationContext().getSharedPreferences("counter123", 0);
        SharedPreferences.Editor editor = prefssss.edit();
        editor.putString("count", count + 1);
        Log.e("zzzz1", "onPageSelected: " + count);
        editor.commit();

        if (count.equals("1111")) {

            prefssss.edit().clear().commit();


            //showInterstitial();


        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NumberActivity.this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onPause() {
//        if (adView != null) {
//            adView.pause();
//        }
        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();
//        if (adView != null) {
//            adView.resume();
//        }
    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
//        if (adView != null) {
//            adView.destroy();
//        }
        super.onDestroy();
    }

//    private void showInterstitial() {
//        // Show the ad if it's ready. Otherwise toast and restart the game.
//        if (interstitialAd != null && interstitialAd.isLoaded()) {
//            interstitialAd.show();
//        } else {
//
//        }
//    }


}
