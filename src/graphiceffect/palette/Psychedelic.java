package graphiceffect.palette;

import graphiceffect.main.Palette;
import graphiceffect.xml.palette;
import graphiceffect.xml.param;

public class Psychedelic extends Palette
{
  private static int[] ints =
  {
    0xe6e5f1, 0xf7ffb4, 0xe97c41, 0xd8ee18, 0xbdbba7, 0x78faf5, 0xfffd00,
    0x00ffa2, 0x232127, 0x697f69, 0x312dd1, 0xff00f6, 0xd71b32, 0xf18eef,
    0xffffff,
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


/*
new Color(0xcd1333),
new Color(0x2d242d),
new Color(0xf67dcf),
new Color(0x0aa2d9),
new Color(0x9ffb21),
new Color(0xd511ca),
new Color(0x33fea7),
new Color(0x23ca1c),
new Color(0x2d19d8),
new Color(0xe7d3f2),
new Color(0xf2d82a),
new Color(0x85f2eb),
new Color(0xe47b13),
new Color(0x8184f4),
new Color(0xffffff),
*/
