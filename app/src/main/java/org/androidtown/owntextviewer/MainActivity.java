package org.androidtown.owntextviewer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends Activity {

    private Activity act = this;
    private GridView gridView;
    private ArrayList<String> path;
    private ArrayList<String> item;
    private TextView mPath;
    private String root = "/sdcard";
    private ScrollView scrollView;
    private Button setting;

    //textºä ¼³Á¤
    private int textSize = 10;
    private String incoding = "UTF8";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPath = (TextView)findViewById(R.id.location);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        gridView = (GridView)findViewById(R.id.gridView);
        setting = (Button)findViewById(R.id.setting);

        getDir(root);

        scrollView.setVerticalScrollBarEnabled(true);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("hing","1");
                Intent setintent = new Intent(getApplicationContext(),setting.class);
                Log.d("hing","2");
                startActivityForResult(setintent, 1000);
                Log.d("hing","3");
            }
        });


        gridView.setOnItemClickListener(new GridView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = new File(path.get(position));
                if (file.isDirectory()) {
                    if (file.canRead())
                        getDir(path.get(position));
                } else {
                    if (file.getName().endsWith(".txt")) {
                        Intent intent = new Intent(getApplicationContext(), TextRead.class);
                        intent.putExtra("txtName", file.getName());
                        intent.putExtra("txtPath", file.getPath());
                        intent.putExtra("txtSize", textSize);
                        intent.putExtra("incoding",incoding);
                        startActivity(intent);
                    }
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000){
            textSize =  resultCode;
        }
    }

    public void getDir(String dirPath){
        mPath.setText("Location: " + dirPath);

        item = new ArrayList<String>();
        path = new ArrayList<String>();

        File f = new File(dirPath);
        File[] files = f.listFiles();
        if(!dirPath.equals(root)){
            item.add(root);
            path.add(root);
            item.add("../");
            path.add(f.getParent());
        }
        for(int i = 0; i<files.length;i++){
            File file = files[i];
            path.add(file.getPath());
            if(file.isDirectory())
                item.add(file.getName()+"/");
            else
                item.add(file.getName());
        }
        gridView.setAdapter(new gridAdapter());
    }

    public class gridAdapter extends BaseAdapter{
        LayoutInflater inflater;

        public gridAdapter(){
            inflater = (LayoutInflater)act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return item.size();
        }

        @Override
        public Object getItem(int position) {
            return item.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = inflater.inflate(R.layout.file,parent,false);
            }
            ImageView imageView = (ImageView)convertView.findViewById(R.id.fileImage);
            TextView textView = (TextView)convertView.findViewById(R.id.fileName);
            imageView.setImageResource(R.drawable.folder);
            textView.setText(item.get(position));
            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
