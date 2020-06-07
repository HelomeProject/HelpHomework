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
    if request.FILES['file']:
        if not (os.path.exists(settings.MEDIA_ROOT)):
            os.mkdir(settings.MEDIA_ROOT)
        save_path = os.path.join(settings.MEDIA_ROOT, 'homeworkoriginal')
        if not (os.path.exists(save_path)):
            os.mkdir(save_path)
        save_path = os.path.join(settings.MEDIA_ROOT, 'homeworkoriginal', request.POST['homeworkIdx'])
        if not (os.path.exists(save_path)):
            os.mkdir(save_path)
        file_path = os.path.join(save_path, str(request.POST['memberIdx']) + '.jpg')
        if os.path.exists(file_path):
            os.remove(file_path)
        path = default_storage.save(file_path, request.FILES['file'])

        try:
            point = scoring(path, request.POST['homeworkIdx'], request.POST['memberIdx'])
            return JsonResponse({
                'success':'save to DB',
                'point': point
                }, status=200)
        except:
            return JsonResponse({'error':'retry'}, status=401)
    else:
        return JsonResponse({'error':'no file upload'}, status=400)


@csrf_exempt
def savenotification(request):
    if request.FILES['file']:
        print(1)
        if not (os.path.exists(settings.MEDIA_ROOT)):
            os.mkdir(settings.MEDIA_ROOT)
        print(2)
        save_path = os.path.join(settings.MEDIA_ROOT, 'noticeImg')
        if not (os.path.exists(save_path)):
            os.mkdir(save_path)
        print(3)
        filename = str(request.POST['grade']) + '_' + str(request.POST['classnum']) + '_' + 'noticeIdx.jpg'
        file_path = os.path.join(save_path, filename)
        default_storage.save(file_path, request.FILES['file'])
        return JsonResponse({'success': 'save to DB'}, status=200)
    else:
        return JsonResponse({'error':'no file upload'}, status=400)
