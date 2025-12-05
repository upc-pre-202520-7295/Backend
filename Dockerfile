# Imagen base JRE (sin herramientas de compilación)


FROM eclipse-temurin:21-jre-ubi9-minimal

# Directorio de trabajo
WORKDIR /app

# Copia solo el .jar ya compilado
COPY target/*.jar app.jar


# Puerto que expone tu app
EXPOSE 8092

# Comando para ejecutar el microservicio
ENTRYPOINT ["java", "-jar", "app.jar"]




