package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Pedido;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.R;

/**
 * Created by Gand on 20/01/17.
 */

public class AdapterDetalles extends BaseAdapter {

    private ArrayList<Pedido> pedidos;

    public AdapterDetalles(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public int getCount() {
        return pedidos.size();
    }

    @Override
    public Object getItem(int position) {
        return pedidos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View vista, ViewGroup parent) {
        if (vista==null){
            Context cnt=parent.getContext();
            vista= LayoutInflater.from(cnt).inflate(R.layout.list_producto,null);
        }
        final Pedido pedidoActual=pedidos.get(position);

        ((TextView)vista.findViewById(R.id.tv_dt_list_nombre)).setText(pedidoActual.getProducto().getNombre());
        ((TextView)vista.findViewById(R.id.tv_dt_list_precio)).setText(""+pedidoActual.getPrecio());
        ((TextView)vista.findViewById(R.id.tv_dt_list_cantidad)).setText(""+pedidoActual.getCantidad());
        ((TextView)vista.findViewById(R.id.tv_dt_list_comentarios)).setText(pedidoActual.getComentarios());


        return vista;
    }
}
