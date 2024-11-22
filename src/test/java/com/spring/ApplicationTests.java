package com.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // Activar el perfil "test" definido en application-test.properties
class ApplicationTests {

	@Test
	void contextLoads() {
		// Verifica que el contexto de Spring se cargue correctamente
	}
}
