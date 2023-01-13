# -*- mode: python -*-
CONFIG.setdefault("NIX_TOOL", "/nix/var/nix/profiles/default/bin/nix")

def nix_tool(name, nix_attr, tool):
    genrule(
        name = name,
        srcs = [],
        outs = [tool],
        cmd = f"""
    CS_TOOL=$($TOOL shell nixpkgs#{nix_attr} nixpkgs#which -c which {tool})
    ln -s $CS_TOOL $OUT
    """,
        tools = [CONFIG.NIX_TOOL],
    )

nix_tool("coursier", "coursier", "cs")

nix_tool("scalacli", "scala-cli", "scala-cli")

genrule(
    name = "deps",
    srcs = [],
    cmd = """
  mkdir -p libs
  $TOOL fetch --scala 2.13.8 dev.zio::zio-test-sbt:2.0.2 com.lihaoyi::sourcecode:0.3.0 --quiet | xargs -I JAR cp JAR libs/
  """,
    output_dirs = ["libs"],
    tools = ["//:coursier"],
)

genrule(
    name = "compile",
    srcs = ["rodeo/src/"],
    outs = ["compiled"],
    cmd = """
  $TOOL compile --scala 2.13.8 --dep dev.zio::zio-test-sbt:2.0.2 --dep com.lihaoyi::sourcecode:0.3.0 -d compiled $SRC
  """,
    tools = ["//:scalacli"],
)
