package graphiceffect.effect;

import graphiceffect.main.Effect;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;

import java.awt.Color;

public class Snake1 extends Effect
{
  int start;

  int dir;

  int dirx;

  int diry;

  int x1;

  int x2;

  int y1;

  int y2;

  int indent = 0;

  Color[][] li;

  Color invisible = new Color(0x00000000, true);

  Color color = Color.red;


  @Override
  public void drawImage(Image image)
  {
    for (int i = 0; i < 10; i++)
    {
      update();
    }
    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < height; y++)
      {
        image.setPixel(x, y, li[x][y]);
      }
    }
  }


  @Override
  protected void setXml(effect xml) throws Exception
  {
    li = new Color[width][height];

    start = (int) (4 * Math.random());
    dir = (int) (2 * Math.random());
    init();
  }


  @Override
  protected void init()
  {
    int w = width - 1;
    int h = height - 1;

    indent = 0;

    switch (start)
    {
      case 0:
        x1 = 0;
        y1 = 0;
        if (dir == 0)
        {
          dirx = 0;
          diry = 1;
        }
        else
        {
          dirx = 1;
          diry = 0;
        }
        break;
      case 1:
        x1 = 0;
        y1 = h;
        if (dir == 0)
        {
          dirx = 1;
          diry = 0;
        }
        else
        {
          dirx = 0;
          diry = -1;
        }
        break;
      case 2:
        x1 = w;
        y1 = h;
        if (dir == 0)
        {
          dirx = 0;
          diry = -1;
        }
        else
        {
          dirx = -1;
          diry = 0;
        }
        break;
      case 3:
        x1 = w;
        y1 = 0;
        if (dir == 0)
        {
          dirx = -1;
          diry = 0;
        }
        else
        {
          dirx = 0;
          diry = 1;
        }
        break;
    }
    x2 = x1 + dirx * w;
    y2 = y1 + diry * h;

    if ((x1 == x2) && (y1 == y2))
    {
      int dx;
      int dy;
      if (dir == 0)
      {
        dx = diry;
        dy = dirx;
        if (dy != 0)
        {
          dy = -dy;
        }
      }
      else
      {
        dx = diry;
        dy = dirx;
        if (dx != 0)
        {
          dx = -dx;
        }
      }
    }

    li[x1][y1] = color;
    update();
    li[x1][y1] = color;
  }


  void update()
  {
    x1 += dirx;
    y1 += diry;

    li[x1][y1] = color;

    if ((x1 == x2) && (y1 == y2))
    {
      indent++;
      if ((indent >= height - 1) || (indent >= width - 1))
      {
        color = invisible;
        init();
        return;
      }

      int w = width - 1 - indent;
      int h = height - 1 - indent;

      int dx;
      int dy;
      if (dir == 0)
      {
        dx = diry;
        dy = dirx;
        if (dy != 0)
        {
          dy = -dy;
        }
      }
      else
      {
        dx = diry;
        dy = dirx;
        if (dx != 0)
        {
          dx = -dx;
        }
      }
      dirx = dx;
      diry = dy;
      x2 = x1 + dirx * w;
      y2 = y1 + diry * h;
    }
  }
}
