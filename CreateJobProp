package org.DandelionSeed.javaEx;

import java.io.StringReader;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class CreateJobProp
{
	
	public static void main(String[] args)
	{
		
		try {
			String jobName = "NewJobFromWeb"; // I/F ID
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			/*
			 * Creating Property ID (5종류 필요: "_length21 String" = "_" + jobname base String(5) + 구분자 + time base String(16)) 
			 * */
			StringBuilder sb = new StringBuilder("_");
			Encoder encoder = Base64.getEncoder();
			
			byte[] encodedJobName = encoder.encode(jobName.getBytes());
			if(encodedJobName.length < 5) {
				StringBuilder keyFiller = new StringBuilder(new String(encodedJobName));
				while(keyFiller.length() < 5) {
					keyFiller.append("0");
				}//while() end
				encodedJobName = keyFiller.toString().getBytes();
			}//if() end
			String strEncodedJobName = new String(encodedJobName).substring(0, 5);
			sb.append(strEncodedJobName);
			sb.append("-");
			
			Date date = new Date();
			String strLongTime = Long.toString(date.getTime()).substring(2);
			byte[] byteKey = strLongTime.getBytes();
			byte[] encodedTimeKey = encoder.encode(byteKey);
			sb.append(new String(encodedTimeKey));
			
			sb.setCharAt(6, 'a');
			String adtnPropxmiID = sb.toString();
			sb.setCharAt(6, 'P');
			String propID = sb.toString();
			sb.setCharAt(6, 'T');
			String talendPropID = sb.toString();
			sb.setCharAt(6, 'i');
			String itemID = sb.toString();
			sb.setCharAt(6, 's');
			String stateID = sb.toString();
			
			String base = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
					"<xmi:XMI xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:TalendProperties=\"http://www.talend.org/properties\">\r\n" + 
					"  <TalendProperties:Property xmi:id=\"_0Lvh8YeLEeuqROxs4W2u4w\" id=\"_0Lvh8IeLEeuqROxs4W2u4w\" label=\"ExternalJobControl\" version=\"0.1\" statusCode=\"\" item=\"_0Lvh84eLEeuqROxs4W2u4w\" displayName=\"ExternalJobControl\">\r\n" + 
					"    <author href=\"../talend.project#_3To4UYbNEeuGNvHHRUn2FQ\"/>\r\n" + 
					"    <additionalProperties xmi:id=\"_0L2PoIeLEeuqROxs4W2u4w\" key=\"created_product_fullname\" value=\"Talend Open Studio for Big Data\"/>\r\n" + 
					"    <additionalProperties xmi:id=\"_0L22sIeLEeuqROxs4W2u4w\" key=\"created_product_version\" value=\"7.1.1.20181026_1147\"/>\r\n" + 
					"    <additionalProperties xmi:id=\"_0L22sYeLEeuqROxs4W2u4w\" key=\"created_date\" value=\"2021-03-18T10:46:53.722+0900\"/>\r\n" + 
					"  </TalendProperties:Property>\r\n" + 
					"  <TalendProperties:ItemState xmi:id=\"_0Lvh8oeLEeuqROxs4W2u4w\" path=\"\"/>\r\n" + 
					"  <TalendProperties:ProcessItem xmi:id=\"_0Lvh84eLEeuqROxs4W2u4w\" property=\"_0Lvh8YeLEeuqROxs4W2u4w\" state=\"_0Lvh8oeLEeuqROxs4W2u4w\">\r\n" + 
					"    <process href=\"ExternalJobControl_0.1.item#/\"/>\r\n" + 
					"  </TalendProperties:ProcessItem>\r\n" + 
					"</xmi:XMI>";
			Document doc = builder.parse(new InputSource(new StringReader(base)));
			
			doc.getFirstChild().getFirstChild().getNextSibling().getAttributes().getNamedItem("xmi:id").setNodeValue(propID);
			doc.getFirstChild().getFirstChild().getNextSibling().getAttributes().getNamedItem("id").setNodeValue(talendPropID);
			doc.getFirstChild().getFirstChild().getNextSibling().getAttributes().getNamedItem("item").setNodeValue(itemID);
			doc.getFirstChild().getFirstChild().getNextSibling().getChildNodes().item(1)
				.getNextSibling().getNextSibling().getAttributes().getNamedItem("xmi:id").setNodeValue(adtnPropxmiID);
			
			TransformerFactory tfFactory = TransformerFactory.newInstance();
			Transformer tf = tfFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
//			StreamResult result = new StreamResult(System.out);
//			tf.transform(source, result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}//try~catch() end
		
	}//main() end
	
}//CreateJobProp end
