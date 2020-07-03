package id.ac.unpam.absensisiswa;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Setting {
  public static final int LEVEL_ADMIN = 0;
  public static final int LEVEL_GURU = 1;
  public static final int LEVEL_SISWA = 2;


  public static final int BY_ID = 0;
  public static final int BY_KELAS = 1;
  public static final int BY_MAPEL = 1;
  public static final int BY_USER = 2;

  public String KEY = "id.ac.unpam.absensisiswa.sharedpreferences";
  private Context context;

  public SharedPreferences sharedPref;
	public Editor editor;

  public Setting(Context ctx){
    this.context = ctx;
    this.sharedPref = ctx.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    this.editor = sharedPref.edit();
  }

  public String getToken(){
    return sharedPref.getString("token", "");
  }

  public void setToken(String token){
    editor.putString("token", token);
    editor.commit();
  }

  public int getId(){
    return sharedPref.getInt("id", 0);
  }

  public void setId(int id){
    editor.putInt("id", id);
    editor.commit();
  }

  public String getServer(){
    return sharedPref.getString("server", "");
  }

  public void setServer(String server){
    editor.putString("server", server);
    editor.commit();
  }

  public String getPort(){
    return sharedPref.getString("port", "");
  }

  public void setPort(String port){
    editor.putString("port", port);
    editor.commit();
  }

  public String getServerPort(){
    return "http://" + getServer() + ":" + getPort();
  }

  public void setServerPort(String server, String port){
    setServer(server);
    setPort(port);
  }

  public String getUrlLogin(){
    return getServerPort() + "/api/login";
  }

  public String getUrlId(){
    return getServerPort() + "/api/" + getToken() + "/id";
  }

  public String getUrlTokenCheck(){
    return getServerPort() + "/api/" + getToken() + "/check";
  }

  public String getUrlLabel(){
    return getServerPort() + "/api/" + getToken() + "/label";
  }

  public String getUrlKelas(){
    return getServerPort() + "/api/" + getToken() + "/kelas";
  }

  public String getUrlKelas(int id){
    return getServerPort() + "/api/" + getToken() + "/kelas/" + id;
  }

  public String getUrlKelas(String id){
    return getServerPort() + "/api/" + getToken() + "/kelas/" + id;
  }

  public String getUrlMapel(){
    return getServerPort() + "/api/" + getToken() + "/mapel";
  }

  public String getUrlMapel(int id){
    return getServerPort() + "/api/" + getToken() + "/mapel/" + id;
  }

  public String getUrlMapel(String id){
    return getServerPort() + "/api/" + getToken() + "/mapel/" + id;
  }

  public String getUrlKMUGuruSiswa(int kelas_id, int mapel_id){
    return getServerPort() + "/api/" + getToken() + "/kmu/guru_siswa/" + kelas_id + "/" + mapel_id;
  }

  public String getUrlKMUGuru(int kelas_id, int mapel_id){
    return getServerPort() + "/api/" + getToken() + "/kmu/guru/" + kelas_id + "/" + mapel_id;
  }

  public String getUrlKMUSiswa(int kelas_id, int mapel_id){
    return getServerPort() + "/api/" + getToken() + "/kmu/siswa/" + kelas_id + "/" + mapel_id;
  }

  public String getUrlKMUGuruSiswaNotOn(int kelas_id, int mapel_id){
    return getServerPort() + "/api/" + getToken() + "/kmu/guru_siswa/noton/" + kelas_id + "/" + mapel_id;
  }

  public String getUrlKMUGuruNotOn(int kelas_id, int mapel_id){
    return getServerPort() + "/api/" + getToken() + "/kmu/guru/noton/" + kelas_id + "/" + mapel_id;
  }

  public String getUrlKMUSiswaNotOn(int kelas_id, int mapel_id){
    return getServerPort() + "/api/" + getToken() + "/kmu/siswa/noton/" + kelas_id + "/" + mapel_id;
  }

  public String getUrlKMUKelas(int user_id){
    return getServerPort() + "/api/" + getToken() + "/kmu/kelas/" + user_id;
  }

  public String getUrlKMUMapel(int kelas_id){
    return getServerPort() + "/api/" + getToken() + "/kmu/mapel/" + kelas_id;
  }

  public String getUrlKMUMapel(int user_id, int kelas_id){
    return getServerPort() + "/api/" + getToken() + "/kmu/mapel/" + user_id + "/" + kelas_id;
  }

  public String getUrlCreateKelas(){
    return getServerPort() + "/api/" + getToken() + "/create/kelas";
  }

  public String getUrlCreateMapel(){
    return getServerPort() + "/api/" + getToken() + "/create/mapel";
  }

  public String getUrlCreateGuru(){
    return getServerPort() + "/api/" + getToken() + "/create/guru";
  }

  public String getUrlCreateSiswa(){
    return getServerPort() + "/api/" + getToken() + "/create/siswa";
  }

  public String getUrlCreateKMU(){
    return getServerPort() + "/api/" + getToken() + "/create/kmu";
  }

}
