# Despliegue en Render

Para que el login y la API funcionen desde el frontend en producción:

1. **El servicio de backend en Render** debe estar conectado a **este repositorio** (lashes-backend).  
   Si el servicio se llama `bethlashes-backend` y tiene otra URL, esa URL debe corresponder a **este** proyecto.

2. **Rutas que debe exponer este backend:**
   - `GET  /api/auth/health` → responde `"OK"`
   - `POST /api/auth/login`
   - `POST /api/auth/register`
   - resto bajo `/api/...`

3. **Si ves "Not Found"** al abrir `https://tu-backend.onrender.com/api/auth/health` en el navegador, ese servicio **no** está ejecutando este backend. Conecta el Web Service de Render a este repo y vuelve a desplegar.

4. **Variable en el frontend:** En el build de producción del frontend se usa la URL del backend. Si despliegas este backend en un **nuevo** servicio de Render (URL distinta), configura en el frontend la variable de entorno `VITE_API_URL=https://tu-nueva-url.onrender.com/api` y vuelve a construir/desplegar el frontend.
