package graphiceffect.effect;

import graphiceffect.main.Effect;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;
import graphiceffect.xml.param;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import utils.Utils;

public class Gif extends Effect
{
  ArrayList<Image> list = new ArrayList<Image>();

  int count = 0;

  String fileName = "snoopy_dancing.gif";

  int cmin = 127;

  int shift = 0;

  int offset = 0;


  @Override
  public void drawImage(Image image)
  {
    Image i = list.get(count++);
    count %= list.size();
    image.paint(i, offset);
    offset += shift;
  }


  @Override
  protected void init()
  {
  }


  @Override
  protected void setXml(effect xml) throws Exception
  {
    for (param p : xml.getParam())
    {
      String name = p.getName();
      String value = p.getValue();

      if ((name == null) || (name.length() == 0))
      {
        continue;
      }

      if (name.equals("file"))
      {
        fileName = value;
      }
      else if (name.equals("cmin"))
      {
        cmin = Integer.parseInt(value);
      }
      else if (name.equals("shift"))
      {
        shift = Integer.parseInt(value);
      }
    }
    load();
  }


  public void load() throws Exception
  {
    ImageInputStream stream =
      ImageIO.createImageInputStream(Utils.getUrl(fileName).openStream());
    Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
    if (!readers.hasNext())
      throw new RuntimeException("no image reader found");
    ImageReader reader = (ImageReader) readers.next();
    reader.setInput(stream); // don't omit this line!
    int n = reader.getNumImages(true); // don't use false!
    System.out.println("numImages = " + n);
    for (int i = 0; i < n; i++)
    {
      BufferedImage bi = reader.read(i);
      Image image = new Image(width, height);
      image.cmin = cmin;
      image.paint(bi);
      list.add(image);
    }
    stream.close();
  }
}
