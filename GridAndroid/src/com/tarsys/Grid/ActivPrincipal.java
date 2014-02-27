package com.tarsys.Grid;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tarsys.android.data.OrigenDatos;
import com.tarsys.android.view.GridDatos;
import com.tarsys.android.view.listeners.ClickHeaderListener;
import com.tarsys.android.view.util.AlineacionDatos;
import com.tarsys.android.view.util.ColumnaGridDatos;
import com.tarsys.android.view.util.TipoDatoColumna;
import com.tarsys.android.view.util.TipoResumenColumna;


public class ActivPrincipal extends Activity
{
    private GridDatos gridDatos;    
    
    /** Called when the activity is first created.
     * @param savedInstanceState */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.main);
            gridDatos = (GridDatos) this.findViewById(R.id.gridDatos);
            
            //<editor-fold defaultstate="collapsed" desc="Columnas del grid">
            
            ColumnaGridDatos colX = new ColumnaGridDatos("Seleccion", TipoDatoColumna.Boolean, "X",5,false, 1);
            colX.setColorFondoCabecera(Color.GRAY);                        
            
            ColumnaGridDatos colCodigo = new ColumnaGridDatos("Cuenta", TipoDatoColumna.Texto, "Código",16,true,2);
            colCodigo.setColorFondoCabecera(Color.GRAY);
            
            ColumnaGridDatos colNombre = new ColumnaGridDatos("Nombre", TipoDatoColumna.Texto, "Nombre",50,false,3);
            colNombre.setColorFondoCabecera(Color.GRAY);
            
            ColumnaGridDatos colValor = new ColumnaGridDatos("Valor", TipoDatoColumna.NumeroDecimal, "Valor",18,false, 4);
            colValor.setFormatoDatosColumna("#,##0.00 €");
            colValor.setTipoResumenColumna(TipoResumenColumna.Sumatorio);
            colValor.setColorFondoCabecera(Color.GRAY);
            colValor.setAlineacionDatos(AlineacionDatos.Derecha);
            
            gridDatos.addColumna(colX);
            gridDatos.addColumna(colCodigo);
            gridDatos.addColumna(colNombre);
            gridDatos.addColumna(colValor);
            
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Origen Datos">
            
            JsonArray array = new JsonArray();
            
            for(int i = 1; i < 10; i++){
                for(int j = 1; j < i + 5; j++){
                    JsonObject o1 = new JsonObject();
                    o1.addProperty("Seleccion", j <= 3 || j >=7 );
                    o1.addProperty("Cuenta", "4300000000" + String.valueOf(i));
                    o1.addProperty("Nombre", "Nombre cuenta 4300000000" + String.valueOf(i));
                    o1.addProperty("Valor", 123.45 + j * 2.34);
                    array.add(o1);
                }
            }
            //</editor-fold>            
            
            gridDatos.setControladorColumnas(new ClickHeaderListener() {

                public void Click(Object sender, ColumnaGridDatos columna)
                {
                    ((GridDatos)sender).GroupBy(columna.getNombreColumna());
                }
            });
            gridDatos.setOrigenDatos(new OrigenDatos(array));            
            
            
        }catch(Exception ex){
            if (ex != null){
                
            }
        }
    }
}
