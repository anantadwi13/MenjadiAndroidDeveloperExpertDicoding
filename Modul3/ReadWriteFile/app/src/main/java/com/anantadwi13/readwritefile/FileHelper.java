package com.anantadwi13.readwritefile;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileHelper {
    public static void writeToFile(Context context, String filename, String data){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename,Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: "+e.toString());
        } catch (Exception e){
            Log.e("Exception", "File write failed: "+e.toString());
        }
    }

    public static String readFromFile(Context context, String filename){
        String data = "";
        boolean firstline = true;
        try {
            InputStream inputStream = context.openFileInput(filename);
            if (inputStream!=null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String stringPerLine;
                StringBuilder str = new StringBuilder();
                while ((stringPerLine=bufferedReader.readLine())!=null) {
                    if (!firstline)
                        str.append("\n");
                    str.append(stringPerLine);
                    firstline = false;
                }
                inputStream.close();
                data = str.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("Exception", "File not found: "+e.toString());
        } catch (IOException e) {
            Log.e("Exception", "File read failed: "+e.toString());
        }
        return data;
    }
}