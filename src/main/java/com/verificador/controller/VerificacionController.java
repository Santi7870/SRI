package com.verificador.controller;

import com.verificador.service.VerificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/existe-contribuyente")
    public boolean verificarContribuyente(@RequestParam String ruc) {
        return verificacionService.esContribuyente(ruc);
    }

    @GetMapping("/datos-vehiculo")
    public ResponseEntity<?> obtenerDatosVehiculo(@RequestParam String placa) {
        Object datos = verificacionService.obtenerInformacionVehiculo(placa);
        if (datos == null || datos.toString().contains("no existe") || datos.toString().contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehículo no encontrado");
        }
        return ResponseEntity.ok(datos);
    }

    @GetMapping("/licencia")
    public ResponseEntity<?> obtenerDatosLicencia(@RequestParam String cedula, @RequestParam String placa) {
        String datos = verificacionService.obtenerInformacionLicencia(cedula, placa);
        if (datos.contains("error")) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("No se pudo obtener datos de la licencia");
        }
        return ResponseEntity.ok(datos);
    }

    @GetMapping("/licencia/puntos")
    public ResponseEntity<?> obtenerPuntosLicenciaDesdeCedula(@RequestParam String cedula) {
        String datos = verificacionService.obtenerPuntosLicenciaDesdeWeb(cedula);
        return ResponseEntity.ok(datos);
    }
}
