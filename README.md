# ChtBot

Первая задача
-----
Это консольный чат-бот, который умеет играть в "Виселицу". В основе построения диалога - конечный автомат. Слова для отгадывания
берутся из архива существительных русского языка. Имеется запас жизней, каждый дается на одно слово. Если вы истратите все жизни, то, соответсвенно, проиграете.

Для работы бота необходимо запустить файл Base. Далее бот сам вам всё расскажет :)

Вторая задача
-----
Реализована вторая игра - Правда или действие. Перестроена логика чат-бота: 
диалог должен начать пользователь, а не сам бот. Многопользовательский режим.

Для работы с ботом нужно сначала ввести свой id, потом поставить двоеточие, и затем ввести желаемую команду.
(Примеры: "Вика:виселица", "1:правда или действие")

Третья задача
-----
Сделаны тесты, отражающие логику игр. 
Отделена логика игры от логики ведения диалога.
Изменили возвращающее значение автомата, теперь это - непосредственно ответ и набор кнопок для тг.
Теперь это также телеграм-бот! Где следует - графический интерфейс представлен кнопочками. Таке присутсвуют эмодзи,
дабы сделать нашего бота дружелюбнее :) Нужные библиотеки находятся в папке lib. Чтобы инициализировать бота, 
нужен токен и его имя. Эти данные следует запомнить в переменных среды окружения: BOTSTOKEN и BOTSNAME. Также 
для режима телеграм-бот сделан многопользовательский режим.

Авторы: Виктория Помыкалова и Оксана Гривас.

Четвертая задача 
-----
Появилась статистика по игре "Виселица" для каждого игрока. Отображается в виде списка из элементов: юзернейм (если его нет, то первое имя), количество отгаданных и неотгаданных слов. Нажимаешь на кнопочку - и вуаля! Список выводится отсортированным по количеству отгаданных слов. 
Что ж, узнаем, у кого самый большой словарный запас! :) 
Реализован деплой в интернет с помощью heroku.
Исправлено: зависимость статистики от телеграма, хранение ненужных (как потом оказалось) словарей.
Добавлено: вся статистика хранится в бд firebase. 

Пятая задача 
-----
Создана админка для редактирования архива слов в игре "Виселица" и набора вопросов и заданий в игре "Правда или действие". 
Наборы можно как пополнять, так и уменьшать. 
Исправлено: 
-Первичные данные для статистики берутся из базы данных и обновляются в бд при изменении значений. 
-При редактировании данных в самой базе данных в статистике изменения отобразятся. 
-Не хранятся поля wins и fails, эти данные лежат и изменяются в бд.
-Разделен большой класс AdminRepository на 3 небольших, каждый отвечает за свой функционал.

Шестая задача
-----
Теперь бот может взаимодействовать с групповыми чатами. Для этого нужно просто добавить его в групповой чат 
и начнётся игра "Виселица".
Каждый из участников может загадать слово остальным игрокам, написав боту сообщение вида: 
"@ImPickleBot <слово>". Когда список загаданных участниками слов пуст, бот выдает своё слово. 
Для того, чтобы прекратить игру, нужно удалить бота из чата. 

Авторы: Виктория Помыкалова и Екатерина Мустафина.
