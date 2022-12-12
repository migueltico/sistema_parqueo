/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parqueo;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author migue
 */
public class ParqueoController implements IParqueo {

  private ArrayList<ParqueoEspacio> _db = new ArrayList<>();

  @Override
  public void RegistrarIngreso() {
    String horaEntrada = SetParqueoInfo(
      "Por favor ingrese la hora de entrada",
      1,
      2,
      false
    );
    String minutoEntrada = SetParqueoInfo(
      "Por favor ingrese el minuto de entrada",
      1,
      2,
      false
    );
    String numeroEntrada = SetParqueoInfo(
      "Por favor ingrese el numero de entrada",
      1,
      10,
      false
    );
    String codigo = CrearCodigo();

    ParqueoEspacio parqueoInfo = new ParqueoEspacio(
      horaEntrada,
      minutoEntrada,
      numeroEntrada
    );
    parqueoInfo.setCodigo(codigo);
    parqueoInfo.setPlacaVehiculo(
      SetParqueoInfo("Por favor ingrese el numero de placa", 1, 10, true)
    );
    parqueoInfo.setPasajeros(
      SetPasajerosList()
    );

    _db.add(parqueoInfo);
  }

  public boolean RegistrarPlaca(String placa) {
      for (ParqueoEspacio parqueo : _db) {
         if(parqueo.getPlacaVehiculo().equals(placa) ) {
            return false;
            
            
         }
        
      
     }
    return true;
  }

  private String SetParqueoInfo(
    String msg,
    int minLen,
    int maxLen,
    boolean validarPlaca
  ) {
    boolean keepAsk = true;
    do {
      String input = JOptionPane.showInputDialog(msg);
      if (input.length() >= minLen && input.length() <= maxLen) {
        if (validarPlaca) {
          if (RegistrarPlaca(input)) {
            return input;
          } else {
            JOptionPane.showMessageDialog(
              null,
              "El dato ingresado es una placa existente"
            );
          }
        } else {
          return input;
        }
      }
      if (input.length() < minLen) {
        JOptionPane.showMessageDialog(null, "El dato ingresado es muy corto");
      }
      if (input.length() > maxLen) {
        JOptionPane.showMessageDialog(null, "El dato ingresado es muy largo");
      }

      int option = JOptionPane.showConfirmDialog(
        null,
        "Desea continuar?",
        "Continuar",
        JOptionPane.YES_NO_OPTION
      );
      if (option == JOptionPane.YES_OPTION) {
        keepAsk = true;
      } else {
        keepAsk = false;
      }
    } while (keepAsk);

    return "";
  }

  private ArrayList<String> SetPasajerosList() {
    ArrayList<String> pasajeros = new ArrayList<>();
    boolean keepAsk = true;
    do {
      String input = JOptionPane.showInputDialog(
        "Por favor ingrese el nombre del pasajero"
      );
      if (input.length() < 1) {
        JOptionPane.showMessageDialog(
          null,
          "El dato ingresado es muy corto o muy largo",
          "Error",
          JOptionPane.ERROR_MESSAGE
        );
      } else {
        pasajeros.add(input);
      }
      int optionMore = JOptionPane.showConfirmDialog(
        null,
        "Desea continuar agregando pasajeros a la lista?",
        "Continuar",
        JOptionPane.YES_NO_OPTION
      );
      if (optionMore == JOptionPane.NO_OPTION) {
        keepAsk = false;
      }
    } while (keepAsk);
    return pasajeros;
  }

  private String CrearCodigo() {
      
    int leftLimit = 48; // numeral '0'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 10;
    Random random = new Random();

    String generatedString = random.ints(leftLimit, rightLimit + 1)
      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
      .limit(targetStringLength)
      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
      .toString();
      System.out.println(generatedString);
      
     for (ParqueoEspacio parqueo : _db) {
         if(parqueo.getCodigo().equals(generatedString) ) {
            generatedString = CrearCodigo();
            
         }
        
      
     }
    return generatedString;
  }

  public void MostrarParqueo() {
    for (ParqueoEspacio parqueo : _db) {
      System.out.println("Codigo: " + parqueo.getCodigo());
      System.out.println("Placa del Vehiculo: " + parqueo.getPlacaVehiculo());
      System.out.println("Hora de entrada: " + parqueo.getHoraEntrada());
      System.out.println("Numero de entrada: " + parqueo.getNumeroEntrada());
      System.out.println("Hora de salida: " + parqueo.getHoraSalida());
      String pasajeros = "";
      for (String pasajero : parqueo.getPasajeros()) {
        pasajeros += pasajero + ", ";
      }
      //remove last comma and space
      pasajeros =
        pasajeros.length() > 2
          ? pasajeros.substring(0, pasajeros.length() - 2)
          : pasajeros;
      System.out.println(pasajeros);
    }
  }
}
