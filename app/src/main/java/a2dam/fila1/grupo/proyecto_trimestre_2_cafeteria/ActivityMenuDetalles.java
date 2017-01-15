package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

//Detalles del pedido selecionado

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Producto;

public class ActivityMenuDetalles extends AppCompatActivity {

    static Producto producto;
    Button pedir, volver, mas, menos;
    ImageView foto;
    TextView nombre, precio, ingredientes, cantidad;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detalles);

//
//        if (savedInstanceState == null) {
//            Bundle extras = getIntent().getExtras();
//            if(extras == null) {
//                producto= null;
//            } else {
//                producto= (Producto) extras.getParcelable("PRODUCTO");
//            }
//        } else {
//            producto= (Producto) savedInstanceState.getParcelable("PRODUCTO");
//        }

        inflar();
        datos();
        listener();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
        }
    }

    private void inflar() {
        pedir = (Button)findViewById(R.id.btn_Pedir);
        volver = (Button)findViewById(R.id.btn_md_Volver);
        mas = (Button)findViewById(R.id.btn_md_mas);
        menos = (Button)findViewById(R.id.btn_md_menos);
        foto = (ImageView) findViewById(R.id.iv_md_Foto);
        nombre = (TextView) findViewById(R.id.tv_md_Nombre);
        precio = (TextView)findViewById(R.id.tv_md_Precio);
        ingredientes = (TextView)findViewById(R.id.tv_md_Ingredientes);
        cantidad = (TextView)findViewById(R.id.tv_md_canidad);
        fab = (FloatingActionButton)findViewById(R.id.fab_md);
    }

    private void datos() {
//        foto.setImageResource();
        nombre.setText(producto.getNombre());
        precio.setText(""+producto.getPrecio());
        ingredientes.setText(producto.getIngredientes());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = new Intent(this, ActivityMenu.class);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.from(this)
                            .addNextIntent(upIntent)
                            .startActivities();
                    finish();
                } else {
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void listener() {
        //----------- VOLVER ----------------------------------------------------------------------
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ActivityMenu.class);
                startActivity(i);
            }
        });//Fin volver
        //----------- VOLVER ----------------------------------------------------------------------44

        //----------- PEDIR -----------------------------------------------------------------------
        pedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //----------------------------------------------------------------------------------------------
            }
        });//Fin pedir
        //----------- PEDIR -----------------------------------------------------------------------

        //------------ MAS ------------------------------------------------------------------------
        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCantidad(1);
            }
        });//Fin mas
        //------------ MAS ------------------------------------------------------------------------

        //----------- MENOS -----------------------------------------------------------------------
        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCantidad(-1);
            }
        });//Fin menos
        //----------- MENOS -----------------------------------------------------------------------

        //------------ FAB ------------------------------------------------------------------------
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //----------------------------------------------------------------------------------------------
            }
        });//Fin fab
        //------------ FAB ------------------------------------------------------------------------
    }

    private void setCantidad(int i) {
        int cant=Integer.parseInt(cantidad.getText().toString());
        if ((cant+i)==0 || (cant+i)==31){
            Toast.makeText(getApplicationContext(),"Cantidad mínima 1, cantidad máxima 30", Toast.LENGTH_SHORT).show();
        }else{
            cantidad.setText(""+(cant+i));
        }
    }
}//Fin Activity
