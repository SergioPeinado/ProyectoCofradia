package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.sqlite.SQLiteConfig;

import controlador.ControladorBD.MiShutdownHook;
import modelo.Cofradia;

public class ControladorBD {
	private static Connection conexionCofrade = null;
	private static Statement sentencia;
	private static PreparedStatement sp;
	private ControladorBD(){};
	//creamos el metodo para obtener la conexion con la base de datos 
		public static Connection getConexionCofrade(){
			//vamos a cerrar la conexion
			//con el hook de la maquina virtual
			Runtime.getRuntime().addShutdownHook(new MiShutdownHook());
			//usamos el patron Singleton
			if(conexionCofrade == null){
				try {
				//vamos a trabajar con el fichero de propiedades		
				ResourceBundle rb = ResourceBundle.getBundle("sqlite");
				String url = rb.getString("url");
				String driver = rb.getString("driver");
				//y empezamos cargando todos los drivers
				
					//lo cargamos
					Class.forName(driver);
					//establecemos una config de sqlite
					SQLiteConfig configuracion = new SQLiteConfig();
					//configuracion.enforceForeignKeys(true);
					conexionCofrade = DriverManager.getConnection(url,configuracion.toProperties());
					//cargamos la bd
				} catch (SQLException  |ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			return conexionCofrade;
		}
		//me dispongo a crear la tabla
		public static void crearTablaCofradia(Connection con){
			//La sentencia se crea como un  String
			//En mi caso sera sqlC, c de cofradia
			String sqlC = "CREATE TABLE IF NOT EXISTS cofradia("
					+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "nombre TEXT,"
					+ "fundacion INTEGER,"
					+ "cofrades INTEGER,"
					+ "titulares INTEGER,"
					+ "salida TEXT,"
					+ "entrada TEXT)";
			try {
				//creamos la conexion y la ejecutamos
				sentencia = con.createStatement();
				sentencia.executeUpdate(sqlC);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//metodo para actualizar las cofradias que se modifiquen en la tabla
		public static int actualizarCofradias(Connection con, Cofradia c, int posicion){
			//creamos la variable para almacenar los cambios que sufra la bd
			int cambios = 0;
			String sqlc = "UPDATE cofradia SET nombre=?,fundacion=?,cofrades=?,"
					+ "titulares=?, salida=?,entrada=?"
					+ "WHERE id=?";
				try {
					sp= con.prepareStatement(sqlc);
					sp.setString(1, c.getNombre());
					sp.setString(2, c.getFundacion()+"");
					sp.setString(3, c.getN_hermanos()+"");
					sp.setString(4, c.getTitulares()+"");
					sp.setString(5, c.getH_salida());
					sp.setString(6, c.getH_entrada());
					sp.setString(7, posicion+"");
					//una vez que se han obtenido y modificado los cambios
					//hacemos que se ejecute el update
					cambios = sp.executeUpdate();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return cambios;
		}
		public static void insertarCofradias(Connection con, List<Cofradia> lista){
			for (Cofradia cofradia : lista) {
				String sql = "INSERT INTO cofradia VALUES"
						+ "(null"+", '"+cofradia.getNombre()+"', "+cofradia.getFundacion()+" , "+cofradia.getN_hermanos()+" , "+cofradia.getTitulares()+", ' "+cofradia.getH_salida()+" ', ' "+cofradia.getH_entrada()+" ')";
				try {
					sentencia = con.createStatement();
					sentencia.executeUpdate(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					try {
						sentencia.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		public static void insertarCofradiaVacia(Connection con, List<Cofradia> lista){
			
			try {
				String sql = "INSERT INTO cofradia VALUES"
						+ "(null, null, 0, 0, 0, null, null)";
				sentencia = con.createStatement();
				sentencia.executeUpdate(sql);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					sentencia.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
		public static int borrarCofradia(Connection con, int index){
			int borrado = 0;
			
			try {
				PreparedStatement sp;
				String sql = "DELETE FROM cofradia WHERE id=?";
				sp = con.prepareStatement(sql);
				sp.setString(1, index+"");
				borrado = sp.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return borrado;
		}
		public static int verNumeroRegistros(Connection con){
			ResultSet resultado = null;
			int numReg = 0;
			try {
				sentencia = con.createStatement();
				String sql ="Select COUNT(id) from cofradia";
				resultado = sentencia.executeQuery(sql);
				numReg = Integer.parseInt(resultado.getString(1));
			} catch (SQLException e) {
				return 0;
			}finally{
				try {
					sentencia.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return numReg;
		}
		public static List<Cofradia> devolverCofradias(Connection con){
			List <Cofradia> lista  = new ArrayList<>();
			ResultSet resultado = null;
			try {
				sentencia = con.createStatement();
				String sql = "Select * from cofradia";
				resultado = sentencia.executeQuery(sql);
				while(resultado.next()){
					lista.add(new Cofradia(resultado.getString(2),resultado.getInt(3),resultado.getInt(4),resultado.getInt(5),resultado.getString(6), resultado.getString(7)));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					sentencia.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return lista;
		}
		public static void crearTriggerCofradia(Connection con){
			
		}
		public static void crearVistaCofradia(Connection con){
			
		}
		static class MiShutdownHook extends Thread{

			/* (non-Javadoc)
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(conexionCofrade != null){
					try {
						conexionCofrade.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
}
