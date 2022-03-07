
from apps.views.rent import RentView

from rest_framework.routers import DefaultRouter
from rest_framework import routers
from rest_framework.urlpatterns import format_suffix_patterns
from django.urls import path


router = DefaultRouter()
router.register(r'rent', RentView, basename='RentView')
urlpatterns = router.urls
