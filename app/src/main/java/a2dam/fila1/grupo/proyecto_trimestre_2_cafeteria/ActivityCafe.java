package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
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
import dmax.dialog.SpotsDialog;

public class ActivityCafe extends AppCompatActivity {

    AlertDialog dialogo;

    Spinner spTipo, spLeche, spAzucar;
    CheckBox lactosa, crema, chocolate, hielo;
    TextView precio, cantidad;
    ImageButton volver, menu;
    Button pedir, menos, mas;
    FloatingActionButton fab;
    ImageView fotoMenu;

    static ArrayList<String> arrayTipo = new ArrayList<>();
    static Boolean leche = false;
    static Float p = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe);

        inflar();

        metodosSpinner();
        metodosListener();

        llevaLeche(leche);

    }//Fin onCreate

    /**
     * Infla todos los elementos del layout del activity
     */
    private void inflar() {
        dialogo =new SpotsDialog(this,"Calculando precios...");
        spTipo = (Spinner) findViewById(R.id.sp_cf_tipo);
        spLeche = (Spinner) findViewById(R.id.sp_cf_leche);
        spAzucar = (Spinner) findViewById(R.id.sp_cf_azucar);
        lactosa = (CheckBox) findViewById(R.id.cb_cf_lactosa);
        crema = (CheckBox) findViewById(R.id.cb_cf_crema);
        chocolate = (CheckBox) findViewById(R.id.cb_cf_choco);
        hielo = (CheckBox) findViewById(R.id.cb_cf_hielo);
        precio = (TextView) findViewById(R.id.tv_cf_precioNum);
        cantidad = (TextView) findViewById(R.id.tv_cnt_cantidad);
        volver = (ImageButton) findViewById(R.id.ib_cf_volver);
        menu = (ImageButton) findViewById(R.id.ib_cf_menu);
        pedir = (Button) findViewById(R.id.btn_cf_pedir);
        menos = (Button) findViewById(R.id.btn_cnt_menos);
        mas = (Button) findViewById(R.id.btn_cnt_mas);
        fab = (FloatingActionButton) findViewById(R.id.fab_cf);
        fotoMenu = (ImageView) findViewById(R.id.iv_cf_menu);
    }//Fin inflar

    /**
     * Captura la acción de pulsar el botón atrás y vuelve a la pantalla de login, borrando los
     * pedidos guardados
     */
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar Sesión")
                .setMessage("Si sale se cerrará la sesión y se perderán los pedidos no realizados\n¿Continuar?")
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        //ActivityLogin.USER=null;
                        ActivityLogin.loginCorrecto=false;
                        BDFinal.pedidosFinal.clear();
                        Intent i = new Intent(getApplicationContext(), ActivityLogin.class);
                        startActivity(i);
                    }
                }).create().show();
    }

    /**
     * Métodos de los spinner
     */
    private void metodosSpinner() {
        /**
         * Spinner Tipo, muestra una lista de los nombres de los produdos para seleccionar uno
         * Al seleccionar un tipo se consulta su precio y si lleva leche o no para modificar las opciones
         */
        final String[] arrayT = new String[BDFinal.productosFinal.size()];
        for (int i = 0; i < arrayT.length; i++){
            arrayT[i] = BDFinal.productosFinal.get(i).getNombre();
        }
        spTipo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayT));
        spTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dialogo.show();
                new ConsultasCafe("Select precio, leche from productos where nom_pro = '"+parent.getSelectedItem().toString().trim()+"'",dialogo).execute("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //No se usa pero no se puede borrar
            }
        });//Fin Spinner Tipo

        //----------------------------------------------------------------------------------------//
        //----------------------------------------------------------------------------------------//
        /**
         * Spinner Leche, muestra las opciones de temperatura de la leche que se recogeran luego
         * para los detalles del pedido
         */
        ArrayAdapter<CharSequence> adapterLch = ArrayAdapter.createFromResource(this,
                R.array.leche, android.R.layout.simple_spinner_dropdown_item);
        spLeche.setAdapter(adapterLch);
        //Fin Spinner Leche

        //----------------------------------------------------------------------------------------//
        //----------------------------------------------------------------------------------------//
        /**
         * Spinner Azucar, muestra tipos de azucar que se recogeran luego para los detalles del pedido
         */
        ArrayAdapter<CharSequence> adapterAzc = ArrayAdapter.createFromResource(this,
                R.array.azucar, android.R.layout.simple_spinner_dropdown_item);
        spAzucar.setAdapter(adapterAzc);
        //Fin Spinner Azucar

    }//Fin Spinner

    /**
     * Métodos lístener de todos los botones del layout
     */
    private void metodosListener() {
        /**
         * Listener Menú, muestra la imagen de los cafés para seleccionar el menú
         * Cambiar a AlertDialog
         */
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fotoMenu.setVisibility(View.VISIBLE);
            }
        });

        //----------------------------------------------------------------------------------------//
        //----------------------------------------------------------------------------------------//
        /**
         * Listener FotoMenu, oculta la imagen del menú cuando se pincha encima
         * Eliminar si se introduce AlertDialog, eliminar la ImagenView fotoMenu
         */
        fotoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fotoMenu.setVisibility(View.INVISIBLE);

            }
        });

        //----------------------------------------------------------------------------------------//
        //----------------------------------------------------------------------------------------//

        /**
         * Listener volver, llama a metodo onBackPressed, vuelve a la activity de login y elimina
         * pedidos no realizados
         */
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //----------------------------------------------------------------------------------------//
        //----------------------------------------------------------------------------------------//
        /**
         * Listener menos, llama al método setCantidad, reduce la cantidad de producto a pedir en 1
         */
        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCantidad(-1);
            }
        });

        //----------------------------------------------------------------------------------------//
        //----------------------------------------------------------------------------------------//
        /**
         * Listener mas, llama al método setCantidad, aumenta la cantidad de producto a pedir en 1
         */
        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCantidad(1);
            }
        });

        //----------------------------------------------------------------------------------------//
        //----------------------------------------------------------------------------------------//
        /**
         * Listener fab, toma los datos de los spinner y los checkbox y añade un pedido al array
         * Añade pedidos a un array auxiliar, no a la BBDD
         */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Producto producto = null;
                String comentarios = generarComentarios();

                producto = BDFinal.productosFinal.get(spTipo.getSelectedItemPosition());
