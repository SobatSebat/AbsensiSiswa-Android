package id.ac.unpam.absensisiswa;

public class ItemUserClass {
  private int id;
  private String nama_lengkap;
  private String nomor_induk;
  private String username;

  public ItemUserClass(int id, String nama_lengkap, String nomor_induk, String username){
    this.id = id;
    this.nama_lengkap = nama_lengkap;
    this.nomor_induk = nomor_induk;
    this.username = username;
  }

  public int getId(){
    return this.id;
  }

  public void setId(int id){
    this.id = id;
  }

  public String getNamaLengkap(){
    return this.nama_lengkap;
  }

  public void setNamaLengkap(String nama_lengkap){
    this.nama_lengkap = nama_lengkap;
  }

  public String getNomorInduk(){
    return this.nomor_induk;
  }

  public void setNomorInduk(String nomor_induk){
    this.nomor_induk = nomor_induk;
  }

  public String getUsername(){
    return this.username;
  }

  public void setUsername(String username){
    this.username = username;
  }
}
