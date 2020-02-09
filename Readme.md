## Beer shop "АлкашНя"

## Overview

Приложение эмулирующее работу магазина по продаже пива

## Сущности

Ниже перечислены сущности в предметной области прокта их поля и связи.

### Пиво (Beer):

Поля:   
- Тип
- Наличие
- Название
- Описание
- Крепость
- Плотность
- Страна производитель
- Цена
    
### Заказ (Order)

Поля:
- Покупатель
- Статус
- Количество
- Общая стоимость
- Список заказа

Связи:
- список наименований пива, появляется при оформлении заказа ("Beer" to "Order")
- покупатель, появляется при оформлении заказа ("Customer" to "Order")
 
### Покупатель (Customer)

Поля:
- ФИО
- Email
- Номер телефона
     
### Администратор (Administrator)
Пользователь, который занимается оформлением заказов и добавлением, изменением и удалением наименований пива

Поля:
- ФИО
- Email
- Номер телефона
        
## User Stories

### BS-1 Как "Покупатель", я хочу получить список всех товаров, имеющихся в магазине с кратким описанием, и в результате получаю его

Request: 

`GET /api/beer`

Response: `200 OK`
```
[
    {
        "id": 1,
        "type": "светлое",
        "in_stock": true,
        "name": "Лидское",
        "description": "Лучшее пиво по бабушкиным рецептам",
        "alcohol": "5.0",
        "density": "11.5",
        "country": "Республика Беларусь",
        "price": "5.0"
    },
    {
        "id": 2,
        "type": "темное",
        "in_stock": true,
        "name": "Аливария",
        "description": "Пиво номер 1 в Беларуси",
        "alcohol": 4.6,
        "density": 10.2,
        "country": "Республика Беларусь",
        "price": 3.0
    }
]
```

### BS-2 Как "Покупатель", я хочу получить список товаров, отфильтрованных по критерию "Тип" (темное) , и в результате получаю список наименований темного пива

Request: 

`GET /api/beer/?type=${beerType}`

`Headers: beerType="темное"`

Response: `200 OK`

``` 
[
    {
        "id": 2,
        "type": "темное",
        "in_stock": true,
        "name": "Аливария",
        "description": "Пиво номер 1 в Беларуси",
        "alcohol": 4.6,
        "density": 10.2,
        "country": "Республика Беларусь",
        "price": 3.0
    }
]
```

### BS-3 Как "Покупатель", хочу зарегистрироваться, и если пользователя с таким E-mail не найдено, регистрируюсь

Request: 
    
`POST /api/user/sign-up`
    
```    
{
    "name": "Иван Иванов",
    "email": "ivan.ivanov@mail.ru",
    "password": "123456",
    "phone": "+375331234567"
}
```
Response: `201 CREATED`

```
{
   "id" : 1
}
```
### BS-4 Как "Покупатель", будучи зарегистрированным пользователем, я хочу войти в систему, и, если такой пользователь существует и пароль совпадает, войти в систему

Request: 
    
`POST /api/user/sign-in`
    
```    
{
    "email": "ivan.ivanov@mail.ru",
    "password": "123456"
}
```
Response: `200 OK`

```
{
   "id" : 1
}
```

### BS-5 Как "Покупатель", я хочу выбрать, интресующие меня наименования пива с указанием объема, и оформить заказ, и если я авторизован, оформляю заказ

Request: 

```POST /api/admin/orders```

```
{
    "customerId": 2,
    "goods": [
        {
            "id": 2,
            "value": 1
        },
        {
            "id": 3,
            "value": 3
        }
    ]
}
```

Response: `201 CREATED`

