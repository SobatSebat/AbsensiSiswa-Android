package id.ac.unpam.absensisiswa;

public class ItemClass {
  private int id;
  private String txt;

  public ItemClass(int id, String txt){
    this.id = id;
    this.txt = txt;
  }

  public int getId(){
    return this.id;
  }

  public String getTxt(){
    return this.txt;
  }

  public void getId(int id){
    this.id = id;
  }

  public void getTxt(String txt){
    this.txt = txt;
  }
}
