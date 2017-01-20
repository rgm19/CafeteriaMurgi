package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDPruebas;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Usuario;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Dialog.Dialog_pass;

public class ActivityLogin extends AppCompatActivity {

    public static Usuario USER = null;

    EditText usuario, pass;
    Button entrar;
    ImageButton ayuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inflar();
        listener();

        if (USER==null){
            login();
        }else{
            lanzarActivity();
        }
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
        usuario = (EditText) findViewById(R.id.et_lg_usuario);
        pass = (EditText) findViewById(R.id.et_lg_pass);
        entrar = (Button) findViewById(R.id.btn_lg_entrar);
        ayuda = (ImageButton) findViewById(R.id.ibtn_lg_ayuda);
    }

    private void login() {
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usuario.getText().toString().trim().equals("") || pass.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(), "Debe rellenar todos los campos",Toast.LENGTH_SHORT).show();
                }else{
                    boolean user = false;
    //_______________________________________________________________________________________________
    // Comprobar en BBDD usuario y contraseña ------------------------------------------------------
                    // usuario y pass y devuvelce un boleano
                    for (Usuario u : BDPruebas.usuarios){
                        if (usuario.getText().toString().trim().equals(u.getNombre())){
                            if (pass.getText().toString().trim().equals(u.getPass())){
                                USER=u;
                                lanzarActivity();
                                user = true;
                            }
                        }
                    }
                    if (!user)
                    Toast.makeText(getApplicationContext(), "Usuario o Contraseña incorrectos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void lanzarActivity() {
        switch (USER.getCategoria()){
//            case 0: Intent i0 = new Intent(getApplicationContext(), ActivityAdmin.class);
//                startActivity(i0);
//                break;
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
}//Fin Acticity
