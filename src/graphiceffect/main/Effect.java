package graphiceffect.main;

import graphiceffect.xml.effect;


public abstract class Effect
{
  protected int width;

  protected int height;

  protected Palette palette;

  protected boolean running = false;

  protected Scene scene;


  // Note: Must have a default constructor (and no others).
  public Effect()
  {
  }


  abstract public void drawImage(Image image);


  public static Effect getEffect(effect xml, int width, int height,
    Scene scene) throws Exception
  {
    String name = xml.getClassName();
    Effect e =
      (Effect) ClassLoader.getSystemClassLoader().loadClass(name)
        .newInstance();
    e.width = width;
    e.height = height;
    e.scene = scene;
    e.init();
    if (xml.getPalette() != null)
    {
      e.palette = Palette.getPalette(xml.getPalette());
    }
    if ((xml.getParam() != null) && (xml.getParam().length > 0))
    {
      e.setXml(xml);
    }
    return e;
  }


  abstract protected void init() throws Exception;


  public void running(boolean running)
  {
    this.running = running;
  }


  /**
   * This method is intended to do the initialization of the subclass. Before 
   * it is called the width, height and palette have been set.
   * 
   * @param xml  the xml data for this effect
   * @throws Exception
   */
  abstract protected void setXml(effect xml) throws Exception;


  public int getWidth()
  {
    return width;
  }


  public int getHeight()
  {
    return height;
  }
}
