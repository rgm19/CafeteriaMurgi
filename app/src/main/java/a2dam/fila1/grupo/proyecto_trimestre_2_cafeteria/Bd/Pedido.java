package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd;

import java.util.ArrayList;

/**
 * Created by Raquel on 12/01/17.
 */

public class Pedido {
    private int idPedido;
    private Cliente cliente;
    private ArrayList<Producto> productos;
    private ArrayList<Integer> cantidad;

    public Pedido(int idPedido, Cliente cliente, ArrayList<Producto> productos, ArrayList<Integer> cantidad) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.productos = productos;
        this.cantidad = cantidad;
    }

    public int getIdPedido() {
        return idPedido;
    }


}
