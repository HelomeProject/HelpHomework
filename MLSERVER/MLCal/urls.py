from django.urls import path
from . import views

urlpatterns = [
    path('calc/', views.calc, name="calc"),
    path('addnoti/', views.savenotification, name="addnoti")
]
