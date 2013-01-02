package graphiceffect.effect;

import graphiceffect.main.Effect;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;


public class RotatingColors extends Effect
{
  int count = 0;

  int index[][] =
  {
    {
      162, 970, 486
    },
    {
      648, 0, 1294
    },
    {
      1132, 324, 808
    }
  };

  int numCells = 3;


  @Override
  public void drawImage(Image image)
  {
    int xx = 0;
    int yy = 0;
    for (int x = 0; x < width; x++)
    {
      xx = (int) (x * numCells / width);
      for (int y = 0; y < height; y++)
      {
        yy = (int) (y * numCells / height);

        int c = (count + index[xx][yy]) % palette.length();
        image.setPixel(x, y, palette.getColor(c));
      }
    }
    count = (count + 1) % palette.length();
  }


  @Override
  protected void init()
  {
  }


  protected void setXml(effect effect)
  {
  }
}
