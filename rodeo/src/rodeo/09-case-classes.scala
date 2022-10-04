package rodeo

import rodeo.Functions.Exercise
import zio.test.assertTrue

import scala.collection.immutable.List

/**
 *
 */
object CaseClasses {
  /*
      Scala Case Classes are good for modeling immutable data. To define a Case Class we need to specify a constructor
      parameter list. These parameters are meant to define the most significant properties we want the objects of our
      class to have. Some of the benefits of using Case Classes are the following.

      1) An accessor method is generated for each parameter in the constructor.
      2) Case classes are easier to instantiate because  the `new` keyword in unnecessary.
      3) Case classes allows us to use pattern matching.

  */

   Exercise("Area") {
    // Traits can be thought of as interfaces. They define a type but no constructor for it.
    // Constructors are defined only in derived types.
    // Case classes that inherit from a given trait must define a implementation for the methods within this trait.


    sealed trait Shape {
      def getPerimeter: Float
    }

    case class Circle(radius: Float) extends Shape {
      override def getPerimeter: Float = 2 * 3.1416 * radius
    }

    case class Polygon(sides: List[Float]) extends Shape {

      override def getPerimeter: Float = sides.sum
    }

    val circle = Circle(1)
    val square = Polygon(List(1, 1, 1, 1))


    assertTrue(circle.getPerimeter == ???)
    assertTrue(square.getPerimeter == ???)

    // The correct definition of perimeter for these two shapes is defined inside the corresponding case class.
    // Another way to define an operation that could handle both cases is by using pattern matching.

    def ShapePerimeter(someshape: Shape): Float = {
      someshape match {
        case Circle(radius) => 2 * 3.1416 * radius
        case Polygon(sides) => ???
      }

      // In this case getPerimeter doesn't need to be included as an interface function of the Shape trait.
      // The global function ShapePerimeter uses pattern matching to handle the possible types of the input.

    }

    Exercise("Equals"){

      // To compare two instances of a given case class we only have two compare there values.
      // If two instances have they same values the equality operator `==` will return true.
      // This makes case classes lightweight datastructures.

      case class ScalaBooks(name:String,level:String,numberOfPages:Int)

      val Book: ScalaBooks = ScalaBooks("Functional Programming in Scala ", "Advanced", 320)

      val BookPirate: ScalaBooks = ScalaBooks("Functional Programming in Scala ", "Advanced", 320)

      val BookDummies: ScalaBooks = ScalaBooks("Scala for dummies ", "dummy", 420)


      assertTrue(Book == ???)
      assertTrue(Book != ???)

    }

     case class DealEngineScalaGurus(name: String, experience: Int)
     val David: DealEngineScalaGurus =  DealEngineScalaGurus("David",10)

     Exercise("Enumerations") {
       // The definition of our ScalaGurus case class allows us to pass invalid constructor arguments.
       // For example a DealEngineScalaGuru with name Andrew is clearly not a valid option.

       sealed trait Gurus
       case object David extends Gurus
       case object Vic extends Gurus
       case object Mon extends Gurus
       case object Fabian extends Gurus

       def GuruExperience(name: Gurus): Int =
         name match {
           case David => 10
           case Vic => 10
           case Mon => 7
           case Fabian => 10
         }

       assertTrue(GuruExperience(???)==7)
     }




   }


