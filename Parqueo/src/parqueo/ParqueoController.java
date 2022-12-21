package parqueo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author migue
 */
public class ParqueoController extends Helper implements IParqueo {

  private ArrayList<ParqueoEspacio> dbList = new ArrayList<>();

  @Override
  public void registerCarEntry() {
    String placaVehiculo = setParqueoInfo(
      "Por favor ingrese el numero de placa",
      1,
      10,
      true
    );
    String horaEntrada = setParqueoInfo(
      "Por favor ingrese la hora de entrada",
      1,
      2,
      false
    );
    String minutoEntrada = setParqueoInfo(
      "Por favor ingrese el minuto de entrada",
      1,
      2,
      false
    );
    boolean hasEntrada = hasTicket();
    String numeroEntrada = null;
    if (hasEntrada) {
      numeroEntrada = createCode();
    }
    boolean setManualZona = askManualZone();

    String zone = "";
    String zonaSeleccionada = "";

    if (setManualZona) {
      String[] options;
      options = new String[Helper.getMaxZonasParqueo()];
      for (int i = 0; i < Helper.getMaxZonasParqueo(); i++) {
        options[i] = "Zona " + (i + 1);
      }
      int opcion = JOptionPane.showOptionDialog(
        null,
        "Seleccione la zona del parqueo",
        "Zona",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.INFORMATION_MESSAGE,
        null,
        options,
        options[0]
      );
      zonaSeleccionada = options[opcion].substring(5);
    }
    zone = getZoneWithSpace(zonaSeleccionada);
    ArrayList<String> pasajeros = setPasajerosList();
    String numberParqueo = getRandomeNumberParqueo(zone);
    String codigo = createCode();
    try {
      ParqueoEspacio parqueoInfo = new ParqueoEspacio(
        horaEntrada,
        minutoEntrada,
        placaVehiculo,
        pasajeros,
        numeroEntrada,
        null,
        null,
        codigo,
        zone,
        numberParqueo,
        "Ocupado"
      );
      dbList.add(parqueoInfo);
      DbController.writeParqueoEspacioList(dbList);
      String message =
        "Se asigno el espacio en el parqueo # " +
        numberParqueo +
        " en la zona # " +
        zone;
      JOptionPane.showMessageDialog(
        null,
        message,
        "Codigo de parqueo",
        JOptionPane.INFORMATION_MESSAGE
      );
    } catch (Exception e) {
      JOptionPane.showMessageDialog(
        null,
        "Error al guardar el parqueo",
        "Error",
        JOptionPane.ERROR_MESSAGE
      );
    }
  }

  public void checkOutCar() {
    String placa = setParqueoInfo(
      "Por favor ingrese el numero de placa del vehiculo que va a retirar",
      1,
      10,
      false
    );

    String zona = setParqueoInfo(
      "Por favor ingrese la zona del vehiculo que va a retirar",
      1,
      2,
      false
    );

    String numeroParqueo = setParqueoInfo(
      "Por favor ingrese el numero de parqueo del vehiculo que va a retirar",
      1,
      10,
      false
    );

    ParqueoEspacio parqueoInfo = getParqueoEspacio(placa, zona, numeroParqueo);
    String horaSalida = "";
    String minutoSalida = "";
    if (parqueoInfo == null) {
      JOptionPane.showMessageDialog(
        null,
        "No se encontro el codigo de parqueo",
        "Error",
        JOptionPane.ERROR_MESSAGE
      );
      return;
    } else {
      horaSalida =
        setParqueoInfo("Por favor ingrese la hora de salida", 1, 2, false);
      minutoSalida =
        setParqueoInfo("Por favor ingrese el minuto de salida", 1, 2, false);
    }
    parqueoInfo.setHoraSalida(horaSalida);
    parqueoInfo.setMinutoSalida(minutoSalida);
    int dialogResult = JOptionPane.showConfirmDialog(
      null,
      "¿Desea eliminar el registro del parqueo? Si elige no hacerlo, el estado del registro se actualizará a 'Retirado' y el registro se mantendrá para futuras consultas",
      "Eliminar registro",
      JOptionPane.YES_NO_OPTION
    );
    if (dialogResult == JOptionPane.YES_OPTION) {
      dbList.remove(parqueoInfo);
      DbController.writeParqueoEspacioList(dbList);
    } else {
      parqueoInfo.setEstado("Retirado");
      dbList.remove(parqueoInfo);
      dbList.add(parqueoInfo);
      DbController.writeParqueoEspacioList(dbList);
    }

    String getInfoParqueoFactura = getInfoForInvoice(parqueoInfo);
    JOptionPane.showMessageDialog(
      null,
      getInfoParqueoFactura,
      "Total a pagar",
      JOptionPane.INFORMATION_MESSAGE
    );
  }