```
{
    "id": 2,
    "customer": {
        "id": 2,
        "name": "Петр Петров",
        "email": "petr.petrov@yandex.ru",
        "phone": "+375337654321"
    },
    "processed": false,
    "total": 27.0,
    "order": [
        {
            "beer": {
                "id": 2,
                "type": "темное",
                "in_stock": true,
                "name": "Аливария",
                "description": "Пиво номер 1 в Беларуси",
                "alcohol": 4.6,
                "density": 10.2,
                "country": "Республика Беларусь",
                "price": 3.0
            },
            "volume": 1
        },
        {
            "beer": {
                "id": 3,
                "type": "светлое осветлённое",
                "in_stock": true,
                "name": "Pilsner Urquell",
                "description": "непастеризованное",
                "alcohol": 4.2,
                "density": 12.0,
                "country": "Чехия",
                "price": 8.0
            },
            "volume": 3
        }
    ]
}
```

### BS-6 Как "Администратор", я хочу добавить новое наименование пива, и если такого наименования нет, добавляю его

Request: 
    
`POST /api/beer`
    
```    
{
    "type": "светлое осветлённое",
    "in_stock": true,
    "name": "Pilsner Urquell",
    "description": "непастеризованное",
    "alcohol": 4.2,
    "density": 12.0,
    "country": "Чехия",
    "price": 8.0
}
```

Response: `201 CREATED`

```
{
   "id" : 3
}
```
### BS-7 Как "Администратор", я хочу изменить цену пива, и если такого наименования есть, изменяю ему цену

Request: 
    
`PATCH /api/beer/${beerId}`

`Headers: beerId=3`
    
```    
{
    "price": 8.30
}
```

Response: `200 OK`

```
{
    "id" : 3
}
```

### BS-8 Как "Администратор", я хочу удалить наименование пива, и если такое наименование есть, удаляю его

Request: 
    
`DELETE /api/beer/${beerId}`

`Headers: beerId=3`

Response: `200 OK`


### BS-9 Как "Администратор", хочу получить список заказов, и получаю список с информацией по каждому заказу

Request: 
    
`GET /api/admin/orders`
    
Response: `200 OK`

```   
[
    {
        "id": 1,
        "customer": {
            "id": 1,
            "name": "Иван Иванов",
            "email": "ivan.ivanov@mail.ru",
            "phone": "+375331234567"
        },
        "processed": true,
        "total": 31.0,
        "order": [
            {
                "beer": {
                    "id": 2,
                    "type": "темное",
                    "in_stock": true,
                    "name": "Аливария",
                    "description": "Пиво номер 1 в Беларуси",
                    "alcohol": 4.6,
                    "density": 10.2,
                    "country": "Республика Беларусь",
                    "price": 3.0
                },
                "volume": 5
            },
            {
                "beer": {
                    "id": 3,
                    "type": "светлое осветлённое",
                    "in_stock": true,
                    "name": "Pilsner Urquell",
                    "description": "непастеризованное",
                    "alcohol": 4.2,
                    "density": 12.0,
                    "country": "Чехия",
                    "price": 8.0
                },
                "volume": 2
            }
        ]
    },
    {
        "id": 2,
        "customer": {
            "id": 2,
            "name": "Петр Петров",
            "email": "petr.petrov@yandex.ru",
            "phone": "+375337654321"
        },
        "processed": false,
        "total": 27.0,
        "order": [
            {
                "beer": {
                    "id": 2,
                    "type": "темное",
                    "in_stock": true,
                    "name": "Аливария",
                    "description": "Пиво номер 1 в Беларуси",
                    "alcohol": 4.6,
                    "density": 10.2,
                    "country": "Республика Беларусь",
                    "price": 3.0
                },
                "volume": 1
            },
            {
                "beer": {
                    "id": 3,
                    "type": "светлое осветлённое",
                    "in_stock": true,
                    "name": "Pilsner Urquell",
                    "description": "непастеризованное",
                    "alcohol": 4.2,
                    "density": 12.0,
                    "country": "Чехия",
                    "price": 8.0
                },
                "volume": 3
            }
        ]
    }
] 
```

### BS-10 Как "Администратор", я хочу изменить статус заказа на "Обработано", меняю его

Request: 
    
`PATCH /api/admin/orders/${orderId}`

`Headers: orderId=2`

```
{
    "processed": true
}
```
    
Response: `200 OK`

```
{
    "id": 2
}
```
