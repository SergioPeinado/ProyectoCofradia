package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelo.Cofradia;

public class ActualizadorBD {
	private static PreparedStatement sentenciaAct;
	public static int actualizarNombre(Connection con, String nombrec, String newNombre){
		int nombreCambiado=0;
		String sql="UPDATE usuario SET nombre=? WHERE nombre = ?";
		try {
			sentenciaAct = con.prepareStatement(sql);
			sentenciaAct.setString(1, nombrec);
			sentenciaAct.setString(2, newNombre);
			nombreCambiado = sentenciaAct.executeUpdate();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				sentenciaAct.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return nombreCambiado;
	}
	public static int actualizarFundacion(Connection con, int fundacionc, int newFundacion){
		int fundacionCambiada=0;
		String sql="UPDATE fundacion SET fundacion=? WHERE fundacion=?";
		try {
			sentenciaAct = con.prepareStatement(sql);
			sentenciaAct.setInt(1, fundacionc);
			sentenciaAct.setInt(2, newFundacion);
			fundacionCambiada = sentenciaAct.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				sentenciaAct.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fundacionCambiada;
	}
	public static int actualizarCofrades(Connection con, int cofradesc, int newCofrades){
		int cofradesCambiados=0;
		String sql ="UPDATE cofrades SET cofrades=? WHERE cofrades=?";
		try {
			sentenciaAct = con.prepareStatement(sql);
			sentenciaAct.setInt(1, cofradesc);
			sentenciaAct.setInt(2, newCofrades);
			cofradesCambiados = sentenciaAct.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				sentenciaAct.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return cofradesCambiados;
	}
	public static int actualizarTitulares(Connection con, int titularesc, int newTitulares){
		int titularesCambiados=0;
		String sql = "UPDATE titulares SET titulares=? WHERE titulares=?";
		try {
			sentenciaAct = con.prepareStatement(sql);
			sentenciaAct.setInt(1, titularesc);
			sentenciaAct.setInt(2, newTitulares);
			titularesCambiados = sentenciaAct.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				sentenciaAct.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return titularesCambiados;
	}
	public static int actualizarSalida(Connection con, String salidac, String newSalida){
		int salidaCambiada = 0;
		String sql ="UPDATE salida SET salida=? WHERE salida=?";
		try {
			sentenciaAct = con.prepareStatement(sql);
			sentenciaAct.setString(1, salidac);
			sentenciaAct.setString(2, newSalida);
			salidaCambiada = sentenciaAct.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				sentenciaAct.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return salidaCambiada;
	}
	public static int actualizarEntrada(Connection con, String entradac, String newEntrada){
		int entradaCambiada = 0;
		String sql ="UPDATE entrada SET entrada=? WHERE entrada=?";
		try {
			sentenciaAct = con.prepareStatement(sql);
			sentenciaAct.setString(1, entradac);
			sentenciaAct.setString(2, newEntrada);
			entradaCambiada = sentenciaAct.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				sentenciaAct.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return entradaCambiada;
	}
	
}
