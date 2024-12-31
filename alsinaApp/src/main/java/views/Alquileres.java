package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import raven.datetime.DatePicker;
import raven.datetime.event.DateSelectionEvent;
import raven.datetime.event.DateSelectionListener;

import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

public class Alquileres extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_3;
	private JButton btnNewButton;
	private JButton btnEliminar;
	private JButton btnNewButton_2;
	private JPanel panel;
	private JTabbedPane tabbedPane_1;
	private JLabel lblBuscarPorPatente;
	private JTextField textField_1;
	private JLabel lblFiltrarPorMarca;
	private JComboBox comboBox_1;
	private JPanel panel_1;
	private JTable table_1;
	private JTextField textField_4;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JComboBox comboBox_2;
	private JTextField textField_5;
	private JPanel panel_2;
	private JButton btnNewButton_3;

	public static void main(String args[]) {
		new Alquileres().setVisible(true);
	}
			
	public Alquileres() {

		setBounds(100, 100, 981, 790);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(10, 11, 945, 729);
		contentPane.add(tabbedPane_1);
		
		JPanel panel2 = new JPanel();

		panel = new JPanel();
		panel.setLayout(null);

		DatePicker dp = new DatePicker();
		dp.setDateSelectionMode(DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
		//dp.setSeparator("  Hasta  ");
		dp.setUsePanelOption(true);
		dp.addDateSelectionListener(new DateSelectionListener() {

			@Override
			public void dateSelected(DateSelectionEvent dateEvent) {
				LocalDate date[] = dp.getSelectedDateRange(); 
				System.out.println("desde "+ date[0] + "  hasta  "+ date[1]);
			}
			
		});
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(173, 20, 180, 30);
		dp.setEditor(formattedTextField);
		panel2.add(formattedTextField);
				
		tabbedPane_1.addTab("Autos de Alquiler", null, panel, null);
		tabbedPane_1.addTab("Alquileres", null, panel2, null);
		panel2.setLayout(null);
		
		table_1 = new JTable();
		table_1.setBounds(10, 82, 920, 608);
		panel2.add(table_1);
		
		textField_4 = new JTextField();
		textField_4.setBounds(47, 51, 171, 20);
		panel2.add(textField_4);
		textField_4.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Fechas");
		lblNewLabel_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_1.setBounds(47, 21, 104, 20);
		panel2.add(lblNewLabel_1);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Buscar por", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(306, 5, 438, 78);
		panel2.add(panel_2);
		panel_2.setLayout(null);
		
		lblNewLabel_2 = new JLabel("Cliente");
		lblNewLabel_2.setBounds(256, 16, 104, 20);
		panel_2.add(lblNewLabel_2);
		lblNewLabel_2.setVerticalAlignment(SwingConstants.BOTTOM);
		
		comboBox_2 = new JComboBox();
		comboBox_2.setBounds(256, 47, 171, 20);
		panel_2.add(comboBox_2);
		
		textField_5 = new JTextField();
		textField_5.setBounds(16, 47, 171, 20);
		panel_2.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Patente/dominio");
		lblNewLabel_1_1.setBounds(16, 16, 89, 20);
		panel_2.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setVerticalAlignment(SwingConstants.BOTTOM);
		
		JButton btnNewButton_4 = new JButton("PDF");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			try {

				//manipulatePdf();
				createPdf();
			
			} catch (IOException e1) {
				e1.printStackTrace();
			}					
			}
		});
		btnNewButton_4.setBounds(770, 24, 123, 32);
		panel2.add(btnNewButton_4);
		
		table = new JTable();
		table.setBounds(209, 95, 731, 606);
		panel.add(table);
		
		panel_1 = new JPanel();
		panel_1.setBackground(UIManager.getColor("CheckBox.light"));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_1.setBounds(6, 95, 193, 425);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("Ver Alquileres");
		btnNewButton_1.setBounds(12, 535, 181, 51);
		panel.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane_1.setSelectedIndex(1);
			}
		});
		
		JLabel lblNewLabel = new JLabel("Dominio");
		lblNewLabel.setBounds(6, 16, 82, 20);
		panel_1.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(6, 39, 181, 20);
		panel_1.add(textField);
		textField.setColumns(10);
		
		JLabel lblMarca = new JLabel("Marca");
		lblMarca.setBounds(6, 70, 82, 14);
		panel_1.add(lblMarca);
		
		JLabel lblModelo = new JLabel("Modelo");
		lblModelo.setBounds(6, 124, 82, 20);
		panel_1.add(lblModelo);
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setBounds(6, 176, 82, 14);
		panel_1.add(lblDescripcion);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(6, 95, 181, 18);
		panel_1.add(comboBox);
		
		textField_2 = new JTextField();
		textField_2.setBounds(6, 194, 181, 20);
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(6, 145, 181, 20);
		panel_1.add(textField_3);
		textField_3.setColumns(10);
		
		btnNewButton = new JButton("Confirmar");
		btnNewButton.setBounds(6, 232, 181, 51);
		panel_1.add(btnNewButton);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(6, 294, 181, 51);
		panel_1.add(btnEliminar);
		
		btnNewButton_2 = new JButton("cancelar");
		btnNewButton_2.setBounds(6, 360, 181, 51);
		panel_1.add(btnNewButton_2);
		
		lblBuscarPorPatente = new JLabel("Buscar por patente");
		lblBuscarPorPatente.setBounds(655, 19, 151, 20);
		panel.add(lblBuscarPorPatente);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(655, 39, 181, 20);
		panel.add(textField_1);
		
		lblFiltrarPorMarca = new JLabel("Filtrar por Marca");
		lblFiltrarPorMarca.setBounds(408, 22, 132, 14);
		panel.add(lblFiltrarPorMarca);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(404, 40, 181, 19);
		panel.add(comboBox_1);
		
		JLabel lblOrdenarPorAo = new JLabel("Ordenar por Año");
		lblOrdenarPorAo.setBounds(245, 22, 132, 14);
		panel.add(lblOrdenarPorAo);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("Mas Reciente");
		tglbtnNewToggleButton.setBounds(243, 39, 134, 21);
		panel.add(tglbtnNewToggleButton);
		
		btnNewButton_3 = new JButton("Gastos");
		btnNewButton_3.setBounds(12, 600, 181, 51);
		panel.add(btnNewButton_3);
		
	}
	
	private void createPdf() throws IOException {
		final String dest = "C:\\Users\\simon\\Desktop\\escribesolonecesario.pdf";

		File file = new File(dest);
        file.getParentFile().mkdirs();
        
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
		Document document = new Document(pdf);

	}

	private void manipulatePdf() {
		final String dest = "C:\\Users\\simon\\Desktop\\pruebaITEXT.pdf";
		final String src  = "C:\\Users\\simon\\Desktop\\33-2022-09-09";

		File file = new File(dest);
        file.getParentFile().mkdirs();
        
		try {
		
			PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
			Document document = new Document(pdfDoc);

			Rectangle pageSize;
			PdfCanvas canvas;
			int n = pdfDoc.getNumberOfPages();
			for (int i = 1; i <= n; i++) {
			    PdfPage page = pdfDoc.getPage(i);
			    pageSize = page.getPageSize();
			    canvas = new PdfCanvas(page);
			    // add new content
			    Paragraph p = new Paragraph("CONFIDENTIAL").setFontSize(60);
			    canvas.saveState();
			    PdfExtGState gs1 = new PdfExtGState().setFillOpacity(0.2f);
			    canvas.setExtGState(gs1);
			    document.showTextAligned(p,
			            pageSize.getWidth() / 2, pageSize.getHeight() / 2,
			            pdfDoc.getPageNumber(page),TextAlignment.CENTER, VerticalAlignment.MIDDLE, 45);
			    canvas.restoreState();
			}
			
    	    pdfDoc.close();
    		
    	}catch(Exception e2) {
    		e2.printStackTrace();
    	}
	}	
}
