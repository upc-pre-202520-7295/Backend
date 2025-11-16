FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

COPY . .

# Construye el proyecto sin tests
RUN ./mvnw clean package -DskipTests

# -------------------------------
# Runtime Image
# -------------------------------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia cualquier archivo .jar generado en target/
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8101

ENTRYPOINT ["java", "-jar", "app.jar"]
