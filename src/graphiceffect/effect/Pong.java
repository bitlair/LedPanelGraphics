package graphiceffect.effect;

import graphiceffect.main.Effect;
import graphiceffect.main.Image;
import graphiceffect.main.Scene;
import graphiceffect.xml.effect;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class Pong extends Effect
{
  Image img;

  int bath = 9;

  int bat1;

  int bat2;

  int bat1v = 0;

  int bat2v = 0;

  int xx;

  int yy;

  int vx = 2;

  int vy = 1;

  // Color bg = new Color(0x40000000, true);
  Color bg = new Color(0x40000000, false);

  int tcount = 0;

  long[][] digits =
  {
    {
      0xf, 0xff, 0xfff, 0xffff, 0xfffff, 0xfffff0, 0xfffff00, 0x3ffff000,
      0x7fff0000, 0xfff00000, 0x7f000000, 0x30000000,
    },
    {
      0xf, 0xff, 0xfff, 0xffff, 0xfffff, 0xfffff0, 0xfffff00, 0x3ffff000,
      0x7fff0000, 0xfff00000, 0x7f00000f, 0x300000ff, 0xfff, 0xffff,
      0xfffff, 0xfffff0, 0xfffff00, 0x3ffff000, 0x7fff0000, 0xfff00000,
      0x7f000000, 0x30000000,
    },
    {
      0xf, 0xff, 0xfff, 0xffff, 0xfffff, 0xfffff0, 0xfffff00, 0x3ffff000,
      0x7fff0000, 0xfff00000, 0x7f00000f, 0x300000ff, 0xfff, 0xffff,
      0xfffff, 0xfffff0, 0xfffff00, 0x3ffff000, 0x7fff0000, 0xfff00000,
      0x7f00000f, 0x300000ff, 0xfff, 0xffff, 0xfffff, 0xfffff0, 0xfffff00,
      0x3ffff000, 0x7fff0000, 0xfff00000, 0x7f000000, 0x30000000,
    },
    {
      0xf, 0xff, 0xfff, 0xffff, 0xfffff, 0xfffff0, 0xfffff00, 0x3ffff000,
      0x7fff0000, 0xfff00000, 0x7f00000f, 0x300000ff, 0xfff, 0xffff,
      0xfffff, 0xfffff0, 0xfffff00, 0x3ffff000, 0x7fff0000, 0xfff00000,
      0x7f00000f, 0x300000ff, 0xfff, 0xffff, 0xfffff, 0xfffff0, 0xfffff00,
      0x3ffff000, 0x7fff0000, 0xfff00000, 0x7f00000f, 0x300000ff, 0xfff,
      0xffff, 0xfffff, 0xfffff0, 0xfffff00, 0x3ffff000, 0x7fff0000,
      0xfff00000, 0x7f000000, 0x30000000,
    },
  };


  enum GameState
  {
    starting, playing, ending
  }

  GameState state = GameState.starting;

  int lost = 0;


  @Override
  public void drawImage(Image image)
  {
    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < height; y++)
      {
        img.setPixel(x, y, bg);
      }
    }

    switch (state)
    {
      case starting:
        starting(image);
        break;
      case playing:
        playing(image);
        break;
      case ending:
        ending(image);
        break;
    }
  }


  private void drawDigit(int value, Image image)
  {
    switch (value)
    {
      case 5:
      {
        int x0 = width / 2 - 18;
        int y0 = (height - 32) / 2;
        for (int x = 0; x < digits[3].length; x++)
        {
          for (int y = 0; y < 32; y++)
          {
            if ((digits[3][x] & (0x1 << y)) > 0)
            {
              image.setPixel(x + x0, y + y0, Color.red);
            }
          }
        }
        x0 = (width - 52) / 2 + 2;
        y0 = (height - 12) / 2;
        int z = 0x1f;
        for (int x = 0; x < 52; x++)
        {
          if ((x > 0) && (x % 8 == 0))
          {
            z <<= 1;
          }
          for (int y = 0; y < 32; y++)
          {
            if ((z & (0x1 << y)) > 0)
            {
              image.setPixel(x + x0, y + y0, Color.red);
            }
          }
        }
        break;
      }
      case 4:
      {
        int x0 = width / 2 - 18;
        int y0 = (height - 32) / 2;
        for (int x = 0; x < digits[3].length; x++)
        {
          for (int y = 0; y < 32; y++)
          {
            if ((digits[3][x] & (0x1 << y)) > 0)
            {
              image.setPixel(x + x0, y + y0, Color.red);
            }
          }
        }
        break;
      }
      case 3:
      {
        int x0 = width / 2 - 18;
        int y0 = (height - 32) / 2;
        for (int x = 0; x < digits[2].length; x++)
        {
          for (int y = 0; y < 32; y++)
          {
            if ((digits[2][x] & (0x1 << y)) > 0)
            {
              image.setPixel(x + x0, y + y0, Color.red);
            }
          }
        }
        break;
      }
      case 2:
      {
        int x0 = width / 2 - 8;
        int y0 = (height - 32) / 2;
        for (int x = 0; x < digits[1].length; x++)
        {
          for (int y = 0; y < 32; y++)
          {
            if ((digits[1][x] & (0x1 << y)) > 0)
            {
              image.setPixel(x + x0, y + y0, Color.red);
            }
          }
        }
        break;
      }
      case 1:
      {
        int x0 = width / 2 - 8;
        int y0 = (height - 32) / 2;
        for (int x = 0; x < digits[0].length; x++)
        {
          for (int y = 0; y < 32; y++)
          {
            if ((digits[0][x] & (0x1 << y)) > 0)
            {
              image.setPixel(x + x0, y + y0, Color.red);
            }
          }
        }
        break;
      }
    }
  }


  @Override
  protected void init()
  {
    img = new Image(width, height);
    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < height; y++)
      {
        img.setPixel(x, y, Color.black);
      }
    }
    bat1 = height / 2;
    bat2 = height / 2;
    xx = width / 2;
    yy = height / 2;

    scene.addKeyListener(new Scene.KeyListener()
    {
      @Override
      public void keyTyped(KeyEvent event)
      {
        handleKey(event);
      }
    });
  }


  private void handleKey(KeyEvent event)
  {
    switch (event.getKeyChar())
    {
      case 'a':
        // up 1
        bat1++;
        bat1v = 1;
        break;
      case 'x':
        // down 1
        bat1--;
        bat1v = -1;
        break;
      case 'l':
        // up 2
        bat2++;
        bat2v = 1;
        break;
      case 'm':
        // down 2
        bat2--;
        bat2v = -1;
        break;
    }
  }


  public Pong()
  {
    super();
    setState(GameState.starting);
  }


  @Override
  protected void setXml(effect xml) throws Exception
  {
  }


  void reset()
  {
    bat1 = (int) (Math.random() * height);
    bat2 = (int) (Math.random() * height);
    vx = 0;
    while (vx == 0)
    {
      vx = (int) (Math.random() * 5 - 2);
    }
    vy = 0;
    while (vy == 0)
    {
      vy = (int) (Math.random() * 5 - 2);
    }
    if (vx > vy)
    {
      int t;
      t = vx;
      vx = vy;
      vy = t;
    }
    int x = (int) (Math.random() * width / 3);
    if (vx > 0)
    {
      xx = x + 10;
    }
    else
    {
      xx = width - x - 10;
    }
    yy = (int) (Math.random() * height);
  }


  void starting(Image image)
  {

    if (tcount > 0)
    {
      drawDigit(tcount / 10 + 1, image);
      tcount--;
    }
    else
    {
      reset();
      setState(GameState.playing);
    }
  }


  void playing(Image image)
  {
    if (checkEnd())
    {
      setState(GameState.ending);
      return;
    }

    xx += vx;
    yy += vy;
    if (xx < 0)
    {
      xx = -xx;
      vx = -vx;
    }
    if (xx >= width)
    {
      xx = width - 1 - (xx - width);
      vx = -vx;
    }
    if (yy < 0)
    {
      yy = -yy;
      vy = -vy;
    }
    if (yy >= height)
    {
      yy = height - 1 - (yy - height);
      vy = -vy;
    }
    img.setPixel(xx, yy, Color.red);

    img.drawLine(0, bat1 - bath / 2, 0, bat1 + bath / 2, Color.green);
    img.drawLine(width - 1, bat2 - bath / 2, width - 1, bat2 + bath / 2,
      Color.yellow);
    image.paint(img);

    bat1v = 0;
    bat2v = 0;
  }


  boolean checkEnd()
  {
    boolean result = false;
    
    lost = 0;
    if (xx <= vx)
    {
      yy += vy * xx / vx;
      if ((yy < bat1 - bath / 2) || (yy > bat1 + bath / 2))
      {
        lost = 1;
        System.out.println("lost 1");
        result = true;
      }
    }
    else if (xx >= width - vx)
    {
      yy += vy * (width - xx) / vx;
      if ((yy < bat2 - bath / 2) || (yy > bat2 + bath / 2))
      {
        lost = 2;
        System.out.println("lost 2");
        result = true;
      }
    }
    return result;
  }


  void ending(Image image)
  {
    tcount--;
    if (tcount > 0)
    {
      if (lost == 1)
      {
        image.drawLine(10, 10, width/2 - 10, height - 10, Color.red);
        image.drawLine(width/2 - 10, 10, 10, height - 10, Color.red);
      }
      if (lost == 2)
      {
        image.drawLine(width/2 + 10, 10, width - 10, height - 10, Color.red);
        image.drawLine(width - 10, 10, width/2 + 10, height - 10, Color.red);
      }
    }
    else
    {
      setState(GameState.starting);
    }
  }


  void setState(GameState state)
  {
    switch (state)
    {
      case starting:
        tcount = 50;
        break;
      case playing:
        break;
      case ending:
        tcount = 30;
        break;
    }
    this.state = state;
  }
}
