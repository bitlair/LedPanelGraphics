package graphiceffect.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class palette
{
  @XmlElement
  private String className;

  @XmlElement
  private param[] param;


  public void print()
  {
  }


  public String getClassName()
  {
    return className;
  }


  public param[] getParam()
  {
    return param;
  }
}
