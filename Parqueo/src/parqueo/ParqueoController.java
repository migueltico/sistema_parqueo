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
public class ParqueoController implements IParqueo {

    private ArrayList<ParqueoEspacio> _db = new ArrayList<>();

    @Override
    public void RegistrarIngreso() {
        ParqueoEspacio parqueoInfo = new ParqueoEspacio();

        parqueoInfo._horaEntrada = "10";
        parqueoInfo._horaSalida = "12";
        parqueoInfo._minutoEntrada = "23";
        parqueoInfo._numeroEntrada = "968554132A";
        parqueoInfo._pasajeros.add("Pablo");
        parqueoInfo._pasajeros.add("Maria");
        parqueoInfo._pasajeros.add("Juan");
        parqueoInfo._placaVehiculo = "BDA 7896";

        _db.add(parqueoInfo);

    }

    public void MostrarParqueo() {
        _db.forEach((espacio) -> {
            System.out.println("Placa: " + espacio._placaVehiculo);
            System.out.println("Hora de Entrada: " + espacio._horaEntrada);
            System.out.println("Hora de Salida: " + espacio._horaSalida);
            System.out.println("Numero de entrada: " + espacio._numeroEntrada);
            

            String _nombres = "";

            for (String nombre : espacio._pasajeros) {
                _nombres += nombre + ", ";
            }
            _nombres = _nombres.substring(0,_nombres.length() - 2);
            System.out.println("Pasajeros: " + _nombres);

        });
    }
}
