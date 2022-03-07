from rest_framework import serializers, fields

from apps.models.rent import Rent


class RentSerializer:
    class Base(serializers.ModelSerializer):
        class Meta:
            model = Rent
            fields = [
                'id',
                'text',
                'returned',
                'user',
                'bookitem',
                'created_at',
                'updated_at',
                'deleted_at',
                'is_deleted',
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
