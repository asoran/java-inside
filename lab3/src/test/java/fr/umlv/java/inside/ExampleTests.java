package fr.umlv.java.inside;

import static java.lang.invoke.MethodHandles.constant;
import static java.lang.invoke.MethodHandles.dropArguments;
import static java.lang.invoke.MethodHandles.insertArguments;
import static java.lang.invoke.MethodHandles.privateLookupIn;
import static java.lang.invoke.MethodType.methodType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class ExampleTests {
	
	@Test
	@Tag("Question3")
	public void testInvokeAnInstanceHello() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		var ex = new Example();
		assertDoesNotThrow(() -> ex.getClass().getDeclaredMethod("anInstanceHello", int.class));
		var m = ex.getClass().getDeclaredMethod("anInstanceHello", int.class);

		m.setAccessible(true);

		assertEquals(
			"question 2",
			m.invoke(ex, 2)
		);
	}

	@Test
	@Tag("Question3")
	public void testInvokeAStaticHello() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		assertDoesNotThrow(() -> Example.class.getDeclaredMethod("aStaticHello", int.class));
		var m = Example.class.getDeclaredMethod("aStaticHello", int.class);

		m.setAccessible(true);

		assertEquals(
			"question 2",
			m.invoke(null, 2)
		);
		
	}

	@Test
	@Tag("Question4")
	public void textCallAStaticHelloWithLookup() throws Throwable {
		var lookup = MethodHandles.lookup();
		var exampleLookup = privateLookupIn(Example.class, lookup);

		var handle = exampleLookup.findStatic(Example.class, "aStaticHello",
				methodType(String.class, int.class));

		assertEquals("question 2", (String) handle.invokeExact(2));

	}

	@Test
	@Tag("Question5")
	public void textCallAnInstanceHelloWithLookup() throws Throwable {
		var lookup = MethodHandles.lookup();
		var exampleLookup = privateLookupIn(Example.class, lookup);

		var handle = exampleLookup.findVirtual(Example.class, "anInstanceHello",
				methodType(String.class, int.class));

		assertEquals("question 2",
			(String) handle.invokeWithArguments(new Example(), 2));

	}

	@Test
	@Tag("Question6")
	public void testMethodHandlerInsertArgument() throws Throwable {
		var lookup = MethodHandles.lookup();
		var exampleLookup = privateLookupIn(Example.class, lookup);

		var handle = exampleLookup.findStatic(Example.class, "aStaticHello",
				methodType(String.class, int.class));

		var handleWithArgument = insertArguments(handle, 0, 8);
		assertEquals("question 8",
				(String) handleWithArgument.invokeExact());
	}

	@Test
	@Tag("Question7")
	public void testMethodHandlerDropArgumentWorks() throws Throwable {
		var lookup = MethodHandles.lookup();
		var exampleLookup = privateLookupIn(Example.class, lookup);

		var handle = exampleLookup.findStatic(Example.class, "aStaticHello",
				methodType(String.class, int.class));

		var handlerRemove1Argument = dropArguments(handle, 0, int.class);
		var handlerAdd1Argument = insertArguments(handlerRemove1Argument, 0, 8);

		assertEquals("question 3",
				(String) handlerAdd1Argument.invokeExact(3));
	}

	@Test
	@Tag("Question8")
	public void textMethodeHandlesAsType() throws Throwable {
		var lookup = MethodHandles.lookup();
		var exampleLookup = privateLookupIn(Example.class, lookup);

		var handle = exampleLookup.findStatic(Example.class, "aStaticHello",
				methodType(String.class, int.class));

		var integerToIntHandler = handle.asType(methodType(String.class, Integer.class));

		assertEquals("question 3",
				(String) integerToIntHandler.invokeExact(Integer.valueOf(3)));
		
	}
	
	@Test
	@Tag("Question9")
	public void testMethodHandlesConstant() throws Throwable {
		assertEquals(
			(String)constant(String.class, "Question 9").invoke(), "Question 9");
	}

	@Test
	@Tag("Question10")
	public void guardWithTest () throws Throwable {

		var equalsHandle = MethodHandles.lookup()
				.findVirtual(String.class, "equals", methodType(boolean.class, Object.class));

		var guardTest = MethodHandles.guardWithTest(
				equalsHandle.asType(methodType(boolean.class, String.class, String.class)),
				dropArguments(constant(int.class, 1), 0, String.class, String.class),
				dropArguments(constant(int.class, -1), 0, String.class, String.class)
			);

		assertEquals(1,
			(int)guardTest.invokeExact("foo", "foo")
		);
		assertEquals(-1,
			(int)guardTest.invokeExact("foo", "bar")
		);
	}


}
