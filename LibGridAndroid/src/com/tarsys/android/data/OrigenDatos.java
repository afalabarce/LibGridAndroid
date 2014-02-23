/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tarsys.android.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author TaRSyS
 */
public class OrigenDatos implements Serializable, Iterable<JsonObject>
{
    //<editor-fold defaultstate="collapsed" desc="Variables privadas a la clase">
    
    private final ArrayList<String> columnas = new ArrayList<String>();
    private JsonArray datos = new JsonArray();

    //</editor-fold>        
    
    //<editor-fold defaultstate="collapsed" desc="Métodos públicos de la clase">
    
    /**
     * Obtiene las columnas del origen de datos
     * @return
     */
    public ArrayList<String> getColumnas() {
        return columnas;
    }
    
    /**
     * Obtiene el número de elementos del origen de datos
     * @return
     */
    public int size(){
        return this.datos.size();
    }
    
    /**
     * Obtiene el dato de la posición i-esima
     * @param i
     * @return Dato en la posición i-ésima, null en caso de índice incorrecto
     */
    public JsonObject getDato(int i){
        return i >= 0 && i < this.size() ? this.datos.get(i).getAsJsonObject() : null;
    }
    
    /**
     * Obtiene el objeto JsonArray asociado al origen de datos
     * @return
     */
    public JsonArray getDatos() {
        return datos;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores de la clase">
    
    public OrigenDatos(){
        
    }
    public OrigenDatos (JsonArray jArray){
        if (jArray != null && jArray.size() > 0){
            // cogemos el primer objeto del array, para sacarle sus claves...
            try{
                JsonObject obj = jArray.get(0).getAsJsonObject();
                if (obj != null){
                    for (Map.Entry<String, JsonElement>e : obj.entrySet()) this.columnas.add(e.getKey());
                    
                }
                this.datos = jArray;
            }catch(Exception ex){
                this.datos = new JsonArray();
            }
        }
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos de la interfaz Iterable<JsonObject>">
    
    public Iterator<JsonObject> iterator() {
        Iterator<JsonObject> it = new Iterator<JsonObject>() {
            
            private int currentIndex = 0;
            
            @Override
            public boolean hasNext() {
                return currentIndex < OrigenDatos.this.datos.size() && OrigenDatos.this.datos.get(currentIndex) != null;
            }
            
            @Override
            public JsonObject next() {
                return OrigenDatos.this.datos.get(currentIndex++).getAsJsonObject();
            }
            
            @Override
            public void remove() {
                // TODO Auto-generated method stub
            }
        };
        return it;
        
    }
    
    //</editor-fold>
}
