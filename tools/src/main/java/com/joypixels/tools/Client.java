package com.joypixels.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Client {

    private boolean ascii = true;                // convert ascii smileys? =)
    private boolean riskyMatchAscii = true;      // set true to match ascii without leading/trailing space char
    private boolean shortcodes = true;           // convert shortcodes? :joy:
    private boolean greedyMatch = false;         // when true, matches non-fully-qualified Unicode values

    private String imagePathPNG = "https://cdn.jsdelivr.net/joypixels/assets/";
    private String emojiVersion = "5.0";
    private String emojiDownloadSize = "128";

    private String unicodeRegexp = "(?:\\x{1f9d1}[\\x{1f3fb}-\\x{1f3ff}]?\\x{200d}\\x{1f91d}\\x{200d}\\x{1f9d1}[\\x{1f3fb}-\\x{1f3ff}]?)|(?:[\\x{1F3F3}|\\x{1F3F4}]\\x{FE0F}?\\x{200D}?[\\x{1F308}|\\x{2620}]\\x{FE0F}?)|(?:\\x{1F441}\\x{FE0F}?\\x{200D}?\\x{1F5E8}\\x{FE0F}?)|(?:[\\x{1f468}|\\x{1f469}]\\x{200d}\\x{2764}\\x{fe0f}?\\x{200d}[\\x{1f48b}\\x{200d}]*[\\x{1f468}|\\x{1f469}])|(?:[\\x{1f468}|\\x{1f469}]\\x{200d}[\\x{1f468}|\\x{1f469}]\\x{200d}[\\x{1f466}|\\x{1f467}]\\x{200d}[\\x{1f466}|\\x{1f467}])|(?:[\\x{1f468}|\\x{1f469}]\\x{200d}[\\x{1f466}|\\x{1f467}]\\x{200d}[\\x{1f466}|\\x{1f467}])|(?:[\\x{1f468}|\\x{1f469}]\\x{200d}[\\x{1f468}|\\x{1f469}]\\x{200d}[\\x{1f466}|\\x{1f467}])|(?:[\\x{1f468}|\\x{1f469}]\\x{200d}[\\x{1f466}|\\x{1f467}])|(?:[\\x{1F9B8}|\\x{1F9B9}]+[\\x{1F3FB}-\\x{1F3FF}]?\\x{200D}[\\x{2640}-\\x{2642}]?\\x{FE0F}?)|(?:[\\x{1F468}|\\x{1F469}]+[\\x{1F3FB}-\\x{1F3FF}]?\\x{200D}[\\x{1F9B0}-\\x{1F9B3}]+\\x{FE0F}?)|[\\x{0023}-\\x{0039}]\\x{FE0F}?\\x{20e3}|(?:\\x{1F3F4}[\\x{E0060}-\\x{E00FF}]{1,6})|[\\x{1F1E0}-\\x{1F1FF}]{2}|(?:[\\x{1F468}|\\x{1F469}]\\x{FE0F}?[\\x{1F3FB}-\\x{1F3FF}]?\\x{200D}?[\\x{2695}|\\x{2696}|\\x{2708}|\\x{1F4BB}|\\x{1F4BC}|\\x{1F527}|\\x{1F52C}|\\x{1F680}|\\x{1F692}|\\x{1F33E}|\\x{1F3EB}|\\x{1F3EC}|\\x{1f373}|\\x{1f393}|\\x{1f3a4}|\\x{1f3ed}|\\x{1f3a8}]\\x{FE0F}?)|[\\x{1F468}-\\x{1F469}\\x{1F9D0}-\\x{1F9DF}][\\x{1F3FA}-\\x{1F3FF}]?\\x{200D}?[\\x{2640}\\x{2642}\\x{2695}\\x{2696}\\x{2708}]?\\x{FE0F}?|(?:[\\x{1F9B5}|\\x{1F9B6}]+[\\x{1F3FB}-\\x{1F3FF}]+)|(?:[\\x{1f46e}\\x{1F468}\\x{1F469}\\x{1f575}\\x{1f471}-\\x{1f487}\\x{1F645}-\\x{1F64E}\\x{1F926}\\x{1F937}]|[\\x{1F460}-\\x{1F482}\\x{1F3C3}-\\x{1F3CC}\\x{26F9}\\x{1F486}\\x{1F487}\\x{1F6A3}-\\x{1F6B6}\\x{1F938}-\\x{1F93E}]|\\x{1F46F})\\x{FE0F}?[\\x{1F3FA}-\\x{1F3FF}]?\\x{200D}?[\\x{2640}\\x{2642}]?\\x{FE0F}?|(?:[\\x{26F9}\\x{261D}\\x{270A}-\\x{270D}\\x{1F385}-\\x{1F3CC}\\x{1F442}-\\x{1F4AA}\\x{1F574}-\\x{1F596}\\x{1F645}-\\x{1F64F}\\x{1F6A3}-\\x{1F6CC}\\x{1F918}-\\x{1F93E}]\\x{FE0F}?[\\x{1F3FA}-\\x{1F3FF}])|(?:[\\x{2194}-\\x{2199}\\x{21a9}-\\x{21aa}]\\x{FE0F}?|[\\x{0023}-\\x{002a}]|[\\x{3030}\\x{303d}]\\x{FE0F}?|(?:[\\x{1F170}-\\x{1F171}]|[\\x{1F17E}-\\x{1F17F}]|\\x{1F18E}|[\\x{1F191}-\\x{1F19A}]|[\\x{1F1E6}-\\x{1F1FF}])\\x{FE0F}?|\\x{24c2}\\x{FE0F}?|[\\x{3297}\\x{3299}]\\x{FE0F}?|(?:[\\x{1F201}-\\x{1F202}]|\\x{1F21A}|\\x{1F22F}|[\\x{1F232}-\\x{1F23A}]|[\\x{1F250}-\\x{1F251}])\\x{FE0F}?|[\\x{203c}\\x{2049}]\\x{FE0F}?|[\\x{25aa}-\\x{25ab}\\x{25b6}\\x{25c0}\\x{25fb}-\\x{25fe}]\\x{FE0F}?|[\\x{00a9}\\x{00ae}]\\x{FE0F}?|[\\x{2122}\\x{2139}]\\x{FE0F}?|\\x{1F004}\\x{FE0F}?|[\\x{2b05}-\\x{2b07}\\x{2b1b}-\\x{2b1c}\\x{2b50}\\x{2b55}]\\x{FE0F}?|[\\x{231a}-\\x{231b}\\x{2328}\\x{23cf}\\x{23e9}-\\x{23f3}\\x{23f8}-\\x{23fa}]\\x{FE0F}?|\\x{1F0CF}|[\\x{2934}\\x{2935}]\\x{FE0F}?)|[\\x{2700}-\\x{27bf}]\\x{FE0F}?|[\\x{1F000}-\\x{1F6FF}\\x{1F900}-\\x{1F9FF}]\\x{FE0F}?|[\\x{2600}-\\x{26ff}]\\x{FE0F}?|(?:[\\x{1F466}-\\x{1F469}]+\\x{FE0F}?[\\x{1F3FB}-\\x{1F3FF}]?)|[\\x{0030}-\\x{0039}]\\x{FE0F}";

    private String shortnameRegexp = ":([-+\\w]+):";

    private Ruleset ruleset = new Ruleset();
    private Context context = null;

    public Client(Context context) {
        this.context = context;
    }

    /**
     * This will return the shortname from unicode input.
     */
    public String toShortname(String string) {
        Pattern pattern = Pattern.compile(this.unicodeRegexp);
        Matcher matcher = pattern.matcher(string);
        return replaceUnicodeWithShortname(string, matcher);
    }

    /**
     * This will output unicode from shortname input.
     */
    public String shortnameToUnicode(String string) {
        if(this.shortcodes) {
            Pattern pattern = Pattern.compile(this.shortnameRegexp);
            Matcher matches = pattern.matcher(string);
            string = replaceShortnameWithUnicode(string, matches);
        }
        if(this.ascii) {
            String asciiRegexp = this.ruleset.getAsciiRegexp();
            String asciiRX = (this.riskyMatchAscii) ? "(()" + asciiRegexp + "())" : "((\\s|^)"+asciiRegexp+"(?=\\s|$|[!,.?]))";

            Pattern pattern = Pattern.compile(asciiRX);
            Matcher matches = pattern.matcher(string);
            string = replaceAsciiWithUnicode(string, matches);
        }
        return string;
    }

    /**
     * Changes unicode to a shortname then converts those shortnames
     * to images and places them in a spannablestringbuilder. The size of the images
     * are set using imageSize. A callback is made once the function completes on
     * the main thread so that the spannablestringbuilder may be applied to a ui element.
     */
    public void toImage(String string, int imageSize, com.joypixels.tools.Callback callback) {
        if(this.ascii) {
            String asciiRegexp = this.ruleset.getAsciiRegexp();
            String asciiRX = (this.riskyMatchAscii) ? "(()" + asciiRegexp + "())" : "((\\s|^)"+asciiRegexp+"(?=\\s|$|[!,.?]))";

            Pattern pattern = Pattern.compile(asciiRX);
            Matcher matcher = pattern.matcher(string);
            string = replaceAsciiWithUnicode(string, matcher);
        }
        Pattern pattern = Pattern.compile(this.unicodeRegexp);
        Matcher matcher = pattern.matcher(string);
        string = replaceUnicodeWithShortname(string, matcher);

        SpannableStringBuilder ssb = new SpannableStringBuilder(string);
        pattern = Pattern.compile(this.shortnameRegexp);
        matcher = pattern.matcher(string);
        ArrayList<String> matchList = new ArrayList<>();
        while (matcher.find()) {
            matchList.add(matcher.group(0));
        }
        if(matchList.size()==0) {
            callback.onSuccess(ssb);
        }
        replaceShortnameWithImages(ssb, imageSize, matchList, callback);
    }

    /**
     * Changes shortname to images and places them into a spannablestringbuilder.
     * The size of the images are set using imageSize and callback is made once
     * the function completes.
     */
    public void shortnameToImage(String string, int imageSize, com.joypixels.tools.Callback callback) {
        if (this.ascii) {
            String asciiRegexp = this.ruleset.getAsciiRegexp();
            String asciiRX = (this.riskyMatchAscii) ? "(()" + asciiRegexp + "())" : "((\\s|^)"+asciiRegexp+"(?=\\s|$|[!,.?]))";

            Pattern pattern = Pattern.compile(asciiRX);
            Matcher matcher = pattern.matcher(string);
            string = replaceAsciiWithShortname(string, matcher);
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder(string);
        Pattern pattern = Pattern.compile(this.shortnameRegexp);
        Matcher matcher = pattern.matcher(string);
        ArrayList<String> matchList = new ArrayList<>();
        while (matcher.find()) {
            matchList.add(matcher.group(0));
        }
        if(matchList.size()==0) {
            callback.onSuccess(ssb);
        }
        replaceShortnameWithImages(ssb, imageSize, matchList, callback);
    }

    /**
     * Changes everything to a shortname then converts those shortnames
     * to images into a spannablestringbuilder. The size of the images
     * are set using imageSize and callback is made once the function completes.
     */
    public void unicodeToImage(String string, int imageSize, com.joypixels.tools.Callback callback) {
        if(this.ascii) {
            String asciiRegexp = this.ruleset.getAsciiRegexp();
            String asciiRX = (this.riskyMatchAscii) ? "(()" + asciiRegexp + "())" : "((\\s|^)"+asciiRegexp+"(?=\\s|$|[!,.?]))";

            Pattern pattern = Pattern.compile(asciiRX);
            Matcher matcher = pattern.matcher(string);
            string = replaceAsciiWithUnicode(string, matcher);
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder(string);
        Pattern pattern = Pattern.compile(this.unicodeRegexp);
        Matcher matcher = pattern.matcher(string);
        ArrayList<String> matchList = new ArrayList<>();
        while (matcher.find()) {
            matchList.add(matcher.group(0));
        }
        if(matchList.size()==0) {
            callback.onSuccess(ssb);
        }
        replaceUnicodeWithImages(ssb, imageSize, matchList, callback);
    }

    /**
     * A recursive function that generates a spannablestringbuilder using the
     * shortnames found in the matchList. Once there are no matches the
     * callback is executed on the main thread so that the spannablestringbuilder
     * may be applied to a ui element.
     */
    private void replaceShortnameWithImages(final SpannableStringBuilder ssb, final int imageSize, final ArrayList<String> matchList, final com.joypixels.tools.Callback callback) {
        if(matchList.size()==0) {
            new Handler(context.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    callback.onSuccess(ssb);
                }
            });
        } else {
            LinkedHashMap<String, ArrayList<String>> shortcode_replace = this.ruleset.getShortcodeReplace();
            final String shortname = matchList.get(0);
            matchList.remove(0);
            if (shortcode_replace.containsKey(shortname)) {
                String filename = shortcode_replace.get(shortname).get(1);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(this.imagePathPNG+this.emojiVersion+"/png/unicode/"+this.emojiDownloadSize+"/"+filename+".png")
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        replaceShortnameWithImages(ssb, imageSize, matchList, callback);
                    }
                    @Override
                    public void onResponse(Call call, final Response response) {
                        if (!response.isSuccessful()) {
                            replaceShortnameWithImages(ssb, imageSize, matchList, callback);
                        } else {
                            int startIndex = ssb.toString().indexOf(shortname);
                            InputStream inputStream = response.body().byteStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            BitmapDrawable bmpDrawable = new BitmapDrawable(context.getResources(), bitmap);
                            bmpDrawable.setBounds(0, 0, imageSize, imageSize);
                            ssb.replace(startIndex,startIndex+shortname.length(), " ");
                            ssb.setSpan(new ImageSpan(bmpDrawable), startIndex, startIndex+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            replaceShortnameWithImages(ssb, imageSize, matchList, callback);
                        }
                    }
                });
            }
        }
    }

    /**
     * A recursive function that generates a spannablestringbuilder using the
     * unicodes found in the matchList. Once there are no matches the
     * callback is executed on the main thread so that the spannablestringbuilder
     * may be applied to a ui element.
     */
    private void replaceUnicodeWithImages(final SpannableStringBuilder ssb, final int imageSize, final ArrayList<String> matchList, final com.joypixels.tools.Callback callback) {
        if(matchList.size()==0) {
            new Handler(context.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    callback.onSuccess(ssb);
                }
            });
        } else {
            LinkedHashMap<String, String> unicode_replace = this.ruleset.getUnicodeReplace();
            LinkedHashMap<String, String> unicode_replace_greedy = this.ruleset.getUnicodeReplaceGreedy();
            LinkedHashMap<String, ArrayList<String>> shortcode_replace = this.ruleset.getShortcodeReplace();
            final String unicode = matchList.get(0);
            matchList.remove(0);
            String shortname = "";
            StringBuilder hexValue = new StringBuilder();
            String hexString = "";
            try {
                byte[] xxx = unicode.getBytes("UTF-8");
                for (byte x : xxx) {
                    hexValue.append(String.format("%02X", x));
                }
            } catch (Exception e) {
                Log.e("UnicodeWithImages", e.getMessage());
            }
            if(unicode_replace.containsKey(hexValue.toString())) {
                shortname = unicode_replace.get(hexValue.toString());
            } else if(this.greedyMatch && unicode_replace_greedy.containsKey(hexValue.toString())) {
                shortname = unicode_replace_greedy.get(hexValue.toString());
            } else {
                replaceUnicodeWithImages(ssb, imageSize, matchList, callback);
                return;
            }
            String filename = shortcode_replace.get(shortname).get(1);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(this.imagePathPNG + this.emojiVersion + "/png/unicode/" + this.emojiDownloadSize + "/" + filename + ".png")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    replaceUnicodeWithImages(ssb, imageSize, matchList, callback);
                }
                @Override
                public void onResponse(Call call, final Response response) {
                    if (!response.isSuccessful()) {
                        replaceUnicodeWithImages(ssb, imageSize, matchList, callback);
                    } else {
                        int startIndex = ssb.toString().indexOf(unicode);
                        InputStream inputStream = response.body().byteStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        BitmapDrawable bmpDrawable = new BitmapDrawable(context.getResources(), bitmap);
                        bmpDrawable.setBounds(0, 0, imageSize, imageSize);
                        ssb.replace(startIndex, startIndex + unicode.length(), " ");
                        ssb.setSpan(new ImageSpan(bmpDrawable), startIndex, startIndex + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        replaceUnicodeWithImages(ssb, imageSize, matchList, callback);
                    }
                }
            });
        }
    }

    /**
     * Replaces all shortname instances with its unicode equivalent.
     * Matcher contains a list of matching items.
     */
    private String replaceShortnameWithUnicode(String string, Matcher matcher) {
        ArrayList<String> matchList = new ArrayList<>();
        while (matcher.find()) {
            matchList.add(matcher.group(0));
        }
        if(matchList.size()==0) {
            return string;
        } else {
            LinkedHashMap<String, ArrayList<String>> shortcode_replace = this.ruleset.getShortcodeReplace();
            for(String shortname : matchList) {
                try {
                    if (shortcode_replace.containsKey(shortname)) {
                        String emojiHex = shortcode_replace.get(shortname).get(1);
                        String emoji = hexStringToCodePoint(emojiHex);
                        string = string.replace(shortname, emoji);
                    }
                } catch (Exception e) {
                    Log.e("ShortnameWithUnicode",e.getMessage());
                }
            }
            return string;
        }
    }

    /**
     * Replaces all ascii instances with its unicode equivalent.
     */
    private String replaceAsciiWithUnicode(String string, Matcher matcher) {
        ArrayList<String> matchList = new ArrayList<>();
        while (matcher.find()) {
            matchList.add(matcher.group(0));
        }
        if(matchList.size()==0) {
            return string;
        } else {
            LinkedHashMap<String, String> ascii_replace = this.ruleset.getAsciiReplace();
            LinkedHashMap<String, ArrayList<String>> shortcode_replace = this.ruleset.getShortcodeReplace();
            for(String ascii : matchList) {
                try {
                    if (ascii_replace.containsKey(ascii)) {
                        string = string.replace(ascii, ascii_replace.get(ascii));
                        if(shortcode_replace.containsKey(ascii_replace.get(ascii))) {
                            string = string.replace(ascii_replace.get(ascii), shortcode_replace.get(ascii_replace.get(ascii)).get(0));
                        }
                    }
                } catch (Exception e) {
                    Log.e("replaceAsciiWithUnicode",e.getMessage());
                }
            }
            return string;
        }
    }

    /**
     * Replaces all ascii instances with its shortname equivalent.
     */
    private String replaceAsciiWithShortname(String string, Matcher matcher) {
        ArrayList<String> matchList = new ArrayList<>();
        while (matcher.find()) {
            matchList.add(matcher.group(0));
        }
        if(matchList.size()==0) {
            return string;
        } else {
            LinkedHashMap<String, String> ascii_replace = this.ruleset.getAsciiReplace();
            LinkedHashMap<String, ArrayList<String>> shortcode_replace = this.ruleset.getShortcodeReplace();
            for(String ascii : matchList) {
                try {
                    if (ascii_replace.containsKey(ascii)) {
                        string = string.replace(ascii, ascii_replace.get(ascii));
                    }
                } catch (Exception e) {
                    Log.e("replaceAsciiWithUnicode",e.getMessage());
                }
            }
            return string;
        }
    }

    /**
     * Replaces all unicode instances with its shortname equivalent.
     */
    private String replaceUnicodeWithShortname(String string, Matcher matcher) {
        ArrayList<String> matchList = new ArrayList<>();
        while (matcher.find()) {
            matchList.add(matcher.group(0));
        }
        if(matchList.size()==0) {
            return string;
        } else {
            LinkedHashMap<String, String> unicode_replace = this.ruleset.getUnicodeReplace();
            for(String unicode : matchList) {
                try {
                    StringBuilder hexValue = new StringBuilder();
                    byte[] xxx = unicode.getBytes("UTF-8");
                    String hexString = "";
                    for (byte x : xxx) {
                        hexValue.append(String.format("%02X", x));
                    }
                    if (unicode_replace.containsKey(hexValue.toString())) {
                        string = string.replace(unicode, unicode_replace.get(hexValue.toString()));
                    }
                } catch (Exception e) {
                    Log.e("MatchesWithShortnam",e.getMessage());
                }
            }
            return string;
        }
    }

    private String hexStringToCodePoint(String hexString) {
        int codePoint = Integer.parseInt(hexString, 16 );
        return new String(new int[]{codePoint}, 0, 1);
    }

    public boolean isAscii() {
        return ascii;
    }
    public void setAscii(boolean ascii) {
        this.ascii = ascii;
    }
    public boolean isRiskyMatchAscii() {
        return riskyMatchAscii;
    }
    public void setRiskyMatchAscii(boolean riskyMatchAscii) {
        this.riskyMatchAscii = riskyMatchAscii;
    }
    public boolean isShortcodes() {
        return shortcodes;
    }
    public void setShortcodes(boolean shortcodes) {
        this.shortcodes = shortcodes;
    }
    public String getEmojiVersion() {
        return emojiVersion;
    }
    public void setEmojiVersion(String emojiVersion) {
        this.emojiVersion = emojiVersion;
    }
    public String getemojiDownloadSize() {
        return emojiDownloadSize;
    }
    public void setemojiDownloadSize(String emojiDownloadSize) {
        this.emojiDownloadSize = emojiDownloadSize;
    }
    public boolean isGreedyMatch() {
        return greedyMatch;
    }
    public void setGreedyMatch(boolean greedyMatch) {
        this.greedyMatch = greedyMatch;
    }
    public String getImagePathPNG() {
        return imagePathPNG;
    }
    public void setImagePathPNG(String imagePathPNG) {
        this.imagePathPNG = imagePathPNG;
    }
}
