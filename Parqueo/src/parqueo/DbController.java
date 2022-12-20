package parqueo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class DbController {

  public static void writeParqueoEspacioList(
    ArrayList<ParqueoEspacio> parqueoEspacioList
  ) {
    FileWriter fileWriter = null;
    PrintWriter printWriter = null;

    try {
      fileWriter = new FileWriter(Config.fileName);
      printWriter = new PrintWriter(fileWriter);
      for (ParqueoEspacio parqueoEspacio : parqueoEspacioList) {
        printWriter.println(
          parqueoEspacio.getOnlyHourEntrada() +
          "," +
          parqueoEspacio.getOnlyMinuteEntrada() +
          "," +
          parqueoEspacio.getPlacaVehiculo() +
          "," +
          parqueoEspacio.getPasajeros() +
          "," +
          parqueoEspacio.getNumeroEntrada() +
          "," +
          parqueoEspacio.getOnlyHourSalida() +
          "," +
          parqueoEspacio.getOnlyMinuteSalida() +
          "," +
          parqueoEspacio.getCodigo() +
          "," +
          parqueoEspacio.getZona() +
          "," +
          parqueoEspacio.getNumeroParqueo() +
          "," +
          parqueoEspacio.getEstado()

        );
      }
      //[horaEntrada(0),minutoEntrada(1),placaVehiculo(2),pasajeros(3),numeroEntrada(4),horaSalida(5),minutoSalida(6),codigo(7), zona(8), numeroParqueo(9), estado(10)]
    } catch (IOException e) {
      JOptionPane.showMessageDialog(
        null,
        "Error al guardar los datos en el archivo",
        "Error",
        JOptionPane.ERROR_MESSAGE
      );
    } finally {
      if (printWriter != null) {
        printWriter.close();
      }
      if (fileWriter != null) {
        try {
          fileWriter.close();
        } catch (IOException e) {
          JOptionPane.showMessageDialog(
            null,
            "Error al cerrar el archivo",
            "Error",
            JOptionPane.ERROR_MESSAGE
          );
        }
      }
    }
  }

  public static ArrayList<ParqueoEspacio> readParqueoEspacioList() {
    FileInputStream fileInputStream = null;
    DataInputStream dataInputStream = null;
    BufferedReader bufferedReader = null;

    ArrayList<ParqueoEspacio> parqueoEspacioList = new ArrayList<>();
    try {
      fileInputStream = new FileInputStream(Config.fileName);
      dataInputStream = new DataInputStream(fileInputStream);
      bufferedReader =
        new BufferedReader(new java.io.InputStreamReader(dataInputStream));
      String strLine;
      while ((strLine = bufferedReader.readLine()) != null) {
        if (strLine.trim().equals("")) continue;
        String[] parqueoEspacioData = strLine.split(",");
        //[horaEntrada(0),minutoEntrada(1),placaVehiculo(2),pasajeros(3),numeroEntrada(4),horaSalida(5),minutoSalida(6),codigo(7)]
        String[] pasajerosData = parqueoEspacioData[3].split(";");
        ArrayList<String> pasajerosString = new ArrayList<>();
        for (String pasajero : pasajerosData) {
          pasajerosString.add(pasajero);
        }

        parqueoEspacioList.add(
          setNewItemParqueoEspacio(parqueoEspacioData, pasajerosString)
        );
      }
      
      return ordenarPorZonaYNumeroParqueo(parqueoEspacioList);
    } catch (IOException e) {
      System.out.println("Error1: " + e.getMessage());
    } finally {
      closeStreamsObjects(fileInputStream, dataInputStream, bufferedReader);
    }
    return parqueoEspacioList;
  }
  public static ArrayList<ParqueoEspacio> ordenarPorZonaYNumeroParqueo(ArrayList<ParqueoEspacio> parqueos) {
    // Ordenamos la lista de parqueos mediante una expresión lambda que compara primero por zona y luego por número de parqueo
    parqueos.sort((p1, p2) -> {
      int comparacionZona = p1.getZona().compareTo(p2.getZona());
      if (comparacionZona != 0) {
        return comparacionZona;
      }
      return p1.getNumeroParqueo().compareTo(p2.getNumeroParqueo());
    });
    // Devolvemos la lista de parqueos ordenada
    return parqueos;
  }

  private static void closeStreamsObjects(
    FileInputStream fileInputStream,
    DataInputStream dataInputStream,
    BufferedReader bufferedReader
  ) {
    if (bufferedReader != null) {
      try {
        bufferedReader.close();
      } catch (IOException e) {
        System.out.println("Error2: " + e.getMessage());
      }
    }
    if (dataInputStream != null) {
      try {
        dataInputStream.close();
      } catch (IOException e) {
        System.out.println("Error3: " + e.getMessage());
      }
    }
    if (fileInputStream != null) {
      try {
        fileInputStream.close();
      } catch (IOException e) {
        System.out.println("Error4: " + e.getMessage());
      }
    }
  }

  private static ParqueoEspacio setNewItemParqueoEspacio(
    String[] parqueoEspacioData,
    ArrayList<String> pasajerosString
  ) {
    //[horaEntrada(0),minutoEntrada(1),placaVehiculo(2),pasajeros(3),numeroEntrada(4),horaSalida(5),minutoSalida(6),codigo(7), zona(8), numeroParqueo(9), estado(10)]
    return new ParqueoEspacio(
      parqueoEspacioData[0].equals("null") ? null : parqueoEspacioData[0],
      parqueoEspacioData[1].equals("null") ? null : parqueoEspacioData[1],
      parqueoEspacioData[2].equals("null") ? null : parqueoEspacioData[2],
      pasajerosString,
      parqueoEspacioData[4].equals("null") ? null : parqueoEspacioData[4],
      parqueoEspacioData[5].equals("null") ? null : parqueoEspacioData[5],
      parqueoEspacioData[6].equals("null") ? null : parqueoEspacioData[6],
      parqueoEspacioData[7].equals("null") ? null : parqueoEspacioData[7],
      parqueoEspacioData[8].equals("null") ? null : parqueoEspacioData[8],
      parqueoEspacioData[9].equals("null") ? null : parqueoEspacioData[9],
      parqueoEspacioData[10].equals("null") ? null : parqueoEspacioData[10]
    );
  }

  public static boolean existPathFile() {
    try {
      File file = new File(Config.fileName);
      return file.exists();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(
        null,
        "Error al verificar la existencia del archivo",
        "Error",
        JOptionPane.ERROR_MESSAGE
      );
    }
    return false;
  }

  public static boolean createFile() {
    File file = new File(Config.fileName);
    try {
      return  file.createNewFile();
    } catch (IOException e) {
      JOptionPane.showMessageDialog(
        null,
        "Error al crear el archivo",
        "Error",
        JOptionPane.ERROR_MESSAGE
      );
    }
    return false;
  }
}
