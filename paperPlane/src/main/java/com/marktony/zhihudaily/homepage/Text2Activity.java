package com.marktony.zhihudaily.homepage;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.marktony.zhihudaily.R;

import org.w3c.dom.Text;

import static com.marktony.zhihudaily.R.id.toolbar;

public class Text2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text2);
        final TextView text = (TextView)findViewById(R.id.text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("测试");

        final FloatingActionButton ffs=  (FloatingActionButton)findViewById(R.id.fab);
        ffs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(text,"我最帅",Snackbar.LENGTH_LONG)
                        .setAction("cancel", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //这里的单击事件代表点击消除Action后的响应事件

                            }
                        })
                        .show();
                ffs.hide();
            }
        }

        );

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(text,"谁最帅",Snackbar.LENGTH_LONG)
//                        .setAction("xx", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Toast.makeText(Text2Activity.this,"3333",Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .setActionTextColor(ContextCompat.getColor(Text2Activity.this,R.color.colorPrimaryDark))
//                        .show();
                ffs.show();
            }
        });
        initViews();
    }

    private void initViews() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
