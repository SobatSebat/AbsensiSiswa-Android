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

public class MenuReport extends AppCompatActivity {
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
  private Intent intent = null;
  private int mode = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_daftar_activity);

		setting = new Setting(this);

    intent = getIntent();
    mode = intent.getIntExtra("MODE", 3);
    if(mode == 1)
      setTitle(getTitle() + " Guru");
    else if(mode == 2 || mode == 5)
      setTitle(getTitle() + " Siswa");
    else if(mode == 3 || mode == 4){
      setTitle(getTitle());
    }

		itemsKelas = new ArrayList<ItemClass>();
		itemsMapel = new ArrayList<ItemClass>();
		itemsGuru = new ArrayList<ItemClass>();
		itemsSiswa = new ArrayList<ItemClass>();

		listItemKelas = (Spinner)findViewById(R.id.listItemKelas);
		listItemMapel = (Spinner)findViewById(R.id.listItemMapel);
		listItemGuru = (Spinner)findViewById(R.id.listItemGuru);
		listItemSiswa = (Spinner)findViewById(R.id.listItemSiswa);
    if(mode != 5)
      listItemSiswa.setVisibility(View.GONE);

    if(mode == 3 || mode == 4 || mode == 5){
		    listItemGuru.setVisibility(View.GONE);
    }

		guru = (RadioButton)findViewById(R.id.guru);
		siswa = (RadioButton)findViewById(R.id.siswa);
		simpan = (Button)findViewById(R.id.simpan);
    simpan.setText("Lihat");
    guru.setVisibility(View.GONE);
    siswa.setVisibility(View.GONE);

		itemAdapterKelas = new ItemAdapter(this, android.R.layout.simple_spinner_item, itemsKelas);
		itemAdapterKelas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		listItemKelas.setAdapter(itemAdapterKelas);

		itemAdapterMapel = new ItemAdapter(this, android.R.layout.simple_spinner_item, itemsMapel);
		itemAdapterMapel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		listItemMapel.setAdapter(itemAdapterMapel);

		itemAdapterGuru = new ItemAdapter(this, android.R.layout.simple_spinner_item, itemsGuru);
		itemAdapterGuru.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		listItemGuru.setAdapter(itemAdapterGuru);
    if(mode == 5){
  		itemAdapterSiswa = new ItemAdapter(this, android.R.layout.simple_spinner_item, itemsSiswa);
  		itemAdapterSiswa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
  		listItemSiswa.setAdapter(itemAdapterSiswa);
      guruForm();
    }
    else
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

    if(mode == 3 || mode == 4)
      daftar.setUserId(setting.getId());

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
  				daftar.setKelasId(item.getId());
          if(mode == 3 || mode == 4) return;
  				itemsGuru.clear();
  				listItemGuru.setAdapter(itemAdapterGuru);

  				(new CoreAPI(MenuReport.this, setting.getUrlKMUGuruSiswa(daftar.getKelasId(), daftar.getMapelId()), new HandlerAPI(){
  					@Override
  					public void onReceive(JSONObject data){
      				itemsGuru.clear();
      				listItemGuru.setAdapter(itemAdapterGuru);
  						try{
  							if(data.getBoolean("success")){
  								JSONArray rows = data.getJSONArray("data");
  								for (int i = 0; i < rows.length(); i++) {
  									JSONObject row = rows.getJSONObject(i);
  									if(row.getInt("level") == mode)
  										itemsGuru.add(new ItemClass(row.getInt("id"), row.getString("nama_lengkap")));
                    listItemGuru.setAdapter(itemAdapterGuru);
  								}
  								listItemGuru.setAdapter(itemAdapterGuru);
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
  				ItemClass item = itemsKelas.get(position);
  				daftar.setMapelId(item.getId());
          if(mode == 3 || mode == 4) return;
  				itemsGuru.clear();
  				listItemGuru.setAdapter(itemAdapterGuru);

  				(new CoreAPI(MenuReport.this, setting.getUrlKMUGuruSiswa(daftar.getKelasId(), daftar.getMapelId()), new HandlerAPI(){
  					@Override
  					public void onReceive(JSONObject data){
      				itemsGuru.clear();
      				listItemGuru.setAdapter(itemAdapterGuru);
  						try{
  							if(data.getBoolean("success")){
  								JSONArray rows = data.getJSONArray("data");
  								for (int i = 0; i < rows.length(); i++) {
  									JSONObject row = rows.getJSONObject(i);
  									if(row.getInt("level") == mode)
  										itemsGuru.add(new ItemClass(row.getInt("user_id"), row.getString("nama_lengkap")));
                    listItemGuru.setAdapter(itemAdapterGuru);
  								}
  								listItemGuru.setAdapter(itemAdapterGuru);
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
      if(mode != 3 && mode != 4){
    		listItemGuru.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
    			@Override
    			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
    				ItemClass item = itemsGuru.get(position);
    				if(guru_siswa) daftar.setUserId(item.getId());
    			}

    			@Override
    			public void onNothingSelected(AdapterView<?> arg0) {}
    		});
      }

      simpan.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
          int user_id = daftar.getUserId();
          int kelas_id = daftar.getKelasId();
          int mapel_id = daftar.getMapelId();
          if(user_id >= 1 && kelas_id >= 1 && mapel_id >= 1){
            Intent i = new Intent(MenuReport.this, ListActivity.class);
            i.putExtra("MODE", (ListActivity.MODE_REPORT_GURU_VIEW - 1) + mode);
            i.putExtra("level", mode);
            i.putExtra("user_id", user_id);
            i.putExtra("kelas_id", kelas_id);
            i.putExtra("mapel_id", mapel_id);
            startActivity(i);
          }
        }
      });
    }
	}

  public void guruForm(){
		daftar = new Daftar();
    listItemSiswa.setVisibility(View.VISIBLE);
    listItemGuru.setVisibility(View.GONE);
		// get kelas
		itemsKelas.clear();
		listItemKelas.setAdapter(itemAdapterKelas);
		(new CoreAPI(this, setting.getUrlKMUKelas(setting.getId()), new HandlerAPI(){
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

		if(!reload){
			reload = true;
			listItemKelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
					ItemClass item = itemsKelas.get(position);
					itemsMapel.clear();
					listItemMapel.setAdapter(itemAdapterMapel);

					daftar.setKelasId(item.getId());

					(new CoreAPI(MenuReport.this, setting.getUrlKMUMapel(setting.getId(), daftar.getKelasId()), new HandlerAPI(){
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
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {}
			});

			listItemMapel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
					ItemClass item = itemsMapel.get(position);
					itemsSiswa.clear();
					listItemSiswa.setAdapter(itemAdapterSiswa);
					daftar.setMapelId(item.getId());

					(new CoreAPI(MenuReport.this, setting.getUrlKMUSiswa(daftar.getKelasId(), daftar.getMapelId()), new HandlerAPI(){
						@Override
						public void onReceive(JSONObject data){
							itemsSiswa.clear();
							listItemSiswa.setAdapter(itemAdapterSiswa);
							try{
								if(data.getBoolean("success")){
									JSONArray rows = data.getJSONArray("data");
									for (int i = 0; i < rows.length(); i++) {
										JSONObject row = rows.getJSONObject(i);
										if(row.getInt("level") == 2)
											itemsSiswa.add(new ItemClass(row.getInt("user_id"), row.getString("nama_lengkap")));
  									listItemSiswa.setAdapter(itemAdapterSiswa);
									}
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

			listItemSiswa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
					ItemClass item = itemsSiswa.get(position);
					daftar.setUserId(item.getId());
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
            Intent i = new Intent(MenuReport.this, ListActivity.class);
            i.putExtra("MODE", (ListActivity.MODE_REPORT_GURU_VIEW - 1) + 2);
            i.putExtra("level", mode);
            i.putExtra("user_id", user_id);
            i.putExtra("kelas_id", kelas_id);
            i.putExtra("mapel_id", mapel_id);
            startActivity(i);
          }
        }
      });
		}
	}

	@Override
	public void onResume(){
		super.onResume();
    if(mode == 5)
      guruForm();
    else
      adminForm();
  }
}
