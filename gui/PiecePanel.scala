package kierros5.gui
import scala.swing._
import scala.swing.event._
import kierros5.model._
import java.awt.Graphics2D._
import java.awt.Color

/**
 * PiecePanel on peliruudun oikealla puolella oleva pieni paneeli, jossa näkyy seuraava pala.
 * 
 * @author Katri Saarinen 530347, Roosa Laurikainen 529510
 */
class PiecePanel(piece: Piece) extends GridPanel(5, 5) {
  val square = 30  // ruudukon ruudun leveys / korkeus (neliö)
  
  def updatePiece(p: Piece): Unit = {    // päivitetään PiecePanelissa näkyvä pala, eli seuraavaksi putoava pala uudeksi
    piece.grid.getNextPiece
  }
  
  
  override def paintComponent(g: Graphics2D): Unit = {
    for (x <- 0 until 5) {
      for (y <- 1 until 6) {  // yhdestä kuuteen, jotta palikka ei ilmeistyisi aivan PiecePanelin yläreunaan
        if (piece.grid.nextPiece.thisShape(y - 1)(x)) {
        val color = piece.grid.nextPiece.shape.getColor()
        g.setColor(color)
        g.fillRect(square * x, square * y, square, square)    // Täytetään väreillä
        g.setColor(Color.darkGray)
        g.drawRect(square * x, square * y, square, square)    // Piirretään gridin ruudukko
        }
      }
    }
  }
  this.preferredSize = new Dimension(square * 5, square * 5)
  this.repaint()
  
}