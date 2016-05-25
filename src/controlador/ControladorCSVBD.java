package controlador;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import  com.opencsv.CSVReader;

import modelo.Cofradia;
import vista.Prueba;

public class ControladorCSVBD {
	/*metodo que lee con CSVReader
	 * las lineas de un fichero CSV 
	 * y devuelve una lista
	 * y ademas todas las operaciones con la base de datos 
	 * que tambien se van a ejecutar en este fichero.(por ahora y siempre que sea posible)
	 * ademas he agregado el metodo para crear un pdf.
	 * */
	private static ModificarTabla dtm = new ModificarTabla();
	private static Connection conexionCofrade = null;
	private static Statement sentencia;
	private static final String[] cabecera={"Nombre","Ano Fundacion","Nº hermanos", "Titulares","Hora salida","Hora entrada"};
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
	 * yan cargados en memoria a la tabla
	 * 
	 * */
	public static DefaultTableModel insertarRegistros(String[] cabecera, List<Cofradia> lista){
		//creo el defaultTableModel
		//DefaultTableModel dtm = new DefaultTableModel();
		dtm.setRowCount(0);
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
		//en la lista añadimos un nuevo registro
		lista.add(new Cofradia());
		return insertarRegistros(cabecera,lista);
	}
	//metodo que va a borrar un registro
	public static DefaultTableModel borrarRegistro(String[] cabecera, List<Cofradia> lista, int fila){
		//borramos una fila de la lista
		lista.remove(fila);
		return insertarRegistros(cabecera, lista);
	}
	//creamos el metodo para obtener la conexion con la base de datos 
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
	//este metodo insertar el contenido de las filas en la base de datos
	public static void insertarContenido(Connection con,List<Cofradia> lista){
		for (Cofradia c : lista) {
			//en el string sqlc1 agregamos el INSERT
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
	//metodo para actualizar las cofradias que se modifiquen en la tabla
	public static int actualizarCofradias(Connection con, Cofradia c, int posicion){
		//creamos la variable para almacenar los cambios que sufra la bd
		int cambios = 0;
		String sqlc = "UPDATE cofradia SET nombre=?,fundacion=?,cofrades=?"
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
	
	//metodo para borrar datos de la tabla
	//el cual funciona
	//pero no llego a usar por falta de conocimientos
	//y no entender bien que hace
	public static int borrarCofradia(Connection con, int borrar){
		//creamos la variable para contar las filas que se borran
		int borrados =0;
		String sqlc="DELETE FROM cofradia WHERE nombre=?";
		try {
			//esta parte no la llego a entender del todo
			//por lo cual la tengo pero no la uso
			sp = con.prepareStatement(sqlc);
			//No se que borra exactamente.
			sp.setString(1, borrar+"");
			borrados = sp.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return borrados;
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
	//ultimo metodo de la clase
	//que nos va a generar un PDF 
	//de lo que se encuentra en la tabla 
	
	public static void creacionDePDF(List<Cofradia> lista){
		//creo el documento
		Document archivoPDF = new Document();
		try {
			//Instancio donde como se va a llamar y el nuevo fichero 
			//que se crea
			PdfWriter.getInstance(archivoPDF, new FileOutputStream(new File("Cofradias.pdf")));
			archivoPDF.open();
			//añado las columnas que va a tener el fichero
			PdfPTable filaCofradia = new PdfPTable(6);
			//añado la cabecera
			filaCofradia.setHeaderRows(1);
			for(String c : cabecera){
				filaCofradia.addCell(c);				
			}
			//recorro el bucle para ir añadiendo
			//todas las filas de la lista cofradia
			for (Cofradia cofradia : lista) {
				filaCofradia.setSpacingAfter(5);
				filaCofradia.addCell(cofradia.getNombre());
				filaCofradia.addCell(cofradia.getFundacion()+"");
				filaCofradia.addCell(cofradia.getN_hermanos()+"");
				filaCofradia.addCell(cofradia.getTitulares()+"");
				filaCofradia.addCell(cofradia.getH_salida());
				filaCofradia.addCell(cofradia.getH_entrada());
			}
			//añado las filas.
			archivoPDF.add(filaCofradia);
			archivoPDF.close();
		} catch (FileNotFoundException  | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

