package id.ac.unpam.absensisiswa.utils;

import org.json.JSONObject;

public abstract class HandlerAPI {
  public abstract void onReceive(JSONObject data);
  public abstract void onError();
}
