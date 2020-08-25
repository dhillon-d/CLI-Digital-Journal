# Design
## Flow
|Input|Class|Output|
|-|-|-|
|Command Line Arguments|CLI Parser|Args Map|
|Args Map|CSV Parser|ArrayList of Maps|
|Args Map, ArrayList of Maps|Entry Manager|Updated ArrayList of Maps, Write to CSV|
|Args Map, Updated ArrayList of Maps|Display Entries|Display Data|

## MVC Pattern
|Model|View|Controller|
|-|-|-|
|CLI Parser, CSV Parser, Entry Manager|Command Line Arguments, Display Entries|Main|

## Class Patterns
|Class|Pattern|
|-|-|
|CLI Parser|Simplified Command|
|CSV Parser|Singleton|
|Entry Manager|Strategy|
|Display Entries|Factory|
