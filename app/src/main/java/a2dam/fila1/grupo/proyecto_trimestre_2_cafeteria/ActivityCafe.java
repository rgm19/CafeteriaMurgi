package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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

public class ActivityCafe extends AppCompatActivity {

    Spinner spTipo, spLeche, spAzucar;
    CheckBox lactosa, crema, chocolate, hielo;
    TextView precio;
    ImageButton volver, menu;
    Button pedir, menos, mas;
    FloatingActionButton fab;
    ImageView fotoMenu;

    Boolean leche = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe);

        inflar();
        metodosSpinner();
        metodosListener();

//        llevaLeche(leche);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Log.e("ERRRRRRRRRRRRRROR", "Captura backpresed");

        new AlertDialog.Builder(getApplicationContext())
                .setTitle("Salir")
                .setMessage("Si sale se cerrará la sesión y se perderán los pedidos almacenados. \n¿Salir?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityLogin.USER=null;
                        Intent i = new Intent(getApplicationContext(), ActivityLogin.class);
                        startActivity(i);
                        // pedidos.clear();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void inflar() {
        spTipo = (Spinner) findViewById(R.id.sp_cf_tipo);
        spLeche = (Spinner) findViewById(R.id.sp_cf_leche);
        spAzucar = (Spinner) findViewById(R.id.sp_cf_azucar);
        lactosa = (CheckBox) findViewById(R.id.cb_cf_lactosa);
        crema = (CheckBox) findViewById(R.id.cb_cf_crema);
        chocolate = (CheckBox) findViewById(R.id.cb_cf_choco);
        hielo = (CheckBox) findViewById(R.id.cb_cf_hielo);
        precio = (TextView) findViewById(R.id.tv_cf_precioNum);
        volver = (ImageButton) findViewById(R.id.ib_cf_volver);
        menu = (ImageButton) findViewById(R.id.ib_cf_menu);
        pedir = (Button) findViewById(R.id.btn_cf_pedir);
        menos = (Button) findViewById(R.id.btn_cnt_menos);
        mas = (Button) findViewById(R.id.btn_cnt_mas);
        fab = (FloatingActionButton) findViewById(R.id.fab_cf);
        fotoMenu = (ImageView) findViewById(R.id.iv_cf_menu);
    }

    private void metodosSpinner() {
        String[] arrayTipo = {"Espreso","Doble","Cortado","Capuchino","Bombón"};
        spTipo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayTipo));
        spTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
        spLeche.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getSelectedItemPosition()){
                    case 0:
                        break;
                    default:
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });//Fin Spinner Leche

        //----------------------------------------------------------------------------------------//
        //----------------------------------------------------------------------------------------//

        ArrayAdapter<CharSequence> adapterAzc = ArrayAdapter.createFromResource(this,
                R.array.azucar, android.R.layout.simple_spinner_item);
        adapterAzc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spAzucar.setAdapter(adapterAzc);
        spAzucar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getSelectedItemPosition()){
                    case 0:
                        break;
                    default:
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });//Fin Spinner Leche
    }

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
                new AlertDialog.Builder(getApplicationContext())
                        .setTitle("Salir")
                        .setMessage("Si sale se cerrará la sesión y se perderán los pedidos almacenados. \n¿Salir?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityLogin.USER=null;
                                Intent i = new Intent(getApplicationContext(), ActivityLogin.class);
                                startActivity(i);
                                // pedidos.clear();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    private void llevaLeche(boolean b) {
        spLeche.setEnabled(b);
        lactosa.setEnabled(b);
    }
}//Fin Activity
