package com.gustavosotnas.saep.persistencia.view;

import com.gustavosotnas.saep.persistencia.controller.DBController;

/**
 * Created by gustavosotnas on 10/07/16.
 */
public class SAEP {
    public static void main(String args[]) {
        System.out.println("Persistência do Sistema de Apoio à Elaboração de Pareceres (SAEP)\n" +
                "por Gustavo Moraes (http://github.com/gustavosotnas)");
        DBController.initializeDB();
    }
}
