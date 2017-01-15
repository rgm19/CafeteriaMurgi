package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by usuario on 12/01/17.
 */

public class Producto implements Parcelable{
    private int id;
    private String nombre, img, ingredientes;
    private float precio;

    public Producto(int id, String nombre, String ingredientes, float precio, String img) {
        this.id=id;
        this.nombre=nombre;
        this.ingredientes=ingredientes;
        this.precio=precio;
        this.img=img;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImg() {
        return img;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public float getPrecio() {
        return precio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
