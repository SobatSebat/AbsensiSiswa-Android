package id.ac.unpam.absensisiswa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.content.Context;
import android.content.Intent;

import android.widget.Toast;

import id.ac.unpam.absensisiswa.utils.CoreAPI;
import id.ac.unpam.absensisiswa.utils.HandlerAPI;

import org.json.JSONObject;

public class Login extends AppCompatActivity {
    /** Called when the activity is first created. */

    private Button login;
    private EditText server;
    private EditText port;
    private EditText username;
    private EditText password;
    private TextView test_out;
    private Setting setting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setting = new Setting(this);

        setContentView(R.layout.login_activity);

        login = (Button)findViewById(R.id.login);
        server = (EditText)findViewById(R.id.server);
        port = (EditText)findViewById(R.id.port);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        test_out = (TextView)findViewById(R.id.test_out);

        login.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View v){
            setting.setServerPort(server.getText().toString(), port.getText().toString());
            //Toast.makeText(Login.this, setting.getUrlLogin(), Toast.LENGTH_LONG).show();
            CoreAPI capi = new CoreAPI((Context) Login.this, setting.getUrlLogin(), "username=" + username.getText() + "&password=" + password.getText(), new HandlerAPI(){
              @Override
              public void onReceive(JSONObject data){
                try{
                  if(data.getBoolean("success")){
                    setting.setToken(data.getString("token"));
                    setting.setId(data.getInt("id"));
                    startActivity(new Intent(Login.this, MenuAbsensi.class));
                  }
                  else{
                    test_out.setText("Login Error!!!!!!!!!!!!!");
                  }
                }
                catch(Exception e){
                  test_out.setText("Error!!!!!!!!!!!!!!");
                }
              }

              @Override
              public void onError(){
                test_out.setText("Error!!!!!!!!!!!!!!");
              }
            });
            capi.execute();
          }
        });
    }
}
