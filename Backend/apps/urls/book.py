
from apps.views.book import BookView, BookListView, BookDetailView, getBookImg, getBookImg64


from rest_framework import routers
from rest_framework.urlpatterns import format_suffix_patterns
from django.urls import path




router = routers.SimpleRouter()
#router.register(r'book', BookView, basename='bookView')
#urlpatterns = router.urls


urlpatterns = [
    path('book/', BookListView),
    path('book/<int:id>', BookDetailView),
    path('bookimg/<int:id>', getBookImg),
    path('bookimg64/<int:id>', getBookImg64),
   #path('bookitemfilter/<int:book>', BookItemFilterView),
]
urlpatterns = format_suffix_patterns(urlpatterns)







# router.register(r'bookitem', BookItemView)
# router.register(r'bookitem', BookItemListView, basename='BookitemView')
# router.register(r'bookitem/<int:id>', BookItemDetailView, basename='BookitemView')
#
# router.register(r'api/question', QuestionViewSet)
# router.register(r'api/lastfour', QuestionsViewGetLastFour)


urlpatterns += router.urls




