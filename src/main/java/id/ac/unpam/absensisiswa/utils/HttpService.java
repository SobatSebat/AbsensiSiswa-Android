package id.ac.unpam.absensisiswa.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.util.Log;

public class HttpService {
  private String link = null;
  private URL url = null;
  private HttpURLConnection connection = null;
  public HttpService(String url){
    this.link = url;
  }

  public String GET(){
    try {
      this.url = new URL(this.link);
      this.connection = (HttpURLConnection) this.url.openConnection();
      this.connection.setReadTimeout(15000);
      this.connection.setConnectTimeout(15000);
      this.connection.setRequestMethod("GET");
      this.connection.setDoInput(true);


      if(this.connection.getResponseCode() != 200){
        return null;
      }

      InputStream is = new BufferedInputStream(this.connection.getInputStream());
      return this.convertToString(is);
    }
    catch(Exception e){
      return null;
    }
  }

  public String POST(String params){
    try {
      this.url = new URL(this.link);
      this.connection = (HttpURLConnection) this.url.openConnection();
      this.connection.setReadTimeout(15000);
      this.connection.setConnectTimeout(15000);
      this.connection.setRequestMethod("POST");
      this.connection.setDoInput(true);
      this.connection.setDoOutput(true);

      OutputStream os = this.connection.getOutputStream();
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
      writer.write(params);
      writer.flush();
      writer.close();
      os.close();

      if(this.connection.getResponseCode() != 200){
        return null;
      }

      InputStream is = new BufferedInputStream(this.connection.getInputStream());
      return this.convertToString(is);
    }
    catch(Exception e){

      Log.e("AyamHTTP", e.getMessage());

      return null;
    }
  }

  private String convertToString(InputStream is){
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();

    char[] data = new char[409600];
    try {
      int off = 0;
      while((off += reader.read(data, off, 409600)) != 0){
        sb.append(new String(data, 0, off));
      }
    }
    catch(Exception e){
      try {
        is.close();
      }
      catch(Exception ee){
        return null;
      }
    }

    try {
      is.close();
    }
    catch(Exception e){

        Log.e("Ayam HTTP", "FFFFFFFFFFFFFFFFFFFFFFF");
      return null;
    }

    return sb.toString();
  }
}
