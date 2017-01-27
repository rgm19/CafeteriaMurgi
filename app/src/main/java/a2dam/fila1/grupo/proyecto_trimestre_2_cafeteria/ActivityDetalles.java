package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDFinal;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDPruebas;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Pedido;

public class ActivityDetalles extends AppCompatActivity {

    static ListView listaProcductos;
    static TextView precio;
    ImageButton volver;
    Button confirmar;
    TimePicker reloj;

    boolean insertado=false;

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
        confirmar = (Button)findViewById(R.id.btn_dt_confirmar);
        reloj = (TimePicker)findViewById(R.id.timePicker2);
    }

    static void lanzarAdapter() {
        AdapterDetalles adapta = new AdapterDetalles(BDFinal.pedidosFinal);
        listaProcductos.setAdapter(adapta);
        precioTotal();
    }

    private static void precioTotal() {
        float pf = 0f;
        for (int i = 0; i < BDFinal.pedidosFinal.size(); i++){
            pf += BDFinal.pedidosFinal.get(i).getPrecio();
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

        //----------------------------------------------------------------------------------------//
        //----------------------------------------------------------------------------------------//

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BDFinal.pedidosFinal.size()==0)
                    Toast.makeText(getApplicationContext(),"No se han añadido productos.", Toast.LENGTH_SHORT).show();
                else{
                    for (Pedido p : BDFinal.pedidosFinal){
                        //Insert de pedidos en BBDD
                    }
                    confirmar.setEnabled(false);
                    BDFinal.pedidosFinal.clear();
                    Toast.makeText(getApplicationContext(),"Pedido realizado con exito.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
