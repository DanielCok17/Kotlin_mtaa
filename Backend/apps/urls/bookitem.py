
from apps.views.bookitem import BookItemView, BookItemDetailView, BookItemListView, BookItemFilterView

from rest_framework import routers
from rest_framework.urlpatterns import format_suffix_patterns
from django.urls import path


router = routers.SimpleRouter()
# router.register(r'bookitem', BookItemView)
# router.register(r'bookitem', BookItemListView, basename='BookitemView')
# router.register(r'bookitem/<int:id>', BookItemDetailView, basename='BookitemView')
#
# router.register(r'api/question', QuestionViewSet)
# router.register(r'api/lastfour', QuestionsViewGetLastFour)

urlpatterns = [
    path('bookitem/', BookItemListView),
    path('bookitem/<int:id>', BookItemDetailView),
    path('bookitemfilter/<int:book>', BookItemFilterView),
]
urlpatterns = format_suffix_patterns(urlpatterns)


urlpatterns += router.urls




