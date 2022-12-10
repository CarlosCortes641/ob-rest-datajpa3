package com.example.obrestdatajpa.controller;

import com.example.obrestdatajpa.entitites.Laptop;
import com.example.obrestdatajpa.repository.LaptopRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {


    // atributos
    private LaptopRepository laptopRepository;

    // constructores
       public LaptopController(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    // CRUD sobre la entidad Laptop

    // Buscar todos los laptos

    /**
     * http://localhost:8081/api/laptops
     * @return
     */

    @GetMapping("/api/laptops")
    @ApiOperation("Buscar todos los laptops")
    public List<Laptop> findAll(){
        // recuperar y devolver los laptops de base de datos
        return laptopRepository.findAll();

    }

    // buscar un solo laptop de base de datos de datos
    @GetMapping("/api/laptops/{id}")
    @ApiOperation("Buscar un laptop por clave primaria id Long")
    public ResponseEntity<Laptop> findOneById(@ApiParam("Clave primaria tipo Long") @PathVariable Long id) {

        Optional<Laptop> lapOpt = laptopRepository.findById(id);
        if(lapOpt.isPresent())
            return ResponseEntity.ok(lapOpt.get());
        else return ResponseEntity.notFound().build();

        // opcion 2
        // return bookOpt.orElse(null);
        // return bookOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }
    /**
     * Crear un nuevo libro en base de datos
     * Método POST, no colisiona con findAll porque son diferentes métodos HTTP: GET vs. POST
     * @param laptop
     * @param headers
     * @return
     */

    @PostMapping("/api/laptops")
    @ApiOperation("Crear un laptop")
    public ResponseEntity<Laptop> create(@RequestBody Laptop laptop, @RequestHeader HttpHeaders headers){
        System.out.println(headers.get("User-Agent"));
        // guardar el laptop recibido por parámetro en la base de datos
        if(laptop.getId() != null){
            System.out.println("Tratando de crear un laptop con id");
            return ResponseEntity.badRequest().build();
        }
        Laptop result = laptopRepository.save(laptop);
        return ResponseEntity.ok(result); // el laptop devuelto tiene una clave primaria
    }

    /**
     * Actualizar un laptop en base de datos
     */
    @PutMapping("/api/laptops")
    @ApiOperation("Actualizar un laptop")
    public ResponseEntity<Laptop> update(@RequestBody Laptop laptop) {
        if(laptop.getId() == null){ // si no tiene id quiere decir que si es una creación
            System.out.println("Tratando de actualizar un laptop no existente en la BD");
            return ResponseEntity.badRequest().build();
        }
        if(!laptopRepository.existsById(laptop.getId())){
            System.out.println("Tratando de actualizar un laptop no existente en la BD");
            return ResponseEntity.notFound().build();
        }
        // El proceso de actualización
        Laptop result = laptopRepository.save(laptop);
        return ResponseEntity.ok(result);  // el laptop devuelto tiene clave primaria
    }

    /**
     * Eliminar un laptop en base de datos
     */
    @ApiIgnore // ignorar este método para que no aparezca en la documentación de la api Swagger
    @DeleteMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> delete(@PathVariable Long id){
        if(!laptopRepository.existsById(id)){
            System.out.println("Tratando de eliminar un laptop no existente en la BD");
            return ResponseEntity.notFound().build();
        }
        laptopRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Eliminar todos los laptops en base de datos
     */
    @ApiIgnore // ignorar este método para que no aparezca en la documentación de la api Swagger
    @DeleteMapping("/api/laptops")
    public ResponseEntity<Laptop> deleteAll(){
        System.out.println("Eliminando todos los laptos de la BD");
        laptopRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
