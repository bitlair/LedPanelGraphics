package graphiceffect.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import utils.Utils;

public class Image
{
  private int width;

  private int height;

  private Color[][] image;

  OutputStream out;

  int bCount;

  public int cmin = 127;


  public Image(int width, int height)
  {
    this.width = width;
    this.height = height;
    image = new Color[width][height];
  }


  public void setPixel(int x, int y, Color color)
  {
    if ((x >= 0) && (x < width) && (y >= 0) && (y < height))
    {
      Color pixel = image[x][y];

      pixel = Utils.addColor(color, pixel);

      image[x][y] = pixel;
    }
  }


  public void setPixel(int x, int y, int color)
  {
    if ((x >= 0) && (x < width) && (y >= 0) && (y < height))
    {
      int a = (color >> 24) & 0xff;
      int r = (color >> 16) & 0xff;
      int g = (color >> 8) & 0xff;
      int b = (color >> 0) & 0xff;
      setPixel(x, y, new Color(r, g, b, a));
    }
  }


  public Color getPixel(int x, int y)
  {
    if ((x >= 0) && (x < width) && (y >= 0) && (y < height))
    {
      Color c = image[x][y];
      if (c == null)
      {
        return Color.black;
      }
      return c;
    }
    return null;
  }


  /**
   * Paint this Image on the given canvas with blocks of size pixelSize.
   * 
   * @param g
   * @param pixelSize
   */
  public void paint(Graphics g, int pixelSize)
  {
    // g.fillRect(0, 0, width * pixelSize, height * pixelSize);
    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < height; y++)
      {
        g.setColor(getPixel(x, y));
        // g.fillOval(x * pixelSize, (height - y - 1) * pixelSize, pixelSize,
        g.fillRect(x * pixelSize, (height - y - 1) * pixelSize, pixelSize,
          pixelSize);
      }
    }
  }


  /**
   * Get a BufferedImage representing this Image painted with blocks
   * of size pixelSize.
   * 
   * @param pixelSize
   * @return
   */
  public BufferedImage paint(int pixelSize)
  {
    BufferedImage bi =
      new BufferedImage(width * pixelSize, height * pixelSize,
        BufferedImage.TYPE_INT_RGB);
    paint(bi.getGraphics(), pixelSize);
    return bi;
  }


  /**
   * Get a BufferedImage representing this Image painted with blocks
   * of size pixelSize.
   * 
   * @param pixelSize
   * @return
   */
  public void paint(BufferedImage image)
  {
    BufferedImage bi =
      new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    {
      Graphics2D g = (Graphics2D) bi.getGraphics();
      g.scale((double) getWidth() / bi.getWidth(),
        (double) getHeight() / bi.getHeight());
      g.setRenderingHint(RenderingHints.KEY_RENDERING,
        RenderingHints.VALUE_RENDER_QUALITY);
      g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
      g.setRenderingHint(RenderingHints.KEY_DITHERING,
        RenderingHints.VALUE_DITHER_ENABLE);
      g.drawImage(image, 0, 0, width, height, null);
      g.dispose();
    }
    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < height; y++)
      {
        int c = bi.getRGB(x, height - 1 - y);
        int r = (c >> 16) & 0xff;
        int g = (c >> 8) & 0xff;
        int b = (c >> 0) & 0xff;
        g = (g + b) / 2;
        Color color;
        if ((r > cmin) && (g > cmin))
        {
          color = Color.yellow;
        }
        else if (r > cmin)
        {
          color = Color.red;
        }
        else if (g > cmin)
        {
          color = Color.green;
        }
        else
        {
          color = Color.black;
        }
        setPixel(x, y, color);
      }
    }
  }


  /**
   * Get a BufferedImage representing this Image painted with blocks
   * of size pixelSize.
   * 
   * @param pixelSize
   * @return
   */
  public void paint(Image image)
  {
    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < height; y++)
      {
        setPixel(x, y, image.getPixel(x, y));
      }
    }
  }


  /**
   * Get a BufferedImage representing this Image painted with blocks
   * of size pixelSize.
   * 
   * @param pixelSize
   * @return
   */
  public void paint(Image image, int shift)
  {
    int xx = shift;
    for (int x = 0; x < width; x++)
    {
      xx = (x + shift) % width;
      for (int y = 0; y < height; y++)
      {
        setPixel(xx, y, image.getPixel(x, y));
      }
    }
  }


  /**
   * Send this Image to the output stream (formated for the Bitlair
   * red/green/yellow board).
   * 
   * @param out
   * @throws IOException
   */
  public void send(OutputStream out) throws IOException
  {
    this.out = out;
    bCount = 0;

    int count = 0;
    int b = 0;
    int c = 0;
    write(0x3a);
    write(0x30);
    write(0x30);
    for (int y = height - 1; y >= 0; y--)
    {
      for (int x = 0; x < width; x++)
      {
        c =
          (getPixel(x, y).getRed() > 0 ? 2 : 0)
            + (getPixel(x, y).getGreen() > 0 ? 1 : 0);
        b <<= 2;
        b += c & 0x3;

        count++;
        if (count == 4)
        {
          write(b);
          b = 0;
          count = 0;
        }
      }
    }
    for (int i = 0; i < 10; i++)
    {
      write(0x00);
    }
    out.flush();
    // System.out.println("bCount = " + bCount);
  }


  /** 
   * Method used to get information about the bytes send.
   *  
   * @param b
   * @throws IOException
   */
  void write(int b) throws IOException
  {
    out.write((byte) (b & 0xff));
    bCount++;
  }


  public int getWidth()
  {
    return width;
  }


  public int getHeight()
  {
    return height;
  }


  public void fill()
  {
    fill(new Color(0, true));
  }


  public void fill(Color fill)
  {
    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < height; y++)
      {
        setPixel(x, y, fill);
      }
    }
  }


  public void drawRect(int x, int y, int w, int h, int sw, Color stroke,
    Color fill)
  {
    for (int i = 0; i < w; i++)
    {
      for (int j = 0; j < sw; j++)
      {
        setPixel(x + i, y + j, stroke);
        setPixel(x + i, y - j + h - 1, stroke);
      }
    }
    for (int i = sw; i < h - sw; i++)
    {
      for (int j = 0; j < sw; j++)
      {
        setPixel(x + j, y + i, stroke);
        setPixel(x - j + w - 1, y + i, stroke);
      }
    }
    if (fill != null)
    {
      for (int i = sw; i < w - sw; i++)
      {
        for (int j = sw; j < h - sw; j++)
        {
          setPixel(x + i, y + j, fill);
        }
      }
    }
  }


  public void drawLine(int x1, int y1, int x2, int y2, Color color)
  {
    int w = x2 - x1;
    int h = y2 - y1;
    int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
    if (w < 0)
      dx1 = -1;
    else if (w > 0)
      dx1 = 1;
    if (h < 0)
      dy1 = -1;
    else if (h > 0)
      dy1 = 1;
    if (w < 0)
      dx2 = -1;
    else if (w > 0)
      dx2 = 1;
    int longest = Math.abs(w);
    int shortest = Math.abs(h);
    if (!(longest > shortest))
    {
      longest = Math.abs(h);
      shortest = Math.abs(w);
      if (h < 0)
        dy2 = -1;
      else if (h > 0)
        dy2 = 1;
      dx2 = 0;
    }
    int numerator = longest >> 1;
    for (int i = 0; i <= longest; i++)
    {
      setPixel(x1, y1, color);
      numerator += shortest;
      if (!(numerator < longest))
      {
        numerator -= longest;
        x1 += dx1;
        y1 += dy1;
      }
      else
      {
        x1 += dx2;
        y1 += dy2;
      }
    }
  }
}
