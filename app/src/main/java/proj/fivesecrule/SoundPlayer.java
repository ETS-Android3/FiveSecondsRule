package proj.fivesecrule;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundPlayer {
    private MediaPlayer mediaPlayer;

    public SoundPlayer(Context cnt){
        mediaPlayer = MediaPlayer.create(cnt, R.raw.timer);
    }

    public void play_sound(){
        mediaPlayer.start();
    }

    public void stop_sound(){
        mediaPlayer.stop();
        mediaPlayer.reset();
    }
}
