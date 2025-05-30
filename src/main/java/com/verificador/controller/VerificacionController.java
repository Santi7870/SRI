package com.verificador.controller;

import com.verificador.service.VerificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/verificacion")
@CrossOrigin(origins = "*")
public class VerificacionController {

    @Autowired
    private VerificacionService verificacionService;

    @GetMapping("/contribuyente")
    public ResponseEntity<?> obtenerDatosContribuyente(@RequestParam String ruc) {
        Object datos = verificacionService.obtenerContribuyente(ruc);
        if (datos == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se pudo obtener información para el RUC proporcionado.");
        }
        return ResponseEntity.ok(datos);
    }

    @GetMapping("/datos-vehiculo")
    public ResponseEntity<?> obtenerDatosVehiculo(@RequestParam String placa) {
        Object datos = verificacionService.obtenerInformacionVehiculo(placa);
        if (datos == null || datos.toString().contains("no existe") || datos.toString().contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehículo no encontrado");
        }
        return ResponseEntity.ok(datos);
    }




}

