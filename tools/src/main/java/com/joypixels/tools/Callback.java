package com.joypixels.tools;

import android.text.SpannableStringBuilder;

import java.io.IOException;

public interface Callback {

    void onFailure(IOException e);

    void onSuccess(SpannableStringBuilder ssb);
}