//                for (int i = 0; i < BDPruebas.productos.size(); i++){
//                    if (spTipo.getSelectedItem().toString().trim().equals(BDPruebas.productos.get(i).getNombre()))
//                        producto = BDPruebas.productos.get(i);
//                }

                BDFinal.pedidosFinal.add(new Pedido(ActivityLogin.USER, producto,
                        Integer.parseInt(cantidad.getText().toString().trim()),
                        Float.parseFloat(precio.getText().toString().trim()),comentarios));
                Toast.makeText(getApplicationContext(),"Café "+producto.getNombre()+" añadido a tus pedidos",Toast.LENGTH_SHORT).show();
                limpiar();
            }
        });

        //----------------------------------------------------------------------------------------//
        //----------------------------------------------------------------------------------------//
        /**
         * Listener pedir, lanza el activity detalles para mostar el "carrito" y finalizar el pedido
         */
        pedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActivityDetalles.class);
                startActivity(i);
            }
        });

    }//Fin Listener

    /**
     * Limpiar, al realizar un pedido con FAB, desmarca los checkBox y coloca los spinner en
     * posicion inicial
     */
    private void limpiar() {
        spTipo.setSelection(0);
        spLeche.setSelection(0);
        spAzucar.setSelection(0);
        lactosa.setChecked(false);
        crema.setChecked(false);
        chocolate.setChecked(false);
        hielo.setChecked(false);
        cantidad.setText(""+1);

        setPrecio();
    }//Fin limpiar

    /**
     * generarComentarios, devuelve un String con las opciones selecionadas en los checkBox y spinner
     * @return
     */
    private String generarComentarios() {
        String comentarios = "";
        if (leche)
            comentarios = comentarios.concat(spLeche.getSelectedItem().toString().trim()+", ");

        comentarios = comentarios.concat(spAzucar.getSelectedItem().toString().trim()+"");
        if (leche)
            if (lactosa.isChecked())
                comentarios = comentarios.concat(", Sin lactosa");
        if (crema.isChecked())
            comentarios = comentarios.concat(", Crema");
        if (chocolate.isChecked())
            comentarios = comentarios.concat(", Chocolate");
        if (hielo.isChecked())
            comentarios = comentarios.concat(", Hielo");

        return comentarios;
    }//Fin generarComentarios

    /**
     * setCantidad, calcula y controla la cantidad de producto a pedir
     * @param i Cantidad a sumar o restar a la cantidad
     */
    private void setCantidad(int i) {
        int cant = Integer.parseInt(cantidad.getText().toString().trim());
        if ((cant + i) < 1 || (cant + i) > 20 )
            Toast.makeText(getApplicationContext(), "Cantidad mínima 1, catidad máxima 20", Toast.LENGTH_SHORT).show();
        else
            cantidad.setText("" + (cant + i));

        setPrecio();
    }//Fin setCantidad

    /**
     * setPrecio, calcula el precio del pedido según el producto y las opciones seleccionadas
     * Redondea para que salga sólo con 2 decimales
     */
    private void setPrecio(){
        float pFinal = p;
        if (leche)
            if (lactosa.isChecked())
                pFinal += 0.2f;
        if (hielo.isChecked())
            pFinal += 0.3f;
        if (crema.isChecked())
            pFinal += 0.4f;
        if (chocolate.isChecked())
            pFinal += 0.5f;

        pFinal = pFinal * Integer.parseInt(cantidad.getText().toString().trim());
        double redondeo = Math.round(pFinal*100.0)/100.0;

        precio.setText(""+redondeo);
    }//Fin setPrecio

    /**
     * llevaLeche, habilita o deshabilita las opciones de leche según el producto
     * @param b
     */
    private void llevaLeche(boolean b) {
        spLeche.setEnabled(b);
        lactosa.setEnabled(b);
    }//Fin llevaLeche

    /**
     * metodosCheked, onClick de los checkBox implementado en el XML
     * Llama a setPrecio cada vez que se cliquea un un checkBox
     * @param view
     */
    public void metodosCheked(View view) {
        setPrecio();
    }//Fin metodosCheked


    ////////////////////////////////////////////////////////////////////////////////////////////////

    public class ConsultasCafe extends AsyncTask<String,Void,ResultSet> {

        String consultaCf;
        Connection conexCf;
        Statement sentenciaCf;
        android.app.AlertDialog dialog;
        ResultSet resultCf;

        public ConsultasCafe(String consulta, android.app.AlertDialog dialog){
            this.consultaCf=consulta;
            this.dialog=dialog;
        }

        @Override
        protected ResultSet doInBackground(String... params) {

            try {
                conexCf = DriverManager.getConnection("jdbc:mysql://" + ActivityLogin.ip + "/base20171", "ubase20171", "pbase20171");
                sentenciaCf = conexCf.createStatement();
                resultCf = null;
                publishProgress();

                resultCf = sentenciaCf.executeQuery(consultaCf);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return resultCf;
        }

        @Override
        protected void onPostExecute(ResultSet resultSet) {
            super.onPostExecute(resultSet);

            try{
                while (resultSet.next()) {
                    if (consultaCf.contains("precio")){
                        p=resultSet.getFloat(1);
                        leche=resultSet.getBoolean(2);
                        llevaLeche(leche);
                        setPrecio();
                    }
                }

                conexCf.close();
                sentenciaCf.close();
                resultCf.close();
                dialog.dismiss();

            }
            catch (Exception ex)
            {
                Log.d("Fallo de cojones","");
            }
        }
    }//Fin AsynTack

}//Fin Acticity
