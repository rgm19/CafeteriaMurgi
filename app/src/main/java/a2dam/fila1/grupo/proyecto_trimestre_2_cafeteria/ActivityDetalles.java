package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDFinal;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Pedido;
import dmax.dialog.SpotsDialog;

public class ActivityDetalles extends AppCompatActivity {

    AlertDialog dialogo;

    static ListView listaProcductos;
    static TextView precio;
    ImageButton volver;
    Button confirmar;
    TimePicker reloj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        if((this.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                <= Configuration.SCREENLAYOUT_SIZE_LARGE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        inflar();

        lanzarAdapter();
        listenerBotones();

    }
    /**
     * Infla todos los elementos del layout del activity
     */
    private void inflar() {
        dialogo          = new SpotsDialog(this,"Enviado Pedidos...");
        listaProcductos  = (ListView)findViewById(R.id.lv_dt);
        precio           = (TextView)findViewById(R.id.tv_dt_precio);
        volver           = (ImageButton)findViewById(R.id.ib_dt_volver);
        confirmar        = (Button)findViewById(R.id.btn_dt_confirmar);
        reloj            = (TimePicker)findViewById(R.id.timePicker2);
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
        float precioFinal = 0f;
        for (int i = 0; i < BDFinal.pedidosFinal.size(); i++){
            precioFinal += BDFinal.pedidosFinal.get(i).getPrecio();
        }
        double redondeo = Math.round(precioFinal*100.0)/100.0;
        precio.setText("" + redondeo);
    }

    /**
     * Listener de los botones de la activity
     */
    private void listenerBotones() {
        volver.setOnClickListener(new View.OnClickListener() {//Boton volver
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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

                //Detecta la API que se esta ejecutando, si es menor que 23 se usan metodos deprecated para obtener la hora actual
                if (Build.VERSION.SDK_INT >= 23 ){
                    hour = reloj.getHour();
                    minute = reloj.getMinute();
                }else{
                    hour = reloj.getCurrentHour();
                    minute = reloj.getCurrentMinute();
                }
                int hora = hour * 10000 + minute * 100;


                if (BDFinal.pedidosFinal.size()==0)
                    Toast.makeText(getApplicationContext(),"No se han añadido productos.", Toast.LENGTH_SHORT).show();
                else{
                    for (Pedido p : BDFinal.pedidosFinal){
                        dialogo.show();

                        String insert = "insert into pedidos (idProducto, idCliente, complementos, hora, cantidad, precio, estado) "
                                + "values (" + p.getProducto().getNumProducto() + ","
                                + ActivityLogin.USER.getId() + ", '" + p.getComentarios() + "', "
                                + "'" + hora + "', " + p.getCantidad() + ", "
                                + p.getPrecio() + ", " + 0 + ");";

                        new Insertar(insert, dialogo).execute();
                    }
                    confirmar.setEnabled(false);
                    BDFinal.pedidosFinal.clear();
                    Toast.makeText(getApplicationContext(),"Pedido realizado con exito.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }//Fin Listener

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public class Insertar extends AsyncTask<Void,Void,Statement> {

        String consultaDt;
        Connection conexDt;
        Statement sentenciaDt;
        android.app.AlertDialog dialog;

        public Insertar(String consulta, android.app.AlertDialog dialog){
            this.consultaDt=consulta;
            this.dialog=dialog;
        }

        @Override
        protected Statement doInBackground(Void... params) {

            try {
                conexDt = DriverManager.getConnection("jdbc:mysql://" + ActivityLogin.ip + "/base20171", "ubase20171", "pbase20171");
                sentenciaDt = conexDt.createStatement();
                publishProgress();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sentenciaDt;
        }

        @Override
        protected void onPostExecute(Statement statement) {
            super.onPostExecute(statement);

            try {
                sentenciaDt.executeUpdate(consultaDt);

                conexDt.close();
                sentenciaDt.close();

            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

            dialog.dismiss();

        }
    }//Fin AsynTack
}//Fin Activity
