from django.db import models

from .category import Category

# Create your models here.

class Book(models.Model):
    class Meta:
        db_table = 'book'

    name = models.CharField(max_length = 200)
    author = models.CharField(max_length = 200)
    description = models.TextField()
    photo = models.ImageField(upload_to='media')

    category = models.ManyToManyField(Category, related_name='bookcategories', blank=True)

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)
    deleted_at = models.DateTimeField(blank=True, null=True)
    is_deleted = models.BooleanField(default=False)

