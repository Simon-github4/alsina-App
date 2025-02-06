package utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import entities.Alquiler;
import entities.Compra;
import entities.Transaccion;

public class PdfUtils {
	
	/*public static void main(String args[]) {
		openPdfToPrint(new Alquiler());
	}*/

	private static final String[] monthName = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

	public static void openPdfToPrint(Alquiler a) throws IOException, IllegalArgumentException {
		String user = System.getProperty("user.home");
		final String dest = user + "\\Desktop\\printwithpdf.pdf" ;
		//final String src  = user + "\\OneDrive\\.AA-Escritorio\\Scan.pdf"; 
		//final int width = 595;
		//final int height = 842;
		final float lineY = -17.9f; //-15
		final int startY = 726;//745?
		final int x = 75; // 120?

		File file = new File(dest);
        file.getParentFile().mkdirs();
        
		
			PdfDocument pdfDoc = new PdfDocument(/*new PdfReader(src)*/ new PdfWriter(dest));
			Document document = new Document(pdfDoc);

			Rectangle pageSize;
			PdfCanvas canvas;
			int n = pdfDoc.getNumberOfPages();
			pdfDoc.addNewPage(1);
				
			    PdfPage page = pdfDoc.getPage(/*i*/1);
			    pageSize = page.getPageSize();
			    canvas = new PdfCanvas(page);

			    canvas.saveState();
			    PdfExtGState gs1 = new PdfExtGState().setFillOpacity(1);
			    canvas.setExtGState(gs1);

			    String name; 			    
				if(a.getClient().getName().contains("-"))
					name = (String)a.getClient().getName().subSequence(0, a.getClient().getName().indexOf('-'));
				else
					name = a.getClient().getName();

				Paragraph p = new Paragraph(name).setFontSize(9.5f);
				document.showTextAligned(p, x, startY,
						pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
				
				if(a.getClient().getName().contains("-")) {
				    p = new Paragraph(a.getClient().getName().substring(a.getClient().getName().indexOf('-') + 1)).setFontSize(9);
				    document.showTextAligned(p, x, startY + lineY * 2.5f,
				            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
				}
			    
			    p = new Paragraph(a.getVehicle().getPlate()).setFontSize(9);
			    document.showTextAligned(p, x, startY + lineY * 4.45f,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			    
			    p = new Paragraph(a.getVehicle().getModel()).setFontSize(9);
			    document.showTextAligned(p, x + 145, startY + lineY * 4.45f,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			    
			    p = new Paragraph(a.getEnd().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).setFontSize(9);
			    document.showTextAligned(p, x, startY + lineY * 8,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			    
			    p = new Paragraph(a.getStart().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).setFontSize(9);
			    document.showTextAligned(p, x, startY + lineY * 9,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			    
			    p = new Paragraph(String.valueOf(a.getReturnKm())).setFontSize(9);
			    document.showTextAligned(p, x + 230, startY + lineY * 8,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);

			    p = new Paragraph(String.valueOf(a.getDepartureKm())).setFontSize(9);
			    document.showTextAligned(p, x + 230, startY + lineY * 9,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);

			    p = new Paragraph("X");//comb retorno
			    document.showTextAligned(p, x + 285 + 29* getXPositionGas(a.getGasReturn()), startY + lineY * 8,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);

			    p = new Paragraph("X");//comb salida
			    document.showTextAligned(p, x + 285 + 29* getXPositionGas(a.getGasExit()), startY + lineY * 9,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);

			    p = new Paragraph(a.getClient().getAdress()).setFontSize(9);
			    document.showTextAligned(p, x - 20, startY + lineY * 11,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			    
			    p = new Paragraph(a.getClient().getPhone()).setFontSize(8);
			    document.showTextAligned(p, x + 165, startY + lineY * 11,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			  
			    p = new Paragraph(String.valueOf(a.getTotalPrice() / a.getDurationDays())).setFontSize(9);
			    document.showTextAligned(p, x + 270, startY + lineY * 11.8f,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);

			    p = new Paragraph(a.getClient().getLicense()).setFontSize(8);
			    document.showTextAligned(p, x - 30, startY + lineY * 12,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);

			    p = new Paragraph(a.getClient().getExpiration()).setFontSize(8);
			    document.showTextAligned(p, x + 80, startY + lineY * 12,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);

			    /*p = new Paragraph("financ.").setFontSize(8);
			    document.showTextAligned(p, x + 160, startY + lineY * 12,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			     */
			    p = new Paragraph(a.getClient().getCard()).setFontSize(8);
			    document.showTextAligned(p, x - 50, startY + lineY * 13.3f,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			    
			    p = new Paragraph(a.getClient().getCardNumber()).setFontSize(8);
			    document.showTextAligned(p, x + 20, startY + lineY * 13.3f,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			    
			    p = new Paragraph(a.getClient().getCardExpiration()).setFontSize(8);
			    document.showTextAligned(p, x + 95, startY + lineY * 13.3f,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
	
			    p = new Paragraph(String.valueOf(a.getTotalPrice())).setFontSize(9);
			    document.showTextAligned(p, x + 145, startY + lineY * 13.3f,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
	
			    p = new Paragraph(a.getClient().getCod()).setFontSize(8);
			    document.showTextAligned(p, x + 215, startY + lineY * 13.3f,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			    
			    
			    p = new Paragraph(String.valueOf(a.getVehicle().getEnsuranceFranchise())).setFontSize(9); //quizas achicar Font
			    document.showTextAligned(p, x + 35, startY + lineY * 35,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			    
			    canvas.restoreState();

			Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(file);
                //desktop.browse(file.toURI());
            } catch (IOException e3) {
                e3.printStackTrace();
            }
			 
    	    pdfDoc.close();
    		document.close();

	}

	private static float getXPositionGas(String gas) {

		//if(gas.equalsIgnoreCase("RESERVA"))
		//	return 1;
		if(gas.equalsIgnoreCase("1/8"))
			return 2;
		else if(gas.equalsIgnoreCase("1/4"))
			return 3;
		else if(gas.equalsIgnoreCase("3/8"))
			return 4;
		else if(gas.equalsIgnoreCase("1/2"))
			return 5;
		else if(gas.equalsIgnoreCase("5/8"))
			return 6;
		else if(gas.equalsIgnoreCase("7/8"))
			return 7;
		else //if(gas.equalsIgnoreCase("FULL"))
			return 7;

	}

	public static void createTransactionPdf(Transaccion t) throws IOException {
		String amountInWords = JOptionPane.showInputDialog("Ingrese el Monto: "+ String.valueOf(t.getAmount())+ " en palabras");
		String pagaderos = JOptionPane.showInputDialog("Ingrese la forma de Cobro:");  pagaderos += ".";
		
		ImageData data = ImageDataFactory.create(PdfUtils.class.getResource(t.getVehicle().getBranch().getLogoPath()));
		Image img = new Image(data);
        img.setHeight(150).setWidth(275);
        
		PdfFont font = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA);
		PdfFont fontBold = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD);

		String user = System.getProperty("user.home");
		final String path = user + "\\Desktop\\Boletos Compra - Venta\\";
		final String fileName = t.getClass().getSimpleName() +"_"+ t.getVehicle().getPlate()+"_"
							  + t.getClient().getName()+"_"+ t.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) +".pdf";
									// \\Desktop
		File file = new File(path + fileName);
        file.getParentFile().mkdirs();
        
        PdfDocument pdf = new PdfDocument(new PdfWriter(path + fileName));
		Document document = new Document(pdf);

		Paragraph text = new Paragraph(" ").add(" Boleto de Compra - Venta")
				.setFontSize(19).setFont(fontBold);

        Table table = new Table(2);
        table.addCell(new Cell().add(img).setBorder(null));  // Image in first column
        table.addCell(new Cell().add(text).setBorder(null).setVerticalAlignment(VerticalAlignment.MIDDLE).setHorizontalAlignment(HorizontalAlignment.RIGHT));

        document.add(table);
		document.setMargins(40,55,55,40);
		
		String voc;
		if(t instanceof Compra) {
			voc = "Compra ";
		}else {
			voc = "Vende ";			
		}
		StringBuilder sb = new StringBuilder("\nConste por el presente que el señor : ");
		sb.append(t.getVehicle().getBranch().getDescription()).append(" domiciliado en: ").append(t.getVehicle().getBranch().getAdress());
		sb.append(" de Necochea "+voc+"al señor: ").append(t.getClient().getName()).append(" D.N.I: ").append(t.getClient().getDni());
		sb.append(" domiciliado en: ").append(t.getClient().getAdress()).append(" de Necochea, Telefono: ");
		sb.append(t.getClient().getPhone()).append(" lo siguiente: un automotor Marca: ").append(t.getVehicle().getBrand())
		.append(" Modelo: ").append(t.getVehicle().getModel()).append(" Dominio: ").append(t.getVehicle().getPlate())
		.append(" en el estado que se encuentra, por el cual presta conformidad. El precio de venta pactado es $ ")
		.append(t.getAmount()).append(".-(").append(amountInWords).append(") pagaderos de la siguiente forma: "+ pagaderos)
		.append("\n\nEl vendedor declara expresamente que el automotor motivo del presente boleto no reconoce gravamenes de ninguna indole")
		.append(" por prenda, embargo o deposito, responsabilizando, por cualquier inconveniente que impidiera disponer libremente del mismo.")
		.append("\n\nEn prueba de conformidad se firman dos ejemplares del mismo tenor y a un solo efecto en Necochea a los ").append(t.getDate().getDayOfMonth())
		.append(" dias del mes ").append(monthName[t.getDate().getMonthValue() - 1]).append(" de "+t.getDate().getYear())
		.append(".\n\n\n\n\n"); 
		
				
		Paragraph p = new Paragraph(sb.toString()).add(new Tab()).add("Firma comprador").add(new Tab()).add(new Tab()).add(new Tab()).add("Firma vendedor");
		p.setFontSize(13.5f).setFixedLeading(25).setFont(font);

		document.add(p);
		
		Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(file);
        } catch (IOException e3) {
            e3.printStackTrace();
        }
		pdf.close();
		document.close();

	}

}
