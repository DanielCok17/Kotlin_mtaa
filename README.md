# MTA_mobile_app-Library

## Erik Belák, Lukáš Adamík

# API design

## Version

- Current version  of API is **v1**
  
  ## Error Codes
  
  - **200 201 204**    - Success response
  
  - **401**    - Unauthorized
  
  - **403**   - Insufficient permissions for action
  
  - **404**  -  Resource not found

## Simple response

```json
{
  "response": {
    "token": "PRETTY_AWESOME_TOKEN"
  }
}

{
    "refresh": "PRETTY_AWESOME_REFRESH_TOKEN",
    "access": "PRETTY_AWESOME_ACCESS_TOKEN"
}
```

## Response pagination

```json
{
    "count": 27,
    "next": "http://localhost:8000/library/bookitem/?page=2",
    "previous": null,
    "results": [
        {
            "id": 1,
            "shelf": "L1",
            "condition": "1",
            "book": 1,
            "created_at": "2021-04-06T10:18:36.738981Z",
            "updated_at": "2021-04-06T10:18:36.739003Z",
            "deleted_at": null,
            "is_deleted": false
        },
        {
            "id": 2,
            "shelf": "L1",
            "condition": "1",
            "book": 1,
            "created_at": "2021-04-06T10:18:52.192297Z",
            "updated_at": "2021-04-06T10:18:52.192312Z",
            "deleted_at": null,
            "is_deleted": false
        },
        {
            "id": 3,
            "shelf": "L1",
            "condition": "1",
            "book": 1,
            "created_at": "2021-04-06T10:18:52.913605Z",
            "updated_at": "2021-04-06T10:18:52.913621Z",
            "deleted_at": null,
            "is_deleted": false
        } 
    ]
}
```

## GET library/book

fetch all books

## GET library/book/%id

fetch profile of book

## PUT library/bookitem/%id

edit state of book item

## POST library/bookitem/%id

add new book item

## DELETE library/bookitem/%id

delete book item

# Backend

Install and create venv (virtual environment) folder in root folder [using pip](https://packaging.python.org/guides/installing-using-pip-and-virtual-environments/)

### Packages required in virtual environment

```
# Using pip
pip install psycopg2
pip install psycopg2-binary
pip install djangorestframework
pip install django-cors-headers
pip install Pillow
pip install django-rest-auth
pip install djangorestframework-simplejwt
pip install django-filter
pip install django-allauth 
```

### Start venv and django server

```
# in root folder
source venv/bin/activate
python manage.py runserver
```
