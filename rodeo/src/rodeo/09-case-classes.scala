package rodeo

import rodeo.Functions.Exercise
import zio.test.assertTrue

import scala.collection.immutable.List

/**
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
    5) `equals` methods and `hashCode` and other serializable methods are generated automatically for each instance, which
    means they can be used as keys on HashMaps
 */



  Exercise("Mentors") {

    case class PersonaA(name: String, age: Int)
    class PersonaB(name: String, val age: Int)

    val codingMaster = PersonaA.apply("Vic", ???)
    val bossMaster = PersonaA("David", 33)
    val mentor = new PersonaB("Fabian", 28)
    val mentor2 = new PersonaB("Mon", 29)

    assertTrue(??? == 29)

  }

  Exercise("Perimeter") {
    // Traits can be thought of as interfaces. They define a type but no constructor for it.
    // Constructors are defined only in derived types.
    // Case classes that inherit from a given trait must define a implementation for the methods within this trait.

    sealed trait Shape {
      def getPerimeter: Double
    }

    case class Circle(radius: Double) extends Shape {
      override def getPerimeter: Double = 2 * 3.1416 * radius
    }

    case class Polygon(sides: List[Double]) extends Shape {
      override def getPerimeter: Double = sides.sum
    }

    val circle = Circle(1)
    val square = Polygon(List(1, 1, 1, 1))

    assertTrue(circle.getPerimeter == ???)
    assertTrue(square.getPerimeter == ???)

    // The correct definition of  `getPerimeter` for these two shapes is defined inside the corresponding case class.
    // Another way to define an operation that could handle both cases is by using pattern matching.

    def getPerimeter(someShape: Shape): Double = {
      someShape match {
        case Circle(radius) => 2 * 3.1416 * radius
        case Polygon(sides) => ???

        /*
      In this case `getPerimeter` doesn't need to be included as an interface function of the Shape trait.
      The global function getPerimeter uses pattern matching to handle the possible types of the input.
      This is only possible because we defined shape as a sealed trait.
      A sealed trait can be extended only in the same file it is defined.
      This allows the compiler to check if all the cases are exhausted.
         */
      }
    }

    Exercise("Equals") {

      // To compare two instances of a given case class we only have to compare their values.
      // If two instances have they same values the equality method `==` will return true.
      // This makes case classes lightweight datastructures.


      case class ScalaBooks(name: String, level: String, numberOfPages: Int)

      val book: ScalaBooks =
        ScalaBooks("Functional Programming in Scala ", "Advanced", 320)

      val bookPirate: ScalaBooks =
        ScalaBooks("Functional Programming in Scala ", "Advanced", 320)

      val bookDummies: ScalaBooks =
        ScalaBooks("Scala for dummies ", "dummy", 420)

      assertTrue(book == ???)
      assertTrue(book != ???)

    }

    Exercise("Instantiating classes without `new`") {
      /*
      When you instantiate a class by doing something like val Ana = Person("Ana", 34) you are implicitly thinking
      the name of the class `Person` as a constructor for the instances of this class.
      This little piece of dark magic relies on the use of an apply method defined inside the companion Object Person.
      A companion Object to a class is an Object with the same name as the class and that is defined in the same file.
      An apply method defined inside a companion Object has an special meaning for the compiler.
      When you instantiate the class Persona by doing `val Ana = Persona("Ana", 34)` the compiler reads this as
     `val Ana = Persona.apply("Ana", 34)`.
     */

      class Persona(name: String, val age: Int)
      object Persona {
        def apply(name: String, age: Int): Persona =
          ???
      }
      val ana = Persona("Ana", 24)

      assertTrue(Persona("Ana", 24) == ???) // Don't use `new`
    }

    Exercise("unapply and pattern matching") {
      /*
      In order to recover the constructor parameters used to instantiate a class we need an unapply method.
      This method takes an instance of a class and returns an Option of the parameters used to construct this instance.
      It can be thought of as "wrapped" version of the inverse of apply.
      TODO Explain in a satisfactory manner why unapply is fundamental for pattern matching.
      */

      sealed trait SolidShape {
        def getVolume: Double
      }

      class Sphere(val radius: Double) extends SolidShape {
        override def getVolume: Double = (1.33 * 3.1416 * math.pow(radius, 3))
      }

      object Sphere {
        def apply(radius: Double): Sphere = new Sphere(radius: Double)

        def unapply(someSolidShape: Sphere): Option[Double] =
          Some(someSolidShape.radius)
      }

      class SquarePyramid(val sideLength: Double, val height: Double)
        extends SolidShape {
        override def getVolume: Double = (sideLength * sideLength * height) / 3
      }

      object SquarePyramid {
        def apply(sideLength: Double, height: Double): SquarePyramid =
          new SquarePyramid(sideLength, height)

        def unapply(squarePyramid: SquarePyramid): Option[(Double, Double)] =
          Some(squarePyramid.sideLength, squarePyramid.height)

      }

      // Having defined and unapply method we can now define a global function `getVolume` using pattern matching.
      // This function will handle both cases just as before.

      def getVolume(someSolidShape: SolidShape): Double = {
        someSolidShape match {
          case Sphere(radius) => (1.33 * 3.1416 * math.pow(radius, 3))
          case SquarePyramid(sideLength, height) =>
            (sideLength * sideLength * height) / 3
        }
      }


      val pyramid = SquarePyramid(3, 7)

      assertTrue(Sphere.apply(3) == ???) // Don't use the `new` keyword.
      assertTrue(SquarePyramid.unapply(pyramid) == ???)

    }

    // Case classes are endowed with a copy method.
    // This method allows us to create a new instance of a given case class by "copying" the desired fields from another instance.

    Exercise("Copy method") {

      case class StarWarsJedi(name: String, forceGhost: Boolean)
      val ObiWan = StarWarsJedi("Obi-Wan Kenobi", false)
      val ObiWanGhost = ObiWan.copy(forceGhost = ???)
      assertTrue(ObiWanGhost.forceGhost == ???)

    }


    Exercise("Enumerations") {

      case class DealEngineScalaGuru(name: String, experience: Int)

      val boss: DealEngineScalaGuru = DealEngineScalaGuru("David", 10)

      // The definition of our ScalaGurus case class allows us to pass "invalid" constructor arguments.
      // For example, a DealEngineScalaGuru with name Andrew is clearly not a valid option.
      // In cases like this an enumeration can be useful.
      // The basic idea of an enumerations is to provide a fixed set of constants.
      // In this example we want each of this constants to represent a valid choice of an `DealEngineScalaGuru`.


      sealed trait Gurus
      object David extends Gurus
      object Vic extends Gurus
      object Fabian extends Gurus
      object Mon extends Gurus

      def guruYearsOfExperience(name: Gurus): Int =
        name match {
          case David => 10
          case Vic => ??? // You need to research this.
          case Fabian => 10
          case Mon => 4
        }


      assertTrue(guruYearsOfExperience(???) == 4)
    }

  }
}
