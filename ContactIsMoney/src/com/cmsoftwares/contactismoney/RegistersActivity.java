package com.cmsoftwares.contactismoney;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import com.cmsoftwares.contactismoney.RegistroDBAdapter;;

public class RegistersActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registers);           
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_registers, menu);
        return true;
    }
   
   
}
