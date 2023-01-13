{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixpkgs-unstable";
    flake-parts.url = "github:hercules-ci/flake-parts";
    flake-root.url = "github:srid/flake-root";
    mission-control.url = "github:Platonic-Systems/mission-control";

    please-aarch64-darwin.url = "https://github.com/thought-machine/please/releases/download/v16.26.1/please_16.26.1_darwin_arm64.tar.gz";
    please-aarch64-darwin.flake = false;
  };

  outputs = inputs@{ self, nixpkgs, flake-parts, ... }:
    flake-parts.lib.mkFlake { inherit inputs; } {
      systems = nixpkgs.lib.systems.flakeExposed;
      imports = [
        inputs.flake-root.flakeModule
        inputs.mission-control.flakeModule
        inputs.flake-parts.flakeModules.easyOverlay
      ];
      perSystem = { pkgs, lib, config, system, ... }: {
        overlayAttrs = {
          inherit (config.packages) please;
        };
        packages.please = with pkgs; stdenvNoCC.mkDerivation {
          name = "please";
          src = inputs."please-${system}";
          phases = ["install"];
          install = ''
          mkdir -p $out/bin
          cp $src/* $out/bin
          (cd $out/bin; ln -s please plz)
          '';
        };
        mission-control.scripts = {
          readme = {
            description = "Read the readme.";
            exec = "${lib.getExe pkgs.glow} README.md";
          };
          tests = {
            description = "Run the Rodeo tests.";
            exec = "mill -w rodeo.test";
          };
          editor = {
            description = "Run a preconfigured VSCode.";
            exec = "code .";
          };
          editor-setup = {
            description = "Setup the editor.";
            exec = ''
              rm -rf .bsp/ .metals/ .bloop/ out/
              mill mill.bsp.BSP/install
              echo "Done 🎉"
              echo "Run the editor for the first time and then open the Command Palette"
              echo "with Shift+Cmd+P, then search for «switch build server»; when asked"
              echo "select mill-bsp as your build server, don't use the other option!"
              echo "You should do this only every time that you execute the editor-setup."
              echo "Have fun! 🐴"
            '';
          };
          build = {
            description = "Please build.";
            exec = "please build";
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
              config.packages.please
              pkgs.mill
              vscode
            ];
          };
          in config.mission-control.installToDevShell shell;
      };
    };
}
