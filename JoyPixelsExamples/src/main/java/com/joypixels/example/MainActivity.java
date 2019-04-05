package com.joypixels.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.joypixels.tools.Client;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.editText) EditText editText;
    @BindView(R.id.textView) TextView textView;

    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        client = new Client(this);

        client.setAscii(true);              // convert ascii smileys? =)
        client.setShortcodes(true);         // convert shortcodes? :joy:
        client.setGreedyMatch(true);        // true enables less strict unicode matching
        client.setRiskyMatchAscii(true);    // match ascii without leading/trailing space character

        editText.setText("Hello! \uD83D\uDE04 <3 :joy:");
    }

    @OnClick({R.id.btnToImage, R.id.btnShortnameToImage,R.id.btnShortnameToUnicode,R.id.btnToShortname,R.id.btnUnicodeToImage}) public void submit(Button view) {
        String result;
        switch (view.getId()) {
            case R.id.btnToShortname:
                // Convert native unicode emoji to shortnames
                result = client.toShortname(editText.getText().toString());
                textView.setText(result);
                break;
            case R.id.btnShortnameToUnicode:
                // Convert shortnames to native unicode
                result = client.shortnameToUnicode(editText.getText().toString());
                textView.setText(result);
                break;
            case R.id.btnToImage:
                // Convert native unicode emoji and shortnames to images on a spannable string
                client.toImage(editText.getText().toString(),100, new com.joypixels.tools.Callback() {
                    @Override
                    public void onFailure(IOException e) {
                        textView.setText(e.getMessage());
                    }
                    @Override
                    public void onSuccess(final SpannableStringBuilder ssb) {
                        textView.setText(ssb);
                    }
                });
                break;
            case R.id.btnShortnameToImage:
                // Convert shortnames to images on a spannable string
                client.shortnameToImage(editText.getText().toString(),100, new com.joypixels.tools.Callback() {
                    @Override
                    public void onFailure(IOException e) {
                        textView.setText(e.getMessage());
                    }
                    @Override
                    public void onSuccess(final SpannableStringBuilder ssb) {
                        textView.setText(ssb);
                    }
                });
                break;
            case R.id.btnUnicodeToImage:
                // Convert native unicode emoji to images on a spannable string
                client.unicodeToImage(editText.getText().toString(),100, new com.joypixels.tools.Callback() {
                    @Override
                    public void onFailure(IOException e) {
                        textView.setText(e.getMessage());
                    }
                    @Override
                    public void onSuccess(final SpannableStringBuilder ssb) {
                        textView.setText(ssb);
                    }
                });
        }
    }
}
