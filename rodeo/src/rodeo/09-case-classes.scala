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
      2) Case classes are easier to instantiate because  the `new` keyword in unnecessary. An
      `apply` method is generated on the companion-object which internally calls `new`.
      3) Case classes allows us to use pattern matching. An `unapply` method is generated on the
      companion-object which allows destructuring the fields that were used to build an instance.
      4) A copy method is automatically added on each instance that allows an easy way to modify part of an existing
      instance.
      5) equals methods and hashCode and other serializable methods are generated automatically for each instance, which
      means they can be used as keys on HashMaps
  */


  // The following exercise is meant to explain why case classes are used to model immutable data.

  Exercise("Mentors") {

    case class PersonaA(name: String, age: Int)
    class PersonaB(name: String, age: Int)

    val codingMaster = PersonaA.apply("Vic", 40)
    val bossMaster = PersonaA.apply("David", 33)
    val mentor = new PersonaB("Fabian", 28)
    val mentor2 = new PersonaB("Mon", 29)



  }


  Exercise("Perimeter") {
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


    // The correct definition of  `getPerimeter` for these two shapes is defined inside the corresponding case class.
    // Another way to define an operation that could handle both cases is by using pattern matching.

    def getPerimeter(someShape: Shape): Float = {
      someShape match {
        case Circle(radius) => 2 * 3.1416 * radius
        case Polygon(sides) => ???

        /*
      In this case `getPerimeter` doesn't need to be included as an interface function of the Shape trait.
      The global function getPerimeter uses pattern matching to handle the possible types of the input.
      This is only possible because we defined shape as a sealed trait.
      A sealed trait can be extended only in the same file it is defined.
      This allows the compiler to check if all the cases are exhausted by our "match".
    */
      }
    }

    Exercise("Equals"){

      // To compare two instances of a given case class we only have to compare their values.
      // If two instances have they same values the equality method `==` will return true.
      // This makes case classes lightweight datastructures.

      case class ScalaBooks(name:String,level:String,numberOfPages:Int)

      val book: ScalaBooks = ScalaBooks("Functional Programming in Scala ", "Advanced", 320)

      val bookPirate: ScalaBooks = ScalaBooks("Functional Programming in Scala ", "Advanced", 320)

      val bookDummies: ScalaBooks = ScalaBooks("Scala for dummies ", "dummy", 420)


      assertTrue(book == ???)
      assertTrue(book != ???)
    }

    Exercise("case class from class") {
      /*
      In order to define an accessor method for the constructor parameters in a plain class we need to declare this
      parameters as val s.
      */
      class Persona(val name: String, val age: Int)

      /*
     If we want to instantiate this class without the `new` keyword we need to construct a companion object `Persona`
      and define an appropriate apply method within this object.
      */

      object Persona {
        def apply(name: String, age: Int): Persona = new Persona("Alex", 20)
      }

      val Alex = Persona("Alejandro", 33)

      assertTrue(Alex.name == ???)

      /*

      */

    }

    Exercise("Enumerations"){

       case class DealEngineScalaGurus(name: String, experience: Int)
       val boss : DealEngineScalaGurus = DealEngineScalaGurus("David", 10)

       // The definition of our ScalaGurus case class allows us to pass invalid constructor arguments.
       // For example a DealEngineScalaGuru with name Andrew is clearly not a valid option.
       // This example is not over.

       sealed trait Gurus
       case object David extends Gurus
       case object Vic extends Gurus
       case object Mon extends Gurus
       case object Fabian extends Gurus

       def guruYearsOfExperience(name: Gurus): Int =
         name match {
           case David => 10
           case Vic => ??? // You need to research this.
           case Mon => 4
           case Fabian => 10
         }

       assertTrue(guruYearsOfExperience(???)==4)
     }




   }




