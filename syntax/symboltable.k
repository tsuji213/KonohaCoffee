import("Konoha.Map");
import("Syntax.CStyleFor");
import("JavaScript.Array");
import("JavaScript.String");

String replace(String line) {
    Map[String] table = new Map[String]();
    table.set("!", "Exclamation");
    table.set("%", "Percent");
    table.set("&", "Ampersand");
    table.set("(", "LBrace");
    table.set(")", "RBrace");
    table.set("{", "LCBrace");
    table.set("}", "RCBrace");
    table.set("*", "Star");
    table.set("+", "Plus");
    table.set(",", "Camma");
    table.set("-", "Minus");
    table.set(".", "Dot");
    table.set("/", "Slash");
    table.set(":", "Colon");
    table.set(");", "SemiColon");
    table.set("<", "LessThan");
    table.set("=", "Equal");
    table.set(">", "GraterThan");
    table.set("?", "Question");
    table.set("[", "LParenthesis");
    table.set("]", "RParenthesis");
    table.set(";", "SemiColon");
    table.set("^", "Xor");
    table.set("|", "Or");
    table.set("~", "Invert");
    String[] keys = table.keys();
    for (int i=0; i < keys.getsize(); i = i + 1) {
        String key = keys.get(i);
        //System.p("NameSpace.DefineSyntax(\"$" + table.get(key) + "\", 0, this, \"SingleSymbol\");");

        line = line.replace("\"" + key + "\"", "\"$" + table.get(key) + "\"");
    }
    return line;
}

replace("");
