

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.net.URL;
import java.net.URLConnection;

public class ReadXMLFile {

  public static void main(String argv[]) {

    try {

        String[]  urls = {"http://ohyeahczarina.tumblr.com"};
        
        
        for (String _url:urls){
            System.out.println("[");
            for(int k = 1; k <= 20; k++){
                URL url = (k==1)?new URL(_url+"/rss"):new URL(_url+"/page/"+k+"/rss");
                
                URLConnection conn = url.openConnection();

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(conn.getInputStream());

                //optional, but recommended
                //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName("item");

                //System.out.println("----------------------------");
                
                for (int temp = 0; temp < nList.getLength(); temp++) {

                        Node nNode = nList.item(temp);

                        //System.out.println("\nCurrent Element :" + nNode.getNodeName());
                        System.out.println("{");
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                                Element eElement = (Element) nNode;
                                String title = eElement.getAttribute("title");
                                System.out.println("Title: \"" + title + "\",");
                                String test = eElement.getElementsByTagName("description").item(0).getTextContent();
                                String retTest = "";
                                boolean hasImage = false;
                                if (test.contains("img")){
                                    hasImage = true;
                                }
                                boolean flag = false;
                                for (int i = 0; i < test.length(); i++) {
                                    if (test.charAt(i) == '<'){
                                        flag = true;
                                    }
                                    else if (test.charAt(i) == '>'){
                                        flag = false;
                                    }
                                    else if (flag == false){
                                        retTest += test.charAt(i);
                                    }
                                    else{
                                        //do nothing
                                    }
                                }
                                
                                System.out.println("Description : \"" + retTest + "\",");
                                System.out.println("Text Length : " + title.length() + retTest.length());
                                System.out.println("Link : \"" + eElement.getElementsByTagName("link").item(0).getTextContent() + "\",");
                                String[] date = eElement.getElementsByTagName("pubDate").item(0).getTextContent().split(" ");
                                System.out.println("Date : \"" + date[1] + "\"");
                                System.out.println("Time : \"" + date[2] + "\"");
                                System.out.print("Category : \"");
                                for(int ctr = 0; ctr < eElement.getElementsByTagName("category").getLength(); ctr++){
                                    System.out.print(eElement.getElementsByTagName("category").item(ctr).getTextContent() + (ctr<eElement.getElementsByTagName("category").getLength()-1?",":""));
                                }
                                System.out.println("\",");
                                System.out.println("Has_image : \"" + hasImage + "\"");

                        }
                        System.out.println("},");
                }
            }
            System.out.println("]");
        }
    } catch (Exception e) {
	e.printStackTrace();
    }
  }
  
  

}