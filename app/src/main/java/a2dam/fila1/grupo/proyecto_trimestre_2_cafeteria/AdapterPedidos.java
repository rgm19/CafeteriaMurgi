package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Raquel.

 * Adapter para el listView de pedidos
 */

public class AdapterPedidos extends BaseAdapter {
    View listItemView;


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        listItemView = view;
        if (listItemView == null)
            listItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_pedidos, null);


        return listItemView;
    }

    private void inflar() {

    }
}
