/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tarsys.android.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tarsys.android.data.OrigenDatos;
import com.tarsys.android.view.listeners.ClickHeaderListener;
import com.tarsys.android.view.listeners.ClickRowListener;
import com.tarsys.android.view.util.ColumnaGridDatos;
import com.tarsys.android.view.util.TipoDatoColumna;
import com.tarsys.android.view.util.TipoResumenColumna;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author TaRSyS
 */
public class GridDatos extends LinearLayout
{
    
    //<editor-fold defaultstate="collapsed" desc="Variables privadas">
    
    private OrigenDatos origenDatos;
    private final Context contexto;
    private ArrayList<ColumnaGridDatos> columnas;
    private LinkedHashMap<String, OrigenDatos> vistaDatos;
    private HorizontalScrollView hScroller;
    private LinearLayout lytPanelGrupos;
    private LinearLayout lytTabular;
    private ScrollView vScroller;
    private TableLayout tablaCabecera;
    private TableLayout tablaDatos;
    private String tituloProgresoCarga = "";
    private String textoProgresoCarga = "";
    private boolean refrescarDatos = false;
    private float densidad;
    
    private int separacionCeldasIzquierda = 0;
    private int separacionCeldasDerecha = 0;
    private int separacionCeldasArriba = 0;
    private int separacionCeldasAbajo = 0;
    
    private boolean colorFondoCabeceraSolido = true;
    private boolean colorFondoDatosSolido = true;
    
    private boolean colorFondoFilaAgrupacionSolido = true;
    private int colorFilaAgrupacion = Color.LTGRAY;
    private int colorTextoAgrupacion = Color.BLACK;
    private float tamTextoAgrupacion = 12;
    private int colorFilaImpar = Color.TRANSPARENT;
    private int colorFilaPar = Color.TRANSPARENT;
    
    
    private int margenCeldasIzquierda = 0;
    private int margenCeldasDerecha = 0;
    private int margenCeldasArriba = 0;
    private int margenCeldasAbajo = 0;
    private float altoCabecera = TableRow.LayoutParams.MATCH_PARENT;
    
    private boolean mostrarResumenGrupos = false;
    private boolean mostrarResumenGeneral = false;
    private boolean mostrarPanelAgrupacion = true;
    
    private ArrayList<String>columnasAgrupacion;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores de la clase">
    
    public GridDatos(Context context)
    {
        this(context,null);
    }
    
    public GridDatos(Context context, AttributeSet attrs)
    {
        super(context, attrs);        
        this.contexto = context;
        this.setOrientation(LinearLayout.VERTICAL);
        this.columnas = new ArrayList<ColumnaGridDatos>();       
        this.densidad = this.contexto.getResources().getDisplayMetrics().density;        
        TypedArray atributos = context.obtainStyledAttributes(attrs, R.styleable.GridDatos);        
        this.colorFondoCabeceraSolido = atributos.getBoolean(R.styleable.GridDatos_colorFondoCabeceraSolido, true);
        this.colorFondoDatosSolido = atributos.getBoolean(R.styleable.GridDatos_colorFondoDatosSolido, true);
        this.colorFilaImpar = atributos.getInt(R.styleable.GridDatos_colorFilaImpar, Color.TRANSPARENT);
        this.colorFilaPar = atributos.getInt(R.styleable.GridDatos_colorFilaPar, Color.TRANSPARENT);
        
        this.colorFondoFilaAgrupacionSolido = atributos.getBoolean(R.styleable.GridDatos_colorFondoFilaAgrupacionSolido, true); 
        this.colorFilaAgrupacion = atributos.getInt(R.styleable.GridDatos_colorFilaAgrupacion, Color.LTGRAY);
        this.colorTextoAgrupacion = atributos.getInt(R.styleable.GridDatos_colorTextoAgrupacion, Color.BLACK);
        this.tamTextoAgrupacion = atributos.getDimension(R.styleable.GridDatos_tamTextoAgrupacion, 12);
        this.altoCabecera = atributos.getDimension(R.styleable.GridDatos_altoCabecera, TableRow.LayoutParams.MATCH_PARENT);
        
        this.separacionCeldasAbajo = (int)atributos.getDimension(R.styleable.GridDatos_separacionCeldasAbajo, 0);
        this.separacionCeldasArriba = (int)atributos.getDimension(R.styleable.GridDatos_separacionCeldasArriba, 0);
        this.separacionCeldasIzquierda = (int)atributos.getDimension(R.styleable.GridDatos_separacionCeldasIzquierda, 0);
        this.separacionCeldasDerecha = (int)atributos.getDimension(R.styleable.GridDatos_separacionCeldasDerecha, 0);
        
        this.margenCeldasAbajo = (int)atributos.getDimension(R.styleable.GridDatos_margenCeldasAbajo, 0);
        this.margenCeldasArriba = (int)atributos.getDimension(R.styleable.GridDatos_margenCeldasArriba, 0);
        this.margenCeldasIzquierda = (int)atributos.getDimension(R.styleable.GridDatos_margenCeldasIzquierda, 0);
        this.margenCeldasDerecha = (int)atributos.getDimension(R.styleable.GridDatos_margenCeldasDerecha, 0);
        this.tituloProgresoCarga = atributos.getString(R.styleable.GridDatos_tituloProgresoCarga);
        this.textoProgresoCarga = atributos.getString(R.styleable.GridDatos_textoProgresoCarga);
        
        this.mostrarResumenGrupos = atributos.getBoolean(R.styleable.GridDatos_mostrarResumenGrupos, false);
        this.mostrarResumenGeneral = atributos.getBoolean(R.styleable.GridDatos_mostrarResumenGeneral, false);
        this.mostrarPanelAgrupacion = atributos.getBoolean(R.styleable.GridDatos_mostrarPanelAgrupacion, true);
        this.columnasAgrupacion = new ArrayList<String>();        
        this.InicializarObjetos();
    }
    
    
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="Controladores de eventos">
    
