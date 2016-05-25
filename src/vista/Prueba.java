package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.JobAttributes;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.plaf.basic.BasicTabbedPaneUI.TabbedPaneLayout;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import controlador.ControladorCSVBD;
import modelo.Cofradia;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Prueba extends JFrame {

	List<Cofradia> lista = new ArrayList<>();
	boolean actualizar = true;
	boolean borrar = false;
	int vez =0;
	int numero_registro;
	int d;
	private JPanel contentPane;
	private JTable table;
	private JScrollPane  scrollPane;
	private String[] cabecera;
	private DefaultTableModel dtm;
	//private JLabel lblRegistro; 
	private JTextField titulares;
	private JTextField salida;
	private JTextField fundacion;
	private JTextField nombre;
	private JTextField nCofrades;
	private JTextField entrada;
	JLabel lblRegistro = new JLabel("Registro:");
	
	public Prueba(){
		componentes();
		table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) ->{
			if(!borrar && !nombre.getText().matches("") && !fundacion.getText().matches("") && !nCofrades.getText().matches("") &&
					!titulares.getText().matches("") && !salida.getText().matches("") && !entrada.getText().matches("")){
				Cofradia c = new Cofradia(nombre.getText(), Integer.parseInt(fundacion.getText()), Integer.parseInt(nCofrades.getText()), Integer.parseInt(titulares.getText()),
						salida.getText(), entrada.getText());
						
			if(!lista.get(numero_registro).equals(c) && vez < -1){
				vez++;
				if(table.getSelectedRow() != -1){
					d = table.getSelectedRow();
				}
				int valor = JOptionPane.showConfirmDialog(rootPane, "�Desea guardar el registro?","Guardar",JOptionPane.YES_NO_OPTION);
				if(valor == JOptionPane.YES_OPTION);{
					actualizar = false;
					lista.get(numero_registro).setCofradia(new Cofradia(nombre.getText(), Integer.parseInt(fundacion.getText()), Integer.parseInt(nCofrades.getText()), Integer.parseInt(titulares.getText()),
						salida.getText(), entrada.getText()));
					table.setModel(ControladorCSVBD.insertarRegistros(cabecera, lista));
					actualizar = true;
					table.setRowSelectionInterval(d, d);
				}
				vez = 0;
			}
		}
			if(table.getSelectedRow() != -1){
				numero_registro = table.getSelectedRow();
				lblRegistro.setText("Registro: "+(table.getSelectedRow()+1)+" de "+table.getRowCount());
			}
			if (actualizar){
				nombre.setText(lista.get(table.getSelectedRow()).getNombre());
				fundacion.setText(lista.get(table.getSelectedRow()).getFundacion()+"");
				nCofrades.setText(lista.get(table.getSelectedRow()).getN_hermanos()+"");
				titulares.setText(lista.get(table.getSelectedRow()).getTitulares()+"");
				salida.setText(lista.get(table.getSelectedRow()).getH_salida());
				entrada.setText(lista.get(table.getSelectedRow()).getH_entrada());
			}
	  });
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Prueba frame = new Prueba();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	private void componentes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 440);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JFileChooser cargarArchivo = new JFileChooser();
		
		JMenu mnNewMenu = new JMenu("Archivo");
		menuBar.add(mnNewMenu);
		
		JMenuItem cargar = new JMenuItem("Cargar");
		mnNewMenu.add(cargar);
		cargar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int elegir = cargarArchivo.showOpenDialog(contentPane);
				if (elegir == JFileChooser.APPROVE_OPTION){
					lista = ControladorCSVBD.devolverString(cargarArchivo.getSelectedFile());
					table.setModel(ControladorCSVBD.insertarRegistros(cabecera, lista));
					
				}
				
			}
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		JSplitPane panel1 = new JSplitPane();
		
		
		
		panel1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel1.setResizeWeight(0.5);
		contentPane.add(panel1);
		
		JScrollPane scrollPane = new JScrollPane();
		panel1.setLeftComponent(scrollPane);
		//scrollPane.setVerticalScrollBar(new JScrollBar(JScrollBar.VERTICAL));
		
		cabecera = new String[] {"Nombre","Ano Fundacion","N� hermanos", "Titulares","Hora salida","Hora entrada"};
		dtm = new DefaultTableModel();
		dtm.setColumnIdentifiers(cabecera);
		
		
		table = new JTable(dtm);
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel1.setRightComponent(panel);
		
		nombre = new JTextField();
		nombre.setColumns(10);
		
		fundacion = new JTextField();
		fundacion.setColumns(10);
		
		titulares = new JTextField();
		titulares.setColumns(10);
		
		salida = new JTextField();
		salida.setColumns(10);
		
		entrada = new JTextField();
		entrada.setColumns(10);
		
		nCofrades = new JTextField();
		nCofrades.setColumns(10);
		
		JLabel lblNombre = DefaultComponentFactory.getInstance().createLabel("Nombre: ");
		 nombre.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				celdaNombreActionPerformed(evt);				
			}			 
		 });
		JLabel lblFundacin = DefaultComponentFactory.getInstance().createLabel("Fundaci\u00F3n: ");
			fundacion.addActionListener (new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					celdaFuncadionActionPerformed(evt);				
				}			 
			 });
		JLabel lblNDeCofrades = DefaultComponentFactory.getInstance().createLabel("N\u00BA de Cofrades:");
			nCofrades.addActionListener (new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					celdaHermanosActionPerformed(evt);			
			}			 
		 });
		JLabel lblSalida = DefaultComponentFactory.getInstance().createLabel("Salida");
		salida.addActionListener (new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				celdaSalidaActionPerformed(evt);				
			}			 
		 });
		JLabel lblEntrada = DefaultComponentFactory.getInstance().createLabel("Entrada:");
		entrada.addActionListener (new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				celdaEntradaActionPerformed(evt);				
			}			 
		 });
		
		
		
		JLabel lblTitulares = DefaultComponentFactory.getInstance().createLabel("Titulares: ");
		titulares.addActionListener (new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				celdaTitularesActionPerformed(evt);				
			}			 
		 });
		JButton btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener (new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botonA�adirActionPerformed(evt);				
			}			 
		 });
		JButton btnSiguiente = new JButton("Siguiente");
		btnSiguiente.addActionListener (new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botonSiguienteActionPerformed(evt);				
			}			 
		 });
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener (new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botonEliminarActionPerformed(evt);				
			}			 
		 });
		JButton btnAtras = new JButton("Atras");
		btnAtras.addActionListener (new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botonAtrasActionPerformed(evt);				
			}			 
		 });
		
		//JLabel lblRegistro = new JLabel("Registro:");
		
		//lblRegistro.setText("Registro");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNDeCofrades)
								.addComponent(lblNombre)
								.addComponent(lblSalida))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(nCofrades, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(nombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(salida, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblFundacin)
										.addComponent(lblTitulares)
										.addComponent(lblEntrada))
									.addGap(29))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(btnNuevo)
									.addGap(18)
									.addComponent(btnBorrar, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(60)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(titulares, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(fundacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(entrada, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(21)
									.addComponent(btnAtras, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(btnSiguiente, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)))
							.addGap(40))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblRegistro)
							.addContainerGap(516, Short.MAX_VALUE))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNombre)
								.addComponent(nombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNDeCofrades)
								.addComponent(nCofrades, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSalida)
								.addComponent(salida, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(fundacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblFundacin))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(titulares, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTitulares))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(entrada, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblEntrada))))
					.addGap(57)
					.addComponent(lblRegistro)
					.addPreferredGap(ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNuevo)
						.addComponent(btnBorrar)
						.addComponent(btnAtras)
						.addComponent(btnSiguiente))
					.addGap(28))
		);
		panel.setLayout(gl_panel);
						
	}
	
	private void celdaNombreActionPerformed(java.awt.event.ActionEvent evt){
		 lista.get(table.getSelectedRow()).setNombre(nombre.getText());
		 int fila = table.getSelectedRow();
		 actualizar = false;
		 table.setModel(ControladorCSVBD.insertarRegistros(cabecera, lista));
		 actualizar = true;
		 table.setRowSelectionInterval(fila, fila);
	 }
	 private void celdaFuncadionActionPerformed(java.awt.event.ActionEvent evt){
		 lista.get(table.getSelectedRow()).setFundacion(Integer.parseInt(fundacion.getText()));
		 int fila = table.getSelectedRow();
		 actualizar = false;
		 table.setModel(ControladorCSVBD.insertarRegistros(cabecera, lista));
		 actualizar = true;
		 table.setRowSelectionInterval(fila, fila);
	 }
	 private void celdaHermanosActionPerformed(java.awt.event.ActionEvent evt){
		 lista.get(table.getSelectedRow()).setN_hermanos(Integer.parseInt(nCofrades.getText()));
		 int fila = table.getSelectedRow();
		 actualizar = false;
		 table.setModel(ControladorCSVBD.insertarRegistros(cabecera, lista));
		 actualizar = true;
		 table.setRowSelectionInterval(fila, fila);
	 }
	 private void celdaTitularesActionPerformed(java.awt.event.ActionEvent evt){
		 lista.get(table.getSelectedRow()).setTitulares(Integer.parseInt(titulares.getText()));
		 int fila = table.getSelectedRow();
		 actualizar = false;
		 table.setModel(ControladorCSVBD.insertarRegistros(cabecera, lista));
		 actualizar = true;
		 table.setRowSelectionInterval(fila, fila);
	 }
	 private void celdaSalidaActionPerformed(java.awt.event.ActionEvent evt){
		 lista.get(table.getSelectedRow()).setH_salida(salida.getText());
		 int fila= table.getSelectedRow();
		 actualizar = false;
		 table.setModel(ControladorCSVBD.insertarRegistros(cabecera, lista));
		 actualizar = true;
		 table.setRowSelectionInterval(fila, fila);
	 }
	 private void celdaEntradaActionPerformed(java.awt.event.ActionEvent evt){
		 lista.get(table.getSelectedRow()).setH_entrada(entrada.getText());
		 int fila= table.getSelectedRow();
		 actualizar = false;
		 table.setModel(ControladorCSVBD.insertarRegistros(cabecera, lista));
		 actualizar = true;
		 table.setRowSelectionInterval(fila, fila);
	 }
	 private void botonSiguienteActionPerformed(java.awt.event.ActionEvent evt){
		 if(table.getSelectedRow()!=table.getRowCount()-1 && table.getSelectedRow()!=-1)
			 table.setRowSelectionInterval(table.getSelectedRow()+1,table.getSelectedRow()+1);
		 else if(table.getSelectedRow()==table.getRowCount()-1 && table.getSelectedRow()!=-1){
	            table.setRowSelectionInterval(0, 0);
	                    
	        }
	 }
	 private void botonAtrasActionPerformed(java.awt.event.ActionEvent evt){
		 if (table.getSelectedRow()!=0 && table.getSelectedRow()!=-1)
	            table.setRowSelectionInterval(table.getSelectedRow()-1, table.getSelectedRow()-1);
	        else if (table.getSelectedRow()==0){
	            table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
	           
	        }	  
	 }
	 private void botonA�adirActionPerformed(java.awt.event.ActionEvent evt){
		 actualizar =false;
		 table.setModel(ControladorCSVBD.agregarRegistro(cabecera,lista));	 
		 actualizar = true;
		 table.setRowSelectionInterval(table.getRowCount()-1,table.getRowCount()-1);
	 }
	 private void botonEliminarActionPerformed(java.awt.event.ActionEvent evt){
		 borrar = true;
		 actualizar =false;
		 table.setModel(ControladorCSVBD.borrarRegistro(cabecera, lista, table.getSelectedRow()));
		 actualizar = true;
		 if(table.getRowCount() !=0){
			 if(numero_registro == table.getRowCount())
				 numero_registro -=1;
			 table.setRowSelectionInterval(numero_registro, numero_registro);
		 }
		 borrar = false;
		
	 }
}
