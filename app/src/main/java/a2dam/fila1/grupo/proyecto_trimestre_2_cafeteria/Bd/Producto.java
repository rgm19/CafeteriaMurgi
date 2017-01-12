package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd;

/**
 * Created by usuario on 12/01/17.
 */

public class Producto {
    private int id;
    private String nombre, img, ingredientes;
    private float precio;

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
}
