package kierros5.model
import scala.collection.mutable.Buffer

/**
 * @author Katri & Roosa
 */
class GameGrid(height: Int, width: Int) {
  private var grid: Array[Array[Shape]] = Array.fill(height, width)(` `)    // kuvaa peliruudukkoa
  var nextPiece = getRandomPiece()    // seuraavaksi putoava pala
  var fallingPiece = getRandomPiece()  // tämänhetkinen putoava pala
  var points: Int = 0      // pitää kirjaa pisteistä (11p / täysi rivi)
  
  def getFallingPiece(): Piece = fallingPiece
  def getRandomPiece(): Piece = new Piece(3, 0, Shape.getRandom(), this)
  def getNextPiece: Piece = nextPiece

  
  /**
   * Shape (5*5) sijoitetaan aluksi aina gridin pisteeseen (3, 0), gridin leveys 11
   * Tätä metodia käytetään monessa muussa metodissa testaamaan, voiko palaa sijoittaa haluttuun kohtaan
   */
  def canBePlaced(x: Int, y: Int, shape: Shape, shapeGrid: List[List[Boolean]]): Boolean = { 
    var counter: Int = 0
    var booleanCounter = 0
    for (shapeX <- 0 until 5) {
      for (shapeY <- 0 until 5) {
        if (shapeGrid(shapeY)(shapeX)) {
          booleanCounter += 1
          if ((x + shapeX) < this.width && (x + shapeX) >= 0) {
            if ((y + shapeY) < this.height && (y + shapeY) >= 0) {
              if (grid(y + shapeY)(x + shapeX) == ` `) {
                counter += 1
              }
            }
          }
        }
      }
    }
    counter == booleanCounter    // Jos shapen gridin sisältämien true arvojen määrä (booleanCounter) on sama, kuin 
                                 // tyhjien määrä peligridissä (counter), palautetaan true
  }


  def getShape(x: Int, y: Int): Shape = {      // Palauttaa halutussa koordinaatissa olevan palan
    grid(y)(x)
  }

  /** 
   *  laitetaan shape gridiin haluttuun koordinaattiin
   */
  def setShape(x: Int , y: Int, shape: Shape, shapeGrid: List[List[Boolean]]) = {
    if (canBePlaced(x, y, shape, shapeGrid)) {
      for (x2 <- 0 to 4) {
        for (y2 <- 0 to 4) {
          if (shapeGrid(y2)(x2)) {
            grid(y + y2)(x + x2) = shape
          }
        }
      }
      true
    } else false
  }

  /** 
   *  poistetaan shape gridistä halutusta koordinaatista
   */
  def removeShape(x: Int, y: Int, shape: Shape, shapeGrid: List[List[Boolean]]): Boolean = {
      for (x2 <- 0 to 4) {
        for (y2 <- 0 to 4) {
          if (shapeGrid(y2)(x2)) {
            this.grid(y + y2)(x + x2) = ` `
          }
        }
      }
      true
  }

  
  /**
   * Poistaa täydet rivit ja lisää saman verran tyhjiä tilalle pelilaudan alkuun
   */
  def removeRows: Unit = {
    var temporaryGrid = Buffer[Array[Shape]]()
    var newGrid = Buffer[Array[Shape]]()

    for (every <- 0 until this.height) {
      if (!grid(every).forall(n => n != ` `)) {
        temporaryGrid += grid(every)
      }
    }
    points += (this.grid.length - temporaryGrid.length)
    
    while (newGrid.length < (this.grid.length - temporaryGrid.length)) {
      newGrid += Array.fill(this.width)(` `)
    }
    for (current <- 0 until temporaryGrid.length) {
      newGrid += temporaryGrid(current)
    }
    grid = newGrid.toArray
  }
  
  /**
   *  Poistaa täydet rivit ja sen jälkeen jos uusi palikka (entinen next piece) on mahdollista sijoittaa gridin yläreunaan, 
   *  se sijoitetaan sinne. Täten falling piece on entinen next piece ja next piece valitaan randomilla.
   *  Jos uusi palikka ei mahdu, falling piecestä tulee null, jolloin peli loppuu.
   */
  def dropNextPiece(): Unit = {
    
    removeRows
    
    if (canBePlaced(nextPiece.x, nextPiece.y, this.nextPiece.shape, this.nextPiece.shape.getShape())) {
      fallingPiece = nextPiece
      nextPiece = getRandomPiece
      fallingPiece.addToGameGrid()
    } else {
      fallingPiece = null
    }

  }

}