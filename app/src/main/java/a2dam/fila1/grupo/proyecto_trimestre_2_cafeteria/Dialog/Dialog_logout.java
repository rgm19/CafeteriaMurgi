package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.ActivityLogin;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.R;

/**
 * Created by Gand on 03/12/16.
 */

/**
 * Clase para mostrar un AlertDialog personalizado con los datos licencias de imagenes
 * Layout asociada dialog_licencia.xml
 */
public class Dialog_logout extends DialogFragment {

    Activity activity;

    public Dialog_logout() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        activity=this.getActivity();

        return createDialogo();
    }

    public AlertDialog createDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_logout, null);

        builder.setView(v);

        Button si = (Button) v.findViewById(R.id.bt_dl_si);
        si.setOnClickListener(
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {
                        ActivityLogin.USER=null;
                        Intent i = new Intent(activity.getApplicationContext(), ActivityLogin.class);
                        startActivity(i);
                    }
                }
        );
        Button no = (Button) v.findViewById(R.id.bt_dl_no);
        no.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }
        );
        return builder.create();
    }
}