    private static ClickHeaderListener controladorColumnas;
    public ClickHeaderListener getControladorColumnas()
    {
        return GridDatos.controladorColumnas;
    }

    public void setControladorColumnas(ClickHeaderListener controladorColumnas)
    {
        GridDatos.controladorColumnas = controladorColumnas;
    }
    
    private static ClickRowListener clickFilas;
    public ClickRowListener getClickFilas() {
        return GridDatos.clickFilas;
    }

    public void setClickFilas(ClickRowListener clickFilas) {
        GridDatos.clickFilas = clickFilas;
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Métodos privados">
    
    private void EstablecerPadding()
    {
        this.tablaCabecera.setPadding(this.separacionCeldasIzquierda, this.separacionCeldasArriba, this.separacionCeldasDerecha, this.separacionCeldasAbajo);
        this.tablaDatos.setPadding(this.separacionCeldasIzquierda, this.separacionCeldasArriba, this.separacionCeldasDerecha, this.separacionCeldasAbajo);
        this.refrescarDatos = true;
        this.requestLayout();
    }
    
    private void RefrescarPanelGrupos(){        
        if (!this.mostrarPanelAgrupacion) this.lytPanelGrupos.setVisibility(LinearLayout.GONE);
        this.lytPanelGrupos.removeAllViews();
        for(String colAgrup : this.columnasAgrupacion){
            TextView btnCol = new TextView (this.contexto);
            ColumnaGridDatos col = this.ColumnaPorNombre(colAgrup);
            
            btnCol.setText(col.getTitulo());
            btnCol.setTextColor(Color.BLACK);
            btnCol.setTextSize(TypedValue.COMPLEX_UNIT_SP, GridDatos.this.tamTextoAgrupacion / densidad);
            btnCol.setTag(col);
            int []coloresCabecera = new int[2]; coloresCabecera[0] = Color.TRANSPARENT; coloresCabecera[1] = GridDatos.this.colorFilaAgrupacion;
            GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, coloresCabecera);
            btnCol.setBackgroundDrawable(gd);
            btnCol.setPadding(5, 5, 5, 5);
            btnCol.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ((LinearLayout.LayoutParams)btnCol.getLayoutParams()).leftMargin = 5;            
            ((LinearLayout.LayoutParams)btnCol.getLayoutParams()).topMargin = 5;
            ((LinearLayout.LayoutParams)btnCol.getLayoutParams()).bottomMargin = 5;
            
            
            
            btnCol.setOnClickListener(new OnClickListener() {

                public void onClick(View arg0)
                {
                    GridDatos.this.columnasAgrupacion.remove(((ColumnaGridDatos)arg0.getTag()).getNombreColumna());
                    ((ColumnaGridDatos)arg0.getTag()).setVisible(true);
                    GridDatos.this.lytPanelGrupos.removeView(arg0);
                    GridDatos.this.GroupBy("");
                }
            });
            
            this.lytPanelGrupos.addView(btnCol);
        }
    }
    
