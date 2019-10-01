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

		@JSONProperty
		public String getPlanet() {
			return planet;
		}

		@JSONProperty
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

		@JSONProperty(name = "prenom")
		public String getFirstName() {
			return firstName;
		}

		@JSONProperty(name = "nom")
		public String getLastName() {
			return lastName;
		}
	}

	@Test
	@Tag("toJSON_Person")
	public void testToJSONWithPerson() {
		var p = new Person("Altan", "Le bg");
		assertEquals("{\n" + 
				"	prenom : Altan,\n" + 
				"	nom : Le bg\n" +  
				"}", Main.toJSON(p));
	}

	@Test
	@Tag("toJSON_Alien")
	public void testToJSONWithAlien() {
		var p = new Alien("Jupiter", 5);
		assertEquals("{\n" +
				"	age : 5,\n" + 
				"	planet : Jupiter\n" + 
				"}", Main.toJSON(p));
	}

}
