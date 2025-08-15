# 📝 ToDo List — Spring Boot

Простое REST API приложение для управления задачами (ToDo List).  
Подходит для демонстрации навыков работы с **Spring Boot**, **JPA/Hibernate**, **REST API** и **базами данных**.

## 🚀 Стек технологий
- Java 17+
- Spring Boot
- Spring Data JPA (Hibernate)
- PostgreSQL / H2 Database
- Maven
- Lombok

## ✨ Возможности
- Создание задачи
- Получение списка всех задач
- Обновление задачи
- Удаление задачи
- Поиск задачи по ID

## 🗂 Архитектура проекта

```mermaid
flowchart TD
    A[Controller] --> B[Service]
    B --> C[Repository]
    C --> D[(Database)]
    B --> E[DTO]
    C --> F[Entity]