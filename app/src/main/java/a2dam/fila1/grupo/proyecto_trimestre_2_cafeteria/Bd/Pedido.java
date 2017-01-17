package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd;

import java.util.ArrayList;

/**
 * Created by Raquel on 12/01/17.
 */

public class Pedido {
    private int idPedido;
    private Usuario usuario;
    private ArrayList<Producto> productos;
    private ArrayList<Integer> cantidad;

    public Pedido(int idPedido, Usuario usuario, ArrayList<Producto> productos, ArrayList<Integer> cantidad) {
        this.idPedido = idPedido;
        this.usuario = usuario;
        this.productos = productos;
        this.cantidad = cantidad;
    }

    public int getIdPedido() {
        return idPedido;
    }


}
