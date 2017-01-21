package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDPruebas;

public class ActivityDetalles extends AppCompatActivity {

    static ListView listaProcductos;
    static TextView precio;
    ImageButton volver;
    TimePicker reloj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        inflar();

        lanzarAdapter();
        metodosListener();

    }

    private void inflar() {
        listaProcductos = (ListView)findViewById(R.id.lv_dt);
        precio = (TextView)findViewById(R.id.tv_dt_precio);
        volver = (ImageButton)findViewById(R.id.ib_dt_volver);
    }

    static void lanzarAdapter() {
        AdapterDetalles adapta = new AdapterDetalles(BDPruebas.pedidos);
        listaProcductos.setAdapter(adapta);
        precioTotal();
    }

    private static void precioTotal() {
        float pf = 0f;
        for (int i = 0; i < BDPruebas.pedidos.size(); i++){
            pf += BDPruebas.pedidos.get(i).getPrecio();
        }
        precio.setText(""+pf);

    }

    private void metodosListener() {
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActivityCafe.class);
                startActivity(i);
            }
        });
    }
}
