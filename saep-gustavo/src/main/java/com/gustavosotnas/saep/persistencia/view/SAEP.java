package com.gustavosotnas.saep.persistencia.view;

import com.gustavosotnas.saep.persistencia.controller.DBController;

/**
 * Created by gustavosotnas on 10/07/16.
 */
public class SAEP {
    public static void main(String args[]) {
        DBController.initializeDB();
        System.out.println("Hello world");
    }
}
