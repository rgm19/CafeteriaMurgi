package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDPruebas;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Pedido;

/**
 * Created by Raquel.

 * Adapter para el listView de pedidos
 */

public class AdapterPedidos extends BaseAdapter {
//    public static ArrayList<Pedido> pedidos = BDPruebas.pedidos;
    public static ArrayList<Pedido> pedidos;

    private View listItemView;
    private TextView tvNombre, tvHora, tvPrecio;

    public AdapterPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
    public AdapterPedidos(){}

    @Override
    public int getCount() {
        return pedidos.size();
    }

    @Override
    public Pedido getItem(int i) {
        return pedidos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        listItemView = view;
        if (listItemView == null)
            listItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_pedido, null);

        inflar();

        tvNombre.setText(getItem(i).getUsuario().getNombre());
        tvPrecio.setText(getItem(i).getPrecio() + "€");
        tvHora.setText(getItem(i).getHora());


        // Si se pulsa la vista iremos a detalles del pedido.
        // En el proceso envía el id del cliente
        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = view.getContext();
                Intent intent = new Intent(context, ActivityPedidosDetalles.class);
                intent.putExtra("id", pedidos.get(i).getUsuario().getId());
                context.startActivity(intent);
            }
        });

        return listItemView;
    }

    private void inflar() {
        tvNombre = (TextView) listItemView.findViewById(R.id.tvListPedidosNombre);
        tvHora = (TextView) listItemView.findViewById(R.id.tvListPedidosHora);
        tvPrecio = (TextView) listItemView.findViewById(R.id.tvListPedidosPrecio);
    }
}
