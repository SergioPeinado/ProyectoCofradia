package controlador;

import javax.swing.table.DefaultTableModel;

public class ModificarTabla  extends DefaultTableModel{
	//Esta clase va a permitir, bueno mas bien va a 
	//no permitir que se pueda modificar la tabla 
	//a la hora de escribir en ella y tal.
	/* @author Sergio
	 * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
