from django.db.models import Count
from django.shortcuts import render

from rest_framework import viewsets, status
from rest_framework.decorators import api_view, permission_classes
from rest_framework.response import Response
from rest_framework.permissions import IsAuthenticated

from apps.serializers.bookitem import BookItemSerializer
from apps.models.bookitem import BookItem


from rest_framework.permissions import AllowAny


from rest_framework.pagination import PageNumberPagination

# @api_view(['GET'])
class BookItemView(viewsets.ModelViewSet):
    queryset = BookItem.objects.all()
    serializer_class = BookItemSerializer.Base








@api_view(['GET', 'POST'])
@permission_classes([IsAuthenticated])
def BookItemListView(request):
    print(request.headers)
    """
    List all code snippets, or create a new snippet.
    """
    print(request.user)
    try:
        obj = BookItem.objects.all()
    except BookItem.DoesNotExist:
        return Response({"message": "404"},status=status.HTTP_404_NOT_FOUND)

    if request.method == 'GET':
        paginator = PageNumberPagination()
        context = paginator.paginate_queryset(obj, request)
        q = BookItemSerializer.Base(context, many=True)
        return paginator.get_paginated_response(q.data)

    elif request.method == 'POST':
        q = BookItemSerializer.Base(data=request.data)
        if q.is_valid():
            q.save()
            return Response({'Created BookItem': q.data} , status=status.HTTP_201_CREATED)
        return Response(q.errors, status=status.HTTP_400_BAD_REQUEST)


@api_view(['GET', 'PUT', 'DELETE'])
@permission_classes([IsAuthenticated])
def BookItemDetailView(request, id):
    """
    Retrieve, update or delete a code snippet.
    """
    try:
        obj = BookItem.objects.get(pk=id)
    except BookItem.DoesNotExist:
        return Response({"message": "BookItem " + str(id) + " does not exist"}, status=status.HTTP_404_NOT_FOUND)
    print(request.user)

    if request.method == 'GET':
        serializer = BookItemSerializer.Base(obj)


        # votes = Vote.objects.all()
        # positive_votes = votes.filter(question_id=id, vote = True ).count()
        # negative_votes = votes.filter(question_id=id, vote = False ).count()
        # combine = serializer.data.copy()
        # combine.update({"positive_votes": positive_votes, "negative_votes": negative_votes})
        # return Response(combine, status=status.HTTP_200_OK)



        return Response(serializer.data, status=status.HTTP_200_OK)

    elif request.method == 'PUT':
        serializer = BookItemSerializer.Update(obj, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response({'Updated BookItem' : serializer.data}, status=status.HTTP_204_NO_CONTENT)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    elif request.method == 'DELETE':
        obj.delete()
        return Response({"message": "BookItem " + str(id) + " deleted"},  status=status.HTTP_200_OK)


from django.db.models import Q


@api_view(['GET', 'PUT', 'DELETE'])
@permission_classes([IsAuthenticated])
def BookItemFilterView(request, book):
    """
    Retrieve, update or delete a code snippet.
    """
    try:
        obj = BookItem.objects.filter(book=book)
    except BookItem.DoesNotExist:
        return Response({"message": "BookItems with book number " + str(book) + " does not exists"}, status=status.HTTP_404_NOT_FOUND)
    print(request.user)

    if request.method == 'GET':
        # serializer = BookItemSerializer.Filter(obj)
        result_list = list(obj.values('book').annotate(count=Count('book')))
        return Response(result_list, status=status.HTTP_200_OK)

