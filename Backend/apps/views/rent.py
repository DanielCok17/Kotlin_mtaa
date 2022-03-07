from django.shortcuts import render

from rest_framework import viewsets, status
from rest_framework.decorators import api_view, permission_classes
from rest_framework.response import Response
from rest_framework.permissions import IsAuthenticated

from apps.serializers.rent import RentSerializer
from apps.models.rent import Rent


# @api_view(['GET'])
class RentView(viewsets.ModelViewSet):
    queryset = Rent.objects.all()
    serializer_class = RentSerializer.Base
