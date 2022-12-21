package parqueo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author migue
 */
public class ParqueoEspacio implements Serializable {

  private String horaEntrada;
  private String minutoEntrada;
  private String placaVehiculo;
  private ArrayList<String> pasajeros = new ArrayList<>();
  private String numeroEntrada;
  private String horaSalida;
  private String minutoSalida;
  private String codigo;
  private String zona;
  private String numeroParqueo;
  private String estado;
  
  public ParqueoEspacio(
    String horaEntrada,
    String minutoEntrada,
    String placaVehiculo,
    ArrayList<String> pasajeros,
    String numeroEntrada,
    String horaSalida,
    String minutoSalida,
    String codigo,
    String zona,
    String numeroParqueo,
    String estado
  ) {
    this.horaEntrada = horaEntrada;
    this.minutoEntrada = minutoEntrada;
    this.placaVehiculo = placaVehiculo;
    this.pasajeros = pasajeros;
    this.numeroEntrada = numeroEntrada;
    this.horaSalida = horaSalida;
    this.minutoSalida = minutoSalida;
    this.codigo = codigo;
    this.zona = zona;
    this.numeroParqueo = numeroParqueo;
    this.estado = estado;
  }

  public void setPasajeros(ArrayList<String> pasajero) {
    this.pasajeros = pasajero;
  }

  public String getPasajeros() {
    StringBuilder pasajerosString = new StringBuilder();
    for (String pasajero : this.pasajeros) {
      pasajerosString.append(pasajero).append(";");
    }
    return pasajerosString.toString();
  }

  public String getPasajerosToShow() {
    StringBuilder pasajerosString = new StringBuilder();
    for (String pasajero : this.pasajeros) {
      pasajerosString.append(pasajero).append(",");
    }
    return pasajerosString
      .toString()
      .substring(0, pasajerosString.length() - 1);
  }

  public void setPlacaVehiculo(String placaVehiculo) {
    this.placaVehiculo = placaVehiculo;
  }

  public String getPlacaVehiculo() {
    return this.placaVehiculo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getCodigo() {
    return this.codigo;
  }

  public String getHoraEntrada() {
    if (
      (this.horaEntrada == null || this.minutoEntrada == null) ||
      (this.horaEntrada.equals("") && this.minutoEntrada.equals(""))
    ) {
      return null;
    }
    return this.horaEntrada + ":" + this.minutoEntrada;
  }

  public String getOnlyHourEntrada() {
    return this.horaEntrada;
  }

  public String getOnlyMinuteEntrada() {
    return this.minutoEntrada;
  }

  public String getOnlyHourSalida() {
    return this.horaSalida;
  }

  public String getOnlyMinuteSalida() {
    return this.minutoSalida;
  }

  public String getNumeroEntrada() {
    return this.numeroEntrada;
  }
  public String getHoraSalida() {
    if (
      (this.horaSalida == null || this.minutoSalida == null) ||
      (this.horaSalida.equals("") && this.minutoSalida.equals(""))
    ) {
      return null;
    }
    return this.horaSalida + ":" + this.minutoSalida;
  }

  public String getMinutoEntrada() {
    return minutoEntrada;
  }

  public String getMinutoSalida() {
    return minutoSalida;
  }

  public void setHoraSalida(String horaSalida) {
    this.horaSalida = horaSalida;
  }

  public void setMinutoSalida(String minutoSalida) {
    this.minutoSalida = minutoSalida;
  }

  public String getZona() {
    return this.zona;
  }

  public String getNumeroParqueo() {
    return this.numeroParqueo;
  }

  public void setZona(String zona) {
    this.zona = zona;
  }

  public void setNumeroParqueo(String numeroParqueo) {
    this.numeroParqueo = numeroParqueo;
  }
  public String getEstado() {
    return this.estado;
  }
  public void setEstado(String estado) {
    this.estado = estado;
  }
}
