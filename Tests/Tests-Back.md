# Akceptačné testy backend

## 1. Vymazanie pridanej knihy

### pre-condition:

Vytváranémú `book_item` bude pridelený `id` 2.

### post-conditon:

Vytvorený  `book_item` bude označený v DB za zmazaný.

**Request library/book_item/add**

```raw
curl --location --request POST 'library/v1/book_item/add' \
--header 'Content-Type: application/json' \
--data-raw '{
    "isbn": "978-0-13-601970-3",
     "state": "3",
     "shelf": "N34-RedTriangle",
     "photo": "way to the photo3",
}'
```

**response HTTP 201**

```JSON
{
    "response": {
        "message": "Book successfully uploaded"
    }
}
```

**Request library/book_item/delete/%id**

```raw
curl --location --request DELETE 'library/v1/book/item/%id?id=1' 
```

**response HTTP 200**

```JSON
{
    "response": {
        "message": "Book deleted successfully"
    }
}
```

## 2. Zmenenie stavu pridanej knihy

### pre-condition:

Vytváranému `book_item` bude pridelený `id` 2.

### post-conditon:

Vytvorenému  `book_item` bude zmenený atribút v DB `state` na 4.

**Request library/v1/book_item/add**

```raw
curl --location --request POST 'library/v1/book_item/add' \
--header 'Content-Type: application/json' \
--data-raw '{
    "isbn": "978-0-13-601970-3",
    "state": "3",
    "shelf": "N34-RedTriangle",
    "photo": "way to the photo3",
}'
```

**response HTTP 201**

```JSON
{
    "response": {
        "message": "Book successfully uploaded"
    }
}
```

**Request PUT library/v1/book/item/%id**

```raw
curl --location --request PUT 'library/v1/book/item/%id?id=2' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": "2",
    "state": "4"
}'
```

**response  HTTP 202**

```JSON
{
    "response": {
        "message": "Book state was updated"
    }
}
```

## 3. Zamietnutie získania vymazanej knihy

### pre-condition:

V DB neexistuje `book_item` s atribútom `id` 1.

### post-conditon:

Zmazanie `book_item` s `id` 1.Backend pošle HTTP code `404` Resource not found  o tom, že `book_item` neexistuje.

**Request DELETE library/v1/book/item/%id**

```raw
curl --location --request DELETE 'library/v1/book/item/%id?id=1'
```

**response HTTP 200**

```JSON
{
     "response": {
        "message": "Book deleted successfully "
    }
}
```

**Request library/book_item/get/%id**

```
curl --location --request GET 'library/book_item/%id?id=1'
```

**response HTTP 404**

```JSON
{
	"response": {
        "message": "Book item not found "
    }
}
```

### 4. Zmena počtu volných kníh

### pre-condition:

V DB existuje `book_item` s atribútom `id` 1 s `book_id` .

### post-conditon:

Zmena počtu dostupných kníh na 3, zmazanie `book_item` s `id` 1.

**Request DELETE library/v1/book/item/%id**

```raw
curl --location --request DELETE 'library/v1/book/item/%id?id=1' 
```

**response HTTP 200**

```JSON
{ 
	"response": {
 		"message": "Book deleted successfully "
 	}
}
```

**Request library/book/all**

```raw
curl --location --request GET 'library/book'
```

**response HTTP 200**

```JSON
{
 "items":[
	{
	"isbn": "978-0-13-601970-3",
	"name": "Book_name1",
	"author_name" : "Book Author1"
	"photo": "url of the photo1",
	"available": "3"
	},
	{
	"isbn": "478-7-13-457970-3",
	"name": "Book_name2",
	"author_name" : "Book Author2"
	"photo": "url of the photo2",
	"available": "1"
}
```

## 5. Nedovolené zmazanie získaneho book_item

### pre-condition:

Použivatel nieje prihlásený ako pracovník knižnice. V DB existuje `book_item` s atribútom `id` 1 s `book_id` .

### post-conditon:

Pri pokuse o zmazanie pošle backend HTTP code `403` Insufficient permissions for action. Zachovanie `book_item` v db.

**Request library/book_item/get/%id**

```raw
curl --location --request GET 'library/book_item/%id?id=1'
```

**response HTTP 404**

```JSON
{
	"response":{
		 "isbn": "978-0-13-601970-3",
		 "state": "3",
		 "shelf": "N34-RedTriangle",
		 "photo": "way to the photo3",
	 }
}
```

**Request DELETE library/v1/book/item/%id**

```raw
curl --location --request DELETE 'library/v1/book/item/%id?id=1'
``` 

**response HTTP 403**

```JSON
{
	"response": {
 		"message": "Insufficient permissions for action"
 	}
}
```




