package id.ac.unpam.absensisiswa;

import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ItemUserAdapter extends ArrayAdapter<ItemUserClass>{

  private Context ctx;

  public ItemUserAdapter(Context ctx, int id_view, ArrayList<ItemUserClass> items){
    super(ctx, id_view, items);
    this.ctx = ctx;
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.user_item, parent, false);
    }

    TextView nama_lengkap = (TextView) convertView.findViewById(R.id.nama_lengkap);
    TextView nomor_induk = (TextView) convertView.findViewById(R.id.nomor_induk);
    TextView username = (TextView) convertView.findViewById(R.id.username);

    ItemUserClass item = getItem(position);
    if (item != null) {
      // Toast.makeText(this.ctx, item.getNamaLengkap() + " " + item.getNomorInduk() + " " + item.getUsername(), Toast.LENGTH_LONG).show();
      nama_lengkap.setText(item.getNamaLengkap());
      nomor_induk.setText(item.getNomorInduk());
      username.setText(item.getUsername());
    }

    return convertView;
  }
}
