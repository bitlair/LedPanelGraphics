package graphiceffect.main;

import graphiceffect.xml.effect;
import graphiceffect.xml.scene;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Scene
{
  int duration;

  Effect[] effect;


  public Scene(scene scene, int width, int height) throws Exception
  {
    duration = scene.getDuration();

    effect[] xmlEffect = scene.getEffect();
    effect = new Effect[xmlEffect.length];
    for (int i = 0; i < xmlEffect.length; i++)
    {
      effect[i] = Effect.getEffect(xmlEffect[i], width, height, this);
    }
  }


  public void running(boolean running)
  {
    for (Effect e : effect)
    {
      e.running(running);
    }
  }


  ArrayList<KeyListener> keyListener = new ArrayList<Scene.KeyListener>();


  public void keyTyped(KeyEvent event)
  {
    for (KeyListener kl : keyListener)
    {
      kl.keyTyped(event);
    }
  }


  public interface KeyListener
  {
    public void keyTyped(KeyEvent event);
  }


  public void addKeyListener(KeyListener keyListener)
  {
    this.keyListener.add(keyListener);
  }
}
