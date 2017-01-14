package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;




//////////////////LAS TABLAS DE LA BBDD ESTAN AL FINAL DE ESTE DOCUMENTO//////////////////////


import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Manuel on 14/01/2017.
 */

public class HiloConexionBBDD {


    static Connection conexion;
    static Statement sentencia;
    static ResultSet resultado;

    static int ultimoPedido;




    public static void consultaProductos()
    {
        new AsyncTask<Void,Void, ResultSet>()//variables envolventes, no funcionan primitivos
        {
            protected ResultSet doInBackground(Void... voids)//Metodo que se ejecuta en 2º plano
            {
                try
                {
                    conexion= DriverManager.getConnection("jdbc:mysql://www.iesmurgi.org/usuario2", "invitado", "pinvitado");
                    sentencia = conexion.createStatement();
                    resultado = null;
                    Log.d("Conexion", "Establecida");
                    publishProgress();//Este metodo llama a metodo onProgressUpdate de abajo: realiza cambios en la UI que desde 2º plano no se puede hacer. No esta en uso

//                  String consulta = "Select * from productos";///Estas sentencias no se puede usar hasta que dispongamos de BBDD
                    String consulta="show tables";
                    resultado = sentencia.executeQuery(consulta);

                } catch (Exception e) {
                    Log.e("Conexion", "Fallida: " + e.getMessage());
                }

                return resultado;
            }

//            protected void onProgressUpdate(Void... mensaje)//Metodo lanzado desde el segundo plano que realiza cambios en la UI
//            {
//
//            }

            @Override
            protected void onPostExecute(ResultSet resultSet) {
                super.onPostExecute(resultSet);

                try
                {
                    while (resultSet.next())//prueba de la base de datos
                    {
                        Log.e("", "ID Pedido: " + resultSet.getString(1));
                    }
                    conexion.close();
                    sentencia.close();
                    resultSet.close();

                }
                catch (Exception ex)
                {
                    Log.d("Fallo de cojones","");
                }
            }
        }.execute(null, null, null);
    }//Fin de productos

















    public static void consultaUltimoPedido()
    {
        new AsyncTask<Void,Void, ResultSet>()//variables envolventes, no funcionan primitivos
        {
            protected ResultSet doInBackground(Void... voids)//Metodo que se ejecuta en 2º plano
            {
                try
                {
                    conexion= DriverManager.getConnection("jdbc:mysql://www.iesmurgi.org/usuario2", "invitado", "pinvitado");
                    sentencia = conexion.createStatement();
                    resultado = null;
                    Log.d("Conexion", "Establecida");
                    publishProgress();//Este metodo llama a metodo onProgressUpdate de abajo: realiza cambios en la UI que desde 2º plano no se puede hacer. No esta en uso

//                  String consulta = "Select max(num_pedido) * from pedidos";///Estas sentencias no se puede usar hasta que dispongamos de BBDD
                    String consulta="show tables";




                    resultado = sentencia.executeQuery(consulta);
                    ultimoPedido = resultado.getInt(1);
                    Log.d("Ultimo ","pedido actualizado");

                } catch (Exception e) {
                    Log.e("Conexion", "Fallida: " + e.getMessage());
                }

                return resultado;
            }

//            protected void onProgressUpdate(Void... mensaje)//Metodo lanzado desde el segundo plano que realiza cambios en la UI
//            {
//
//            }

            @Override
            protected void onPostExecute(ResultSet resultSet) {
                super.onPostExecute(resultSet);

                try
                {
                    while (resultSet.next())//prueba de la base de datos
                    {
                        Log.e("", "ID Pedido: " + resultSet.getString(1));
                    }
                    conexion.close();
                    sentencia.close();
                    resultSet.close();

                }
                catch (Exception ex)
                {
                    Log.d("Fallo de cojones","");
                }
            }
        }.execute(null, null, null);
    }//Fin de pedido maximo


















    public static void insertarPedido(String producto)
    {
        new AsyncTask<String,Void, ResultSet>()//variables envolventes, no funcionan primitivos
        {
            protected ResultSet doInBackground(String ... voids)//Metodo que se ejecuta en 2º plano
            {
                try
                {
                    conexion= DriverManager.getConnection("jdbc:mysql://www.iesmurgi.org/usuario2", "invitado", "pinvitado");
                    sentencia = conexion.createStatement();
                    resultado = null;
                    Log.d("Conexion", "Establecida");
                    publishProgress();//Este metodo llama a metodo onProgressUpdate de abajo: realiza cambios en la UI que desde 2º plano no se puede hacer. No esta en uso

//                  String consulta = "Select max(num_pedido) * from pedidos";///Estas sentencias no se puede usar hasta que dispongamos de BBDD
                    String consulta="show tables";


                    sentencia.executeUpdate(consulta);

                } catch (Exception e) {
                    Log.e("Conexion", "Fallida: " + e.getMessage());
                }

                return resultado;
            }

            protected void onProgressUpdate(Void... mensaje)//Metodo lanzado desde el segundo plano que realiza cambios en la UI
            {
                consultaUltimoPedido();
            }

            @Override
            protected void onPostExecute(ResultSet resultSet) {
                super.onPostExecute(resultSet);

                try
                {
                    while (resultSet.next())//prueba de la base de datos
                    {
                        Log.e("", "ID Pedido: " + resultSet.getString(1));
                    }
                    conexion.close();
                    sentencia.close();
                    resultSet.close();

                }
                catch (Exception ex)
                {
                    Log.d("Fallo de cojones","");
                }
            }
        }.execute(producto, null, null);
    }
}


/*
create table productos(
	id_pro int (2) PRIMARY KEY auto_increment,
    nom_pro varchar(40),
    precio float(4,2),
    img varchar(50),
    ingredientes varchar(200)
);

create table clientes(
	id_cli int (2) PRIMARY KEY auto_increment,
    nom_cli varchar(40),
    camarero boolean default false,
    mesa int(2),
    pass int(4)
);

create table pedidos(
	num_pedido int(6) NOT NULL,
	idProducto int(2) NOT NULL,
    idCliente int(2) NOT NULL,
	fecha date NOT NULL,
    cantidad int(2),

    constraint pk_producto_cliente_fecha primary key (num_pedido,idProducto, idCliente),
    constraint fk_pedido_producto foreign key (idProducto) references productos(id_pro),
    constraint fk_pedido_cliente foreign key (idCliente) references clientes(id_cli)
);

-- Inserciones de ejemplo

INSERT into productos (nom_pro,precio,img,ingredientes) values ('cafe',1.3,'url','cafe y leche');
INSERT into productos (nom_pro,precio,img,ingredientes) values ('cafe con leche',1.3,'url','cafe y leche');
INSERT into productos (nom_pro,precio,img,ingredientes) values ('tostada mantequilla',1.3,'url','cafe y leche');
INSERT into clientes (nom_cli) values ('juan');
INSERT into pedidos values (1,1,1,curdate(),1);


-- Consultas para el proyecto

-- select max(num_pedido) from pedidos; para saber cual es el ultimo pedido

-- INSERT into clientes (nom_cli) values ('juan'); para agregar cliente

-- select nom_cli, nom_pro from productos, pedidos,clientes where productos.id_pro=pedidos.idProducto and pedidos.idCliente=clientes.id_cli and num_pedido=1; para saber los nombre de productos de un pedido concreto por nombre de cliente
--



--

 */