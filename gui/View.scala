package kierros5.gui
import scala.swing._
import javax.swing.Timer
import kierros5.model._
import kierros5.model.GameGrid
import javax.swing.AbstractAction
import java.awt.event._

/**
 * Mahdollistaa koodin ilmestymisen näytölle - tekee siis kaiken työn, jotta peliä voidaan pelata.
 * @author Katri & Roosa
 */
object View extends SimpleSwingApplication {
  
  val timer = new Timer(700, new AbstractAction() {    // luodaan ajastin
      def actionPerformed(e : ActionEvent) {
        if (game.fallingPiece != null) {        // jos seuraava palikka on määritelty, peli jatkuu
          if (!game.fallingPiece.move(Direction.down)) {
            game.dropNextPiece()
            text.text_=(" Score: " + game.points * 11) // jokaisesta täydestä rivistä saa 11 pistettä, sillä rivin leveys on 11
          }
          gamePanel.repaint()
          piecePanel.repaint()
        }
      }
    })
  
  val game = new GameGrid(20, 11)        // luodaan GameGrid
  val gamePanel = new GamePanel(game)    // luodaan GamePanel
  timer.start()                          // käynnistetään ajastin
  val piecePanel = new PiecePanel(game.nextPiece)    // luodaan PiecePanel
  val frame = new MainFrame {
      title = "Älytris"
  }
  
  def top = frame
    
  val container = new BoxPanel(Orientation.Horizontal)  // uusi BoxPanel, johon tulee vierekkäin GamePanel ja uusi BoxPanel
  val otherPanel = new BoxPanel(Orientation.Vertical)   // Sisältää allekkain PiecePanelin ja pistetilanteen
  val text = new Label(" Score: " + game.points * 11) 
  container.contents += gamePanel
  container.contents += otherPanel
  otherPanel.contents += piecePanel
  otherPanel.contents += text
  
  frame.peer.setSize(new Dimension(gamePanel.rows, gamePanel.columns))
  frame.contents = container
    
}