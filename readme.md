
# tzZft
Тестовое задание (ЦФТ)

Описание задачи
Реализовать Android-приложение со следующими функциями:
1. Пользователь вводит BIN банковской карты и видит всю доступную информацию о нём, загруженную с https://binlist.net/
2. История предыдущих запросов выводится списком
3. История предыдущих запросов не теряется при перезапуске приложения
4. Нажатие на URL банка, телефон банка, координаты страны отправляет пользователя в
приложение, которое может обработать эти данные (браузер, звонилка, карты)

Решение задачи
Чтобы удобно масштабировать, модифицировать и просто читать существующий код используется "Clean Architecture". В одномодульном приложении на Presentation слое применен архитектурный паттерн MVVM. Domain слой содержит use case`ы с реализацией логики запроса данных из репозитория, и с реализацией логики работы с историей запросов. Data слой содержит методы работы с сетью и с базой данных.

Используемые библиотеки:
1. Для работы с DI используется Dagger Hilt. Упрощенного варианта Dagger`а вполне достаточно.
2. Базы данных MySQL, а значит Room. Удобнее, чем использовать DbHelper.
3. Сеть - это Retrofit и Okhttp. Для указания параметра в заголовке использовался Interceptor.
4. Сериализация - это Kotlin Serialization. Т.к. быстрее Gson и нет проблем с nullable.
5. Чтобы добавить капельку реактивности в задании используется LiveData, а значит и ее библиотеки.
6. Так как MVVM, то нужны библиотеки с описанием жизненного цикла ViewModel.
7. Для "многопотока" и "асинхрона" Kotlin Coroutines. Использую также Flow (возможно для этого задания это избыточно, но все же).