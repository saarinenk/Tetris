package kierros5.gui
import scala.swing._
import scala.swing.event._
import kierros5.model._
import java.awt.Graphics2D._
import java.awt.Color

/**
 * GamePaneliin ilmestyy peliruudukko, josta varsinainen peli näkyy.
 * @author Katri Saarinen 530347, Roosa Laurikainen 529510
 */

// testataan GamePanelin toimintaa testiohjelmalla
object GamePanel extends SimpleSwingApplication {
  
  val gamegrid = new GameGrid(20, 11)         // luodaan uusi GameGrid
  gamegrid.setShape(2, 2, L, L.getShape())    // pitäisi ilmestyä gridiin
  gamegrid.setShape(6, 11, S, S.getShape())   // pitäisi ilmestyä gridiin
  gamegrid.setShape(30, 45, Z, Z.getShape())  // ei pitäisi näkyä tai aiheuttaa virhettä
  gamegrid.setShape(8, 8, I, I.getShape())    // pitäisi ilmestyä gridiin
  
  def top = new MainFrame {
    title = "game"
    contents = new GamePanel(gamegrid)
  }
  
}

class GamePanel(grid: GameGrid) extends GridPanel(20, 11) {
   
  val square = 30      // yhden ruudukon ruudun leveys / korkeus (neliö)
  this.focusable = true
  this.requestFocus()
  
  this.listenTo(keys)  // laitetaan ohjelma "kuuntelemaan" tietokoneen näppäimiä
  
  reactions += {
    case k: KeyPressed => {
      if (this.grid.fallingPiece != null) {
        if (Key.Up == k.key) {
          grid.fallingPiece.move(Direction.up)
        } else if (Key.Down == k.key) {
          grid.fallingPiece.move(Direction.down)
        } else if (Key.Left == k.key) {
          grid.fallingPiece.move(Direction.left)
        } else if (Key.Right == k.key) {
          grid.fallingPiece.move(Direction.right)
        } else if (Key.Space == k.key) {
          grid.fallingPiece.drop()
        }
      }
      this.repaint()
    }
  }

  override def paintComponent(g: Graphics2D): Unit = {   // Piirretään ja väritetään GameGrid näytölle
    for (x <- 0 until 11) {
      for (y <- 0 until 20) {
        val color = grid.getShape(x, y).getColor()      // otetaan palikoiden oikea väri
        g.setColor(color)                            
        g.fillRect(square * x, square * y, square, square)  // täytetään palikat
        g.setColor(Color.darkGray)
        g.drawRect(square * x, square * y, square, square)  // piirretään gridin ääriviivat
      }
    }
  }
  this.preferredSize = new Dimension(square * 11, square * 20)
  this.repaint()

}
