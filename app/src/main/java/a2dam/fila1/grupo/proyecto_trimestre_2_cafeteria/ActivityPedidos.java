package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDFinal;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDPruebas;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Producto;
import dmax.dialog.SpotsDialog;

public class ActivityPedidos extends AppCompatActivity {

    AlertDialog dialogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

//        dialogo = new SpotsDialog(this, "Cargando pedidos...");
//        dialogo.show();
//
//        String consulta = "select username, hora, sum(precio) as total ";
//        new ConsultasPedidos(consulta, dialogo);

        lanzarAdapter();


    }

    private void lanzarAdapter() {
        ListView listView = (ListView) findViewById(R.id.lvAPedidos);
        listView.setAdapter(new AdapterPedidos(BDPruebas.pedidos));
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

    public class ConsultasPedidos extends AsyncTask<String,Void,ResultSet> {

        String consultaPd;
        Connection conexPd;
        Statement sentenciaPd;
        android.app.AlertDialog dialog;
        ResultSet resultPd;

        public ConsultasPedidos(String consulta, android.app.AlertDialog dialog){
            this.consultaPd=consulta;
            this.dialog=dialog;
        }

        @Override
        protected ResultSet doInBackground(String... params) {

            try {
                conexPd = DriverManager.getConnection("jdbc:mysql://" + ActivityLogin.ip + "/base20171", "ubase20171", "pbase20171");
                sentenciaPd = conexPd.createStatement();
                resultPd = null;
                publishProgress();

//                resultPd = sentenciaPd.executeQuery(consultaPd);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return resultPd;
        }

        @Override
        protected void onPostExecute(ResultSet resultSet) {
            super.onPostExecute(resultSet);

            try{
//                while (resultSet.next()) {
//
//
//                }
                lanzarAdapter();

                conexPd.close();
                sentenciaPd.close();
                resultPd.close();
                dialog.dismiss();

            }
            catch (Exception ex)
            {
                Log.d("Fallo de cojones","");
            }
        }
    }//Fin AsynTack
}//Fin Activity
