package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

//Activity para mostar menú de selección para los clientes

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDPruebas;

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

    }
}
