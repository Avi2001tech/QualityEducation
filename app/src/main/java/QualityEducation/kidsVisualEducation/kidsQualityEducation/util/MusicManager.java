package QualityEducation.kidsVisualEducation.kidsQualityEducation.util;

import android.content.Context;
import android.media.MediaPlayer;

import QualityEducation.kidsVisualEducation.kidsQualityEducation.R;


public class MusicManager {

    public static MediaPlayer background;
    public static MediaPlayer buttonClick;
    public static Boolean Mute = false;
    static Context context;

    public MusicManager(Context mcontext) {

        context = mcontext;

        background = new MediaPlayer();
        background = MediaPlayer.create(mcontext, R.raw.game);
        background.setLooping(true);
    }

    public static void startBGM() {

        if ((!Mute) && (background != null) && PrefUtils.isMusicOn(context)) {

            background.start();
        }
    }

    public static void stopBGM() {

        if (background != null) {
            background.stop();
        }
    }

    public static void release() {
        background.release();
    }

    public static void bClick()
    {
        if(PrefUtils.isSoundOn(context)){
            PrefUtils.markSoundOn(context, true);
            buttonClick= MediaPlayer.create(context, R.raw.popin);
            buttonClick.start();
            buttonClick.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.stop();
                    mp.release();
                }
            });
        }
    }

    public static void gameOver()
    {
        if(PrefUtils.isSoundOn(context)){
            PrefUtils.markSoundOn(context, true);
            buttonClick= MediaPlayer.create(context, R.raw.gameover);
            buttonClick.start();

        }
    }

}
