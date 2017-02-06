package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDFinal;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Pedido;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Email.SendMail;
import dmax.dialog.SpotsDialog;

public class ActivityPedidosDetalles extends AppCompatActivity {
    AlertDialog dialogo;
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

    private void inflar() {
        dialogo     = new SpotsDialog(this,  "Termiando pedidos...");

        listView    = (ListView) findViewById(R.id.lvAPedidosD);

        tvNombre    = (TextView) findViewById(R.id.tvAPedidosDNomCli);
        tvHora      = (TextView) findViewById(R.id.tvAPedidosDHora);
        precioT     = (TextView) findViewById(R.id.tvAPedidosDPrecioT);

        fab         = (FloatingActionButton) findViewById(R.id.fab_done);

        tvNombre    .setText(BDFinal.pedidosFinal.get(0).getUsuario().getNombre());
        tvHora      .setText(BDFinal.pedidosFinal.get(0).getHora());
        precioT     .setText(calcularPrecio() + " €");
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        BDFinal.pedidosFinal.clear();
        finish();
    }

    private void listeners() {
        listView.setAdapter(new AdapterPedidosDetalles(BDFinal.pedidosFinal));

        /**
         * Manda mail al usuario con el pedido preparado y actualiza el estado a preparado
         * Una vez enviado el mail al pulsar se vuelve a la pantalla de pedidos
         */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mail){
                    String message = "Pedido preparado para las " + BDFinal.pedidosFinal.get(0).getHora()
                            + " con un precio total de " + precioT.getText();
                    SendMail sendEmail = new SendMail(ActivityPedidosDetalles.this,
                            BDFinal.pedidosFinal.get(0).getUsuario().getMail(),
                            "Pedido C@fetería", message);

                    /**
                     * En la red del istituto falla por temas de seguridad de la red,
                     * comentar la linea y ya
                     */
                    sendEmail.execute();//ejecuta el AsynTask  //Descomentar esta liena para mail
                    /**
                     * /////////////////////////////////////////////////////////////////////////////
                     */
                    fab.setImageResource(R.drawable.ic_done);
                    mail = true;

                    dialogo.show();
                    String update = "update pedidos set estado = 2 where idCliente = " +
                            BDFinal.pedidosFinal.get(0).getUsuario().getId() +
                            " and hora = '" + BDFinal.pedidosFinal.get(0).getHora() + "'";
                    new UpdatePedido(update, dialogo).execute();
                }else{
                    mail = false;
                    onBackPressed();
                }

            }
        });
    }



    private float calcularPrecio() {
        float p = 0;
        for (Pedido pd : BDFinal.pedidosFinal){
            p += pd.getPrecio();
        }
        return p;
    }

    public class UpdatePedido extends AsyncTask<Void,Void,Statement> {

        String consultaPDt;
        Connection conexPDt;
        Statement sentenciaPDt;
        android.app.AlertDialog dialog;

        public UpdatePedido(String consulta, android.app.AlertDialog dialog){
            this.consultaPDt=consulta;
            this.dialog=dialog;
        }

        @Override
        protected Statement doInBackground(Void... params) {

            try {
                conexPDt = DriverManager.getConnection("jdbc:mysql://" + ActivityLogin.ip + "/base20171", "ubase20171", "pbase20171");
                sentenciaPDt = conexPDt.createStatement();
                publishProgress();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sentenciaPDt;
        }

        @Override
        protected void onPostExecute(Statement statement) {
            super.onPostExecute(statement);
            try {
                sentenciaPDt.executeUpdate(consultaPDt);
                conexPDt.close();
                sentenciaPDt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        }
    }//Fin AsynTack
}//Fin Activity
