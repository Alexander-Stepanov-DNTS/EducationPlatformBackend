# Используем официальный образ Maven для создания артефакта сборки
FROM maven:3.8.5-openjdk-11 AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы pom.xml и source
COPY pom.xml .
COPY src ./src

# Выполняем сборку Maven, пропуская тесты для ускорения процесса
RUN mvn clean package -DskipTests

# Используем официальный образ OpenJDK для запуска приложения
FROM openjdk:11-jre-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный артефакт из предыдущего шага
COPY --from=build /app/target/EducationPlatform-0.0.1-SNAPSHOT.jar app.jar

# Пробрасываем порт 8080
EXPOSE 8080

# Определяем точку входа для контейнера
ENTRYPOINT ["java", "-jar", "app.jar"]
