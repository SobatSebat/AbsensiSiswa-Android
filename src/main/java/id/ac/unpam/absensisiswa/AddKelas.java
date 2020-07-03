package id.ac.unpam.absensisiswa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;

import id.ac.unpam.absensisiswa.utils.CoreAPI;
import id.ac.unpam.absensisiswa.utils.HandlerAPI;

import org.json.JSONObject;

public class AddKelas extends AppCompatActivity {
	/** Called when the activity is first created. */

	private Setting setting;
	private EditText nama_kelas;
	private Button simpan;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_kelas_activity);

		setting = new Setting(this);
		nama_kelas = (EditText)findViewById(R.id.nama_kelas);
		simpan = (Button)findViewById(R.id.simpan);

		simpan.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				final String nama = nama_kelas.getText().toString();
				if(!nama.equals("")){
					(new CoreAPI(AddKelas.this, setting.getUrlCreateKelas(), "nama=" + nama, new HandlerAPI(){
						@Override
						public void onReceive(JSONObject data){
							try{
								if(data.getBoolean("success")){
									nama_kelas.setText("");
									Toast.makeText(AddKelas.this, "Berhasil, Add Kelas " + nama, Toast.LENGTH_LONG).show();
								}
							}
							catch(Exception e){}
						}

						public void onError(){}
					})).execute();
				}
			}
		});
	}
}
