package graphiceffect.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class graphiceffect
{
  //@XmlElement(required=false)
  private boolean nogui = false;

  @XmlElement
  private int width;

  @XmlElement
  private int height;

  @XmlElement
  private int pixelSize;

  //@XmlElement(required = false)
  private String hostname = null;

  //@XmlElement(required = false)
  private int port;

  @XmlElement
  private int fpsTarget;

  @XmlElement
  private scene[] scene;


  public void print()
  {
  }


  public scene[] getScene()
  {
    return scene;
  }


  public boolean isNogui()
  {
    return nogui;
  }


  public int getWidth()
  {
    return width;
  }


  public int getHeight()
  {
    return height;
  }


  public int getPixelSize()
  {
    return pixelSize;
  }


  public String getHostname()
  {
    return hostname;
  }


  public int getPort()
  {
    return port;
  }


  public int getFpsTarget()
  {
    return fpsTarget;
  }
}
