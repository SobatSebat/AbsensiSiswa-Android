package id.ac.unpam.absensisiswa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import id.ac.unpam.absensisiswa.utils.CoreAPI;
import id.ac.unpam.absensisiswa.utils.HandlerAPI;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    /** Called when the activity is first created. */

    private Setting setting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        setting = new Setting(this);

        if(setting.getToken().equals("")){
          startActivity(new Intent(this, Login.class));
        }
        else {
          (new CoreAPI(this, setting.getUrlTokenCheck(), new HandlerAPI(){
            @Override
            public void onReceive(JSONObject data){
              try{
                if(data.getBoolean("success"))
                  startActivity(new Intent(MainActivity.this, MenuAbsensi.class));
                else
                  startActivity(new Intent(MainActivity.this, Login.class));
              }
              catch(Exception e){
                startActivity(new Intent(MainActivity.this, Login.class));
              }
            }

            @Override
            public void onError(){
              startActivity(new Intent(MainActivity.this, Login.class));
            }
          })).execute();
        }
    }
}
