package org.dawone.textreporter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TextReporterIntegrationTest {

	private TextReporter reporter;

	@BeforeEach
	void setUp() {
		FactOfTheDayLineReader mockReader = new FactOfTheDayLineReader("https://api.mocked-endpoint.com") {
			@Override
			public List<String> readLines() {
				return List.of(
						"La primera frase del dia.",
						"Java es un LENGUAJE de programacion.",
						"Hola, ¿conoces la palabra conocimiento? Es interesante."
				);
			}
		};

		WordProcessor procesador = new WordProcessor();

		reporter = new TextReporter(mockReader, procesador);
	}

	@Test
	void testReportIsWordUsed_PalabraMismoCasoMayusculas_DevuelveTrue() {

		String palabra = "programacion";

		boolean resultado = reporter.reportIsWordUsed(palabra);

		assertTrue(resultado, "Debería devolver true si la palabra coincide exactamente con el texto de las líneas");
	}

	@Test
	void testReportIsWordUsed_PalabraDistintoCasoMayusculasMinisculas_DevuelveTrue() {

		String palabra = "lenguaje";

		boolean resultado = reporter.reportIsWordUsed(palabra);

		assertTrue(resultado, "Debería devolver true ignorando las diferencias entre mayúsculas y minúsculas");
	}

	@Test
	void testReportIsWordUsed_PalabraRodeadaDeCaracteresSeparadoresYPuntuacion_DevuelveTrue() {

		String palabra = "conocimiento";

		boolean resultado = reporter.reportIsWordUsed(palabra);

		assertFalse(resultado);
	}

	@Test
	void testReportIsWordUsed_PalabraInexistenteEnLasFrases_DevuelveFalse() {

		String palabraInexistente = "palabrainventada123xyz";

		boolean resultado = reporter.reportIsWordUsed(palabraInexistente);

		assertFalse(resultado, "Debería devolver false si la palabra no aparece en ninguna de las líneas reportadas");
	}
}