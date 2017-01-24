package kierros5.model

/**
 * @author Katri
 */
object TestApp extends App {

  //GamePanelin toimintaa testattu GamePanel-luokassa GamePanel-objektilla / Testing the GamePanel-class

  val grid = new GameGrid(20, 11)

  println("Testataan GameGridin toimintaa.")
  println("Testi alkaa.")
  println(" ")
  println((grid.setShape(2, 2, L, L.getShape())) + " - Gridissä vasemmalla ylhäällä")
  println((grid.setShape(19, 10, S, S.getShape())) + " - Ei pitäisi mahtua - menee oikean alakulman ulkopuolelle")
  println((grid.setShape(30, 45, Z, Z.getShape())) + " - Yritetään laittaa taulukon ulkopuolelle")
  println((grid.setShape(8, 8, I, I.getShape())) + " - Mahtuu juuri ja juuri taulukkoon")
  println((grid.setShape(3, 3, S, S.getShape())) + " - Yritetään laittaa toisen palan päälle")
  println((grid.setShape(6, 11, S, S.getShape())) + " - Pitäisi mahtua I-palikan viereen")
  println(" ")
  println("Testi ohi.")

  val piece = new Piece(2, 4, I, grid) // aivan oikeassa reunassa
  piece.addToGameGrid()

  println(" ")
  println(" ")
  println("Testataan Piece-luokkaa")
  println("Testi alkaa.")
  println(" ")
  println(piece.x + ", " + piece.y)
  println("Liikutetaan palaa vasemmalle, kunnes se palauttaa 'false', eli sitä ei voi enää liikuttaa siihen suuntaan.")
  
  while (piece.move(Direction.right)) {   // siirretään vasemmalle niin kauan kun voi
    piece.move(Direction.right)
  }
  
  println("Koordinaatit nyt: " + piece.x + ", " + piece.y)
  println("Yritetään kääntää palaa - pitäisi palauttaa 'false', koska pala on kiinni seinässä, eikä voi kääntyä.")
  println(piece.move(Direction.up))
  println("Siirretään kaksi askelta vasemmalle ja yritetään uudestaan.")
  println(piece.move(Direction.left))
  println(piece.move(Direction.left))
  println("Käännetään uudestaan.")
  println(piece.move(Direction.up))
  println(" ")
  println("Testi ohi.")

}