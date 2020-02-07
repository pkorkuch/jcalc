# jcalc
![](https://raw.githubusercontent.com/pkorkuch/jcalc/master/img/jcalc.png)

A basic Java GUI calculator with support for parenthetical expressions and storing of the last result.

jcalc supports addition, subtraction, multiplication, division, and exponentiation, plus modulo, with the +, -, \*, /, and ^, % operators and standard PEMDAS order of operations:

```Java
5 + 5 * 3 = 20
```

Unlike many programming languages, modulo is of lowest precedence:

```java
10 * 5 % 2 = 0 // not 10 * 5 % 2 = 10 * 1 = 10
```

jcalc also supports parenthetical expressions to modify the order of operations:

```java
(5 + 5) * 3 = 45
```

```java
2 ^ (1 + 2) = 8
```

Finally, jcalc supports an ANS token insertable with the "ANS" button to represent the value of the last valid operation:

```java
5 * 3 = 15
ANS * 3 = 45
```
