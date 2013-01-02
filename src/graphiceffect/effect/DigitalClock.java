package graphiceffect.effect;

import java.awt.Color;
import java.util.Calendar;

import graphiceffect.main.Effect;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;

public class DigitalClock extends Effect
{
  long[][] segment1 = new long[][]
  {
    {
      1, 0x4000000, 0x6000000, 0x7000000, 0x7800000, 0x7800000, 0x7800000,
      0x7800000, 0x7800000, 0x7800000, 0x7800000, 0x7800000, 0x7800000,
      0x7000000, 0x6000000, 0x4000000,
    },
    {
      0, 0x3ffc000, 0x1ffc000, 0xff8000, 0x7f0000,
    },
    {
      13, 0x7f0000, 0xff8000, 0x1ffc000, 0x3ffc000,
    },
    {
      2, 0x2000, 0x7000, 0xf000, 0xf000, 0xf000, 0xf000, 0xf000, 0xf000,
      0xf000, 0xf000, 0xf000, 0x7000, 0x2000,
    },
    {
      0, 0x1ffe, 0x1ffc, 0xff8, 0x7f0,
    },
    {
      13, 0x7f0, 0xff8, 0x1ffc, 0x1ffe,
    },
    {
      1, 0x1, 0x3, 0x7, 0xf, 0xf, 0xf, 0xf, 0xf, 0xf, 0xf, 0xf, 0xf, 0x7,
      0x3, 0x1,
    },
    {
      0, 0xf0780, 0xf0780, 0xf0780, 0xf0780,
    },
  };


  long[][] segment = new long[][]
  {
    {
      5, 0x4000000, 0x6000000, 0x7000000, 0x7000000, 0x7000000, 0x7000000,
      0x7000000, 0x7000000, 0x7000000, 0x7000000, 0x7000000, 0x7000000,
      0x7000000, 0x6000000, 0x4000000,
    },
    {
      2, 0xc000, 0x1fc000, 0x3ff8000, 0x1ff0000, 0xe00000,
    },
    {
      16, 0x8000, 0x1fc000, 0xffc000, 0x1ff0000, 0x3e00000,
    },
    {
      4, 0x2000, 0x7000, 0x7000, 0x7000, 0x7000, 0x7000, 0x7000, 0x7000,
      0x7000, 0x7000, 0x7000, 0x7000, 0x2000,
    },
    {
      0, 0x3e, 0x7fc, 0x1ff8, 0x1fc0, 0x800,
    },
    {
      14, 0x38, 0x7fc, 0xffe, 0x1fc0, 0x1800,
    },
    {
      1, 0x1, 0x3, 0x7, 0x7, 0x7, 0x7, 0x7, 0x7, 0x7, 0x7, 0x7, 0x7, 0x7,
      0x3, 0x1,
    },
    {
      0, 0x700, 0x700, 0x70700, 0x70000, 0x70000,
    },
  };


  int[][] digit = new int[][]
  {
    {
      0, 1, 2, 4, 5, 6
    },
    {
      2, 5
    },
    {
      0, 2, 3, 4, 6
    },
    {
      0, 2, 3, 5, 6
    },
    {
      1, 2, 3, 5
    },
    {
      0, 1, 3, 5, 6
    },
    {
      0, 1, 3, 4, 5, 6
    },
    {
      0, 2, 5
    },
    {
      0, 1, 2, 3, 4, 5, 6
    },
    {
      0, 1, 2, 3, 5, 6
    }
  };

  // double count = 2000;
  //
  // double step = -0.2;

  Color colorOn = Color.black;

  Color colorOff = null;


  @Override
  public void drawImage(Image image)
  {
    int offsetX = 13;
    int segmentWidth = 21;
    int dotsWidth = 5;
    int space = 2;

    // int offsetX = 15;
    // int segmentWidth = 17;
    // int dotsWidth = 4;
    // int space = 4;

    // // count += step;
    // // int c = (int) count;
    //
    // int d4 = c % 10;
    // int d3 = (c / 10) % 10;
    // int d2 = (c / 100) % 10;
    // int d1 = (c / 1000) % 10;

    Calendar cal = Calendar.getInstance();
    int d4 = cal.get(Calendar.MINUTE);
    int d3 = d4 / 10;
    d4 %= 10;
    int d2 = cal.get(Calendar.HOUR_OF_DAY);
    int d1 = d2 / 10;
    d2 %= 10;
    int d5 = cal.get(Calendar.MILLISECOND);

    int offset = offsetX;
    if (d1 > 0)
    {
      paintDigit(image, offset, d1);
    }
    offset += segmentWidth + space;
    paintDigit(image, offset, d2);
    offset += segmentWidth + space;
    if (d5 < 500)
    {
      paintDots(image, offset);
    }
    offset += dotsWidth + space;
    paintDigit(image, offset, d3);
    offset += segmentWidth + space;
    paintDigit(image, offset, d4);
  }


  void paintDigit(Image image, int offset, int value)
  {
    int offsetY = 10;
    Color color;

    int ds = 0;
    for (int i = 0; i < segment.length - 1; i++)
    {
      if ((ds < digit[value].length) && (digit[value][ds] == i))
      {
        color = colorOn;
        ds++;
      }
      else
      {
        color = colorOff;
      }
      if (color == null)
      {
        continue;
      }
      int x = offset + (int) segment[i][0];
      for (int j = 1; j < segment[i].length; j++, x++)
      {
        for (int k = 0; k < 27; k++)
        {
          if ((segment[i][j] & ((0x1) << k)) > 0)
          {
            image.setPixel(x, offsetY + k, color);
          }
        }
      }
    }
  }


  void paintDots(Image image, int offset)
  {
    int offsetY = 10;
    int i = segment.length - 1;

    int x = offset + (int) segment[i][0];
    for (int j = 1; j < segment[i].length; j++, x++)
    {
      for (int k = 0; k < 27; k++)
      {
        if ((segment[i][j] & ((0x1) << k)) > 0)
        {
          image.setPixel(x, offsetY + k, colorOn);
        }
      }
    }
  }


  @Override
  protected void init()
  {
  }


  @Override
  protected void setXml(effect xml) throws Exception
  {
  }
}
