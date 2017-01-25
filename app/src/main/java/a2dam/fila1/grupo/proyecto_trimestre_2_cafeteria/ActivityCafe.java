package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDPruebas;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Pedido;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Producto;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Dialog.Dialog_logout;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Dialog.Dialog_pass;

public class ActivityCafe extends AppCompatActivity {

    Spinner spTipo, spLeche, spAzucar;
    CheckBox lactosa, crema, chocolate, hielo;
    TextView precio, cantidad;
    ImageButton volver, menu;
    Button pedir, menos, mas;
    FloatingActionButton fab;
    ImageView fotoMenu;

    Boolean leche = false;
    Float p = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe);

        inflar();
        metodosSpinner();
        metodosListener();

//        llevaLeche(leche);

    }//Fin onCreate

    private void inflar() {
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
                        Intent i = new Intent(getApplicationContext(), ActivityLogin.class);
                        startActivity(i);
                    }
                }).create().show();
    }

    private void metodosSpinner() {
        final String[] arrayTipo = new String[BDPruebas.productos.size()];
        for (int i = 0; i < arrayTipo.length; i++){
            arrayTipo[i] = BDPruebas.productos.get(i).getNombre();
        }
        spTipo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayTipo));
        spTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < arrayTipo.length; i++){
                    if (parent.getSelectedItem().toString().trim().equals(BDPruebas.productos.get(i).getNombre()))
                        p=BDPruebas.productos.get(i).getPrecio();
                }

                setPrecio();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // vacio
            }
        });//Fin Spinner Tipo

        //----------------------------------------------------------------------------------------//
        //----------------------------------------------------------------------------------------//

        ArrayAdapter<CharSequence> adapterLch = ArrayAdapter.createFromResource(this,
                R.array.leche, android.R.layout.simple_spinner_item);
        adapterLch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spLeche.setAdapter(adapterLch);
        //Fin Spinner Leche

        //----------------------------------------------------------------------------------------//
        //----------------------------------------------------------------------------------------//

        ArrayAdapter<CharSequence> adapterAzc = ArrayAdapter.createFromResource(this,
                R.array.azucar, android.R.layout.simple_spinner_item);
        adapterAzc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spAzucar.setAdapter(adapterAzc);
        //Fin Spinner Azucar

    }//Fin Spinner

    private void metodosListener() {
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fotoMenu.setVisibility(View.VISIBLE);
            }
        });

        //----------------------------------------------------------------------------------------//
        //----------------------------------------------------------------------------------------//

        fotoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fotoMenu.setVisibility(View.INVISIBLE);
            }
        });

        //----------------------------------------------------------------------------------------//
        //----------------------------------------------------------------------------------------//

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Dialog_logout dialogo= new Dialog_logout();
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                dialogo.show(ft, "Salir");
                onBackPressed();
            }
        });

        //----------------------------------------------------------------------------------------//
        //----------------------------------------------------------------------------------------//

        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCantidad(-1);
            }
        });

        //----------------------------------------------------------------------------------------//
        //----------------------------------------------------------------------------------------//

        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCantidad(1);
            }
        });

        //----------------------------------------------------------------------------------------//
        //----------------------------------------------------------------------------------------//

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Producto producto = null;
                String comentarios = generarComentarios();

                for (int i = 0; i < BDPruebas.productos.size(); i++){
                    if (spTipo.getSelectedItem().toString().trim().equals(BDPruebas.productos.get(i).getNombre()))
                        producto = BDPruebas.productos.get(i);
                }

                BDPruebas.pedidos.add(new Pedido(ActivityLogin.USER, producto,
                        Integer.parseInt(cantidad.getText().toString().trim()),
                        Float.parseFloat(precio.getText().toString().trim()),comentarios));
                Toast.makeText(getApplicationContext(),"Café "+producto.getNombre()+" añadido a tus pedidos",Toast.LENGTH_SHORT).show();
                limpiar();
            }
        });

        //----------------------------------------------------------------------------------------//
        //----------------------------------------------------------------------------------------//

        pedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActivityDetalles.class);
                startActivity(i);
            }
        });

    }//Fin Listener

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

    private String generarComentarios() {
        String comentarios="";
        if (leche)
            comentarios=comentarios.concat(spLeche.getSelectedItem().toString().trim()+", ");
        comentarios.concat(spAzucar.getSelectedItem().toString().trim()+"");
        if (lactosa.isChecked())
            comentarios=comentarios.concat(", Sin lactosa");
        if (crema.isChecked())
            comentarios=comentarios.concat(", Crema");
        if (chocolate.isChecked())
            comentarios=comentarios.concat(", Chocolate");
        if (hielo.isChecked())
            comentarios=comentarios.concat(", Hielo");

        return comentarios;
    }//Fin generarComentarios

    private void setCantidad(int i) {
        int cant = Integer.parseInt(cantidad.getText().toString().trim());
        if ((cant + i) < 1 || (cant + i) > 20 )
            Toast.makeText(getApplicationContext(), "Cantidad mínima 1, catidad máxima 20", Toast.LENGTH_SHORT).show();
        else
            cantidad.setText("" + (cant + i));

        setPrecio();
    }//Fin setCantidad

    private void setPrecio(){
        float pFinal = p;
        if (lactosa.isChecked())
            pFinal += 0.2f;
        if (hielo.isChecked())
            pFinal += 0.3f;
        if (crema.isChecked())
            pFinal += 0.4f;
        if (chocolate.isChecked())
            pFinal += 0.5f;

        pFinal = pFinal * Integer.parseInt(cantidad.getText().toString().trim());

        precio.setText(""+pFinal);
    }//Fin setPrecio

    private void llevaLeche(boolean b) {
        spLeche.setEnabled(b);
        lactosa.setEnabled(b);
    }//Fin llevaLeche

    public void metodosCheked(View view) {
        setPrecio();
    }//Fin metodosCheked
}//Fin Activity