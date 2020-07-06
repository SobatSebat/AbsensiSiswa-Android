package id.ac.unpam.absensisiswa;

import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ItemReportAdapter extends ArrayAdapter<ItemReportClass>{

  private Context ctx;

  public ItemReportAdapter(Context ctx, int id_view, ArrayList<ItemReportClass> items){
    super(ctx, id_view, items);
    this.ctx = ctx;
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.report_item, parent, false);
    }

    TextView jam = (TextView) convertView.findViewById(R.id.jam);
    TextView tanggal = (TextView) convertView.findViewById(R.id.tanggal);
    TextView bulan = (TextView) convertView.findViewById(R.id.bulan);
    TextView tahun = (TextView) convertView.findViewById(R.id.tahun);
    TextView kelas = (TextView) convertView.findViewById(R.id.kelas);
    TextView mapel = (TextView) convertView.findViewById(R.id.mapel);

    ItemReportClass item = getItem(position);
    if (item != null) {
      // Toast.makeText(this.ctx, item.getNamaLengkap() + " " + item.getNomorInduk() + " " + item.getUsername(), Toast.LENGTH_LONG).show();
      jam.setText(item.getJam());
      tanggal.setText(item.getTanggal());
      bulan.setText(item.getBulan());
      tahun.setText(item.getTahun());
      kelas.setText(item.getKelas());
      mapel.setText(item.getMapel());
    }

    return convertView;
  }
}
