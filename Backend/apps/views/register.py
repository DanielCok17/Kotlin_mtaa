from django.contrib.auth.models import User
from rest_framework import generics
from rest_framework.permissions import AllowAny

from django.contrib.auth import get_user_model

from apps.serializers.register import RegisterSerializer


class RegisterView(generics.CreateAPIView):
    queryset = get_user_model().objects.all()
    permission_classes = (AllowAny,)
    serializer_class = RegisterSerializer