import("JavaScript.Array");
import("JavaScript.String");
import("Java.String");
import("Syntax.Null");
import("Type.File");
import("Type.Bytes");
import("Syntax.CStyleWhile");
import("Syntax.CStyleFor");
import("Syntax.JavaStyleClass");
import("Syntax.StringInterpolation");
import("JavaStyle.Object");
import("Konoha.Map");

load("./symboltable.k");

class IO {
	FILE file;
	IO(FILE file) {
		this.file = file;
	}
	void emit(int n, String rule) {
		while (n > 0) {
			file.print("  ");
			n = n - 1;
		}
		file.println(rule);
	}
	void close() {
		file.close();
	}
	int emitBackTrackPoint(int level, int index) {
		this.emit(level, "int pos${index} = Parser.Cursor;");
		this.emit(level, "int thunkpos${index} = Parser.ThunkPos;");
		this.emit(level, "int NodeSize${index} = NodeSize;");
		return index + 1;
	}
	void emitBackTrack(int level, int index, String name) {
		this.emit(level, "NodeSize = this.BackTrack(Parser, pos${index}, thunkpos${index}, NodeSize${index}, \"BackTrack ${name} ${index}\");");
	}

	void emitLog(int level, String message) {
		this.emit(level, "Report(\"${message}\");");
	}
}

String readFile(String path) {
	FILE file = new FILE(path, "rb");
	int len = 0;
	String tmp = "";
	while (true) {
		int ch = file.getc();
		if (ch == -1) {
			break;
		}
		len = len + 1;
	}
	file.close();

	if (len == 0) {
		return "";
	}

	Bytes buf = new Bytes(len);
	file = new FILE(path, "r");
	file.read(buf, 0, len);
	file.close();
	return new String(buf);
}

class ElementBase {}

class ParserGenerator {
	int level;
	int index;
	boolean repeatMode;
	ElementBase[] stack;
	IO io;
	ParserGenerator(IO io) {
		this.level = 0;
		this.index = 0;
		repeatMode = false;
		this.stack = [];
		this.io = io;
	}
	void EnterBlock() {
		this.level = this.level + 1;
		stack.add(null);
	}
	void EnterBlock(ElementBase e) {
		this.level = this.level + 1;
		stack.add(e);
	}
	void ExitBlock() {
		this.level = this.level - 1;
		stack.pop();
		io.emit(level, "}");
	}
	void ExitBlock(int level) {
		while(true) {
			ElementBase e = stack.pop();
			if(this.level == level) {
				break;
			}
			this.ExitBlock();
		}
	}
	void ExitBlock(ElementBase target) {
		while(true) {
			ElementBase e = stack.pop();
			this.ExitBlock();
			if(e == target) {
				break;
			}
			if(this.level == 0) {
				assert(false);
				break;
			}
		}
	}
}

class Element extends ElementBase {
	@Virtual String toString() {
		return "<Element>";
	}
	@Virtual int size() {
		return 0;
	}
	@Virtual void emit(ParserGenerator G) {
		assert(false);
	}
	@Virtual void emitAction(ParserGenerator G) {
		assert(false);
	}
}

class SymbolElement extends Element {
	String name;
	SymbolElement(String name) {
		this.name = name;
	}
	@Virtual int size() {
		return 1;
	}

	@Override void emit(ParserGenerator G) {

		if(name.startsWith("$")) {
			String func = name.replace("#", "");
			G.io.emit(G.level, "if(Parser.Match(\"${func}\", TokenList) >= 0) {");
			G.io.emit(G.level, "NodeSize = NodeSize + 1;");
		} else {
			String sym = replace(name);
			G.io.emit(G.level, "if(Parser.MatchToken(${sym}, TokenList, Parser.Cursor) >= 0) {");
		}
		G.EnterBlock(this);
	}
	@Override void emitAction(ParserGenerator G) {
		if(name.startsWith("$")) {
			G.io.emit(G.level, "List[Index] = Parser.Get(Index, NodeSize);");
			G.io.emit(G.level, "Index = Index + 1;");
		}
	}
	@Override String toString() {
		return "<Symbol:" + this.name + ">";
	}

}

class GroupElement extends Element {
	Element[] elems;
	GroupElement() {
		elems = [];
	}
	void add(Element e) {
		elems.add(e);
	}
	@Virtual int size() {
		int ret = 0;
		for (int i=0; i < elems.getsize(); i = i + 1) {
			ret = ret + elems.get(i).size();
		}
		return ret;
	}

