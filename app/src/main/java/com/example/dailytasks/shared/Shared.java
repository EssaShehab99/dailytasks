package com.example.dailytasks.shared;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Shared {
    static public void showSnackBar(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }
}
