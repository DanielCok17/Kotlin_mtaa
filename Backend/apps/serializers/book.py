from rest_framework import serializers, fields

from apps.models.book import Book
from apps.models.bookitem import BookItem
from apps.models.rent import Rent
from django.db.models import Count
from apps.serializers.bookitem import BookItemSerializer

# from apps.serializers.categories import Category


from io import StringIO
from PIL import Image
from django.db.models import Count
from django.http import HttpResponse
from django.shortcuts import render
from rest_framework import viewsets, status
from rest_framework.parsers import MultiPartParser


from apps.models.book import Book


from rest_framework.decorators import api_view, permission_classes, parser_classes
from rest_framework.response import Response
from rest_framework.permissions import IsAuthenticated
from rest_framework.permissions import AllowAny
from rest_framework.pagination import PageNumberPagination




class BookSerializer:
    class Base(serializers.ModelSerializer):
        #bookitems_count = serializers.SerializerMethodField()
        # bookitems = serializers.PrimaryKeyRelatedField(many=True, read_only=True)

        bookitems = serializers.SerializerMethodField()
        # bookitems = serializers.CharField(source='get_bookitems', read_only=True)
        # bookimg = serializers.SerializerMethodField()

        class Meta:
            model = Book
            fields =   [
                'id',
                'name',
                'author',
                'description',
                'photo',
                'category',
                'created_at',
                'updated_at',
                'deleted_at',
                'is_deleted',
                'bookitems'
            ]
            # read_only_fields = ['image']

        def get_bookitems (self, obj):
            # pubs = BookItem.objects.annotate(bookitems=Count('book'))
            # pubs = BookItem.objects.annotate(Count('book'))
            # return pubs
            return BookItem.objects.filter(book_id=obj.id).count() - Rent.objects.filter(bookitem_id=obj.id).count()

        # def get_bookimg(self, obj):
        #     im = Image.open(BookItem.objects.get(book__photo = )[1:])
        #     im = im.resize((300, 300))
        #     # im.save(serializer.data['photo'][1:])
        #     #
            # with open(photo [1:], "rb") as image_file:
            #     image_data = base64.b64encode(image_file.read()).decode('utf-8')



    class Image(serializers.ModelSerializer):
        class Meta:
            model = Book
            fields = [
                'id',
                'photo',
            ]

    #
    # class Join(serializers.ModelSerializer):
    #     answers = AnswerSerializer.Join(many=True, read_only=True)
    #     user_id = UserSerializer.Join(read_only=True)
    #     comment_question = CommentSerializer.Join(many=True, read_only=True)
    #
    #     vote_count_positive = serializers.SerializerMethodField()
    #     vote_count_negative = serializers.SerializerMethodField()
    #
    #     class Meta:
    #         model = Book
    #         fields = [
    #             'id',
    #             'text',
    #             'title',
    #             'type',
    #             'anonymous',
    #             'user_id',
    #             'created_at',
    #             'updated_at',
    #             'deleted_at',
    #             'is_deleted',
    #             'answers',
    #             'comment_question',
    #             'vote_count_positive',
    #             'vote_count_negative',
    #         ]
    #
    #     def get_vote_count_positive(self, obj):
    #         return obj.vote_question.filter(vote = True).count()
    #
    #
    #     def get_vote_count_negative(self, obj):
    #         return obj.vote_question.filter(vote = False).count()
