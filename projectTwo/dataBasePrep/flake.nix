{

  description = "Python dev Env 4 PIP";
  nixConfig.bash-prompt = "[ Jupyter ]-> ";
  inputs = { nixpkgs.url = "github:nixos/nixpkgs/nixos-unstable"; };

  outputs = { self, nixpkgs }:
    let
    pkgs = nixpkgs.legacyPackages.x86_64-linux.pkgs;
    libs = ps: with ps; 
      [
        ipykernel
        notebook {kernel = ipykernel;
                 }
        jupyter-contrib-core
        jupyter-nbextensions-configurator
        pandas
        numpy
        pycodestyle
	pip
      ];
    in
      {
        devShells.x86_64-linux.default = pkgs.mkShell {
          name = "Jupyter";
          buildInputs = with pkgs; [
            (python311.withPackages libs)
          ];
        };

      };
}
