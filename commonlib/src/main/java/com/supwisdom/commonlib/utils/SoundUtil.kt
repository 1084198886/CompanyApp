package com.supwisdom.commonlib.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool

/**
 * @author zzq
 * @date 2018/4/19.
 * @version 1.0.1
 * @desc  语音管理
 */
object SoundUtil {
    private var soundId: Int = 0
    private var soundPool: SoundPool? = null
    private val soundMap = hashMapOf<Int, Int>()

    fun playMusic(context: Context, mid: Int) {
        if (soundPool == null) {
            val mAudioAttributes = AudioAttributes.Builder().setLegacyStreamType(AudioManager.STREAM_MUSIC)
                    .build()
            soundPool = SoundPool.Builder().setMaxStreams(10).setAudioAttributes(mAudioAttributes).build()
        }
        try {
            if (soundMap.containsKey(mid)) {
                soundPool!!.play(soundMap[mid]!!, 1.0f, 1.0f, 1, 0, 1.0f)
            } else {
                soundId = soundPool!!.load(context, mid, 1)
                soundPool!!.setOnLoadCompleteListener { pool, _, _ ->
                    soundMap[mid] = soundId
                    //  加载完成
                    try {
                        pool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f)
                    } catch (e: Exception) {
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun releaseMusic() {
        if (soundPool != null) {
            soundPool!!.release()
            soundPool = null
        }
        soundMap.clear()
    }
}