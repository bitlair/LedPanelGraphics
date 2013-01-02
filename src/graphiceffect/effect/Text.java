package graphiceffect.effect;

import graphiceffect.font.FontArial10;
import graphiceffect.main.Effect;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import utils.Utils;

public class Text extends Effect
{
  BufferedImage bi;

  String s = "Bitlair: Zarya Rogue Miep HobbyBob AK47 8. MishMash";

  int sw = 0;

  int sh = 0;

  int dir = 1;

  Graphics2D g = null;

  int shift = 0;

  int delay = 0;

  Font font = loadFont();


  @Override
  public void drawImage(Image image)
  {
    if (g == null)
    {
      g = (Graphics2D) bi.getGraphics();
      g.setFont(font);
      Rectangle2D r = g.getFontMetrics().getStringBounds(s, g);
      sw = (int) r.getWidth() + 1;
      sh = (int) r.getHeight() + 1;
    }

    g.setColor(Color.white);
    g.fillRect(0, 0, width, height);
    g.setColor(Color.black);
    g.drawString(s, -shift, 12);

    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < height; y++)
      {
        if (bi.getRGB(x, y) == -1)
        {
          // image[x][y] = new Color(bi.getRGB(x, y));
        }
        else
        {
          // image[x][y] = Utils.invertColor(image[x][y]);
          image.setPixel(x, y, palette.getColor(y));
        }
      }
    }

    if (delay >= 1)
    {
      shift += dir;
      if ((shift + width >= sw) || (shift == 0))
      {
        dir = -dir;
      }
      delay = 0;
    }
    delay++;
  }


  public void drawImage2(Color[][] image) throws Exception
  {
    int id = 21;
    int ii = 0;

    graphiceffect.main.Font font = new FontArial10();

    for (int x = 0; x < width; x++)
    {
      // if ((x - ii) >= letters[id].length)
      // {
      // id = (id + 1) % letters.length;
      // ii = x;
      // }
      // int l = letters[id][x - ii];
      // for (int y = 0; y < 8; y++)
      // {
      // int b = l & (0x1 << y);
      // if (b != 0)
      // {
      // image[x][y] = Color.black;
      // }
      // }
      if ((x - ii) >= font.length())
      {
        id = (id + 1) % font.length();
        ii = x;
      }
      int l = font.getLetter(id)[x - ii];
      for (int y = 0; y < 16; y++)
      {
        int b = l & (0x1 << y);
        if (b != 0)
        {
          image[x][y] = Color.black;
        }
      }
    }
  }


  private Font loadFont()
  {
    int size = 11;
    Font font = new Font("Arial", Font.PLAIN, size);

    try
    {
      // InputStream is = getClass().getResourceAsStream("00TT.TTF");

      // Font font = new Font("Arial", Font.BOLD, size);
      // font = new Font("Arial", Font.PLAIN, size);

      // Only correct size for 00tt.ttf: 11
      size = 11;
      font =
        Font.createFont(Font.TRUETYPE_FONT, Utils.getUrl("00tt.ttf")
          .openStream());
    }
    catch (Exception e)
    {
      System.out.println("ERROR: " + e.getMessage());
      e.printStackTrace();
    }
    font = font.deriveFont((float) size);
    return font;
  }


  @Override
  protected void init()
  {
  }


  protected void setXml(effect effect)
  {
    bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
  }
}
