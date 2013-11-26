Simple calculator with unlimited precision
==========================================

> This small project is a solution for an assignment for my Java class at Charles University in Prague.

### Usage instructions

1. checkout the repository
2. compile the CodEx class
`javac CodEx.java`
3. run the CodEx.class
`java CodEx`
4. run "tests" with
`javac CodEx.java && java CodEx < INPUT > CURRENT_OUTPUT && diff CURRENT_OUTPUT OUTPUT`

Assignment: simple calculator with unlimited precision
------------------------------------------------------

Create a simple calculator, which allows computing with unlimited precision. The program reads data from the standard input and prints to the standard output. The calculator can add, subtract, multiply and divide. Priority of the operators is as usual and can be changed by parentheses. The input format of the numbers is usual with a decimal dot (integer numbers are of course also allowed). Numbers can be negative (i.e. unary minus can be also used).

Expressions are separated by a new line. The program always prints out the result of the expression. The result of the expression is also stored to the special variable last, which can be used in the subsequent expression (for the first expression, the last variable is set to zero). The elements on a single line are separated by an arbitrary number of white spaces (space, tab,. . . ).

If the expression does not correspond to the above rules, the program prints out the text CHYBA. The last variable is then set to zero.

Initially, the program computes with the precision set to 20 digits after the decimal point. The precision can be changed via the precision command.

#### Examples

#### Input

    3 + 2
    2 * ( 3 + 3 )
    ( 2 - 3 * ( 1 + 2) )
    2 * 3
    last - 3
    10 * last
    1.2 + 4.3
    1 / 3
    1000000000 * 1000000000
    precision 30
    1 / 3
    3 ** 5
    last * 3

#### Output

    5
    12
    -7
    6
    3
    30
    5.5
    0.33333333333333333333
    1000000000000000000
    0.333333333333333333333333333333
    CHYBA
    0

## License 

    Released under MIT license.

    Permission is hereby granted, free of charge, to any person obtaining
    a copy of this software and associated documentation files (the
    "Software"), to deal in the Software without restriction, including
    without limitation the rights to use, copy, modify, merge, publish,
    distribute, sublicense, and/or sell copies of the Software, and to
    permit persons to whom the Software is furnished to do so, subject to
    the following conditions:

    The above copyright notice and this permission notice shall be
    included in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
    EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
    MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
    NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
    LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
    OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
    WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
