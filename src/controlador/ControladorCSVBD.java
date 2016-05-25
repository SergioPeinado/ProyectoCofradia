package controlador;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.table.DefaultTableModel;
import org.sqlite.SQLiteConfig;

import  com.opencsv.CSVReader;

import modelo.Cofradia;

public class ControladorCSVBD {
	/*metodo que lee con CSVReader
	 * las lineas de un fichero CSV 
	 * y devuelve una lista
	 * y ademas todas las operaciones con la base de datos 
	 * que tambien se van a ejecutar en este fichero.(por ahora y siempre que sea posible)
	 * */
	private static Connection conexionCofrade = null;
	private static Statement sentencia;
	private static PreparedStatement sp;
	private ControladorCSVBD(){};
	public static List<Cofradia> devolverString(File inFile){
		String[] linea = new String[5];
		//creamos la lista
		List<Cofradia> lista = new ArrayList<>();
		
		try {
			//leemos el fichero con CSVReader
			CSVReader prueba = new CSVReader(new FileReader(inFile));
			linea = prueba.readNext();
			while(linea != null){
				//añadimos a la lista los campos del CSV.
				lista.add(new Cofradia(linea[0], Integer.parseInt(linea[1]), Integer.parseInt(linea[2]), Integer.parseInt(linea[3]), linea[4], linea[5]));
				linea = prueba.readNext();
			}
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}
	/*Con el siguiente metodo introducimos los datos del CSV 
	 * ya cargados en memoria a la tabla
	 * 
	 * */
	public static DefaultTableModel insertarRegistros(String[] cabecera, List<Cofradia> lista){
		//creo el defaultTableModel
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.setColumnIdentifiers(cabecera);
		String[] datosTabla = new String[cabecera.length];
		//Con el bucle for recorro la lista
		//y obtengo los datos para volcarlos 
		//a la tabla
		for (int i = 0; i < lista.size(); i++) {
			datosTabla[0] = lista.get(i).getNombre();
			datosTabla[1] = lista.get(i).getFundacion()+"";
			datosTabla[2] = lista.get(i).getN_hermanos()+"";
			datosTabla[3] = lista.get(i).getTitulares()+"";
			datosTabla[4] = lista.get(i).getH_salida();
			datosTabla[5] = lista.get(i).getH_entrada();
			dtm.addRow(datosTabla);	
		
			
	}
		return dtm;	
	}
	//metodo que va a agregar un nuevo registro
	public static DefaultTableModel agregarRegistro(String[] cabecera, List<Cofradia> lista){
		lista.add(new Cofradia());
		return insertarRegistros(cabecera,lista);
	}
	//metodo que va a borrar un registro
	public static DefaultTableModel borrarRegistro(String[] cabecera, List<Cofradia> lista, int fila){
		lista.remove(fila);
		return insertarRegistros(cabecera, lista);
	}
	public static Connection getConexionCofrade(){
		//vamos a cerrar la conexion
		//con el hook de la máquina virtual
		Runtime.getRuntime().addShutdownHook(new MiShutdownHook());
		//usamos el patron Singleton
		if(conexionCofrade == null){
			//vamos a trabajar con el fichero de propiedades		
			ResourceBundle rb = ResourceBundle.getBundle("sqlite");
			String url = rb.getString("url");
			String driver = rb.getString("driver");
			//y empezamos cargando todos los drivers
			try {
				//lo cargamos
				Class.forName(driver);
				//establecemos una config de sqlite
				SQLiteConfig configuracion = new SQLiteConfig();
				configuracion.enforceForeignKeys(true);
				conexionCofrade = DriverManager.getConnection(url,configuracion.toProperties());
				//cargamos la bd
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return conexionCofrade;
	}
	public static void crearTablaCofradia(Connection con){
		//La sentencia se crea como un  String
		//En mi caso sera sqlC, de cofradia
		String sqlC = "CREATE TABLE IF NOT EXISTS cofradia("
				+ "nombre TEXT PRIMARY KEY,"
				+ "fundacion INTEGER,"
				+ "cofrades INTEGER,"
				+ "titulares INTEGER,"
				+ "salida TEXT,"
				+ "entrada TEXT)";
		try {
			
			sentencia = con.createStatement();
			sentencia.executeUpdate(sqlC);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void insertarContenido(Connection con,List<Cofradia> lista){
		for (Cofradia c : lista) {
			String sqlc1 = "INSERT INTO cofradia VALUES"
					+ "(null,"+"'"+c.getNombre()+"',"+c.getFundacion()+","+c.getN_hermanos()+","+c.getTitulares()+",'"+c.getH_salida()+"','"+c.getH_entrada()+"')";
			try {
				sentencia = con.createStatement();
				sentencia.executeUpdate(sqlc1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static int actualizarCofradias(Connection con, Cofradia c, int posicion){
		int cambios = 0;
		String sqlc = "UPDATE cofradia SET nombre=?,fundacion=?,cofrades=?"
				+ "titulares=?, salida=?,entrada=?"
				+ "WHERE nombre=?";
			try {
				sp= con.prepareStatement(sqlc);
				sp.setString(1, c.getNombre());
				sp.setString(2, c.getFundacion()+"");
				sp.setString(3, c.getN_hermanos()+"");
				sp.setString(4, c.getTitulares()+"");
				sp.setString(5, c.getH_salida());
				sp.setString(6, c.getH_entrada());
				sp.setString(7, posicion+"");
				cambios = sp.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return cambios;
	}
	public static int borrarCofradia(Connection con, Cofradia c, int borrados){
		return borrados;
	}
	static class MiShutdownHook extends Thread{
		
	}
	
}

