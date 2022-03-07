from rest_framework_simplejwt.serializers import TokenObtainPairSerializer

from datetime import timedelta
import json

from rest_framework_simplejwt.serializers import TokenObtainPairSerializer

# SUPERUSER_LIFETIME = datetime.timedelta(days=1)

class MyTokenObtainPairSerializer(TokenObtainPairSerializer):

    @classmethod
    def get_token(cls, user):
        token = super(MyTokenObtainPairSerializer, cls).get_token(user)

        # Add custom claims
        token['email'] = user.email
        token['is_superuser'] = user.is_superuser
        # token['exp'] = json.dumps(timedelta(minutes=30), indent=4, sort_keys=True, default=str)#timedelta(minutes=30)#SUPERUSER_LIFETIME

        return token