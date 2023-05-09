﻿# Сервис погоды
### Сервис погоды — реактивный REST сервис, предоставляющее информацию о погоде в различных городах. Позволяет пользователям просматривать данные о погоде, подписываться на обновления города и выполнять административные задачи, если у них есть соответствующие разрешения.
## Использованные технологии/инструменты:
JDK 11<br />
Spring Boot<br />
Web Flux<br />
Liquibase<br />
Postgresql<br />
Docker<br />
## Установка
### После клонирования приложения переходим в директорию проекта
#### ```docker-compose up -d```
## Запуск
### После успешной установки и запуска, приложение будет доступен по адресу http://localhost:8082
### Для тестирования API админов:
#### Логин: ```admin@gmail.com```
#### Пароль: ```admin```
### Для тестирования API обычных пользователей
#### Логин: ```user@gmail.com```
#### Пароль: ```user```
### API эндпоинты для всех:
**/api/auth/login** - получение токена по логину и паролю<br />
**/api/auth/register** - регистрация нового пользователя (минимальная валидация: для тестирования)<br />
### API эндпоинты для администраторов:
**/api/user/list** - список пользователей<br />
**/api/user/details** - получение деталей подписки пользователей<br />
**/api/user/{userId}/edit** - редактирование пользователя<br />
**/api/city/list** - список городов<br />
**/api/city/{cityId}/edit** - редактирование города<br />
**/api/weather/{cityId}/edit** - обновление погоды в городе.<br />
### API эндпоинты для пользователей
**/api/city/list** - список городов<br />
**/api/city/{cityId}/subscribe** - подписка на город<br />
**/api/city/subscriptions** - получение данных о погоде по подписанным городам<br />

