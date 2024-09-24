# Usar una imagen base de Maven con JDK 17 para construir el jar
FROM maven:3.8.3-openjdk-17 AS builder

# Establecer el directorio de trabajo en /app
WORKDIR /app

# Copiar solo los archivos necesarios del directorio actual al directorio /src dentro del contenedor
COPY pom.xml .
COPY src ./src

# Instalar dependencias y construir el proyecto
RUN mvn dependency:go-offline && mvn clean package -DskipTests

# Usar una imagen base de OpenJDK 17 en su versión ligera (slim) para el runtime
FROM openjdk:17-slim AS runtime

# Establecer el directorio de trabajo en /app
WORKDIR /app

# Copiar el archivo .jar generado en la fase de construcción al directorio /app en la fase de runtime
COPY --from=builder /app/target/miniproject-0.0.1-SNAPSHOT.jar /app/miniproject.jar

# Exponer el puerto 8443 para permitir la comunicación con la aplicación
EXPOSE 8443

# Definir el comando que se ejecutará cuando el contenedor se inicie
ENTRYPOINT ["java", "-jar", "/app/miniproject.jar"]
