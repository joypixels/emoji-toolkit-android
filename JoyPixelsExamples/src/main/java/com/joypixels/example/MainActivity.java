package com.joypixels.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.text.SpannableStringBuilder;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import com.joypixels.tools.Client;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Client client = new Client(this);

        client.setAscii(true);              // convert ascii smileys? =)
        client.setShortcodes(true);         // convert shortcodes? :joy:
        client.setGreedyMatch(true);        // true enables less strict unicode matching
        client.setRiskyMatchAscii(true);    // match ascii without leading/trailing space character

        EditText editText = findViewById(R.id.editText);
        editText.setText("Hello! \uD83D\uDE04 <3 :joy:");

        TextView textView = findViewById(R.id.textView);

        Button shortnameToImage = (Button) findViewById(R.id.btnShortnameToImage);
        shortnameToImage.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
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
            }
        });

        Button toShort = (Button) findViewById(R.id.btnToShortname);
        toShort.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                // Convert native unicode emoji to shortnames
                String result = client.toShortname(editText.getText().toString());
                textView.setText(result);
            }
        });

        Button shortnameToUnicode = (Button) findViewById(R.id.btnShortnameToUnicode);
        shortnameToUnicode.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                // Convert shortnames to native unicode
                String result = client.shortnameToUnicode(editText.getText().toString());
                textView.setText(result);
            }
        });

        Button toImage = (Button) findViewById(R.id.btnToImage);
        toImage.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
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
            }
        });

        Button unicodeToImage = (Button) findViewById(R.id.btnUnicodeToImage);
        unicodeToImage.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
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
        });
    }
}