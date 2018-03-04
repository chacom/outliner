package application;

import java.util.List;

public class WhiteList {

	static boolean checkWhitelist(String text) {
		String[] whitelist = { 	"A1","A2","A3","A4","A5","A6",
								"V1","V2","V3","V4","V5","V6",
								"R1","R2","R3","R4","R5","R6",
								"W1","W2","W3","W4","W5","W6"};

		for (String entry : whitelist) {
			if (text.contains(entry)) {
				return true;
			}
		}

		return false;
	}

	
	static boolean checkWhitelist(List<String> textList) {

		for(String text : textList) {
			if(checkWhitelist(text)) {
				return true;
			}
		}

		return false;
	}
}
