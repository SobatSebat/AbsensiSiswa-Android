package id.ac.unpam.absensisiswa.utils;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Toast;
import android.content.Context;
import android.util.Log;

public class CoreAPI extends AsyncTask<Void, Void, Void> {
  private Context context = null;
  private String url = null;
  private boolean post = false;
  private String params = null;
  private HandlerAPI handlerAPI = null;
  private String output = "";

  public CoreAPI(Context ctx, String url, HandlerAPI h){
    super();
    this.context = ctx;
    this.url = url;
    this.handlerAPI = h;
  }

  public CoreAPI(Context ctx, String url, String params, HandlerAPI h){
    super();
    this.context = ctx;
    this.url = url;
    this.post = true;
    this.params = params;
    this.handlerAPI = h;
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
  }

  @Override
  protected Void doInBackground(Void... arg0) {
    HttpService hs = new HttpService(this.url);

    if(!this.post){
      //Toast.makeText(this.context, hs.GET(), Toast.LENGTH_LONG).show()
      this.output = hs.GET();
    }
    else{
      //Toast.makeText(this.context, hs.POST(this.params), Toast.LENGTH_LONG).show();
      this.output = hs.POST(this.params);
    }

    return null;
  }

  @Override
  protected void onPostExecute(Void result) {
    super.onPostExecute(result);
    try {
      this.handlerAPI.onReceive(new JSONObject(this.output));
    }
    catch(Exception e){
      this.handlerAPI.onError();
    }
  }
}
