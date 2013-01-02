package graphiceffect.effect;

import java.awt.Color;

import graphiceffect.main.Effect;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;
import graphiceffect.xml.param;

public class Bitlair extends Effect
{
  Color fill = Color.red;

  Color stroke = Color.yellow;

  int sizex = 4;

  int sizey = 7;

  int x0 = 3;

  int y0 = 5;

  int[][] blocks = new int[][]
  {
    {
      0x1f, 0x5, 0x7
    },
    {
      0x7
    },
    {
      0xe, 0x5, 0x1
    },
    {
      0xe, 0x1, 0x1
    },
    {
      0x1, 0x5, 0x3
    },
    {
      0x7
    },
    {
      0x3, 0x4, 0x4
    },
  };


  @Override
  public void drawImage(Image image)
  {
    int count = 0;

    for (int i = 0; i < blocks.length; i++)
    {
      for (int j = 0; j < blocks[i].length; j++)
      {
        for (int k = 0; k < 5; k++)
        {
          if ((blocks[i][j] & (0x1 << k)) > 0)
          {
            image.drawRect(x0 + (sizex + 1) * count, y0 + (sizey + 1) * k,
              sizex, sizey, 1, stroke, fill);
          }
        }
        count++;
      }
      count++;
    }
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

      if (name.equals("fill"))
      {
        fill = palette.getColor(Integer.parseInt(value));
      }
      else if (name.equals("stroke"))
      {
        stroke = palette.getColor(Integer.parseInt(value));
      }
      else if (name.equals("sizex"))
      {
        sizex = Integer.parseInt(value);
      }
      else if (name.equals("sizey"))
      {
        sizey = Integer.parseInt(value);
      }
    }
    y0 = (height - 5 * sizey - 4) / 2;
    x0 = (width - 23 * sizex - 22) / 2;
  }
}
