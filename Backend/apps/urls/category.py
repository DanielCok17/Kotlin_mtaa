
from apps.views.category import CategoryView

from rest_framework.routers import DefaultRouter
from rest_framework import routers
from rest_framework.urlpatterns import format_suffix_patterns
from django.urls import path


router = DefaultRouter()
router.register(r'category', CategoryView, basename='CategoryView')
urlpatterns = router.urls
