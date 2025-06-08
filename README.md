# Sistema de Verificación de Contribuyentes, Vehículos y Puntos de Licencia (Ecuador)

Este proyecto permite verificar información oficial del **SRI** y la **ANT** a partir de un RUC, una placa vehicular y una cédula. Combina **Spring Boot**, **React**, **Selenium WebDriver** y **Redis** para ofrecer respuestas rápidas con caché.

---

## Tecnologías Utilizadas

- Backend: Spring Boot 3.5.0 + Java 24  
- Frontend: React 18  
- Web Scraping: Selenium + ChromeDriver  
- Caché: Redis ejecutado en Docker  
- API REST para integración  

---

## 📦 Estructura del Proyecto

LINK FRONTEND: https://github.com/Santi7870/SRI_FRONTEND
LINK BACKEND: https://github.com/Santi7870/SRI


---

## Funcionalidades

- Verifica si el RUC es de un contribuyente activo y de tipo PERSONA NATURAL.
- Obtiene datos del vehículo registrado por número de placa.
- Consulta los puntos de licencia desde la web oficial de la ANT (scraping).
- Implementa **caché en Redis** para mejorar el rendimiento.

---

## 🗃Estructura del Caché en Redis

El sistema guarda los resultados por clave, ejemplo:

keys *
1) "contribuyentes::0913743464001"
2) "vehiculos::PBT2966"
3) "licencias::1707765937-PBT2966"
4) "puntosLicencia::1707765937"

---

📷 Capturas del Sistema
Pantalla Principal

![image](https://github.com/user-attachments/assets/a3fdb050-da9c-4dd8-9121-23a446ab894e)

Caché

![image](https://github.com/user-attachments/assets/d951525b-c082-4e4c-97b1-4ddbbf4a0e7c)

---

🐳 Redis en Docker
Ejecuta Redis en tu entorno con:

bash
Copiar
Editar
docker run -d --name redis-server -p 6379:6379 redis
Puedes ver las claves guardadas con:

bash
Copiar
Editar
docker exec -it redis-server redis-cli
127.0.0.1:6379> keys *

---

🚀 Instrucciones para Ejecutar
-- Backend (Spring Boot)

Compilar el sistema alojado en el respositorio SRI.

Frontend (React)
Ingresar a una terminal desde la raiz y ejecutar
npm install
npm run start
Disponible en: http://localhost:3000

✅ Ejemplo de Entrada y Resultado
Entrada:

 - RUC: 0913743464001
 
- Cédula: 1707765937

- Placa: ABC1234

Salida esperada:

Datos del contribuyente desde el SRI

Información vehicular de la ANT

Puntos de licencia actuales desde scraping web

Resultado mostrado y cacheado en Redis

---

👤 Autores
Santiago, Dayana, Vanessa — Proyecto académico UDLA 2025

Desarrollado con fines de práctica en arquitectura de software, scraping y rendimiento en servicios REST



