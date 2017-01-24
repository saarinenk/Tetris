package kierros5.model
import kierros5.model._

/**
 * Kuvaa yhtä pelin palikkaa, jolla on muoto (shape). Palikkaa voidaan liikuttaa ja kääntää. 
 * @author Katri & Roosa
 */

class Piece(var x: Int, var y: Int, val shape: Shape, val grid: GameGrid) {
  var thisShape: List[List[Boolean]] = shape.getShape()

  def addToGameGrid(): Unit = {    // Lisää palikan pelin gridiin
    this.grid.setShape(3, 0, this.shape, thisShape)
  }

  def removePiece = {    // Poistaa palikan pelin gridistä
    this.grid.removeShape(x, y, shape, thisShape)
  }

  def move(direction: Direction.Value): Boolean = {      // Liikuttaa tai kääntää palikkaa
    this.grid.removeShape(this.x, this.y, this.shape, thisShape)

    if (direction == Direction.up) {    // Jos näppäin on ylös, palikka kääntyy
        val newShape = thisShape.transpose.map(_.reverse)
        if (grid.canBePlaced(x, y, shape, newShape) && !(shape == O)) {
          thisShape = newShape
          grid.setShape(x, y, shape, thisShape)
          true
        } else {
          grid.setShape(x, y, shape, thisShape)
          false
        }
      

    } else if (direction == Direction.right) {    // Jos näppäin on oikealle, palikka liikkuu oikealle

      if (grid.canBePlaced(x + 1, y, shape, thisShape)) {
        x += 1
        grid.setShape(x, y, shape, thisShape)
        true

      } else {
        grid.setShape(x, y, shape, thisShape)
        false
      }

    } else if (direction == Direction.down) {    // Jos näppäin on alas, palikka liikkuu alaspäin vähän nopeammin kuin normaalisti

      if (grid.canBePlaced(x, y + 1, shape, thisShape)) {
        y += 1
        grid.setShape(x, y, shape, thisShape)
        true
      } else {
        grid.setShape(x, y, shape, thisShape)
        false
      }

    } else {    // Jos näppäin on vasemmalle, palikka liikkuu vasemmalle

      if (grid.canBePlaced(x - 1, y, shape, thisShape)) {
        x -= 1
        grid.setShape(x, y, shape, thisShape)
        true

      } else {
        grid.setShape(x, y, shape, thisShape)
        false
      }
    }
  }

  
  // pudottaa palikan välittömästi pelilaudan pohjalle, pohjalla jo mahdollisesti olevien palojen päälle.
  def drop(): Boolean = {
    grid.removeShape(x, y, shape, thisShape)
    while (grid.canBePlaced(x, y + 1, shape, thisShape)) {
      y += 1
    }
    grid.setShape(x, y, shape, thisShape)
    true
  }

}