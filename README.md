LibGridAndroid
==============

Implementa una vista del tipo grid de datos con soporte para agrupar columnas

Ejemplo: main.xml
-------------------------------------------------------------------------------------
&lt;?xml version="1.0" encoding="utf-8"?&gt;
&lt;LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              xmlns:tarsys="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#f3f3f3"
    &gt;    
&lt;com.tarsys.android.view.GridDatos 
                            android:id="@+id/gridDatos"
                            android:background="#c3d3e3"
                            android:layout_width="match_parent"
                            android:layout_height="match_parent"
                            tarsys:colorFondoCabeceraSolido="false"
                            tarsys:colorFondoDatosSolido="true"
                            tarsys:colorFondoFilaAgrupacionSolido="false"
                            tarsys:colorFilaAgrupacion="#c3d3e3"
                            tarsys:colorTextoAgrupacion="#000000"
                            tarsys:tamTextoAgrupacion="12sp"
                            tarsys:colorFilaImpar="#e3e3e3"
                            tarsys:colorFilaPar="#93e383"
                            tarsys:altoCabecera="20dp"
                            tarsys:separacionCeldasIzquierda="2dp"
                            tarsys:separacionCeldasDerecha="2dp"
                            tarsys:margenCeldasDerecha="2dp"
                            tarsys:margenCeldasArriba="2dp"
                            tarsys:textoProgresoCarga="Cargando datos..."
                            tarsys:tituloProgresoCarga=""
/&gt;
&lt;/LinearLayout&gt;
-------------------------------------------------------------------------------------
En la actividad principal:

public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.main);
            gridDatos = (GridDatos) this.findViewById(R.id.gridDatos);
            
            //&lt;editor-fold defaultstate="collapsed" desc="Columnas del grid"&gt;
            
            ColumnaGridDatos colX = new ColumnaGridDatos("Seleccion", TipoDatoColumna.Boolean, "X",5);
            colX.setColorFondoCabecera(Color.GRAY);                        
            
            ColumnaGridDatos colCodigo = new ColumnaGridDatos("Cuenta", TipoDatoColumna.Texto, "Código",16);
            colCodigo.setColorFondoCabecera(Color.GRAY);
            
            ColumnaGridDatos colNombre = new ColumnaGridDatos("Nombre", TipoDatoColumna.Texto, "Nombre",50);
            colNombre.setColorFondoCabecera(Color.GRAY);
            
            ColumnaGridDatos colValor = new ColumnaGridDatos("Valor", TipoDatoColumna.NumeroDecimal, "Valor",18);
            colValor.setFormatoDatosColumna("#,##0.00 €");
            colValor.setColorFondoCabecera(Color.GRAY);
            colValor.setAlineacionDatos(AlineacionDatos.Derecha);
            
            gridDatos.addColumna(colX);
            gridDatos.addColumna(colCodigo);
            gridDatos.addColumna(colNombre);
            gridDatos.addColumna(colValor);
            
            //&lt;/editor-fold&gt;
            
            //&lt;editor-fold defaultstate="collapsed" desc="Origen Datos"&gt;
            
            JsonArray array = new JsonArray();
            
            for(int i = 1; i &lt; 10; i++){
                for(int j = 1; j &lt; i + 5; j++){
                    JsonObject o1 = new JsonObject();
                    o1.addProperty("Seleccion", j &lt;= 3 || j &gt;=7 );
                    o1.addProperty("Cuenta", "4300000000" + String.valueOf(i));
                    o1.addProperty("Nombre", "Nombre cuenta 4300000000" + String.valueOf(i));
                    o1.addProperty("Valor", 123.45 + j * 2.34);
                    array.add(o1);
                }
            }
            //&lt;/editor-fold&gt;            
            
            gridDatos.setControladorColumnas(new ClickHeaderListener() {

                public void Click(Object sender, ColumnaGridDatos columna)
                {
                    ((GridDatos)sender).GroupBy(columna.getNombreColumna());
                }
            });
            gridDatos.setOrigenDatos(new OrigenDatos(array));            
            
            
        }catch(Exception ex){
            // .....
        }
    }

