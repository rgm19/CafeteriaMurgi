package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd;

import java.util.ArrayList;

/**
 * Created by Gand on 12/01/17.
 */

public class BDPruebas {
    public static ArrayList<Producto> productos = new ArrayList<>();
    public static ArrayList<Cliente> clientes = new ArrayList<>();
    public static ArrayList<Pedido> pedidos = new ArrayList<>();


    public static void iniciarBBDD(){
        productos.add(new Producto(1,"Tostada tomate","Pan, aceite, tomate",1.25f,"foto"));
        productos.add(new Producto(2,"Tostada queso","Pan, queso",1.0f,"foto"));
        productos.add(new Producto(3,"Tostada atún","Pan, queso, atún",1.5f,"foto"));
        productos.add(new Producto(4,"Tostada bacon","Pan, bacon",1.75f,"foto"));
        productos.add(new Producto(5,"Tostada jamón","Pan, jamón",2.0f,"foto"));

        clientes.add(new Cliente(1,"Paco",null,false));
        clientes.add(new Cliente(2,"Juan",null,false));
        clientes.add(new Cliente(3,"Sebastian","pass",true));

        ArrayList<Producto> pedido1=new ArrayList<>();
        ArrayList<Integer> cantidad1=new ArrayList<>();
        ArrayList<Producto> pedido2=new ArrayList<>();
        ArrayList<Integer> cantidad2=new ArrayList<>();
        pedido1.add(productos.get(0));cantidad1.add(1);
        pedido1.add(productos.get(2));cantidad1.add(3);
        pedido1.add(productos.get(4));cantidad1.add(2);
        pedido2.add(productos.get(1));cantidad2.add(10);
        pedido2.add(productos.get(3));cantidad2.add(1);

        pedidos.add(new Pedido(1,clientes.get(0),pedido1,cantidad1));
        pedidos.add(new Pedido(1,clientes.get(1),pedido2,cantidad2));

    }

}//Fin Class BDPruebas