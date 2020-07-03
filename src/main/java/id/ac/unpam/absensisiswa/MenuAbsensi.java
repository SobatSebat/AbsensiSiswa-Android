package id.ac.unpam.absensisiswa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.Button;
import android.util.Log;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import java.util.ArrayList;

import id.ac.unpam.absensisiswa.utils.CoreAPI;
import id.ac.unpam.absensisiswa.utils.HandlerAPI;

import org.json.JSONObject;
import org.json.JSONArray;

import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;

public class MenuAbsensi extends AppCompatActivity {
	/** Called when the activity is first created. */

	private Setting setting = null;
	private TextView label = null;
	private Spinner listItemKelas = null;
	private Spinner listItemMapel = null;
	private Spinner listItemGuru = null;
	private Spinner listItemSiswa = null;
	private RadioButton guru = null;
	private RadioButton siswa = null;
	private FloatingActionMenu menu = null;
	private FloatingActionButton add_kelas = null;
	private FloatingActionButton add_mapel = null;
	private FloatingActionButton add_guru_siswa = null;
	private FloatingActionButton add_daftar = null;
	private ArrayList<ItemClass> itemsKelas, itemsMapel, itemsGuru, itemsSiswa;
	private ItemAdapter itemAdapterKelas, itemAdapterMapel, itemAdapterGuru, itemAdapterSiswa;
	private boolean guru_siswa = true;
	private int admin_guru = 0;
  private boolean reload = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_absensi_activity);

		setting = new Setting(this);

		if(setting.getToken().equals("")){
			startActivity(new Intent(this, Login.class));
		}
		else {
			(new CoreAPI(this, setting.getUrlTokenCheck(), new HandlerAPI(){
				@Override
				public void onReceive(JSONObject data){
					try{
						if(!data.getBoolean("success"))
							startActivity(new Intent(MenuAbsensi.this, Login.class));
					}
					catch(Exception e){
						startActivity(new Intent(MenuAbsensi.this, Login.class));
					}
				}

				@Override
				public void onError(){
					startActivity(new Intent(MenuAbsensi.this, Login.class));
				}
			})).execute();
		}

		itemsKelas = new ArrayList<ItemClass>();
		itemsMapel = new ArrayList<ItemClass>();
		itemsGuru = new ArrayList<ItemClass>();
		itemsSiswa = new ArrayList<ItemClass>();

		label = (TextView)findViewById(R.id.label);
		listItemKelas = (Spinner)findViewById(R.id.listItemKelas);
		listItemMapel = (Spinner)findViewById(R.id.listItemMapel);
		listItemGuru = (Spinner)findViewById(R.id.listItemGuru);
		listItemSiswa = (Spinner)findViewById(R.id.listItemSiswa);
		listItemSiswa.setVisibility(View.GONE);

		guru = (RadioButton)findViewById(R.id.guru);
		siswa = (RadioButton)findViewById(R.id.siswa);
		menu = (FloatingActionMenu)findViewById(R.id.menu);

		add_kelas = (FloatingActionButton)findViewById(R.id.add_kelas);
		add_mapel = (FloatingActionButton)findViewById(R.id.add_mapel);
		add_guru_siswa = (FloatingActionButton)findViewById(R.id.add_guru_siswa);
		add_daftar = (FloatingActionButton)findViewById(R.id.add_daftar);

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

		(new CoreAPI(this, setting.getUrlLabel(), new HandlerAPI(){
			@Override
			public void onReceive(JSONObject data){
				try{
					if(data.getBoolean("success")){
						label.setText(data.getString("data"));
						if(!data.getString("data").equals("Admin")){
							menu.setVisibility(View.GONE);
							guru.setVisibility(View.GONE);
							siswa.setVisibility(View.GONE);
							listItemGuru.setVisibility(View.GONE);
							listItemSiswa.setVisibility(View.VISIBLE);
							guru_siswa = false;
							guruForm();
							admin_guru = 2;
						}
						else if(data.getString("data").equals("Admin")){
							floatingMenu();
							adminForm();
							guru_siswa = true;
							admin_guru = 1;
						}
					}
				}
				catch(Exception e){}
			}

			@Override
			public void onError(){}
		})).execute();
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

					(new CoreAPI(MenuAbsensi.this, setting.getUrlKMUGuruSiswa(daftar.getKelasId(), daftar.getMapelId()), new HandlerAPI(){
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

					(new CoreAPI(MenuAbsensi.this, setting.getUrlKMUGuruSiswa(daftar.getKelasId(), daftar.getMapelId()), new HandlerAPI(){
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
					if(guru_siswa) daftar.setId(item.getId());
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {}
			});

			listItemSiswa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
					ItemClass item = itemsSiswa.get(position);
					if(!guru_siswa) daftar.setId(item.getId());
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {}
			});
		}
	}

	public void guruForm(){
		daftar = new Daftar();
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

					(new CoreAPI(MenuAbsensi.this, setting.getUrlKMUMapel(setting.getId(), daftar.getKelasId()), new HandlerAPI(){
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

					(new CoreAPI(MenuAbsensi.this, setting.getUrlKMUSiswa(daftar.getKelasId(), daftar.getMapelId()), new HandlerAPI(){
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
											itemsSiswa.add(new ItemClass(row.getInt("id"), row.getString("nama_lengkap")));
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
					if(!guru_siswa) daftar.setId(item.getId());
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {}
			});
		}
	}

	public void floatingMenu(){
		add_kelas.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				startActivity(new Intent(MenuAbsensi.this, AddKelas.class));
			}
		});

		add_mapel.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				startActivity(new Intent(MenuAbsensi.this, AddMapel.class));
			}
		});

		add_guru_siswa.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				startActivity(new Intent(MenuAbsensi.this, AddGuruSiswa.class));
			}
		});

		add_daftar.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				startActivity(new Intent(MenuAbsensi.this, AddDaftar.class));
			}
		});

	}

	@Override
	public void onResume(){
		super.onResume();
		if(admin_guru == 1) adminForm();
		else if(admin_guru == 2) guruForm();
	}
}
