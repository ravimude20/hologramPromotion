package hologram;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GifViaSwing {

  public static void main(String[] args) {
    new GifViaSwing();
  }

  public GifViaSwing() {
    EventQueue.invokeLater(() -> {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        ex.printStackTrace();
      }

      JFrame frame = new JFrame("Testing");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(new TestPane());
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
    });
  }

  public class TestPane extends JPanel {

    private BufferedImage master;
    private BufferedImage rotated;

    public TestPane() {
      try {
        master = ImageIO.read(new File("/Users/1023556/Desktop/coca-cola.png"));
        rotated = Util.rotateImageByDegrees(master, 0.0);
      } catch (IOException ex) {
        ex.printStackTrace();
      }

      Timer timer = new Timer(40, new ActionListener() {
        private double angle = 0;
        private double delta = 2.0;

        @Override
        public void actionPerformed(ActionEvent e) {
          angle += delta;
          rotated = Util.rotateImageByDegrees(master, angle);
          repaint();
        }
      });
      timer.start();
    }

    @Override
    public Dimension getPreferredSize() {
      return master == null
              ? new Dimension(200, 200)
              : new Dimension(master.getWidth(), master.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (rotated != null) {
        Graphics2D g2d = (Graphics2D) g.create();
        int x = (getWidth() - rotated.getWidth()) / 2;
        int y = (getHeight() - rotated.getHeight()) / 2;
        g2d.drawImage(rotated, x, y, this);
        g2d.dispose();
      }
    }
  }

}

