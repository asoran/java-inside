package fr.umlv.java.inside;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MainTest {

	public static class Alien {
	
		private final String planet;
		private final int age;
	
		public Alien(String planet, int age) {
			if (age <= 0) {
				throw new IllegalArgumentException("Too young...");
			}
			this.planet = Objects.requireNonNull(planet);
			this.age = age;
		}
	
		public String getPlanet() {
			return planet;
		}
	
		public int getAge() {
			return age;
		}
	}

	public static class Person {

		private final String firstName;
		private final String lastName;

		public Person(String firstName, String lastName) {
			this.firstName = Objects.requireNonNull(firstName);
			this.lastName = Objects.requireNonNull(lastName);
		}

		public String getFirstName() {
			return firstName;
		}

		public String getLastName() {
			return lastName;
		}
	}

	@Test
	@Tag("toJSON_Person")
	public void testToJSONWithPerson() {
		var p = new Person("Altan", "Le bg");
		assertEquals("{\n" + 
				"	class : class fr.umlv.java.inside.MainTest$Person,\n" +
				"	firstName : Altan,\n" + 
				"	lastName : Le bg\n" +  
				"}", Main.toJSON(p));
	}

	@Test
	@Tag("toJSON_Alien")
	public void testToJSONWithAlien() {
		var p = new Alien("Jupiter", 5);
		assertEquals("{\n" +
				"	age : 5,\n" + 
				"	class : class fr.umlv.java.inside.MainTest$Alien,\n" +
				"	planet : Jupiter\n" + 
				"}", Main.toJSON(p));
	}

}
