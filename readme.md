# Image manipulation library
(I'm still working on the name)
 
## Installation
Currently, there is no way to build a jar with one command. It's on the TODO list... I will (maybe) do it in some distant future.
 
## Usage
There is two ways of using this project: the cli and in interractive mode.
It's planned to add a java api... on the TODO list :)

### CLI

The normal usage fo this lib is one of the following:
```
java -jar lib.jar help
java -jar lib.jar list
java -jar lib.jar <path to picture> <formated filters list>
```

- The `list` command list all filters availables. 
- The `help` command show a helper message. *Currently broken - wip*
The `<path to picture>` must be a path (absolute or not) to a single picture (multiple is wip)
The `formated filters list` must be the list of filter you want to apply and the picture, with, when needed, some arguments for the filter.
The filter list must respect thoses rules:
    - All the filter must be names from the `imageManipulation.all` package (example: `blur.box` is a valid filter name).
    - When multiple filters are required, separate them with the '>' symbol (example: `blur.box>edge.sobel` is a valid chain of filters).
    - When a filter need one or more arguments, the filter and it's arguments must be separated by using the ':' symbol.
    - When a filter multiple arguments, separate the arguments with the ',' symbol.
    
Example of correclty formated fitlers:

- `colour.full.red`
- `blur.gaussianBig>edge.sobel`
- `blur.gaussianSmall>colour.gamma:3.5`
- `blur.box>grain.softmax:10,0.97>edge.prewitt`

Always read the 2-3 lines in the filter that explain with argument are needed ! 
All the filters don't require the same kind of arguments ! 

### Interractive mode

WIP

## Availables filters

This is the list of all currently available filters and their organisation. 
More filters will be added in the future.

*Note: any filter that start with "x_" is still under development and is verry probably currently broken.*

- all
    - blur
        - box
        - gaussianBig
        - gaussianSmall
    - colour
        - filter
            - grey
            - greymean
            - invert
            - root
            - sepia
            - solarizehigh
            - solarizelow
            - square
        - full
            - red
            - green
            - blue
        - no
            - red
            - green
            - blue
        - brightness
        - contrast
        - gamma
    - edge
        - basic1
        - basic2
        - basic3
        - prewitt
        - scharr
        - sobal
    - grain
        - softmax
    - mirror
        - vertical
        - horizontal
    - nette
        - sharpen
    - zone
        - first

## TODO
- The TODO list
- Jar
- The "interractive mode" part of the doc
- Add details on the list of filters
- add support for multiple pictures
- The help message
- Add a java api
- ...
 
## Dependencies
- [jblas](jblas.org)
- [ast-classpath-scanner](https://github.com/lukehutch/fast-classpath-scanner)
 
## License
This project is under the [GPLv3](https://www.gnu.org/licenses/gpl-3.0.en.html) license.
 