package proj.fivesecrule;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {
    private SoundPool soundPool;
    private int mStreamId;
    private Context context;
    public SoundPlayer(Context cnt){
        context = cnt;
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
        soundPool.load(context, R.raw.timer, 1);
    }

    public void play_sound(){
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int priority = 1;
        int no_loop = 0;
        float normal_playback_rate = 1f;
        int mSoundId = 1;
        mStreamId = soundPool.play(mSoundId, curVolume, curVolume, priority, no_loop,
                normal_playback_rate);
    }

    public void stop_sound(){
        soundPool.stop(mStreamId);
    }
}
