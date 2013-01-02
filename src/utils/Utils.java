package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Utils
{
  /**
   * Add the first color to the second color. The second color is assumed to be
   * opaque. The resulting color is opaque.
   * 
   * @param c1
   * @param c2
   * @return the resulting color
   */
  public static Color addColor(Color c1, Color c2)
  {
    if ((c1 == null) && (c2 == null))
    {
      return null;
    }
    else if (c1 == null)
    {
      return c2;
    }
    else if (c2 == null)
    {
      return c1;
    }
    else if (c1.getAlpha() == 0)
    {
      return c2;
    }

    double a = ((double) c1.getAlpha()) / 255.0;
    int r =
      (int) (((double) c1.getRed()) * a + ((double) c2.getRed()) * (1 - a) + 0.5);
    int g =
      (int) (((double) c1.getGreen()) * a + ((double) c2.getGreen())
        * (1 - a) + 0.5);
    int b =
      (int) (((double) c1.getBlue()) * a + ((double) c2.getBlue()) * (1 - a) + 0.5);
    return new Color(r, g, b);
  }


  /**
   * Add two colors (ARGB). The second color is assumed to be opaque. 
   * The resulting color is opaque.
   * 
   * @param c1
   * @param c2
   * @return the resulting color
   */
  public static long addColor(long c1, long c2)
  {
    double a = ((double) ((c1 >> 24) & 0xff)) / 255.0;
    double x1 = ((int) c1 >> 16) & 0xff;
    double x2 = ((int) c2 >> 16) & 0xff;
    int r = (int) (x1 * a + x2 * (1 - a) + 0.5);
    x1 = ((int) c1 >> 8) & 0xff;
    x2 = ((int) c2 >> 8) & 0xff;
    int g = (int) (x1 * a + x2 * (1 - a) + 0.5);
    x1 = ((int) c1 >> 0) & 0xff;
    x2 = ((int) c2 >> 0) & 0xff;
    int b = (int) (x1 * a + x2 * (1 - a) + 0.5);
    return (0xff << 24) + (r << 16) + (g << 8) + b;
  }


  /**
   * Invert the color given by c. The alpha value will be kept the same.
   * 
   * @param c
   * @return the inverted color
   */
  public static Color invertColor(Color c)
  {
    int r = c.getRed();
    int g = c.getGreen();
    int b = c.getBlue();
    int a = c.getAlpha();
    return new Color(255 - r, 255 - g, 255 - b, a);
  }


  /**
   * Invert the color given by the long value c (ARGB). The alpha value
   * will be kept the same.
   * 
   * @param c
   * @return the inverted color
   */
  public static long invertColor(long c)
  {
    int r = (int) (c >> 16) & 0xff;
    int g = (int) (c >> 8) & 0xff;
    int b = (int) (c >> 0) & 0xff;
    c &= 0xff000000L;
    r = 255 - r;
    g = 255 - g;
    b = 255 - b;
    return c + (r << 16) + (g << 8) + b;
  }


  /**
   * Do an unsigned conversion from int to long.
   * 
   * @param i the input integer value
   * @return the resulting long value
   */
  public static long long_(int i)
  {
    return ((long) i) & 0xffffffff;
  }


  public static void savePng(BufferedImage image, String fileName)
  {
    try
    {
      ImageIO.write(image, "png", new File(fileName + ".png"));
    }
    catch (IOException e)
    {
      System.out.println("ERROR: failed to write image: " + e.getMessage());
      e.printStackTrace();
    }
  }


  public static void sleep(int msec)
  {
    try
    {
      Thread.sleep(msec);
    }
    catch (InterruptedException e)
    {
      // Ignore
    }
  }


  int fileIndex = -1;


  public void saveIndexedPng(BufferedImage bi, String name)
  {
    String format = "%s_%03d";

    if (fileIndex < 0)
    {
      for (fileIndex = 0;; fileIndex++)
      {
        String s = String.format(format, name, fileIndex);
        if (!(new File(s)).exists())
        {
          break;
        }
      }
    }

    try
    {
      ImageIO.write(bi, "png",
        new File(String.format(format, name, fileIndex++)));
    }
    catch (IOException e)
    {
      System.out.println("ERROR: " + e.getMessage());
      e.printStackTrace();
    }
  }


  public static int mod(int value, int mod)
  {
    value %= mod;
    while (value < 0)
    {
      value += mod;
    }
    return value;
  }


  public static double mod(double value, double mod)
  {
    value %= mod;
    while (value < 0)
    {
      value += mod;
    }
    return value;
  }


  public static void invertArray(int[][] data, int skip)
  {
    // Invert the bit order
    for (int i = 0; i < data.length; i++)
    {
      for (int j = skip; j < data[i].length; j++)
      {
        long d1 = data[i][j] & 0xffffffffL;
        long d2 = 0;
        for (int k = 0; k < 32; k++)
        {
          d2 <<= 1;
          if ((d1 & 0x1) > 0)
          {
            d2 += 1;
          }
          d1 >>= 1;
        }
        data[i][j] = (int) d2;
      }
    }

    int shift = Integer.MAX_VALUE;

    // Store the minimum number of zeros from the least significant side
    // in shift.
    for (int i = 0; i < data.length; i++)
    {
      for (int j = skip; j < data[i].length; j++)
      {
        long d = data[i][j];
        for (int k = 0; k < 32; k++)
        {
          if ((d & 0x1) > 0)
          {
            if (k < shift)
            {
              shift = k;
              break;
            }
          }
          d >>= 1;
        }
      }
    }
    System.out.println("Shift = " + shift);

    // Shift and print the data
    for (int i = 0; i < data.length; i++)
    {
      System.out.print("{");
      for (int j = 0; j < data[i].length; j++)
      {
        if (j < skip)
        {
          System.out.print(data[i][j] + ", ");
          continue;
        }
        long d = data[i][j] & 0xffffffffL;
        d >>= shift;
        data[i][j] = (int) d;
        System.out.printf("0x%x, ", d);
      }
      System.out.println("},");
    }
  }


  public static void invertArray(long[][] data, int skip)
  {
    // Invert the bit order
    for (int i = 0; i < data.length; i++)
    {
      for (int j = skip; j < data[i].length; j++)
      {
        long d1 = data[i][j] & 0x7fffffffffffffffL;
        long d2 = 0;
        for (int k = 0; k < 63; k++)
        {
          d2 <<= 1;
          if ((d1 & 0x1) > 0)
          {
            d2 += 1;
          }
          d1 >>= 1;
        }
        data[i][j] = d2;
      }
    }

    int shift = Integer.MAX_VALUE;

    // Store the minimum number of zeros from the least significant side
    // in shift.
    for (int i = 0; i < data.length; i++)
    {
      for (int j = skip; j < data[i].length; j++)
      {
        long d = data[i][j];
        for (int k = 0; k < 63; k++)
        {
          if ((d & 0x1) > 0)
          {
            if (k < shift)
            {
              shift = k;
              break;
            }
          }
          d >>= 1;
        }
      }
    }
    System.out.println("Shift = " + shift);

    // Shift and print the data
    for (int i = 0; i < data.length; i++)
    {
      System.out.print("{");
      for (int j = 0; j < data[i].length; j++)
      {
        if (j < skip)
        {
          System.out.print(data[i][j] + ", ");
          continue;
        }
        long d = data[i][j] & 0x7fffffffffffffffL;
        d >>= shift;
        data[i][j] = d;
        System.out.printf("0x%x, ", d);
      }
      System.out.println("},");
    }
  }


  public static URL getUrl(String fileName)
  {
    if ((fileName == null) || (fileName.length() == 0))
    {
      return null;
    }
    
    URL url = null;
    try
    {
      url = ClassLoader.getSystemResource(fileName);
      if (url != null)
      {
        System.out.println("Found in jar: " + fileName);
      }
      else
      {
        File f = new File(fileName);
        if (f.exists())
        {
          url = f.toURI().toURL();
          System.out.println("Found in file system: " + fileName);
        }
      }
    }
    catch (Exception e)
    {
      // Ignore
    }
    if (url == null)
    {
      System.out.println("File not found: " + fileName);
    }
    return url;
  }
}
