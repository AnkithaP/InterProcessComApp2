package com.aryaan.ankitha.interprocesscomapp2;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText messageFromApp1;
    TextView status;

    String packageName = "com.aryaan.ankitha.interprocesscomapp1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageFromApp1 = (EditText)findViewById(R.id.editText);
        status = (TextView)findViewById(R.id.status);
    }

    public void loadFile(View view){
        PackageManager packageManager = getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName,packageManager.GET_META_DATA);
            //status.setText(applicationInfo.dataDir+"/files/data.txt");
            String fullPath = applicationInfo.dataDir+"/files/data.txt";
            readFile(fullPath);
        } catch (PackageManager.NameNotFoundException e) {
            status.setTextColor(Color.RED);
            status.setText(""+e);
        }
    }

    public void readFile(String fullPath){

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(fullPath));
            int read = -1;
            StringBuffer buffer = new StringBuffer();
            while((read = fileInputStream.read())!= -1){
                buffer.append((char)read);
            }
            message(""+buffer);
            messageFromApp1.setText(buffer);
            messageFromApp1.setTextColor(Color.GREEN);
            status.setText(buffer+"\n Was read successfully from \n"+fullPath);
        } catch (FileNotFoundException e) {
            status.setTextColor(Color.RED);
            status.setText(""+e);
        } catch (IOException e) {
            status.setTextColor(Color.RED);
            status.setText(""+e);
        }finally {
            if (fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    status.setTextColor(Color.RED);
                    status.setText(""+e);
                }
            }
        }
    }
    public void message(String s){
        Toast.makeText(MainActivity.this,s, Toast.LENGTH_SHORT).show();
    }
}
