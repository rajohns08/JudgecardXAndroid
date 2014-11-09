package com.rajohns.judgecardx;

import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by rajohns on 11/9/14.
 *
 */
public class EditTextUtil {
    public static boolean hasEmptyRequiredTextField(ArrayList<EditText> requiredFields) {
        for (EditText editText : requiredFields) {
            if (editText.getText().toString().trim().length() == 0) {
                return true;
            }
        }

        return false;
    }
}
