package id.ac.unpam.absensisiswa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ScrollView;
import android.widget.ListView;
import android.view.View;

import java.util.ArrayList;

import id.ac.unpam.absensisiswa.utils.CoreAPI;
import id.ac.unpam.absensisiswa.utils.HandlerAPI;

import org.json.JSONObject;
import org.json.JSONArray;

public class ListActivity extends AppCompatActivity {
	/** Called when the activity is first created. */

  public static final int MODE_NONE = 0;
  public static final int MODE_KELAS_VIEW = 1;
  public static final int MODE_MAPEL_VIEW = 2;
  public static final int MODE_GURU_VIEW = 3;
  public static final int MODE_SISWA_VIEW = 4;
  public static final int MODE_DAFTAR_GURU_VIEW = 5;
  public static final int MODE_DAFTAR_SISWA_VIEW = 6;
  public static final int MODE_REPORT_GURU_VIEW = 7;
  public static final int MODE_REPORT_SISWA_VIEW = 8;
  public static final int MODE_REPORT_SISWA_LOG_VIEW = 9;
  public static final int MODE_REPORT_GURU_LOG_VIEW = 10;
  private int mode = 0;
  private Intent intent;

	private Setting setting;
  private ScrollView scroll;
  private ListView listView;

	private ArrayList<ItemClass> items;
  private ArrayList<ItemUserClass> itemsUser;
  private ArrayList<ItemDaftarClass> itemsDaftar;
  private ArrayList<ItemReportClass> itemsReport;
	private ItemAdapter itemAdapter;
  private ItemUserAdapter itemAdapterUser;
  private ItemDaftarAdapter itemAdapterDaftar;
  private ItemReportAdapter itemAdapterReport;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_activity);

		setting = new Setting(this);

    items = new ArrayList<ItemClass>();
    itemsUser = new ArrayList<ItemUserClass>();
    itemsDaftar = new ArrayList<ItemDaftarClass>();
    itemsReport = new ArrayList<ItemReportClass>();

    scroll = (ScrollView)findViewById(R.id.scroll);
    listView = (ListView)findViewById(R.id.listView);

    intent = getIntent();
    mode = intent.getIntExtra("MODE", MODE_NONE);
    switch(mode){
      case MODE_NONE:
        break;
      case MODE_KELAS_VIEW:
        kelasView();
        break;
      case MODE_MAPEL_VIEW:
        mapelView();
        break;
      case MODE_GURU_VIEW:
        guruView();
        break;
      case MODE_SISWA_VIEW:
        siswaView();
        break;
      case MODE_DAFTAR_GURU_VIEW:
        daftarGuruView();
        break;
      case MODE_DAFTAR_SISWA_VIEW:
        daftarSiswaView();
        break;
      case MODE_REPORT_GURU_LOG_VIEW:
      case MODE_REPORT_GURU_VIEW:
        reportGuruView();
        break;
      case MODE_REPORT_SISWA_LOG_VIEW:
      case MODE_REPORT_SISWA_VIEW:
        reportSiswaView();
        break;
      default:
        break;
    }
	}

  public void kelasView(){
    setTitle(getTitle().toString() + " Kelas");
    itemAdapter = new ItemAdapter(this, R.layout.simple_item, items, true);
		listView.setAdapter(itemAdapter);

    items.clear();
		listView.setAdapter(itemAdapter);
		(new CoreAPI(this, setting.getUrlKelas(), new HandlerAPI(){
			@Override
			public void onReceive(JSONObject data){
				items.clear();
				listView.setAdapter(itemAdapter);
				try{
					if(data.getBoolean("success")){
						JSONArray rows = data.getJSONArray("data");
						for (int i = 0; i < rows.length(); i++) {
							JSONObject row = rows.getJSONObject(i);
							items.add(new ItemClass(row.getInt("id"), row.getString("nama")));
						}
						listView.setAdapter(itemAdapter);
					}
				}
				catch(Exception e){}
			}

      @Override
      public void onError(){}
    })).execute();
  }

  public void mapelView(){
    setTitle(getTitle().toString() + " Mata Pelajaran");
    itemAdapter = new ItemAdapter(this, R.layout.simple_item, items, true);
		listView.setAdapter(itemAdapter);

    items.clear();
		listView.setAdapter(itemAdapter);
		(new CoreAPI(this, setting.getUrlMapel(), new HandlerAPI(){
			@Override
			public void onReceive(JSONObject data){
				items.clear();
				listView.setAdapter(itemAdapter);
				try{
					if(data.getBoolean("success")){
						JSONArray rows = data.getJSONArray("data");
						for (int i = 0; i < rows.length(); i++) {
							JSONObject row = rows.getJSONObject(i);
							items.add(new ItemClass(row.getInt("id"), row.getString("nama")));
						}
						listView.setAdapter(itemAdapter);
					}
				}
				catch(Exception e){}
			}

      @Override
      public void onError(){}
    })).execute();
  }

  public void guruView(){
    setTitle(getTitle().toString() + " Guru");
    itemAdapterUser = new ItemUserAdapter(this, R.layout.user_item, itemsUser);
		listView.setAdapter(itemAdapterUser);

    itemsUser.clear();
		listView.setAdapter(itemAdapterUser);
		(new CoreAPI(this, setting.getUrlGuru(), new HandlerAPI(){
			@Override
			public void onReceive(JSONObject data){
				items.clear();
				listView.setAdapter(itemAdapter);
				try{
					if(data.getBoolean("success")){
						JSONArray rows = data.getJSONArray("data");
						for (int i = 0; i < rows.length(); i++) {
							JSONObject row = rows.getJSONObject(i);
							itemsUser.add(new ItemUserClass(row.getInt("id"), row.getString("nama_lengkap"), row.getString("nomor_induk"), row.getString("username")));
						}
						listView.setAdapter(itemAdapterUser);
					}
				}
				catch(Exception e){}
			}

      @Override
      public void onError(){}
    })).execute();
  }

  public void siswaView(){
    setTitle(getTitle().toString() + " Siswa");
    itemAdapterUser = new ItemUserAdapter(this, R.layout.user_item, itemsUser);
		listView.setAdapter(itemAdapterUser);

    itemsUser.clear();
		listView.setAdapter(itemAdapterUser);
		(new CoreAPI(this, setting.getUrlSiswa(), new HandlerAPI(){
			@Override
			public void onReceive(JSONObject data){
				items.clear();
				listView.setAdapter(itemAdapter);
				try{
					if(data.getBoolean("success")){
						JSONArray rows = data.getJSONArray("data");
						for (int i = 0; i < rows.length(); i++) {
							JSONObject row = rows.getJSONObject(i);
							itemsUser.add(new ItemUserClass(row.getInt("id"), row.getString("nama_lengkap"), row.getString("nomor_induk"), row.getString("username")));
						}
						listView.setAdapter(itemAdapterUser);
					}
				}
				catch(Exception e){}
			}

      @Override
      public void onError(){}
    })).execute();
  }

  public void daftarGuruView(){
    setTitle(getTitle().toString() + " Daftar Tempat Guru");
    itemAdapterDaftar = new ItemDaftarAdapter(this, R.layout.daftar_item, itemsDaftar);
		listView.setAdapter(itemAdapterDaftar);

    itemsDaftar.clear();
		listView.setAdapter(itemAdapterDaftar);
		(new CoreAPI(this, setting.getUrlKMUAllGuru(), new HandlerAPI(){
			@Override
			public void onReceive(JSONObject data){
				itemsDaftar.clear();
				listView.setAdapter(itemAdapterDaftar);
				try{
					if(data.getBoolean("success")){
						JSONArray rows = data.getJSONArray("data");
						for (int i = 0; i < rows.length(); i++) {
							JSONObject row = rows.getJSONObject(i);
							itemsDaftar.add(new ItemDaftarClass(row.getInt("id"), row.getString("nama_lengkap"), row.getString("nama_kelas"), row.getString("nama_mapel")));
						}
						listView.setAdapter(itemAdapterDaftar);
					}
				}
				catch(Exception e){}
			}

      @Override
      public void onError(){}
    })).execute();
  }

  public void daftarSiswaView(){
    setTitle(getTitle().toString() + " Daftar Tempat Siswa");
    itemAdapterDaftar = new ItemDaftarAdapter(this, R.layout.daftar_item, itemsDaftar);
		listView.setAdapter(itemAdapterDaftar);

    itemsDaftar.clear();
		listView.setAdapter(itemAdapterDaftar);
		(new CoreAPI(this, setting.getUrlKMUAllSiswa(), new HandlerAPI(){
			@Override
			public void onReceive(JSONObject data){
				itemsDaftar.clear();
				listView.setAdapter(itemAdapterDaftar);
				try{
					if(data.getBoolean("success")){
						JSONArray rows = data.getJSONArray("data");
						for (int i = 0; i < rows.length(); i++) {
							JSONObject row = rows.getJSONObject(i);
							itemsDaftar.add(new ItemDaftarClass(row.getInt("id"), row.getString("nama_lengkap"), row.getString("nama_kelas"), row.getString("nama_mapel")));
						}
						listView.setAdapter(itemAdapterDaftar);
					}
				}
				catch(Exception e){}
			}

      @Override
      public void onError(){}
    })).execute();
  }

  public void reportGuruView(){
    setTitle("Report Guru");
    itemAdapterReport = new ItemReportAdapter(this, R.layout.report_item, itemsReport);
		listView.setAdapter(itemAdapterReport);

    int user_id = intent.getIntExtra("user_id", 0);
    int kelas_id = intent.getIntExtra("kelas_id", 0);
    int mapel_id = intent.getIntExtra("mapel_id", 0);

    itemsReport.clear();
		listView.setAdapter(itemAdapterReport);
		(new CoreAPI(this, setting.getUrlAbsenGuru(user_id, kelas_id, mapel_id), new HandlerAPI(){
			@Override
			public void onReceive(JSONObject data){
				itemsReport.clear();
				listView.setAdapter(itemAdapterReport);
				try{
					if(data.getBoolean("success")){
						JSONArray rows = data.getJSONArray("data");
						for (int i = 0; i < rows.length(); i++) {
							JSONObject row = rows.getJSONObject(i);
							itemsReport.add(new ItemReportClass(row.getInt("id"), row.getString("jam"), row.getString("tanggal"), row.getString("bulan"), row.getString("tahun"), row.getString("nama_kelas"), row.getString("nama_mapel")));
              setTitle("Report Guru " + row.getString("nama_lengkap"));
						}
						listView.setAdapter(itemAdapterReport);
					}
				}
				catch(Exception e){}
			}

      @Override
      public void onError(){}
    })).execute();
  }

  public void reportSiswaView(){
    setTitle("Report Siswa");
    itemAdapterReport = new ItemReportAdapter(this, R.layout.report_item, itemsReport);
		listView.setAdapter(itemAdapterReport);

    int user_id = intent.getIntExtra("user_id", 0);
    int kelas_id = intent.getIntExtra("kelas_id", 0);
    int mapel_id = intent.getIntExtra("mapel_id", 0);

    itemsReport.clear();
		listView.setAdapter(itemAdapterReport);
		(new CoreAPI(this, setting.getUrlAbsenSiswa(user_id, kelas_id, mapel_id), new HandlerAPI(){
			@Override
			public void onReceive(JSONObject data){
				itemsReport.clear();
				listView.setAdapter(itemAdapterReport);
				try{
					if(data.getBoolean("success")){
						JSONArray rows = data.getJSONArray("data");
						for (int i = 0; i < rows.length(); i++) {
							JSONObject row = rows.getJSONObject(i);
							itemsReport.add(new ItemReportClass(row.getInt("id"), row.getString("jam"), row.getString("tanggal"), row.getString("bulan"), row.getString("tahun"), row.getString("nama_kelas"), row.getString("nama_mapel")));
              setTitle("Report Siswa " + row.getString("nama_lengkap"));
						}
						listView.setAdapter(itemAdapterReport);
					}
				}
				catch(Exception e){}
			}

      @Override
      public void onError(){}
    })).execute();
  }
}
