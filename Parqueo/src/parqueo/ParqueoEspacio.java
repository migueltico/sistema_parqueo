/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parqueo;

import java.util.ArrayList;

/**
 *
 * @author migue
 */
public class ParqueoEspacio {

  private String _horaEntrada;
  private String _minutoEntrada;
  private String _placaVehiculo;
  private ArrayList<String> _pasajeros = new ArrayList<>();
  private String _numeroEntrada;
  private String _horaSalida;
  private String _minutoSalida;
  private String _codigo;

  ///make a constructor
  public ParqueoEspacio(
    String horaEntrada,
    String minutoEntrada,
    String numeroEntrada
  ) {
    this._horaEntrada = horaEntrada;
    this._minutoEntrada = minutoEntrada;
    this._numeroEntrada = numeroEntrada;
  }

  //make getters and setters for pasajeros placaVehiculo and codigo
  public void setPasajeros(ArrayList<String> pasajero) {
    this._pasajeros = pasajero;
  }

  public ArrayList<String> getPasajeros() {
    return this._pasajeros;
  }

  public void setPlacaVehiculo(String placaVehiculo) {
    this._placaVehiculo = placaVehiculo;
  }

  public String getPlacaVehiculo() {
    return this._placaVehiculo;
  }

  public void setCodigo(String codigo) {
    this._codigo = codigo;
  }

  public String getCodigo() {
    return this._codigo;
  }

  //make a method to get the time of the parking space
  public String getHoraEntrada() {
    return this._horaEntrada + ":" + this._minutoEntrada;
  }

  //make a method to get the number of the parking space
  public String getNumeroEntrada() {
    return this._numeroEntrada;
  }

  //make a method to get the time of the parking space
  public String getHoraSalida() {
    if (
      this._horaSalida == null || this._minutoSalida == null
    ) return "No ha salido";
    return this._horaSalida + ":" + this._minutoSalida;
  }
}
