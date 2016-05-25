package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JToolBar;
import javax.swing.JTable;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import controlador.ControladorCSVBD;
import modelo.Cofradia;

import javax.swing.JButton;

public class VistaProyecto extends JFrame {
	
	List<Cofradia> lista = new ArrayList<>();
	private JPanel contenedor;
	private JScrollPane scroll;
	protected String [] cabecera;
	protected DefaultTableModel dtm;
	protected JTable table;
	private JTextField titulares;
	private JTextField salida;
	private JTextField fundacion;
	private JTextField nombre;
	private JTextField nCofrades;
	private JTextField entrada;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaProyecto frame = new VistaProyecto();
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
	public VistaProyecto() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 488, 327);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Cargar Archivo");
		menuBar.add(mnNewMenu);
		JFileChooser cargarArchivo = new JFileChooser();
	    	
		
		JMenuItem abrir = new JMenuItem("Abrir");
		mnNewMenu.add(abrir);
		abrir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int elegir = cargarArchivo.showOpenDialog(contenedor);
				if (elegir == JFileChooser.APPROVE_OPTION){
					lista = ControladorCSVBD.devolverString(cargarArchivo.getSelectedFile());
					
				}
				
			}
		});
		//cargarArchivo.
		contenedor = new JPanel();
		contenedor.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contenedor);
		contenedor.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		contenedor.add(panel);
		
		scroll = new JScrollPane();
		cabecera = new String[] {"Nombre","Año Fundacion","Nº hermanos", "Titulares","Hora salida","Hora entrada"};
		dtm = new DefaultTableModel();
		dtm.setColumnIdentifiers(cabecera);
		table = new JTable(dtm);
		panel.add(scroll);
		//scroll.add(table);
		
		JPanel panel_1 = new JPanel();
		contenedor.add(panel_1);
		
		nombre = new JTextField();
		nombre.setColumns(10);
		
		nCofrades = new JTextField();
		nCofrades.setColumns(10);
		
		fundacion = new JTextField();
		fundacion.setColumns(10);
		
		titulares = new JTextField();
		titulares.setColumns(10);
		
		salida = new JTextField();
		salida.setColumns(10);
		
		entrada = new JTextField();
		entrada.setColumns(10);
		
		JLabel lblNombre = DefaultComponentFactory.getInstance().createLabel("Nombre: ");
		
		JLabel lblFundacin = DefaultComponentFactory.getInstance().createLabel("Fundaci\u00F3n: ");
		
		JLabel lblNDeCofrades = DefaultComponentFactory.getInstance().createLabel("N\u00BA de Cofrades:");
		
		JLabel lblTitulares = DefaultComponentFactory.getInstance().createLabel("Titulares: ");
		
		JLabel lblSalida = DefaultComponentFactory.getInstance().createLabel("Salida");
		
		JLabel lblEntrada = DefaultComponentFactory.getInstance().createLabel("Entrada:");
		
		JButton btnGuardar = new JButton("A\u00F1adir");
		
		JButton btnBorrar = new JButton("Borrar");
		
		JButton btnModificar = new JButton("Modificar");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(44)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNombre)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(nombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblFundacin))
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(3)
									.addComponent(lblSalida)
									.addGap(18)
									.addComponent(salida, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
									.addComponent(lblEntrada))))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(8)
									.addComponent(lblNDeCofrades)
									.addGap(18)
									.addComponent(nCofrades, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(71)
									.addComponent(btnGuardar)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(btnBorrar)
								.addComponent(lblTitulares))))
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(fundacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(10)
									.addComponent(titulares, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(entrada, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addContainerGap(93, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnModificar)
							.addGap(45))))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
							.addComponent(nombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNombre))
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
							.addComponent(fundacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblFundacin)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNDeCofrades)
						.addComponent(lblTitulares)
						.addComponent(titulares, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(nCofrades, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSalida)
						.addComponent(salida, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(entrada, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEntrada))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnGuardar)
						.addComponent(btnBorrar)
						.addComponent(btnModificar))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
	}
}
