/*******************************************************************************
 * Copyright (c) 2015 Unilever.
 *
 * All rights reserved. Do not distribute any of these files without prior consent from Unilever.
 *
 * Contributors:
 *     Publicis.Sapient - initial API and implementation
 *******************************************************************************/
package com.unilever.d2.dcu.crypto;

/**
 * @author Awanish Kumar
 * @since Sep 6, 2016
 */
public class LamdaExpression {

	interface Student {

		public boolean test(int age, int ag);

	}

	class Teacher {
		public void test1(Student student) {
			System.out.println(student);
		}

	}

	public static void main(String[] args) {

		Teacher teacher = new LamdaExpression().new Teacher();

		Student student = (age, ag) -> {
			System.out.println(age);
			System.out.println(ag);
			return true;
		};

		teacher.test1((age, ag) -> {
			System.out.println(age);
			System.out.println(ag);
			return true;
		});

		Thread thread1 = new Thread(() -> {
			System.out.println("appple");
		});

		Thread thread2 = new Thread(() -> {

		});

		thread1.start();
		thread2.start();

	}
}
