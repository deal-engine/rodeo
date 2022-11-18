import zio._
import zio.Console._

type somethingSomething = ???

/* Parser combinators in Scala
 *
 * This is an introductory material to Parsers Combinators in Scala.
 * We're assuming you already have some experience with Functional
 * Programming idiosyncrasies; this is important since Parsers 
 * Combinators rely heavily on those concepts.
 *
 * Unlike Rodeo, this guide is using Scala 3 with Cats, but that should
 * not be an issue.
 */

object VeryBasicParser {
  /* Unlike hoomans, computers speak with data structures and not
   * with words (directly). But we often want computers to understand
   * some data.
   *
   * Hoomans have languages: may it be English, Spanish or a different
   * one. Those languages are constructed from a grammar which defines
   * the "correct syntax" of the language. But hooman languages are
   * constantly changing and don't really have an specific way of
   * wording things.
   *
   * Languages are complex, specially those used by hoomans. So we
   * should restrict (for now) ourselves to those that can be defined
   * by a formal grammar. An example of languages defined by a formal
   * grammar are programming languages (like Haskell and Scala) or
   * some structured language for data (like JSON or YAML). That doesn't
   * mean that we cannot extract information from spoken languages,
   * but that's out if the scope of this guide (but if you're
   * interested, the field of study is called NLP).
   *
   * Let's pretend that we have a String of character from which we
   * want to obtain some data. We know that our string has some
   * information, a number, and we want to obtain some data type
   * from it (an Integer). We can build a function for it which
   * is going to have a signature like the following:
   */
  
  type ParserIntNaive = String => Int

  val someParser0: ParserIntNaive = somethingSomething

  /* A parser with this type should consume a String and return an
   * Integer. But there is a problem with that: the provided string may
   * not satisfy our specification. We need some error handling for
   * that, maybe using an Option:
   */

  type ParserIntOption = String => Option[Int]

  /* That's is already a great improvement, but we can do a lot better.
   * Option cannot tell why our Parser failed at some point, it can
   * only specify that the parsing was unsuccessful. To solve this we
   * can use (and abuse) of Either:
   */

  type ParserIntEither[E] = String => Either[E, Int]

  /* As you can see, we're choosing to represent out error as a
   * polymorphic value (a generic), which give us a lot of options
   * to correctly handle an error.
   */

  /* But that Int was just an example. Int the real world we'll want
   * to parse more complicated data like some person information.
   * So our result is going to be different according to our parsing
   * need. So let's reflect that in our types:
   */

  type ParserEither[E,A] = String => Either[E, A]

  /* Now there is an important thing to know about Parser Combinators.
   * As its name suggests, Parser Combinators can be "combined" to
   * construct new (and bigger) parsers. Maybe we have a string with
   * the name and age of some person; we can combine a parser for the
   * name and a parser for the age in order to get a parser for that
   * information. So our first parser (the name) it is consuming
   * some part of our input and we expect the second parser to
   * consume the rest. Our second parser needs to know where to start
   * reading data from the stream in order to generate a value.
   *
   * The way we do this is having a value which represent our current
   * input, that is our input which hasn't been consumed.
   */

  type ParserConsume[E,A] = String => Either[E, (A, String)]

  /* So if our result has been correctly parsed, we are going to
   * return the parsed data into the expected type and the rest of
   * the stream so it can be consumed by other parsers. The fact
   * that a parser cannot continue parsing data doesn't mean that
   * that sould be an error, usually we want to parse until it's
   * possible to do so.
   */

  /* We're almost there, just one more thing: our input.
   * Until now we have been assuming that our input is just a String,
   * but that is not always going to be the case. We should
   * considerate edge cases in which our input is going to be of
   * a different type for some reason. We are going to call this
   * input as our "input stream" from which we are going to be
   * reading data:
   */

  type Parser[S,E,A] = S => Either[E, (A, S)]

  /* So that's it. A parser is going to be just a function that
   * consumes some strean and either returns an error or returns the
   * expected result (the expected type) with rest of the stream.
   */
}

object MyApp extends ZIOAppDefault:

  def run = myAppLogic

  val myAppLogic =
    for
      _    <- printLine("Hello! What is your name?")
      name <- readLine
      _    <- printLine(s"Hello, ${name}, welcome to ZIO!")
    yield ()

