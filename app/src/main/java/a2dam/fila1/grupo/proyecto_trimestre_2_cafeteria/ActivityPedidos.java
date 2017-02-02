package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.mysql.jdbc.PreparedStatement;

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

public class ActivityPedidos extends AppCompatActivity {

    AlertDialog dialogo;
    ListView listView;
    Button actualizar;

    ArrayList<VistaPedido> vistaPedidos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        updateBBDD();

        actualizar = (Button)findViewById(R.id.btnAPedidos);
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBBDD();
            }
        });

    }//Fin onCreate

    /**
     * Actualiza la base de datos y lanza el adapter5
     */
    private void updateBBDD() {
        dialogo = new SpotsDialog(this, "Cargando pedidos...");
        dialogo.show();

        vistaPedidos.clear();

        String consulta = "select username, hora, sum(precio) as total, estado from pedidos, usuarios " +
                "where id_cli = idCliente group by username, hora order by hora, num_pedido, username, estado, total";
        new ConsultasPedidos(consulta, dialogo).execute();
    }//Fin updateBBDD

    /**
     * Lanza el Adapter del listView con todos los pedidos actuales
     */
    private void lanzarAdapter() {
        listView = (ListView) findViewById(R.id.lvAPedidos);
        listView.setAdapter(new AdapterPedidos(vistaPedidos));
        itemListener();
    }//Fin lanzarAdapter

    private void itemListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogo.show();
                String consulta = "select nom_pro, complementos, cantidad, pedidos.precio, idCliente, " +
                        "username, hora from usuarios, pedidos, productos " +
                        "where usuarios.id_cli = pedidos.idCliente " +
                        "AND productos.id_pro = pedidos.idProducto AND " +
                        "username = '" + vistaPedidos.get(position).getNombre() + "' AND " +
                        "hora = '" + vistaPedidos.get(position).getHora() + "'" +
                        "group by idCliente, hora, num_pedido;";

                new ConsultasPedidos(consulta, dialogo).execute();
            }
        });
    }//Fin itemlistener

    /**
     * Lanza ActivityDetalles con todos los productos del pedido
     * @param usuario
     * @param hora
     */
    private void lanzarDetalles(String usuario, String hora) {
        Intent intent = new Intent(getApplicationContext(), ActivityPedidosDetalles.class);
//        intent.putExtra("USUARIO", usuario);
//        intent.putExtra("HORA", hora);
        startActivity(intent);
    }//Fin lanzarDetalles

    /**
     * Captura la acción de pulsar el botón atrás y vuelve a la pantalla de login
     */
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar Sesión")
                .setMessage("¿Estás seguro de que desea cerrar sesión?")
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        ActivityLogin.loginCorrecto = false;
                        finish();
//                        Intent i = new Intent(getApplicationContext(), ActivityLogin.class);
//                        startActivity(i);
                    }
                }).create().show();
    }//Fin onBackPressed

////////////////////////////////////////////////////////////////////////////////////////////////////

    public class ConsultasPedidos extends AsyncTask<Void,Void,Statement> {

        android.app.AlertDialog dialog;
        String consultaPd;
        Connection conexPd;
        Statement sentenciaPd;
        ResultSet resultPd;

        public ConsultasPedidos(String consulta, android.app.AlertDialog dialog){
            this.consultaPd=consulta;
            this.dialog=dialog;
        }

        @Override
        protected Statement doInBackground(Void... params) {

            try {
                conexPd = DriverManager.getConnection("jdbc:mysql://" + ActivityLogin.ip + "/base20171",
                        "ubase20171", "pbase20171");
                sentenciaPd = conexPd.createStatement();
                resultPd = null;
                publishProgress();

                if (consultaPd.startsWith("select"))
                    resultPd = sentenciaPd.executeQuery(consultaPd);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sentenciaPd;
        }

        @Override
        protected void onPostExecute(Statement sentencia) {
            super.onPostExecute(sentencia);
//            ResultSet resultPd = null;

            try{
                if (consultaPd.contains("sum(precio)")){
                    while (resultPd.next()){
                        vistaPedidos.add(new VistaPedido(resultPd.getString("username"),
                                resultPd.getTime("hora").toString(), resultPd.getFloat("total"),
                                resultPd.getInt("estado")));
                    }
                    lanzarAdapter();
                }

                if (consultaPd.contains("complementos")){
                    int idCli = 0;
                    String usuario = null;
                    String hora = null;

                    while (resultPd.next()){
                         BDFinal.pedidosFinal.add(new Pedido(new Usuario(resultPd.getInt("idCliente"),
                                 resultPd.getString("username")), new Producto(resultPd.getString("nom_pro")),
                                 resultPd.getInt("cantidad"), resultPd.getFloat("precio"),
                                 resultPd.getString("complementos"), resultPd.getString("hora")));
                    }
                    idCli = BDFinal.pedidosFinal.get(0).getUsuario().getId();
                    usuario = BDFinal.pedidosFinal.get(0).getUsuario().getNombre();
                    hora = BDFinal.pedidosFinal.get(0).getHora().trim();

                    String update = "update pedidos set estado = 1 where idCliente = " + idCli +
                            " and hora = '" + hora+ "'";

                    Log.e("ERROR", "Hora: "+hora);

//                    sentenciaPd.executeUpdate(update);
                    lanzarDetalles(usuario, hora);
                }

                conexPd.close();
                sentenciaPd.close();
                resultPd.close();
                dialog.dismiss();

            }catch (Exception ex) { Log.d("Fallo de cojones",""); }
        }
    }//Fin AsynTack

    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     *  ///////////////////////////////////////////////////////////////////////////////////////////
     */
    ///////////////////////////////////////////////////////////////////////////////////////////////

    public class ActualizacionPedidos extends AsyncTask<String,ResultSet,Void> {


        String consultaPd;
        Connection conexPd;
        Statement sentenciaPd;
        ResultSet resultPd;

        public ActualizacionPedidos(String consulta){
            this.consultaPd=consulta;
        }

        @Override
        protected Void doInBackground(String... params)
        {

                try {
                    conexPd = DriverManager.getConnection("jdbc:mysql://" + ActivityLogin.ip + "/base20171",
                            "ubase20171", "pbase20171");
                    sentenciaPd = conexPd.createStatement();
                    resultPd = null;


                    resultPd = sentenciaPd.executeQuery(consultaPd);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                publishProgress(resultPd);
                return null;

        }

        @Override
        protected void onProgressUpdate(ResultSet... resultSet) {
            super.onProgressUpdate(resultSet);
            try{
                while (resultSet[0].next()) {
                    vistaPedidos.add(new VistaPedido(resultSet[0].getString("username"),
                            resultSet[0].getTime("hora").toString(), resultSet[0].getFloat("total"),0));
                }
                lanzarAdapter();
                conexPd.close();
                sentenciaPd.close();
                resultPd.close();


            }catch (Exception ex) { Log.d("Fallo de cojones",""); }
        }

        @Override
        protected void onPostExecute(Void voids)
        {
            super.onPostExecute(voids);



        }
    }//Fin AsynTack
}//Fin Activity
