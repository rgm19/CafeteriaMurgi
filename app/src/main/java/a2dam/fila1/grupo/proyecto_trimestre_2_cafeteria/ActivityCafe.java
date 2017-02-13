package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;


import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
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
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Pedido;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Producto;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Dialog.Dialog_menu;
import dmax.dialog.SpotsDialog;

public class ActivityCafe extends AppCompatActivity {

    AlertDialog dialogo;

    Spinner spTipo;     //Tipo de cafe
    Spinner spLeche;    //Temperatura leche
    Spinner spAzucar;   //Tipo de azucar

    CheckBox lactosa;
    CheckBox crema;
    CheckBox chocolate;
    CheckBox hielo;

    TextView precio;
    TextView cantidad;

    ImageButton volver;
    ImageButton menu;

    Button pedir;
    Button menos;
    Button mas;

    FloatingActionButton fab;

    ArrayAdapter<CharSequence> adapterLeche;    //Adapter para el spinner de la temperatura de la leche
    ArrayAdapter<CharSequence> adapterAzucar;   //Adapter para el spinner del tipo de azucar
    ArrayAdapter<String> adapterTipo;           //Adapter para el spinner de tipos de cafe


    static ArrayList<String> arrayTipo = new ArrayList<>();

    static boolean datos = false;
    static boolean leche = false;

    static float precioCafe = 0f;//Precio cafe

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe);

        if((this.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                <= Configuration.SCREENLAYOUT_SIZE_LARGE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        inflar();
    }//Fin onCreate

    /**
     * Carga la BBDD desde cada vez que se cargue la Activity
     */
    @Override
    protected void onStart() {
        super.onStart();
        BDFinal.productosFinal.clear();
        arrayTipo.clear();
        datos = false;
        init();
    }



    /**
     * Llama a los metodos para cargar la bbdd e inicializa los datos
     */
    private void init(){
        lanzarDialogo("Cargando BBDD...");
        new ConsultasCafe("Select * from productos order by nom_pro", dialogo).execute();
        listenerBotones();
        llevaLeche(leche);
    }

    /**
     * Activa el Dialog con el String del parametro
     * @param texto
     */
    private void lanzarDialogo(String texto) {
        dialogo = new SpotsDialog(this,""+texto);
        dialogo.show();
    }

    /**
     * Infla todos los elementos del layout del activity
     */
    private void inflar() {
        spTipo       = (Spinner)     findViewById(R.id.sp_cf_tipo);
        spLeche      = (Spinner)     findViewById(R.id.sp_cf_leche);
        spAzucar     = (Spinner)     findViewById(R.id.sp_cf_azucar);

        lactosa      = (CheckBox)    findViewById(R.id.cb_cf_lactosa);
        crema        = (CheckBox)    findViewById(R.id.cb_cf_crema);
        chocolate    = (CheckBox)    findViewById(R.id.cb_cf_choco);
        hielo        = (CheckBox)    findViewById(R.id.cb_cf_hielo);

        precio       = (TextView)    findViewById(R.id.tv_cf_precioNum);
        cantidad     = (TextView)    findViewById(R.id.tv_cnt_cantidad);

        volver       = (ImageButton) findViewById(R.id.ib_cf_volver);
        menu         = (ImageButton) findViewById(R.id.ib_cf_menu);

        pedir        = (Button)      findViewById(R.id.btn_cf_pedir);
        menos        = (Button)      findViewById(R.id.btn_cnt_menos);
        mas          = (Button)      findViewById(R.id.btn_cnt_mas);

        fab          = (FloatingActionButton) findViewById(R.id.fab_cf);

        adapterLeche = ArrayAdapter.createFromResource(this, R.array.leche , android.R.layout.simple_spinner_dropdown_item);
        adapterAzucar= ArrayAdapter.createFromResource(this, R.array.azucar, android.R.layout.simple_spinner_dropdown_item);

        adapterTipo  = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrayTipo);

        spLeche .setAdapter(adapterLeche);
        spAzucar.setAdapter(adapterAzucar);

    }//Fin inflar

    /**
     * Captura la acción de pulsar el botón atrás y vuelve a la pantalla de login, borrando los pedidos guardados
     */
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar Sesión")
                .setMessage("Si sale se cerrará la sesión y se perderán los pedidos no realizados\n¿Continuar?")
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        //ActivityLogin.USER=null;
                        BDFinal.pedidosFinal.clear();
                        finish();