    private void InicializarObjetos(){
        this.removeAllViews();        
        this.refrescarDatos = false;
        
        this.lytPanelGrupos = new LinearLayout(this.contexto);
        this.lytPanelGrupos.setOrientation(LinearLayout.HORIZONTAL);
        this.lytPanelGrupos.setBackgroundColor(Color.LTGRAY);
        
        this.hScroller = new HorizontalScrollView(this.contexto);
        this.hScroller.setScrollBarStyle(HorizontalScrollView.SCROLLBARS_OUTSIDE_OVERLAY);
        this.vScroller = new ScrollView(this.contexto);
        this.lytTabular = new LinearLayout(this.contexto);
        this.tablaCabecera = new TableLayout(this.contexto);
        this.tablaDatos = new TableLayout(this.contexto);        
                        
        this.lytTabular.setOrientation(LinearLayout.VERTICAL);
        this.lytTabular.addView(this.tablaCabecera,0, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        this.vScroller.addView(this.tablaDatos, new ScrollView.LayoutParams(ScrollView.LayoutParams.WRAP_CONTENT, ScrollView.LayoutParams.WRAP_CONTENT));
        
        this.lytTabular.addView(this.vScroller, 1, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        
        this.hScroller.setVisibility(HorizontalScrollView.VISIBLE);
        this.lytTabular.setVisibility(LinearLayout.VISIBLE);
        this.tablaCabecera.setVisibility(TableLayout.VISIBLE);
        this.vScroller.setVisibility(ScrollView.VISIBLE);
        this.tablaDatos.setVisibility(TableLayout.VISIBLE);
        
        // Agregamos el linear laout al horizontal scroller...
        this.hScroller.addView(this.lytTabular, new HorizontalScrollView.LayoutParams(HorizontalScrollView.LayoutParams.WRAP_CONTENT,HorizontalScrollView.LayoutParams.WRAP_CONTENT));
        
        this.EstablecerPadding();
    }
        
    private int NroColumnasVisibles(){
        int retorno = 0;
        
        for(ColumnaGridDatos c : this.columnas) retorno += c.isVisible() ? 1 : 0;
        
        return retorno;
    }
    
    private ColumnaGridDatos ColumnaPorNombre(String nombreColumna){
        ColumnaGridDatos retorno = null;
        
        for(ColumnaGridDatos col : this.columnas){
            if (col.getNombreColumna().equals(nombreColumna)){
                retorno = col;
                break;
            }
        }
        
        return retorno;
    }
       
    private void CargarDatos()
    {
        this.refrescarDatos = false;
        this.removeAllViews();
        this.InicializarObjetos();
        if (this.columnas != null && this.columnas.size() > 0){  
            
            this.RefrescarPanelGrupos();
            // Si tenemos columnas, lo primero, es preparar el header...
            
            //<editor-fold defaultstate="collapsed" desc="Definición de la cabecera del grid">
            this.tablaCabecera.removeAllViews();
            
            TableRow rowHeader = new TableRow(this.contexto);
            
            for(ColumnaGridDatos columna : this.columnas){
                TextView celda = new TextView (this.contexto);
                celda.setTextSize(TypedValue.COMPLEX_UNIT_SP, columna.getTamFuenteTitulo());
                int estiloBold = columna.isNegritaTexto() ? Typeface.BOLD : Typeface.NORMAL;
                int estiloItalic = columna.isCursivaTexto() ? Typeface.ITALIC : Typeface.NORMAL;
                if (columna.isSubrayadoTexto()) celda.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                celda.setTypeface(celda.getTypeface(), estiloBold | estiloItalic);
                if (!this.colorFondoCabeceraSolido){
                    int []coloresCabecera = new int[2]; coloresCabecera[0] = Color.TRANSPARENT; coloresCabecera[1] = columna.getColorFondoCabecera();
                    GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, coloresCabecera);
                    celda.setBackgroundDrawable(gd);
                }else{
                    celda.setBackgroundColor(columna.getColorFondoCabecera());
                }
                celda.setTextColor(columna.getColorTextoCabecera());
                celda.setGravity(Gravity.CENTER_VERTICAL);
                if (columna.getTipoDatoColumna() == TipoDatoColumna.Boolean) celda.setGravity(Gravity.CENTER);
                celda.setPadding(this.separacionCeldasIzquierda, this.separacionCeldasArriba, this.separacionCeldasDerecha, this.separacionCeldasAbajo);
                celda.setMaxLines(1);
                TableRow.LayoutParams layout = new TableRow.LayoutParams(columna.getAnchoColumna() * 7 * (int)this.densidad, (int)this.altoCabecera);
                layout.leftMargin = this.margenCeldasIzquierda; layout.bottomMargin = this.margenCeldasAbajo;
                layout.rightMargin = this.margenCeldasDerecha; layout.topMargin = this.margenCeldasArriba;
                celda.setLayoutParams(layout);
                celda.setVisibility(columna.isVisible() ? TextView.VISIBLE : TextView.GONE);
                celda.setTag(columna);
                celda.setText(columna.getTitulo());
                if (GridDatos.controladorColumnas != null){
                    celda.setOnClickListener(new OnClickListener() {
                        
                        public void onClick(View arg0)
                        {
                            GridDatos.controladorColumnas.Click(GridDatos.this, (ColumnaGridDatos)arg0.getTag());
                        }
                    });
                }
                rowHeader.addView(celda);
            }
            // Para terminar, agregamos el row atributos la tabla de cabecera
            this.tablaCabecera.addView(rowHeader, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            this.tablaCabecera.setVisibility(TableLayout.VISIBLE);
            //</editor-fold>
            
            // Agregamos los datos
            AsyncTask taskCargaDatos = new AsyncTask() {
                
                private TableRow FilaAgrupacion(String textoAgrupacion){
                    TableRow filaAgrup = new TableRow(GridDatos.this.contexto);      
                    
                    TextView celdaAgrup = new TextView(GridDatos.this.contexto);
                    celdaAgrup.setMaxLines(1);
                    celdaAgrup.setPadding(GridDatos.this.separacionCeldasIzquierda, GridDatos.this.separacionCeldasArriba, GridDatos.this.separacionCeldasDerecha, GridDatos.this.separacionCeldasAbajo);
                    celdaAgrup.setTypeface(celdaAgrup.getTypeface(), Typeface.BOLD);
                    celdaAgrup.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    ((TableRow.LayoutParams)celdaAgrup.getLayoutParams()).span = GridDatos.this.columnas.size();
                    celdaAgrup.setTextSize(TypedValue.COMPLEX_UNIT_SP, GridDatos.this.tamTextoAgrupacion / densidad);
                    celdaAgrup.setText(textoAgrupacion);
                    if (!GridDatos.this.colorFondoFilaAgrupacionSolido){
                        int []coloresCabecera = new int[2]; coloresCabecera[0] = Color.TRANSPARENT; coloresCabecera[1] = GridDatos.this.colorFilaAgrupacion;
                        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, coloresCabecera);
                        celdaAgrup.setBackgroundDrawable(gd);
                    }else{
                        celdaAgrup.setBackgroundColor(GridDatos.this.colorFilaAgrupacion);
                    }
                    celdaAgrup.setTextColor(GridDatos.this.colorTextoAgrupacion);
                    filaAgrup.addView(celdaAgrup);
                    
                    return filaAgrup;
                }
                
                private TableRow FilaDatos (final JsonObject dato, int idx){
                    TableRow filaDato = new TableRow(GridDatos.this.contexto);
                    
                    for(final ColumnaGridDatos columna : GridDatos.this.columnas){                                                
                        TextView celda = new TextView (GridDatos.this.contexto);
                        celda.setTextSize(TypedValue.COMPLEX_UNIT_SP, columna.getTamFuenteCelda());
                        int estiloBold = columna.isNegritaTexto() ? Typeface.BOLD : Typeface.NORMAL;
                        int estiloItalic = columna.isCursivaTexto() ? Typeface.ITALIC : Typeface.NORMAL;
                        if (columna.isSubrayadoTexto()) celda.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                        celda.setTypeface(celda.getTypeface(), estiloBold | estiloItalic);
                        celda.setTextColor(columna.getColorTextoDatos());
                        if (GridDatos.clickFilas != null)                     {
                            celda.setOnClickListener(new OnClickListener() {
                                public void onClick(View arg0) {
                                    GridDatos.clickFilas.Click(GridDatos.this, dato, columna);
                                }
                            });
                        }
                        celda.setPadding(GridDatos.this.separacionCeldasIzquierda, GridDatos.this.separacionCeldasArriba, GridDatos.this.separacionCeldasDerecha, GridDatos.this.separacionCeldasAbajo);

                        TableRow.LayoutParams layout = new TableRow.LayoutParams(columna.getAnchoColumna() * 7 * (int)GridDatos.this.densidad, TableRow.LayoutParams.MATCH_PARENT);
                        layout.leftMargin = GridDatos.this.margenCeldasIzquierda; layout.bottomMargin = GridDatos.this.margenCeldasAbajo;
                        layout.rightMargin = GridDatos.this.margenCeldasDerecha; layout.topMargin = GridDatos.this.margenCeldasArriba;
                        celda.setLayoutParams(layout);
                        ArrayList<Integer> coloresFondo = new ArrayList<Integer>();
                        int colorFondo = idx % 2 == 0? GridDatos.this.colorFilaPar : GridDatos.this.colorFilaImpar;

                        if (colorFondo != Color.TRANSPARENT) coloresFondo.add(colorFondo);
                        if (columna.getColorFondoDatos() != Color.TRANSPARENT)
                            coloresFondo.add(columna.getColorFondoDatos());
                        if (coloresFondo.size() > 1){
                            int [] c = new int[coloresFondo.size()];
                            int i = 0;
                            for(int c1 : coloresFondo)  c[i++] = c1;
                            GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, c);
                            celda.setBackgroundDrawable(gd);
                        }else{
                            int cFondo = columna.getColorFondoDatos() != Color.TRANSPARENT? columna.getColorFondoDatos() : colorFondo;

                            if (!GridDatos.this.colorFondoDatosSolido){
                                int []coloresCabecera = new int[2]; coloresCabecera[0] = Color.TRANSPARENT; coloresCabecera[1] = cFondo;
                                GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, coloresCabecera);
                                celda.setBackgroundDrawable(gd);
                            }else{
                                celda.setBackgroundColor(cFondo);
                            }
                        }
                        celda.setGravity(columna.getAlineacionCabecera().value());
                        celda.setVisibility(columna.isVisible() ? TextView.VISIBLE : TextView.GONE);
                        if (columna.getTipoDatoColumna() == TipoDatoColumna.Boolean){
                            celda.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                            celda.setGravity(Gravity.CENTER);
                            celda.setText(dato.get(columna.getNombreColumna()).getAsBoolean() ? "x" : "");
                        }else{
                            //<editor-fold defaultstate="collapsed" desc="Formateo de datos">
                            String texto = "";
                            if (!dato.get(columna.getNombreColumna()).isJsonNull()){
                                switch (columna.getTipoDatoColumna()){
                                    case NumeroEntero:
                                    {
                                        try{
                                            int valorNum = dato.get(columna.getNombreColumna()).getAsInt();
                                            texto = columna.getFormatoDatosColumna().trim().isEmpty()? dato.get(columna.getNombreColumna()).getAsString() :  new DecimalFormat(columna.getFormatoDatosColumna()).format(valorNum);
                                        }catch (NumberFormatException ex){
                                            texto = "NaN";
                                        }
                                        break;
                                    }
                                    case NumeroDecimal:
                                    {
                                        try{
                                            float valorNum = dato.get(columna.getNombreColumna()).getAsFloat();
                                            texto = columna.getFormatoDatosColumna().trim().isEmpty()? dato.get(columna.getNombreColumna()).getAsString() :  new DecimalFormat(columna.getFormatoDatosColumna()).format(valorNum);
                                        }catch (NumberFormatException ex){
                                            texto = "NaN";
                                        }

                                        break;
                                    }
                                    case Fecha:
                                    {                                                                                                
                                        try{
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                                            Date fecha = sdf.parse(dato.get(columna.getNombreColumna()).getAsString());
                                            // Si se ha podido crear la fecha, la formateamos a lo que deseemos...
                                            texto = columna.getFormatoDatosColumna().trim().isEmpty()? sdf.format(fecha) :  new SimpleDateFormat(columna.getFormatoDatosColumna()).format(fecha);
                                        }catch(ParseException ex){
                                            texto = "NaD";
                                        }
                                        break;
                                    }
                                    case Texto:
                                        texto = dato.get(columna.getNombreColumna()).isJsonNull()? "" : dato.get(columna.getNombreColumna()).getAsString();
                                        break;                                            
                                }
                            }
                            if (columna.getTipoDatoColumna() != TipoDatoColumna.Boolean) celda.setGravity(columna.getAlineacionDatos().value());
                            celda.setText(texto);
                            //</editor-fold>

                        }
                        filaDato.addView(celda);
                    }
                    return filaDato;
                }
                
                private TableRow FilaTotalGrupo (String tituloTotales, OrigenDatos datosGrupo, ArrayList<ColumnaGridDatos> columnasConResumen){
                    TableRow rowResumenGrupo = new TableRow(GridDatos.this.contexto);
                    if (columnasConResumen.size()>0){                    
                        TextView celdaAgrup = new TextView (GridDatos.this.contexto);
                        int nInvisibles = 0;
                        for (ColumnaGridDatos colAgrup : GridDatos.this.columnas){                                                        
                            if (colAgrup.getPosicion() >= columnasConResumen.get(0).getPosicion()) break;
                            nInvisibles += !colAgrup.isVisible() && !colAgrup.isColumnaAgrupacion()?1:0;
                        }
                        celdaAgrup.setTypeface(celdaAgrup.getTypeface(), Typeface.BOLD);
                        celdaAgrup.setMaxLines(1);
                        celdaAgrup.setPadding(GridDatos.this.separacionCeldasIzquierda, GridDatos.this.separacionCeldasArriba, GridDatos.this.separacionCeldasDerecha, GridDatos.this.separacionCeldasAbajo);
                        TableRow.LayoutParams layoutAgrup = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                        layoutAgrup.leftMargin = GridDatos.this.margenCeldasIzquierda; layoutAgrup.bottomMargin = GridDatos.this.margenCeldasAbajo;
                        layoutAgrup.rightMargin = GridDatos.this.margenCeldasDerecha; layoutAgrup.topMargin = GridDatos.this.margenCeldasArriba;
                        celdaAgrup.setLayoutParams(layoutAgrup);
                        ((TableRow.LayoutParams)celdaAgrup.getLayoutParams()).span = columnasConResumen.get(0).getPosicion() - 1 + nInvisibles; //GridDatos.this.columnas.size();
                        celdaAgrup.setTextSize(TypedValue.COMPLEX_UNIT_SP, GridDatos.this.tamTextoAgrupacion / densidad);
                        celdaAgrup.setText(tituloTotales);
                        if (!GridDatos.this.colorFondoFilaAgrupacionSolido){
                            int []coloresCabecera = new int[2]; coloresCabecera[0] = Color.TRANSPARENT; coloresCabecera[1] = GridDatos.this.colorFilaAgrupacion;
                            GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, coloresCabecera);
                            celdaAgrup.setBackgroundDrawable(gd);
                        }else{
                            celdaAgrup.setBackgroundColor(GridDatos.this.colorFilaAgrupacion);
                        }
                        celdaAgrup.setTextColor(GridDatos.this.colorTextoAgrupacion);

                        rowResumenGrupo.addView(celdaAgrup);
                        // Una vez hemos agregado la celda de título de grupo, agregaremos las de totalización...
                        
                        for (ColumnaGridDatos colAgrup : GridDatos.this.columnas){
                            
                            if (colAgrup.getPosicion() >= columnasConResumen.get(0).getPosicion()){
                                TextView celdaTotal = new TextView (GridDatos.this.contexto);
                                celdaTotal.setMaxLines(1);
                                celdaTotal.setTypeface(celdaAgrup.getTypeface(), Typeface.BOLD);
                                celdaTotal.setPadding(GridDatos.this.separacionCeldasIzquierda, GridDatos.this.separacionCeldasArriba, GridDatos.this.separacionCeldasDerecha, GridDatos.this.separacionCeldasAbajo);
                                TableRow.LayoutParams layout = new TableRow.LayoutParams(colAgrup.getAnchoColumna() * 7 * (int)GridDatos.this.densidad, TableRow.LayoutParams.MATCH_PARENT);
                                layout.leftMargin = GridDatos.this.margenCeldasIzquierda; layout.bottomMargin = GridDatos.this.margenCeldasAbajo;
                                layout.rightMargin = GridDatos.this.margenCeldasDerecha; layout.topMargin = GridDatos.this.margenCeldasArriba;
                                celdaTotal.setVisibility(colAgrup.isVisible()? TextView.VISIBLE : TextView.GONE);
                                celdaTotal.setLayoutParams(layout);                                
                                celdaTotal.setTextSize(TypedValue.COMPLEX_UNIT_SP, GridDatos.this.tamTextoAgrupacion / densidad);                                
                                if (!GridDatos.this.colorFondoFilaAgrupacionSolido){
                                    int []coloresCabecera = new int[2]; coloresCabecera[0] = Color.TRANSPARENT; coloresCabecera[1] = GridDatos.this.colorFilaAgrupacion;
                                    GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, coloresCabecera);
                                    celdaTotal.setBackgroundDrawable(gd);
                                }else{
                                    celdaTotal.setBackgroundColor(GridDatos.this.colorFilaAgrupacion);
                                }
                                celdaTotal.setTextColor(GridDatos.this.colorTextoAgrupacion);                                
                                if (colAgrup.getTipoResumenColumna() != TipoResumenColumna.SinResumen){
                                    double valor = 0;
                                    HashMap<String, Double> valoresFormula = new HashMap<String, Double>();
                                    String operadorFormula = "";
                                    if (colAgrup.getTipoResumenColumna() == TipoResumenColumna.Formula){
                                        // Como es una formula, sacamos operando y operador
                                        String rFormula = "\\s*(\\w+)\\s*([\\+|\\-|\\*|\\/])\\s*(\\w+)\\s*";
                                        Pattern pFormula = Pattern.compile(rFormula, Pattern.CASE_INSENSITIVE |Pattern.DOTALL);
                                        Matcher mFormula = pFormula.matcher(colAgrup.getFormulaResumen());
                                        if (mFormula.find()){
                                            operadorFormula = mFormula.group(2);
                                            valoresFormula.put(mFormula.group(1), 0D);
                                            valoresFormula.put(mFormula.group(3), 0D);
                                        }
                                    }
                                    for(JsonObject dato: datosGrupo){
                                        if (dato.get(colAgrup.getNombreColumna()).isJsonNull()) continue;
                                        switch(colAgrup.getTipoResumenColumna()){
                                            case Sumatorio:
                                            case MediaAritmetica:
                                                valor += dato.get(colAgrup.getNombreColumna()).getAsDouble();
                                                break;
                                            case Maximo:
                                                valor = valor < dato.get(colAgrup.getNombreColumna()).getAsDouble() ? dato.get(colAgrup.getNombreColumna()).getAsDouble() : valor;
                                                break;
                                            case Minimo:
                                                valor = valor > dato.get(colAgrup.getNombreColumna()).getAsDouble() ? dato.get(colAgrup.getNombreColumna()).getAsDouble(): valor;
                                                break;                                                                                                                                    
                                            case Formula:
                                            {
                                                if (valoresFormula.size() == 2 && !operadorFormula.isEmpty()){
                                                    double v1 = valoresFormula.get(valoresFormula.keySet().toArray()[0].toString());
                                                    double v2 = valoresFormula.get(valoresFormula.keySet().toArray()[1].toString());
                                                    v1 += dato.get(valoresFormula.keySet().toArray()[0].toString()).isJsonNull()? 0 : dato.get(valoresFormula.keySet().toArray()[0].toString()).getAsDouble(); 
                                                    v2 += dato.get(valoresFormula.keySet().toArray()[1].toString()).isJsonNull()? 0 : dato.get(valoresFormula.keySet().toArray()[1].toString()).getAsDouble(); 
                                                    valoresFormula.put(valoresFormula.keySet().toArray()[0].toString(), v1);
                                                    valoresFormula.put(valoresFormula.keySet().toArray()[1].toString(), v2);
                                                }
                                                break;
                                            }
                                        }
                                    }
                                    if (colAgrup.getTipoResumenColumna() == TipoResumenColumna.MediaAritmetica && datosGrupo.size() > 0){
                                        valor = valor / datosGrupo.size();
                                    }
                                    if (colAgrup.getTipoResumenColumna() == TipoResumenColumna.Recuento){
                                        valor = datosGrupo.size();
                                    }
                                    if (colAgrup.getTipoResumenColumna() == TipoResumenColumna.Formula && valoresFormula.size() == 2 && !operadorFormula.isEmpty()){
                                        double v1 = valoresFormula.get(valoresFormula.keySet().toArray()[0].toString());
                                        double v2 = valoresFormula.get(valoresFormula.keySet().toArray()[1].toString());
                                        if (operadorFormula.equals("+")) valor = v1 + v2;
                                        if (operadorFormula.equals("-")) valor = v1 - v2;
                                        if (operadorFormula.equals("*")) valor = v1 * v2;
                                        if (operadorFormula.equals("/") && v2 != 0) valor = v1 / v2;
                                    }
                                    celdaTotal.setGravity(colAgrup.getAlineacionDatos().value());
                                    celdaTotal.setText(new DecimalFormat(colAgrup.getFormatoDatosColumna()).format(valor));
                                }else{
                                    celdaTotal.setText("");
                                }
                                rowResumenGrupo.addView(celdaTotal);
                                
                            }
                        }
                    }
                    return rowResumenGrupo;
                }
                
