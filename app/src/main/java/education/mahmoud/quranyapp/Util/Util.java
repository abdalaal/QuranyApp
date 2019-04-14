package education.mahmoud.quranyapp.Util;

import android.arch.persistence.room.util.StringUtil;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import education.mahmoud.quranyapp.model.Quran;

public class Util {

    private static final String TAG = "Util";

    public static Quran getQuran(Context context) {
        try (InputStream fileIn = context.getAssets().open("data.json");
             BufferedInputStream bufferedIn = new BufferedInputStream(fileIn);
             Reader reader = new InputStreamReader(bufferedIn, StandardCharsets.UTF_8)) {
            return new Gson().fromJson(reader, Quran.class);

        } catch (Exception e) {
            return null;
        }
    }

    public static Quran getQuranClean(Context context) {
        try (InputStream fileIn = context.getAssets().open("quran_clean.json");
             BufferedInputStream bufferedIn = new BufferedInputStream(fileIn);
             Reader reader = new InputStreamReader(bufferedIn, StandardCharsets.UTF_8)) {
            return new Gson().fromJson(reader, Quran.class);

        } catch (Exception e) {
            return null;
        }
    }

    public static Spannable getSpannable(String text) {

        Spannable spannable = new SpannableString(text);

        String REGEX = "لل";
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(text);

        int start, end;

        //region allah match
        while (m.find()) {
            start = m.start();
            while (text.charAt(start) != ' ' && start != 0) {
                start--;
            }
            end = m.end();
            while (text.charAt(end) != ' ') {
                end++;
            }
            spannable.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        //endregion


        return spannable;

    }

    public static Spannable getSpanOfText(String text, String word) {
        Spannable spannable = new SpannableString(text);
        String REGEX = word;
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(text);

        Log.d(TAG, text + "getSpanOfText: word " + word);
        while (m.find()) {
            Log.d(TAG, "getSpanOfText: start " + m.start());
            Log.d(TAG, "getSpanOfText: end " + m.end());
            spannable.setSpan(new ForegroundColorSpan(Color.YELLOW), m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        return spannable;

    }

    public static String getDirectoryPath() {
        File f = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOCUMENTS), "quran");
        return f.getAbsolutePath();
    }

    public static String getDirectoryPath(String dir) {
        File f = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOCUMENTS), dir);
        return f.getAbsolutePath();
    }

    public static String makeFilePath(String name) {
        String dirPath = getDirectoryPath("quran");
        return dirPath + name;
    }

    /**
     * checks if the device is connected to the internet or not
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static void hideInputKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);
    }


    public  static Spannable getDiffSpannaled(String original, String toCompStr){
        TestText testText = new TestText();
        testText.gitDiff(original, toCompStr);
        String res = testText.getResString();
        return getSpannable(res ,testText.getCorrectPoints() ,testText.getInsertionPoints(),testText.getDeletionPoints());

    }

    public static Spannable getSpannable(String text ,List<Point> correctPoints , List<Point> insertPoints  , List<Point> delePoint) {
        Spannable spannable = new SpannableString(text);

        for(Point point : insertPoints){
            spannable.setSpan(new ForegroundColorSpan(Color.YELLOW), point.getStart()
                    , point.getEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        for(Point point : delePoint){
            spannable.setSpan(new ForegroundColorSpan(Color.RED), point.getStart()
                    , point.getEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        for(Point point : correctPoints){
            spannable.setSpan(new ForegroundColorSpan(Color.GREEN), point.getStart()
                    , point.getEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }



        return spannable;

    }


}
