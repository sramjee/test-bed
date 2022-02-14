package com.ap;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;

public class HashTest {
	public static void main(String[] args) {
		// The following code to create an head/tail toss logic using random...
		for (int i = 0; i < 3; i++)
			System.out.println((int) (Math.random() * 2 + 1));

		String[] strArray = new String[15];

		for (int i = 0; i < strArray.length; i++)
			strArray[i] = Double.toString(Math.random() + Math.random());

		System.out.println(Integer.toBinaryString(15));

		int n = 15;

		for (String s : strArray) {
			int hashCode = s.hashCode();
			System.out.println(s + "\t" + StringUtils.leftPad(Integer.toBinaryString(hashCode), 32, "0") + "\t" + (hashCode & n));
		}
		
		System.out.println("");

		for (String s : strArray) {
			int hashCode = s.hashCode();
			System.out.println(s + "\t" + Strings.padStart(Integer.toBinaryString(hashCode), 32, '0') + "\t" + (hashCode & n));
		}
	}
}
