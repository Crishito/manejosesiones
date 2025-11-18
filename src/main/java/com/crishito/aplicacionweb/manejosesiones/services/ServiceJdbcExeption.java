package com.crishito.aplicacionweb.manejosesiones.services;

public class ServiceJdbcExeption extends RuntimeException{
    /*
    * implementamos un constructor que invoca al constructor padre
    * y recibe un parametro de tipo string llamado mensaje*/

    public ServiceJdbcExeption(String menssage){
        //Llamamos al constructor padre para mostrar el mensaje
        super(menssage);

    }

    /*
     * Implementamos un constructor que recibe dos parámetros
     * de tipo String message y tambien la causa del error
     */

    public ServiceJdbcExeption(String menssage, Throwable cause){
        super(menssage,cause);
    }
}