                private ProgressDialog dlgProgreso;
                @Override
                protected void onPreExecute()
                {
                    dlgProgreso = ProgressDialog.show(contexto, tituloProgresoCarga, textoProgresoCarga, true, false);                    
                    GridDatos.this.tablaDatos.removeAllViews();                                                                  
                }
                
                @Override
                protected Object doInBackground(Object... arg0)
                {
                    
                    ArrayList<TableRow> filas = new ArrayList<TableRow>();
                    int i = 0;                    
                    if (GridDatos.this.vistaDatos != null){
                        ArrayList<ColumnaGridDatos> resumidas = new ArrayList<ColumnaGridDatos>();
                        for(ColumnaGridDatos c : GridDatos.this.columnas){
                            if (c.getTipoResumenColumna() != TipoResumenColumna.SinResumen){
                                resumidas.add(c);
                            }
                        }
                        for(String vAgrup : GridDatos.this.vistaDatos.keySet()){
                            if (!vAgrup.trim().isEmpty()) filas.add(this.FilaAgrupacion(vAgrup));
                                
                            for(JsonObject dato : GridDatos.this.vistaDatos.get(vAgrup))
                            {                                
                                filas.add(this.FilaDatos(dato, i++));                                    
                            }
                            // Si se debe mostrar un resumen de grupos...
                            if (GridDatos.this.mostrarResumenGrupos && GridDatos.this.columnasAgrupacion.size() > 0){                                
                                //filas.add(this.FilaTotalGrupo(String.format("Total Grupo %s:", vAgrup), GridDatos.this.vistaDatos.get(vAgrup), resumidas));
                                filas.add(this.FilaTotalGrupo(" ", GridDatos.this.vistaDatos.get(vAgrup), resumidas));
                                TableRow trMargen = new TableRow(GridDatos.this.contexto);
                                TextView txRelleno = new TextView(GridDatos.this.contexto);
                                txRelleno.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
                                txRelleno.setText("   ");
                                ((TableRow.LayoutParams)txRelleno.getLayoutParams()).span = GridDatos.this.columnas.size();
                                trMargen.addView(txRelleno);                                                                
                                filas.add(trMargen);
                            }
                        }
                        if (GridDatos.this.mostrarResumenGeneral){
                            filas.add(this.FilaTotalGrupo("Total General:", GridDatos.this.origenDatos, resumidas));
                        }
                    }
                    return filas;
                }
                
