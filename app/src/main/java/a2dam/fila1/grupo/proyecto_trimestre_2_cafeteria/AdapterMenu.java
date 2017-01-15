package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDPruebas;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Producto;

/**
 * Created by Gand on 15/01/17.
 */

public class AdapterMenu extends BaseAdapter {
    private ArrayList<Producto>productos;

    public AdapterMenu(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int position) {
        return productos.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View vista, ViewGroup parent) {
        if (vista==null){
            Context cnt=parent.getContext();
            vista= LayoutInflater.from(cnt).inflate(R.layout.list_producto,null);
        }
        final Producto currentProducto= productos.get(position);

        TextView nombre=(TextView)parent.findViewById(R.id.tv_lp_Nombre);
        nombre.setText(""+currentProducto.getNombre());
        TextView precio=(TextView)parent.findViewById(R.id.tv_lp_Precio);
        precio.setText(""+currentProducto.getPrecio()+" €");



        return vista;
    }
}
