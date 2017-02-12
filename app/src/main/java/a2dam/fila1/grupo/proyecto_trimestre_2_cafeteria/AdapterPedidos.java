package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDFinal;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDPruebas;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Pedido;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Producto;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Usuario;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.VistaPedido;
import dmax.dialog.SpotsDialog;

/**
 * Created by Raquel.

 * Adapter para el listView de pedidos
 */

public class AdapterPedidos extends BaseAdapter {
//    public static ArrayList<Pedido> pedidos = BDPruebas.pedidos;
    public static ArrayList<VistaPedido> pedidos;

    private View listItemView;
    private TextView tvNombre, tvHora, tvPrecio;
    private ConstraintLayout fondo;

    public AdapterPedidos(ArrayList<VistaPedido> pedidos) {
        this.pedidos = pedidos;
    }
    public AdapterPedidos(){}

    @Override
    public int getCount() {
        return pedidos.size();
    }

    @Override
    public VistaPedido getItem(int i) {
        return pedidos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        listItemView = view;
        if (listItemView == null)
            listItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_pedido, null);

        inflar();

        final String nombre = getItem(i).getNombre();
        final String hora = getItem(i).getHora();

        tvNombre.setText(nombre);
        tvPrecio.setText(getItem(i).getPrecioTotal() + "€");
        tvHora.setText(hora);

        switch (getItem(i).getEstado()){
            case 1: fondo.setBackgroundColor(0xffe1b1b1);
                break;
            case 2: fondo.setBackgroundColor(0xff90d78f);
                break;
            default:
        }

        return listItemView;
    }

    private void inflar() {
        tvNombre = (TextView) listItemView.findViewById(R.id.tvListPedidosNombre);
        tvHora = (TextView) listItemView.findViewById(R.id.tvListPedidosHora);
        tvPrecio = (TextView) listItemView.findViewById(R.id.tvListPedidosPrecio);
        fondo = (ConstraintLayout) listItemView.findViewById(R.id.layout);
    }

}//Fin Adapter
