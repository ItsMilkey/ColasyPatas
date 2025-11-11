# 1. Base: Usamos una imagen de Java 17 (coincide con tu pom.xml)
FROM openjdk:17-slim

# 2. Argumento: Le decimos que el archivo .jar estará en la carpeta 'target'
ARG JAR_FILE=target/*.jar

# 3. Copia: Copiamos ese .jar al interior de la imagen y lo llamamos 'app.jar'
COPY ${JAR_FILE} app.jar

# 4. Exponer: Le decimos a Docker que tu app corre en el puerto 8080
EXPOSE 8080

# 5. Ejecutar: El comando final para iniciar tu aplicación
ENTRYPOINT ["java","-jar","/app.jar"]