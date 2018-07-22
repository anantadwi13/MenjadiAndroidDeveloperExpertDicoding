package com.anantadwi13.readwritefile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.tv);
        FileHelper.writeToFile(this,"oke","lain filenya\nOKEOKE\nMantap");
        String str = FileHelper.readFromFile(this,"coba");
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

        try {
            File newfile = new File(getFilesDir()+"/"+"folderawal/sip/lebihdalam lagi/namafile");
            new File(newfile.getParent()).mkdirs();
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(new FileOutputStream(newfile));
            outputStreamWriter.write("OKE");
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = getFilesDir();

        StringBuilder filepaths= new StringBuilder();
        for (String item : file.list()) {
            filepaths.append(item).append("\n");
        }
        String textviewisi = str+"\n\n"+filepaths;
        textView.setText(textviewisi);
    }
}
