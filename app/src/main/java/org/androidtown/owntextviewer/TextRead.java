package org.androidtown.owntextviewer;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class TextRead extends Activity {

    private Intent intent;
    private String content;
    String path;
    private TextView txtView;
    ProgressBar progress;
    LoadTask load = new LoadTask();
    String txt;
    Button translateBtn;
    TranslateTask translateTask = new TranslateTask();
    int txtSize;
    String incoding = "UTF8";
    BufferedReader br;
    Spinner trans;
    String result;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_read);
        progress = (ProgressBar)findViewById(R.id.progressBar);

        Translate.setClientId("IT_mentoring");
        Translate.setClientSecret("bSM0GBRJP2LgcwR/yhSsXmuASg4S6UzSbFiIHyIeqyY=");

        intent = getIntent();
        content = intent.getStringExtra("txtName");
        path = intent.getStringExtra("txtPath");
        txtSize = intent.getIntExtra("txtSize",10);
//        incoding = intent.getStringExtra("incoding");
        txtView = (TextView)findViewById(R.id.txtContent);
        translateBtn = (Button)findViewById(R.id.translateBtn);

        load.execute(content, path);
        Log.i("txtPath", "txt설정");

        trans = (Spinner)findViewById(R.id.transSpn);
        ArrayAdapter transAdapter = ArrayAdapter.createFromResource(this,R.array.translate,android.R.layout.simple_spinner_item);
        transAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trans.setAdapter(transAdapter);


        if(txtSize < 100) {
            txtView.setTextSize(txtSize);
        }
        else{
            txtView.setTextSize(txtSize-100);
            incoding = "MS949";
        }

        translateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i("txtPath", "버튼눌림");

                translateTask.execute();
            }
        });
    }

    public class TranslateTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            try{
                Log.i("txtPath","txt : "+txt);
                if(trans.getSelectedItemPosition() == 0) {
                    result = Translate.execute(txt, Language.KOREAN, Language.ENGLISH);
                }
                else{
                    result = Translate.execute(txt, Language.ENGLISH, Language.KOREAN);
                }
                Log.i("txtPath","result : "+result);

                return result;
            }
            catch (Exception e){
                Log.i("txtPath","error catch");
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result){
            txtView.setText(result);
        }
    }

       public class LoadTask extends AsyncTask<String,Void,StringBuilder> {

        @Override
        protected StringBuilder doInBackground(String... params) {
            File file = new File(params[1]);
            StringBuilder text = new StringBuilder();

            try {
                if(txtSize < 100) {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
                }
                else{
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "MS949"));
                }

                String line;
                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }


            return text;
        }

        protected void onPostExecute(StringBuilder result){
            Log.i("txtPath","textPost : "+result.toString());
            progress.setVisibility(View.GONE);
            txtView.setText(result.toString());
            txt = result.toString();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_text_read, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
