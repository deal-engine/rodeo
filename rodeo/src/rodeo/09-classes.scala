package rodeo
import zio.test._
// We can think of a class as factory that allows us to produce objects of a certain type and with a certain functionality.
// We could also say that a class is an implementation of an interface.
// The ability to implement the same interface in two different ways and achieve the same functionality is called data abstraction.
// Data abstraction is of fundamental importance in software engineering.
// It enables code reuse, modularization and extensibility.

object Class extends Chapter {

  Exercise("Instantianting a class") {

    // To define a class we nee to specify a constructor parameter list.
    class Person(name: String, age: Int)

    // To instantiate a class we need to use the `new` constructor.
    // Exercise: Write the correct name.
    val ana = new Person(???, 32)

    assertTrue(true)
  }

  Exercise("Data abstraction") {
    // In the following exercise we give two different implementations for the animal and color interface.
    // Both of this implementations allow us to match animals with their corresponding colors.

    class Animal(name: String, color: String) {
      def getName = name

      def getColor = color
    }

    class AnimalColor(name: String, color: String) {
      def getName = name

      def getColor = color

      def matchColors(z: AnimalColor): Boolean =
        getColor == z.getColor
    }

    val animal = new Animal("Dog", "Brown")
    val animalColor = new AnimalColor("Cat", "Orange")

    assertTrue(animal.getName == ???)
    assertTrue(animalColor.getColor == ???)
  }

  Exercise("Accesor methods") {

    // To define the Animal class we had to implement the getName and getColor methods inside of this class.
    // We could achieve the same functionality by defining the constructor parameters with vals.
    // Doing this endows classes with accessor methods that allow us to recover the parameters that were used to instantiate a class.

    class AnimalWithGetters(val name: String, val furColor: String) {
      def matchAnimals(z: AnimalWithGetters) = new AnimalWithGetters(
        name + " and " + z.name,
        furColor + " and " + z.furColor
      )
    }
    val dog = new AnimalWithGetters("dog", "brown")
    val cat = new AnimalWithGetters("cat", "orange")

    assertTrue((dog.matchAnimals(cat)).name == ???)
  }

  // A Class B can inherit from a Class A. This means that the methods that were defined in A are also abaivable in B.
  // When a Class B inherits from a Class A we say that B extends A.
  // Classes that inherit from a given class can implement methods that were not defined in the original class.

  Exercise("Inheritance") {
    // In object-oriented programming, inheritance allows us to define a new class that inherits properties and methods from an existing class.
    // This is useful for creating classes that are closely related to existing classes, while still maintaining code reuse and modularization.

    // Let's say we have a base class Animal
    class Animal(val name: String, val numberOfLegs: Int) {
      def makeNoise(): String = "some noise"
    }

    // Now we want to create classes for specific animals that inherit from Animal
    class Dog(name: String) extends Animal(name, 4) {
      override def makeNoise(): String = "woof"
    }

    class Cat(name: String) extends Animal(name, 4) {
      override def makeNoise(): String = "meow"
    }

    class Duck(name: String) extends Animal(name, 2) {
      override def makeNoise(): String = "quack"
    }

    val dog = new Dog("Fido")
    val cat = new Cat("Whiskers")
    val duck = new Duck("Donald")

    // Check that the inherited properties and methods are correct
    assertTrue(dog.name == ???)
    assertTrue(dog.numberOfLegs == 0)
    assertTrue(dog.makeNoise() == ???)

    assertTrue(cat.name == ???)
    assertTrue(cat.numberOfLegs == 0)
    assertTrue(cat.makeNoise() == ???)

    assertTrue(duck.name == ???)
    assertTrue(duck.numberOfLegs == 0)
    assertTrue(duck.makeNoise() == ???)
  }

  // An abstract class is similar to an interface but it might specify constructor parameter arguments.
  // Another important difference is that a class can only inherit from a single abstract class but could inherit from
  // multiple "interfaces" (traits). We will return to this point later in Rodeo.

  Exercise("Abstract classes") {

    abstract class Food(isDelicious: Boolean = true) {
      def howDelicious: String
    }

    class Fruit(color: String) extends Food {
      override def howDelicious: String = {
        if (color == "green") "Not sweet"
        else if (color == "yellow" || color == "orange") "Sweet"
        else "Extremely sweet"
      }
    }

    class Vegetable(typeOf: String) extends Food {
      override def howDelicious: String =
        if (typeOf == "leafy") "Delicious, healthy greens"
        else "It depends on your preference, it may be bitter"
    }

    assertTrue("Extremely sweet" == ???)
  }
}
