/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checking_doc;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

/**
 *
 * @author Daniel
 */
public class info {
    public String wave_to_doc;
    private static String HighKolontit;
    private static String LowKolontit;
    
    private static void getKolontitules(String wave) {
        try{
            FileInputStream fileInputStream = new FileInputStream(wave);
            // открываем файл и считываем его содержимое в объект XWPFDocument
            XWPFDocument docxFile = new XWPFDocument(OPCPackage.open(fileInputStream));
            XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(docxFile);
 
            // считываем верхний колонтитул (херед документа)
            XWPFHeader docHeader = headerFooterPolicy.getDefaultHeader();
            HighKolontit = docHeader.getText();
            XWPFFooter docFooter = headerFooterPolicy.getDefaultFooter();
            LowKolontit = docFooter.getText();
            fileInputStream.close();
        }
        catch(Exception e){
            
        }
    }
    public void messages(){
        getKolontitules(wave_to_doc);
        boolean error = true;
        if(HighKolontit==null){
            JOptionPane.showMessageDialog(null,"<html><h2>Внимание!!!</h2><i>Верхний колонтитул пуст </i>");
            error = false;
        }
        if(LowKolontit==null){
            JOptionPane.showMessageDialog(null,"<html><h2>Внимание!!!</h2><i>Нижний колонтитул пуст </i>");
            error = false;
        }
        try{
            FileInputStream fileInputStream = new FileInputStream(wave_to_doc);
            XWPFDocument docxFile = new XWPFDocument(OPCPackage.open(fileInputStream));
            List<XWPFParagraph> paragraphs = docxFile.getParagraphs();
            String UDK;
            UDK = paragraphs.get(0).getText();
            if(UDK.indexOf("УДК")==-1){
                JOptionPane.showMessageDialog(null,"<html><h2>Внимание!!!</h2><i>Отсутствует строка УДК </i>");
                error = false;
            }
            
            if(UDK.equals("УДК")||UDK.equals("УДК ")){
                JOptionPane.showMessageDialog(null,"<html><h2>Внимание!!!</h2><i>Отсутствует номер УДК </i>");
                error = false;
            }

            
            
            
            if (error){
                JOptionPane.showMessageDialog(null,"<html><h2>Удивительно!!!</h2><i>Похоже, будто все правильно, <br>но все-таки проверь</i>");
            }
            if (!error){
                JOptionPane.showMessageDialog(null,"<html><h2>Ну ты и ламер...</h2><i>Даже файл worda создать не можешь</i>");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
