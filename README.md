Scala Rodeo
===========

Welcome to Deal Engine's Scala Rodeo.

This material is intended for you to learn the basic of scala and some
common functional idioms.

Along the way you will find some Exercises, requiring you to change some
code in order to practice some concepts.

Quickstart
----------

- Install Nix
- (Optional) Enable Flakes support
- (Optional) Install direnv
- Clone this repository

If using direnv: Allow this repository. Else: Enter the Nix Shell using `nix develop`, unless you are not using flakes, then
`nix-shell shell.nix`

Detailed instructions
----------------------
The first thing to do is to install Nix. This is a package manager. It will allow you to download
dependencies in an isolated way.

The new recomended way of installation for both Linux and macOS is this one:

    curl --proto '=https' --tlsv1.2 -sSf -L https://install.determinate.systems/nix | sh -s -- install

To install Nix in Linux run (using the official installer):

    sh <(curl -L https://nixos.org/nix/install) --daemon

In macOS (Darwin) don't use `--daemon` (using the official installer):

    sh <(curl -L https://nixos.org/nix/install)

For more information about Nix installation you can check their [documentation](https://nixos.org/manual/nix/stable/installation/installation.html).

If you are using NixOS, you already have Nix installed.

### Flakes support

Flakes is a feature in the Nix package manager. It allows us to create
isolated environments.

To enable Flakes:

- In Linux/macOS: `mkdir -p ~/.config/nix/ && echo "experimental-features = nix-command flakes" >> ~/.config/nix/nix.conf`

### Installing direnv

We recommend using direnv to enter the environment in an easier way.

You can install it using flakes:

    nix profile install nixpkgs#direnv

Now add the hook to your `~/.zshrc` if using ZSH:

    eval "$(direnv hook zsh)"

For bash is similar:

    eval "$(direnv hook bash)"

### Clone this repository

Clone this repository to begin the Scala Rodeo.

    git clone https://github.com/deal-engine/rodeo.git

### Entering the environment

If you are using direnv you can just enter the repository and it will
enter the nix shell unless it's your first time. For security reasons
direnv will ask you to allow the environment. If it doesn't you can
allow it with `direnv allow`.

Otherwise you can enter the environment using the command:

    nix develop

Unless you are not using flakes, then you need to use `nix-shell shell.nix`.

### Exiting

To exit the environment either exit the shell or exit the directory if you are
using direnv.

### Devshell

You will be presented with something like this:

    ### ️🔨 Welcome to the Nix devshell ###

    Available commands:

    ## Commands

      , editor        : Run a preconfigured VSCode.
      , editor-setup  : Setup the editor.
      , readme        : Read the readme.
      , tests         : Run the Rodeo tests.

    (Run ',' to display this menu again)

IntelliJ it's recommended. but we have included a functional VS Code editor with support
for scala.

You can open it with `, editor` command. Unless it's your first time,
you will need to setup the environment first. You can do it with the
`, editor-setup` command.

Once you have opened the editor you can open the `src` folder. You will
find the source code of the Rodeo.

You can read the README with `, readme` command.

Once you are finished you can exit the environment with `exit`.

You can run the tests with the `, tests` command.

Tests
-----

Tests are here to check your exercises.

You can run the tests with `, tests` command.

This is syntactic sugar for `mill -w rodeo.test`

### Running specific tests

You can run a specific test by running `mill -w rodeo.test -t "something"` where `something` is a tag that matches over all the tests.

For example, you can run all the tests related to the `Option` exercises with `mill -w rodeo.test -t "Option"`.

That's all for now!
-----------------

Happy rodeeeoooooo! 🤠🏇🏻
