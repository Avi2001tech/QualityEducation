package QualityEducation.kidsVisualEducation.kidsQualityEducation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import QualityEducation.kidsVisualEducation.kidsQualityEducation.activity.SettingsActivity;
import QualityEducation.kidsVisualEducation.kidsQualityEducation.util.BackgroundSoundService;
import QualityEducation.kidsVisualEducation.kidsQualityEducation.util.MusicManager;
import QualityEducation.kidsVisualEducation.kidsQualityEducation.util.PrefClass;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView numId = null;
    ImageView fruitId = null;
    ImageView shapeId = null;
    ImageView alphabetId = null;
    ImageButton monthBtn = null;
    ImageButton weekBtn = null;
    ImageView ivKids;
    ImageView comunityhelp;
    ImageView veg;
    ImageView vihical;
    ImageView shape;
    ImageView color;
    ImageView day;
    ImageView month;
    ImageView body;
    MusicManager music_manager;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taw_activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                //dosomething
            }
        });

        adView = findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        music_manager = new MusicManager(getApplicationContext());

        if (new PrefClass(getApplicationContext()).getSound() == true) {
            Intent svc = new Intent(this, BackgroundSoundService.class);
            svc.putExtra("isPlay", true);
            startService(svc);

        } else {
            Intent svc = new Intent(this, BackgroundSoundService.class);
            svc.putExtra("isPlay", false);
            startService(svc);
        }

        ivKids = findViewById(R.id.iv_kids);
        Glide.with(getApplicationContext())
                .load(R.drawable.kids_gif)
                .into(ivKids);
        numId = findViewById(R.id.numId);
        Glide.with(getApplicationContext())
                .load(R.drawable.num)
                .into(numId);

        fruitId = findViewById(R.id.fruitId);
        Glide.with(getApplicationContext())
                .load(R.drawable.fruit_gif)
                .into(fruitId);
        shapeId = findViewById(R.id.shape_id);
        Glide.with(getApplicationContext())
                .load(R.drawable.animal_gif)
                .into(shapeId);
        alphabetId = findViewById(R.id.alphabetId);
        Glide.with(getApplicationContext())
                .load(R.drawable.alpha_gif)
                .into(alphabetId);
        comunityhelp = findViewById(R.id.comunityhelp);
        veg = findViewById(R.id.veg);
        vihical = findViewById(R.id.vihical);
        shape = findViewById(R.id.shape);
        color = findViewById(R.id.color);
        day = findViewById(R.id.day);
        month = findViewById(R.id.month);
        body = findViewById(R.id.body);
        monthBtn = (ImageButton)findViewById(R.id.monthId);
        weekBtn = (ImageButton)findViewById(R.id.weekId);

        final  ImageButton imageButton = findViewById(R.id.imageButton2);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                final Animation myRotation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
                myRotation.setRepeatCount(0);
                imageButton.startAnimation(myRotation);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presentActivity(v);

                    }
                }, 900);


            }
        });


        numId.setOnClickListener(this);
        fruitId.setOnClickListener(this);
        shapeId.setOnClickListener(this);
        alphabetId.setOnClickListener(this);
        monthBtn.setOnClickListener(this);
        weekBtn.setOnClickListener(this);
        comunityhelp.setOnClickListener(this);
        veg.setOnClickListener(this);
        vihical.setOnClickListener(this);
        shape.setOnClickListener(this);
        color.setOnClickListener(this);
        day.setOnClickListener(this);
        month.setOnClickListener(this);
        body.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fruitId:
                Intent fruitIntent = new Intent(MainActivity.this, FruitsActivity.class);
                fruitIntent.putExtra("type", ResourcePool.fruit);
                startActivity(fruitIntent);
                break;
            case R.id.shape_id:
                Intent animalIntent = new Intent(MainActivity.this, FruitsActivity.class);
                animalIntent.putExtra("type", ResourcePool.animal);
                startActivity(animalIntent);
                break;
            case R.id.comunityhelp:
                Intent comunity = new Intent(MainActivity.this, FruitsActivity.class);
                comunity.putExtra("type", ResourcePool.comunity);
                startActivity(comunity);
                break;
            case R.id.veg:
                Intent iveg = new Intent(MainActivity.this, FruitsActivity.class);
                iveg.putExtra("type", ResourcePool.veg);
                startActivity(iveg);
                break;
            case R.id.vihical:
                Intent ivihical = new Intent(MainActivity.this, FruitsActivity.class);
                ivihical.putExtra("type", ResourcePool.vehical);
                startActivity(ivihical);
                break;
            case R.id.alphabetId:
                Intent alphabetIntent = new Intent(MainActivity.this, AlphabetActivity.class);
                startActivity(alphabetIntent);
                break;
            case R.id.numId:
                Intent numberIntent = new Intent(MainActivity.this, NumberActivity.class);
                startActivity(numberIntent);
                break;
            case R.id.monthId:
                Intent monthIntent = new Intent(MainActivity.this, KnowledgeActivity.class);
                monthIntent.putExtra("type", ResourcePool.month);
                startActivity(monthIntent);
                break;
            case R.id.weekId:
                Intent weekIntent = new Intent(MainActivity.this, KnowledgeActivity.class);
                weekIntent.putExtra("type", ResourcePool.week);
                startActivity(weekIntent);
                break;
            case R.id.shape:
                Intent shape = new Intent(MainActivity.this, FruitsActivity.class);
                shape.putExtra("type", ResourcePool.shape);
                startActivity(shape);
                break;
            case R.id.color:
                Intent color = new Intent(MainActivity.this, FruitsActivity.class);
                color.putExtra("type", ResourcePool.color);
                startActivity(color);
                break;
            case R.id.day:
                Intent day = new Intent(MainActivity.this, FruitsActivity.class);
                day.putExtra("type", ResourcePool.day);
                startActivity(day);
                break;
            case R.id.month:
                Intent month = new Intent(MainActivity.this, FruitsActivity.class);
                month.putExtra("type", ResourcePool.months);
                startActivity(month);
                break;
            case R.id.body:
                Intent body = new Intent(MainActivity.this, FruitsActivity.class);
                body.putExtra("type", ResourcePool.bodypart);
                startActivity(body);
                break;


            default:
                break;
        }

    }

    public void presentActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.

                makeSceneTransitionAnimation(this, view, "transition");

        int revealX = (int) (view.getX() + view.getWidth() / 2);

        int revealY = (int) (view.getY() + view.getHeight() / 2);
        Intent intent =
                new Intent(this, SettingsActivity.class);
        intent.putExtra(SettingsActivity.
                EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(SettingsActivity.
                EXTRA_CIRCULAR_REVEAL_Y, revealY);
        ActivityCompat.
                startActivity(this, intent, options.toBundle());
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        Log.e("0011", "onPause: 12");
        Intent svc = new Intent(this, BackgroundSoundService.class);
        svc.putExtra("isPlay", false);
        startService(svc);
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (new PrefClass(getApplicationContext()).getSound()) {
            Log.e("0011", "onResume: 11");
            Intent svc = new Intent(this, BackgroundSoundService.class);
            svc.putExtra("isPlay", true);
            startService(svc);

        } else {
            Log.e("0011", "onResume: 12");
            Intent svc = new Intent(this, BackgroundSoundService.class);
            svc.putExtra("isPlay", false);
            startService(svc);
        }
        if (adView != null) {
            adView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        Log.e("0011", "onDestroy: 12");
        Intent svc = new Intent(this, BackgroundSoundService.class);
        svc.putExtra("isPlay", false);
        startService(svc);
        super.onDestroy();
    }
}