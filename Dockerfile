# 1. Base: Usamos una imagen de Java 17 estándar (eclipse-temurin)
FROM eclipse-temurin:17-jdk-focal

# 2. Copia la carpeta wallet (desde su ubicación real)
#    al interior de la imagen, en una carpeta llamada /wallet
COPY src/main/resources/wallet /wallet

# 3. Copia el .jar
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# 4. Exponer puerto
EXPOSE 8080

# 5. Ejecutar, diciéndole a Java dónde está el TNS_ADMIN
ENTRYPOINT ["java","-Doracle.tns.admin=/wallet","-jar","/app.jar"]