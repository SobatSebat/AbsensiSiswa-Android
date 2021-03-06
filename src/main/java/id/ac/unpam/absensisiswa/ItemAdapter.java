package id.ac.unpam.absensisiswa;

import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ItemAdapter extends ArrayAdapter<ItemClass>{

  private boolean simple = false;

  public ItemAdapter(Context ctx, int id_view, ArrayList<ItemClass> items){
    super(ctx, id_view, items);
  }

  public ItemAdapter(Context ctx, int id_view, ArrayList<ItemClass> items, boolean simple){
    super(ctx, id_view, items);
    this.simple = simple;
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = LayoutInflater.from(this.getContext()).inflate(simple ? R.layout.simple_item : android.R.layout.simple_spinner_item, parent, false);
    }

    TextView itemView = (TextView) convertView.findViewById(simple ? R.id.text1 : android.R.id.text1);

    ItemClass item = getItem(position);
    if (item != null) {
      itemView.setText(item.getTxt());
    }

    return convertView;
  }

  public View getDropDownView(int position, View convertView, ViewGroup parent){
    if (convertView == null) {
      convertView = LayoutInflater.from(this.getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
    }

    TextView itemView = (TextView) convertView.findViewById(android.R.id.text1);

    ItemClass item = getItem(position);
    if (item != null) {
      itemView.setText(item.getTxt());
    }

    return convertView;
  }
}
