package parqueo;

public class Helper {
	
	public static String getFileName() {
		return Config.fileName;
	}
	
	public static String getAppName() {
		return Config.appName;
	}
	
	public static int getMaxPasajeros() {
		return Config.maxPasajeros;
	}
	
	public static int getMaxVehiculosEnParqueo() {
		return Config.maxVehiculosEnParqueo;
	}
	
	public static int getMaxZonasParqueo() {
		return Config.maxZonasParqueo;
	}
	
	public static double getCostoParqueoPorHora() {
		return Config.costoParqueoPorHora;
	}
	
	public static void setFileName(String fileName) {
		Config.fileName = fileName;
	}
	
	public static void setAppName(String appName) {
		Config.appName = appName;
	}
	
	public static void setMaxPasajeros(int maxPasajeros) {
		Config.maxPasajeros = maxPasajeros;
	}
	
	public static void setMaxVehiculosEnParqueo(int maxVehiculosEnParqueo) {
		Config.maxVehiculosEnParqueo = maxVehiculosEnParqueo;
	}
	
	public static void setMaxZonasParqueo(int maxZonasParqueo) {
		Config.maxZonasParqueo = maxZonasParqueo;
	}
	
	public static void setCostoParqueoPorHora(double costoParqueoPorHora) {
		Config.costoParqueoPorHora = costoParqueoPorHora;
	}
}
