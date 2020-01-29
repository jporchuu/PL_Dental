package Bill;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.FileOutputStream;
import java.io.IOException;

public class BillController extends BillIntroController{

    @FXML
    private AnchorPane billAnchor;

    @FXML
    private Text givenName;

    @FXML
    private Text serviceRendered;

    @FXML
    private Text dateOfTreatment;

    @FXML
    private Text timeOfTreatment;

    @FXML
    private Text totalPriceString;

    @FXML
    private Text timeAndDate;


    public void initialize(){
        givenName.setText(searchedName);
        serviceRendered.setText(searchedService);
        dateOfTreatment.setText(searchedDate);
        timeOfTreatment.setText(searchedTime);
        timeAndDate.setText(currentTime);

        if(totalPrice <= 0){
            totalPriceString.setText("FREE");
        }
        else{
            totalPriceString.setText(String.valueOf(totalPrice));
        }

    }

    public void savePDF(ActionEvent event) throws IOException, DocumentException {

        Document document = new Document(PageSize.LETTER);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Receipts/Receipt#" + receiptsGenerated +  ".pdf"));

        document.open();

        PdfPTable logoAndName = new PdfPTable(2);
        logoAndName.setWidthPercentage(100);
        logoAndName.setWidths(new int[]{1, 6});

        Image img = Image.getInstance("src/assets/Portu-Len_LogoSmall.png");

        Font title = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
        Font fontBase = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
        Font fontGrand = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);

        Chunk progName = new Chunk("Dental Health Status, Schedule, and Service Report Generator", title);
        Chunk chunkName = new Chunk("Patient Name:\t\t\t" + searchedName, fontBase);
        Chunk chunkService = new Chunk("Service Rendered:\t\t\t" + searchedService, fontBase);
        Chunk chunkDate = new Chunk("Date of Treatment:\t\t\t" + searchedDate, fontBase);
        Chunk chunkTime = new Chunk("Time of Treatment:\t\t\t" + searchedTime, fontBase);
        Chunk chunkBillTime = new Chunk("Time of Billing:\t\t\t" + currentTime, fontBase);
        Chunk chunkPrice = new Chunk("Price:\t\t\t" + priceTreatment, fontBase);
        Chunk chunkQuantity = new Chunk("Quantity:\t\t\t" + quantityTreatment, fontBase);
        Chunk chunkTotal = new Chunk("Grand Total:\t\t\t" + totalPrice, fontGrand);
        Chunk spacing = new Chunk("\n");

        Phrase phrase = new Phrase();
        phrase.add("\n\n\n");
        phrase.add(chunkName +"\n\n");
        phrase.add(chunkService+"\n\n");
        phrase.add(chunkDate+"\n\n");
        phrase.add(chunkTime+"\n\n");
        phrase.add(chunkBillTime+"\n\n");
        phrase.add(chunkPrice+"\n\n");
        phrase.add(chunkQuantity);
        phrase.add("\n\n==========================\n\n");
        phrase.add(chunkTotal);

        logoAndName.addCell(createImageCell(img));
        logoAndName.addCell(createTextCell(progName));

        document.add(spacing);
        document.add(logoAndName);
        document.add(spacing);
        document.add(phrase);

        document.close();

        receiptsGenerated = receiptsGenerated + 1;

        addReceiptCount();

    }


    public static PdfPCell createImageCell(Image img) throws DocumentException, IOException {
        PdfPCell cell = new PdfPCell(img, false);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    public static PdfPCell createTextCell(Chunk text) throws DocumentException, IOException {
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(text);
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    public void back(ActionEvent event) throws IOException {
        AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("../Menu/MenuUI.fxml"));
        billAnchor.getChildren().setAll(nextAnchorPane);
    }

}
