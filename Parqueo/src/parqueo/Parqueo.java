package parqueo;

import java.awt.Font;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

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
    controller.init();

    //Menu
    boolean keepMenu = true;
    String welcomeDialog =
      "Bienvenido al sistema de parqueo de " + Config.appName + " \n\n";
    do {
      try {
        String[] options = {
          "1-Registrar Ingreso",
          "2-Registrar Salida",
          "3-Reportes",
          "4-Buscar",
          "5-Salir",
        };

        int opcion = JOptionPane.showOptionDialog(
          null,
          welcomeDialog + "Seleccione una de las siguientes opciones.",
          "Menu - " + Config.appName + " | Proyecto Final (Miguel Esquivel, David Segura, Kevin Solis)",
          JOptionPane.DEFAULT_OPTION,
          JOptionPane.INFORMATION_MESSAGE,
          null,
          options,
          options[0]
        );

        if (opcion >= 0 && opcion <= 5) {
          switch (opcion) {
            case 0 -> controller.registerCarEntry();
            case 1 -> controller.checkOutCar();
            case 2 -> controller.report();
            case 3 -> controller.findCarByUser();
            case 4 -> keepMenu = false;
          }
        } else {
          JOptionPane.showMessageDialog(
            null,
            "La opcion ingresada no coincide con las opciones mostradas",
            "Error 1",
            JOptionPane.ERROR_MESSAGE
          );
        }
      } catch (ArithmeticException e) {
        JOptionPane.showMessageDialog(
          null,
          "Arithmetic exception: " + e.getMessage(),
          "Error",
          JOptionPane.ERROR_MESSAGE
        );
      } catch (NullPointerException e) {
        JOptionPane.showMessageDialog(
          null,
          "Null pointer exception: " + e.getMessage(),
          "Error",
          JOptionPane.ERROR_MESSAGE
        );
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(
          null,
          "Number format exception: " + e.getMessage(),
          "Error",
          JOptionPane.ERROR_MESSAGE
        );
      } catch (IllegalArgumentException e) {
        JOptionPane.showMessageDialog(
          null,
          "Illegal argument exception: " + e.getMessage(),
          "Error",
          JOptionPane.ERROR_MESSAGE
        );
      } catch (ArrayIndexOutOfBoundsException e) {
        JOptionPane.showMessageDialog(
          null,
          "Array index out of bounds exception: " + e.getMessage(),
          "Error",
          JOptionPane.ERROR_MESSAGE
        );
      } catch (Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        int lineNumber = stackTrace[0].getLineNumber();
        String fileName = stackTrace[0].getFileName();
        String methodName = stackTrace[0].getMethodName();
        JOptionPane.showMessageDialog(
          null,
          "Exception: " +
          e.getMessage() +
          " \n\n" + lineNumber + " -> " + fileName + " -> " + methodName +
          " \n\n" +
          e.toString(),
          "Error",
          JOptionPane.ERROR_MESSAGE
        );

      }
    } while (keepMenu);
  }
}
