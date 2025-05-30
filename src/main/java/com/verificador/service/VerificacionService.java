package com.verificador.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
public class VerificacionService {

    private final RestTemplate restTemplate = new RestTemplate();

    // Verifica si el RUC existe como contribuyente
// Elimina temporalmente el @Cacheable
// @Cacheable("contribuyentes")
    public boolean esContribuyente(String ruc) {
        try {
            String url = "https://srienlinea.sri.gob.ec/sri-catastro-sujeto-servicio-internet/rest/ConsolidadoContribuyente/existePorNumeroRuc?numeroRuc=" + ruc;
            Boolean result = restTemplate.getForObject(url, Boolean.class);
            System.out.println("✔️ SRI responde: " + result);
            return Boolean.TRUE.equals(result);
        } catch (Exception e) {
            System.out.println("❌ Error consultando SRI: " + e.getMessage());
            return false;
        }
    }


    // Obtiene los datos completos de un contribuyente
    public Object obtenerContribuyente(String ruc) {
        try {
            String url = "https://srienlinea.sri.gob.ec/sri-catastro-sujeto-servicio-internet/rest/ConsolidadoContribuyente/obtenerPorNumerosRuc?&ruc=" + ruc;
            return restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            System.out.println("Error obteniendo datos del contribuyente: " + e.getMessage());
            return null;
        }
    }

    public Object obtenerInformacionVehiculo(String placa) {
        try {
            String url = "https://srienlinea.sri.gob.ec/sri-matriculacion-vehicular-recaudacion-servicio-internet/rest/BaseVehiculo/obtenerPorNumeroPlacaOPorNumeroCampvOPorNumeroCpn?numeroPlacaCampvCpn=" + placa;

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            headers.set("Accept", "application/json");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            System.out.println("Respuesta (raw): " + response.getBody());

            return response.getBody(); // Por ahora como String. Luego lo convertimos si deseas.

        } catch (Exception e) {
            System.out.println("Error consultando vehículo: " + e.getMessage());
            return "Error: " + e.getMessage();
        }
    }

    @Cacheable("licencias")
    public String obtenerInformacionLicencia(String cedula, String placa) {
        // Simulación de datos por baja disponibilidad de la ANT
        try {
            System.out.println("Consultando licencia de: " + cedula + " con placa: " + placa);

            // Este sería el HTML o texto simulado de una respuesta
            String respuestaSimulada = """
            {
              "cedula": "%s",
              "placa": "%s",
              "puntosLicencia": 28,
              "fechaUltimaActualizacion": "2024-12-15"
            }
        """.formatted(cedula, placa);

            return respuestaSimulada;

        } catch (Exception e) {
            System.out.println("Error consultando licencia: " + e.getMessage());
            return "{\"error\": \"No se pudo consultar la licencia\"}";
        }
    }




}
