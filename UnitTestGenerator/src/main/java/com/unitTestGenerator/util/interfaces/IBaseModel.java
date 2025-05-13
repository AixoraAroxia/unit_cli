package com.unitTestGenerator.util.interfaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import static com.unitTestGenerator.util.interfaces.IConstantModel.pathSeparator;

public interface IBaseModel extends IStringEnsambleService{


	public  String Separator = IConstantModel.Separator;
	public  String BREAK_LINE = IConstantModel.BREAK_LINE;

	default String path(List<String> paths) {
		StringBuilder newPathBuilder = new StringBuilder();

		for(int i = 0; paths.size() > i; ++i) {
			if (i != 0 && i + 1 != paths.size() && paths.get(i) != " ") {
				newPathBuilder.append(this.stringEnsamble(Arrays.asList(pathSeparator, (String)paths.get(i))));
			} else if (paths.get(i) != " " && i + 1 == paths.size()) {
				newPathBuilder.append(this.stringEnsamble(Arrays.asList(pathSeparator, (String)paths.get(i))));
			} else if (paths.get(i) != " ") {
				newPathBuilder.append((String)paths.get(i));
			}

			if (paths.get(i) == " ") {
				newPathBuilder.append(pathSeparator);
			}
		}
		return newPathBuilder.toString();
	}



	public static String stringEnsamble2(String... stringPaths) {
		StringBuffer newString = new StringBuffer();
		String[] var3 = stringPaths;
		int var4 = stringPaths.length;

		for(int var5 = 0; var5 < var4; ++var5) {
			String path = var3[var5];
			newString.append(path);
		}
		return newString.toString();
	}

	default List<String> toList(String... StringPaths) {
		List<String> stringList = new ArrayList(Arrays.asList(StringPaths));
		return stringList;
	}

	default String stringPaths(Boolean starSeparator, Boolean endSeparator,String... paths) {
		StringBuffer newString = new StringBuffer();
		List<String> stringPaths = toList(paths);

		if (starSeparator) {
			newString.append(this.Separator);
		}
		stringPaths.stream()
				.reduce((path1, path2) -> {
					newString.append(path1).append(this.Separator);
					return path2;
				})
				.ifPresent(last -> newString.append(last));

		if (endSeparator) {
			newString.append(this.Separator);
		}
		return newString.toString();
	}

	default String packageToPaths(String packageClass){
		String[] valor = packageClass.split("\\.");
		return this.stringPaths(false,false, valor);
	}


	default String listStringToColummString(String... paths) {
		StringBuilder stringColumm = new StringBuilder();
		List<String> parameters = toList(paths);
		if (parameters != null && !parameters.isEmpty()) {
			StringJoiner joiner = new StringJoiner(BREAK_LINE);
			parameters.forEach(joiner::add);
			stringColumm.append(joiner.toString());
		}
		return stringColumm.toString();
	}


	default void printColummStringY(String... paths){
		System.out.println("\u001B[33m" + listStringToColummString(paths));
	}



//	default String listStringStructureToColummString(List<String> parameters) {
//		StringBuilder stringColumm = new StringBuilder(BREAK_LINE);
//		if (parameters != null && parameters.size() > 0) {
//			for(int i = 0; i < parameters.size(); ++i) {
//				stringColumm.append(this.stringEnsamble(Arrays.asList((String)parameters.get(i))));
//				if (i < parameters.size()) {
//					stringColumm.append(BREAK_LINE);
//				}
//			}
//		}
//		return stringColumm.toString();
//	}
//
//	default  String indentation(Integer indentationLevel){
//		StringBuilder indentation = new StringBuilder();
//		if(indentationLevel > 0){
//			int index = 0;
//			while(index < indentationLevel) {
//				indentation.append("\t");
//				index++;
//			}
//		}
//		return indentation.toString();
//	}

//	default String capitalizeFirstLetter(String str) {
//		if (str != null && !str.isEmpty()) {
//			String firstCharInString = str.substring(0, 1).toUpperCase();
//			String remainingCharsInString = str.substring(1);
//			return this.stringEnsamble(Arrays.asList(firstCharInString, remainingCharsInString));
//		} else {
//			return str;
//		}
//	}
	//	default String capitalizeOrUncapitalisedFirstLetter(String str, Character action) {
//		if (str != null && !str.isEmpty()) {
//			String remainingCharsInString = str.substring(1);
//			return action.equals('u') ? this.stringEnsamble(Arrays.asList(str.substring(0, 1).toUpperCase(), remainingCharsInString)) : this.stringEnsamble( Arrays.asList(str.substring(0, 1).toLowerCase(), remainingCharsInString));
//		} else {
//			return str;
//		}
//	}

//	default String toCamelCase(String text) {
//		String[] words = text.split("[\\W_]+");
//		StringBuilder builder = new StringBuilder();
//
//		for(int i = 0; i < words.length; ++i) {
//			String word = words[i];
//			builder.append(i > 0 ? word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase() : word.toLowerCase());
//		}
//
//		return builder.toString();
//	}
//
//	default void printColummString(String... paths){
//		System.out.println(listStringToColummString(paths));
//	}

}
