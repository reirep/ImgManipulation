# Image manipulation library
(I'm still working on the name)

A small lib writen in java (1.8) to manipulate images.
 
## Installation
Currently, there is no way to build a jar with one command. It's on the TODO list... I will (maybe) do it in some distant future.
 
## Usage
There is multiple way of using this lib. They are all listed here.

### CLI

The normal usage fo this lib is one of the following:
```
java -jar lib.jar help
java -jar lib.jar list
java -jar lib.jar <path to picture> <formated filters list>
```

- The `list` command list all filters availables. 
- The `help` command show a helper message. *Currently broken - wip*
- The `<path to picture>` must be a path (absolute or not) to a single picture (multiple is wip)
- The `formated filters list` must be the list of filter you want to apply and the picture, with, when needed, some arguments for the filter.
The filter list must respect thoses rules:
    - All the filter must be names from the `imageManipulation.all` package (example: `blur.box` is a valid filter name).
    - When multiple filters are required, separate them with the '>' symbol (example: `blur.box>edge.sobel` is a valid chain of filters).
    - When a filter need one or more arguments, the filter and it's arguments must be separated by using the ':' symbol.
    - When a filter has multiple arguments, separate the arguments with the ',' symbol.
    
Example of correclty formated fitlers:

- `colour.full.red`
- `blur.gaussianBig>edge.sobel`
- `blur.gaussianSmall>colour.gamma:3.5`
- `blur.box>grain.softmax:10,0.97>edge.prewitt`

Always read the 2-3 lines in the filter that explain with argument are needed ! 
All the filters don't require the same kind of arguments ! 

### Interractive mode

documentation WIP

### Java api

All the methods needed to use this lib are contained in the Filter class.
Thoses methods can return a variety of exceptions don't hesitate to read the doc.

- `public static List<String> list()`: return a list of all the available filters.
- `public static Picture applyFilter(Picture p, String filterLine)`: apply the filter to the picture and append
the name of the filter to the name of the picture.
- `public static void applyFilters(String pathPicture, String [] filters)`: apply all the given filter
to the given picture an return write the new picture to the disk.

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
        - x_canny
    - grain
        - softmax
    - mirror
        - vertical
        - horizontal
    - nette
        - sharpen
        - x_unsharp
    - zone
        - first

## TODO
- The generation of a jar
- The "interractive mode" part of the doc
- Add details on the list of filters
- Add support for multiple pictures at the same time
- The help message
- Remodel the java API
- add a logger
- update the filter list with the new ones
- add multiple output formats
- ...
 
## Dependencies
- [jblas](jblas.org)
- [fast-classpath-scanner](https://github.com/lukehutch/fast-classpath-scanner)
 
## License
This project is under the [GPLv3](https://www.gnu.org/licenses/gpl-3.0.en.html) license.
 