package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd;

/**
 * Created by Raquel on 12/01/17.
 */

public class Cliente {
    private int id;
    private String nombre, pass;
    private boolean camarero;

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
