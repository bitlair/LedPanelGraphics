package graphiceffect.effect;

import graphiceffect.font.DosHercules;
import graphiceffect.main.Effect;
import graphiceffect.main.Font;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import utils.Utils;

public class PowerMeter extends Effect
{
  URI url;

  double power = -1.0;

  long time = 0;

  BufferedImage bi;

  Image im1;

  Image im2;

  Font font;


  @Override
  public void drawImage(Image image)
  {
    // if (power >= 0)
    {
      image.paint(im1);
    }
  }


  @SuppressWarnings("unused")
  void drawMeter(Image image)
  {
    Graphics2D g = (Graphics2D) bi.getGraphics();
    g.scale(1, -1);
    g.translate(0, -height + 1);
    g.setColor(Color.white);
    g.fillRect(0, 0, width, height);

    g.translate(-13, 0);

    if (false)
    {
      if (power < 750)
      {
        g.setColor(Color.green);
      }
      else if (power < 1500)
      {
        g.setColor(Color.yellow);
      }
      else
      {
        g.setColor(Color.red);
      }
      g.fillArc(0, 0, 2 * height, 2 * height, 45, 90);
      g.setColor(Color.yellow);
      g.drawArc(0, 0, 2 * height, 2 * height, 45, 90);
    }
    else
    {
      g.setColor(Color.green);
      g.fillArc(0, 0, 2 * height, 2 * height, 85, 50);
      g.setColor(Color.yellow);
      g.fillArc(0, 0, 2 * height, 2 * height, 65, 20);
      g.setColor(Color.red);
      g.fillArc(0, 0, 2 * height, 2 * height, 45, 20);
      g.setColor(Color.black);
      g.fillArc(height / 2, height / 2, height, height, 45, 90);

      g.setColor(Color.yellow);
      g.drawArc(0, 0, 2 * height, 2 * height, 45, 90);
    }


    double angle;
    int x1;
    int y1;
    int x2;
    int y2;

    // Left side
    angle = 0.25 * Math.PI;
    x1 = height;
    y1 = height;
    x2 = (int) (height - height * Math.cos(angle));
    y2 = height - (int) (height * Math.sin(angle));
    g.drawLine(x1, y1, x2, y2);

    // Right side
    angle = 0.75 * Math.PI;
    x1 = height;
    y1 = height;
    x2 = (int) (height - height * Math.cos(angle));
    y2 = height - (int) (height * Math.sin(angle));
    g.drawLine(x1, y1, x2, y2);

    // Needle (large, black)
    angle = (0.25 + (power / 4000) * 0.5) * Math.PI;
    x1 = height;
    y1 = height;
    x2 = (int) (height - height * Math.cos(angle));
    y2 = height - (int) (height * Math.sin(angle));
    g.setColor(Color.black);
    g.setStroke(new BasicStroke(1));
    g.drawLine(x1, y1, x2, y2);

    // Needle (small, red)
    angle = (0.25 + (power / 4000) * 0.5) * Math.PI;
    x1 = height;
    y1 = height;
    x2 = (int) (height - 0.5 * height * Math.cos(angle));
    y2 = height - (int) (0.5 * height * Math.sin(angle));
    g.setColor(Color.red);
    g.setStroke(new BasicStroke(1));
    g.drawLine(x1, y1, x2, y2);

    image.fill(Color.black);
    
    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < height; y++)
      {
        int p = bi.getRGB(x, y);
        Color c;
        if (p == Color.white.getRGB())
        {
          c = new Color(0x0000000000, true);
        }
        else if (p == Color.black.getRGB())
        {
          c = Color.black;
        }
        else if (p == Color.red.getRGB())
        {
          c = Color.red;
        }
        else if (p == Color.green.getRGB())
        {
          c = Color.green;
        }
        else if (p == Color.yellow.getRGB())
        {
          c = Color.yellow;
        }
        else
        {
          System.out.printf("Unknown value: %x06d\n", p);
          c = Color.black;
        }
        image.setPixel(x, y, c);
      }
    }

    font.drawString(image, "" + ((int) (power + 0.5)) + " W", 55, 5,
      Color.red);
  }


  void swap()
  {
    Image t = im1;
    im1 = im2;
    im2 = t;
  }


  @Override
  protected void init()
  {
  }


  @Override
  protected void setXml(effect xml) throws Exception
  {
    bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    im1 = new Image(width, height);
    im2 = new Image(width, height);
    font = new DosHercules();

    new Thread()
    {
      public void run()
      {
        while (true)
        {
          // if (running)
          {
            readData();
            drawMeter(im2);
            swap();
          }
          Utils.sleep(2000);
        }
      }
    }.start();
  }


  void readData()
  {
    try
    {
      URL url = new URL("http://stats.bitlair.nl/kwh");
      URLConnection uc = url.openConnection();
      BufferedReader in =
        new BufferedReader(new InputStreamReader(uc.getInputStream()));
      String s = in.readLine();
      if (s != null)
      {
        double p = Double.parseDouble(s);
        if (p > 0.5)
        {
          power = p;
        }
      }
      System.out.println("power = " + power);
      in.close();
    }
    catch (Throwable e)
    {
      System.out.println("ERROR: " + e.getMessage());
      e.printStackTrace();
      power = -1;
    }
  }
}