  public void findCarByUser() {
    String pasajero = setParqueoInfo(
      "Por favor ingrese el nombre del pasajero",
      1,
      10,
      false
    );

    List<ParqueoEspacio> parqueoInfo = getParqueoEspacioByUser(pasajero);

    showParqueoInfo(parqueoInfo);
  }

  private void showParqueoInfo(List<ParqueoEspacio> parqueoInfo) {
    StringBuilder html = new StringBuilder();
    html.append(
      "<html><body><table style='border: 1px solid black;border-collapse: collapse; border-spacing: 0;'>"
    );
    html.append(
      "<tr><th style='border: 1px solid black;'>Placa del vehículo</th><th style='border: 1px solid black;'>Pasajeros</th><th style='border: 1px solid black;'>Número de parqueo</th><th style='border: 1px solid black;'>Zona</th></tr>"
    );
    String td = "</td><td style='border: 1px solid black;'>";
    for (ParqueoEspacio parqueo : parqueoInfo) {
      html.append("<tr><td style='border: 1px solid black;'>");
      html.append(parqueo.getPlacaVehiculo());
      html.append(td);
      html.append(parqueo.getPasajerosToShow());
      html.append(td);
      html.append(parqueo.getNumeroParqueo());
      html.append(td);
      html.append(parqueo.getZona());
      html.append("</td></tr>");
    }

    html.append("</table></body></html>");

    JPanel panel = new JPanel();
    JLabel label = new JLabel(html.toString());
    panel.add(label);

    JOptionPane.showMessageDialog(
      null,
      panel,
      "Información del parqueo",
      JOptionPane.PLAIN_MESSAGE
    );
  }

  private List<ParqueoEspacio> getParqueoEspacioByUser(String pasajero) {
    List<ParqueoEspacio> result = new ArrayList<>();
    for (ParqueoEspacio data : dbList) {
      if (data.getPasajeros().toUpperCase().contains(pasajero.toUpperCase())) {
        result.add(data);
      }
    }

    return result.isEmpty() ? null : result;
  }

  private boolean askManualZone() {
    int dialogResult = JOptionPane.showConfirmDialog(
      null,
      "¿Desea ingresar la zona manualmente? si elige no, se le asignará la primera zona disponible.\n El espacio se asignaran de forma secuencial en el primer espacio disponible de la zona seleccionada",
      "Zona",
      JOptionPane.YES_NO_OPTION
    );
    return dialogResult == JOptionPane.YES_OPTION;
  }

  private String getInfoForInvoice(ParqueoEspacio data) {
    String horaEntrada = data.getHoraEntrada();
    String horaSalida = data.getHoraSalida();
    String placaVehiculo = data.getPlacaVehiculo();
    String numeroEntrada = data.getNumeroEntrada();
    String zona = data.getZona();
    String numeroParqueo = data.getNumeroParqueo();
    boolean hasEntrada = validateTicketRegister(data);
    long totalHoras = parkingHours(data.getHoraEntrada(), data.getHoraSalida());
    double costo =
      (!hasEntrada ? calculateParkingCost(totalHoras, hasEntrada) : 0.00);
    String styleTable =
      "border-collapse: collapse; border-spacing: 0;height:200px; max-height: 300px;verflow-y: scroll;";
    String styleTd = "border: 1px solid black; padding: 5px;";
    StringBuilder factura = new StringBuilder();
    factura.append("<html><body>");
    factura.append("<table style='" + styleTable + "'>");
    factura.append("<tr>");
    factura.append("<th>Placa</th>");
    factura.append("<th># Entrada</th>");
    factura.append("<th>Hora Entrada</th>");
    factura.append("<th>Hora Salida</th>");
    factura.append("<th>Zona</th>");
    factura.append("<th>Numero Parqueo</th>");
    factura.append("<th>Total Horas</th>");
    factura.append("<th>Total</th>");
    factura.append("</tr>");
    factura.append("<td style='" + styleTd + "'>" + placaVehiculo + "</td>");
    factura.append(
      "<td style='" +
      styleTd +
      "'>" +
      (hasEntrada ? numeroEntrada : "--") +
      "</td>"
    );
    factura.append("<td style='" + styleTd + "'>" + horaEntrada + "</td>");
    factura.append("<td style='" + styleTd + "'>" + horaSalida + "</td>");

    factura.append("<td style='" + styleTd + "'>" + zona + "</td>");
    factura.append("<td style='" + styleTd + "'>" + numeroParqueo + "</td>");
    factura.append("<td style='" + styleTd + "'>" + totalHoras + "</td>");
    factura.append("<td style='" + styleTd + "'>" + costo + "</td>");
    factura.append("</tr>");
    factura.append("</table>");
    factura.append("</body></html>");
    return factura.toString();
  }

