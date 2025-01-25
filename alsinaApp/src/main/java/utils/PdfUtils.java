package utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

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

import entities.Alquiler;

public class PdfUtils {
	
	/*public static void main(String args[]) {
		openPdfToPrint(new Alquiler());
	}*/

	public static void openPdfToPrint(Alquiler a) throws IOException, IllegalArgumentException {
		String user = System.getProperty("user.home");
		final String dest = user + "\\OneDrive\\.AA-Escritorio\\printwithpdf.pdf" ;
		final String src  = user + "\\OneDrive\\.AA-Escritorio\\Scan.pdf"; 
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

			/*    */
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

	public void createPdf() throws IOException {
		final String dest = "C:\\Users\\simon\\Desktop\\escribesolonecesario.pdf";

		File file = new File(dest);
        file.getParentFile().mkdirs();
        
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
		Document document = new Document(pdf);

		
		document.close();

	}

}
