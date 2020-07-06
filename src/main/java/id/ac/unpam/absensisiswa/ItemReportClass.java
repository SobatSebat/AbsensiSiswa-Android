package id.ac.unpam.absensisiswa;

public class ItemReportClass {
  private int id;
  private String jam;
  private String tanggal;
  private String bulan;
  private String tahun;
  private String kelas;
  private String mapel;

  public ItemReportClass(int id, String jam, String tanggal, String bulan, String tahun, String kelas, String mapel){
    this.id = id;
    this.jam = jam;
    this.tanggal = tanggal;
    this.bulan = bulan;
    this.tahun = tahun;
    this.kelas = kelas;
    this.mapel = mapel;
  }

  public int getId(){
    return this.id;
  }

  public void setId(int id){
    this.id = id;
  }

  public String getJam(){
    return this.jam;
  }

  public void setJam(String jam){
    this.jam = jam;
  }

  public String getTanggal(){
    return this.tanggal;
  }

  public void setTanggal(String tanggal){
    this.tanggal = tanggal;
  }

  public String getBulan(){
    return this.bulan;
  }

  public void setBulan(String bulan){
    this.bulan = bulan;
  }

  public String getTahun(){
    return this.tahun;
  }

  public void setTahun(String tahun){
    this.tahun = tahun;
  }

  public String getKelas(){
    return this.kelas;
  }

  public void setKelas(String kelas){
    this.kelas = kelas;
  }

  public String getMapel(){
    return this.mapel;
  }

  public void setMapel(String mapel){
    this.mapel = mapel;
  }
}
