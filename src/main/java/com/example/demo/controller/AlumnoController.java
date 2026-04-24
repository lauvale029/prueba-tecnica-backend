package com.example.demo.controller;

import com.example.demo.model.Alumno;
import com.example.demo.repository.AlumnoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
@CrossOrigin(origins = "*") 
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoRepository alumnoRepository;

    // 1. Listar todos los alumnos
    @GetMapping
    public ResponseEntity<List<Alumno>> listarTodos() {
        return ResponseEntity.ok(alumnoRepository.findAll());
    }

    // 2. Consultar un alumno por id
    @GetMapping("/{id}")
    public ResponseEntity<Alumno> obtenerPorId(@PathVariable Long id) {
        return alumnoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Crear un alumno
    @PostMapping
    public ResponseEntity<Alumno> crear(@RequestBody Alumno alumno) {
        Alumno nuevoAlumno = alumnoRepository.save(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAlumno);
    }

    // 4. Actualizar un alumno
    @PutMapping("/{id}")
    public ResponseEntity<Alumno> actualizar(@PathVariable Long id, @RequestBody Alumno alumnoDetalles) {
        return alumnoRepository.findById(id)
                .map(alumno -> {
                    alumno.setNombre(alumnoDetalles.getNombre());
                    alumno.setApellido(alumnoDetalles.getApellido());
                    alumno.setEmail(alumnoDetalles.getEmail());
                    alumno.setFechaNacimiento(alumnoDetalles.getFechaNacimiento());
                    return ResponseEntity.ok(alumnoRepository.save(alumno));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. Eliminar un alumno
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (alumnoRepository.existsById(id)) {
            alumnoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}