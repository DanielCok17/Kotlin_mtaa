from django.db import models

# Create your models here.
from .book import Book

class BookItem(models.Model):
    class Meta:
        db_table = "bookitem"

    shelf = models.CharField(max_length = 200)
    condition = models.DecimalField(max_digits=2, decimal_places=0)

    book = models.ForeignKey(Book,related_name='bookitems', on_delete=models.CASCADE, blank=False, null=False)

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)
    deleted_at = models.DateTimeField(blank=True, null=True)
    is_deleted = models.BooleanField(default=False)