  private boolean validateTicketRegister(ParqueoEspacio data) {
    return data.getNumeroEntrada() != null;
  }

  private ParqueoEspacio getParqueoEspacio(
    String placa,
    String zona,
    String numeroParqueo
  ) {
    DbController.readParqueoEspacioList();
    for (ParqueoEspacio parqueoEspacio : dbList) {
      if (
        parqueoEspacio.getPlacaVehiculo().equalsIgnoreCase(placa) &&
        parqueoEspacio.getZona().equalsIgnoreCase(zona) &&
        parqueoEspacio.getNumeroParqueo().equalsIgnoreCase(numeroParqueo) &&
        parqueoEspacio.getEstado().equalsIgnoreCase("Ocupado")
      ) {
        return parqueoEspacio;
      }
    }
    return null;
  }

  private boolean hasTicket() {
    int response = JOptionPane.showConfirmDialog(
      null,
      "¿Tiene entrada?",
      "Confirmación",
      JOptionPane.YES_NO_OPTION
    );
    return response == JOptionPane.YES_OPTION;
  }

  private long parkingHours(
    String horaDeEntradaString,
    String horaDeSalidaString
  ) {
    LocalTime horaDeEntrada = LocalTime.parse(horaDeEntradaString);
    LocalTime horaDeSalida = LocalTime.parse(horaDeSalidaString);

    horaDeEntrada = roundHour(horaDeEntrada);
    horaDeSalida = roundHour(horaDeSalida);
    horaDeSalida = fixDay(horaDeEntrada, horaDeSalida);
    long minutosTrabajados = horaDeEntrada.until(
      horaDeSalida,
      ChronoUnit.MINUTES
    );
    return calculateHours(minutosTrabajados);
  }

  private LocalTime roundHour(LocalTime hora) {
    // redondea siempre hacia arriba a la hora mas cercana
    int minutos = hora.getMinute();
    if (minutos > 0) {
      hora = hora.plusHours(1);
    }
    // ajusta la hora a 00:00
    hora = hora.withMinute(0).withSecond(0).withNano(0);
    return hora;
  }

  private LocalTime fixDay(LocalTime horaDeEntrada, LocalTime horaDeSalida) {
    LocalDateTime horaDeSalidaDateTime = horaDeSalida.atDate(LocalDate.now());
    long diferenciaEnMinutos = horaDeEntrada.until(
      horaDeSalida,
      ChronoUnit.MINUTES
    );
    long diferenciaEnHoras = diferenciaEnMinutos / 60;
    if (diferenciaEnHoras < 0) {
      diferenciaEnHoras += 24;
    }
    horaDeSalidaDateTime = horaDeSalidaDateTime.plusDays(diferenciaEnHoras);
    return horaDeSalidaDateTime.toLocalTime();
  }

  private long calculateHours(long minutosTrabajados) {
    long horas = minutosTrabajados / 60;
    //si el resultado es menor a 0, suma 24 y retorna el resultado
    if (horas < 0) {
      horas += 24;
    }
    return horas;
  }

  private String getZoneWithSpace(String zona) {
    String zone = "";
    int count = 0;
    if (zona.isEmpty()) {
      for (int i = 1; i <= Helper.getMaxZonasParqueo(); i++) {
        for (ParqueoEspacio parqueo : dbList) {
          if (
            parqueo.getZona().equals(String.valueOf(i)) &&
            parqueo.getEstado().equals("Ocupado")
          ) {
            count++;
          }
        }
        if (count < Helper.getMaxVehiculosEnParqueo()) {
          zone = String.valueOf(i);
          break;
        }
        count = 0;
      }
    } else {
      for (ParqueoEspacio parqueo : dbList) {
        if (
          parqueo.getZona().equals(zona) &&
          parqueo.getEstado().equals("Ocupado")
        ) {
          count++;
        }
      }
      if (count < Helper.getMaxVehiculosEnParqueo()) {
        zone = zona;
      }
    }
    return zone;
  }

