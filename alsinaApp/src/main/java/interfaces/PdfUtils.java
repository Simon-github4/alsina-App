package interfaces;

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
		final String dest = "C:\\Users\\simon\\OneDrive\\.AA-Escritorio\\ScanCompleted.pdf";
		final String src  = "C:\\Users\\simon\\OneDrive\\.AA-Escritorio\\Scan.pdf"; //"C:\\Users\\simon\\Desktop\\33-2022-09-09";
		//final int width = 595;
		//final int height = 842;
		final int lineY = -15;
		final int startY = 745;
		final int x = 120;

		File file = new File(dest);
        file.getParentFile().mkdirs();
        
		
			PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
			Document document = new Document(pdfDoc);

			Rectangle pageSize;
			PdfCanvas canvas;
			int n = pdfDoc.getNumberOfPages();
			for (int i = 1; i <= n; i++) {
			    PdfPage page = pdfDoc.getPage(i);
			    pageSize = page.getPageSize();
			    canvas = new PdfCanvas(page);

			    Paragraph p = new Paragraph(a.getClient().getName()).setFontSize(11.5f);
			    canvas.saveState();
			    PdfExtGState gs1 = new PdfExtGState().setFillOpacity(1);
			    canvas.setExtGState(gs1);

			    document.showTextAligned(p, x, startY,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			    
			    p = new Paragraph("Usuario");
			    document.showTextAligned(p, x, startY + lineY * 2,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			    
			    p = new Paragraph(a.getVehicle().getPlate());
			    document.showTextAligned(p, x, startY + lineY * 4,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			    
			    p = new Paragraph(a.getVehicle().getModel());
			    document.showTextAligned(p, x + 120, startY + lineY * 4,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			    
			    p = new Paragraph(a.getEnd().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			    document.showTextAligned(p, x, startY + lineY * 7,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			    
			    p = new Paragraph(a.getStart().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			    document.showTextAligned(p, x, startY + lineY * 8,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			    
			    p = new Paragraph(String.valueOf(a.getReturnKm()));
			    document.showTextAligned(p, x + 180, startY + lineY * 7,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);

			    p = new Paragraph(String.valueOf(a.getDepartureKm()));
			    document.showTextAligned(p, x + 180, startY + lineY * 8,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);

			    p = new Paragraph("X");//comb retorno
			    document.showTextAligned(p, x + 256 + 18.2f* getXPositionGas(a.getGasReturn()), startY + lineY * 7,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);

			    p = new Paragraph("X");//comb salida
			    document.showTextAligned(p, x + 256 + 18.2f* getXPositionGas(a.getGasExit()), startY + lineY * 8,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);

			    p = new Paragraph(a.getClient().getAdress());
			    document.showTextAligned(p, x - 20, startY + lineY * 10,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			    
			    p = new Paragraph(a.getClient().getPhone()).setFontSize(9.5f);
			    document.showTextAligned(p, x + 140, startY + lineY * 10,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			  
			    p = new Paragraph("valor x dia");
			    document.showTextAligned(p, x + 225, startY + lineY * 10.3f,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);

			    p = new Paragraph(a.getClient().getLicense()).setFontSize(9);
			    document.showTextAligned(p, x - 30, startY + lineY * 11,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);

			    p = new Paragraph(a.getClient().getExpiration()).setFontSize(8.5f);
			    document.showTextAligned(p, x + 80, startY + lineY * 11,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);

			    p = new Paragraph("financ.").setFontSize(8.5f);
			    document.showTextAligned(p, x + 160, startY + lineY * 11,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
		
			    p = new Paragraph(a.getClient().getCard()).setFontSize(8.5f);
			    document.showTextAligned(p, x - 30, startY + lineY * 12,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			    
			    p = new Paragraph(a.getClient().getCardNumber()).setFontSize(8.5f);
			    document.showTextAligned(p, x + 20, startY + lineY * 12,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			    
			    p = new Paragraph(a.getClient().getCardExpiration()).setFontSize(8.5f);
			    document.showTextAligned(p, x + 95, startY + lineY * 12,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
	
			    p = new Paragraph(String.valueOf(a.getTotalPrice())).setFontSize(9.3f);
			    document.showTextAligned(p, x + 130, startY + lineY * 12,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
	
			    p = new Paragraph(a.getClient().getCod()).setFontSize(8.5f);
			    document.showTextAligned(p, x + 180, startY + lineY * 12,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			    
			    
			    p = new Paragraph(String.valueOf(a.getVehicle().getEnsuranceFranchise())).setFontSize(10); //quizas achicar Font
			    document.showTextAligned(p, x + 35, startY + lineY * 32.3f,
			            pdfDoc.getPageNumber(page),TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
			    
			    canvas.restoreState();
			}
			
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

		if(gas.equalsIgnoreCase("RESERVA"))
			return 1;
		else if(gas.equalsIgnoreCase("1/4"))
			return 2;
		else if(gas.equalsIgnoreCase("1/2"))
			return 4;
		else if(gas.equalsIgnoreCase("3/4"))
			return 6;
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
