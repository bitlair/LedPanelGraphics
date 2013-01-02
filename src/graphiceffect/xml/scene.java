package graphiceffect.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class scene
{
  @XmlElement
  private int duration;

  @XmlElement
  private effect[] effect;


  public void print()
  {
  }


  public effect[] getEffect()
  {
    return effect;
  }


  public int getDuration()
  {
    return duration;
  }
}
