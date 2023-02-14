import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.*

private var speechRate: Float = 1.0f
private var pitch: Float = 1.0f
class TTS(private val context: Context) {
    private lateinit var tts: TextToSpeech
    private var text: String = ""
    private var utteranceId: String = ""

    fun init(context: Context) {
        tts = TextToSpeech(context, TextToSpeech.OnInitListener {
            if (it == TextToSpeech.SUCCESS) {
                tts.language = Locale.US
            }
        })
    }

    fun start(text: String) {
        this.text = text
        utteranceId = UUID.randomUUID().toString()
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    fun stop() {
        tts.stop()
    }

    fun pause() {
        tts.stop()
    }
    fun resume() {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    fun destroy() {
        tts?.shutdown()
    }

    fun setSpeechRate(speed: Float) {
        tts?.setSpeechRate(speed)
        speechRate = speed
    }

    fun setPitch(pitchValue: Float) {
        tts?.setPitch(pitchValue)
        pitch = pitchValue
    }

    fun getSpeechRate(): Float {
        return speechRate
    }

    fun getPitch(): Float {
        return pitch
    }
    fun speak(testing:Boolean)
    {
        tts?.speak("This is a test of the Text To Speech engine of Android", TextToSpeech.QUEUE_FLUSH, null,"")
    }
}