                @Override
                protected void onPostExecute(Object result)
                {
                    super.onPostExecute(result); //To change body of generated methods, choose Tools | Templates.                    
                    
                    ArrayList<TableRow> filas = (ArrayList<TableRow>) result;
                    for(TableRow filaDato : filas)
                        GridDatos.this.tablaDatos.addView(filaDato, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    GridDatos.this.tablaDatos.setVisibility(TableLayout.VISIBLE);
                    dlgProgreso.dismiss();
                    
                    GridDatos.this.removeAllViews();
                    GridDatos.this.addView(GridDatos.this.lytPanelGrupos, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    GridDatos.this.addView(GridDatos.this.hScroller, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
                }
            };
            taskCargaDatos.execute();
        }
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Propiedades con Getters y Setters">
        
    public boolean isMostrarPanelAgrupacion()
    {
        return mostrarPanelAgrupacion;
    }

    public void setMostrarPanelAgrupacion(boolean mostrarPanelAgrupacion)
    {
        this.mostrarPanelAgrupacion = mostrarPanelAgrupacion;
    }
    
    
    
    public boolean isMostrarResumenGrupos()
    {
        return mostrarResumenGrupos;
    }

    public void setMostrarResumenGrupos(boolean mostrarResumenGrupos)
    {
        this.mostrarResumenGrupos = mostrarResumenGrupos;
    }

    public boolean isMostrarResumenGeneral()
    {
        return mostrarResumenGeneral;
    }

    public void setMostrarResumenGeneral(boolean mostrarResumenGeneral)
    {
        this.mostrarResumenGeneral = mostrarResumenGeneral;
    }
    
    public float getTamTextoAgrupacion()
    {
        return tamTextoAgrupacion;
    }

    public void setTamTextoAgrupacion(float tamTextoAgrupacion)
    {
        this.tamTextoAgrupacion = tamTextoAgrupacion;
    }
    
    
    public boolean isColorFondoFilaAgrupacionSolido()
    {
        return colorFondoFilaAgrupacionSolido;
    }

    public void setColorFondoFilaAgrupacionSolido(boolean colorFondoFilaAgrupacionSolido)
    {
        this.colorFondoFilaAgrupacionSolido = colorFondoFilaAgrupacionSolido;
    }

    public int getColorFilaAgrupacion()
    {
        return colorFilaAgrupacion;
    }

    public void setColorFilaAgrupacion(int colorFilaAgrupacion)
    {
        this.colorFilaAgrupacion = colorFilaAgrupacion;
    }

    public int getColorTextoAgrupacion()
    {
        return colorTextoAgrupacion;
    }

    public void setColorTextoAgrupacion(int colorTextAgrupacion)
    {
        this.colorTextoAgrupacion = colorTextAgrupacion;
    }
    
    public boolean isColorFondoDatosSolido()
    {
        return colorFondoDatosSolido;
    }

    public void setColorFondoDatosSolido(boolean colorFondoDatosSolido)
    {
        this.colorFondoDatosSolido = colorFondoDatosSolido;
    }
    
    public String getTituloProgresoCarga()
    {
        return tituloProgresoCarga;
    }

    public void setTituloProgresoCarga(String tituloProgresoCarga)
    {
        this.tituloProgresoCarga = tituloProgresoCarga;
    }

    public String getTextoProgresoCarga()
    {
        return textoProgresoCarga;
    }

    public void setTextoProgresoCarga(String textoProgresoCarga)
    {
        this.textoProgresoCarga = textoProgresoCarga;
    }
    
    
    
    public boolean isColorFondoCabeceraSolido()
    {
        return colorFondoCabeceraSolido;
    }

    public void setColorFondoCabeceraSolido(boolean colorFondoCabeceraSolido)
    {
        this.colorFondoCabeceraSolido = colorFondoCabeceraSolido;
    }
    
    public int getColorFilaImpar()
    {
        return colorFilaImpar;
    }
    
    public void setColorFilaImpar(int colorFilaImpar)
    {
        this.colorFilaImpar = colorFilaImpar;
    }
    
    public int getColorFilaPar()
    {
        return colorFilaPar;
    }
    
    public void setColorFilaPar(int colorFilaPar)
    {
        this.colorFilaPar = colorFilaPar;
    }
    
    
    
    public float getAltoCabecera()
    {
        return altoCabecera;
    }
    
    public void setAltoCabecera(float altoCabecera)
    {
        this.altoCabecera = altoCabecera;
    }
    
    
    public int getMargenCeldasIzquierda()
    {
        return margenCeldasIzquierda;
    }
    
    public void setMargenCeldasIzquierda(int margenCeldasIzquierda)
    {
        this.margenCeldasIzquierda = margenCeldasIzquierda;
    }
    
    public int getMargenCeldasDerecha()
    {
        return margenCeldasDerecha;
    }
    
    public void setMargenCeldasDerecha(int margenCeldasDerecha)
    {
        this.margenCeldasDerecha = margenCeldasDerecha;
    }
    
    public int getMargenCeldasArriba()
    {
        return margenCeldasArriba;
    }
    
    public void setMargenCeldasArriba(int margenCeldasArriba)
    {
        this.margenCeldasArriba = margenCeldasArriba;
    }
    
    public int getMargenCeldasAbajo()
    {
        return margenCeldasAbajo;
    }
    
    public void setMargenCeldasAbajo(int margenCeldasAbajo)
    {
        this.margenCeldasAbajo = margenCeldasAbajo;
    }
    
    
    
    public int getSeparacionCeldasIzquierda()
    {
        return separacionCeldasIzquierda;
    }
    
    public void setSeparacionCeldasIzquierda(int separacionCeldasIzquierda)
    {
        this.separacionCeldasIzquierda = separacionCeldasIzquierda;
        this.EstablecerPadding();
    }
    
    
    
    public int getSeparacionCeldasDerecha()
    {
        return separacionCeldasDerecha;
    }
    
    public void setSeparacionCeldasDerecha(int separacionCeldasDerecha)
    {
        this.separacionCeldasDerecha = separacionCeldasDerecha;
        this.EstablecerPadding();
    }
    
    public int getSeparacionCeldasArriba()
    {
        return separacionCeldasArriba;
    }
    
    public void setSeparacionCeldasArriba(int separacionCeldasArriba)
    {
        this.separacionCeldasArriba = separacionCeldasArriba;
        this.EstablecerPadding();
    }
    
    public int getSeparacionCeldasAbajo()
    {
        return separacionCeldasAbajo;
    }
    
    public void setSeparacionCeldasAbajo(int separacionCeldasAbajo)
    {
        this.separacionCeldasAbajo = separacionCeldasAbajo;
        this.EstablecerPadding();
    }

    public OrigenDatos getOrigenDatos()
    {
        return origenDatos;
    }

    public void EliminarAgrupaciones(){
        this.columnasAgrupacion.clear();
        this.GroupBy("");
    }
    public void setOrigenDatos(OrigenDatos origenDatos)
    {
        this.origenDatos = origenDatos;
        this.vistaDatos = new LinkedHashMap<String, OrigenDatos>();
        if (this.columnasAgrupacion.size() > 0){
            this.GroupBy("");
        }else{        
            // Por defecto no hay agrupaciones
            this.vistaDatos.put("", origenDatos);
            this.CargarDatos();
        }
    }
    
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Propiedades de sólo lectura">
    
    public Context getContexto()
    {
        return contexto;
    }
    
    public ArrayList<ColumnaGridDatos> getColumnas()
    {
        return columnas;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos públicos de manipulación de datos">
    
    
    /**
     * Agrupa los datos según las columnas proporcionadas, en el orden indicado
     * @param strColumnas Nombres de las columnas (separados por | ) por las que agrupar (nombres de columna, no títulos de columna)
     */
    public void GroupBy(String strColumnas){        
        if (this.vistaDatos != null) 
            this.vistaDatos.clear();
        else 
            this.vistaDatos = new LinkedHashMap<String, OrigenDatos>();
        // Comenzamos con todas las columnas visibles...
        if (!strColumnas.isEmpty()){
            String[] split = Pattern.compile("|", Pattern.LITERAL).split(strColumnas); //strColumnas.split("|");
            for (String col : split){
                if (this.ColumnaPorNombre(col) != null && !this.columnasAgrupacion.contains(col)) this.columnasAgrupacion.add(col);
            }
        }
        if (this.columnasAgrupacion.isEmpty()){
            this.vistaDatos.put("", origenDatos);
        }else{
            // Como no está vacio, comenzamos el proceso de agrupación...
            
            for(String vAgrup : this.columnasAgrupacion){
                // Ocultamos la columna agrupada
                this.ColumnaPorNombre(vAgrup).setVisible(false);
            }
            
            for(JsonObject item : GridDatos.this.origenDatos){
                String textoAgrupacion = "";
                for(String vAgrup : GridDatos.this.columnasAgrupacion){
                    // Se construye el texto para la agrupación del siguiente modo <Título de la columna>: <valor de la columna> <Título de la columna>: <valor de la columna> ....                            
                    textoAgrupacion += String.format("%s: %s ", GridDatos.this.ColumnaPorNombre(vAgrup).getTitulo(), item.get(vAgrup).getAsString());
                }
                if (!GridDatos.this.vistaDatos.containsKey(textoAgrupacion)) GridDatos.this.vistaDatos.put(textoAgrupacion, new OrigenDatos(new JsonArray()));
                GridDatos.this.vistaDatos.get(textoAgrupacion).addDato(item);
            }
            for(String k : GridDatos.this.vistaDatos.keySet()){
                GridDatos.this.vistaDatos.put(k, new OrigenDatos(GridDatos.this.vistaDatos.get(k).getDatos()));
            }                       
        }               
        GridDatos.this.CargarDatos();
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos públicos de gestión de columnas">
    
    public void addColumna (ColumnaGridDatos columna){
        this.columnas.add(columna);
        if (columna.isColumnaAgrupacion() && !this.columnasAgrupacion.contains(columna.getNombreColumna())) this.columnasAgrupacion.add(columna.getNombreColumna());
        
        // Ordenamos las columnas
        Collections.sort(this.columnas, new Comparator<ColumnaGridDatos>() {

            public int compare(ColumnaGridDatos arg0, ColumnaGridDatos arg1){
                return Integer.valueOf(arg0.getPosicion()).compareTo(arg1.getPosicion());
            }
        });
        this.refrescarDatos = true;
    }
    
    public void addColumnas (ColumnaGridDatos [] nColumnas){
        this.columnas.addAll(Arrays.asList(nColumnas));
        // Ordenamos las columnas
        Collections.sort(this.columnas, new Comparator<ColumnaGridDatos>() {

            public int compare(ColumnaGridDatos arg0, ColumnaGridDatos arg1){
                return Integer.valueOf(arg0.getPosicion()).compareTo(arg1.getPosicion());
            }
        });
    }
    
    public void addColumnas (ArrayList<ColumnaGridDatos> nColumnas){
        this.columnas.addAll(nColumnas);
        // Ordenamos las columnas
        Collections.sort(this.columnas, new Comparator<ColumnaGridDatos>() {

            public int compare(ColumnaGridDatos arg0, ColumnaGridDatos arg1){
                return Integer.valueOf(arg0.getPosicion()).compareTo(arg1.getPosicion());
            }
        });
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos sobrecargados">
    @Override
    protected void onRestoreInstanceState(Parcelable state)
    {
        if (state instanceof Bundle) {            
            Bundle bundle = (Bundle) state;               
            state = bundle.getParcelable("instanceState");
            this.origenDatos = (OrigenDatos) bundle.getSerializable("OrigenDatos");
            this.vistaDatos = (LinkedHashMap<String, OrigenDatos>) bundle.getSerializable("VistaDatos");
            this.columnasAgrupacion = bundle.getStringArrayList("ColumnasAgrupacion");            
            this.columnas = (ArrayList<ColumnaGridDatos>) bundle.getSerializable("Columnas");
            
            // Ordenamos las columnas
            Collections.sort(this.columnas, new Comparator<ColumnaGridDatos>() {

                public int compare(ColumnaGridDatos arg0, ColumnaGridDatos arg1){
                    return Integer.valueOf(arg0.getPosicion()).compareTo(arg1.getPosicion());
                }
            });
        }    
        super.onRestoreInstanceState(state); //To change body of generated methods, choose Tools | Templates.
        
    }

    @Override
    protected Parcelable onSaveInstanceState()
    {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putSerializable("OrigenDatos", this.origenDatos);
        bundle.putSerializable("VistaDatos", this.vistaDatos);
        bundle.putSerializable("Columnas", this.columnas);
        bundle.putStringArrayList("ColumnasAgrupacion", columnasAgrupacion);
                
        return bundle;
    }    
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b); //To change body of generated methods, choose Tools | Templates.
        if (changed){
            this.InicializarObjetos();
            this.GroupBy("");
        }
    }

    //</editor-fold>
}