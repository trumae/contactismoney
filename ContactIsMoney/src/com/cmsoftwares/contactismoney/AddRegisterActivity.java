package com.cmsoftwares.contactismoney;

import java.text.NumberFormat;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class AddRegisterActivity extends Activity {
	private Button btnOk;
	private Button btnCancel;
	private DatePicker datePickerData;
	private EditText editTextValue;
	private EditText editTextDescription;
	private Activity addreg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_register);
		addreg = this;

		btnOk = (Button) this.findViewById(R.id.btnAddRegOk);
		btnCancel = (Button) this.findViewById(R.id.btnAddRegCancel);

		datePickerData = (DatePicker) this
				.findViewById(R.id.datePickereditRegDate);
		editTextValue = (EditText) this.findViewById(R.id.editTextAddRegValue);
		editTextDescription = (EditText) this
				.findViewById(R.id.editTextAddRegDescription);

		btnOk.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// operacao
				try{
				Register reg = new Register();
				java.util.Date data = new Date(datePickerData.getYear() - 1900,
						datePickerData.getMonth(), datePickerData
								.getDayOfMonth());
				reg.setIdContact(RegistersActivity.idContact);
				reg.setCreated(new Date());
				reg.setData(data);
				reg.setDescription(editTextDescription.getText().toString());
				String val = editTextValue.getText().toString();
				Number num = NumberFormat.getNumberInstance().parse(val);
				if(RegistersActivity.oper == RegistersActivity.BUY)
				   reg.setValue(num.longValue());
				else 
					reg.setValue(-num.longValue());
				MainActivity.registroDBAdapter.addRecord(reg);
				addreg.finish();
				} catch(Exception e) {
				   Log.e("SQL", "Error including register " + e.getMessage());
				   Context context = getApplicationContext();
				   CharSequence text = "Error including register!";
				   int duration = Toast.LENGTH_SHORT;

				   Toast toast = Toast.makeText(context, text, duration);
				   toast.show();
				}
				
			}
		});

		btnCancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
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
