package graphiceffect.palette;

import graphiceffect.main.Palette;
import graphiceffect.xml.palette;
import graphiceffect.xml.param;

public class Palette1 extends Palette
{
  private static int[] ints =
  {
    0xd09a11, 0x289cc8, 0x63a11a, 0xb33274, 0xe01832, 0x0d539f,
  };


  @Override
  protected void setXml(palette palette) throws Exception
  {
    for (param p : palette.getParam())
    {
      String name = p.getName();
      String value = p.getValue();
      
      if ((name == null) || (name.length() == 0))
      {
        continue;
      }

      if (name.equals("alpha"))
      {
        alpha = Integer.parseInt(value);
      }
    }
    setColors(ints);
  }
}
