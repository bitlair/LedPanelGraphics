package graphiceffect.effect;

import graphiceffect.main.Effect;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;
import graphiceffect.xml.param;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import utils.Utils;


public class Image2 extends Effect
{
  BufferedImage bi;


  public void drawImage(Image image)
  {
    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < height; y++)
      {
        int c = bi.getRGB(x, y) & 0xffffff;
        // System.out.printf("%06x ", c);
        int r = (c >> 16) & 0xff;
        int g = (c >> 8) & 0xff;
        int b = (c >> 0) & 0xff;
        if (r < 10 & g < 10 & b < 10)
        {
          image.setPixel(x, y, palette.getColor(0));
        }
      }
      // System.out.println();
    }
    // System.out.println();
  }


  @Override
  protected void init()
  {
  }


  protected void setXml(effect xml) throws Exception
  {
    String fileName = "smiley1.gif";

    for (param p : xml.getParam())
    {
      String name = p.getName();
      String value = p.getValue();

      if ((name == null) || (name.length() == 0))
      {
        continue;
      }

      if (name.equals("image"))
      {
        fileName = value;
      }
    }

    bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    BufferedImage b = ImageIO.read(Utils.getUrl(fileName));
    Graphics2D g = (Graphics2D) bi.getGraphics();
    g.scale(1, -1);
    g.translate(0, -height);
    g.drawImage(b, 0, 0, null);
  }
}
