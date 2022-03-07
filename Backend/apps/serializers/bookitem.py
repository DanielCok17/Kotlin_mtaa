from rest_framework import serializers, fields

from apps.models.bookitem import BookItem

# from apps.serializers.categories import Category

class BookItemSerializer:
    class Base(serializers.ModelSerializer):
        class Meta:
            model = BookItem
            fields = [
                'id',
                'shelf',
                'condition',
                'book',
                'created_at',
                'updated_at',
                'deleted_at',
                'is_deleted',
            ]

    class Update(serializers.ModelSerializer):
        class Meta:
            model = BookItem
            fields = [
                'shelf',
            ]

    class Filter(serializers.Serializer):
        count_completed = serializers.IntegerField(read_only=True)
        class Meta:
            model = BookItem
            fields = [
                'count_completed',
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
