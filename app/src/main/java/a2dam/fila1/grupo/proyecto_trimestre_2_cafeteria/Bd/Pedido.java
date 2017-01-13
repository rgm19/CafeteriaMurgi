package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd;

import java.util.ArrayList;

/**
 * Created by Raquel on 12/01/17.
 */

public class Pedido {
    private int idPedido, idCliente;
    private ArrayList<Producto> productos;
    private ArrayList<Integer> cantidad;

    public int getIdPedido() {
        return idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }


}
