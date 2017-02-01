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
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDFinal;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDPruebas;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Producto;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.VistaPedido;
import dmax.dialog.SpotsDialog;

public class ActivityPedidos extends AppCompatActivity {

    AlertDialog dialogo;
    ListView listView;

    ArrayList<VistaPedido> vistaPedidos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        dialogo = new SpotsDialog(this, "Cargando pedidos...");
        dialogo.show();

        String consulta = "select username, hora, sum(precio) as total from pedidos, usuarios " +
                "where id_cli = idCliente group by idCliente, hora order by hora, num_pedido, idCliente";
        new ConsultasPedidos(consulta, dialogo).execute();

    }//Fin onCreate


    private void lanzarAdapter() {
        listView = (ListView) findViewById(R.id.lvAPedidos);
        listView.setAdapter(new AdapterPedidos(vistaPedidos));
    }//Fin lanzarAdapter

    private void itemListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String consulta = "Select ";
            }
        });
    }

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
                        Intent i = new Intent(getApplicationContext(), ActivityLogin.class);
                        startActivity(i);
                    }
                }).create().show();
    }//Fin onBackPressed

////////////////////////////////////////////////////////////////////////////////////////////////////

    public class ConsultasPedidos extends AsyncTask<Void,Void,ResultSet> {

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
        protected ResultSet doInBackground(Void... params) {

            try {
                conexPd = DriverManager.getConnection("jdbc:mysql://" + ActivityLogin.ip + "/base20171",
                        "ubase20171", "pbase20171");
                sentenciaPd = conexPd.createStatement();
                resultPd = null;
                publishProgress();

                resultPd = sentenciaPd.executeQuery(consultaPd);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return resultPd;
        }

        @Override
        protected void onPostExecute(ResultSet resultSet) {
            super.onPostExecute(resultSet);

            try{
                if (consultaPd.contains("sum(precio)")){
                while (resultSet.next()) {
                    vistaPedidos.add(new VistaPedido(resultSet.getString("username"),
                            resultSet.getTime("hora").toString(), resultSet.getFloat("total"),
                            0));
                }
                lanzarAdapter();}
                if (consultaPd.contains(""))

                conexPd.close();
                sentenciaPd.close();
                resultPd.close();
                dialog.dismiss();

            }catch (Exception ex) { Log.d("Fallo de cojones",""); }

        }
    }//Fin AsynTack



    public class ActualizacionPedidos extends AsyncTask<String,ResultSet,Void> {


        String consultaPd;
        Connection conexPd;
        Statement sentenciaPd;
        ResultSet resultPd;

        public ActualizacionPedidos(String consulta){
            this.consultaPd=consulta;
        }

        @Override
        protected Void doInBackground(String... params) {

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
        }

        @Override
        protected void onProgressUpdate(ResultSet... resultSet) {
            super.onProgressUpdate(resultSet);
            try{
                while (resultSet[0].next()) {
                    vistaPedidos.add(new VistaPedido(resultSet.getString("username"),
                            resultSet.getTime("hora").toString(), resultSet.getFloat("total")));
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
