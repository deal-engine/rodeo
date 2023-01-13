CONFIG.setdefault('NIX_TOOL', '/nix/var/nix/profiles/default/bin/nix')

genrule(
  name = "hello",
  srcs = [],
  outs = ["hello.out"],
  cmd = "echo hello > $OUT",
)


def nix_tool(name, nix_attr, tool):
  genrule(
    name = name,
    srcs = [],
    tools = [ CONFIG.NIX_TOOL ],
    outs = [ tool ],
    cmd = f"""
    CS_TOOL=$($TOOL shell {nix_attr} -c command -- which {tool})
    ln -s $CS_TOOL $OUT
    """
  )

nix_tool("coursier", "nixpkgs#coursier", "cs")
nix_tool("scalacli", "nixpkgs#scala-cli", "scala-cli")

genrule(
  name = "deps",
  srcs = [],
  tools = [ "//:coursier" ],
  output_dirs = [ "libs" ],
  cmd = """
  mkdir -p libs
  $TOOL fetch --scala 2.13.8 dev.zio::zio-test-sbt:2.0.2 com.lihaoyi::sourcecode:0.3.0 --quiet | xargs -I JAR ln -s JAR libs/
  """
)

genrule(
  name = "compile",
  srcs = [ "rodeo/src/" ],
  outs = [  "compiled" ],
  tools = [ "//:scalacli" ],
  cmd = """
  $TOOL compile --scala 2.13.8 --dep dev.zio::zio-test-sbt:2.0.2 --dep com.lihaoyi::sourcecode:0.3.0 -d compiled $SRC
  """
)
