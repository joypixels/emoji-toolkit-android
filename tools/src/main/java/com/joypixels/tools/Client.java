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
    private String emojiVersion = "4.5";
    private String emojiDownloadSize = "128";

    private String unicodeRegexp = "(?:\\x{D83C}\\x{DFF3})\\x{FE0F}?\\x{200D}?(?:\\x{D83C}\\x{DF08})|(?:\\x{D83D}\\x{DC41})\\x{FE0F}?\\x{200D}?(?:\\x{D83D}\\x{DDE8})\\x{FE0F}?|[#-9]\\x{FE0F}?\\x{20E3}|(?:(?:\\x{D83C}\\x{DFF4})(?:\\x{DB40}[\\x{DC60}-\\x{DCFF}]){1,6})|(?:\\x{D83C}[\\x{DDE0}-\\x{DDFF}]){2}|(?:(?:\\x{D83D}[\\x{DC68}\\x{DC69}]))\\x{FE0F}?(?:\\x{D83C}[\\x{DFFA}-\\x{DFFF}])?\\x{200D}?(?:[\\x{2695}\\x{2696}\\x{2708}]|\\x{D83C}[\\x{DF3E}-\\x{DFED}]|\\x{D83D}[\\x{DCBB}\\x{DCBC}\\x{DD27}\\x{DD2C}\\x{DE80}\\x{DE92}])|(?:\\x{D83D}[\\x{DC68}\\x{DC69}]|\\x{D83E}[\\x{DDD0}-\\x{DDDF}])(?:\\x{D83C}[\\x{DFFA}-\\x{DFFF}])?\\x{200D}?[\\x{2640}\\x{2642}\\x{2695}\\x{2696}\\x{2708}]?\\x{FE0F}?|(?:(?:\\x{2764}|\\x{D83D}[\\x{DC66}-\\x{DC69}\\x{DC8B}])[\\x{200D}\\x{FE0F}]{0,2}){1,3}(?:\\x{2764}|\\x{D83D}[\\x{DC66}-\\x{DC69}\\x{DC8B}])|(?:(?:\\x{2764}|\\x{D83D}[\\x{DC66}-\\x{DC69}\\x{DC8B}])\\x{FE0F}?){2,4}|(?:\\x{D83D}[\\x{DC68}\\x{DC69}\\x{DC6E}\\x{DC71}-\\x{DC87}\\x{DD75}\\x{DE45}-\\x{DE4E}]|\\x{D83E}[\\x{DD26}\\x{DD37}]|\\x{D83C}[\\x{DFC3}-\\x{DFCC}]|\\x{D83E}[\\x{DD38}-\\x{DD3E}]|\\x{D83D}[\\x{DEA3}-\\x{DEB6}]|\\x{26f9}|\\x{D83D}\\x{DC6F})\\x{FE0F}?(?:\\x{D83C}[\\x{DFFB}-\\x{DFFF}])?\\x{200D}?[\\x{2640}\\x{2642}]?\\x{FE0F}?|(?:[\\x{261D}\\x{26F9}\\x{270A}-\\x{270D}]|\\x{D83C}[\\x{DF85}-\\x{DFCC}]|\\x{D83D}[\\x{DC42}-\\x{DCAA}\\x{DD74}-\\x{DD96}\\x{DE45}-\\x{DE4F}\\x{DEA3}-\\x{DECC}]|\\x{D83E}[\\x{DD18}-\\x{DD3E}])\\x{FE0F}?(?:\\x{D83C}[\\x{DFFB}-\\x{DFFF}])|(?:[\\x{2194}-\\x{2199}\\x{21a9}-\\x{21aa}]\\x{FE0F}?|[\\x{0023}\\x{002a}]|[\\x{3030}\\x{303d}]\\x{FE0F}?|(?:\\x{d83c}[\\x{dd70}-\\x{dd71}]|\\x{d83c}\\x{dd8e}|\\x{d83c}[\\x{dd91}-\\x{dd9a}])\\x{FE0F}?|\\x{24c2}\\x{FE0F}?|[\\x{3297}\\x{3299}]\\x{FE0F}?|(?:\\x{d83c}[\\x{de01}-\\x{de02}]|\\x{d83c}\\x{de1a}|\\x{d83c}\\x{de2f}|\\x{d83c}[\\x{de32}-\\x{de3a}]|\\x{d83c}[\\x{de50}-\\x{de51}])\\x{FE0F}?|[\\x{203c}\\x{2049}]\\x{FE0F}?|[\\x{25aa}-\\x{25ab}\\x{25b6}\\x{25c0}\\x{25fb}-\\x{25fe}]\\x{FE0F}?|[\\x{00a9}\\x{00ae}]\\x{FE0F}?|[\\x{2122}\\x{2139}]\\x{FE0F}?|\\x{d83c}\\x{dc04}\\x{FE0F}?|[\\x{2b05}-\\x{2b07}\\x{2b1b}-\\x{2b1c}\\x{2b50}\\x{2b55}]\\x{FE0F}?|[\\x{231a}-\\x{231b}\\x{2328}\\x{23cf}\\x{23e9}-\\x{23f3}\\x{23f8}-\\x{23fa}]\\x{FE0F}?|\\x{d83c}\\x{dccf}|[\\x{2934}\\x{2935}]\\x{FE0F}?)|[\\x{2700}-\\x{27bf}]\\x{FE0F}?|[\\x{d800}-\\x{dbff}][\\x{dc00}-\\x{dfff}]\\x{FE0F}?|[\\x{2600}-\\x{26FF}]\\x{FE0F}?|[\\x{0030}-\\x{0039}]\\x{FE0F}";

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
                        .url(this.imagePathPNG+this.emojiVersion+"/png/"+this.emojiDownloadSize+"/"+filename+".png")
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
                    .url(this.imagePathPNG + this.emojiVersion + "/png/" + this.emojiDownloadSize + "/" + filename + ".png")
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
