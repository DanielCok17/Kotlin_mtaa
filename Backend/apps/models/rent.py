from django.db import models
from django.conf import settings
# Create your models here.

from .bookitem import BookItem

User = settings.AUTH_USER_MODEL

class Rent(models.Model):
    class Meta:
        db_table = "rent"

    text = models.TextField()
    returned = models.BooleanField(blank=True, null=True)

    user = models.ForeignKey(User, on_delete=models.CASCADE, blank=False, null=False)
    bookitem = models.ForeignKey(BookItem,related_name='bookitem', on_delete=models.CASCADE, blank=False, null=False)

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)
    deleted_at = models.DateTimeField(blank=True, null=True)
    is_deleted = models.BooleanField(default=False)

    def __str__(self):
        return '%d: %s' % (self.id, self.text)

