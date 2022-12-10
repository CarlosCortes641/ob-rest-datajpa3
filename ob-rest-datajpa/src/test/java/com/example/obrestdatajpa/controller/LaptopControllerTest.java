package com.example.obrestdatajpa.controller;

import com.example.obrestdatajpa.entitites.Laptop;
import com.example.obrestdatajpa.repository.LaptopRepository;
import com.sun.istack.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControllerTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;
    @Autowired
    private LaptopRepository laptopRepository;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @DisplayName("Comprobar consulta de todos los laptops")
    @Test
    void findAll() {
        ResponseEntity<Laptop[]> response =
                testRestTemplate.getForEntity("/api/laptops", Laptop[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Laptop> laptops = Arrays.asList(response.getBody());
        System.out.println(laptops.size());
    }

    @DisplayName("Comprobar consulta de un laptop")
    @Test
    void findOneById() {
        ResponseEntity<Laptop> response =
                testRestTemplate.getForEntity("/api/laptops/1", Laptop.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("Comprobar creación de un laptop")
    @Test
    void create() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                 {
                 "marca": "Apple",
                 "modelo": "Air",
                 "version": 5,
                 "precio": 299.99,
                 "online": true
                 }
                """;
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);
        Laptop result = response.getBody();

        assertEquals(1L, result.getId());
        assertEquals("Apple", result.getMarca());
    }

    @DisplayName("Comprobar actualización de un laptop")
    @Test
    void update() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                 {
                 "marca": "Apple",
                 "modelo": "Air",
                 "version": 5,
                 "precio": 299.99,
                 "online": true
                 }
                """;
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);
        Laptop result = response.getBody();

        assertEquals(3L, result.getId());

        if (result.getId() != 0) {
            HttpHeaders headers2 = new HttpHeaders();
            headers2.setContentType(MediaType.APPLICATION_JSON);
            headers2.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            String json2 = """
                     {
                     "marca": "Apple2",
                     "modelo": "Air",
                     "version": 5,
                     "precio": 299.99,
                     "online": true
                     }
                    """;
            HttpEntity<String> request2 = new HttpEntity<>(json2, headers2);
            ResponseEntity<Laptop> response2 = testRestTemplate.exchange("/api/laptops/1", HttpMethod.PUT, request2, Laptop.class);
            Laptop result2 = response2.getBody();

            assertEquals(null, result2.getId());
        }

    }

    @DisplayName("Comprobar eliminación de un laptop")
    @Test
    void delete() {
        //Creando un laptop para eliminar
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                 {
                 "marca": "Apple",
                 "modelo": "Air",
                 "version": 5,
                 "precio": 299.99,
                 "online": true
                 }
                """;
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);
        Laptop result = response.getBody();

        assertEquals(2L, result.getId());
        // Eliminando laptop
        if (result.getId() == 1l) {
            ResponseEntity<Laptop> response2 = testRestTemplate.exchange("/api/laptops/1", HttpMethod.DELETE, request, Laptop.class);
            Laptop result2 = response2.getBody();
            assert result2 == null;

        }

    }

    @DisplayName("Comprobar eliminación de todos los laptops de la BD")
    @Test
    void deleteAll() {
        //Creando laptops
        for (int i = 0; i < 10; i++) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            String json = """
                     {
                     "marca": "Apple1",
                     "modelo": "Air",
                     "version": 5,
                     "precio": 299.99,
                     "online": true
                     }
                    """;
            HttpEntity<String> request = new HttpEntity<>(json, headers);
            ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.POST, request, Laptop.class);
            Laptop result = response.getBody();
            assert result != null;
        }
        // Consultando todos los laptops creados
        ResponseEntity<Laptop[]> response =
                testRestTemplate.getForEntity("/api/laptops", Laptop[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Laptop> laptops = Arrays.asList(response.getBody());
        System.out.println(laptops.size());

        // Eliminando todos los laptops
        if (laptops.size() != 0) {
            HttpHeaders headers2 = new HttpHeaders();
            headers2.setContentType(MediaType.APPLICATION_JSON);
            headers2.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            String json2 = """
                     {
                     "marca": "Apple2",
                     "modelo": "Air",
                     "version": 5,
                     "precio": 299.99,
                     "online": true
                     }
                    """;
            HttpEntity<String> request2 = new HttpEntity<>(json2, headers2);
            ResponseEntity<Laptop> response2 = testRestTemplate.exchange("/api/laptops/*", HttpMethod.DELETE, request2, Laptop.class);
            Laptop result2 = response2.getBody();

            assertEquals(null, result2.getId());
            assert result2 != null;

            if (result2.getId() == null) {
                System.out.println("Se eliminaron todos los laptops de la BD");
            } else {
                System.out.println("No se han eliminado todos los laptops de la BD");
            }


        }
    }
}