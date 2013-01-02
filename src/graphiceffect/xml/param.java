package graphiceffect.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class param
{
  @XmlAttribute
  private String name;

  @XmlAttribute
  private String value;


  public void print()
  {
    System.out.printf("param: name=%s, value=%s", name, value);
    System.out.println();
  }


  public String getName()
  {
    return name;
  }


  public String getValue()
  {
    return value;
  }
}
