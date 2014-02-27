/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tarsys.android.view.util;

import android.graphics.Color;
import java.io.Serializable;

/**
 *
 * @author TaRSyS
 */
public class ColumnaGridDatos implements Serializable
{
    private float margenIzquierdo;
    private float margenDerecho;
    private float margenSuperior;
    private float margenInferior;
    private AlineacionDatos alineacionCabecera = AlineacionDatos.Izquierda;
    private AlineacionDatos alineacionDatos = AlineacionDatos.Izquierda;
    private TipoDatoColumna tipoDatoColumna;
    private String formatoDatosColumna;
    private String nombreColumna;
    private String titulo;
    private int anchoColumna;

    private int colorFondoCabecera;
    private int colorTextoCabecera;
    private int colorTextoDatos;
    private int colorFondoDatos;

    private float tamFuenteTitulo;
    private float tamFuenteCelda;

    private boolean subrayadoTitulo;
    private boolean subrayadoTexto;
    private boolean negritaTitulo;
    private boolean negritaTexto;
    private boolean cursivaTitulo;
    private boolean cursivaTexto;
    private int posicion;
    private boolean columnaAgrupacion;
    private TipoResumenColumna tipoResumenColumna = TipoResumenColumna.SinResumen;
    private String formulaResumen = "";
    private boolean visible = true;

    public ColumnaGridDatos(String nombreColumna, TipoDatoColumna tipoCol, String tituloCol, int anchoColumna, boolean agrupacion, int orden)
    {
        this.nombreColumna = nombreColumna;
        this.anchoColumna = anchoColumna;
        this.posicion = orden;
        this.colorFondoCabecera = Color.TRANSPARENT;
        this.colorTextoCabecera = Color.BLACK;
        this.colorFondoDatos = Color.TRANSPARENT;
        this.colorTextoDatos = Color.BLACK;

        this.columnaAgrupacion = agrupacion;

        this.cursivaTexto = false;
        this.cursivaTitulo = false;
        this.negritaTexto = false;
        this.negritaTitulo = false;
        this.subrayadoTexto = false;
        this.subrayadoTitulo = false;

        this.formatoDatosColumna = "";

        this.margenDerecho = 0;
        this.margenInferior = 0;
        this.margenIzquierdo = 0;
        this.margenSuperior = 0;

        this.tamFuenteCelda = 12;
        this.tamFuenteTitulo = 12;
        this.tipoDatoColumna = tipoCol;

        this.titulo = tituloCol;

    }

    public TipoResumenColumna getTipoResumenColumna()
    {
        return tipoResumenColumna;
    }

    public int getPosicion()
    {
        return posicion;
    }

    public void setPosicion(int posicion)
    {
        this.posicion = posicion;
    }

    public String getFormulaResumen() {
        return formulaResumen;
    }

    public void setFormulaResumen(String formulaResumen) {
        this.formulaResumen = formulaResumen;
    }

    
    public void setTipoResumenColumna(TipoResumenColumna tipoResumenColumna)
    {
        this.tipoResumenColumna = tipoResumenColumna;
    }

    public AlineacionDatos getAlineacionCabecera()
    {
        return alineacionCabecera;
    }

    public void setAlineacionCabecera(AlineacionDatos alineacionCabecera)
    {
        this.alineacionCabecera = alineacionCabecera;
    }

    public AlineacionDatos getAlineacionDatos()
    {
        return alineacionDatos;
    }

    public void setAlineacionDatos(AlineacionDatos alineacionDatos)
    {
        this.alineacionDatos = alineacionDatos;
    }

    
    
    public TipoDatoColumna getTipoDatoColumna()
    {
        return tipoDatoColumna;
    }

    public void setTipoDatoColumna(TipoDatoColumna tipoDatoColumna)
    {
        this.tipoDatoColumna = tipoDatoColumna;
    }

    public String getFormatoDatosColumna()
    {
        return formatoDatosColumna;
    }

    public void setFormatoDatosColumna(String formatoDatosColumna)
    {
        this.formatoDatosColumna = formatoDatosColumna;
    }

    public boolean isSubrayadoTitulo()
    {
        return subrayadoTitulo;
    }

