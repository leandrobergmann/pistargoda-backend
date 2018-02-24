package br.unb.cic.goda.rtgoretoprism.util;

public class NameUtility {

    public static String adjustName(String source) {

        //A line terminator is a one- or two-character sequence that marks the end of a line of
        //the input character sequence. The following are recognized as line terminators:
        //A newline (line feed) character ('\n'),
        //A carriage-return character followed immediately by a newline character ("\r\n"),
        //A standalone carriage-return character ('\r'),
        //A next-line character ('\u0085'),
        //A line-separator character ('\u2028'), or
        //A paragraph-separator character ('\u2029).

        //\t	The tab character ('\u0009')
/*    	//\n	The newline (line feed) character ('\u000A')	
        //\r	The carriage-return character ('\u000D') */

        //the space character

        return source.replaceAll("(\r\n)|[\t\n\r\u0085\u2028\u2029 ]", "_");
        //\s	A whitespace character: [ \t\n\x0B\f\r]
    }
}