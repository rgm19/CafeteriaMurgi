package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by usuario on 12/01/17.
 */

public class Producto {
    private String nombre;
    private float precio;
    private boolean leche;

    public Producto(String nombre, float precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public Producto(String nombre, float precio, boolean leche) {
        this.nombre = nombre;
        this.precio = precio;
        this.leche = leche;
    }

    public String getNombre() {        return nombre;    }
    public float getPrecio() {        return precio;    }

    public boolean isLeche() {
        return leche;
    }
}
