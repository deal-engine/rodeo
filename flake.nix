{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixpkgs-unstable";
  };

  outputs = (inputs:
  let
    darwinPkgs = inputs.nixpkgs.legacyPackages.aarch64-darwin;
  in
  {
    devShells.aarch64-darwin.default = with darwinPkgs; mkShell {
      buildInputs = [ mill ];
    };
  });
}
