from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing import image
from django.conf import settings
import pickle
import numpy as np
import tensorflow as tf
from PIL import Image
import cv2
import os

# make Rectangle to Square
def fillSquare(img):
    w, h = img.shape
    back = img[0][0]
    fill = abs(w - h)
    
    if w > h:
        li = [[back for i in range(fill // 2 + 1)] for j in range(w)]
        li = np.array(li, dtype='uint8')
        square = np.concatenate((li, img, li), axis=1)
    else:
        li = [[back for i in range(h)] for j in range(fill // 2 + 1)]
        li = np.array(li, dtype='uint8')
        square = np.concatenate((li, img, li), axis=0)

    return square


def nomalization(img_arr):
    s = 0
    cnt = 45 * 45
    maxV = 0
    for img in img_arr:
        for i in img:
            s += i
            maxV = max(maxV,i)
    avg = s / cnt
    for img in img_arr:
        for i in range(45):
            if img[i] < avg:
                img[i] = 0
    
    return img_arr


# 계산
def calculate(predict_nums):
    result = eval(predict_nums)
    return result

def sort_rects(rects):
    mid_points = []
    arrs = []
    arr = [(rects[0][0], rects[0][1], rects[0][2], rects[0][3])]
    for i in range(1, len(rects)):
        x, y, w, h = rects[i][0], rects[i][1], rects[i][2], rects[i][3]
        x_, y_, w_, h_ = rects[i-1][0], rects[i-1][1], rects[i-1][2], rects[i-1][3]
        if (y+(h//2)) - (y_+(h_//2)) > 20:
            arr.sort()
            mid_points.append(arr[0])
            arrs.append(arr)
            arr = []
        
        arr.append((x,y,w,h))
    arr.sort()
    mid_points.append(arr[0])
    arrs.append(arr)

    for i in range(len(arrs)):
        for j in range(len(arrs[i])):
            if arrs[i][j+1][3] - arrs[i][j][3] > 5:
                arrs[i] = arrs[i][j+1:]
                break

    return arrs, mid_points


def load_and_test(image_path):
    new_model = load_model('model.hdf5')
    classes = define_classes()

    res_predict = []

    img = cv2.imread(image_path)
    img_gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    img_blur = cv2.GaussianBlur(img_gray, (5, 5), 0)

    img_th = cv2.adaptiveThreshold(img_blur, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY_INV, 5, 2)
    # cv2.imshow('abc',img_th)
    # cv2.waitKey(0)
    # cv2.destroyAllWindows()
    contours, hierachy= cv2.findContours(img_th.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE) # 오류 생기면 images를 지우세요

    rects = [cv2.boundingRect(each) for each in contours]
    rects_ = [(x,y,w,h) for (x,y,w,h) in rects if (w*h>29)]
    rects = sorted(rects_, key=lambda x:x[1])
    
    # 정렬 ㅡㅡ...;; 후.
    RECTS, mid_points = sort_rects(rects)

    for rect in rects_:
        cv2.rectangle(img, (rect[0] - 3, rect[1] - 3), (rect[0] + rect[2] + 4, rect[1] + rect[3] + 4), (0, 255, 0), 2)

    for rects in RECTS:
        predict_nums = ''
        predict_res = ''
        jud = False
        for rect in rects: # [y : y+h, x : x+w]
            new_img = img_gray[rect[1]-1 : rect[1]+rect[3]+1, rect[0] : rect[0]+rect[2]]
        
            squared_img = fillSquare(new_img)
            resize_img = cv2.resize(squared_img, (45, 45))
            resize_img = cv2.GaussianBlur(resize_img, (3, 3), 0)

            img_inv = cv2.bitwise_not(resize_img)
            
            img_arr = image.img_to_array(img_inv, dtype='float32')
            img_arr = img_arr.astype('float32') / 255

            img_arr = nomalization(img_arr)
            # ii = image.array_to_img(img_arr)
            # plt.imshow(ii)
            # plt.show()
            predict_nums, predict_res, jud = pre(new_model, img_arr, jud, predict_nums, predict_res, classes)

        predict_nums = predict_nums[:-2]
        if predict_nums[0] == ')':
            predict_nums = predict_nums[1:]
        
        cal = calculate(predict_nums)
        res_predict.append([predict_res, cal])
        
    
    return res_predict, mid_points


def pre(new_model, img_arr, jud, predict_nums, predict_res, classes):

    output = new_model.predict(img_arr[None, :, :, :])
    np.set_printoptions(formatter={'float': lambda x: "{0:0.5f}".format(x)})
    predict_num = classes[np.argmax(output)]

    if predict_num == 'minus':
        predict_num = '-'
    elif predict_num == 'plus':
        predict_num = '+'
    elif predict_num == 'times':
        predict_num = '*'
    elif predict_num == 'div':
        predict_num = '/'
    elif predict_num == 'open':
        predict_num = '('
    elif predict_num == 'close':
        predict_num = ')'
    elif predict_num == 'equ':
        predict_num = '='
    else:
        pass

    if jud == True:
        predict_res += predict_num
    else:
        predict_nums += predict_num
        if predict_num == '-' and predict_nums[-2] == '-':
            jud = True
    
    return predict_nums, predict_res, jud

    
def define_classes(classfile = './classes.pkl'):
    with open(classfile, 'rb') as f:
        new_classes = pickle.load(f)
    classes = {}
    for key, value in new_classes.items():
        classes[value] = key

    return classes

def ox(res_predict, mid_points, testimage, homeworkidx, memberidx):
    c = 0
    correct, wrong = [], []
    len_correct, len_wrong = 0, 0
    for key, value in res_predict:
        if key == str(value):
            correct.append(c)
            len_correct += 1
        else:
            wrong.append(c)
            len_wrong += 1
        c += 1
    
    img = cv2.imread(testimage)
    for i in correct:
        x, y, w, h = mid_points[i][0], mid_points[i][1], mid_points[i][2], mid_points[i][3]
        img = cv2.circle(img, (x+9,y+9), 14, (0,0,255), 2)
    for j in wrong:
        x, y, w, h = mid_points[j][0], mid_points[j][1], mid_points[j][2], mid_points[j][3]
        img = cv2.line(img, (x,y), (x+18, y+18), (0,0,255), 2)
        img = cv2.line(img, (x+18,y), (x, y+18), (0,0,255), 2)

    img_re = cv2.resize(img, (400, 600))
    save_path = os.path.join(settings.MEDIA_ROOT, 'homeworkImg')
    if not (os.path.exists(save_path)):
        os.mkdir(save_path)
    save_path = os.path.join(settings.MEDIA_ROOT, 'homeworkImg', str(homeworkidx))
    if not (os.path.exists(save_path)):
        os.mkdir(save_path)

    file_path = os.path.join(save_path, str(memberidx)+'.jpg')
    cv2.imwrite(file_path, img_re)
    point = (len_correct / (len_correct+len_wrong)) * 100

    return point


def scoring(img, homeworkidx, memberidx):
    res_predict, mid_points = load_and_test(img)
    point = ox(res_predict, mid_points, img, homeworkidx, memberidx)

    return point