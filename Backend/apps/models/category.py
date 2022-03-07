from django.db import models

# Create your models here.

class Category(models.Model):
    class Meta:
        db_table = 'category'

    name = models.CharField(max_length = 200)

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)
    deleted_at = models.DateTimeField(blank=True, null=True)
    is_deleted = models.BooleanField(default=False)

