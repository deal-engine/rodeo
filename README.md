Scala Rodeo
===========

Welcome to Deal Engine's Scala Rodeo.

This material is intended for you to learn the basic of scala and some
common functional idioms.

Along the way you will find some Exercises, requiring you to change some
code in order to practice some concepts.

Installation
------------

    git clone https://github.com/deal-engine/rodeo.git
    cd rodeo
    nix develop

Instructions
------------

Make sure you are in a nix shell by running `nix develop` at least once,
and then run this in your terminal to see your progress.

    mill -w rodeo.test

Rodeos
-----

- [ ] `Seq`
- [ ] pattern matching `match`, anonymous functions, `for`
- [ ] objects / trait / case class
- [ ] `Future`
- [ ] OOO Programming → Functional Programming
- [ ] `for` vs `map`
- [ ] `while` vs `foreach`
- [ ] mutability vs immutability
- [ ] Data transformations
    - [ ] Map → Seq()
    - [ ] LocalDateTime → LocalDate
- [ ] `map`/`flatMap` - differences between `Option`/`Seq`/`Try`
- [ ] Best practices (wart remover)
    - [ ] `.get`
    - [ ] `unsafeRun`
    - [ ] `Await.result`
- [ ] implicits
- [ ] Dependency Injections
- [ ] ZIO
- [ ] abstract class / sealed trait / class / final case class
- [ ] GADTs / sealed traits / Data Modeling
- [ ] Parsers
- [ ] Architecture


- Rope colors (difficulty)

