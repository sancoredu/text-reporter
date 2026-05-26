package org.dawone.textreporter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FactOfTheDayLineReaderTest {

	private FactOfTheDayLineReader reader;

	@BeforeEach
	void setUp() {
		reader = new FactOfTheDayLineReader("https://invalid.url.that.fails");
	}

	@Test
	void testReadLines_ContenidoValidoSimulado_DevuelveListaDeLineasFiltrada() {

		FactOfTheDayLineReader lectorLocal = new FactOfTheDayLineReader("https://api.mock-local.com") {
			@Override
			public List<String> readLines() {
				return List.of("Línea de prueba 1", "Línea de prueba 2");
			}
		};

		List<String> resultado = lectorLocal.readLines();

		assertNotNull(resultado, "La lista devuelta no debería ser nula");
		assertEquals(2, resultado.size());
		assertEquals("Línea de prueba 1", resultado.get(0));
	}
}