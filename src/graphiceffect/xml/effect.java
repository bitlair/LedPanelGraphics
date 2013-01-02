package graphiceffect.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class effect
{
  @XmlElement
  private String className;

  private int alpha = 255;

  @XmlElement
  private palette palette;

  @XmlElement
  private param[] param;


  public void print()
  {
  }


  public String getClassName()
  {
    return className;
  }


  public int getAlpha()
  {
    return alpha;
  }


  public palette getPalette()
  {
    return palette;
  }


  public param[] getParam()
  {
    return param;
  }
}
