/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tarsys.android.view.util;

import android.view.Gravity;

/**
 *
 * @author TaRSyS
 */
public enum AlineacionDatos
{
    Izquierda{
        public int value(){
            return Gravity.CENTER_VERTICAL | Gravity.LEFT;
        }
    },
    Centro{
        public int value(){
            return Gravity.CENTER_VERTICAL | Gravity.CENTER;
        }
    },
    Derecha{
        public int value(){
            return Gravity.CENTER_VERTICAL| Gravity.RIGHT;
        }
    };
    
    public abstract int value();
}