	@Override void emit(ParserGenerator G) {
		for (int i=0; i < elems.getsize(); i = i + 1) {
			elems.get(i).emit(G);
		}
	}
	@Override void emitAction(ParserGenerator G) {
		for (int i=0; i < elems.getsize(); i = i + 1) {
			elems.get(i).emitAction(G);
		}
	}
	@Override String toString() {
		String ret = "<Group:";
		for (int i=0; i < elems.getsize(); i = i + 1) {
			ret = ret + elems.get(i).toString() + " ";
		}
		ret = ret + ">";
		return ret;
	}
}

class RepeatElement extends Element {
	Element target;
	RepeatElement() {
		this.target = null;
	}
	@Virtual int size() {
		return target.size();
	}
	@Override void emit(ParserGenerator G) {
		G.io.emit(G.level, "while(true) {");
		G.EnterBlock(target);
		target.emit(G);
		G.io.emit(G.level, "continue;");
		G.ExitBlock(target);
	}
	@Override void emitAction(ParserGenerator G) {
		G.io.emit(G.level, "while(Index < NodeSize) {");
		G.EnterBlock();
		target.emitAction(G);
		G.ExitBlock();
	}

	@Override String toString() {
		return "<Repeat:" + target.toString() + ">";
	}
}

class Syntax {
	String name;
	Array[Array[Element]] rules;
	Syntax(String name) {
		this.name = name;
		this.rules = [];
	}
	void append(Element[] rule) {
		rules.add(rule);
	}
	void emit(ParserGenerator G) {
		int b0 = G.index;
		//emit(G.level, "int Cursor = Parser.Cursor;");
		G.index = G.io.emitBackTrackPoint(G.level, G.index);
		G.io.emitLog(G.level, "Enter ${this.name}");

		//G.index = emitBackTrackPoint(G.level, G.index);
		for (int i=0; i < rules.getsize(); i = i + 1) {
			Array[Element] subrules = rules.get(i);
			int baselevel = G.level;
			for (int j=0; j < subrules.getsize(); j = j + 1) {
				Element e = subrules.get(j);
				e.emit(G);
			}
			G.io.emit(G.level, "return action${i}(\"${this.name}\", Parser, pos0, NodeSize);");
			for (int j=0; j < subrules.getsize(); j = j + 1) {
				G.ExitBlock();
			}
			if(!(rules.getsize() == 1 && rules.get(0).get(0) instanceof RepeatElement)) {
				G.io.emitBackTrack(G.level, b0, name);
			}
		}
		if(!(rules.getsize() == 1 && rules.get(0).get(0) instanceof RepeatElement)) {
			G.io.emit(G.level, "return Fail(\"${this.name}\", Parser);");
		}
	}

	String toString() {
		String ret =  "[" + name + ":\n";
		for (int i=0; i < rules.getsize(); i = i + 1) {
			ret = ret + "\t";
			Array[Element] subrules = rules.get(i);
			ret = ret + "[\n";
			for (int j=0; j < subrules.getsize(); j = j + 1) {
				ret = ret + "\t\t";
				ret = ret + subrules.get(j).toString() + "\n";
			}
			ret = ret + "\t]\n";
		}
		ret = ret + "]";
		return ret;
	}

	void parseRule(String rule) {
		Element[] ruleList = [];
		rule = rule.trim();
		String[] r = rule.split(" ");
		Element[] stack = [];
		for(int i = 0; i < r.getsize(); i = i + 1) {
			String s = r.get(i);
			if(s.endsWith("*")) {
				s = s.substring(0, s.length() - 1);
				stack.add(new RepeatElement());
			} else {}

			if(s.startsWith("(")) {
				s = s.substring(1, s.length());
				GroupElement ge = new GroupElement();
				ge.add(new SymbolElement(s));
				stack.add(ge);
			} else if(s.endsWith(")")) {
				s = s.substring(0, s.length() - 1);
				Element last = stack.pop();
				GroupElement ge = null;
				RepeatElement re = null;
				if(last instanceof RepeatElement) {
					re = last as RepeatElement;
					ge = stack.pop() as GroupElement;
				} else {
					ge = last as GroupElement;
				}
				ge.add(new SymbolElement(s));

				if(re != null) {
					re.target = ge;
					ruleList.add(re);
				} else {
					ruleList.add(ge);
				}
			} else {
				Element element = new SymbolElement(s);
				if(stack.getsize() > 0) {
					Element e = stack.pop();
					if(e instanceof GroupElement) {
						GroupElement ge = e as GroupElement;
						ge.add(element);
						ruleList.add(ge);
					} else if(e instanceof RepeatElement) {
						RepeatElement re = e as RepeatElement;
						assert(re.target == null);
						re.target = element;
						ruleList.add(re);
					} else {
						ruleList.add(element);
					}
				} else {
					ruleList.add(element);
				}
			}
		}
		this.append(ruleList);
	}
}

