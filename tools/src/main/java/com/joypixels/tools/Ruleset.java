package com.joypixels.tools;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class Ruleset {

    private Context context = null;

    LinkedHashMap<String, ArrayList<String>> shortcode_replace = new LinkedHashMap<String, ArrayList<String>>(3400,1f);
    LinkedHashMap<String, String> ascii_replace = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> unicode_replace = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> unicode_replace_greedy = new LinkedHashMap<String, String>();
    String asciiRegexp = "\\*\\\\0\\/\\*|\\\\0\\/|\\*\\\\O\\/\\*|\\\\O\\/|O:-\\)|0:-3|0:3|0:-\\)|0:\\)|0;\\^\\)|O:\\)|O;-\\)|O=\\)|0;-\\)|O:-3|O:3|<\\/3|B-\\)|B\\)|8\\)|8-\\)|B-D|8-D|-_-|-__-|-___-|>:\\\\|>:\\/|:-\\/|:-\\.|:\\/|:\\\\|=\\/|=\\\\|:L|=L|:-O|:O|O_O|>:O|:-X|:X|:-#|:#|=X|=#|:(\"|“)\\)|:(\"|“)-\\)|(\"|“):\\)|(\"|“):-\\)|(\"|“)=\\)|(\"|“):D|(\"|“):-D|(\"|“)=D|>:\\)|>;\\)|>:-\\)|>=\\)|(\"|“):\\(|(\"|“):-\\(|(\"|“)=\\(|>:P|X-P|>:\\[|:-\\(|:\\(|:-\\[|:\\[|=\\(|>:\\(|>:-\\(|:@|:(\"|“)\\(|:(\"|“)-\\(|;\\(|;-\\(|>\\.<|#-\\)|#\\)|%-\\)|%\\)|X\\)|X-\\)|:\\)|:-\\)|=]|=\\)|:]|:P|:-P|=P|:-Þ|:Þ|:-b|:b|<3|:D|:-D|=D|;\\)|;-\\)|\\*-\\)|\\*\\)|;-]|;]|;D|;\\^\\)|:\\*|:-\\*|=\\*|:\\^\\*|D:|:\\$|=\\$|\\(y\\)";

    public Ruleset(Context context) {
        this.context = context;

        populate_shortcode_replace();
        populate_ascii_replace();
        populate_unicode_replace();
        populate_unicode_replace_greedy();
    }

    private void populate_shortcode_replace() {
        BufferedReader br = null;

        try {
            InputStream ins = context.getResources().openRawResource(context.getResources().getIdentifier("shortcode_replace", "raw",context.getPackageName()));

            br = new BufferedReader(new InputStreamReader(ins));

            String line = null;

            // read file line by line
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                String name = parts[0].trim();
                String code = parts[1].trim();
                String code2 = parts[2].trim();

                if (!name.equals("")) {
                    shortcode_replace.put(name, new ArrayList<String>(Arrays.asList(code, code2)));
                }
            }
        }
        catch (Exception e) {
            Log.e("PopulateSCodeReplace",e.getMessage());
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                }
                catch (Exception e) {
                    Log.e("PopulateSCodeReplace",e.getMessage());
                };
            }
        }
    }

    private void populate_ascii_replace() {
        ascii_replace.put("*\\0/*", ":person_gesturing_ok:");
        ascii_replace.put("\\0/", ":person_gesturing_ok:");
        ascii_replace.put("*\\O/*", ":person_gesturing_ok:");
        ascii_replace.put("\\O/", ":person_gesturing_ok:");
        ascii_replace.put("O:-)", ":innocent:");
        ascii_replace.put("0:-3", ":innocent:");
        ascii_replace.put("0:3", ":innocent:");
        ascii_replace.put("0:-)", ":innocent:");
        ascii_replace.put("0:)", ":innocent:");
        ascii_replace.put("0;^)", ":innocent:");
        ascii_replace.put("O:)", ":innocent:");
        ascii_replace.put("O;-)", ":innocent:");
        ascii_replace.put("O=)", ":innocent:");
        ascii_replace.put("0;-)", ":innocent:");
        ascii_replace.put("O:-3", ":innocent:");
        ascii_replace.put("O:3", ":innocent:");
        ascii_replace.put("</3", ":broken_heart:");
        ascii_replace.put("B-)", ":sunglasses:");
        ascii_replace.put("B)", ":sunglasses:");
        ascii_replace.put("8)", ":sunglasses:");
        ascii_replace.put("8-)", ":sunglasses:");
        ascii_replace.put("B-D", ":sunglasses:");
        ascii_replace.put("8-D", ":sunglasses:");
        ascii_replace.put("-_-", ":expressionless:");
        ascii_replace.put("-__-", ":expressionless:");
        ascii_replace.put("-___-", ":expressionless:");
        ascii_replace.put(">:\\", ":confused:");
        ascii_replace.put(">:/", ":confused:");
        ascii_replace.put(":-/", ":confused:");
        ascii_replace.put(":-.", ":confused:");
        ascii_replace.put(":/", ":confused:");
        ascii_replace.put(":\\", ":confused:");
        ascii_replace.put("=/", ":confused:");
        ascii_replace.put("=\\", ":confused:");
        ascii_replace.put(":L", ":confused:");
        ascii_replace.put("=L", ":confused:");
        ascii_replace.put(":-O", ":open_mouth:");
        ascii_replace.put(":O", ":open_mouth:");
        ascii_replace.put("O_O", ":open_mouth:");
        ascii_replace.put(">:O", ":open_mouth:");
        ascii_replace.put(":-X", ":no_mouth:");
        ascii_replace.put(":X", ":no_mouth:");
        ascii_replace.put(":-#", ":no_mouth:");
        ascii_replace.put(":#", ":no_mouth:");
        ascii_replace.put("=X", ":no_mouth:");
        ascii_replace.put("=#", ":no_mouth:");
        ascii_replace.put(":\")", ":joy:");
        ascii_replace.put(":\"-)", ":joy:");
        ascii_replace.put("\":)", ":sweat_smile:");
        ascii_replace.put("\":-)", ":sweat_smile:");
        ascii_replace.put("\"=)", ":sweat_smile:");
        ascii_replace.put("\":D", ":sweat_smile:");
        ascii_replace.put("\":-D", ":sweat_smile:");
        ascii_replace.put("\"=D", ":sweat_smile:");
        ascii_replace.put(">:)", ":laughing:");
        ascii_replace.put(">;)", ":laughing:");
        ascii_replace.put(">:-)", ":laughing:");
        ascii_replace.put(">=)", ":laughing:");
        ascii_replace.put("\":(", ":sweat:");
        ascii_replace.put("\":-(", ":sweat:");
        ascii_replace.put("\"=(", ":sweat:");
        ascii_replace.put(">:P", ":stuck_out_tongue_winking_eye:");
        ascii_replace.put("X-P", ":stuck_out_tongue_winking_eye:");
        ascii_replace.put(">:[", ":disappointed:");
        ascii_replace.put(":-(", ":disappointed:");
        ascii_replace.put(":(", ":disappointed:");
        ascii_replace.put(":-[", ":disappointed:");
        ascii_replace.put(":[", ":disappointed:");
        ascii_replace.put("=(", ":disappointed:");
        ascii_replace.put(">:(", ":angry:");
        ascii_replace.put(">:-(", ":angry:");
        ascii_replace.put(":@", ":angry:");
        ascii_replace.put(":\"(", ":cry:");
        ascii_replace.put(":\"-(", ":cry:");
        ascii_replace.put(";(", ":cry:");
        ascii_replace.put(";-(", ":cry:");
        ascii_replace.put(">.<", ":persevere:");
        ascii_replace.put("#-)", ":dizzy_face:");
        ascii_replace.put("#)", ":dizzy_face:");
        ascii_replace.put("%-)", ":dizzy_face:");
        ascii_replace.put("%)", ":dizzy_face:");
        ascii_replace.put("X)", ":dizzy_face:");
        ascii_replace.put("X-)", ":dizzy_face:");
        ascii_replace.put(":)", ":slight_smile:");
        ascii_replace.put(":-)", ":slight_smile:");
        ascii_replace.put("=]", ":slight_smile:");
        ascii_replace.put("=)", ":slight_smile:");
        ascii_replace.put(":]", ":slight_smile:");
        ascii_replace.put(":P", ":stuck_out_tongue:");
        ascii_replace.put(":-P", ":stuck_out_tongue:");
        ascii_replace.put("=P", ":stuck_out_tongue:");
        ascii_replace.put(":-Þ", ":stuck_out_tongue:");
        ascii_replace.put(":Þ", ":stuck_out_tongue:");
        ascii_replace.put(":-b", ":stuck_out_tongue:");
        ascii_replace.put(":b", ":stuck_out_tongue:");
        ascii_replace.put("<3", ":heart:");
        ascii_replace.put(":D", ":smiley:");
        ascii_replace.put(":-D", ":smiley:");
        ascii_replace.put("=D", ":smiley:");
        ascii_replace.put(";)", ":wink:");
        ascii_replace.put(";-)", ":wink:");
        ascii_replace.put("*-)", ":wink:");
        ascii_replace.put("*)", ":wink:");
        ascii_replace.put(";-]", ":wink:");
        ascii_replace.put(";]", ":wink:");
        ascii_replace.put(";D", ":wink:");
        ascii_replace.put(";^)", ":wink:");
        ascii_replace.put(":*", ":kissing_heart:");
        ascii_replace.put(":-*", ":kissing_heart:");
        ascii_replace.put("=*", ":kissing_heart:");
        ascii_replace.put(":^*", ":kissing_heart:");
        ascii_replace.put("D:", ":fearful:");
        ascii_replace.put(":$", ":flushed:");
        ascii_replace.put("=$", ":flushed:");
        ascii_replace.put("(y)", ":thumbsup:");
    }

    private void populate_unicode_replace() {
        BufferedReader br = null;

        try {
            InputStream ins = context.getResources().openRawResource(context.getResources().getIdentifier("unicode_replace", "raw",context.getPackageName()));

            br = new BufferedReader(new InputStreamReader(ins));

            String line = null;

            // read file line by line
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                String hex = parts[0].trim();
                String shortcode = parts[1].trim();

                if (!hex.equals("")) {
                    unicode_replace.put(hex, shortcode);
                }
            }
        }
        catch (Exception e) {
            Log.e("PopulateUnicodeReplace",e.getMessage());
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                }
                catch (Exception e) {
                    Log.e("PopulateUnicodeReplace",e.getMessage());
                };
            }
        }
    }

    private void populate_unicode_replace_greedy() {
        unicode_replace_greedy.put("E380B0", ":wavy_dash:");
    }

    public LinkedHashMap<String, ArrayList<String>> getShortcodeReplace() {
        return shortcode_replace;
    }

    public LinkedHashMap<String, String> getAsciiReplace() {
        return ascii_replace;
    }

    public LinkedHashMap<String, String> getUnicodeReplace() {
        return unicode_replace;
    }

    public LinkedHashMap<String, String> getUnicodeReplaceGreedy() {
        return unicode_replace_greedy;
    }

    public String getAsciiRegexp() {
        return asciiRegexp;
    }
}
