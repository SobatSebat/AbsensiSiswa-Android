package id.ac.unpam.absensisiswa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.widget.RadioButton;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.view.View;

import id.ac.unpam.absensisiswa.utils.CoreAPI;
import id.ac.unpam.absensisiswa.utils.HandlerAPI;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class AddGuruSiswa extends AppCompatActivity {
    /** Called when the activity is first created. */

    private Setting setting;
    private boolean guru_siswa = true;
    private RadioButton guru = null;
    private RadioButton siswa = null;
    private EditText username = null;
    private EditText password = null;
    private EditText nomor_induk = null;
    private EditText nama_lengkap = null;
    private EditText tanggal_lahir = null;
    private EditText nomor_telpon = null;
    private EditText nomor_telpon_ortu = null;
    private EditText alamat = null;
    private Button simpan = null;
    private DatePickerDialog tgl_lahir = null;
    private long tgl = 881427600;
    private long def = 881427600;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_guru_siswa_activity);

        setting = new Setting(this);

        guru = (RadioButton)findViewById(R.id.guru);
        siswa = (RadioButton)findViewById(R.id.siswa);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        nomor_induk = (EditText)findViewById(R.id.nomor_induk);
        nama_lengkap = (EditText)findViewById(R.id.nama_lengkap);
        tanggal_lahir = (EditText)findViewById(R.id.tanggal_lahir);
        nomor_telpon = (EditText)findViewById(R.id.nomor_telpon);
        nomor_telpon_ortu = (EditText)findViewById(R.id.nomor_telpon_ortu);
        alamat = (EditText)findViewById(R.id.alamat);

        simpan = (Button)findViewById(R.id.simpan);
        nomor_telpon_ortu.setVisibility(View.GONE);

        guru.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View v){
            nomor_telpon_ortu.setVisibility(View.GONE);
            guru_siswa = true;
          }
        });

        siswa.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View v){
            nomor_telpon_ortu.setVisibility(View.VISIBLE);
            guru_siswa = false;
          }
        });

        tanggal_lahir.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View v){
            showDatePicker();
          }
        });

        tanggal_lahir.setOnFocusChangeListener(new View.OnFocusChangeListener() {
          @Override
          public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
              showDatePicker();
            }
          }
        });

        simpan.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View v){
            final String _username = username.getText().toString();
            String _password = password.getText().toString();
            final String _nomor_induk = nomor_induk.getText().toString();
            final String _nama_lengkap = nama_lengkap.getText().toString();
            String _nomor_telpon = nomor_telpon.getText().toString();
            String _nomor_telpon_ortu = nomor_telpon_ortu.getText().toString();
            String _alamat = alamat.getText().toString();
            if(!_username.equals("") && !_password.equals("") && !_nomor_induk.equals("") && !_nama_lengkap.equals("")){
              String params = "username=" + _username;
              params += "&password=" + _password;
              params += "&nomor_induk=" + _nomor_induk;
              params += "&nama_lengkap=" + _nama_lengkap;

              if(!_nomor_telpon.equals("")) params += "&nomor_telpon=" + _nomor_telpon;
              if(!guru_siswa && !_nomor_telpon_ortu.equals("")) params += "&nomor_telpon_ortu=" + _nomor_telpon_ortu;
              if(tgl != def) params += "&tanggal_lahir=" + tgl;
              if(!_alamat.equals("")) params += "&alamat=" + _alamat;

              String url = setting.getUrlCreateSiswa();
              if(guru_siswa) url = setting.getUrlCreateGuru();
              (new CoreAPI(AddGuruSiswa.this, url, params, new HandlerAPI(){
                @Override
                public void onReceive(JSONObject data){
                  try{
                    if(data.getBoolean("success")){
                      username.setText("");
                      password.setText("");
                      nomor_induk.setText("");
                      nama_lengkap.setText("");
                      tanggal_lahir.setText("");
                      setTgl(def);
                      nomor_telpon.setText("");
                      nomor_telpon_ortu.setText("");
                      alamat.setText("");
                      Toast.makeText(AddGuruSiswa.this, "Berhasil Add " + (guru_siswa ? "Guru" : "Siswa") + " " + _nama_lengkap + " (" + _nomor_induk + "/" + _username + ")", Toast.LENGTH_LONG).show();
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

    public void setTgl(long t){
      tgl = t;
    }

    public void showDatePicker(){
      Date D = new Date(tgl * 1000);
      D.setYear(D.getYear() + 1900);
      tgl_lahir = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
          Date date = new Date(year, monthOfYear, dayOfMonth);
          date.setYear(date.getYear() - 1900);
          tanggal_lahir.setText(dayOfMonth + "-" + monthOfYear + "-" + year);
          setTgl(Long.valueOf(date.getTime()/1000));
        }
      }, D.getYear(), D.getMonth(), D.getDate());
      tgl_lahir.show();
    }
}
