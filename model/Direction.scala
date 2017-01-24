package kierros5.model

/**
 * Direction kuvaa neljää eri suuntaa, joiden avulla palikkaa liikutetaan / käännetään
 * 
 * @author Katri Saarinen 530347, Roosa Laurikainen 529510
 */
object Direction extends Enumeration {
  type Direction = Value
  val up, down, right, left = Value
  
  def getXChange(x: Direction.Value) : Int = {   //Xn arvo muuttuu, jos suunta on oikealle tai vasemmalle
    if (x == up || x == down) 0
    else if (x == right) 1
    else -1
  }
  
  def getYChange(y: Direction.Value) : Int = {    //Yn arvo muuttuu, jos suunta on ylös tai alas
    if (y == right || y == left) 0
    else if (y == up) -1
    else 1
  }
}