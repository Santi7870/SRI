package com.verificador.service;

import com.verificador.selenium.ANTPuntosLicenciaScraper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VerificacionService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Cacheable("contribuyentes")
    public boolean esContribuyente(String ruc) {
        try {
            String url = "https://srienlinea.sri.gob.ec/sri-catastro-sujeto-servicio-internet/rest/ConsolidadoContribuyente/existePorNumeroRuc?numeroRuc=" + ruc;
            Boolean result = restTemplate.getForObject(url, Boolean.class);
            return Boolean.TRUE.equals(result);
        } catch (Exception e) {
            return false;
        }
    }

    @Cacheable("vehiculos")
    public Object obtenerInformacionVehiculo(String placa) {
        try {
            String url = "https://srienlinea.sri.gob.ec/sri-matriculacion-vehicular-recaudacion-servicio-internet/rest/BaseVehiculo/obtenerPorNumeroPlacaOPorNumeroCampvOPorNumeroCpn?numeroPlacaCampvCpn=" + placa;

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @Cacheable(value = "licencias", key = "#cedula + '-' + #placa")
    public String obtenerInformacionLicencia(String cedula, String placa) {
        return """
        {
          "cedula": "%s",
          "placa": "%s",
          "puntosLicencia": 28,
          "fechaUltimaActualizacion": "2024-12-15"
        }
        """.formatted(cedula, placa);
    }

    @Cacheable(value = "puntosLicencia", key = "#cedula")
    public String obtenerPuntosLicenciaDesdeWeb(String cedula) {
        System.out.println("➡️ Consultando puntos desde la web de ANT para: " + cedula);
        return ANTPuntosLicenciaScraper.obtenerPuntosDesdeUrl(cedula);
    }


    public Object obtenerContribuyente(String ruc) {
        try {
            String url = "https://srienlinea.sri.gob.ec/sri-catastro-sujeto-servicio-internet/rest/ConsolidadoContribuyente/obtenerPorNumerosRuc?&ruc=" + ruc;
            return restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            return null;
        }
    }
}

