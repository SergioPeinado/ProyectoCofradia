package controlador;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

public class ControladorCSV {
	/*metodo que lee con CSVReader
	 * las lineas de un fichero CSV 
	 * y devuelve una lista
	 * y ademas todas las operaciones con la base de datos 
	 * que tambien se van a ejecutar en este fichero.(por ahora y siempre que sea posible)
	 * ademas he agregado el metodo para crear un pdf.
	 * */
	private static ModificarTabla dtm = new ModificarTabla();
	
	private static final String[] cabecera={"Nombre","Ano Fundacion","Cofrades", "Titulares","Hora salida","Hora entrada"};
	
	public static List<Cofradia> devolverString(File inFile){
		String[] linea = new String[5];
		//creamos la lista
		List<Cofradia> lista = new ArrayList<>();
		
		try {
			//leemos el fichero con CSVReader
			CSVReader prueba = new CSVReader(new FileReader(inFile));
			linea = prueba.readNext();
			while(linea != null){
				//anadimos a la lista los campos del CSV.
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
		ControladorBD.crearTablaCofradia(ControladorBD.getConexionCofrade());
		ControladorBD.insertarCofradias(ControladorBD.getConexionCofrade(), lista);
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
		//en la lista a�adimos un nuevo registro
		ControladorBD.insertarCofradiaVacia(ControladorBD.getConexionCofrade(), lista);
		lista.add(new Cofradia());
		return insertarRegistros(cabecera,lista);
	}
	//metodo que va a borrar un registro
	public static DefaultTableModel borrarRegistro(String[] cabecera, List<Cofradia> lista, int fila){
		//borramos una fila de la lista
		ControladorBD.borrarCofradia(ControladorBD.getConexionCofrade(), fila+1);
		lista.remove(fila);
		return insertarRegistros(cabecera, lista);
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
			//anado las columnas que va a tener el fichero
			PdfPTable filaCofradia = new PdfPTable(6);
			//anado la cabecera
			filaCofradia.setHeaderRows(1);
			for(String c : cabecera){
				filaCofradia.addCell(c);				
			}
			//recorro el bucle para ir anadiendo
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
			//anado las filas.
			archivoPDF.add(filaCofradia);
			archivoPDF.close();
		} catch (FileNotFoundException  | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

