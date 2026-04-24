package com.example.demo.controller;

import com.example.demo.model.Materia;
import com.example.demo.repository.MateriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materias")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MateriaController {

    private final MateriaRepository materiaRepository;

    // 1. Listar todas las materias
    @GetMapping
    public ResponseEntity<List<Materia>> listarTodas() {
        return ResponseEntity.ok(materiaRepository.findAll());
    }

    // 2. Consultar una materia por id
    @GetMapping("/{id}")
    public ResponseEntity<Materia> obtenerPorId(@PathVariable Long id) {
        return materiaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Crear una materia
    @PostMapping
    public ResponseEntity<Materia> crear(@RequestBody Materia materia) {
        Materia nuevaMateria = materiaRepository.save(materia);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMateria);
    }

    // 4. Actualizar una materia
    @PutMapping("/{id}")
    public ResponseEntity<Materia> actualizar(@PathVariable Long id, @RequestBody Materia materiaDetalles) {
        return materiaRepository.findById(id)
                .map(materia -> {
                    materia.setNombre(materiaDetalles.getNombre());
                    materia.setCodigo(materiaDetalles.getCodigo());
                    materia.setCreditos(materiaDetalles.getCreditos());
                    return ResponseEntity.ok(materiaRepository.save(materia));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. Eliminar una materia
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (materiaRepository.existsById(id)) {
            materiaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}