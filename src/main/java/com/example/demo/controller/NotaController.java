package com.example.demo.controller;

import com.example.demo.model.Nota;
import com.example.demo.repository.NotaRepository;
import com.example.demo.repository.AlumnoRepository;
import com.example.demo.repository.MateriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/notas")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class NotaController {

    private final NotaRepository notaRepository;
    private final AlumnoRepository alumnoRepository;
    private final MateriaRepository materiaRepository;

    // 1. Registrar una nota para un alumno en una materia
    @PostMapping
    public ResponseEntity<?> registrarNota(@RequestBody Nota nota) {
        
        if (nota.getAlumno() == null || nota.getAlumno().getId() == null ||
            nota.getMateria() == null || nota.getMateria().getId() == null) {
            return ResponseEntity.badRequest().body("Se requiere el ID del alumno y de la materia.");
        }

        boolean alumnoExiste = alumnoRepository.existsById(nota.getAlumno().getId());
        boolean materiaExiste = materiaRepository.existsById(nota.getMateria().getId());

        if (!alumnoExiste || !materiaExiste) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El alumno o la materia no existen en la base de datos.");
        }

        
        if (nota.getFechaRegistro() == null) {
            nota.setFechaRegistro(LocalDate.now());
        }

        Nota nuevaNota = notaRepository.save(nota);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaNota);
    }

    // 2. Método general para cargar TODAS las notas 
    @GetMapping
    public ResponseEntity<List<Nota>> listarTodasLasNotas() {
        List<Nota> notas = notaRepository.findAll();
        return ResponseEntity.ok(notas);
    }

    // 3. Listar notas por alumno en cada materia
    @GetMapping("/alumno/{alumnoId}/materia/{materiaId}")
    public ResponseEntity<List<Nota>> listarNotasPorAlumnoYMateria(
            @PathVariable Long alumnoId,
            @PathVariable Long materiaId) {
        
        List<Nota> notas = notaRepository.findByAlumnoIdAndMateriaId(alumnoId, materiaId);
        return ResponseEntity.ok(notas);
    }
}