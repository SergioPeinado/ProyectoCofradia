package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import controlador.ControladorBD;
import controlador.ControladorCSV
;

public class CofradiaDAOImpSqlite implements CofradiaDAO{
	private Connection conexionCofrade = ControladorBD.getConexionCofrade();
	private static PreparedStatement sp;
	@Override
	public void insertarCofradia(Cofradia c) {
			//en el string sqlc1 agregamos el INSERT
			String sqlc1 = "INSERT INTO cofradia VALUES"
					+ "(null,"+"'"+c.getNombre()+"',"+c.getFundacion()+","+c.getN_hermanos()+","+c.getTitulares()+",'"+c.getH_salida()+"','"+c.getH_entrada()+"')";
			try {
				sp = conexionCofrade.prepareStatement(sqlc1);
				sp.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	

	@Override
	public void borrarCofradia(Cofradia c) {
		String sqlc="DELETE FROM cofradia WHERE nombre=?";
		try {
			//esta parte no la llego a entender del todo
			//por lo cual la tengo pero no la uso
			sp = conexionCofrade.prepareStatement(sqlc);
			//No se que borra exactamente.
			sp.setString(1, c.getNombre());
			sp.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
