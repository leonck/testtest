package com.example.androidvideoplayer;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

public class Aout {

	private AudioTrack mAudioTrack;
	private static final String TAG = "AudioPlayer/aout";

	public void initAout(int sampleRateInHz, int channels, int samples) {

		int minBufferSize = AudioTrack.getMinBufferSize(sampleRateInHz,
				channels == 1 ? AudioFormat.CHANNEL_OUT_MONO : AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT);
		mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRateInHz,
				channels == 1 ? AudioFormat.CHANNEL_OUT_MONO : AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT,
				Math.max(minBufferSize, channels * samples * 2),
				AudioTrack.MODE_STREAM);
	}

	public void closeAout() {
		if (mAudioTrack != null) {
	
			//mAudioTrack.stop();
	
			mAudioTrack.release();
		}
		mAudioTrack = null;
	}

	public void playAudio(byte[] audioData, int bufferSize) {
		if (mAudioTrack.getState() == AudioTrack.STATE_UNINITIALIZED)
			return;
		if (mAudioTrack.write(audioData, 0, bufferSize) != bufferSize) {
			Log.w(TAG, "Could not write all the samples to the audio device");
		}
		mAudioTrack.play();
	}
	
	public int height[] = new int[80];
	public int numBands = 80;
	public int playing_progress = 0;
	public void showSpectrumHeight(int[] height, int numBands, int playing_progress) {
		// this.height = height;//shouldn't use reference
		try {
			System.arraycopy(height, 0, this.height, 0, numBands);
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		this.numBands = numBands;
		this.playing_progress = playing_progress;
	}

	public void pauseAout() {
		mAudioTrack.pause();
	}
}
