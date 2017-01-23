package a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria;




//////////////////LAS TABLAS DE LA BBDD ESTAN AL FINAL DE ESTE DOCUMENTO//////////////////////


import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.ActivityLogin;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.BDFinal;
import a2dam.fila1.grupo.proyecto_trimestre_2_cafeteria.Bd.Usuario;

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








    public static void comprobarUsuario()
    {
        new AsyncTask<Void,Void, ResultSet>()//variables envolventes, no funcionan primitivos
        {
            protected ResultSet doInBackground(Void... voids)//Metodo que se ejecuta en 2º plano
            {
                try
                {
                    Log.e("Hasta Aqui"," Bien");
                    conexion= DriverManager.getConnection("jdbc:mysql://10.10.4.150/base20171", "ubase20171", "pbase20171");
                    sentencia = conexion.createStatement();
                    resultado = null;
                    Log.d("Conexion", "Establecida");
                    publishProgress();//Este metodo llama a metodo onProgressUpdate de abajo: realiza cambios en la UI que desde 2º plano no se puede hacer. No esta en uso

                  String consulta = "Select * from usuarios";///Estas sentencias no se puede usar hasta que dispongamos de BBDD

                    resultado = sentencia.executeQuery(consulta);

                } catch (Exception e) {
                    try
                    {
                        conexion= DriverManager.getConnection("jdbc:mysql://10.10.4.150/base20171", "ubase20171", "pbase20171");
                        sentencia = conexion.createStatement();
                        resultado = null;
                        Log.e("Conexion", "Establecida");
                        publishProgress();//Este metodo llama a metodo onProgressUpdate de abajo: realiza cambios en la UI que desde 2º plano no se puede hacer. No esta en uso

                        String consulta = "Select * from usuarios";///Estas sentencias no se puede usar hasta que dispongamos de BBDD

                        resultado = sentencia.executeQuery(consulta);

                    } catch (Exception ex) {
                        Log.e("Conexion", "Fallida: " + ex.getMessage());
                    }

                    Log.e("FALLO DE CONEXION",""+e.getMessage());
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
//                        if(resultSet.getString("username").equals((ActivityLogin.usuario.getText().toString().trim())))
//                        {
//                            if(resultSet.getString("pass").equals((ActivityLogin.pass.getText().toString().trim())))
//                            {
//                                ActivityLogin.user=true;
//                                ActivityLogin.USER=new Usuario(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4),resultSet.getInt(5));
//
//                                Log.e("DATOS",""+resultSet.getString("username"));
//                            }
//                        }
                        BDFinal.usuarioFinal.add(new Usuario(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4),resultSet.getInt(5)));
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
