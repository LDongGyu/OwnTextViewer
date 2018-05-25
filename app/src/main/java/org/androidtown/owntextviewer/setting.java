package org.androidtown.owntextviewer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class setting extends Activity {

    private TextView topText;
    //텍스트 사이즈
    private TextView textSizeProm;
    private Button textSizeUp;
    private TextView textSize;
    private Button textSizeDown;
    //텍스트 인코딩
    private Button finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        topText = (TextView)findViewById(R.id.setting);
        textSizeProm = (TextView)findViewById(R.id.sizeProm);
        textSizeUp = (Button)findViewById(R.id.BtnSizeUp);
        textSizeDown = (Button)findViewById(R.id.BtnSizeDown);
        textSize = (TextView)findViewById(R.id.textSize);
        finish = (Button)findViewById(R.id.finish);

        textSizeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer txtint = new Integer(Integer.parseInt(textSize.getText().toString()) + 1);
                textSize.setText(txtint.toString());
            }
        });

        textSizeDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(textSize.getText().toString()) > 0) {
                    Integer txtint2 = new Integer(Integer.parseInt(textSize.getText().toString()) - 1);
                    textSize.setText(txtint2.toString());
                }
            }
        });

        final Spinner incode = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter incodeAdapter = ArrayAdapter.createFromResource(this,R.array.incode,android.R.layout.simple_spinner_item);
        incodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incode.setAdapter(incodeAdapter);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("hing", "4");
                int spinner = incode.getSelectedItemPosition();
                if(spinner == 0) {
                    setResult(Integer.parseInt(textSize.getText().toString()));
                }
                else{
                    setResult(Integer.parseInt(textSize.getText().toString())+100);
                }
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
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
