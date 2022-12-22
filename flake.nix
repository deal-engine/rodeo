{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixpkgs-unstable";
    flake-parts.url = "github:hercules-ci/flake-parts";
    flake-root.url = "github:srid/flake-root";
    mission-control.url = "github:Platonic-Systems/mission-control";
  };

  outputs = inputs@{ self, nixpkgs, flake-parts, ... }:
    flake-parts.lib.mkFlake { inherit inputs; } {
      systems = nixpkgs.lib.systems.flakeExposed;
      imports = [
        inputs.flake-root.flakeModule
        inputs.mission-control.flakeModule
      ];
      perSystem = { pkgs, lib, config, system, ... }: {
        mission-control.scripts = {
          tests = {
            description = "Run the Rodeo tests.";
            exec = "mill -w rodeo.test";
          };
          editor = {
            description = "Run a preconfigured VSCode.";
            exec = "code .";
          };
        };
        devShells.default =
          let unfreepkgs = import nixpkgs { 
            config = { allowUnfree = true; }; 
            system = system;
          };
          vscode = unfreepkgs.vscode-with-extensions.override {
            vscodeExtensions = with unfreepkgs.vscode-extensions; [
              scalameta.metals
              arrterian.nix-env-selector
              scala-lang.scala
              mkhl.direnv
            ];
          };
          shell = pkgs.mkShell {
            buildInputs = [ 
              pkgs.mill
              vscode
            ];
          };
          in config.mission-control.installToDevShell shell;
      };
    };
}