    public void setSubrayadoTitulo(boolean subrayadoTitulo)
    {
        this.subrayadoTitulo = subrayadoTitulo;
    }

    public boolean isSubrayadoTexto()
    {
        return subrayadoTexto;
    }

    public void setSubrayadoTexto(boolean subrayadoTexto)
    {
        this.subrayadoTexto = subrayadoTexto;
    }


    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }


    public float getMargenIzquierdo()
    {
        return margenIzquierdo;
    }

    public void setMargenIzquierdo(float margenIzquierdo)
    {
        this.margenIzquierdo = margenIzquierdo;
    }

    public float getMargenDerecho()
    {
        return margenDerecho;
    }

    public void setMargenDerecho(float margenDerecho)
    {
        this.margenDerecho = margenDerecho;
    }

    public float getMargenSuperior()
    {
        return margenSuperior;
    }

    public void setMargenSuperior(float margenSuperior)
    {
        this.margenSuperior = margenSuperior;
    }

    public float getMargenInferior()
    {
        return margenInferior;
    }

    public void setMargenInferior(float margenInferior)
    {
        this.margenInferior = margenInferior;
    }


    public String getNombreColumna()
    {
        return nombreColumna;
    }

    public void setNombreColumna(String nombreColumna)
    {
        this.nombreColumna = nombreColumna;
    }

    public String getTitulo()
    {
        return titulo == null || (titulo != null && titulo.trim().equals("")) ? this.nombreColumna.trim() : this.titulo.trim();
    }

    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    public int getAnchoColumna()
    {
        return anchoColumna;
    }

    public void setAnchoColumna(int anchoColumna)
    {
        this.anchoColumna = anchoColumna;
    }

    public boolean isColumnaAgrupacion()
    {
        return columnaAgrupacion;
    }

    public void setColumnaAgrupacion(boolean columnaAgrupacion)
    {
        this.columnaAgrupacion = columnaAgrupacion;
    }

    public int getColorTextoDatos()
    {
        return colorTextoDatos;
    }

    public void setColorTextoDatos(int colorTextoColumna)
    {
        this.colorTextoDatos = colorTextoColumna;
    }

    public int getColorFondoDatos()
    {
        return colorFondoDatos;
    }

    public int getColorFondoCabecera()
    {
        return colorFondoCabecera;
    }

    public void setColorFondoCabecera(int colorFondoCabecera)
    {
        this.colorFondoCabecera = colorFondoCabecera;
    }

    public int getColorTextoCabecera()
    {
        return colorTextoCabecera;
    }

    public void setColorTextoCabecera(int colorTextoCabecera)
    {
        this.colorTextoCabecera = colorTextoCabecera;
    }

    public void setColorFondoDatos(int colorFondoColumna)
    {
        this.colorFondoDatos = colorFondoColumna;
    }

    public float getTamFuenteTitulo()
    {
        return tamFuenteTitulo;
    }

    public void setTamFuenteTitulo(float tamFuenteTitulo)
    {
        this.tamFuenteTitulo = tamFuenteTitulo;
    }

    public float getTamFuenteCelda()
    {
        return tamFuenteCelda;
    }

    public void setTamFuenteCelda(float tamFuenteCelda)
    {
        this.tamFuenteCelda = tamFuenteCelda;
    }

    public boolean isNegritaTitulo()
    {
        return negritaTitulo;
    }

    public void setNegritaTitulo(boolean negritaTitulo)
    {
        this.negritaTitulo = negritaTitulo;
    }

    public boolean isNegritaTexto()
    {
        return negritaTexto;
    }

    public void setNegritaTexto(boolean negritaTexto)
    {
        this.negritaTexto = negritaTexto;
    }

    public boolean isCursivaTitulo()
    {
        return cursivaTitulo;
    }

    public void setCursivaTitulo(boolean cursivaTitulo)
    {
        this.cursivaTitulo = cursivaTitulo;
    }

    public boolean isCursivaTexto()
    {
        return cursivaTexto;
    }

    public void setCursivaTexto(boolean cursivaTexto)
    {
        this.cursivaTexto = cursivaTexto;
    }
}
