package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDFinal;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Pedido;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Producto;
import dmax.dialog.SpotsDialog;

public class ActivityDetalles extends AppCompatActivity {

    AlertDialog dialogo;

    static ListView listaProcductos;
    static TextView precio;
    ImageButton volver;
    Button confirmar;
    TimePicker reloj;

    boolean insertado=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        inflar();

        lanzarAdapter();
        metodosListener();

    }
    /**
     * Infla todos los elementos del layout del activity
     */
    private void inflar() {
        dialogo = new SpotsDialog(this,"Enviado Pedidos...");
        listaProcductos = (ListView)findViewById(R.id.lv_dt);
        precio = (TextView)findViewById(R.id.tv_dt_precio);
        volver = (ImageButton)findViewById(R.id.ib_dt_volver);
        confirmar = (Button)findViewById(R.id.btn_dt_confirmar);
        reloj = (TimePicker)findViewById(R.id.timePicker2);
    }

    /**
     * lanzarAdapter, crea y lanza un adapter en el listView con una lista de los productos del pedido
     */
    static void lanzarAdapter() {
        AdapterDetalles adapta = new AdapterDetalles(BDFinal.pedidosFinal);
        listaProcductos.setAdapter(adapta);
        precioTotal();
    }

    /**
     * precioTotal, calcula el precio total haciendo una suma de los precios de todos los productos
     * del pedido y redondea para sólo 2 decimales máximo
     */
    private static void precioTotal() {
        float pf = 0f;
        for (int i = 0; i < BDFinal.pedidosFinal.size(); i++){
            pf += BDFinal.pedidosFinal.get(i).getPrecio();
        }
        double redondeo = Math.round(pf*100.0)/100.0;
        precio.setText(""+redondeo);

    }

    /**
     * Métodos lístener de los botones
     */
    private void metodosListener() {
        /**
         * Listener volver, carga la Activity anterior para seguir añadiendo cafés
         */
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActivityCafe.class);
                startActivity(i);
            }
        });

        //----------------------------------------------------------------------------------------//
        //----------------------------------------------------------------------------------------//

        /**
         * Listener confirmar, comprueba que hay productos añadidos
         * Realiza un insert en la BBDD con todos los productos de la lista, el usuario y la hora
         * Una vez realizado se borra el array de pedidos y se desabilita el botón para evitar
         * realizar pedidos duplicados
         */
        confirmar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                int hour = 0;
                int minute = 0;
                /**
                 * Los métodos getHour() y getMinute() no funcionan en API inferior a 23, hay que
                 * usar metodos deprecated.
                 */
                if (Build.VERSION.SDK_INT >= 23 ){
                    hour = reloj.getHour();
                    minute = reloj.getMinute();
                }else{
                    hour = reloj.getCurrentHour();
                    minute = reloj.getCurrentMinute();
                }

                Toast.makeText(getApplicationContext(), "" + hour + ":" + minute, Toast.LENGTH_SHORT).show();


                if (BDFinal.pedidosFinal.size()==0)
                    Toast.makeText(getApplicationContext(),"No se han añadido productos.", Toast.LENGTH_SHORT).show();
                else{
                    for (Pedido p : BDFinal.pedidosFinal){
                        dialogo.show();
                        String insert = "insert into pedidos (idProducto, idCliente, complementos,"+
                                "hora, cantidad) values ("+p.getProducto().getNumProducto()+","+
                                ActivityLogin.USER.getId()+", '"+p.getComentarios()+"',"+
                                hour+minute+00+"',"+p.getCantidad()+")";
                        new Insertar(insert, dialogo).execute("");
                    }
                    confirmar.setEnabled(false);
                    BDFinal.pedidosFinal.clear();
                    Toast.makeText(getApplicationContext(),"Pedido realizado con exito.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }//Fin Listener

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public class Insertar extends AsyncTask<String,Void,Void> {

        String consultaDt;
        Connection conexDt;
        Statement sentenciaDt;
        android.app.AlertDialog dialog;
        ResultSet resultDt;

        public Insertar(String consulta, android.app.AlertDialog dialog){
            this.consultaDt=consulta;
            this.dialog=dialog;
        }

        @Override
        protected Void doInBackground(String... params) {

            try {
                conexDt = DriverManager.getConnection("jdbc:mysql://" + ActivityLogin.ip + "/base20171", "ubase20171", "pbase20171");
                sentenciaDt = conexDt.createStatement();
//                resultDt = null;
                publishProgress();

                sentenciaDt.executeUpdate(consultaDt);

            } catch (SQLException e) {
                e.printStackTrace();
            }
//            return resultDt;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                conexDt.close();
                sentenciaDt.close();
//                resultDt.close();
                dialog.dismiss();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

//        @Override
//        protected void onPostExecute(Void... param) {
////            super.onPostExecute(resultSet);
//
//            try{
////                while (resultSet.next()) {
////
////                }
//
//
//                conexDt.close();
//                sentenciaDt.close();
//                resultDt.close();
//                dialog.dismiss();
//
//            }
//            catch (Exception ex)
//            {
//                Log.d("Fallo de cojones","");
//            }
//        }
    }//Fin AsynTack


}//Fin Activity
