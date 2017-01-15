package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

//Activity para mostar menú de selección para los clientes

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDPruebas;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Producto;

public class ActivityMenu extends AppCompatActivity {

    ListView lv;
    AdapterMenu adapta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        lv = (ListView)findViewById(R.id.lv_activity_menu);
        adapta=new AdapterMenu(BDPruebas.productos);

        lv.setAdapter(adapta);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(view.getContext(),ActivityMenuDetalles.class);
//                i.putExtra("PRODUCTO", BDPruebas.productos.get(position));
                ActivityMenuDetalles.producto=BDPruebas.productos.get(position);
                startActivity(i);
            }
        });

    }
}
