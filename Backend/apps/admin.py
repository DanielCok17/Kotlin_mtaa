from django.contrib import admin

# Register your models here.
from apps.models.book import Book
from apps.models.bookitem import BookItem
from apps.models.category import Category
from apps.models.rent import Rent
from apps.models.comment import Comment


from django.contrib import admin
from django.contrib.auth.admin import UserAdmin

from .forms import CustomUserCreationForm, CustomUserChangeForm
from .models import CustomUser


class CustomUserAdmin(UserAdmin):
    add_form = CustomUserCreationForm
    form = CustomUserChangeForm
    model = CustomUser
    list_display = ('email', 'is_staff', 'is_active','is_superuser')
    list_filter = ('email', 'is_staff', 'is_active','is_superuser')
    fieldsets = (
        (None, {'fields': ('email', 'password')}),
        ('Permissions', {'fields': ('is_staff', 'is_active', 'is_superuser')}),
    )
    add_fieldsets = (
        (None, {
            'classes': ('wide',),
            'fields': ('email', 'password1', 'password2', 'is_staff', 'is_active', 'is_superuser')}
         ),
    )
    search_fields = ('email',)
    ordering = ('email',)



# Register your models here.
admin.site.register(CustomUser, CustomUserAdmin)
admin.site.register(Book)
admin.site.register(BookItem)
admin.site.register(Category)
admin.site.register(Rent)
admin.site.register(Comment)