//                        Intent i = new Intent(getApplicationContext(), ActivityLogin.class);
//                        startActivity(i);

                    }
                }).create().show();
    }

    /**
     * Métodos de los spinner
     */
    private void inflarSpinnerTipoCafe() {
        lanzarDialogo("Calculando precios...");
        spTipo.setAdapter(adapterTipo);

        //Al seleccionar un tipo de cafe accedemos a la bbdd para consultar su precio y la posibilidad de acompañar con leche
        spTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position){
                        case 0: precioCafe =0;
                                setPrecio();
                                llevaLeche(false);
                                dialogo.dismiss();
                            break;
                        default: dialogo.show();
                            new ConsultasCafe("Select precio, leche from productos where nom_pro = '"
                                    + parent.getSelectedItem().toString().trim() + "'", dialogo).execute();
                    }
                }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //No se usa pero no se puede borrar
            }
        });//Fin Spinner Tipo
    }

    /**
     * Métodos lístener de todos los botones del layout
     */
    private void listenerBotones() {

        menu.setOnClickListener(new View.OnClickListener() {//Muestra la imagen con los cafes disponibles
            @Override
            public void onClick(View v) {
                Dialog_menu dialogoMenu= new Dialog_menu();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialogoMenu.setCancelable(true);
                dialogoMenu.show(ft, "Menú");
            }
        });

        volver.setOnClickListener(new View.OnClickListener() {//Boton volver
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        menos.setOnClickListener(new View.OnClickListener() {//Boton menos resta un cafe
            @Override
            public void onClick(View v) {
                setCantidad(-1);
            }
        });


        mas.setOnClickListener(new View.OnClickListener() {//Boton mas agrega un cafe
            @Override
            public void onClick(View v) {
                setCantidad(1);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spTipo.getSelectedItemPosition() != 0){
                    Producto producto = BDFinal.productosFinal.get(spTipo.getSelectedItemPosition()-1);
                    String comentarios = generarComentarios();

                    BDFinal.pedidosFinal.add(new Pedido(ActivityLogin.USER, producto,
                            Integer.parseInt(cantidad.getText().toString().trim()),
                            Float.parseFloat(precio.getText().toString().trim()),comentarios, "14:45"));

                    Toast.makeText(getApplicationContext(),"Café "+producto.getNombre()+" añadido a tus pedidos",Toast.LENGTH_SHORT).show();
                    limpiar();
                }else
                    Toast.makeText(getApplicationContext(),"Debe seleccionar un TIPO de café",Toast.LENGTH_SHORT).show();
            }
        });


        pedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//Lanza la activity detalles para conocer el resumen del pedido
                Intent i = new Intent(getApplicationContext(), ActivityDetalles.class);
                startActivity(i);
            }
        });

    }//Fin ListenerBotones

    /**
     * Limpiar, al realizar un pedido con FAB, desmarca los checkBox y coloca los spinner  y la
     * cantidad en posicion inicial
     */
    private void limpiar() {
        spTipo      .setSelection(0);
        spLeche     .setSelection(0);
        spAzucar    .setSelection(0);
        lactosa     .setChecked(false);
        crema       .setChecked(false);
        chocolate   .setChecked(false);
        hielo       .setChecked(false);
        cantidad    .setText(""+1);

        setPrecio();
    }//Fin limpiar

    /**
     * Comprueba los checkbox y spinner seleccionados
     * @return String: Cadena con las opciones seleccionadas en spinner y checkbox
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
     * @param i Cantidad a sumar o restar a la cantidad actual
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
     * Redondea para que salga sólo con 2 decimales máximo
     */
    private void setPrecio(){
        float precioFinal = precioCafe;
        if (leche)
            if (lactosa.isChecked())
                precioFinal += 0.2f;
        if (hielo.isChecked())
            precioFinal += 0.3f;
        if (crema.isChecked())
            precioFinal += 0.4f;
        if (chocolate.isChecked())
            precioFinal += 0.5f;

        precioFinal = precioFinal * Integer.parseInt(cantidad.getText().toString().trim());
        double redondeo = Math.round(precioFinal*100.0)/100.0;

        precio.setText(""+redondeo);
    }//Fin setPrecio

    /**
     * Habilita o deshabilita las opciones de leche según el producto
     * @param leche
     */
    private void llevaLeche(boolean leche) {
        spLeche.setEnabled(leche);
        lactosa.setEnabled(leche);
    }//Fin llevaLeche

    /**
     * Actualiza precio al detectar eventos de los checkbox (implementados en XML)
     * @param view
     */
    public void metodosCheked(View view) {
        setPrecio();
    }//Fin metodosCheked















    public class ConsultasCafe extends AsyncTask<Void,Void,ResultSet> {

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
        protected ResultSet doInBackground(Void... params) {

            try {
                conexCf     = DriverManager.getConnection("jdbc:mysql://" + ActivityLogin.ip + "/base20171", "ubase20171", "pbase20171");
                sentenciaCf = conexCf.createStatement();
                resultCf    = null;
                publishProgress();
                resultCf    = sentenciaCf.executeQuery(consultaCf);

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
                    if (!datos){
                        if (arrayTipo.size() == 0)
                            arrayTipo.add("");

                        BDFinal.productosFinal.add(new Producto(resultSet.getInt(1),resultSet.getString(2),resultSet.getFloat(3), resultSet.getBoolean(4)));
                        arrayTipo.add(resultSet.getString(2));
                    }

                    if (consultaCf.contains("precio")){
                        precioCafe=resultSet.getFloat(1);
                        leche=resultSet.getBoolean(2);
                        llevaLeche(leche);
                        setPrecio();
                    }
                }
                if (!datos){

                    inflarSpinnerTipoCafe();
                    limpiar();
                    datos = true;
                }

                conexCf     .close();
                sentenciaCf .close();
                resultCf    .close();
                dialog      .dismiss();

            }
            catch (Exception ex)
            {
                Log.d("ERROR",""+ex.getMessage());
            }
        }
    }//Fin AsynTack
}//Fin Acticity
