package id.ac.unpam.absensisiswa;

import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ItemDaftarAdapter extends ArrayAdapter<ItemDaftarClass>{

  private Context ctx;

  public ItemDaftarAdapter(Context ctx, int id_view, ArrayList<ItemDaftarClass> items){
    super(ctx, id_view, items);
    this.ctx = ctx;
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.daftar_item, parent, false);
    }

    TextView nama_lengkap = (TextView) convertView.findViewById(R.id.nama_lengkap);
    TextView nama_kelas = (TextView) convertView.findViewById(R.id.nama_kelas);
    TextView nama_mapel = (TextView) convertView.findViewById(R.id.nama_mapel);

    ItemDaftarClass item = getItem(position);
    if (item != null) {
      // Toast.makeText(this.ctx, item.getNamaLengkap() + " " + item.getNomorInduk() + " " + item.getUsername(), Toast.LENGTH_LONG).show();
      nama_lengkap.setText(item.getNamaLengkap());
      nama_kelas.setText(item.getNamaKelas());
      nama_mapel.setText(item.getNamaMapel());
    }

    return convertView;
  }
}
