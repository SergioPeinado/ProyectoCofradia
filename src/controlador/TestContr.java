package controlador;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import modelo.Cofradia;

public class TestContr {
	public static void main(String[] args) {
		File inFile =  new File("C:\\Users\\Sergio\\Documents\\Cofradias.csv");
		String[] linea;
		
		List<Cofradia> lista = new ArrayList<>();
		//lista.add(new Cofradia(linea[0], Integer.parseInt(linea[1]), Integer.parseInt(linea[2]), Integer.parseInt(linea[3]), linea[4], linea[5]));
		lista = ControladorCSVBD.devolverString(inFile);
		
		System.out.println(lista.get(15).getNombre());
	}
}
