package org.dawone.textreporter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WordProcessorTest {

	private WordProcessor processor;

	@BeforeEach
	void prepararEntorno() {
		processor = new WordProcessor();
	}

	@Test
	void testGetWords_TextoConMayusculasYMinusculas_DevuelveArrayConTodasLasPalabrasEnMinisculas() {

		String textoInput = "Hola MUNDO Java";
		String[] esperado = {"hola", "mundo", "java"};

		String[] resultado = processor.getWords(textoInput);

		assertArrayEquals(esperado, resultado);
	}

	@Test
	void testGetWords_TextoRodeadoDeSignosDePuntuacionYEspacios_DevuelvePalabrasLimpiasSinTokensVacios() {

		String textoInput = "¡Hola, mundo! Esto es... una prueba.";
		String[] esperado = {"¡hola", "mundo", "esto", "es", "una", "prueba"};

		String[] resultado = processor.getWords(textoInput);

		assertArrayEquals(esperado, resultado);
	}

	@Test
	void testGetWords_TextoVacioOConSoloDelimitadores_DevuelveArrayVacio() {

		String textoInput = " ,.!!! , ";

		String[] resultado = processor.getWords(textoInput);

		assertNotNull(resultado, "El resultado no debería ser nulo");
		assertEquals(0, resultado.length);
	}

	@Test
	void testGetWords_TextoNulo_LanzaIllegalArgumentExceptionConMensajeExacto() {

		IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
			processor.getWords(null);
		}, "Debería lanzar una IllegalArgumentException si el parámetro de entrada es null");

		String mensajeEsperado = "text must not be null";
		assertEquals(mensajeEsperado, excepcion.getMessage());
	}
}