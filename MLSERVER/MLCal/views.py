import os
from django.shortcuts import render
from django.http import JsonResponse
from django.conf import settings
from django.views.decorators.csrf import csrf_exempt
from django.core.files.storage import default_storage

from .func import scoring

# Create your views here.
@csrf_exempt
def calc(request):
    print(settings.BASE_DIR)
    if request.FILES['file']:
        print(request.POST)
        filename="1.jpg"
        save_path = os.path.join(settings.MEDIA_ROOT, 'homeworkoriginal', filename)
        path = default_storage.save(save_path, request.FILES['file'])
        point = scoring(path, 1, 1)
        return JsonResponse({
            'success':'save to DB',
            'point': point
            }, status=200)
    else:
        return JsonResponse({'error':'no file upload'}, status=400)
