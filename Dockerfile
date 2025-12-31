# =========================
# STAGE 1 : BUILD
# =========================
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copier uniquement le pom.xml pour le cache
COPY pom.xml .

# Télécharger les dépendances
RUN mvn dependency:go-offline -B

# Copier le code source
COPY src src

# Construire l'application
RUN mvn clean package -DskipTests


# =========================
# STAGE 2 : RUNTIME
# =========================
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copier le JAR généré
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
