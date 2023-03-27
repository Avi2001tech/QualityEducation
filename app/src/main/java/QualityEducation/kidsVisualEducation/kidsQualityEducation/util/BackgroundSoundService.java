package QualityEducation.kidsVisualEducation.kidsQualityEducation.util;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import QualityEducation.kidsVisualEducation.kidsQualityEducation.R;


public   class BackgroundSoundService extends Service {
    MediaPlayer player;
    public IBinder onBind(Intent arg0) {

        return null;
    }
    @Override
    public void onCreate() {

        super.onCreate();
        player = MediaPlayer.create(this, R.raw.game);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);

    }
    public int onStartCommand(Intent intent, int flags, int startId) {

        boolean var = intent.getBooleanExtra("isPlay",false);//PrefClass.getSound();
        if(var) {

            player.start();
        }
        else {
            player.pause();
        }
        return START_NOT_STICKY;
    }

    public void onStart(Intent intent, int startId) {
        // TO DO
    }



    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory() {

    }
}
