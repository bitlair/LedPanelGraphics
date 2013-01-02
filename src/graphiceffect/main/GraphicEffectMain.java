package graphiceffect.main;

import graphiceffect.xml.Xml;
import graphiceffect.xml.graphiceffect;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

import utils.UtilTimer;
import utils.Utils;

/*
 * Gigi D'Agostino - Blablabla : http://www.youtube.com/watch?v=Hrph2EW9VjY
 * Gigi D'Agostino - The Riddle : http://www.youtube.com/watch?v=cvvd-9azD1M
 * 
 * Countdown
 * Old style movie - http://www.youtube.com/watch?v=CeNMOYuICcM
 *  - http://www.youtube.com/watch?v=CeNMOYuICcM
 * 24 - http://www.youtube.com/watch?v=6nvKN_H4e-I
 * http://www.youtube.com/watch?v=20Xxfl0pAAQ
 * 
 * 
 * Get color palette from gif:
 *   gifsicle --cinfo <file.gif>
 *   
 *   
 * From:
 * http://www.bernzilla.com/2009/08/16/how-to-make-an-animated-gif-from-video/
 * 
 * mplayer.exe ghostbusters.mkv -ss 32:35 -endpos 7 -ao null \
 *   -vo gif89a:fps=10:output=ghostbusters.gif \
 *   -vf palette,rgb2bgr,scale=240:135
 * 
 * 
 * http://blog.ahfr.org/2008/03/making-animated-gifs-with-free-software.html
 * 1) watch what the loop looks like
 * mplayer -ao null -loop 0 -ss 0:11:22 -endpos 5 file.avi
 * 2) write loop to jpeg files
 * mplayer -ao null -ss 0:11:22 -endpos 5 file.avi -vo jpeg:outdir=moviedirectory
 * 3) use GIMP:
 *   - File -> Open as layers
 *   - Save as .gif with "save as animation" and "loop forever"
 * 
 * --------------
 * Space invaders
 * --------------
 * 
 */

public class GraphicEffectMain
{
  /*
   *  Settings
   */

  int imageWidth = 120;

  int imageHeight = 48;

  int pixelSize = 2;

  String hostName = null;

  // String hostName = "192.168.88.117";

  int port = 1337;

  double fpsTarget = 10;

  /*
   *  Variables
   */

  JFrame frame;

  JPanel panelMain;

  JLabel infoLabel;

  long last_millis = System.currentTimeMillis();

  long time = 0;

  Image image;

  Utils utils = new Utils();

  OutputStream out = null;

  boolean saveImage = false;

  int sceneId = 0;

  Scene[] scene;

  boolean running = false;

  File lastConfigDir = new File("scene");

  UtilTimer timer;

  String fileName = null;

  boolean nogui = false;
  
  boolean keepScene = false;


  public static void main(String[] args)
  {
    try
    {
      (new GraphicEffectMain(args)).run();
    }
    catch (Throwable e)
    {
      System.out.println("ERROR: " + e.getMessage());
      e.printStackTrace();
    }
  }


  public GraphicEffectMain(String[] args) throws Exception
  {
    if (args.length > 0)
    {
      for (int i = 0; i < args.length; i++)
      {
        if (args[i].equals("-nogui"))
        {
          nogui = true;
        }
        else
        {
          fileName = args[i];
        }
      }
    }
    init(fileName);
  }


  void init(String fileName) throws Exception
  {
    running = false;
    if (frame != null)
    {
      frame.setVisible(false);
      frame.dispose();
      frame = null;
    }
    if (timer != null)
    {
      timer.stop();
      timer = null;
    }
    if (out != null)
    {
      out.close();
      out = null;
    }

    time = 0;
    out = null;
    sceneId = 0;

    processXml(fileName);

    if (!nogui)
    {
      createGui();
    }

    try
    {
      if ((hostName != null) && (hostName.length() > 0))
      {
        Socket socket = new Socket(hostName, port);
        out = socket.getOutputStream();
      }
    }
    catch (Exception e)
    {
      System.out.println("ERROR: " + e.getMessage());
      e.printStackTrace();
    }
  }


  private URL checkFilename(String fileUrl) throws Exception
  {
    URL url = Utils.getUrl(fileUrl);
    if (url != null)
    {
      return url;
    }

    File file = null;
    if (fileUrl == null)
    {
      JFileChooser jfc = new JFileChooser(lastConfigDir);
      jfc.showDialog(frame, "Select");
      file = jfc.getSelectedFile();
      if ((file == null) || (!file.exists()))
      {
        System.exit(1);
      }
    }
    else
    {
      file = new File(fileUrl);
      if ((file == null) || (!file.exists()))
      {
        JFileChooser jfc = new JFileChooser(lastConfigDir);
        jfc.showDialog(frame, "Select");
        file = jfc.getSelectedFile();
        if ((file == null) || (!file.exists()))
        {
          System.exit(1);
        }
      }
    }
    lastConfigDir = file.getParentFile();
    fileName = file.getPath();
    return file.toURI().toURL();
  }


  void processXml(String fileName) throws Exception
  {
    URL fileUrl = checkFilename(fileName);

    graphiceffect xge =
      (graphiceffect) Xml.read(fileUrl.openStream(),
        graphiceffect.class);

    nogui = xge.isNogui();
    imageWidth = xge.getWidth();
    imageHeight = xge.getHeight();
    pixelSize = xge.getPixelSize();
    hostName = xge.getHostname();
    port = xge.getPort();
    fpsTarget = xge.getFpsTarget();

    image = new Image(imageWidth, imageHeight);

    scene = new Scene[xge.getScene().length];
    for (int i = 0; i < scene.length; i++)
    {
      scene[i] = new Scene(xge.getScene()[i], imageWidth, imageHeight);
    }
  }


