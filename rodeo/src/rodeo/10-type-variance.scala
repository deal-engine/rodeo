package rodeo

import zio.test._
object TypeVariance extends Chapter {

  /* In general terms, the mantra to understand variance is the following:
      - If you produce a type T, you are covariant (+T)
      - If you consume a type T, you are contravariant (-T)
      - If you consume and produce a type T, you are invariant (T)
   */

  trait RealNumber extends Double
  trait IrrationalNumber extends RealNumber
  trait FractionalNumber extends RealNumber
  trait NaturalNumber extends FractionalNumber

  implicitly[FractionalNumber <:< RealNumber]
  implicitly[NaturalNumber <:< RealNumber]
  implicitly[NaturalNumber <:< FractionalNumber]

  Exercise("Covariance") {
    trait Source[+A] {
      def generate: A
    }

    implicitly[Source[FractionalNumber] <:< Source[RealNumber]]
    implicitly[Source[NaturalNumber] <:< Source[RealNumber]]

    val naturalNumberGenerator: Source[NaturalNumber] = ???
    val fractionalNumberGenerator: Source[FractionalNumber] = naturalNumberGenerator
    val realNumberGeneratorA: Source[RealNumber] = naturalNumberGenerator
    val realNumberGeneratorB: Source[RealNumber] = fractionalNumberGenerator



    def incrementReal(number: RealNumber): RealNumber = ???

    val realNumberA: RealNumber = incrementReal(realNumberGeneratorA.generate)
    val realNumberB: RealNumber = incrementReal(naturalNumberGenerator.generate)

    def incrementNatural(number: NaturalNumber): NaturalNumber = ???

//    val realNumberA = incrementNatural(realNumberGenerator.generate)
    val naturalNumberB: NaturalNumber = incrementNatural(naturalNumberGenerator.generate)
    val naturalNumberC: RealNumber = incrementNatural(naturalNumberGenerator.generate)

    def incrementGeneric[A <: RealNumber](number: A): A = ???

    val genericNumberA: RealNumber = incrementGeneric(realNumberGeneratorA.generate)
    val genericNumberB: NaturalNumber = incrementGeneric(naturalNumberGenerator.generate)
    val genericNumberC: RealNumber = incrementGeneric(naturalNumberGenerator.generate)

    assertTrue(true)
  }

  Exercise("Contravariance") {
    trait Sink[-A] {
      def consume(a: A): Unit
    }

    implicitly[Sink[RealNumber] <:< Sink[NaturalNumber]]

    val naturalNumber: NaturalNumber = ???
    val realNumber: RealNumber = naturalNumber

    val realNumberConsumer: Sink[RealNumber] = ???
    val naturalNumberConsumer: Sink[NaturalNumber] = realNumberConsumer

    // TODO
    trait Validator[-A] {
      def validate(a: A): Boolean
    }

    assertTrue(true)
  }

  Exercise("Invariance") {
    trait Pipe[A] {
      def transform(a: A): A
    }

    assertTrue(true)
  }

  Exercise("Mix of everything") {
    trait Stream[-A, B, +C] {
      def consume(a: A): Unit
      def generate: C
      def transform(b: B): B

    }

    assertTrue(true)
  }

//  sealed trait Vampiro
//  sealed trait Ave
//  sealed trait Patito extends Ave
//  sealed trait Humano
//
//  // PatitoVampiro <: Vampiro
//  // PatitoVampiro <: Patito
//  type PatitoVampiro = Patito with Vampiro
//  type HumanoVampiro = Humano with Vampiro
//
//  val patolin: PatitoVampiro = new Patito with Vampiro {}
//  val dracula: HumanoVampiro = new Humano with Vampiro {}
//
//  val todosLosVampiros: Seq[Vampiro] = Seq(patolin, dracula)
//
//  trait ListaInvariante[A]
//  val aInvariante: ListaInvariante[Vampiro] = ???
//  val bInvariante: ListaInvariante[Vampiro with Patito] = ???
//  def usaListaInvariante(l: ListaInvariante[Vampiro]): Unit = ???
//  usaListaInvariante(aInvariante) // OK: L[V] <: L[V]
//  usaListaInvariante(bInvariante) // KO: LI[V & P] <: LI[V]
//
//  implicitly[ListaInvariante[Vampiro] <:< ListaInvariante[Vampiro]]
////  implicitly[ListaInvariante[Vampiro with Patito] <:< ListaInvariante[Vampiro]]
//
//  trait ListaCovariante[+A]
//  val aCovariante: ListaCovariante[Vampiro] = ???
//  val bCovariante: ListaCovariante[Vampiro with Patito] = ???
//  def usaListaCovariante(l: ListaCovariante[Vampiro]): Unit = ???
//  usaListaCovariante(aCovariante) // OK: L+[V] <: L+[V]
//  usaListaCovariante(bCovariante) // OK: L+[V & P] <: L+[V]
//
//  implicitly[ListaCovariante[Vampiro] <:< ListaCovariante[Vampiro]]
//  implicitly[ListaCovariante[Vampiro with Patito] <:< ListaCovariante[Vampiro]]
//
//  trait ListaContravariante[-A]
//  val aContravariante: ListaContravariante[Ave] = ???
//  val bContravariante: ListaContravariante[Patito] = ???
//  def usaListaContravariante(l: ListaContravariante[Patito]): Unit = ???
//  usaListaContravariante(aContravariante) // OK: L-[V] <: L-[V]
//  usaListaContravariante(bContravariante) // KO: L-[V & P] <: L-[V]
//
//  implicitly[ListaContravariante[Vampiro] <:< ListaContravariante[Vampiro]]
//  //  implicitly[ListaContravariante[Vampiro with Patito] <:< ListaContravariante[Vampiro]]
////  implicitly[ListaContravariante[Patito with Ave] <:< ListaContravariante[Ave]]
//  implicitly[ListaContravariante[Ave] <:< ListaContravariante[Patito]]
//  implicitly[ListaContravariante[Any] <:< ListaContravariante[Ave]]
//
////  implicit val rompe: Ave => Patito = ???
//
//  implicitly[Patito <:< Ave]
//  implicitly[Ave <:< Patito]
//
//  trait EtiquetaTicketNum
//  type TicketNum = String with EtiquetaTicketNum
//  val x: Function1[String, Int] = ???
//  val y: Function1[TicketNum, Int] = ???
//
////  implicitly[Int <:< String]
//
////
////
////  val listaDeVampiros: ListaInvariante[Vampiro] = ListaInvariante(patolin, dracula)
//
//  // 1) Los tipos de argumentos de las funciones son Contravariantes.
//  //
//  // Contravarianza: UnTipoMasEspecifico => UnTipoMasGenerico
//  // Todos los tipos de los argumentos de las funciones.
//  // son contravariantes. Es decir, cualquier
//  // subtipo de vampiro se utiliza en esta funcion como un vampiro generico.
//  def alimentar(vampiro: Vampiro, sangre: String): Boolean = ???
//
//  alimentar(patolin, "A+") // Patito with Vampiro  --> Vampiro
//  alimentar(dracula, "A-")
//
//  def morder(victima: String): Vampiro =
//    victima match {
//      case "Blad"    => dracula
//      case "Patolin" => patolin
//    }
//
//  // 2) Los tipos de retorno de las funciones son Covariantes
//  def quienTeMordio(victima: String): PatitoVampiro =
//    victima match {
//      case "Blad"    => ???
//      case "Patolin" => patolin
//    }
//
//  println("HOLA")
//
}
