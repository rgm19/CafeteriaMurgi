package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd;

/**
 * Created by Raquel on 12/01/17.
 */

public class Usuario {
    private int id, categoria;
    //Categoria 0=Admin, 1=Camarero, 2=Profesores, 3=Alumnos (de momento solo hasta el 2)

    private String nombre, pass;
    private String telefono;

    public Usuario(int id, String nombre, String pass, String telefono, int categoria) {
        this.id = id;
        this.nombre = nombre;
        this.pass = pass;
        this.telefono = telefono;
        this.categoria = categoria;
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
    public int getCategoria() {return categoria;}
    public String getTelefono() {return telefono;}
}
