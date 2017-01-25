package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDFinal;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Usuario;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Dialog.Dialog_pass;
import dmax.dialog.SpotsDialog;

import static a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.HiloConexionBBDD.resultado;
import static a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.HiloConexionBBDD.sentencia;

public class ActivityLogin extends AppCompatActivity {

    AlertDialog dialogo;

    public static Usuario USER = null;
    public static String ip = null;

    static EditText usuario, pass;
    Button entrar;
    ImageButton ayuda;
    RadioButton local, externa;

    static boolean loginCorrecto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inflar();
        calcularIP();
        listener();

        if (USER==null){
            login();
        }else{
            lanzarActivity();
        }
    }

    private void calcularIP() {
        if (local.isChecked())
            ip = "10.10.4.150";
        if (externa.isChecked())
            ip = "www.iesmurgi.org";
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (USER==null){
//            login();
//        }else{
//            lanzarActivity();
//        }
//    }


    private void listener() {
        ayuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_pass dialogo= new Dialog_pass();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialogo.show(ft, "Contraseñas");
            }
        });

    }

    private void inflar() {
        dialogo =new SpotsDialog(this,"Cargando...");
        usuario = (EditText) findViewById(R.id.et_lg_usuario);
        pass = (EditText) findViewById(R.id.et_lg_pass);
        entrar = (Button) findViewById(R.id.btn_lg_entrar);
        ayuda = (ImageButton) findViewById(R.id.ibtn_lg_ayuda);
        local = (RadioButton) findViewById(R.id.rb_lg_local);
        externa = (RadioButton) findViewById(R.id.rb_lg_externa);
    }



    private void login() {
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usuario.getText().toString().trim().equals("") || pass.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(), "Debe rellenar todos los campos",Toast.LENGTH_SHORT).show();
                }else{
                    dialogo.show();
                    new ComprobarUsuario(usuario.getText().toString().trim(),pass.getText().toString().trim(),dialogo).execute("");
    //_______________________________________________________________________________________________
    // Comprobar en BBDD usuario y contraseña ------------------------------------------------------
                    // usuario y pass y devuvelce un boleano
//                    for (Usuario u : BDFinal.usuarioFinal){
//                        if (usuario.getText().toString().trim().equals(u.getNombre())){
//                            if (pass.getText().toString().trim().equals(u.getPass())){
//                                USER=u;
////                    if(user)
//                                lanzarActivity();
//                                loginCorrecto = true;
//
//                            }
//                        }
//                    }
//                    if (loginCorrecto)
//                        lanzarActivity();
////                        BDFinal.usuarioFinal.clear();
//                    if (!loginCorrecto)
//                    Toast.makeText(getApplicationContext(), "Usuario o Contraseña incorrectos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void lanzarActivity() {
        switch (USER.getCategoria()){
            case 0: Intent i0 = new Intent(getApplicationContext(), ActivityPedidos.class);
                startActivity(i0);
                break;
            case 1: Intent i1 = new Intent(getApplicationContext(), ActivityPedidos.class);
                startActivity(i1);
                break;
            case 2: Intent i2 = new Intent(getApplicationContext(), ActivityCafe.class);
                startActivity(i2);
                break;
            case 3: Intent i3 = new Intent(getApplicationContext(), ActivityCafe.class);
                startActivity(i3);
                break;
            default:
        }
    }

    public void radio(View view) {
        calcularIP();
    }

    public class ComprobarUsuario extends AsyncTask<String,Void,ResultSet> {

        String user, passw;
        Connection conexLg;
        Statement sentenciaLg;
        AlertDialog dialog;
        ResultSet resultLg;

        public ComprobarUsuario(String user, String passw,AlertDialog dialog){
            this.user=user;
            this.passw=passw;
            this.dialog=dialog;
        }

        @Override
        protected ResultSet doInBackground(String... params) {

            try {
                conexLg = DriverManager.getConnection("jdbc:mysql://" +ip+"/base20171", "ubase20171", "pbase20171");
                sentenciaLg = conexLg.createStatement();
                resultLg = null;
                publishProgress();

                String consulta = "Select * from usuarios;";
                resultLg = sentencia.executeQuery(consulta);
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            dialog.dismiss();
            return resultLg;
        }

        @Override
        protected void onPostExecute(ResultSet resultSet) {
            super.onPostExecute(resultSet);

            try
            {
                while (resultSet.next())//prueba de la base de datos
                {
                        if(resultSet.getString("username").equals(user))
                        {
                            if(resultSet.getString("pass").equals(passw))
                            {
                                loginCorrecto=true;
                                USER=new Usuario(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4),resultSet.getInt(5));
                                lanzarActivity();
                                Log.e("DATOS",""+resultSet.getString("username"));
                            }
                        }
//                    BDFinal.usuarioFinal.add(new Usuario(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4),resultSet.getInt(5)));
                }

                if (!loginCorrecto)
                    Toast.makeText(getApplicationContext(), "Usuario o Contraseña incorrectos",Toast.LENGTH_SHORT).show();

                conexLg.close();
                sentenciaLg.close();
                resultLg.close();
                dialog.dismiss();

            }
            catch (Exception ex)
            {
                Log.d("Fallo de cojones","");
            }
        }
    }

}//Fin Acticity
