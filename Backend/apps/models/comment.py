from django.db import models
from django.conf import settings

# Create your models here.

from .book import Book

User = settings.AUTH_USER_MODEL

class Comment(models.Model):
    class Meta:
        db_table = "comment"

    text = models.TextField()

    user = models.ForeignKey(User, on_delete=models.CASCADE, blank=False, null=False)
    book = models.ForeignKey(Book,related_name='comments', on_delete=models.CASCADE, blank=False, null=False)

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)
    deleted_at = models.DateTimeField(blank=True, null=True)
    is_deleted = models.BooleanField(default=False)

    def __str__(self):
        return '%d: %s' % (self.id, self.text)

