package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDPruebas;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //No modificar ni cambiar la posicion del siguiente metodo
        cargarDriverJDBC();

//        if (BDPruebas.productos.size()==0)
            BDPruebas.iniciarBBDD();
        Intent i = new Intent(getApplicationContext(),ActivityLogin.class);
        startActivity(i);



        //Actualmente tiene datos falsos, para comprobar funcionamiento uso Log.d (Verbose)
        //HiloConexionBBDD.consultaProductos();
        //HiloConexionBBDD.consultaUltimoPedido();
        //HiloConexionBBDD.insertarPedido();


    }





    private void cargarDriverJDBC(){//No modificar
        try
        {
            Class.forName("com.mysql.jdbc.Driver");///////////////////DOCUMENTAR LA NECESIDAD DE ESTA LINEA para el funcionamiento de BBDD
            Log.d("Carga Driver JDBC: ","Driver cargado correctamente");
        }
        catch(Exception ex)
        {
            Log.e("Carga Driver JDBC: ", "No se ha podido cargar el driver JDBC: " + ex.getMessage());
        }
    }//Fin cargarDriverJDBC No Modificar
}
