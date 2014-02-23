LibGridAndroid
==============

Implementa una vista del tipo grid de datos con soporte para agrupar columnas

Ejemplo: main.xml
-------------------------------------------------------------------------------------
&lt;?xml version="1.0" encoding="utf-8"?&gt;<br/>
&lt;LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"<br/>
              xmlns:tarsys="http://schemas.android.com/apk/res-auto"<br/>
    android:orientation="vertical"<br/>
    android:layout_width="match_parent"<br/>
    android:layout_height="match_parent"<br/>
    android:background="#f3f3f3"<br/>
    &gt;    <br/>
&lt;com.tarsys.android.view.GridDatos <br/>
                            android:id="@+id/gridDatos"<br/>
                            android:background="#c3d3e3"<br/>
                            android:layout_width="match_parent"<br/>
                            android:layout_height="match_parent"<br/>
                            tarsys:colorFondoCabeceraSolido="false"<br/>
                            tarsys:colorFondoDatosSolido="true"<br/>
                            tarsys:colorFondoFilaAgrupacionSolido="false"<br/>
                            tarsys:colorFilaAgrupacion="#c3d3e3"<br/>
                            tarsys:colorTextoAgrupacion="#000000"<br/>
                            tarsys:tamTextoAgrupacion="12sp"<br/>
                            tarsys:colorFilaImpar="#e3e3e3"<br/>
                            tarsys:colorFilaPar="#93e383"<br/>
                            tarsys:altoCabecera="20dp"<br/>
                            tarsys:separacionCeldasIzquierda="2dp"<br/>
                            tarsys:separacionCeldasDerecha="2dp"<br/>
                            tarsys:margenCeldasDerecha="2dp"<br/>
                            tarsys:margenCeldasArriba="2dp"<br/>
                            tarsys:textoProgresoCarga="Cargando datos..."<br/>
                            tarsys:tituloProgresoCarga=""<br/>
/&gt;<br/>
&lt;/LinearLayout&gt;<br/>
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