void emitRule(IO aio, IO sio, Syntax[] syntaxList) {
	for(int i = 0; i < syntaxList.getsize(); i = i + 1) {
		Syntax syn = syntaxList.get(i);
		emitSyntax(aio, sio, syn);
	}
}

void emitChild(ParserGenerator G, Syntax syn) {
	Map[String] m = new Map[String]();
	Array[Element] a = [];
	for (int i=0; i < syn.rules.getsize(); i = i + 1) {
		Array[Element] subrules = syn.rules.get(i);
		for (int j=0; j < subrules.getsize(); j = j + 1) {
			Element e = subrules.get(j);
			a.add(e);
		}
	}
	while(true) {
		if(a.getsize() == 0) {
			break;
		}
		Element e = a.pop();
		if(e instanceof SymbolElement) {
			SymbolElement se = e as SymbolElement;
			if(se.name.startsWith("$") && se.name.startsWith("$#") == false) {
				m.set(se.name.replace("$", "") + "Syntax", se.name);
			}
		}
		else if(e instanceof RepeatElement) {
			RepeatElement re = e as RepeatElement;
			a.add(re.target);
		}
		else if(e instanceof GroupElement) {
			GroupElement ge = e as GroupElement;
			for (int i=0; i < ge.elems.getsize(); i = i + 1) {
				a.add(ge.elems.get(i));
			}
		}
	}
	String[] keys = m.keys();
	G.io.emit(1, "public void Init(KonohaNameSpace NameSpace, PegParser Parser) {");
	for (int i=0; i < keys.getsize(); i = i + 1) {
		String name = keys.get(i);
		G.io.emit(2, "Parser.AddSyntax(NameSpace, this, new ${name}(), false);");
	}
	G.io.emit(1, "}");
}



void emitActionEpiloge(Element[] subrules, ParserGenerator G) {
	if(subrules.getsize() == 0) {
		return;
	}
	else if(subrules.getsize() == 1) {
		Element e = subrules.get(0);
		if(e instanceof SymbolElement) {
			SymbolElement se = e as SymbolElement;
			if(se.name.startsWith("$") == false) {
				G.io.emit(G.level, "Parser.Push(new TreeNode(${se.name}));");
				return;
			}
		}
	}
	G.io.emit(G.level, "if(NodeSize > 0) {");
	G.io.emit(G.level+1, "Parser.ReAssign(NodeSize, List[0]);");
	G.io.emit(G.level, "}");
}

void emitAction(String name, IO aio, Array[Element] subrules) {
	ParserGenerator G = new ParserGenerator(aio);
	String v = "";
	for (int j=0; j < subrules.getsize(); j = j + 1) {
		Element e = subrules.get(j);
		if (j != 0) {
			v = v + ", ";
		}
		v = v + e.toString();
	}

	G.io.emit(G.level, "// action: " + v);
	G.io.emit(G.level, "class ${name} extends SyntaxAcceptor {");
	G.EnterBlock();
	G.io.emit(G.level, "static final ${name} Instance = new ${name}();");
	G.io.emit(G.level, "@Override public int Parse(PegParser Parser, TokenList TokenList, int BeginIdx, int EndIdx, int NodeSize) {");
	G.EnterBlock();
	G.io.emit(G.level, "Report(\"${name}\", NodeSize);");
	G.io.emit(G.level, "int Index = 0;");
	G.io.emit(G.level, "Object[] List = new Object[NodeSize];");
	for (int j=0; j < subrules.getsize(); j = j + 1) {
		Element e = subrules.get(j);
		e.emitAction(G);
	}
	emitActionEpiloge(subrules, G);
	G.io.emit(G.level, "return EndIdx;");
	G.ExitBlock();
	G.io.emit(G.level, "@Override public TypedNode TypeCheck(TypeEnv Gamma, UntypedNode UNode, KonohaType TypeInfo) {");
	G.EnterBlock();
	G.io.emit(G.level, "return null;");
	G.ExitBlock();
	G.ExitBlock();
}

