"""Library URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path, include
from allauth.account.views import confirm_email
from django.conf.urls import url


from rest_framework.routers import DefaultRouter

from rest_framework_simplejwt.views import (
    TokenRefreshView,
)


from apps.views import MyObtainTokenPairView, RegisterView



# router = DefaultRouter()

urlpatterns = [
    # admin page
    path('admin/', admin.site.urls),

    # djangorestframework-simplejwt
    # path('', include(router.urls)),
    path('login/', MyObtainTokenPairView.as_view(), name='token_obtain_pair'),
    path('login/refresh/', TokenRefreshView.as_view(), name='token_refresh'),
    path('register/', RegisterView.as_view(), name='auth_register'),

    # items in DB
    path(r'library/', include('apps.urls.book')),
    path(r'library/', include('apps.urls.bookitem')),
    path(r'library/', include('apps.urls.category')),
    path(r'library/', include('apps.urls.comment')),
    path(r'library/', include('apps.urls.rent')),
    path(r'library/', include('apps.urls.user')),



    # django-allauth
    # url('rest-auth/', include('rest_auth.urls')),
    # url('rest-auth/registration/', include('rest_auth.registration.urls')),
    # url('account/', include('allauth.urls')),
    # url('accounts-rest/registration/account-confirm-email/(?P<key>.+)/$', confirm_email, name='account_confirm_email'),

]












