# How to compile
This project utilizes **Maven** as its build system. To compile and package the project, execute the following command:
```bash
maven package
```
Maven will automatically handle all dependencies and generate a JAR file in the target directory. Alternatively, on systems equipped with a Bash shell, you can utilize the **build.sh** script:
```bash
./build.sh r
```
This command not only builds but also runs the program. Since our program accepts multiple arguments,
you may need to adjust the build.sh script according to your requirements. Upon a successful execution,
the program generates a CSV file containing the classification results.
