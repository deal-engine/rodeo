package rodeo
import zio.test._
// We can think of a class as factory that allows us to produce objects of a certain type and with a certain functionality.
// We could also say that a class is an implementation of an interface.
// The ability to implement the same interface in two different ways and achieve the same functionality is called data abstraction.
// Data abstraction is of fundamental importance in software engineering.
// TODO everything.

object Class extends Chapter {

  Exercise("Instantianting a class") {

    // To define a class we nee to specify a constructor parameter list.

    class Person(name: String, age: Int)

    // To instantiate a class we need to use the `new` constructor.

    val Ana = new Person(???, 32)

  }

  Exercise("Data abstraction") {

    // In the following excercise we give two different implementations for the complex numbers with multiplication interface.
    // Both of this implementations allow us to multiply complex numbers.

    class ComplexNumber(x: Double, y: Double) {
      def real = x

      def imaginary = y

      def multiply(z: ComplexNumber) = new ComplexNumber(
        real * z.real - imaginary * z.imaginary,
        real * z.imaginary + imaginary * z.real
      )
    }

    class ComplexNumberPolar(x: Double, y: Double) {
      def radius = x

      def angle = y

      def multiply(z: ComplexNumberPolar) =
        new ComplexNumberPolar(radius * z.radius, angle + z.angle)
    }

    val complexNumber = new ComplexNumber(1, 2)
    val complexNumberPolar = new ComplexNumberPolar(1, 45)

    assertTrue(complexNumber.real == ???)
    assertTrue(complexNumberPolar.angle == ???)

  }

  // To define the ComplexNumber class we had to implement the real and imaginary methods inside of this class.
  // We could achieve the same functionality by defining the constructor parameters with vals.
  // Doing this endowes classes with accesor methods that allows to recover the parameters that were used to instantiate a class.

  Exercise("Accesor methods") {

    class ComplexNumberWithGetters(val real: Double, val imaginary: Double) {
      def multiply(z: ComplexNumberWithGetters) = new ComplexNumberWithGetters(
        real * z.real - imaginary * z.imaginary,
        real * z.imaginary + imaginary * z.real
      )
    }
    val z = new ComplexNumberWithGetters(1, 0)
    val w = new ComplexNumberWithGetters(0, 1)

    assertTrue((z.multiply(w)).real == ???)

  }

  // A Class B can inherit from a Class A. This means that the methods that were defined in A are also abaivable in B.
  // When a Class B inherits from a Class A we say that B extends A.
  // Classes that inherit from a given class can implement methods that were not defined in the original class.

  Exercise("Inheritance") {
    // We don't have a succesor method defined within the complex numbers.
    // In the real and imaginary numbers we can define a succesor method in a natural way.

    class ComplexNumber(val real: Double, val imaginary: Double) {
      def multiply(z: ComplexNumber) = new ComplexNumber(
        real * z.real - imaginary * z.imaginary,
        real * z.imaginary + imaginary * z.real
      )

      def sum(z: ComplexNumber) =
        new ComplexNumber(real + z.real, imaginary + z.imaginary)
    }

    class RealNumbers(override val real: Double, override val imaginary: Double)
        extends ComplexNumber(real, imaginary) {
      val realUnit = new RealNumbers(0, 1)

      def successor(x: ComplexNumber): ComplexNumber = x.sum(realUnit)
    }

    class ImaginaryNumbers(
        override val real: Double,
        override val imaginary: Double
    ) extends ComplexNumber(real, imaginary) {
      val imaginaryUnit = new ImaginaryNumbers(0, ???)

      def successor(x: ComplexNumber): ComplexNumber = x.sum(???)
    }

  }

  Exercise("PositiveRealNumbers") {
    class ComplexNumber(val real: Double, val imaginary: Double) {
      def multiply(z: ComplexNumber) = new ComplexNumber(
        real * z.real - imaginary * z.imaginary,
        real * z.imaginary + imaginary * z.real
      )

      def sum(z: ComplexNumber) =
        new ComplexNumber(real + z.real, imaginary + z.imaginary)
    }
    // If we want to impose a certain conditions on the instances of a certain class, we can do this by using the requiere function.

    class PositiveRealNumbers(override val real: Double)
        extends ComplexNumber(real, 0) {
      require(real > ???, "real should be a positive number")
    }
  }

  // An abstract class is similar to an interface but it might specify constructor parameter arguments.
  // Another important difference is that a class can only inherit from a single abstract class but could inherit from
  // multiple "interfaces"(traits). We will return to this point later.

  Exercise("Abstract classes") {

    abstract class MathematicalObject(isBeautiful: Boolean = true) {
      def howBeautiful: String
    }

    class GeometricalObject(dimension: Int) extends MathematicalObject {
      override def howBeautiful: String = {
        if (dimension < 3) "Boring, a complete classification already exists"
        else if (dimension == 3) "Extremely beautiful"
        else "Beautiful"
      }
    }

    class TuringMachine(halts: Boolean) extends MathematicalObject {
      override def howBeautiful: String =
        if (halts) "Very beautiful, we have al algorithm that terminates!"
        else "It depends what you like, we are in an infinit loop my friend"
    }

    assertTrue(??? == "Beautiful")

  }
}
