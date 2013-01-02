package graphiceffect.effect;

import graphiceffect.main.Effect;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;

import java.awt.Color;

/*
 * bitlair led board: RedYellowGreen,dist=10,noblack, bg: black
 */

public class Path1 extends Effect
{
  int[] path;

  double drawCount = 0;

  double drawCountStep = 1;

  int base = (int) (Math.random() * 2000);

  int dmax = (int) ((4.0 * height / 5.0) + 0.5);

  int dmin = (int) ((1.0 * height / 5.0) + 0.5);

  int dir = 0;

  int d = 0;

  double count = 0;

  public int[][] doll1 = new int[][]
  {
    {
      -3, 0xf, 0xf0, 0x3ff00, 0x33cff,
    },
    {
      0, 0x3ffff, 0x33c00,
    },
    {
      0, 0x3ffff, 0x33cf0, 0xf,
    },
  };

  public int[][] doll = new int[][]
  {
    {
      -5, 0x000e, 0x001f, 0x003f, 0x27f3, 0x77f1, 0xf7f0, 0x77f8, 0x207e,
      0x000f, 0x0007, 0x0003, 0x0001,
    },
    // {
    // -5, 0x0020, 0x0070, 0x20f8, 0x77d0, 0xf7e0, 0x77f8, 0x27ff, 0x003f,
    // 0x0003, 0x0001,
    // },
    {
      -6, 0x0020, 0x0070, 0x00f8, 0x01d0, 0x03e0, 0x07f0, 0x0fff, 0x2f3f,
      0x7203, 0x7801, 0x7000,
    },
    {
      -2, 0x2000, 0x77ff, 0xf7ff, 0x77fb, 0x27f1, 0x0038, 0x001c, 0x000c,
      0x001c, 0x0008,
    },
  };

  int dollIndex = 0;

  int dollX;


  @Override
  public void drawImage(Image image)
  {
    drawCount += drawCountStep;

    int p = pathNext2();

    for (int x = 1; x < width; x++)
    {
      path[x - 1] = path[x];
    }
    path[width - 1] = p;

    p = path[width / 2];
    d = p - height / 3;

    // dir = p - path[width - 2];
    // if (dir > 0)
    // {
    // if (p - d > dmax)
    // {
    // d += p - d - dmax;
    // }
    // }
    // else if (dir < 0)
    // {
    // if (p - d < dmin)
    // {
    // d -= dmin - (p - d);
    // }
    // }
    // // System.out.printf("p=%d d=%d dir=%d p-d=%d\n", p, d, dir, p - d);

    for (int x = 0; x < width; x++)
    {
      int yy = path[x] - d;

      // if (yy > height)
      // {
      // yy = height;
      // }

      for (int y = height; y > yy; y--)
      {
        image.setPixel(x, y, Color.black);
      }
      // image.setPixel(x, yy, Color.white);

      int z = 0;
      for (int y = yy; y >= 0; y--)
      {
        image.setPixel(x, y, palette.getColor((z + x) + (int) count));
        // image.setPixel(x, y, getColor((z + x) * 25 + (int) count));
        // image.setPixel(x, y, Color.black);
        count -= 0.02;
        z++;
      }
    }

    int dx = dollX + doll[dollIndex][0];
    for (int i = 1; i < doll[dollIndex].length; i++, dx++)
    {
      for (int j = 0; j < 16; j++)
      {
        if ((doll[dollIndex][i] & (0x1 << j)) > 0)
        {
          image.setPixel(dx, 17 + j, Color.red);
        }
      }
    }
    dollIndex++;
    if (dollIndex >= doll.length)
    {
      dollIndex = 0;
      // dollX += 2;
    }
    if (dollX > width)
    {
      dollX = 0;
    }
  }


  int pathNext1()
  {
    return path[width - 1] + (int) (5 * Math.random()) - 2;
    // int p =
    // path[width - 1] + (path[width - 1] - path[width - 2])
    // * (int) (5 * Math.random())-2;
  }


  int pathNext2()
  {
    double x =
      0.5 * Math.sin(drawCount * 0.2 / Math.PI + 3) * 0.2
        * Math.sin(drawCount * 0.05 / Math.PI + 5) + 0.3
        * Math.sin(drawCount * 0.01 + 7);
    return (int) (3 * height * x);
  }


  @Override
  protected void init()
  {
  }


  protected void setXml(effect effect)
  {
    dmax = (int) ((4.0 * height / 5.0) + 0.5);
    dmin = (int) ((1.0 * height / 5.0) + 0.5);
    path = new int[width];

    drawCount = Math.random() * 100 * drawCountStep;
    for (int i = 0; i < path.length; i++)
    {
      drawCount += drawCountStep;
      path[i] = pathNext2();
      // path[i] = 100;
    }
    dollX = width / 2;
  }
}
