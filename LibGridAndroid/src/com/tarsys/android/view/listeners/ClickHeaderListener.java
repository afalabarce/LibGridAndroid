/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tarsys.android.view.listeners;

import com.tarsys.android.view.util.ColumnaGridDatos;
import java.util.EventListener;

/**
 *
 * @author TaRSyS
 */
public interface ClickHeaderListener extends EventListener
{
    void Click (Object sender, ColumnaGridDatos columna);
}
