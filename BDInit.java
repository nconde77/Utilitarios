import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;


public class BDInit {
	
	private static final String URI_CONFIG = "./bdinit.config";
	
	public static String getProperty(String nombre) throws FileNotFoundException, IOException {
		Properties config = new Properties();
		config.load (new FileInputStream (URI_CONFIG));
		String propiedad = config.getProperty(nombre);
		
		return propiedad;
	}	// getProerty
	
	/*
	 * Crear la base para persistencia del juego e inicializarla.
	 */
	public static void bdInit(String url) {
		Connection conn = null;
		Statement stmt = null;
		
		try {	// Conectar con la BD e inicializarla.
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + url);
			String sql = new String();
			stmt = conn.createStatement();
			
			System.out.println("Conexión a la base " + url + " establecida.");
			
			// Tabla de patrulleros OPV. 
			sql = "CREATE TABLE OPV " +
					"(ID INT PRIMARY KEY NOT NULL," +
					" PUNTAJE      INT   NOT NULL);";
			System.out.println("Update 1: " + sql);
			stmt.executeUpdate(sql);
			
			// Tabla de buques pesqueros.
			sql = "CREATE TABLE PESQUERO "+
					"(ID INT PRIMARY KEY NOT NULL," +
					" MATRICULA  INT   NOT NULL," +
					" VEL_MAX    INT   NOT NULL," +
					" CAP_CARGA  INT   NOT NULL," +
					" CAP_TANQUE INT   NOT NULL)";
			System.out.println("Update 2: " + sql);
			stmt.executeUpdate(sql);
			System.out.println("Fin updates.");
			
			// Terminada la creacion de tablas, cerrar el statement.
			stmt.close();
		}
		catch (SQLException e) {
			//System.out.println(e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {	// Cerrar la conexión.
			try {
				if (conn != null) {
					System.out.println("Cerrando conexión a la bd.");
					conn.close();
				}
			}
			catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}	// bdInit
	
	
	public static void main(String[] args) {
		String bdURI;
		
		try {
			bdURI = getProperty("ruta_bd");
			System.out.println("Inicializando base de datos...");
			bdInit(bdURI);
			System.out.println("Base de datos inicializada.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	// main

}	// BDInit
