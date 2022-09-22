package rodeo

object TypeVariance extends App {

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
//  // Si un tipo +T se produce es covariante (eg como ret de funciones)
//  // Si un tipo -T se consume es contravariante (eg como arg de funciones)
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
