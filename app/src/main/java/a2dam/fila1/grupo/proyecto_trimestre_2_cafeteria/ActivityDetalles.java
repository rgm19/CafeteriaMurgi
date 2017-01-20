package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDPruebas;

public class ActivityDetalles extends AppCompatActivity {

    ListView listaProcductos;
    TextView precio;
    TimePicker reloj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        inflar();

        AdapterDetalles adapta = new AdapterDetalles(BDPruebas.pedidos);
        listaProcductos.setAdapter(adapta);
    }

    private void inflar() {
        listaProcductos = (ListView)findViewById(R.id.lv_dt);
    }
}
