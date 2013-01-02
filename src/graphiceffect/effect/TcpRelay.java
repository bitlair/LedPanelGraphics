package graphiceffect.effect;

import graphiceffect.main.Effect;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TcpRelay extends Effect
{
  Image image;


  @Override
  public synchronized void drawImage(Image image)
  {
    image.paint(this.image);
  }


  @Override
  protected void init()
  {
  }


  @Override
  protected void setXml(effect xml) throws Exception
  {
    image = new Image(width, height);
    new TcpServer(this);
  }


  synchronized void fillImage(byte[] data, int count) throws Exception
  {
    int x = 0;
    int y = 0;
    for (int i = 0; i < count - 3; i++)
    {
      int v = data[i + 3];
      if (x >= width)
      {
        x = 0;
        y++;
      }
      for (int j = 0; j < 4; j++, x++)
      {
        Color color = null;
        int c = (v >> (3 - j)) & 0x3;
        switch (c)
        {
          case 0:
            color = Color.black;
            break;
          case 1:
            color = Color.green;
            break;
          case 2:
            color = Color.red;
            break;
          case 3:
            color = Color.yellow;
            break;

        }
        image.setPixel(x, y, color);
      }
    }
  }
}

class TcpServer implements Runnable
{
  TcpRelay parent;

  ServerSocket server;


  public TcpServer(TcpRelay parent) throws Exception
  {
    this.parent = parent;

    server = new ServerSocket(1337);
    server.setReuseAddress(true);
    server.setSoTimeout(1);
    (new Thread(this)).start();
  }


  @Override
  public void run()
  {
    Socket client = null;
    InputStream cin = null;
    byte[] data = new byte[10240];

    int frameSize = 3 + parent.getWidth() * parent.getHeight() * 2 / 8;

    // State:
    // 0 = unknown
    // 1 = last byte was zero
    // 2 - received 0x3a
    // 3 - received 1st 0x30
    // 4 - in data frame (=received 2nd 0x30)

    int state = 0;
    int count = 0;

    while (true)
    {
      try
      {
        // Check if somebody (else) wants to connect
        try
        {
          Socket s = server.accept();
          if (s != null)
          {
            if (client != null)
            {
              cin.close();
              client.close();
              client = null;
            }
            client = s;
            client.setSoTimeout(1);
            cin = client.getInputStream();
          }
        }
        catch (Exception e)
        {
          // Ignore
        }

        if (client == null)
        {
          sleep(100);
          continue;
        }

        try
        {
          while (true)
          {
            int c = cin.read();
            // Read data
            switch (state)
            {
              case 0:
              case 1:
                count = 0;
                if (c == 0)
                {
                  state = 1;
                }
                else if (c == 0x3a)
                {
                  state = 2;
                  data[count++] = (byte) c;
                }
                else
                {
                  state = 0;
                }
                break;
              case 2:
                if (c != 0x30)
                {
                  state = 0;
                }
                else
                {
                  state = 3;
                  data[count++] = (byte) c;
                }
                break;
              case 3:
                if (c != 0x30)
                {
                  state = 0;
                }
                else
                {
                  state = 4;
                  data[count++] = (byte) c;
                }
                break;
              case 4:
                if (count >= frameSize)
                {
                  if (c == 0)
                  {
                    parent.fillImage(data, count);
                    state = 1;
                  }
                  else
                  {
                    state = 0;
                  }
                }
                else
                {
                  data[count++] = (byte) c;
                }
                break;
            }
          }
        }
        catch (SocketTimeoutException e)
        {
          // Ignore
        }
      }
      catch (Throwable e)
      {
        System.out.println("ERROR: " + e.getMessage());
        e.printStackTrace();

        if (client != null)
        {
          try
          {
            client.close();
          }
          catch (IOException e1)
          {
            // Ignore
          }
          client = null;
        }
      }
    }
  }


  public void sleep(int msec)
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
}
