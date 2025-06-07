package com.verificador.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class ANTPuntosLicenciaScraper {

    public static String obtenerPuntosDesdeUrl(String cedula) {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        WebDriver driver = new ChromeDriver(options);

        try {
            String url = "https://consultaweb.ant.gob.ec/PortalWEB/paginas/clientes/clp_grid_citaciones.jsp?ps_tipo_identificacion=CED&ps_identificacion=" + cedula;
            driver.get(url);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            WebElement puntosTd;
            try {
                puntosTd = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("td.titulo1[title='Información adicional de Puntos']")
                ));
            } catch (TimeoutException e) {
                return "{ \"cedula\": \"%s\", \"puntos\": 0, \"mensaje\": \"No se encontraron puntos. Posiblemente no tiene licencia o no existen infracciones.\" }"
                        .formatted(cedula);
            }

            String puntos = puntosTd.getText().trim();

            if (puntos.matches("\\d+")) {
                return "{ \"cedula\": \"%s\", \"puntos\": %s }".formatted(cedula, puntos);
            } else {
                return "{ \"cedula\": \"%s\", \"error\": \"Formato inesperado de puntos\" }".formatted(cedula);
            }

        } catch (Exception e) {
            return "❌ Error al consultar puntos reales desde ANT: " + e.getMessage();
        } finally {
            driver.quit();
        }
    }




}
