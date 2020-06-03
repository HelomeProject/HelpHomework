from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing import image
import pickle
import numpy as np
import tensorflow as tf
from PIL import Image
import matplotlib.pyplot as plt
import cv2


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

def load_and_test(image_path, classes):

    res_predict = dict()
    mid_points = []

    img = cv2.imread(image_path)
    img_gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    img_blur = cv2.GaussianBlur(img_gray, (5, 5), 0)

    thr1 = cv2.adaptiveThreshold(img_blur, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY_INV, 3, 2)
    
    images, contours, hierachy= cv2.findContours(thr1.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    rects_ = [cv2.boundingRect(each) for each in contours]

    # rects_ = [(x+6,y+8,w-10,h-11) if w*h > 1100 else (x,y,w,h) for (x,y,w,h) in rects_ ]
    rects_ = [(x,y,w,h) for (x,y,w,h) in rects_ if (w*h>30)]

    rects_1 = [(x,y,w,h) for (x,y,w,h) in rects_ if ((w*h>30) and 10 < y < 120)]
    rects_1.sort()
    mid_points.append(rects_1[0])
    rects_2 = [(x,y,w,h) for (x,y,w,h) in rects_ if ((w*h>30) and 135 < y < 240)]
    rects_2.sort()
    mid_points.append(rects_2[0])
    rects_3 = [(x,y,w,h) for (x,y,w,h) in rects_ if ((w*h>30) and 260 < y < 367)]
    rects_3.sort()
    mid_points.append(rects_3[0])
    rects_4 = [(x,y,w,h) for (x,y,w,h) in rects_ if ((w*h>30) and 377< y < 500)]
    rects_4.sort()
    mid_points.append(rects_4[0])
    rects_5 = [(x,y,w,h) for (x,y,w,h) in rects_ if ((w*h>30) and 505 < y < 620)]
    rects_5.sort()
    mid_points.append(rects_5[0])

    RECTS = [rects_1[3:], rects_2[3:], rects_3[3:], rects_4[3:], rects_5[3:]]


    for rect in rects_:
        cv2.rectangle(img, (rect[0]-5, rect[1]-10), (rect[0] + rect[2] + 2, rect[1] + rect[3] + 10), (0, 255, 0), 2)

    plt.imshow(img)
    plt.show()

    for rects in RECTS:
        predict_nums = ''
        predict_res = ''
        jud = False
        for rect in rects: # [y : y+h, x : x+w]
            new_img = img_gray[rect[1]-1 : rect[1]+rect[3]+1, rect[0] : rect[0]+rect[2]+1]
        
            squared_img = fillSquare(new_img)
            resize_img = cv2.resize(squared_img, (45, 45))
            resize_img = cv2.GaussianBlur(resize_img, (3, 3), 0)

            img_inv = cv2.bitwise_not(resize_img)
            
            img_arr = image.img_to_array(img_inv, dtype='float32')
            img_arr = img_arr.astype('float32') / 255

            img_arr = nomalization(img_arr)
            predict_nums, predict_res, jud = pre(new_model, img_arr, jud, predict_nums, predict_res, classes)

        predict_nums = predict_nums[:-2]
        cal = calculate(predict_nums)
        res_predict[predict_res] = cal
    
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

    
def define_classes():
    with open('classes_45.pkl', 'rb') as f:
        new_classes = pickle.load(f)
    classes = {}
    for key, value in new_classes.items():
        classes[value] = key

    return classes

def ox(res_predict, mid_points, testimage):
    c = 0
    correct, wrong = [], []
    for key, value in res_predict.items():
        if key == str(value):
            correct.append(c)
        else:
            wrong.append(c)
        c += 1
    
    img = cv2.imread(testimage)
    for i in correct:
        x, y, w, h = mid_points[i][0], mid_points[i][1], mid_points[i][2], mid_points[i][3]
        img = cv2.circle(img, (x+9,y+9), 14, (0,0,255), 2)
    for j in wrong:
        x, y, w, h = mid_points[j][0], mid_points[j][1], mid_points[j][2], mid_points[j][3]
        img = cv2.line(img, (x,y), (x+18, y+18), (0,0,255), 2)
        img = cv2.line(img, (x+18,y), (x, y+18), (0,0,255), 2)
    
    cv2.imshow('img',img)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

    point = len(correct) * 20
    return point


# classes 정의
classes = define_classes()

# 저장된 학습 파일열기
new_model = load_model('test_453.hdf5')

# 경로 설정 및 이미지 변환
testimage = input("파일명 입력(XX.jpg): ")

# 이미지 수정, 예측
res_predict, mid_points = load_and_test(testimage, classes)

# 점수
point = ox(res_predict, mid_points, testimage)
print("제 점수는요 {}점 ㅎ".format(point))