void emitSyntax(IO aio, IO sio, Syntax syn) {
	ParserGenerator G = new ParserGenerator(sio);

	G.io.emit(G.level, "/*");
	G.io.emit(G.level, syn.toString());
	G.io.emit(G.level, "*/");

	String func = syn.name.replace("$", "");
	func = func.replace("#", "");
	for (int i=0; i < syn.rules.getsize(); i = i + 1) {
		String name = func + "Syntax" + i;

		Array[Element] subrules = syn.rules.get(i);
		emitAction(name, aio, subrules);
	}

	G.io.emit(G.level, "class ${func}Syntax extends SyntaxTemplate {");
	G.io.emit(G.level+1, "${func}Syntax() {");
	G.io.emit(G.level+2, "super(\"${syn.name}\");");
	G.io.emit(G.level+1, "}");
	emitChild(G, syn);
	G.level = G.level + 1;
	for (int i=0; i < syn.rules.getsize(); i = i + 1) {
		G.io.emit(G.level, "public SyntaxAcceptor Acceptor${i} = new ${func}Syntax${i}();");

		G.io.emit(G.level, "int action${i}(String SyntaxName, PegParser Parser, int BeginIdx, int NodeSize) {");
		G.EnterBlock();
		G.io.emitLog(G.level, "Accept ${syn.name}");
		G.io.emit(G.level, "Parser.PushThunk(this.Acceptor${i}, BeginIdx, NodeSize);");
		G.io.emit(G.level, "return Parser.Cursor;");
		G.ExitBlock();
	}
	G.io.emit(G.level, "public int Match(PegParser Parser, TokenList TokenList) {");
	G.io.emit(G.level+1, "int NodeSize = 0;");
	G.EnterBlock();

	syn.emit(G);
	G.ExitBlock();
	G.ExitBlock();
}

String f(String n) {
	String[] path = n.split("/");
	String[] a = path.get(path.getsize() -1).split(".");
	String r = "";
	for(int i = 0; i < a.getsize()-1; i = i + 1) {
		String l = a.get(i);;
		r = r + l.get(0).toUpperCase() + l.substring(1);
	}
	return r + ".java";
}

void main() {
	String[] args = SCRIPT_ARGV;
	if(args.getsize() != 2) {
		return;
	}

	String peg = args.get(1);
	String actionFile = peg + ".action.java";
	String syntaxFile = peg + ".syntax.java";
	actionFile = f(actionFile);
	syntaxFile = f(syntaxFile);
	String filename = f(peg).split(".").get(0);
	System.p("OutputFile(1):" + actionFile);
	System.p("OutputFile(2):" + syntaxFile);

	String[] file = readFile(peg).split("\n");
	Syntax[] syntaxList = [];
	Syntax cur = null;
	for(int i = 0; i < file.getsize(); i = i + 1) {
		String l = file.get(i);
		l = l.trim();
		if(l.startsWith("#")) {
			continue;
		}
		if(l.startsWith("$")) {
			String[] a = l.split("<-");
			String name = a.get(0).trim();
			if(cur != null) {
				syntaxList.add(cur);
			}
			cur = new Syntax(name);
			String s = a.get(1).trim();
			String prev = s.get(0);
			String buf = prev;
			for(int i = 1; i < s.length(); i = i + 1) {
				String c = s.get(i);
				if(prev != "\"" && c == "/") {
					cur.parseRule(buf);
					buf = "";
					continue;
				}
				prev = c;
				buf = buf + c;
			}
			if(buf == s) {
				cur.parseRule(buf);
			}
		}
		if(l.startsWith("/")) {
			assert(cur != null);
			String s = l.substring(1).trim();
			cur.parseRule(s);
		}
	}
	if(cur != null) {
		syntaxList.add(cur);
	}
	IO aio = new IO(new File(actionFile, "w"));
	IO sio = new IO(new File(syntaxFile, "w"));

	sio.emit(0, "package org.KonohaScript.Peg.${filename};");
	sio.emit(0, "");
	sio.emit(0, "import org.KonohaScript.KLib.TokenList;");
	sio.emit(0, "import org.KonohaScript.PegParser.SyntaxAcceptor;");
	sio.emit(0, "import org.KonohaScript.PegParser.PegParser;");
	sio.emit(0, "import org.KonohaScript.PegParser.SyntaxTemplate;");

	aio.emit(0, "package org.KonohaScript.Peg.${filename};");
	aio.emit(0, "import org.KonohaScript.KonohaType;");
	aio.emit(0, "import org.KonohaScript.KLib.TokenList;");
	aio.emit(0, "import org.KonohaScript.Parser.TypeEnv;");
	aio.emit(0, "import org.KonohaScript.Parser.UntypedNode;");
	aio.emit(0, "import org.KonohaScript.PegParser.SyntaxAcceptor;");
	aio.emit(0, "import org.KonohaScript.PegParser.PegParser;");
	aio.emit(0, "import org.KonohaScript.SyntaxTree.TypedNode;");
	aio.emit(0, "");

	emitRule(aio, sio, syntaxList);

	aio.close();
	sio.close();
}

main();
