package id.ac.unpam.absensisiswa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.RadioButton;
import android.widget.AdapterView;
import android.view.View;

import java.util.ArrayList;

import id.ac.unpam.absensisiswa.utils.CoreAPI;
import id.ac.unpam.absensisiswa.utils.HandlerAPI;

import org.json.JSONObject;
import org.json.JSONArray;

public class AddDaftar extends AppCompatActivity {
	/** Called when the activity is first created. */

	private Setting setting;
	private Spinner listItemKelas = null;
	private Spinner listItemMapel = null;
	private Spinner listItemGuru = null;
	private Spinner listItemSiswa = null;
	private RadioButton guru = null;
	private RadioButton siswa = null;
	private Button simpan = null;
	private ArrayList<ItemClass> itemsKelas, itemsMapel, itemsGuru, itemsSiswa;
	private ItemAdapter itemAdapterKelas, itemAdapterMapel, itemAdapterGuru, itemAdapterSiswa;
	private boolean guru_siswa = true;
  private boolean reload = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_daftar_activity);

		setting = new Setting(this);

		itemsKelas = new ArrayList<ItemClass>();
		itemsMapel = new ArrayList<ItemClass>();
		itemsGuru = new ArrayList<ItemClass>();
		itemsSiswa = new ArrayList<ItemClass>();

		listItemKelas = (Spinner)findViewById(R.id.listItemKelas);
		listItemMapel = (Spinner)findViewById(R.id.listItemMapel);
		listItemGuru = (Spinner)findViewById(R.id.listItemGuru);
		listItemSiswa = (Spinner)findViewById(R.id.listItemSiswa);
		listItemSiswa.setVisibility(View.GONE);

		guru = (RadioButton)findViewById(R.id.guru);
		siswa = (RadioButton)findViewById(R.id.siswa);
		simpan = (Button)findViewById(R.id.simpan);

		itemAdapterKelas = new ItemAdapter(this, android.R.layout.simple_spinner_item, itemsKelas);
		itemAdapterKelas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		listItemKelas.setAdapter(itemAdapterKelas);

		itemAdapterMapel = new ItemAdapter(this, android.R.layout.simple_spinner_item, itemsMapel);
		itemAdapterMapel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		listItemMapel.setAdapter(itemAdapterMapel);

		itemAdapterGuru = new ItemAdapter(this, android.R.layout.simple_spinner_item, itemsGuru);
		itemAdapterGuru.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		listItemGuru.setAdapter(itemAdapterGuru);

		itemAdapterSiswa = new ItemAdapter(this, android.R.layout.simple_spinner_item, itemsSiswa);
		itemAdapterSiswa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		listItemSiswa.setAdapter(itemAdapterSiswa);

    guru.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				listItemSiswa.setVisibility(View.GONE);
				listItemGuru.setVisibility(View.VISIBLE);
				guru_siswa = true;
			}
		});

		siswa.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				listItemGuru.setVisibility(View.GONE);
				listItemSiswa.setVisibility(View.VISIBLE);
				guru_siswa = false;
			}
		});

    adminForm();
	}

	public class Daftar {
		private int id = 0;
		private int user_id = 0;
		private int kelas_id = 0;
		private int mapel_id = 0;

		public int getId(){ return this.id; }
		public int getUserId(){ return this.user_id; }
		public int getKelasId(){ return this.kelas_id; }
		public int getMapelId(){ return this.mapel_id; }

		public void setId(int id){ this.id = id; }
		public void setUserId(int id){ this.user_id = id; }
		public void setKelasId(int id){ this.kelas_id = id; }
		public void setMapelId(int id){ this.mapel_id = id; }
	}

	public Daftar daftar = null;

	public void adminForm(){
		daftar = new Daftar();
		// get kelas
		itemsKelas.clear();
		listItemKelas.setAdapter(itemAdapterKelas);
		(new CoreAPI(this, setting.getUrlKelas(), new HandlerAPI(){
			@Override
			public void onReceive(JSONObject data){
        itemsKelas.clear();
    		listItemKelas.setAdapter(itemAdapterKelas);
				try{
					if(data.getBoolean("success")){
						JSONArray rows = data.getJSONArray("data");
						for (int i = 0; i < rows.length(); i++) {
							JSONObject row = rows.getJSONObject(i);
							itemsKelas.add(new ItemClass(row.getInt("id"), row.getString("nama")));
						}
						listItemKelas.setAdapter(itemAdapterKelas);
					}
				}
				catch(Exception e){}
			}

			@Override
			public void onError(){}
		})).execute();

		// get mapel
		itemsMapel.clear();
		listItemMapel.setAdapter(itemAdapterMapel);
		(new CoreAPI(this, setting.getUrlMapel(), new HandlerAPI(){
			@Override
			public void onReceive(JSONObject data){
        itemsMapel.clear();
    		listItemMapel.setAdapter(itemAdapterMapel);
				try{
					if(data.getBoolean("success")){
						JSONArray rows = data.getJSONArray("data");
						for (int i = 0; i < rows.length(); i++) {
							JSONObject row = rows.getJSONObject(i);
							itemsMapel.add(new ItemClass(row.getInt("id"), row.getString("nama")));
						}
						listItemMapel.setAdapter(itemAdapterMapel);
					}
				}
				catch(Exception e){}
			}

			@Override
			public void onError(){}
		})).execute();

    if(!reload){
      reload = true;
  		listItemKelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
  			@Override
  			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
  				ItemClass item = itemsKelas.get(position);
  				itemsGuru.clear();
  				itemsSiswa.clear();
  				listItemGuru.setAdapter(itemAdapterGuru);
  				listItemSiswa.setAdapter(itemAdapterSiswa);
  				daftar.setKelasId(item.getId());

  				(new CoreAPI(AddDaftar.this, setting.getUrlKMUGuruSiswaNotOn(daftar.getKelasId(), daftar.getMapelId()), new HandlerAPI(){
  					@Override
  					public void onReceive(JSONObject data){
      				itemsGuru.clear();
      				itemsSiswa.clear();
      				listItemGuru.setAdapter(itemAdapterGuru);
      				listItemSiswa.setAdapter(itemAdapterSiswa);
  						try{
  							if(data.getBoolean("success")){
  								JSONArray rows = data.getJSONArray("data");
  								for (int i = 0; i < rows.length(); i++) {
  									JSONObject row = rows.getJSONObject(i);
  									if(row.getInt("level") == 1)
  										itemsGuru.add(new ItemClass(row.getInt("id"), row.getString("nama_lengkap")));
  									else if(row.getInt("level") == 2)
  										itemsSiswa.add(new ItemClass(row.getInt("id"), row.getString("nama_lengkap")));
                    listItemGuru.setAdapter(itemAdapterGuru);
    								listItemSiswa.setAdapter(itemAdapterSiswa);
  								}
  								listItemGuru.setAdapter(itemAdapterGuru);
  								listItemSiswa.setAdapter(itemAdapterSiswa);
  							}
  						}
  						catch(Exception e){}
  					}

  					@Override
  					public void onError(){}
  				})).execute();
  			}

  			@Override
  			public void onNothingSelected(AdapterView<?> arg0) {}
  		});

  		listItemMapel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
  			@Override
  			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
  				ItemClass item = itemsMapel.get(position);
  				itemsGuru.clear();
  				itemsSiswa.clear();
  				listItemGuru.setAdapter(itemAdapterGuru);
  				listItemSiswa.setAdapter(itemAdapterSiswa);
  				daftar.setMapelId(item.getId());

  				(new CoreAPI(AddDaftar.this, setting.getUrlKMUGuruSiswaNotOn(daftar.getKelasId(), daftar.getMapelId()), new HandlerAPI(){
  					@Override
  					public void onReceive(JSONObject data){
      				itemsGuru.clear();
      				itemsSiswa.clear();
      				listItemGuru.setAdapter(itemAdapterGuru);
      				listItemSiswa.setAdapter(itemAdapterSiswa);
  						try{
  							if(data.getBoolean("success")){
  								JSONArray rows = data.getJSONArray("data");
  								for (int i = 0; i < rows.length(); i++) {
  									JSONObject row = rows.getJSONObject(i);
  									if(row.getInt("level") == 1)
  										itemsGuru.add(new ItemClass(row.getInt("id"), row.getString("nama_lengkap")));
  									else if(row.getInt("level") == 2)
  										itemsSiswa.add(new ItemClass(row.getInt("id"), row.getString("nama_lengkap")));
                    listItemGuru.setAdapter(itemAdapterGuru);
    								listItemSiswa.setAdapter(itemAdapterSiswa);
  								}
  								listItemGuru.setAdapter(itemAdapterGuru);
  								listItemSiswa.setAdapter(itemAdapterSiswa);
  							}
  						}
  						catch(Exception e){}
  					}

  					@Override
  					public void onError(){}
  				})).execute();
  			}

  			@Override
  			public void onNothingSelected(AdapterView<?> arg0) {}
  		});

  		listItemGuru.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
  			@Override
  			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
  				ItemClass item = itemsGuru.get(position);
  				if(guru_siswa) daftar.setUserId(item.getId());
  			}

  			@Override
  			public void onNothingSelected(AdapterView<?> arg0) {}
  		});

  		listItemSiswa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
  			@Override
  			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
  				ItemClass item = itemsSiswa.get(position);
  				if(!guru_siswa) daftar.setUserId(item.getId());
  			}

  			@Override
  			public void onNothingSelected(AdapterView<?> arg0) {}
  		});

      simpan.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
          int user_id = daftar.getUserId();
          int kelas_id = daftar.getKelasId();
          int mapel_id = daftar.getMapelId();
          if(user_id >= 1 && kelas_id >= 1 && mapel_id >= 1){
            String params = "";
            params += "user_id=" + user_id;
            params += "&kelas_id=" + kelas_id;
            params += "&mapel_id=" + mapel_id;
            (new CoreAPI(AddDaftar.this, setting.getUrlCreateKMU(), params, new HandlerAPI(){
              @Override
              public void onReceive(JSONObject data){
                try{
                  if(data.getBoolean("success")){
                    adminForm();
                    Toast.makeText(AddDaftar.this, "Berhasil Add Daftar", Toast.LENGTH_LONG).show();
                  }
                }
                catch(Exception e){}
              }

              @Override
              public void onError(){}
            })).execute();
          }
        }
      });
    }
	}

	@Override
	public void onResume(){
		super.onResume();
		adminForm();
  }
}
