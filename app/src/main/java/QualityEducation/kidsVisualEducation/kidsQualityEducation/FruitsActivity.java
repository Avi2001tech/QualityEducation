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
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import QualityEducation.kidsVisualEducation.kidsQualityEducation.util.BackgroundSoundService;

public class FruitsActivity extends Activity implements OnClickListener, OnTouchListener, RewardedVideoAdListener {
    private String type = "";

    ImageView nextBtn = null;
    ImageView playBtn = null;
    ImageView prevBtn = null;
    ImageView itemImage = null;
    ImageView itemName = null;
    private RewardedVideoAd mRewardedVideoAd;
    private int currentPosition = 0;
    private int totalItem = 0;
    private MediaPlayer mediaPlayer = null;
    private AdView adView;
    ResourcePool resourcePool = new ResourcePool();
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.taw_activity_detail);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        adView = findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        Intent svc = new Intent(this, BackgroundSoundService.class);
        svc.putExtra("isPlay", false);
        startService(svc);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.google_interstitial));
        loadinterstitilal();

        load1();


        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");

        nextBtn = (ImageView) findViewById(R.id.nextId);
        playBtn = (ImageView) findViewById(R.id.playId);
        prevBtn = (ImageView) findViewById(R.id.prevId);
        nextBtn.setOnTouchListener(this);
        playBtn.setOnTouchListener(this);
        prevBtn.setOnTouchListener(this);

        itemImage = (ImageView) findViewById(R.id.itemImageId);
        itemName = (ImageView) findViewById(R.id.itemImageId);

        nextBtn.setOnClickListener(this);
        playBtn.setOnClickListener(this);
        prevBtn.setOnClickListener(this);
        itemImage.setOnClickListener(this);
        itemName.setOnClickListener(this);

        if (type.equals(ResourcePool.fruit)) {
            totalItem = resourcePool.fruitImages.length;
            itemImage.setImageResource(resourcePool.fruitImages[currentPosition]);

        } else if (type.equals(ResourcePool.comunity)) {
            totalItem = resourcePool.comunityHelpArray.length;
            itemImage.setImageResource(resourcePool.comunityHelpArray[currentPosition]);

        }else if (type.equals(ResourcePool.veg)) {
            totalItem = resourcePool.vegetable.length;
            itemImage.setImageResource(resourcePool.vegetable[currentPosition]);

        }else if (type.equals(ResourcePool.vehical)) {
            totalItem = resourcePool.vihical.length;
            itemImage.setImageResource(resourcePool.vihical[currentPosition]);

        }else if (type.equals(ResourcePool.shape)) {
            totalItem = resourcePool.shapeArray.length;
            itemImage.setImageResource(resourcePool.shapeArray[currentPosition]);
        }else if (type.equals(ResourcePool.color)) {
            totalItem = resourcePool.colorArray.length;
            itemImage.setImageResource(resourcePool.colorArray[currentPosition]);
        }else if (type.equals(ResourcePool.day)) {
            totalItem = resourcePool.dayArray.length;
            itemImage.setImageResource(resourcePool.dayArray[currentPosition]);
        }else if (type.equals(ResourcePool.months)) {
            totalItem = resourcePool.monthsArray.length;
            itemImage.setImageResource(resourcePool.monthsArray[currentPosition]);
        }else if (type.equals(ResourcePool.bodypart)) {
            totalItem = resourcePool.body.length;
            itemImage.setImageResource(resourcePool.body[currentPosition]);
        } else {
            totalItem = resourcePool.animalImages.length;
            itemImage.setImageResource(resourcePool.animalImages[currentPosition]);
        }
        playSound();

        updatePreviousButton();

    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(getString(R.string.google_reward),
                new AdRequest.Builder().build());
    }

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
                playSound();
                break;

            case R.id.itemImageId:
                if (type.equals(ResourcePool.fruit)) {
                    mediaPlayer = MediaPlayer.create(FruitsActivity.this, resourcePool.fruitsSound[currentPosition]);
                } else if (type.equals(ResourcePool.comunity)) {
                    mediaPlayer = MediaPlayer.create(FruitsActivity.this, resourcePool.comunityHelpSoundArray[currentPosition]);
                }else if (type.equals(ResourcePool.veg)) {
                    mediaPlayer = MediaPlayer.create(FruitsActivity.this, resourcePool.vegetableSoundArray[currentPosition]);
                }else if (type.equals(ResourcePool.vehical)) {
                    mediaPlayer = MediaPlayer.create(FruitsActivity.this, resourcePool.vihicalSoundArray[currentPosition]);
                }else if (type.equals(ResourcePool.shape)) {
                    mediaPlayer = MediaPlayer.create(FruitsActivity.this, resourcePool.shapeSoundArray[currentPosition]);
                }else if (type.equals(ResourcePool.color)) {
                    mediaPlayer = MediaPlayer.create(FruitsActivity.this, resourcePool.colorSoundArray[currentPosition]);
                }else if (type.equals(ResourcePool.day)) {
                    mediaPlayer = MediaPlayer.create(FruitsActivity.this, resourcePool.daySounArray[currentPosition]);
                }else if (type.equals(ResourcePool.months)) {
                    mediaPlayer = MediaPlayer.create(FruitsActivity.this, resourcePool.monthsSound[currentPosition]);
                }else if (type.equals(ResourcePool.bodypart)) {
                    mediaPlayer = MediaPlayer.create(FruitsActivity.this, resourcePool.bodySoundArray[currentPosition]);
                }else {
                    mediaPlayer = MediaPlayer.create(FruitsActivity.this, resourcePool.animalSound[currentPosition]);
                }
                mediaPlayer.start();
                break;


            default:
                break;
        }
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
            showInterstitial();

        }
    }

    private void gotoNext() {
        currentPosition++;
        updateNextButton();
        updatePreviousButton();
        if (currentPosition >= 0 && currentPosition < totalItem) {
            if (type.equals(ResourcePool.fruit)) {
                itemImage.setImageResource(resourcePool.fruitImages[currentPosition]);
            }else if (type.equals(ResourcePool.comunity)) {
                itemImage.setImageResource(resourcePool.comunityHelpArray[currentPosition]);
            }else if (type.equals(ResourcePool.veg)) {
                itemImage.setImageResource(resourcePool.vegetable[currentPosition]);

            }else if (type.equals(ResourcePool.vehical)) {
                itemImage.setImageResource(resourcePool.vihical[currentPosition]);
            }else if (type.equals(ResourcePool.shape)) {
                itemImage.setImageResource(resourcePool.shapeArray[currentPosition]);
            }else if (type.equals(ResourcePool.color)) {
                itemImage.setImageResource(resourcePool.colorArray[currentPosition]);
            }else if (type.equals(ResourcePool.day)) {
                itemImage.setImageResource(resourcePool.dayArray[currentPosition]);
            }else if (type.equals(ResourcePool.months)) {
                itemImage.setImageResource(resourcePool.monthsArray[currentPosition]);
            }else if (type.equals(ResourcePool.bodypart)) {
                itemImage.setImageResource(resourcePool.body[currentPosition]);
            }
            else {
                itemImage.setImageResource(resourcePool.animalImages[currentPosition]);
            }
            playSound();
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

    private void gotoPrevious() {
        currentPosition--;
        updateNextButton();
        updatePreviousButton();
        if (currentPosition >= 0 && currentPosition < totalItem) {
            if (type.equals(ResourcePool.fruit)) {
                itemImage.setImageResource(resourcePool.fruitImages[currentPosition]);
            }else if (type.equals(ResourcePool.comunity)) {
                itemImage.setImageResource(resourcePool.comunityHelpArray[currentPosition]);
            }else if (type.equals(ResourcePool.veg)) {
                itemImage.setImageResource(resourcePool.vegetable[currentPosition]);

            }else if (type.equals(ResourcePool.vehical)) {
                itemImage.setImageResource(resourcePool.vihical[currentPosition]);

            }else if (type.equals(ResourcePool.shape)) {
                itemImage.setImageResource(resourcePool.shapeArray[currentPosition]);
            }else if (type.equals(ResourcePool.color)) {
                itemImage.setImageResource(resourcePool.colorArray[currentPosition]);

            }else if (type.equals(ResourcePool.day)) {
                itemImage.setImageResource(resourcePool.dayArray[currentPosition]);
            }else if (type.equals(ResourcePool.months)) {
                itemImage.setImageResource(resourcePool.monthsArray[currentPosition]);
            }else if (type.equals(ResourcePool.bodypart)) {
                itemImage.setImageResource(resourcePool.body[currentPosition]);
            }
            else {
                itemImage.setImageResource(resourcePool.animalImages[currentPosition]);
            }
            playSound();
        }
    }

    private void playSound() {
        if (type.equals(ResourcePool.fruit)) {
            mediaPlayer = MediaPlayer.create(FruitsActivity.this, resourcePool.fruitsSound[currentPosition]);
        }else if (type.equals(ResourcePool.comunity)) {
            mediaPlayer = MediaPlayer.create(FruitsActivity.this, resourcePool.comunityHelpSoundArray[currentPosition]);
        }else if (type.equals(ResourcePool.veg)) {
            mediaPlayer = MediaPlayer.create(FruitsActivity.this, resourcePool.vegetableSoundArray[currentPosition]);

        }else if (type.equals(ResourcePool.vehical)) {
            mediaPlayer = MediaPlayer.create(FruitsActivity.this, resourcePool.vihicalSoundArray[currentPosition]);

        }else if (type.equals(ResourcePool.shape)) {
            mediaPlayer = MediaPlayer.create(FruitsActivity.this, resourcePool.shapeSoundArray[currentPosition]);

        }else if (type.equals(ResourcePool.color)) {
            mediaPlayer = MediaPlayer.create(FruitsActivity.this, resourcePool.colorSoundArray[currentPosition]);
        }else if (type.equals(ResourcePool.day)) {
            mediaPlayer = MediaPlayer.create(FruitsActivity.this, resourcePool.daySounArray[currentPosition]);
        }else if (type.equals(ResourcePool.months)) {
            mediaPlayer = MediaPlayer.create(FruitsActivity.this, resourcePool.monthsSound[currentPosition]);
        }else if (type.equals(ResourcePool.bodypart)) {
            mediaPlayer = MediaPlayer.create(FruitsActivity.this, resourcePool.bodySoundArray[currentPosition]);
        }
        else {
            mediaPlayer = MediaPlayer.create(FruitsActivity.this, resourcePool.animalSound[currentPosition]);
        }
        mediaPlayer.start();
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

    @Override
    public void onBackPressed() {

        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }else {
            Intent intent = new Intent(FruitsActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void loadinterstitilal() {
        interstitialAd.setAdListener(
                new AdListener() {
                    @Override
                    public void onAdLoaded() {
//dosomething
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        //dosomething
                    }

                    @Override
                    public void onAdClosed() {
                        load1();
                    }
                });
    }

    private void load1() {
        if (!interstitialAd.isLoading() && !interstitialAd.isLoaded()) {
            AdRequest adRequest1 = new AdRequest.Builder().build();
            interstitialAd.loadAd(adRequest1);
        }

    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
//dosomething
        }
    }

    @Override
    public void onRewarded(RewardItem reward) {
        // Reward the user.
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Intent intent = new Intent(FruitsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
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
    public void onRewardedVideoCompleted() {
    }
}
