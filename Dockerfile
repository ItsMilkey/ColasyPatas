# --- ETAPA 1: EL CONSTRUCTOR (USA MAVEN) ---
# Usamos una imagen que tiene Java 17 y Maven
FROM maven:3.8-openjdk-17 AS builder

# Establecemos el directorio de trabajo
WORKDIR /app

# Copiamos TODO tu código fuente (pom.xml, src/, etc.)
COPY . .

# Ejecutamos Maven para compilar el proyecto y crear el .jar
# -DskipTests se salta las pruebas para ir más rápido
RUN mvn package -DskipTests


# --- ETAPA 2: LA IMAGEN FINAL (LIMPIA) ---
# Usamos la imagen de Java 17 limpia que ya funciona
FROM eclipse-temurin:17-jdk-focal

# Copia la carpeta wallet (desde el código fuente de la etapa 1)
# Esto funciona porque la etapa 1 copió tu carpeta "src"
COPY --from=builder /app/src/main/resources/wallet /wallet

# Copia el .jar (que se creó en la etapa 1)
COPY --from=builder /app/target/*.jar app.jar

# Exponer puerto
EXPOSE 8080

# Ejecutar, diciéndole a Java dónde está el TNS_ADMIN
ENTRYPOINT ["java","-Doracle.tns.admin=/wallet","-jar","app.jar"]