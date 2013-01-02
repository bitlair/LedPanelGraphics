package graphiceffect.effect;

import java.awt.Color;

import graphiceffect.main.Effect;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;

public class Life extends Effect
{
  // whether or not the simulation is paused
  boolean paused = false;

  // how many loop ticks before drawing again
  int drawDelay = 1;

  int[][] cells1;

  int[][] cells2;

  int[][] nextGen1;

  int[][] nextGen2;

  int count = 10000;

  int[] gun = new int[]
  {
    0, 0x30, 0x30, 0, 0, 0, 0, 0, 0, 0, 0, 0x38, 0x44, 0x82, 0x82, 0x10,
    0x44, 0x38, 0x10, 0, 0, 0xe0, 0xe0, 0x110, 0, 0x318, 0, 0, 0, 0, 0, 0,
    0, 0, 0, 0xc0, 0xc0
  };


  @Override
  public void drawImage(Image image)
  {
    count++;
    if (count > 600)
    {
      count = 0;
      for (int i = 0; i < 1000; i++)
      {
        int x = (int) (width * Math.random());
        int y = (int) (height * Math.random());
        cells1[x][y] = 1;
        cells2[x][y] = 1;
      }
      for (int i = 0; i < 10; i++)
      {
        int x = (int) (width * Math.random());
        int y = (int) (height * Math.random());
        cells1[x][y] = 1;
      }
      for (int i = 0; i < 10; i++)
      {
        int x = (int) (width * Math.random());
        int y = (int) (height * Math.random());
        cells2[x][y] = 1;
      }
    }
    draw(image);
  }


  @Override
  protected void init()
  {
  }


  @Override
  protected void setXml(effect xml) throws Exception
  {
    cells1 = new int[width][height];
    cells2 = new int[width][height];
    // initialize a 2D array of cells
    for (int x = 0; x < width; x += 1)
    {
      for (int y = 0; y < height; y += 1)
      {
        cells1[x][y] = 0;
        cells2[x][y] = 0;
      }
    }
    // initialize a 2D array of cells for the next generation
    nextGen1 = new int[width][height];
    nextGen2 = new int[width][height];
    for (int x = 0; x < width; x += 1)
    {
      for (int y = 0; y < height; y += 1)
      {
        nextGen1[x][y] = 0;
        nextGen2[x][y] = 0;
      }
    }

    int xx = 1;
    if (xx == 0)
    {
      // initialize a "glider". Try picking different cells
      // and see what happens!
      cells1[10][10] = 1;
      cells1[11][10] = 1;
      cells1[9][10] = 1;
      cells1[11][9] = 1;
      cells1[10][8] = 1;
      for (int i = 0; i < 1000; i++)
      {
        int x = (int) (width * Math.random());
        int y = (int) (height * Math.random());
        cells1[x][y] = 1;
      }
    }
    else
    {
      for (int i = 0; i < gun.length; i++)
      {
        for (int j = 0; j < 10; j++)
        {
          if (((0x1 << j) & gun[i]) > 0)
          {
            cells1[i + 3][height - 12 + j] = 1;
          }
        }
      }
    }
  }


  // this function determines whether or not a cell lives on
  // to the next generation
  boolean livesOn(int[][] cells, int x, int y)
  {
    // first count the number of live neighbors
    int numNeighbors = 0;
    for (int i = -1; i <= 1; i += 1)
    {
      for (int j = -1; j <= 1; j += 1)
      {
        int neighborX = (x + i + width) % width;
        int neighborY = (y + j + height) % height;

        if (neighborX != x || neighborY != y)
        {
          if (cells[neighborX][neighborY] == 1)
          {
            numNeighbors += 1;
          }
        }

      }
    }
    // if the cell is living and has 2 or 3 live neighbors...
    if (cells[x][y] == 1 && (numNeighbors == 2 || numNeighbors == 3))
    {
      return true;
    }
    // if the cell is dead and has exactly 3 neighbors...
    if (cells[x][y] == 0 && numNeighbors == 3)
    {
      return true;
    }
    // otherwise it's either overpopulated or underpopulated
    // and the cell is dead
    return false;
  };


  void nextGeneration(Image image)
  {
    for (int x = 0; x < width; x += 1)
    {
      for (int y = 0; y < height; y += 1)
      {
        // set color and draw
        if ((cells1[x][y] == 1) && (cells2[x][y] == 1))
        {
          image.setPixel(x, y, Color.yellow);
        }
        else if (cells1[x][y] == 1)
        {
          image.setPixel(x, y, Color.red);
        }
        else if (cells2[x][y] == 1)
        {
          image.setPixel(x, y, Color.green);
        }
        else
        {
          // image.setPixel(x, y, new Color(255, 255, 255));
        }
        // build next generation array
        if (livesOn(cells1, x, y))
        {
          nextGen1[x][y] = 1;
        }
        else
        {
          nextGen1[x][y] = 0;
        }
        if (livesOn(cells2, x, y))
        {
          nextGen2[x][y] = 1;
        }
        else
        {
          nextGen2[x][y] = 0;
        }
      }
    }
    // copy next generation into current generation array
    for (int i = 0; i < width; i += 1)
    {
      for (int j = 0; j < height; j += 1)
      {
        cells1[i][j] = nextGen1[i][j];
        cells2[i][j] = nextGen2[i][j];
      }
    }
  }

  // draw loop!
  int t = 0;


  void draw(Image image)
  {
    // to keep the animation from going too fast, only
    // draw after the specified delay
    if (t == drawDelay)
    {
      nextGeneration(image);
      t = 0;
    }
    // only increment t if we are not paused
    if (!paused)
    {
      t += 1;
    }
  }

  // //add a live cell when you click on it
  // void mouseClicked() {
  // int x = floor(mouseX / squareSize);
  // int var y = floor(mouseY / squareSize);
  // cells[x][y] = 1;
  //
  // // draw the new cell
  // fill(199, 0, 209);
  // rect(x * squareSize, y * squareSize,
  // squareSize, squareSize);
  // };
  //
  // //do the same thing when you click and drag
  // int mouseDragged() {
  // int x = floor(mouseX / squareSize);
  // int y = floor(mouseY / squareSize);
  // cells[x][y] = 1;
  //
  // // draw the new cell
  // fill(199, 0, 209);
  // rect(x * squareSize, y * squareSize,
  // squareSize, squareSize);
  // };
  //
  // void keyPressed() {
  // // press 'p' to pause!
  // if (keyCode === 80) {
  // paused = !paused;
  // }
}
