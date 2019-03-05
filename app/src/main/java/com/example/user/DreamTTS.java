package com.example.user.opencv_app1;


import android.content.Context;
import android.speech.tts.TextToSpeech;
import java.util.Locale;

public class DreamTTS{

    private TextToSpeech tts;
    //private String content_text;

    public DreamTTS(Context mContext) {
        tts = new TextToSpeech(mContext.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });
    }

    protected void ttsSetup(){
        tts.setPitch(0.9f);
        tts.setSpeechRate(0.9f);
    }
        
    protected void ttsSpeech(String argtext){
        tts.speak(argtext, TextToSpeech.QUEUE_FLUSH, null, null);
    }
        
    private void ttsClose(){
        tts.stop();
        tts.shutdown();
    }

}
