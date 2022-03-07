from django.shortcuts import render

# Create your views here.
from rest_framework_simplejwt.views import TokenObtainPairView
from rest_framework.permissions import AllowAny
from apps.serializers.login import MyTokenObtainPairSerializer
from django.contrib.auth import get_user_model


class MyObtainTokenPairView(TokenObtainPairView):

    queryset = get_user_model().objects.all()
    permission_classes = (AllowAny,)
    serializer_class = MyTokenObtainPairSerializer