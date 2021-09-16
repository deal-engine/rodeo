package rodeo

case class Horse(name:String, age: Int, breed:String,rider:Option[Cowboy])
case class Cowboy(name: String, age: Int)

object Options{

  /*
  *This will create a horse without a rider, keep in mind that this horse does not have a rider yet so we are setting it
  * to None in the Option value.
   */
  val horse: Horse = Horse("Chispita",1,"Pinto",None)

  val rider: Cowboy = Cowboy("Li Haoyi",30)

  /*
  * With the previous val's create a horse with a rider.
  */
  val horseWithRider: Horse = ???
}