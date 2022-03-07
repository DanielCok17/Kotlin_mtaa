

from .login import MyTokenObtainPairSerializer
from .register import RegisterSerializer
from .book import BookSerializer
from .bookitem import BookItemSerializer
from .category import CategorySerializer
from .comment import CommentSerializer
from .rent import RentSerializer
from .user import CustomUserSerializer


__all__ = [
    'MyTokenObtainPairSerializer',
    'RegisterSerializer',
    'BookSerializer',
    'BookItemSerializer',
    'CategorySerializer',
    'CommentSerializer',
    'RentSerializer',
    'CustomUserSerializer',
]