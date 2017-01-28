package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDFinal;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDPruebas;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Pedido;

public class ActivityPedidosDetalles extends AppCompatActivity {
    public static int id;
    private ListView listView;
    private TextView tvNombre, tvHora;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_detalles);

        inflar();

        id = getIntent().getIntExtra("id", 0);

        tvNombre.setText(BDPruebas.pedidos.get(id).getUsuario().getNombre());
        tvHora.setText(BDPruebas.pedidos.get(id).getHora());

        listView.setAdapter(new AdapterPedidosDetalles());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Implementar
            }
        });
    }

    private void inflar() {
        listView = (ListView) findViewById(R.id.lvAPedidosD);
        tvNombre = (TextView) findViewById(R.id.tvAPedidosDNomCli);
        tvHora = (TextView) findViewById(R.id.tvAPedidosDHora);
        fab = (FloatingActionButton) findViewById(R.id.fab_done);
    }

    /**
     * Este método sirve para coger todos los productos pedidos
     * (que estarán en diferentes posiciones del arrayList de pedidos)
     * en un mismo arrayList de pedidos.
     *
     * @return devuelve un arrayList con todos los productos pedidos por un usuario.
     * */
    public static ArrayList<Pedido> productosPedidos() {
        ArrayList<Pedido> pedido = new ArrayList<>();
        for (int i = 0; i < BDPruebas.pedidos.size(); i++) {
            if (BDPruebas.pedidos.get(i).getUsuario().getId() == id)
                pedido.add(BDPruebas.pedidos.get(i));
        }
        return pedido;
    }


}
