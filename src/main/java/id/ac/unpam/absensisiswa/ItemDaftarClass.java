package id.ac.unpam.absensisiswa;

public class ItemDaftarClass {
  private int id;
  private String nama_lengkap;
  private String nama_kelas;
  private String nama_mapel;

  public ItemDaftarClass(int id, String nama_lengkap, String nama_kelas, String nama_mapel){
    this.id = id;
    this.nama_lengkap = nama_lengkap;
    this.nama_kelas = nama_kelas;
    this.nama_mapel = nama_mapel;
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

  public String getNamaKelas(){
    return this.nama_kelas;
  }

  public void setNamaKelas(String nama_kelas){
    this.nama_kelas = nama_kelas;
  }

  public String getNamaMapel(){
    return this.nama_mapel;
  }

  public void setNamaMapel(String nama_mapel){
    this.nama_mapel = nama_mapel;
  }
}
