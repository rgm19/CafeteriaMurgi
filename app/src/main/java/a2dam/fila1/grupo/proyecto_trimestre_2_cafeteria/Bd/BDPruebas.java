package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd;

import java.util.ArrayList;

/**
 * Created by Gand on 12/01/17.
 */

public class BDPruebas {
    public static ArrayList<Producto> productos = new ArrayList<>();
    public static ArrayList<Usuario> usuarios = new ArrayList<>();
    public static ArrayList<Pedido> pedidos = new ArrayList<>();


    public static void iniciarBBDD(){
        productos.add(new Producto(1,"Tostada tomate","Pan, aceite, tomate",1.25f,"foto"));
        productos.add(new Producto(2,"Tostada queso","Pan, queso",1.0f,"foto"));
        productos.add(new Producto(3,"Tostada atún","Pan, queso, atún",1.5f,"foto"));
        productos.add(new Producto(4,"Tostada bacon","Pan, bacon",1.75f,"foto"));
        productos.add(new Producto(5,"Tostada jamón","Pan, jamón",2.0f,"foto"));

        usuarios.add(new Usuario(1,"usuario1","pass1","666111111",2));
        usuarios.add(new Usuario(2,"usuario1","pass2","666222222",2));
        usuarios.add(new Usuario(3,"camarero","camarero","666333333",1));
        usuarios.add(new Usuario(4,"admin","admin","666444444",0));

        ArrayList<Producto> pedido1=new ArrayList<>();
        ArrayList<Integer> cantidad1=new ArrayList<>();
        ArrayList<Producto> pedido2=new ArrayList<>();
        ArrayList<Integer> cantidad2=new ArrayList<>();
        pedido1.add(productos.get(0));cantidad1.add(1);
        pedido1.add(productos.get(2));cantidad1.add(3);
        pedido1.add(productos.get(4));cantidad1.add(2);
        pedido2.add(productos.get(1));cantidad2.add(10);
        pedido2.add(productos.get(3));cantidad2.add(1);

        pedidos.add(new Pedido(1, usuarios.get(0),pedido1,cantidad1));
        pedidos.add(new Pedido(1, usuarios.get(1),pedido2,cantidad2));

    }

}//Fin Class BDPruebas