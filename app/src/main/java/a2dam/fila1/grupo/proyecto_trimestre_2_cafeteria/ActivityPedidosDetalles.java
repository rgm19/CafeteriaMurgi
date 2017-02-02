package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Email.SendMail;

public class ActivityPedidosDetalles extends AppCompatActivity {
    private static boolean mail = false;
    private ListView listView;
    private TextView tvNombre, tvHora, precioT;
    private FloatingActionButton fab;
//    private String email="mdam2015mdam@gmail.com";//Destino
//    private String message="holamundo";//Mensaje
//    private String subject="hola mundo";//Asunto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_detalles);

        inflar();
        listeners();//Todos los listener

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        BDFinal.pedidosFinal.clear();
        finish();
    }

    private void listeners() {
        listView.setAdapter(new AdapterPedidosDetalles(BDFinal.pedidosFinal));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mail){
                    String message = "Pedido preparado para las " + BDFinal.pedidosFinal.get(0).getHora()
                            + " con un precio total de " + precioT.getText();
                    SendMail sendEmail = new SendMail(ActivityPedidosDetalles.this,
                             "zaken85@gmail.com",
                            "Pedido C@fetería", message);
                    sendEmail.execute();//ejecuta el AsynTask
                    fab.setImageResource(R.drawable.ic_done);
                    mail = true;
                    /*BDFinal.pedidosFinal.get(0).getUsuario().getMail()*/
                }else{
                    mail = false;
                    onBackPressed();
                }

            }
        });
    }

    private void inflar() {
        listView = (ListView) findViewById(R.id.lvAPedidosD);
        tvNombre = (TextView) findViewById(R.id.tvAPedidosDNomCli);
        tvHora = (TextView) findViewById(R.id.tvAPedidosDHora);
        precioT = (TextView) findViewById(R.id.tvAPedidosDPrecioT);
        fab = (FloatingActionButton) findViewById(R.id.fab_done);
        tvNombre.setText(BDFinal.pedidosFinal.get(0).getUsuario().getNombre());
        tvHora.setText(BDFinal.pedidosFinal.get(0).getHora());

        precioT.setText(calcularPrecio() + " €");
    }

    private float calcularPrecio() {
        float p = 0;
        for (Pedido pd : BDFinal.pedidosFinal){
            p += pd.getPrecio();
        }
        return p;
    }

//    /**
//     * Este método sirve para coger todos los productos pedidos
//     * (que estarán en diferentes posiciones del arrayList de pedidos)
//     * en un mismo arrayList de pedidos.
//     *
//     * @return devuelve un arrayList con todos los productos pedidos por un usuario.
//     * */
//    public static ArrayList<Pedido> productosPedidos() {
//        ArrayList<Pedido> pedido = new ArrayList<>();
//        for (int i = 0; i < BDPruebas.pedidos.size(); i++) {
//            if (BDPruebas.pedidos.get(i).getUsuario().getId() == id)
//                pedido.add(BDPruebas.pedidos.get(i));
//        }
//        return pedido;
//    }


}
