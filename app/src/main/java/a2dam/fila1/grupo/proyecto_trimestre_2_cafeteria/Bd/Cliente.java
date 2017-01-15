package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd;

/**
 * Created by Raquel on 12/01/17.
 */

public class Cliente {
    private int id;
    private String nombre, pass;
    private boolean camarero;

    public Cliente(int id, String nombre, String pass, boolean camarero) {
        this.id = id;
        this.nombre = nombre;
        this.pass = pass;
        this.camarero = camarero;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPass() {
        return pass;
    }

    public boolean isCamarero() {
        return camarero;
    }
}
