

from .login import MyObtainTokenPairView
from .register import RegisterView
from .book import BookView
from .bookitem import BookItemView
from .category import CategoryView
from .comment import CommentView
from .rent import RentView
from .user import CustomUserView




__all__ = [
    'MyObtainTokenPairView',
    'RegisterView',
    'BookView',
    'BookItemView',
    'CategoryView',
    'CommentView',
    'RentView',
    'CustomUserView',
]