  @SuppressWarnings("serial")
  void createGui()
  {
    frame = new JFrame("GraphicFX");
    panelMain = new JPanel()
    {
      public void paintComponent(Graphics g)
      {
        try
        {
          GraphicEffectMain.this.paintComponent(g);
        }
        catch (Throwable e)
        {
          System.out.println("ERROR: " + e.getMessage());
          e.printStackTrace();
          System.exit(1);
        }
      }
    };
    panelMain.setFocusable(true);
    panelMain.addKeyListener(new KeyListener()
    {
      @Override
      public void keyTyped(KeyEvent event)
      {
        switch (event.getKeyChar())
        {
          case 'p':
            saveImage = true;
            break;
          case 'n':
            nextScene();
            break;
          case 'b':
            prevScene();
            break;
          case 'k':
            keepScene();
            break;
        }
        for (Scene s : scene)
        {
          s.keyTyped(event);
        }
      }


      @Override
      public void keyReleased(KeyEvent event)
      {
      }


      @Override
      public void keyPressed(KeyEvent event)
      {
      }
    });

    Dimension d;

    JPanel p = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    d = new Dimension(imageWidth * pixelSize, imageHeight * pixelSize);
    panelMain.setMinimumSize(d);
    panelMain.setPreferredSize(d);
    c.gridx = 0;
    c.gridy = 0;
    p.add(panelMain, c);

    infoLabel = new JLabel("x");
    infoLabel.setHorizontalAlignment(SwingConstants.LEFT);
    c.gridx = 0;
    c.gridy = 1;
    c.fill = GridBagConstraints.HORIZONTAL;
    p.add(infoLabel, c);

    frame.setContentPane(p);
    frame.addMouseListener(new PopClickListener(this));

    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }


  public void run()
  {
    timer = new UtilTimer((int) (0.5 + 1000.0 / fpsTarget), new Runnable()
    {
      @Override
      public void run()
      {
        timerTask();
      }
    });

    last_millis = System.currentTimeMillis();
    running = true;
  }


  public void paintComponent(Graphics g)
  {
    if (!running)
    {
      return;
    }

    try
    {
      image.paint(g, pixelSize);
    }
    catch (Throwable e)
    {
      System.out.println("ERROR: " + e.getMessage());
      e.printStackTrace();
      System.exit(1);
    }
  }


  public void timerTask()
  {
    long current_millis = System.currentTimeMillis();
    long passed_millis = current_millis - last_millis;
    last_millis = current_millis;
    time += passed_millis;
    if (!keepScene && (scene[sceneId].duration > 0)
      && ((time / 1000) > scene[sceneId].duration))
    {
      nextScene();
    }
    double fps = 0;
    if (passed_millis > 0L)
    {
      fps = 1000.0 / passed_millis;
    }

    try
    {
      Scene s = scene[sceneId];
      for (Effect e : s.effect)
      {
        e.drawImage(image);
      }

      if (saveImage)
      {
        saveImage();
        saveImage = false;
      }
      if (out != null)
      {
        image.send(out);
      }
    }
    catch (Throwable e)
    {
      System.out.println("ERROR: " + e.getMessage());
      e.printStackTrace();
      System.exit(1);
    }

    if (!nogui)
    {
      infoLabel.setText(String.format("fps=%.1f", fps));
      panelMain.repaint();
    }
  }


  private void saveImage()
  {
    utils.saveIndexedPng(image.paint(pixelSize), "text");
  }


  public void nextScene()
  {
    scene[sceneId].running(false);
    sceneId = (sceneId + 1) % scene.length;
    time = 0;
    scene[sceneId].running(true);
  }


  public void prevScene()
  {
    scene[sceneId].running(false);
    sceneId = (sceneId - 1 + scene.length) % scene.length;
    time = 0;
    scene[sceneId].running(true);
  }
  
  public void keepScene()
  {
    keepScene = !keepScene;
    System.out.println("Keep scene: " + keepScene);
  }
}

class PopClickListener extends MouseAdapter
{
  GraphicEffectMain graphicEffectMain;


  public PopClickListener(GraphicEffectMain graphicEffectMain)
  {
    this.graphicEffectMain = graphicEffectMain;
  }


  public void mousePressed(MouseEvent e)
  {
    if (e.isPopupTrigger())
    {
      doPop(e);
    }
  }


  public void mouseReleased(MouseEvent e)
  {
    if (e.isPopupTrigger())
    {
      doPop(e);
    }
  }


  private void doPop(MouseEvent e)
  {
    PopUpMenu menu = new PopUpMenu(graphicEffectMain);
    menu.show(e.getComponent(), e.getX(), e.getY());
  }
}

@SuppressWarnings("serial")
class PopUpMenu extends JPopupMenu implements ActionListener
{
  GraphicEffectMain graphicEffectMain;

  JMenuItem anItem;


  public PopUpMenu(GraphicEffectMain graphicEffectMain)
  {
    this.graphicEffectMain = graphicEffectMain;

    anItem = new JMenuItem("Load configuration");
    anItem.addActionListener(this);
    add(anItem);
    anItem = new JMenuItem("Reload");
    anItem.addActionListener(this);
    add(anItem);
  }


  @Override
  public void actionPerformed(ActionEvent event)
  {
    try
    {
      if (event.getActionCommand().equals("Load configuration"))
      {
        graphicEffectMain.init(null);
        graphicEffectMain.run();
      }
      else if (event.getActionCommand().equals("Reload"))
      {
        graphicEffectMain.init(graphicEffectMain.fileName);
        graphicEffectMain.run();
      }
    }
    catch (Throwable e)
    {
      System.out.println("ERROR: " + e.getMessage());
      e.printStackTrace();
      System.exit(1);
    }
  }
}
