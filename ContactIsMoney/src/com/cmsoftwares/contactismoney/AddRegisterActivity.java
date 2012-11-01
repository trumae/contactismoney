package com.cmsoftwares.contactismoney;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class AddRegisterActivity extends Activity {
	private Button btnOk;
	private Button btnCancel;
	private Activity addreg;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_register);
        addreg = this;
        
        btnOk = (Button) this.findViewById(R.id.btnAddRegOk);
        btnCancel = (Button) this.findViewById(R.id.btnAddRegCancel);
        
        btnOk.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	//operacao
		    	
		        addreg.finish();
		    }
		});
        
        btnCancel.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	//operacao
		    	
		        addreg.finish();
		    }
		});
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_register, menu);
        return true;
    }
}
