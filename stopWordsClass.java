/*

I took the list of stopwords from  http://norm.al/2009/04/14/list-of-english-stop-words/

*/

import java.util.*;

public class stopWordsClass {
public ArrayList<String> stopWordsListMethod() {
ArrayList<String> stopWordsList = new ArrayList<String>();
    stopWordsList.add("a");
    stopWordsList.add("able");
    stopWordsList.add("about");
    stopWordsList.add("above");
    stopWordsList.add("according");
    stopWordsList.add("accordingly");
    stopWordsList.add("across");
    stopWordsList.add("actually");
    stopWordsList.add("after");
    stopWordsList.add("afterwards");
    stopWordsList.add("again");
    stopWordsList.add("against");
    stopWordsList.add("all");
    stopWordsList.add("allow");
    stopWordsList.add("allows");
    stopWordsList.add("almost");
    stopWordsList.add("alone");
    stopWordsList.add("along");
    stopWordsList.add("already");
    stopWordsList.add("also");
    stopWordsList.add("although");
    stopWordsList.add("always");
    stopWordsList.add("am");
    stopWordsList.add("among");
    stopWordsList.add("amongst");
    stopWordsList.add("an");
    stopWordsList.add("and");
    stopWordsList.add("another");
    stopWordsList.add("any");
    stopWordsList.add("anybody");
    stopWordsList.add("anyhow");
    stopWordsList.add("anyone");
    stopWordsList.add("anything");
    stopWordsList.add("anyway");
    stopWordsList.add("anyways");
    stopWordsList.add("anywhere");
    stopWordsList.add("apart");
    stopWordsList.add("appear");
    stopWordsList.add("appreciate");
    stopWordsList.add("appropriate");
    stopWordsList.add("are");
    stopWordsList.add("around");
    stopWordsList.add("as");
    stopWordsList.add("aside");
    stopWordsList.add("ask");
    stopWordsList.add("asking");
    stopWordsList.add("associated");
    stopWordsList.add("at");
    stopWordsList.add("available");
    stopWordsList.add("away");
    stopWordsList.add("awfully");
    stopWordsList.add("b");
    stopWordsList.add("be");
    stopWordsList.add("became");
    stopWordsList.add("because");
    stopWordsList.add("become");
    stopWordsList.add("becomes");
    stopWordsList.add("becoming");
    stopWordsList.add("been");
    stopWordsList.add("before");
    stopWordsList.add("beforehand");
    stopWordsList.add("behind");
    stopWordsList.add("being");
    stopWordsList.add("believe");
    stopWordsList.add("below");
    stopWordsList.add("beside");
    stopWordsList.add("besides");
    stopWordsList.add("best");
    stopWordsList.add("better");
    stopWordsList.add("between");
    stopWordsList.add("beyond");
    stopWordsList.add("both");
    stopWordsList.add("brief");
    stopWordsList.add("but");
    stopWordsList.add("by");
    stopWordsList.add("c");
    stopWordsList.add("came");
    stopWordsList.add("can");
    stopWordsList.add("cannot");
    stopWordsList.add("cant");
    stopWordsList.add("cause");
    stopWordsList.add("causes");
    stopWordsList.add("certain");
    stopWordsList.add("certainly");
    stopWordsList.add("changes");
    stopWordsList.add("clearly");
    stopWordsList.add("co");
    stopWordsList.add("com");
    stopWordsList.add("come");
    stopWordsList.add("comes");
    stopWordsList.add("concerning");
    stopWordsList.add("consequently");
    stopWordsList.add("consider");
    stopWordsList.add("considering");
    stopWordsList.add("contain");
    stopWordsList.add("containing");
    stopWordsList.add("contains");
    stopWordsList.add("corresponding");
    stopWordsList.add("could");
    stopWordsList.add("course");
    stopWordsList.add("currently");
    stopWordsList.add("d");
    stopWordsList.add("definitely");
    stopWordsList.add("described");
    stopWordsList.add("despite");
    stopWordsList.add("did");
    stopWordsList.add("different");
    stopWordsList.add("do");
    stopWordsList.add("does");
    stopWordsList.add("doing");
    stopWordsList.add("done");
    stopWordsList.add("down");
    stopWordsList.add("downwards");
    stopWordsList.add("during");
    stopWordsList.add("e");
    stopWordsList.add("each");
    stopWordsList.add("edu");
    stopWordsList.add("eg");
    stopWordsList.add("eight");
    stopWordsList.add("either");
    stopWordsList.add("else");
    stopWordsList.add("elsewhere");
    stopWordsList.add("enough");
    stopWordsList.add("entirely");
    stopWordsList.add("especially");
    stopWordsList.add("et");
    stopWordsList.add("etc");
    stopWordsList.add("even");
    stopWordsList.add("ever");
    stopWordsList.add("every");
    stopWordsList.add("everybody");
    stopWordsList.add("everyone");
    stopWordsList.add("everything");
    stopWordsList.add("everywhere");
    stopWordsList.add("ex");
    stopWordsList.add("exactly");
    stopWordsList.add("example");
    stopWordsList.add("except");
    stopWordsList.add("f");
    stopWordsList.add("far");
    stopWordsList.add("few");
    stopWordsList.add("fifth");
    stopWordsList.add("first");
    stopWordsList.add("five");
    stopWordsList.add("followed");
    stopWordsList.add("following");
    stopWordsList.add("follows");
    stopWordsList.add("for");
    stopWordsList.add("former");
    stopWordsList.add("formerly");
    stopWordsList.add("forth");
    stopWordsList.add("four");
    stopWordsList.add("from");
    stopWordsList.add("further");
    stopWordsList.add("furthermore");
    stopWordsList.add("g");
    stopWordsList.add("get");
    stopWordsList.add("gets");
    stopWordsList.add("getting");
    stopWordsList.add("given");
    stopWordsList.add("gives");
    stopWordsList.add("go");
    stopWordsList.add("goes");
    stopWordsList.add("going");
    stopWordsList.add("gone");
    stopWordsList.add("got");
    stopWordsList.add("gotten");
    stopWordsList.add("greetings");
    stopWordsList.add("h");
    stopWordsList.add("had");
    stopWordsList.add("happens");
    stopWordsList.add("hardly");
    stopWordsList.add("has");
    stopWordsList.add("have");
    stopWordsList.add("having");
    stopWordsList.add("he");
    stopWordsList.add("hello");
    stopWordsList.add("help");
    stopWordsList.add("hence");
    stopWordsList.add("her");
    stopWordsList.add("here");
    stopWordsList.add("hereafter");
    stopWordsList.add("hereby");
    stopWordsList.add("herein");
    stopWordsList.add("hereupon");
    stopWordsList.add("hers");
    stopWordsList.add("herself");
    stopWordsList.add("hi");
    stopWordsList.add("him");
    stopWordsList.add("himself");
    stopWordsList.add("his");
    stopWordsList.add("hither");
    stopWordsList.add("hopefully");
    stopWordsList.add("how");
    stopWordsList.add("howbeit");
    stopWordsList.add("however");
    stopWordsList.add("i");
    stopWordsList.add("ie");
    stopWordsList.add("if");
    stopWordsList.add("ignored");
    stopWordsList.add("immediate");
    stopWordsList.add("in");
    stopWordsList.add("inasmuch");
    stopWordsList.add("inc");
    stopWordsList.add("indeed");
    stopWordsList.add("indicate");
    stopWordsList.add("indicated");
    stopWordsList.add("indicates");
    stopWordsList.add("inner");
    stopWordsList.add("insofar");
    stopWordsList.add("instead");
    stopWordsList.add("into");
    stopWordsList.add("inward");
    stopWordsList.add("is");
    stopWordsList.add("it");
    stopWordsList.add("its");
    stopWordsList.add("itself");
    stopWordsList.add("j");
    stopWordsList.add("just");
    stopWordsList.add("k");
    stopWordsList.add("keep");
    stopWordsList.add("keeps");
    stopWordsList.add("kept");
    stopWordsList.add("know");
    stopWordsList.add("knows");
    stopWordsList.add("known");
    stopWordsList.add("l");
    stopWordsList.add("last");
    stopWordsList.add("lately");
    stopWordsList.add("later");
    stopWordsList.add("latter");
    stopWordsList.add("latterly");
    stopWordsList.add("least");
    stopWordsList.add("less");
    stopWordsList.add("lest");
    stopWordsList.add("let");
    stopWordsList.add("like");
    stopWordsList.add("liked");
    stopWordsList.add("likely");
    stopWordsList.add("little");
    stopWordsList.add("ll"); 
    stopWordsList.add("look");
    stopWordsList.add("looking");
    stopWordsList.add("looks");
    stopWordsList.add("ltd");
    stopWordsList.add("m");
    stopWordsList.add("mainly");
    stopWordsList.add("many");
    stopWordsList.add("may");
    stopWordsList.add("maybe");
    stopWordsList.add("me");
    stopWordsList.add("mean");
    stopWordsList.add("meanwhile");
    stopWordsList.add("merely");
    stopWordsList.add("might");
    stopWordsList.add("more");
    stopWordsList.add("moreover");
    stopWordsList.add("most");
    stopWordsList.add("mostly");
    stopWordsList.add("much");
    stopWordsList.add("must");
    stopWordsList.add("my");
    stopWordsList.add("myself");
    stopWordsList.add("n");
    stopWordsList.add("name");
    stopWordsList.add("namely");
    stopWordsList.add("nd");
    stopWordsList.add("near");
    stopWordsList.add("nearly");
    stopWordsList.add("necessary");
    stopWordsList.add("need");
    stopWordsList.add("needs");
    stopWordsList.add("neither");
    stopWordsList.add("never");
    stopWordsList.add("nevertheless");
    stopWordsList.add("new");
    stopWordsList.add("next");
    stopWordsList.add("nine");
    stopWordsList.add("no");
    stopWordsList.add("nobody");
    stopWordsList.add("non");
    stopWordsList.add("none");
    stopWordsList.add("noone");
    stopWordsList.add("nor");
    stopWordsList.add("normally");
    stopWordsList.add("not");
    stopWordsList.add("nothing");
    stopWordsList.add("novel");
    stopWordsList.add("now");
    stopWordsList.add("nowhere");
    stopWordsList.add("o");
    stopWordsList.add("obviously");
    stopWordsList.add("of");
    stopWordsList.add("off");
    stopWordsList.add("often");
    stopWordsList.add("oh");
    stopWordsList.add("ok");
    stopWordsList.add("okay");
    stopWordsList.add("old");
    stopWordsList.add("on");
    stopWordsList.add("once");
    stopWordsList.add("one");
    stopWordsList.add("ones");
    stopWordsList.add("only");
    stopWordsList.add("onto");
    stopWordsList.add("or");
    stopWordsList.add("other");
    stopWordsList.add("others");
    stopWordsList.add("otherwise");
    stopWordsList.add("ought");
    stopWordsList.add("our");
    stopWordsList.add("ours");
    stopWordsList.add("ourselves");
    stopWordsList.add("out");
    stopWordsList.add("outside");
    stopWordsList.add("over");
    stopWordsList.add("overall");
    stopWordsList.add("own");
    stopWordsList.add("p");
    stopWordsList.add("particular");
    stopWordsList.add("particularly");
    stopWordsList.add("per");
    stopWordsList.add("perhaps");
    stopWordsList.add("placed");
    stopWordsList.add("please");
    stopWordsList.add("plus");
    stopWordsList.add("possible");
    stopWordsList.add("presumably");
    stopWordsList.add("probably");
    stopWordsList.add("provides");
    stopWordsList.add("q");
    stopWordsList.add("que");
    stopWordsList.add("quite");
    stopWordsList.add("qv");
    stopWordsList.add("r");
    stopWordsList.add("rather");
    stopWordsList.add("rd");
    stopWordsList.add("re");
    stopWordsList.add("really");
    stopWordsList.add("reasonably");
    stopWordsList.add("regarding");
    stopWordsList.add("regardless");
    stopWordsList.add("regards");
    stopWordsList.add("relatively");
    stopWordsList.add("respectively");
    stopWordsList.add("right");
    stopWordsList.add("s");
    stopWordsList.add("said");
    stopWordsList.add("same");
    stopWordsList.add("saw");
    stopWordsList.add("say");
    stopWordsList.add("saying");
    stopWordsList.add("says");
    stopWordsList.add("second");
    stopWordsList.add("secondly");
    stopWordsList.add("see");
    stopWordsList.add("seeing");
    stopWordsList.add("seem");
    stopWordsList.add("seemed");
    stopWordsList.add("seeming");
    stopWordsList.add("seems");
    stopWordsList.add("seen");
    stopWordsList.add("self");
    stopWordsList.add("selves");
    stopWordsList.add("sensible");
    stopWordsList.add("sent");
    stopWordsList.add("serious");
    stopWordsList.add("seriously");
    stopWordsList.add("seven");
    stopWordsList.add("several");
    stopWordsList.add("shall");
    stopWordsList.add("she");
    stopWordsList.add("should");
    stopWordsList.add("since");
    stopWordsList.add("six");
    stopWordsList.add("so");
    stopWordsList.add("some");
    stopWordsList.add("somebody");
    stopWordsList.add("somehow");
    stopWordsList.add("someone");
    stopWordsList.add("something");
    stopWordsList.add("sometime");
    stopWordsList.add("sometimes");
    stopWordsList.add("somewhat");
    stopWordsList.add("somewhere");
    stopWordsList.add("soon");
    stopWordsList.add("sorry");
    stopWordsList.add("specified");
    stopWordsList.add("specify");
    stopWordsList.add("specifying");
    stopWordsList.add("still");
    stopWordsList.add("sub");
    stopWordsList.add("such");
    stopWordsList.add("sup");
    stopWordsList.add("sure");
    stopWordsList.add("t");
    stopWordsList.add("take");
    stopWordsList.add("taken");
    stopWordsList.add("tell");
    stopWordsList.add("tends");
    stopWordsList.add("th");
    stopWordsList.add("than");
    stopWordsList.add("thank");
    stopWordsList.add("thanks");
    stopWordsList.add("thanx");
    stopWordsList.add("that");
    stopWordsList.add("thats");
    stopWordsList.add("the");
    stopWordsList.add("their");
    stopWordsList.add("theirs");
    stopWordsList.add("them");
    stopWordsList.add("themselves");
    stopWordsList.add("then");
    stopWordsList.add("thence");
    stopWordsList.add("there");
    stopWordsList.add("thereafter");
    stopWordsList.add("thereby");
    stopWordsList.add("therefore");
    stopWordsList.add("therein");
    stopWordsList.add("theres");
    stopWordsList.add("thereupon");
    stopWordsList.add("these");
    stopWordsList.add("they");
    stopWordsList.add("think");
    stopWordsList.add("third");
    stopWordsList.add("this");
    stopWordsList.add("thorough");
    stopWordsList.add("thoroughly");
    stopWordsList.add("those");
    stopWordsList.add("though");
    stopWordsList.add("three");
    stopWordsList.add("through");
    stopWordsList.add("throughout");
    stopWordsList.add("thru");
    stopWordsList.add("thus");
    stopWordsList.add("to");
    stopWordsList.add("together");
    stopWordsList.add("too");
    stopWordsList.add("took");
    stopWordsList.add("toward");
    stopWordsList.add("towards");
    stopWordsList.add("tried");
    stopWordsList.add("tries");
    stopWordsList.add("truly");
    stopWordsList.add("try");
    stopWordsList.add("trying");
    stopWordsList.add("twice");
    stopWordsList.add("two");
    stopWordsList.add("u");
    stopWordsList.add("un");
    stopWordsList.add("under");
    stopWordsList.add("unfortunately");
    stopWordsList.add("unless");
    stopWordsList.add("unlikely");
    stopWordsList.add("until");
    stopWordsList.add("unto");
    stopWordsList.add("up");
    stopWordsList.add("upon");
    stopWordsList.add("us");
    stopWordsList.add("use");
    stopWordsList.add("used");
    stopWordsList.add("useful");
    stopWordsList.add("uses");
    stopWordsList.add("using");
    stopWordsList.add("usually");
    stopWordsList.add("uucp");
    stopWordsList.add("v");
    stopWordsList.add("value");
    stopWordsList.add("various");
    stopWordsList.add("ve");
    stopWordsList.add("very");
    stopWordsList.add("via");
    stopWordsList.add("viz");
    stopWordsList.add("vs");
    stopWordsList.add("w");
    stopWordsList.add("want");
    stopWordsList.add("wants");
    stopWordsList.add("was");
    stopWordsList.add("way");
    stopWordsList.add("we");
    stopWordsList.add("welcome");
    stopWordsList.add("well");
    stopWordsList.add("went");
    stopWordsList.add("were");
    stopWordsList.add("what");
    stopWordsList.add("whatever");
    stopWordsList.add("when");
    stopWordsList.add("whence");
    stopWordsList.add("whenever");
    stopWordsList.add("where");
    stopWordsList.add("whereafter");
    stopWordsList.add("whereas");
    stopWordsList.add("whereby");
    stopWordsList.add("wherein");
    stopWordsList.add("whereupon");
    stopWordsList.add("wherever");
    stopWordsList.add("whether");
    stopWordsList.add("which");
    stopWordsList.add("while");
    stopWordsList.add("whither");
    stopWordsList.add("who");
    stopWordsList.add("whoever");
    stopWordsList.add("whole");
    stopWordsList.add("whom");
    stopWordsList.add("whose");
    stopWordsList.add("why");
    stopWordsList.add("will");
    stopWordsList.add("willing");
    stopWordsList.add("wish");
    stopWordsList.add("with");
    stopWordsList.add("within");
    stopWordsList.add("without");
    stopWordsList.add("wonder");
    stopWordsList.add("would");
    stopWordsList.add("would");
    stopWordsList.add("x");
    stopWordsList.add("y");
    stopWordsList.add("yes");
    stopWordsList.add("yet");
    stopWordsList.add("you");
    stopWordsList.add("your");
    stopWordsList.add("yours");
    stopWordsList.add("yourself");
    stopWordsList.add("yourselves");
    stopWordsList.add("z");
    stopWordsList.add("zero");
	return stopWordsList;
}
}
