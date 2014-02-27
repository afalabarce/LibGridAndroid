/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tarsys.android.view.listeners;

import com.google.gson.JsonObject;
import com.tarsys.android.view.util.ColumnaGridDatos;
import java.io.Serializable;
import java.util.EventListener;

/**
 *
 * @author TaRSyS
 */
public interface ClickRowListener extends EventListener, Serializable
{
    void Click (Object sender, JsonObject row, ColumnaGridDatos columna);
}
