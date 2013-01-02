package graphiceffect.xml;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import utils.Utils;

public class Xml
{
  public static void write(String fileName, Class<?> c, Object o)
  {
    try
    {
      JAXBContext contextObj = JAXBContext.newInstance(c);
      Marshaller marshallerObj = contextObj.createMarshaller();
      marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      marshallerObj.marshal(o, new FileOutputStream(fileName));
    }
    catch (Exception e)
    {
      System.out.println("ERROR: " + e.getMessage());
      e.printStackTrace();
    }
  }


  public static Object read(String fileName, Class<?> c) throws Exception
  {
    URL url = Utils.getUrl(fileName);
    return read(url.openStream(), c);
  }


  public static Object read(InputStream file, Class<?> c)
  {
    Object o = null;
    try
    {
      JAXBContext contextObj = JAXBContext.newInstance(c);
      Unmarshaller unmarshaller = contextObj.createUnmarshaller();
      o = unmarshaller.unmarshal(file);
    }
    catch (Throwable e)
    {
      System.out.println("ERROR: " + e.getMessage());
      e.printStackTrace();
    }
    return o;
  }
}
