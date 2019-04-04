
# Создание и интегрирование бота VK в группу через LongPoll VK API
### Создание сообщества-бота
- Для это нужно зайти в **Группы** -> **Создать сообщество**
- Выберите любой тип сообщества и введите название, тематику группы.

### Получение пользовательского ключа доступа
#### Первый способ
- Перейти на сайт [vkhost.github.io/scope](http://vkhost.github.io/scope/ "vkhost.github.io/scope") 
- В поле *Введите права доступа через запятую:* вводим **groups,offline**
- Нажмите *Готово*. Появится окно **Приложение VK API запрашивает доступ к Вашему аккаунту** . Нажмите *Разрешить*
- Скопируйте с адресной строки **access_token**
`https://oauth.vk.com/blank.html#access_token=ЗДЕСЬНУЖНЫЙВАМТОКЕН&expires_in=0&user_id=385818590`

#### Второй способ
- Перейти по этой ссылке: [ссылка](https://oauth.vk.com/authorize?client_id=3116505&scope=wall,offline&redirect_uri=https://oauth.vk.com/blank.html&display=page&response_type=token&revoke=1 "ссылка")
- Разрешить доступ
- Скопировать с адресной строки **access_token**

### Получение ключей доступа от сообщества
- Открыть сообщество
- Нажать кнопку **Управление**
- На открывшейся странице настроек, выбрать **Работа с API**
- Нажать кнопку **Создать ключ**
- Выбрать необходимые права для нового ключа доступа (желательно все)
- Подтвердить действие при помощи push-уведомления или SMS-кода
- Скопировать полученный API-ключ сообщества

### Включение поддержки сообщений сообщества и клавиатуры
- Открыть сообщество
- Нажать кнопку **Управление**
- Перейти в  **Сообщения** и включить их.
- Также включить **Возможности ботов** в **Сообщения** -> **Настройки для бота**.

### Работа с кодом
`Integer groupID ; // ID группы `
`String adminToken;  // пользовательский ключ доступа`
`String botToken;  // ключ доступа сообщества `
`VK object = new VK(groupID, adminToken, botToken);`
`object.getUpdates(90); // получение обновлений с LongPoll с задержкой 90 секунд`

Фоточки надо добавить?)
![](https://pandao.github.io/editor.md/examples/images/4.jpg)
