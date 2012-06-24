package com.uqbar.vainilla.events.constants;

import java.util.HashMap;
import java.util.Map;

// TODO: LEFT_SHIFT(), RIGHT_SHIFT, ALT, ALT_GR, etc... e.getLocation()
// TODO: NO ES ALTISIMAMENTE CHOTO TENER ESTO ASI? QUE PASA CON LOS OTROS TIPOS DE TECLADO? QUIERO MANEJARLO DE OTRA FORMA?
// TODO: TECLADO NUMERICO
// TODO?: ALPHANUMERIC, ALPHABETIC, NUMERIC, ARROWS
public enum Key {
	A("A", 65), B("B",66), C("C",67), D("D",68), E("E",69), F("F",70), G("G",71), H("H",72), I("I",73), J("J",74), K("K",75), L("L",76), M("M",77), N("N",78), Ñ("Ñ",209), //
	O("O",79), P("P",80), Q("Q",81), R("R",82), S("S",83), T("T",84), U("U",85), V("V",86), W("W",87), X("X",88), Y("Y",89), Z("Z",90), //
	ZERO("ZERO",48), ONE("ONE",49), TWO("TWO",50), THREE("THREE",51), FOUR("FOUR",52), FIVE("FIVE",53), SIX("SIX",54), SEVEN("SEVEN",55),// 
	EIGHT("EIGHT",56), NINE("NINE",57), OPEN_BRACKET("OPEN_BRACKET",161), CLOSE_BRACKET("CLOSE_BRACKET",162), COMMA("COMMA",44), DOT("DOT",46),//
	MINUS("MINUS",45), PLUS("PLUS",521), ACCENT("ACCENT",129), QUOTE("QUOTE",222), BACKSPACE("BACKSPACE",8), TAB("TAB",9), ENTER("ENTER",10), //
	ESC("ESC",27), SPACE("SPACE",32), SHIFT("SHIFT",16), CTRL("CTRL",17), ALT("ALT",18), F1("F1",112), F2("F2",113), F3("F3",114), F4("F4",115), //
	F5("F5",116), F6("F6",117), F7("F7",118), F8("F8",119), F9("F9",120), F10("F10",121), F11("F11",122), F12("F12",123), INSERT("INSERT",155), //
	SUPR("SUPR",127), PAUSE("PAUSE",19), PGUP("PGUP",33), PGDN("PGDN",34), LEFT("LEFT",37), UP("UP",38), RIGHT("RIGHT",39), DOWN("DOWN",40), //
	NUMPAD0("NUMPAD0",96), NUMPAD1("NUMPAD1",97), NUMPAD2("NUMPAD2",98),NUMPAD3("NUMPAD3",99), NUMPAD4("NUMPAD4",100),NUMPAD5("NUMPAD5",101), //
	NUMPAD6("NUMPAD6",102), NUMPAD7("NUMPAD7",103), NUMPAD8("NUMPAD8",104), NUMPAD9("NUMPAD9",105),	ANY("",0);

	private static final Map<Integer, Key> KEY_EQUIVALENCES = new HashMap<Integer, Key>() {
		{
			for (Key key : Key.values()) {
				this.put(key.getKeyCode(), key);
			}
		}
	};
	
	private static final Map<String, Key> CODE_EQUIVALENCES = new HashMap<String, Key>() {
		{
			for (Key key : Key.values()) {
				this.put(key.getCode(), key);
			}
		}
	};

	private int keyCode;

	private String code;

	// ****************************************************************
	// ** STATICS
	// ****************************************************************

	public static Key fromKeyCode(int code) {
		return KEY_EQUIVALENCES.containsKey(code) ? KEY_EQUIVALENCES.get(code) : ANY;
	}
	
	public static Key fromCode(String code) {
		return CODE_EQUIVALENCES.containsKey(code) ? CODE_EQUIVALENCES.get(code) : ANY;
	}

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	private Key(String code, int keyCode) {
		this.code = code;
		this.setKeyCode(keyCode);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public int getKeyCode() {
		return this.keyCode;
	};

	private void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}