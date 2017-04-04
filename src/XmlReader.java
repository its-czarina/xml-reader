import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlReader {
    public static void main (String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        
        String[]  urls = {"http://cruzfucker69.tumblr.com"};
        
        
        for (String _url:urls){
            for(int k = 1; k <= 5; k++){
                URL url = (k==1)?new URL(_url+"/rss"):new URL(_url+"/page/"+k+"/rss");
                
                URLConnection conn = url.openConnection();

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(conn.getInputStream());

                List<Post> empList = new ArrayList<>();
                XmlParser xml = new XmlParser();
                Post emp = new Post();
                //Iterating through the nodes and extracting the data.
                
                
                NodeList nodeList = document.getDocumentElement().getChildNodes();
                
                for (int i = 0; i < nodeList.getLength(); i++) {

                  //We have encountered an <employee> tag.
                  Node node = nodeList.item(i);
                  if (node instanceof Element) {
                    
                    emp.id = node.getAttributes().
                        getNamedItem("id").getNodeValue();

                    NodeList childNodes = node.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                      Node cNode = childNodes.item(j);

                      //Identifying the child tag of employee encountered. 
                      if (cNode instanceof Element) {
                        String content = cNode.getLastChild().
                            getTextContent().trim();
                        switch (cNode.getNodeName()) {
                          case "firstName":
                            emp.firstName = content;
                            break;
                          case "lastName":
                            emp.lastName = content;
                            break;
                          case "location":
                            emp.location = content;
                            break;
                        }
                      }
                    }
                    empList.add(emp);
                  }

                }

                //Printing the Employee list populated.
                for (Post em : empList) {
                  System.out.println(em);
                }

                
            }
        }
        
  }

    
}

//class Post{
//      String id;
//      String firstName; 
//      String lastName;
//      String location;
//
//      @Override
//      public String toString() {
//        return firstName+" "+lastName+"("+id+")"+location;
//      }
//
//}