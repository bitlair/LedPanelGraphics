package graphiceffect.main;

import java.awt.Color;

public abstract class Font
{
  protected char[] letter;

  protected int[][] letterData;


  public Font()
  {
    setData();
  }


  abstract public int getFixedWidth();


  abstract public int getMaxWidth();


  abstract protected void setData();


  public void drawString(Image image, String string, int x, int y,
    Color color)
  {
    for (int i = 0; i < string.length(); i++)
    {
      int[] letter = getLetter(string.charAt(i));

      for (int k = 0; k < letter.length; k++)
      {
        for (int j = 0; j < 16; j++)
        {
          if ((letter[k] & (0x1 << j)) > 0)
          {
            image.setPixel(x, y + j, color);
          }
        }
        x++;
        if (x >= image.getWidth())
        {
          return;
        }
      }
      x += 1;
    }
  }


  public final String testString()
  {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < letter.length; i++)
    {
      sb.append(letter[i]);
    }
    return sb.toString();
  }


  public final int[] getLetter(char c)
  {
    for (int i = 0; i < letter.length; i++)
    {
      if (letter[i] == c)
      {
        return letterData[i];
      }
    }
    return null;
  }


  public final int length()
  {
    return letter.length;
  }


  public final int[] getLetter(int index)
  {
    return letterData[index];
  }

}
