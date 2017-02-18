# SoyFunctions

This library adds a few custom types and functions to the soy runtime, for both javascript and soy tofu.  

To include it into your project, use the dependency:

```xml
<dependency>
 <groupId>org.slieb.soy.plugins</groupId>
 <artifactId>soy-functions</artifact>
</dependency>
```

[More Info Here](https://stefanliebenberg.github.io/soy-functions/dependency-info.html)

And then add the `SoyFunctionsModule` to your Guice injector.


```java
   Guice.createInjector(new SoyModule(), new SoyFunctionsModule());
```

## Functions

The following additional functions are included.

### capitalize(string)

This function will return a capitalized string.
 
Eg:

```soy
  {capitalize('the Soy Functions')} // will produce "The soy functions"
```

### startsWith(string, prefix)

This function will test whether the given string starts with prefix and return a boolean.

Eg. 

```soy
  {startsWith('sf-header', 'sf')} // will return true
```

### endsWith(string, affix)

This function will test whether the given string ends with the suffix and return a boolean.

Eg. 

```soy
  {endsWith('fishes', 'es')} // will return true
```

### join(array, [separator]) 

This function will join an array together using the separator. 

Eg.

```soy
  {join(['one', 'two', 'three'], '-')} // will produce 'one-two-three'
  {join(['one', 'two', 'three'])} // will produce 'onetwothree'
```

### matches(string, pattern)

This function try to test a pattern against a string.

Eg.

```soy
  {matches('gold was found in the sentence', '(gold|silver)')} // will produce 'true'
```

### parseFloat(string)

This function will try parse out a float number from a string.

### parseInt(string, radix)

This function will try parse out a integer number from a string.

### split(string, [separator])

This function will split a string into an array according to some separator

### strLength(string)

This function will return the length of a given string.

### substring(string, from, [to])

This function will return a substring from the given string, based on the `from` and `to` (optional) arguments.

### toFixed(number, radix)

This function print a number into string with fixed number of decimals.

### toJson(object)

This function will attempt to turn any object into a json string.

### toLowerCase(string)

This function will turn a string to lower case.

### toUpperCase()

This function will turn a string to upper case.


### toSafeUrl(string)

This returns a safe url for some string, can used everywhere except to specify `<script>` tag source urls.

### toTrustedUri(string)

This returns a trusted url of string, ideally only to be used to specify `<script> ` tag source urls.

## Experimental Functions:

This functions might not work as expected.


### toInstant([string|number|date])

This will attempt to turn some string or number into a epoch millisecond instant date object. To be used with `isAfter`, `isBefore`, `printDateTime` and 
`printTimestamp` methods. If no arguments are passed, then now is used to get the instant. 

**Technical Note:**, internally this results in an `InstantSoyValue` object.
  
### toDateTime([string|number|date], [zone])

Returns a `DateTimeSoyValue`, which is a `InstantSoyValue` but tied to some specified zone.

### zoneOffset(id|offset)

Returns a zone offset for the given id. To be used with `toDateTime` and `printDate` methods.

### printTimestamp(date)

Prints out the date or instant's epoch milliseconds.

### printDate(date, format, zone)

Prints a date using some format string and optionally in some zone.

### isAfter(date, date)

This function will compare two dates and return true if the first date is after the second.

### isBefore(date, date)

This function will compare two dates and return true if the first date is before the second.
