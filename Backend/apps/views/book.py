

from io import StringIO
from PIL import Image
from django.db.models import Count
from django.http import HttpResponse
from django.shortcuts import render
from rest_framework import viewsets, status
from rest_framework.parsers import MultiPartParser

from apps.serializers.book import BookSerializer
from apps.models.book import Book


from rest_framework.decorators import api_view, permission_classes, parser_classes
from rest_framework.response import Response
from rest_framework.permissions import IsAuthenticated
from rest_framework.permissions import AllowAny
from rest_framework.pagination import PageNumberPagination


class BookView(viewsets.ModelViewSet):
    queryset = Book.objects.all()
    serializer_class = BookSerializer.Base


@api_view(['GET', 'POST'])
@parser_classes((MultiPartParser,))
@permission_classes([IsAuthenticated])
def BookListView(request):
    print(request.headers)
    """
    List all code snippets, or create a new snippet.
    """
    print(request.user)
    try:
        obj = Book.objects.all()
    except Book.DoesNotExist:
        return Response({"message": "404"},status=status.HTTP_404_NOT_FOUND)
    #
    if request.method == 'GET':
        paginator = PageNumberPagination()
        context = paginator.paginate_queryset(obj, request)
        q = BookSerializer.Base(context, many=True)
        return paginator.get_paginated_response(q.data)

    elif request.method == 'POST':


        q = BookSerializer.Base(data=request.data)
        print(request.data)
        if q.is_valid():
            q.save()
            return Response({'Created Book': q.data} , status=status.HTTP_201_CREATED)
        return Response(q.errors, status=status.HTTP_400_BAD_REQUEST)


@api_view(['GET', 'PUT', 'DELETE'])
@permission_classes([IsAuthenticated])
def BookDetailView(request, id):
    """
    Retrieve, update or delete a code snippet.
    """
    try:
        obj = Book.objects.get(pk=id)
    except Book.DoesNotExist:
        return Response({"message": "Book " + str(id) + " does not exist"}, status=status.HTTP_404_NOT_FOUND)


    if request.method == 'GET':
        serializer = BookSerializer.Base(obj)
        return Response(serializer.data, status=status.HTTP_200_OK)

    elif request.method == 'PUT':
        serializer = BookSerializer.Base(obj, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response({'Updated Book' : serializer.data}, status=status.HTTP_204_NO_CONTENT)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    elif request.method == 'DELETE':
        obj.delete()
        return Response({"message": "Book " + str(id) + " deleted"},  status=status.HTTP_200_OK)


def getBookImg(request, id):

    """
   Retrieve, update or delete a code snippet.
   """
    try:
        obj = Book.objects.get(pk=id)
    except Book.DoesNotExist:
        return Response({"message": "Book " + str(id) + " does not exist"}, status=status.HTTP_404_NOT_FOUND)


    serializer = BookSerializer.Base(obj)
    print(serializer.data['photo'])

    im = Image.open(serializer.data['photo'][1:])
    im = im.resize((300, 300))
    im.save(serializer.data['photo'][1:])
    response = HttpResponse( content_type='image/png')
    im.save(response, "png")

    return response

import base64

@api_view(['GET', 'POST'])
@parser_classes((MultiPartParser,))
@permission_classes([IsAuthenticated])
def getBookImg64(request, id):

    """
   Retrieve, update or delete a code snippet.
   """
    try:
        obj = Book.objects.get(pk=id)
    except Book.DoesNotExist:
        return Response({"message": "Book " + str(id) + " does not exist"}, status=status.HTTP_404_NOT_FOUND)

    serializer = BookSerializer.Base(obj)
    print(serializer.data['photo'])

    im = Image.open(serializer.data['photo'][1:])
    im = im.resize((300, 300))
    im.save(serializer.data['photo'][1:])

    with open(serializer.data['photo'][1:], "rb") as image_file:
        image_data = base64.b64encode(image_file.read()).decode('utf-8')

    # response = HttpResponse(image_data, content_type='application/json')#image/png

    newdict={'item':image_data}
    newdict.update(serializer.data)
    return Response(newdict, status=status.HTTP_200_OK)

    # return response

