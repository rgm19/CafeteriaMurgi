package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.HiloConexionBBDD;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //No modificar ni cambiar la posicion del siguiente metodo
        cargarDriverJDBC();



        //Actualmente tiene datos falsos, para comprobar funcionamiento uso Log.d (Verbose)
        HiloConexionBBDD.consultaProductos();
        //HiloConexionBBDD.consultaUltimoPedido();
        //HiloConexionBBDD.insertarPedido();;
    }





    private void cargarDriverJDBC()//No modificar
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");///////////////////DOCUMENTAR LA NECESIDAD DE ESTA LINEA para el funcionamiento de BBDD
        }
        catch(Exception ex)
        {
            Log.e("Conexion BBDD", "No se ha podido establecer conexion: " + ex.getMessage());
        }
    }
}