  private String getRandomeNumberParqueo(String zona) {
    List<Integer> ocupados = new ArrayList<>();
    // recorremos la lista de parqueos
    for (ParqueoEspacio parqueo : dbList) {
      // si el parqueo esta en la zona y esta ocupado, añadimos su numero a la lista de ocupados
      if (
        parqueo.getZona().equals(zona) && parqueo.getEstado().equals("Ocupado")
      ) {
        ocupados.add(Integer.parseInt(parqueo.getNumeroParqueo()));
      }
    }
    // ordenamos la lista de ocupados
    Collections.sort(ocupados);
    // recorremos los números del 1 al límite de vehículos en el parqueo
    for (int i = 1; i <= Helper.getMaxVehiculosEnParqueo(); i++) {
      // si el numero actual no esta en la lista de ocupados, devolvemos ese numero
      if (!ocupados.contains(i)) {
        return String.valueOf(i);
      }
    }
    return null;
  }

  private boolean registrarPlaca(String placa) {
    for (ParqueoEspacio parqueo : dbList) {
      if (parqueo.getPlacaVehiculo().equals(placa)) {
        return false;
      }
    }
    return true;
  }

  private String setParqueoInfo(
    String msg,
    int minLen,
    int maxLen,
    boolean validarPlaca
  ) {
    boolean keepAsk = true;
    do {
      String input = JOptionPane.showInputDialog(msg);
      if (onInputErrorLength(minLen, maxLen, input)) {
        JOptionPane.showMessageDialog(
          null,
          "El dato ingresado debe ser de " +
          minLen +
          " a " +
          maxLen +
          " caracteres"
        );
        continue;
      }
      if (validateCorrectInput(minLen, maxLen, input)) {
        if (validarPlaca) {
          if (registrarPlaca(input)) {
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
      int option = JOptionPane.showConfirmDialog(
        null,
        "Desea continuar?",
        "Continuar",
        JOptionPane.YES_NO_OPTION
      );
      keepAsk = validateContinue(option);
    } while (keepAsk);

    return "";
  }

  private boolean onInputErrorLength(int minLen, int maxLen, String input) {
    return input.length() < minLen || input.length() > maxLen;
  }

  private boolean validateCorrectInput(int minLen, int maxLen, String input) {
    return input.length() >= minLen && input.length() <= maxLen;
  }

  private boolean validateContinue(int option) {
    boolean keepAsk;
    if (option == JOptionPane.YES_OPTION) {
      keepAsk = true;
    } else {
      keepAsk = false;
    }
    return keepAsk;
  }

  private ArrayList<String> setPasajerosList() {
    ArrayList<String> pasajeros = new ArrayList<>();
    boolean keepAsk = true;
    int count = 0;
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
        count++;
      }
      int optionMore = -1;
      if (count >= 5) {
        JOptionPane.showMessageDialog(
          null,
          "Se ha alcanzado el limite de pasajeros",
          "Error",
          JOptionPane.ERROR_MESSAGE
        );
      } else {
        optionMore =
          JOptionPane.showConfirmDialog(
            null,
            "Desea continuar agregando pasajeros a la lista?",
            "Continuar",
            JOptionPane.YES_NO_OPTION
          );
      }

      if (optionMore == JOptionPane.NO_OPTION || count >= 5) {
        keepAsk = false;
      }
    } while (keepAsk);
    return pasajeros;
  }

  private String createCode() {
    int leftLimit = 48; // numeral '0'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 10;
    Random random = new Random();

    String generatedString = random
      .ints(leftLimit, rightLimit + 1)
      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
      .limit(targetStringLength)
      .collect(
        StringBuilder::new,
        StringBuilder::appendCodePoint,
        StringBuilder::append
      )
      .toString();
    System.out.println(generatedString);

    for (ParqueoEspacio parqueo : dbList) {
      if (parqueo.getCodigo().equals(generatedString)) {
        generatedString = createCode();
      }
    }
    return generatedString;
  }

  public void showHistory(String zona) {
    init();
    showTableAllData(dbList, zona);
  }

  private void showTableAllData(ArrayList<ParqueoEspacio> data, String zone) {
    ArrayList<ParqueoEspacio> filteredData = filterDataByZone(data, zone);
    List<Integer> maxLength = getMaxLength(filteredData);
    String message = getMessageToHtml(filteredData, maxLength);
    JOptionPane.showMessageDialog(null, message);
  }

  private ArrayList<ParqueoEspacio> filterDataByZone(
    ArrayList<ParqueoEspacio> data,
    String zone
  ) {
    if (zone == null || zone.isEmpty()) {
      return data;
    }
    ArrayList<ParqueoEspacio> filteredData = new ArrayList<>();
    for (ParqueoEspacio parqueoEspacio : data) {
      if (parqueoEspacio.getZona().equals(zone)) {
        filteredData.add(parqueoEspacio);
      }
    }
    return filteredData;
  }

  private static List<Integer> getMaxLength(ArrayList<ParqueoEspacio> data) {
    int maxLengthCodigo = 0;
    int maxLengthPlaca = 0;
    int maxLengthPasajeros = 0;
    int maxLengthHoraEntrada = 0;
    int maxLengthHoraSalida = 0;
    for (ParqueoEspacio p : data) {
      int maxLenCodigo = p.getCodigo() != null ? p.getCodigo().length() : 0;
      int maxLenPlaca = p.getPlacaVehiculo() != null
        ? p.getPlacaVehiculo().length()
        : 0;
      int maxLenPasajeros = p.getPasajeros() != null
        ? p.getPasajeros().length()
        : 0;
      int maxLenHoraEntrada = p.getHoraEntrada() != null
        ? p.getHoraEntrada().length()
        : 0;
      int maxLenHoraSalida = p.getHoraSalida() != null
        ? p.getHoraSalida().length()
        : 0;
      maxLengthCodigo = Math.max(maxLengthCodigo, maxLenCodigo);
      maxLengthPlaca = Math.max(maxLengthPlaca, maxLenPlaca);
      maxLengthPasajeros = Math.max(maxLengthPasajeros, maxLenPasajeros);
      maxLengthHoraEntrada = Math.max(maxLengthHoraEntrada, maxLenHoraEntrada);
      maxLengthHoraSalida = Math.max(maxLengthHoraSalida, maxLenHoraSalida);
    }
    return Arrays.asList(
      maxLengthCodigo,
      maxLengthPlaca,
      maxLengthPasajeros,
      maxLengthHoraEntrada,
      maxLengthHoraSalida
    );
  }

  private String getMessageToHtml(
    ArrayList<ParqueoEspacio> data,
    List<Integer> maxLength
  ) {
    StringBuilder sb = new StringBuilder();
    String style =
      "<td style='border: 1px solid black; padding: 2px 10px;text-align: left;'>";
    String thead =
      "<thead><tr>" +
      style +
      "#" +
      "</td>" +
      style +
      "ID" +
      "</td>" +
      style +
      "Entrada" +
      style +
      "Placa" +
      "</td>" +
      style +
      "Zona" +
      "</td>" +
      style +
      "Posicion" +
      "</td>" +
      style +
      "Pasajeros" +
      "</td>" +
      style +
      "Hora Entrada" +
      "</td>" +
      style +
      "Hora Salida" +
      "</td>" +
      style +
      "Tiempo" +
      "</td>" +
      style +
      "Costo" +
      "</td>" +
      style +
      "Estado" +
      "</td>" +
      "</tr></thead>";
    sb.append(
      "<html><body><table style='border-collapse: collapse; border-spacing: 0;height:200px; max-height: 300px;verflow-y: scroll;'><tbody>" +
      thead
    );
    int i = 0;
    for (ParqueoEspacio p : data) {
      i++;
      boolean hasHour = p.getHoraSalida() != null && p.getHoraEntrada() != null;
      long horasEnParqueo = hasHour
        ? parkingHours(p.getHoraEntrada(), p.getHoraSalida())
        : 0;
      boolean hasEntrada = p.getNumeroEntrada() != null;
      sb.append("<tr>");
      sb.append(style + i + "</td>");
      sb.append(style + p.getCodigo() + "</td>");
      sb.append(style + (hasEntrada ? p.getNumeroEntrada() : "--") + "</td>");
      sb.append(style + p.getPlacaVehiculo() + "</td>");
      sb.append(style + p.getZona() + "</td>");
      sb.append(style + p.getNumeroParqueo() + "</td>");
      sb.append(style + p.getPasajerosToShow() + "</td>");
      sb.append(
        style +
        (p.getHoraEntrada() == null ? "--:--" : p.getHoraEntrada()) +
        "</td>"
      );
      sb.append(
        style +
        (p.getHoraSalida() == null ? "--:--" : p.getHoraSalida()) +
        "</td>"
      );
      sb.append(
        style +
        (
          hasHour
            ? parkingHours(p.getHoraEntrada(), p.getHoraSalida()) +
            "h" +
            (
              parkingHours(p.getHoraEntrada(), p.getHoraSalida()) > 1
                ? "rs"
                : ""
            )
            : "N/A"
        ) +
        "</td>"
      );
      sb.append(
        style + calculateParkingCost(horasEnParqueo, hasEntrada) + "</td>"
      );
      sb.append(style + p.getEstado() + "</td>");
      sb.append("</tr>");
    }
    sb.append("</tbody></table></body></html>");

    return sb.toString();
  }

  private double calculateParkingCost(long horas, boolean hasNumberEntrada) {
    if (hasNumberEntrada) {
      return 0;
    } else {
      return horas * Config.costoParqueoPorHora;
    }
  }

  public void report() {
    String[] options;
    options = new String[Helper.getMaxZonasParqueo()];
    for (int i = 0; i < Helper.getMaxZonasParqueo(); i++) {
      options[i] = "Zona " + (i + 1);
    }
    String zona = (String) JOptionPane.showInputDialog(
      null,
      "Seleccione la zona a reportar",
      "Reporte",
      JOptionPane.QUESTION_MESSAGE,
      null,
      options,
      options[0]
    );
    if (zona == null) {
      return;
    }
    String[] options2 = { "Reporte por Placa", "Historial" };
    String tipo = (String) JOptionPane.showInputDialog(
      null,
      "Seleccione el tipo de reporte",
      "Reporte",
      JOptionPane.QUESTION_MESSAGE,
      null,
      options2,
      options2[0]
    );
    if (tipo == null) {
      return;
    }
    zona = zona.substring(5);

    // lee el archivo y asigna a un arraylist
    ArrayList<ParqueoEspacio> data = new ArrayList<>();
    DbController.readParqueoEspacioList();

    // filtra dbList por zona y asignar a data
    for (ParqueoEspacio p : dbList) {
      if (p.getZona().equals(zona)) {
        data.add(p);
      }
    }
    // ejecuta el reporte correspondiente al tipo
    if (tipo.equals("Reporte por Placa")) {
      reportePorPlaca(data);
    } else {
      showHistory(zona);
    }
  }

  private void reportePorPlaca(ArrayList<ParqueoEspacio> data) {
    StringBuilder sb = new StringBuilder();
    String style = "<td style='border: 1px solid black; padding: 5px;'>";
    String thead =
      "<thead><tr>" +
      style +
      "N°" +
      "</td>" +
      style +
      "Placa" +
      "</td>" +
      style +
      "Zona" +
      "</td>" +
      style +
      "Posicion" +
      "</td>" +
      style +
      "Pasajeros" +
      "</td>" +
      style +
      "Hora Entrada" +
      "</td>" +
      "</tr></thead>";
    sb.append(
      "<html><body><table style='border-collapse: collapse; width: 100%;'>" +
      thead
    );
    int i = 0;
    for (ParqueoEspacio p : data) {
      i++;
      sb.append("<tr>");
      sb.append(style + i + "</td>");
      sb.append(style + p.getPlacaVehiculo() + "</td>");
      sb.append(style + p.getZona() + "</td>");
      sb.append(style + p.getNumeroParqueo() + "</td>");
      sb.append(style + p.getPasajerosToShow() + "</td>");
      sb.append(
        style +
        (p.getHoraEntrada() == null ? "--:--" : p.getHoraEntrada()) +
        "</td>"
      );
      sb.append("</tr>");
    }
    sb.append("</tbody></table></body></html>");

    JOptionPane.showMessageDialog(
      null,
      sb.toString(),
      "Reporte por Placa",
      JOptionPane.INFORMATION_MESSAGE
    );
  }

  public void init() {
    if (DbController.existPathFile()) {
      dbList = DbController.readParqueoEspacioList();
    } else {
      boolean wasCreate = DbController.createFile();
      if (wasCreate) {
        dbList = DbController.readParqueoEspacioList();
      } else {
        JOptionPane.showMessageDialog(
          null,
          "No se pudo crear la base de datos",
          "Error",
          JOptionPane.ERROR_MESSAGE
        );
      }
    }
  }
}
