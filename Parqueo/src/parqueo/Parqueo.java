/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package parqueo;

import javax.swing.JOptionPane;

/**
 *
 * @author migue
 */
public class Parqueo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ParqueoController controller = new ParqueoController();

        //Menu
        boolean keepMenu = true;
        String welcomeDialog = "Bienvenido al sistema de parqueo de Qurayn Abu al Bawl \n\n";
        do {

            int opcion = Integer.parseInt(JOptionPane.showInputDialog(welcomeDialog + "Seleccione una de las siguientes opciones.\n 1- Registrar Ingreso.\n 2- Mostrar Parqueo.\n 5- Salir"));

            if (opcion > 0 && opcion <= 5) {

                switch (opcion) {
                    case 1 ->
                        controller.RegistrarIngreso();
                    case 2 ->
                        controller.MostrarParqueo();
                    case 5 ->
                        keepMenu = false;
                    default ->
                        throw new AssertionError();
                }

            } else {
                JOptionPane.showMessageDialog(null, "La opcion ingresada no coincide con las opciones mostradas");
            }

        } while (keepMenu);

    }

}
