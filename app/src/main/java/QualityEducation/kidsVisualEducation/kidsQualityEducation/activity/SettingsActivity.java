package QualityEducation.kidsVisualEducation.kidsQualityEducation.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import QualityEducation.kidsVisualEducation.kidsQualityEducation.R;
import QualityEducation.kidsVisualEducation.kidsQualityEducation.util.BackgroundSoundService;
import QualityEducation.kidsVisualEducation.kidsQualityEducation.util.PrefClass;
import QualityEducation.kidsVisualEducation.kidsQualityEducation.util.PrefUtils;

public class SettingsActivity extends AppCompatActivity {

    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    View rootLayout;
    private int revealX;
    String prefNname = "vibration";
    String prefVibration = "TicVib";
    int revealY;
    boolean vibration;
    ImageView text;
    String name;
    ImageView imgRateUs;
    ImageView imgShare;
    ImageView sound;
    ImageView music;
   // AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences preferences = getSharedPreferences(prefNname, MODE_PRIVATE);
        vibration = preferences.getBoolean(prefVibration, true);

        setContentView(R.layout.activity_settings);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
//        adView = findViewById(R.id.ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);

        SharedPreferences prefs = getSharedPreferences("theme", MODE_PRIVATE);
        name = prefs.getString("name", "default");//"No name defined" is the default value.


        text = findViewById(R.id.text);
        rootLayout = findViewById(R.id.rootlay);
        imgShare = findViewById(R.id.imgShare);
        imgRateUs = findViewById(R.id.imgRateUs);
        sound = findViewById(R.id.img_sound);
        music = findViewById(R.id.img_music);

        Glide.with(getApplicationContext())
                .load(R.drawable.sound)
                .into(sound);
        Glide.with(getApplicationContext())
                .load(R.drawable.music)
                .into(music);

        if (PrefUtils.isMusicOn(getApplicationContext())) {
            Glide.with(getApplicationContext())
                    .load(R.drawable.music)
                    .into(music);
        } else {
            Glide.with(getApplicationContext())
                    .load(R.drawable.music_off)
                    .into(music);
        }

        if (PrefUtils.isSoundOn(getApplicationContext())) {
            Glide.with(getApplicationContext())
                    .load(R.drawable.sound)
                    .into(sound);
        } else {
            Glide.with(getApplicationContext())
                    .load(R.drawable.sound_off)
                    .into(sound);
        }

        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PrefUtils.isSoundOn(getApplicationContext())) {
                    Glide.with(getApplicationContext())
                            .load(R.drawable.sound_off)
                            .into(sound);
                    PrefUtils.markSoundOn(getApplicationContext(), false);

                } else {
                    Glide.with(getApplicationContext())
                            .load(R.drawable.sound)
                            .into(sound);
                    PrefUtils.markSoundOn(getApplicationContext(), true);

                }
            }
        });
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PrefUtils.isMusicOn(getApplicationContext())) {
                    Glide.with(getApplicationContext())
                            .load(R.drawable.music_off)
                            .into(music);
                    PrefUtils.markMusicOn(getApplicationContext(), false);
                    PrefClass.setSound(false);

                    Intent svc = new Intent(getApplicationContext(), BackgroundSoundService.class);
                    svc.putExtra("isPlay", false);
                    getApplicationContext().startService(svc);

                } else {
                    Glide.with(getApplicationContext())
                            .load(R.drawable.music)
                            .into(music);
                    PrefUtils.markMusicOn(getApplicationContext(), true);
                    PrefClass.setSound(true);

                    Intent svc = new Intent(getApplicationContext(), BackgroundSoundService.class);
                    svc.putExtra("isPlay", true);
                    getApplicationContext().startService(svc);

                }
            }
        });


        imgRateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // rating();
            }
        });

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Share this Learning Kids Game for great future. " + "https://play.google.com/store/apps/details?id=freecode.kidsgame.kidslearninggame";
                String shareSub = "Your subject here";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivityForResult(sharingIntent, 0);
            }
        });

        final Intent intent = getIntent();

        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(
                        EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(
                        EXTRA_CIRCULAR_REVEAL_Y)) {

            rootLayout.setVisibility(View.INVISIBLE);

            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);

            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);
            ViewTreeObserver viewTreeObserver =
                    rootLayout.getViewTreeObserver();

            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(
                        new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override

                            public void onGlobalLayout() {
                                revealActivity(
                                        revealX, revealY);

                                rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        });
            }
        } else {

            rootLayout.setVisibility(View.VISIBLE);
        }
    }


    protected void revealActivity(int x, int y) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);

// create the animator for this view (the start radius is zero)

            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0, finalRadius);
            circularReveal.setDuration(400);
            circularReveal.setInterpolator(
                    new AccelerateInterpolator());

// make the view visible and start the animation

            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();
        } else {
            finish();
        }
    }

    protected void unRevealActivity() {


        float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);
        Animator circularReveal = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            circularReveal = ViewAnimationUtils.
                    createCircularReveal(

                            rootLayout, revealX, revealY, finalRadius, 0);
        }
        circularReveal.setDuration(400);
        circularReveal.addListener(
                new AnimatorListenerAdapter() {
                    @Override

                    public void onAnimationEnd(Animator animation) {

                        rootLayout.setVisibility(View.INVISIBLE);

                        //finish Activity.
                        finish();

                    }
                });
        circularReveal.start();

    }

    private void rating() {
        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
        }
    }


    @Override
    public void onBackPressed() {
        unRevealActivity();
    }

    @Override
    public void onPause() {
//        if (adView != null) {
//            adView.pause();
//        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
//        if (adView != null) {
//            adView.destroy();
//        }
        super.onDestroy();
    }


}
